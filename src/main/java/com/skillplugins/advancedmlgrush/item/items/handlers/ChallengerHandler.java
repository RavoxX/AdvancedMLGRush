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

package com.skillplugins.advancedmlgrush.item.items.handlers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.config.configs.MessageConfig;
import com.skillplugins.advancedmlgrush.event.EventHandler;
import com.skillplugins.advancedmlgrush.event.EventListener;
import com.skillplugins.advancedmlgrush.event.EventListenerPriority;
import com.skillplugins.advancedmlgrush.game.challenge.ChallengeManager;
import com.skillplugins.advancedmlgrush.item.EnumItem;
import com.skillplugins.advancedmlgrush.item.ItemUtils;
import com.skillplugins.advancedmlgrush.sql.data.SQLDataCache;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@Singleton
public class ChallengerHandler implements EventHandler {

    @Inject
    private ItemUtils itemUtils;
    @Inject
    private MessageConfig messageConfig;
    @Inject
    private SQLDataCache sqlDataCache;
    @Inject
    private ChallengeManager challengeManager;

    @Override
    public void registerListeners(final @NotNull List<EventListener<?>> eventListeners) {
        eventListeners.add(onLeftClick());
    }

    private EventListener<EntityDamageByEntityEvent> onLeftClick() {
        return new EventListener<EntityDamageByEntityEvent>(EntityDamageByEntityEvent.class, EventListenerPriority.MEDIUM) {
            @Override
            protected void onEvent(final @NotNull EntityDamageByEntityEvent event) {
                if (event.getDamager() instanceof Player
                        && event.getEntity() instanceof Player) {
                    final Player attacker = (Player) event.getDamager();
                    final Player target = (Player) event.getEntity();

                    if (itemUtils.compare(attacker.getItemInHand(), EnumItem.CHALLENGER, Optional.of(attacker))) {
                        if (!sqlDataCache.isLoaded(attacker)) {
                            attacker.sendMessage(messageConfig.getWithPrefix(Optional.of(attacker), MessageConfig.LOADING_DATA));
                        } else if (sqlDataCache.isLoaded(target)) {
                            challengeManager.challengePlayer(attacker, target);
                        }
                    }
                }
            }
        };
    }

}
