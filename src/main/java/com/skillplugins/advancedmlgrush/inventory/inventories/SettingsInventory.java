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

package com.skillplugins.advancedmlgrush.inventory.inventories;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.annotations.PostConstruct;
import com.skillplugins.advancedmlgrush.config.configs.InventoryNameConfig;
import com.skillplugins.advancedmlgrush.config.configs.SoundConfig;
import com.skillplugins.advancedmlgrush.event.EventListener;
import com.skillplugins.advancedmlgrush.event.EventListenerPriority;
import com.skillplugins.advancedmlgrush.game.map.BlockRemover;
import com.skillplugins.advancedmlgrush.inventory.AbstractInventory;
import com.skillplugins.advancedmlgrush.item.EnumItem;
import com.skillplugins.advancedmlgrush.sql.data.CachedSQLData;
import com.skillplugins.advancedmlgrush.sql.data.SQLDataCache;
import com.skillplugins.advancedmlgrush.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class SettingsInventory extends AbstractInventory {

    @Inject
    private RoundsInventory roundsInventory;
    @Inject
    private MapInventory mapInventory;
    @Inject
    private SQLDataCache sqlDataCache;

    @PostConstruct
    public void initInventory() {
        super.init();
    }

    @Override
    protected boolean cloneOnOpen() {
        return true;
    }

    @Override
    protected boolean playSound() {
        return true;
    }

    @Override
    protected Pair<Inventory, String> onCreate() {
        final String title = inventoryUtils.getInventoryName(InventoryNameConfig.SETTINGS);
        final Inventory inventory = Bukkit.createInventory(null, 3 * 9, title);
        inventoryUtils.fill(inventory);

        itemManager.setItem(inventory, Optional.empty(), EnumItem.SETTINGS_MAP);
        itemManager.setItem(inventory, Optional.empty(), EnumItem.SETTINGS_ROUNDS);
        itemManager.setItem(inventory, Optional.empty(), EnumItem.SETTINGS_BLOCK_REMOVER);

        return new Pair<>(inventory, title);
    }

    @Override
    protected Inventory onOpen(final @NotNull Inventory inventory, final @NotNull Player player) {
        setBlockRemoverItem(inventory, player);
        return inventory;
    }

    @Override
    protected List<EventListener<?>> listeners(final @NotNull List<EventListener<?>> eventListeners) {
        final Class<? extends AbstractInventory> clazz = this.getClass();
        eventListeners.add(new EventListener<InventoryClickEvent>(InventoryClickEvent.class, EventListenerPriority.MEDIUM) {
            @Override
            protected void onEvent(final @NotNull InventoryClickEvent event) {
                final Player player = (Player) event.getWhoClicked();
                if (inventoryUtils.isOpenInventory(player, clazz)) {
                    final Optional<Player> optionalPlayer = Optional.of(player);
                    final ItemStack currentItem = event.getCurrentItem();
                    if (itemUtils.compare(currentItem, EnumItem.SETTINGS_MAP, optionalPlayer)) {
                        mapInventory.open(player);
                        soundUtil.playSound(player, SoundConfig.INVENTORY_CLICK);
                    } else if (itemUtils.compare(currentItem, EnumItem.SETTINGS_ROUNDS, optionalPlayer)) {
                        roundsInventory.open(player);
                        soundUtil.playSound(player, SoundConfig.INVENTORY_CLICK);
                    } else if (itemUtils.compare(currentItem, EnumItem.SETTINGS_BLOCK_REMOVER, optionalPlayer)) {
                        if (sqlDataCache.isLoaded(player)) {
                            final CachedSQLData data = sqlDataCache.getSQLData(player);
                            final BlockRemover next = BlockRemover.fromId(data.getSettingsBlockRemover()).next();
                            data.setSettingsBlockRemover(next.getId());
                            setBlockRemoverItem(event.getInventory(), player);
                            soundUtil.playSound(player, SoundConfig.INVENTORY_CLICK);
                        }
                    }
                }
            }
        });
        eventListeners.add(new EventListener<InventoryCloseEvent>(InventoryCloseEvent.class, EventListenerPriority.MEDIUM) {
            @Override
            protected void onEvent(final @NotNull InventoryCloseEvent event) {
                final Player player = (Player) event.getPlayer();
                if (inventoryUtils.isOpenInventory(player, clazz)) {
                    sqlDataCache.save(player);
                }
            }
        });
        return eventListeners;
    }

    private void setBlockRemoverItem(final @NotNull Inventory inventory, final @NotNull Player player) {
        final BlockRemover selected = BlockRemover.fromId(
                sqlDataCache.getSQLData(player).getSettingsBlockRemover());
        final ItemStack itemStack = itemManager.getItem(Optional.of(player), EnumItem.SETTINGS_BLOCK_REMOVER);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        final List<String> lore = new ArrayList<>();

        for (final BlockRemover mode : BlockRemover.values()) {
            lore.add((mode == selected ? "\u00a7a" : "\u00a77") + mode.getDisplayName());
        }

        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(itemManager.getItemSlot(EnumItem.SETTINGS_BLOCK_REMOVER), itemStack);
    }
}
