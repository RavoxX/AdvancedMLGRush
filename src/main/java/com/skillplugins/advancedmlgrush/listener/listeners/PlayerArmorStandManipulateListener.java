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
import com.skillplugins.advancedmlgrush.event.EventManager;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.jetbrains.annotations.NotNull;

@NotNull
public class PlayerArmorStandManipulateListener implements Listener {

    private final EventManager eventManager;

    @Inject
    public PlayerArmorStandManipulateListener(final @NotNull EventManager eventManager) {
        this.eventManager = eventManager;
    }

    @EventHandler
    public void onClick(final @NotNull PlayerArmorStandManipulateEvent event) {
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
        }
        eventManager.callEvent(event);
    }
}
