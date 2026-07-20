/*
 * Copyright (c) 2021 SkillCode
 *
 * This file is a part of the source code of the
 * AdvancedMLGRush plugin by SkillCode.
 *
 * This file may only be used in compliance with the
 * LICENSE.txt (https://github.com/SkillC0de/AdvancedMLGRush/blob/master/LICENSE.txt).
 *
 * Support: http://discord.skillplugins.com
 */

package com.skillplugins.advancedmlgrush.sql;

import com.google.inject.Inject;
import com.skillplugins.advancedmlgrush.annotations.PostConstruct;
import com.skillplugins.advancedmlgrush.config.configs.DebugConfig;
import com.skillplugins.advancedmlgrush.exception.ExceptionHandler;
import com.skillplugins.advancedmlgrush.miscellaneous.Constants;
import com.skillplugins.advancedmlgrush.util.Pair;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class DataSaver {

    private static final int SQLITE_BUSY_TIMEOUT_MILLIS = 250;
    private static final long SQLITE_BUSY_COOLDOWN_MILLIS = 1_000L;
    private static final long SQLITE_BUSY_LOG_INTERVAL_MILLIS = 30_000L;

    @Inject
    protected ExceptionHandler exceptionHandler;
    @Inject
    protected ThreadPoolManager threadPoolManager;
    @Inject
    private JavaPlugin javaPlugin;
    @Inject
    private ConnectionManager connectionManager;
    @Inject
    private DebugConfig debugConfig;

    private DataSaverParams params;

    @Nullable
    private Connection connection;

    private final Object databaseLock = new Object();

    private volatile long sqliteWritesBlockedUntil;
    private volatile long nextSQLiteBusyLogAt;

    @PostConstruct
    public void init() {
        params = initParams();
        checkConnection();
        initTable();
    }

    public boolean isConnected() {
        synchronized (databaseLock) {
            return isConnectedUnsafe();
        }
    }

    protected boolean executeUpdateSync(final @NotNull String query) {
        logQuery(query);
        synchronized (databaseLock) {
            if (!params.isMySQL()
                    && System.currentTimeMillis() < sqliteWritesBlockedUntil) {
                return false;
            }
            if (checkConnection()) {
                try (final PreparedStatement preparedStatement = connection.prepareStatement(replaceName(query))) {
                    preparedStatement.executeUpdate();
                    sqliteWritesBlockedUntil = 0L;
                    return true;
                } catch (SQLException exception) {
                    if (!params.isMySQL() && isSQLiteBusy(exception)) {
                        handleSQLiteBusy();
                    } else {
                        exceptionHandler.handleUnexpected(exception);
                    }
                    return false;
                }
            }
        }
        return false;
    }

    protected void executeUpdateAsync(final @NotNull String query) {
        threadPoolManager.submitDatabase(() -> executeUpdateSync(query));
    }

    protected <T> Optional<T> executeQuerySync(final @NotNull String query,
                                               final @NotNull ResultSetHandler<T> handler) {
        logQuery(query);
        synchronized (databaseLock) {
            if (checkConnection()) {
                try (final PreparedStatement preparedStatement = connection.prepareStatement(replaceName(query));
                     final ResultSet resultSet = preparedStatement.executeQuery()) {
                    return Optional.ofNullable(handler.handle(resultSet));
                } catch (SQLException exception) {
                    exceptionHandler.handleUnexpected(exception);
                }
            }
        }
        return Optional.empty();
    }

    protected void executeQueryAsync(final @NotNull String query, final @NotNull Callback callback) {
        threadPoolManager.submitDatabase(() -> {
            logQuery(query);
            synchronized (databaseLock) {
                if (checkConnection()) {
                    try (final PreparedStatement preparedStatement = connection.prepareStatement(replaceName(query));
                         final ResultSet resultSet = preparedStatement.executeQuery()) {
                        callback.onSuccess(resultSet);
                    } catch (SQLException exception) {
                        exceptionHandler.handleUnexpected(exception);
                        callback.onFailure(Optional.of(exception));
                    }
                } else {
                    try {
                        callback.onFailure(Optional.empty());
                    } catch (RuntimeException exception) {
                        exceptionHandler.handleUnexpected(exception);
                    }
                }
            }
        });
    }

    protected abstract DataSaverParams initParams();

    protected abstract String creationQuery();

    //column name, value
    protected abstract List<Pair<String, String>> columns(final @NotNull List<Pair<String, String>> columns);

    private boolean checkConnection() {
        synchronized (databaseLock) {
            if (!isConnectedUnsafe()) {
                createConnection();
            }
            return isConnectedUnsafe();
        }
    }

    private void initTable() {
        synchronized (databaseLock) {
            if (checkConnection()) {
                executeUpdateSync(creationQuery());
                final List<Pair<String, String>> columns = columns(new ArrayList<>());
                try {
                    final DatabaseMetaData databaseMetaData = connection.getMetaData();

                    for (final Pair<String, String> pair : columns) {
                        final String columnName = pair.getKey();
                        final String value = pair.getValue();

                        final boolean missingColumn;
                        try (final ResultSet resultSet = databaseMetaData.getColumns(null, null,
                                replaceName("{name}"), columnName)) {
                            missingColumn = !resultSet.next();
                        }
                        if (missingColumn) {
                            executeUpdateSync(String.format("ALTER TABLE {name} ADD COLUMN %s;", columnName + " " + value));
                        }
                    }
                } catch (SQLException sqlException) {
                    exceptionHandler.handleUnexpected(sqlException);
                }
            }
        }
    }

    private boolean isConnectedUnsafe() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException throwables) {
            exceptionHandler.handleUnexpected(throwables);
        }
        return false;
    }

    private void createConnection() {
        connection = params.isMySQL() ? createMySQLConnection() : createSQLiteConnection();
        connectionManager.addConnection(connection);
    }

    @Nullable
    private Connection createMySQLConnection() {
        try {
            final String url = "jdbc:mysql://" + params.getHost() + ":" + checkPort(params.getPort()) + "/" + params.getDatabase();

            return DriverManager.getConnection(url, params.getUser(), params.getPassword());
        } catch (SQLException throwables) {
            exceptionHandler.handleUnexpected(throwables);
        }
        return null;
    }

    @Nullable
    private Connection createSQLiteConnection() {
        Connection sqliteConnection = null;
        try {
            Class.forName("org.sqlite.JDBC");

            final File file = new File(params.getDataFilePath());
            file.getParentFile().mkdirs();

            sqliteConnection = DriverManager.getConnection("jdbc:sqlite:" + file.getPath());
            try (final Statement statement = sqliteConnection.createStatement()) {
                statement.execute("PRAGMA busy_timeout = " + SQLITE_BUSY_TIMEOUT_MILLIS);
                try {
                    statement.execute("PRAGMA journal_mode = WAL");
                    statement.execute("PRAGMA synchronous = NORMAL");
                } catch (SQLException exception) {
                    if (!isSQLiteBusy(exception)) {
                        throw exception;
                    }
                }
            }
            return sqliteConnection;
        } catch (SQLException | ClassNotFoundException throwables) {
            closeFailedConnection(sqliteConnection, throwables);
            exceptionHandler.handleUnexpected(throwables);
        }
        return null;
    }

    private void closeFailedConnection(final @Nullable Connection failedConnection,
                                       final @NotNull Exception originalException) {
        if (failedConnection != null) {
            try {
                failedConnection.close();
            } catch (SQLException closeException) {
                originalException.addSuppressed(closeException);
            }
        }
    }

    private String checkPort(final @NotNull String port) {
        String finalPort = port;
        try {
            Integer.parseInt(port);
        } catch (final NumberFormatException exception) {
            finalPort = "3306";
            javaPlugin.getLogger().warning(Constants.INVALID_PORT_MESSAGE);
        }
        return finalPort;
    }

    private String replaceName(final @NotNull String query) {
        return query.replace("{name}", params.getTable());
    }

    private void logQuery(final @NotNull String query) {
        if (debugConfig.getBoolean(DebugConfig.LOG_QUERIES)) {
            javaPlugin.getLogger().info(String.format(Constants.QUERY_MESSAGE, query));
        }
    }

    private boolean isSQLiteBusy(final @NotNull SQLException exception) {
        SQLException current = exception;
        while (current != null) {
            final String message = current.getMessage();
            if (current.getErrorCode() == 5
                    || (message != null && message.contains("SQLITE_BUSY"))) {
                return true;
            }
            current = current.getNextException();
        }
        return false;
    }

    private void handleSQLiteBusy() {
        final long now = System.currentTimeMillis();
        sqliteWritesBlockedUntil = now + SQLITE_BUSY_COOLDOWN_MILLIS;
        if (now >= nextSQLiteBusyLogAt) {
            nextSQLiteBusyLogAt = now + SQLITE_BUSY_LOG_INTERVAL_MILLIS;
            javaPlugin.getLogger().warning("SQLite database is locked by another process or plugin: "
                    + new File(params.getDataFilePath()).getAbsolutePath());
        }
    }

    @FunctionalInterface
    protected interface ResultSetHandler<T> {

        T handle(final @NotNull ResultSet resultSet) throws SQLException;

    }

    public interface Callback {

        void onSuccess(final @NotNull ResultSet resultSet) throws SQLException;

        void onFailure(final @NotNull Optional<Exception> optional);

    }
}
