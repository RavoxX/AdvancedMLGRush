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
import com.skillplugins.advancedmlgrush.game.gadgets.ChatColorPerk;
import com.skillplugins.advancedmlgrush.sql.data.CachedSQLData;
import com.skillplugins.advancedmlgrush.sql.data.SQLDataCache;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

@Singleton
public class AsyncPlayerChatListener implements Listener {

    final EventManager eventManager;
    private final SQLDataCache sqlDataCache;

    @Inject
    public AsyncPlayerChatListener(final EventManager eventManager, final SQLDataCache sqlDataCache) {
        this.eventManager = eventManager;
        this.sqlDataCache = sqlDataCache;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(final @NotNull AsyncPlayerChatEvent event) {
        eventManager.callEvent(event);
        if (event.isCancelled() || !sqlDataCache.isLoaded(event.getPlayer())) {
            return;
        }

        final CachedSQLData data = sqlDataCache.getSQLData(event.getPlayer());
        final ChatColorPerk chatColor = ChatColorPerk.fromCode(data.getChatColor());
        if (!chatColor.isDefault() && chatColor.hasAccess(event.getPlayer())) {
            event.setMessage(chatColor.getColor() + ChatColor.stripColor(event.getMessage()));
        }
    }

}
