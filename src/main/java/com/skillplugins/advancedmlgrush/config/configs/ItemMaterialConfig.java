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

package com.skillplugins.advancedmlgrush.config.configs;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.annotations.PostConstruct;
import com.skillplugins.advancedmlgrush.config.Configurable;
import com.skillplugins.advancedmlgrush.item.EnumItem;
import com.skillplugins.advancedmlgrush.item.parser.MaterialParser;
import com.skillplugins.advancedmlgrush.lib.xseries.XMaterial;
import com.skillplugins.advancedmlgrush.miscellaneous.Constants;
import com.skillplugins.advancedmlgrush.util.Pair;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Singleton
public class ItemMaterialConfig extends Configurable {

    private final MaterialParser materialParser;

    @Inject
    public ItemMaterialConfig(final MaterialParser materialParser) {
        this.materialParser = materialParser;
    }

    @PostConstruct
    public void initConfig() {
        super.init();
        boolean changed = false;
        changed |= migrateMaterial(EnumItem.GADGETS, XMaterial.CHEST, XMaterial.DIAMOND);
        changed |= migrateMaterial(EnumItem.QUEUE_2X1, XMaterial.NETHER_STAR, XMaterial.BED);
        changed |= migrateMaterial(EnumItem.QUEUE_4X1, XMaterial.NETHER_STAR, XMaterial.BED);
        changed |= migrateMaterial(EnumItem.STATS_WINS, XMaterial.IRON_SWORD, XMaterial.EMERALD);
        changed |= migrateMaterial(EnumItem.STATS_LOSES, XMaterial.COAL, XMaterial.REDSTONE);
        changed |= migrateMaterial(EnumItem.STATS_RANKING, XMaterial.GOLD_BLOCK, XMaterial.NETHER_STAR);
        changed |= migrateMaterial(EnumItem.STATS_KILLS, XMaterial.BOW, XMaterial.IRON_SWORD);
        changed |= migrateMaterial(EnumItem.STATS_DEATHS, XMaterial.CHAINMAIL_CHESTPLATE, XMaterial.SKELETON_SKULL);
        changed |= migrateMaterial(EnumItem.STATS_PLACED_BLOCKS, XMaterial.SANDSTONE, XMaterial.BRICKS);
        if (changed) {
            saveConfig();
        }
    }

    @Override
    protected String filePath() {
        return Constants.ITEM_MATERIAL_CONFIG_PATH;
    }

    @Override
    protected void configure(final @NotNull List<Pair<String, Object>> list) {
        list.add(new Pair<>(EnumItem.INVENTORY_BACKGROUND.getConfigPath(), XMaterial.GRAY_STAINED_GLASS_PANE.name()));
        list.add(new Pair<>(EnumItem.CHALLENGER.getConfigPath(), XMaterial.IRON_SWORD.name()));
        list.add(new Pair<>(EnumItem.SETTINGS.getConfigPath(), XMaterial.REPEATER.name()));
        list.add(new Pair<>(EnumItem.SPECTATE.getConfigPath(), XMaterial.COMPASS.name()));
        list.add(new Pair<>(EnumItem.GADGETS.getConfigPath(), XMaterial.DIAMOND.name()));
        list.add(new Pair<>(EnumItem.STATS.getConfigPath(), XMaterial.PLAYER_HEAD.name()));
        list.add(new Pair<>(EnumItem.PICKAXE.getConfigPath(), XMaterial.STONE_PICKAXE.name()));
        list.add(new Pair<>(EnumItem.QUEUE_LEAVE.getConfigPath(), XMaterial.BARRIER.name()));
        list.add(new Pair<>(EnumItem.SETTINGS_INVENTORY_SORTING.getConfigPath(), XMaterial.REPEATER.name()));
        list.add(new Pair<>(EnumItem.SETTINGS_MAP.getConfigPath(), XMaterial.MAP.name()));
        list.add(new Pair<>(EnumItem.SETTINGS_ROUNDS.getConfigPath(), XMaterial.SLIME_BALL.name()));
        list.add(new Pair<>(EnumItem.SETTINGS_BLOCK_REMOVER.getConfigPath(), XMaterial.CLOCK.name()));
        list.add(new Pair<>(EnumItem.BLOCK_REMOVER_OFF.getConfigPath(), XMaterial.BARRIER.name()));
        list.add(new Pair<>(EnumItem.BLOCK_REMOVER_NORMAL.getConfigPath(), XMaterial.CLOCK.name()));
        list.add(new Pair<>(EnumItem.BLOCK_REMOVER_DEATH_RESET.getConfigPath(), XMaterial.REDSTONE.name()));
        list.add(new Pair<>(EnumItem.GADGETS_STICK.getConfigPath(), XMaterial.STICK.name()));
        list.add(new Pair<>(EnumItem.GADGETS_BLOCKS.getConfigPath(), XMaterial.SANDSTONE.name()));
        list.add(new Pair<>(EnumItem.GADGETS_CHAT_COLOR.getConfigPath(), XMaterial.NAME_TAG.name()));
        list.add(new Pair<>(EnumItem.SORTING_SAVE.getConfigPath(), XMaterial.LIME_DYE.name()));
        list.add(new Pair<>(EnumItem.SORTING_RESET.getConfigPath(), XMaterial.RED_DYE.name()));
        list.add(new Pair<>(EnumItem.ROUNDS.getConfigPath(), XMaterial.SLIME_BALL.name()));
        list.add(new Pair<>(EnumItem.STATS_WINS.getConfigPath(), XMaterial.EMERALD.name()));
        list.add(new Pair<>(EnumItem.STATS_LOSES.getConfigPath(), XMaterial.REDSTONE.name()));
        list.add(new Pair<>(EnumItem.STATS_WIN_RATE.getConfigPath(), XMaterial.DIAMOND.name()));
        list.add(new Pair<>(EnumItem.STATS_BEDS.getConfigPath(), XMaterial.BED.name()));
        list.add(new Pair<>(EnumItem.STATS_RANKING.getConfigPath(), XMaterial.NETHER_STAR.name()));
        list.add(new Pair<>(EnumItem.STATS_KILLS.getConfigPath(), XMaterial.IRON_SWORD.name()));
        list.add(new Pair<>(EnumItem.STATS_DEATHS.getConfigPath(), XMaterial.SKELETON_SKULL.name()));
        list.add(new Pair<>(EnumItem.STATS_PLACED_BLOCKS.getConfigPath(), XMaterial.BRICKS.name()));
        list.add(new Pair<>(EnumItem.QUEUE_2X1.getConfigPath(), XMaterial.BED.name()));
        list.add(new Pair<>(EnumItem.QUEUE_4X1.getConfigPath(), XMaterial.BED.name()));
        list.add(new Pair<>(EnumItem.SPECTATE_LEAVE.getConfigPath(), XMaterial.MAGMA_CREAM.name()));
    }

    public Pair<Material, Integer> getMaterial(final @NotNull String path) {
        return materialParser.parse(super.getString(path));
    }

    public Pair<Material, Integer> getMaterial(final @NotNull EnumItem enumItem) {
        return getMaterial(enumItem.getConfigPath());
    }

    private boolean migrateMaterial(final @NotNull EnumItem enumItem,
                                    final @NotNull XMaterial oldMaterial,
                                    final @NotNull XMaterial newMaterial) {
        final String path = enumItem.getConfigPath();
        final String configured = getYamlConfiguration().getString(path);
        if (configured != null && configured.equalsIgnoreCase(oldMaterial.name())) {
            getYamlConfiguration().set(path, newMaterial.name());
            return true;
        }
        return false;
    }
}
