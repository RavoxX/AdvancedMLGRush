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
import com.skillplugins.advancedmlgrush.item.EnumItem;
import com.skillplugins.advancedmlgrush.item.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Singleton
public class PlayerInteractListener implements Listener {


    @Inject
    private EventManager eventManager;
    @Inject
    private JavaPlugin javaPlugin;
    @Inject
    private ItemUtils itemUtils;

    private final List<String> cooldowns = new CopyOnWriteArrayList<>();

    @Inject
    public PlayerInteractListener(final @NotNull EventManager eventManager,
                                  final @NotNull JavaPlugin javaPlugin) {
        this.eventManager = eventManager;
        this.javaPlugin = javaPlugin;
    }

    @EventHandler
    public void onInteract(final @NotNull PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK
                && player.getGameMode() != GameMode.CREATIVE
                && event.getClickedBlock() != null
                && isDoorOrTrapdoor(event.getClickedBlock().getType())) {
            event.setCancelled(true);
        }
        final ItemStack itemInHand = player.getItemInHand();
        if ((event.getAction() == Action.RIGHT_CLICK_AIR
                || event.getAction() == Action.RIGHT_CLICK_BLOCK)
                && itemUtils.compare(itemInHand, EnumItem.CHALLENGER, Optional.of(player))) {
            return;
        }
        if (itemUtils.isValidItem(itemInHand)) {
            player.getInventory().setItem(player.getInventory().getHeldItemSlot(), itemInHand.clone());
        }
        if (!cooldowns.contains(player.getName())) {
            eventManager.callEvent(event);
            cooldowns.add(player.getName());
            Bukkit.getScheduler().scheduleSyncDelayedTask(javaPlugin, () -> cooldowns.remove(player.getName()), 1);
        }
    }

    private boolean isDoorOrTrapdoor(final @NotNull Material material) {
        final String name = material.name();
        return name.equals("WOODEN_DOOR")
                || name.equals("IRON_DOOR_BLOCK")
                || name.equals("TRAP_DOOR")
                || name.endsWith("_DOOR")
                || name.endsWith("_TRAPDOOR");
    }

}
