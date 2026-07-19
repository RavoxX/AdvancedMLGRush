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
import com.skillplugins.advancedmlgrush.event.EventListener;
import com.skillplugins.advancedmlgrush.inventory.AbstractInventory;
import com.skillplugins.advancedmlgrush.item.EnumItem;
import com.skillplugins.advancedmlgrush.item.builder.MetaType;
import com.skillplugins.advancedmlgrush.placeholder.Placeholders;
import com.skillplugins.advancedmlgrush.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Singleton
public class StatsInventory extends AbstractInventory {

    @Inject
    private Placeholders placeholders;

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
        final String title = inventoryUtils.getInventoryName(InventoryNameConfig.STATS);
        final Inventory inventory = Bukkit.createInventory(null, 5 * 9, title);

        inventoryUtils.frame(inventory);
        return new Pair<>(inventory, title);
    }

    @Override
    protected Inventory onOpen(final @NotNull Inventory inventory, final @NotNull Player player) {
        final Optional<Player> optionalPlayer = Optional.of(player);

        final List<String> profileLore = new ArrayList<>(Arrays.asList(
                " ",
                "&7Your personal MLGrush overview",
                " ",
                "&8Games &7• &c%stats_wins% wins &8/ &c%stats_loses% losses"
        ));
        placeholders.replace(optionalPlayer, profileLore);
        inventory.setItem(4, ibFactory.create(MetaType.SKULL_META, 3)
                .owner(player.getName())
                .name(placeholders.replace(optionalPlayer, "&e&l" + player.getName()))
                .lore(profileLore)
                .build());

        setStatItem(inventory, optionalPlayer, EnumItem.STATS_WINS,
                "&7Rounds won", "&c%stats_wins%");
        setStatItem(inventory, optionalPlayer, EnumItem.STATS_LOSES,
                "&7Rounds lost", "&c%stats_loses%");
        setStatItem(inventory, optionalPlayer, EnumItem.STATS_WIN_RATE,
                "&7Wins per defeat", "&b%stats_win_rate%");
        setStatItem(inventory, optionalPlayer, EnumItem.STATS_BEDS,
                "&7Enemy beds destroyed", "&e%stats_beds%");
        setStatItem(inventory, optionalPlayer, EnumItem.STATS_RANKING,
                "&7Position on the leaderboard", "&6#%stats_ranking%");
        setStatItem(inventory, optionalPlayer, EnumItem.STATS_KILLS,
                "&7Players knocked into the void", "&c%stats_kills%");
        setStatItem(inventory, optionalPlayer, EnumItem.STATS_DEATHS,
                "&7Times fallen into the void", "&7%stats_deaths%");
        setStatItem(inventory, optionalPlayer, EnumItem.STATS_PLACED_BLOCKS,
                "&7Blocks placed in rounds", "&f%stats_placed_blocks%");
        return inventory;
    }

    private void setStatItem(final @NotNull Inventory inventory, final @NotNull Optional<Player> optionalPlayer,
                             final @NotNull EnumItem enumItem, final @NotNull String description,
                             final @NotNull String value) {
        final ItemStack itemStack = itemManager.getItem(optionalPlayer, enumItem);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        final List<String> lore = new ArrayList<>(Arrays.asList(" ", description, " ", value));
        placeholders.replace(optionalPlayer, lore);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(itemManager.getItemSlot(enumItem), itemStack);
    }

    @Override
    protected List<EventListener<?>> listeners(final @NotNull List<EventListener<?>> eventListeners) {
        return eventListeners;
    }
}
