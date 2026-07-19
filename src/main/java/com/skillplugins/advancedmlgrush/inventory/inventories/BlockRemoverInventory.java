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
import com.skillplugins.advancedmlgrush.config.configs.MainConfig;
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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Singleton
public class BlockRemoverInventory extends AbstractInventory {

    @Inject
    private SQLDataCache sqlDataCache;
    @Inject
    private MainConfig mainConfig;

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
        final String title = inventoryUtils.getInventoryName(InventoryNameConfig.BLOCK_REMOVER);
        final Inventory inventory = Bukkit.createInventory(null, 3 * 9, title);
        inventoryUtils.fill(inventory);
        return new Pair<>(inventory, title);
    }

    @Override
    protected Inventory onOpen(final @NotNull Inventory inventory, final @NotNull Player player) {
        setItems(inventory, player);
        return inventory;
    }

    @Override
    protected List<EventListener<?>> listeners(final @NotNull List<EventListener<?>> eventListeners) {
        final Class<? extends AbstractInventory> clazz = this.getClass();
        eventListeners.add(new EventListener<InventoryClickEvent>(InventoryClickEvent.class, EventListenerPriority.MEDIUM) {
            @Override
            protected void onEvent(final @NotNull InventoryClickEvent event) {
                final Player player = (Player) event.getWhoClicked();
                if (!inventoryUtils.isOpenInventory(player, clazz) || !sqlDataCache.isLoaded(player)) {
                    return;
                }

                final ItemStack currentItem = event.getCurrentItem();
                final Optional<BlockRemover> selected = getSelection(currentItem);
                if (selected.isPresent()) {
                    sqlDataCache.getSQLData(player).setSettingsBlockRemover(selected.get().getId());
                    setItems(event.getInventory(), player);
                    soundUtil.playSound(player, SoundConfig.INVENTORY_CLICK);
                }
            }
        });
        return eventListeners;
    }

    private Optional<BlockRemover> getSelection(final ItemStack itemStack) {
        if (itemUtils.compare(itemStack, EnumItem.BLOCK_REMOVER_OFF, Optional.empty())) {
            return Optional.of(BlockRemover.OFF);
        }
        if (itemUtils.compare(itemStack, EnumItem.BLOCK_REMOVER_NORMAL, Optional.empty())) {
            return Optional.of(BlockRemover.NORMAL);
        }
        if (itemUtils.compare(itemStack, EnumItem.BLOCK_REMOVER_DEATH_RESET, Optional.empty())) {
            return Optional.of(BlockRemover.DEATH_RESET);
        }
        return Optional.empty();
    }

    private void setItems(final @NotNull Inventory inventory, final @NotNull Player player) {
        final CachedSQLData data = sqlDataCache.getSQLData(player);
        final BlockRemover selected = BlockRemover.fromId(data.getSettingsBlockRemover());
        setItem(inventory, EnumItem.BLOCK_REMOVER_OFF, BlockRemover.OFF, selected);
        setItem(inventory, EnumItem.BLOCK_REMOVER_NORMAL, BlockRemover.NORMAL, selected);
        setItem(inventory, EnumItem.BLOCK_REMOVER_DEATH_RESET, BlockRemover.DEATH_RESET, selected);
    }

    private void setItem(final @NotNull Inventory inventory, final @NotNull EnumItem enumItem,
                         final @NotNull BlockRemover blockRemover, final @NotNull BlockRemover selected) {
        final ItemStack itemStack = itemManager.getItem(Optional.empty(), enumItem);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        final List<String> lore = new ArrayList<>();
        switch (blockRemover) {
            case OFF:
                lore.add("\u00a77Placed blocks stay until the game ends.");
                break;
            case NORMAL:
                lore.add("\u00a77Blocks disappear after \u00a7a"
                        + Math.max(0, mainConfig.getInt(MainConfig.BLOCK_REMOVER_DELAY_SECONDS)) + " seconds\u00a77.");
                break;
            case DEATH_RESET:
                lore.add("\u00a77When you fall, only your blocks disappear.");
                break;
            default:
                break;
        }
        lore.add(" ");
        if (blockRemover == selected) {
            lore.add("\u00a7aSelected");
        } else {
            lore.add("\u00a77Click to select");
        }
        itemMeta.setLore(Collections.unmodifiableList(lore));
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(itemManager.getItemSlot(enumItem), itemStack);
    }
}
