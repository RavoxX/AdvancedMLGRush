package com.skillplugins.advancedmlgrush.command.commands;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.config.configs.MessageConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Singleton
public class PingCommand implements CommandExecutor {

    private final MessageConfig messageConfig;

    @Inject
    public PingCommand(final @NotNull MessageConfig messageConfig) {
        this.messageConfig = messageConfig;
    }

    @Override
    public boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command command,
                             final @NotNull String label, final @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player.");
            return true;
        }

        final Player player = (Player) sender;
        player.sendMessage(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.PING)
                .replace("%ping%", Integer.toString(getPing(player))));
        return true;
    }

    private int getPing(final @NotNull Player player) {
        try {
            final Object handle = player.getClass().getMethod("getHandle").invoke(player);
            return handle.getClass().getField("ping").getInt(handle);
        } catch (ReflectiveOperationException exception) {
            return 0;
        }
    }
}
