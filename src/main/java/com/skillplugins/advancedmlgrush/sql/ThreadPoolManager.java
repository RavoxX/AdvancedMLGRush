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

import com.google.inject.Singleton;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Singleton
public class ThreadPoolManager {

    private static final long SHUTDOWN_TIMEOUT_SECONDS = 10L;

    private final ExecutorService threadPool = Executors.newCachedThreadPool(
            namedThreadFactory("AdvancedMLGRush-Worker-"));
    private final ExecutorService databaseThreadPool = Executors.newSingleThreadExecutor(
            namedThreadFactory("AdvancedMLGRush-Database-"));

    public Future<?> submit(final @NotNull Runnable runnable) {
        return threadPool.submit(runnable);
    }

    public Future<?> submitDatabase(final @NotNull Runnable runnable) {
        return databaseThreadPool.submit(runnable);
    }

    public void shutdown() {
        threadPool.shutdown();
        databaseThreadPool.shutdown();

        awaitTermination(databaseThreadPool);
        awaitTermination(threadPool);
    }

    private void awaitTermination(final @NotNull ExecutorService executorService) {
        try {
            if (!executorService.awaitTermination(SHUTDOWN_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException exception) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    private static ThreadFactory namedThreadFactory(final @NotNull String prefix) {
        final AtomicInteger threadNumber = new AtomicInteger();
        return runnable -> new Thread(runnable, prefix + threadNumber.incrementAndGet());
    }
}
