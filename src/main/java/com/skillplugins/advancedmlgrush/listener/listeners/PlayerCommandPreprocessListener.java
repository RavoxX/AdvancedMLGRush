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

package com.skillplugins.advancedmlgrush.listener.listeners;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.config.configs.MainConfig;
import com.skillplugins.advancedmlgrush.config.configs.MessageConfig;
import com.skillplugins.advancedmlgrush.miscellaneous.Constants;
import com.skillplugins.advancedmlgrush.placeholder.Placeholders;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class PlayerCommandPreprocessListener implements Listener {

    @Inject
    private MessageConfig messageConfig;
    @Inject
    private MainConfig mainConfig;
    @Inject
    private JavaPlugin javaPlugin;
    @Inject
    private Placeholders placeholders;

    private final Map<UUID, Long> nickCommandCooldowns = new ConcurrentHashMap<>();

    @Inject
    public PlayerCommandPreprocessListener(final @NotNull MessageConfig messageConfig) {
        this.messageConfig = messageConfig;
    }

    @EventHandler
    public void onCommand(final @NotNull PlayerCommandPreprocessEvent event) {
        final String message = event.getMessage().trim();
        final String command = getCommandName(message);

        if (command.equals("nick") || command.equals("unnick")) {
            if (!checkNickCommandCooldown(event)) {
                return;
            }
        }

        if (message.equalsIgnoreCase("/goonrush")) {
            final Player player = event.getPlayer();
            player.sendMessage(placeholders.replace(Optional.of(player),
                    String.format(messageConfig.getString(MessageConfig.PREFIX) + Constants.MLGRUSH_COMMAND_MESSAGE,
                            javaPlugin.getDescription().getVersion())));
            event.setCancelled(true);
        }
    }

    private boolean checkNickCommandCooldown(final @NotNull PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        final long now = System.currentTimeMillis();
        final long nextAllowed = nickCommandCooldowns.getOrDefault(player.getUniqueId(), 0L);

        if (nextAllowed > now) {
            final long remainingSeconds = (nextAllowed - now + 999L) / 1000L;
            player.sendMessage(messageConfig.getWithPrefix(Optional.of(player),
                    MessageConfig.NICK_COMMAND_COOLDOWN)
                    .replace("%seconds%", Long.toString(remainingSeconds)));
            event.setCancelled(true);
            return false;
        }

        final long cooldownMillis = Math.max(0,
                mainConfig.getInt(MainConfig.NICK_COMMAND_COOLDOWN_SECONDS)) * 1000L;
        if (cooldownMillis > 0L) {
            nickCommandCooldowns.put(player.getUniqueId(), now + cooldownMillis);
        } else {
            nickCommandCooldowns.remove(player.getUniqueId());
        }
        return true;
    }

    private String getCommandName(final @NotNull String message) {
        if (!message.startsWith("/") || message.length() == 1) {
            return "";
        }

        String command = message.substring(1).split("\\s+", 2)[0].toLowerCase(Locale.ROOT);
        final int namespaceSeparator = command.indexOf(':');
        if (namespaceSeparator >= 0) {
            command = command.substring(namespaceSeparator + 1);
        }
        return command;
    }

}
