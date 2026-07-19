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
import com.skillplugins.advancedmlgrush.event.EventManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.jetbrains.annotations.NotNull;

@Singleton
public class PlayerDropItemListener implements Listener {

    private final EventManager eventManager;

    @Inject
    public PlayerDropItemListener(final @NotNull EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @EventHandler
    public void onClick(final @NotNull PlayerDropItemEvent event) {
        final Player player = event.getPlayer();
        event.setCancelled(player.getGameMode() != GameMode.CREATIVE);

        eventManager.callEvent(event);
    }

}
