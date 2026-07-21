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

import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.annotations.PostConstruct;
import com.skillplugins.advancedmlgrush.config.Configurable;
import com.skillplugins.advancedmlgrush.item.EnumItem;
import com.skillplugins.advancedmlgrush.miscellaneous.Constants;
import com.skillplugins.advancedmlgrush.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Singleton
public class SlotsConfig extends Configurable {

    @PostConstruct
    public void initConfig() {
        super.init();
        boolean changed = false;

        final int oldInventorySettingsSlot = getInt(EnumItem.SETTINGS_INVENTORY_SORTING.getConfigPath());
        if (oldInventorySettingsSlot >= 0) {
            getYamlConfiguration().set(EnumItem.SETTINGS_INVENTORY_SORTING.getConfigPath(), -1);
            getYamlConfiguration().set(EnumItem.SETTINGS_BLOCK_REMOVER.getConfigPath(), oldInventorySettingsSlot);
            changed = true;
        } else if (getInt(EnumItem.SETTINGS_BLOCK_REMOVER.getConfigPath()) == 22) {
            getYamlConfiguration().set(EnumItem.SETTINGS_BLOCK_REMOVER.getConfigPath(), 11);
            changed = true;
        }

        changed |= setSlot(EnumItem.SETTINGS, 2);
        changed |= setSlot(EnumItem.CHALLENGER, 4);
        changed |= setSlot(EnumItem.GADGETS, 6);
        changed |= setSlot(EnumItem.SPECTATE, -1);
        changed |= setSlot(EnumItem.STATS, -1);

        changed |= migrateSlot(EnumItem.SETTINGS_MAP, 13, 4);
        changed |= migrateSlot(EnumItem.SETTINGS_MAP, 3, 4);
        changed |= migrateSlot(EnumItem.STATS_RANKING, 13, 13);
        changed |= migrateSlot(EnumItem.STATS_WINS, 19, 11);
        changed |= migrateSlot(EnumItem.STATS_LOSES, 24, 15);
        changed |= migrateSlot(EnumItem.STATS_WIN_RATE, 14, 22);
        changed |= migrateSlot(EnumItem.STATS_BEDS, 12, 20);
        changed |= migrateSlot(EnumItem.STATS_KILLS, 20, 24);
        changed |= migrateSlot(EnumItem.STATS_DEATHS, 25, 30);
        changed |= migrateSlot(EnumItem.STATS_PLACED_BLOCKS, 22, 32);

        if (changed) {
            saveConfig();
        }
    }

    @Override
    protected String filePath() {
        return Constants.SLOTS_CONFIG_PATH;
    }

    @Override
    protected void configure(final @NotNull List<Pair<String, Object>> list) {
        list.add(new Pair<>(EnumItem.CHALLENGER.getConfigPath(), 4));
        list.add(new Pair<>(EnumItem.SETTINGS.getConfigPath(), 2));
        list.add(new Pair<>(EnumItem.SPECTATE.getConfigPath(), -1));
        list.add(new Pair<>(EnumItem.GADGETS.getConfigPath(), 6));
        list.add(new Pair<>(EnumItem.STATS.getConfigPath(), -1));
        list.add(new Pair<>(EnumItem.QUEUE_LEAVE.getConfigPath(), 4));
        list.add(new Pair<>(EnumItem.SETTINGS_INVENTORY_SORTING.getConfigPath(), -1));
        list.add(new Pair<>(EnumItem.SETTINGS_MAP.getConfigPath(), 4));
        list.add(new Pair<>(EnumItem.SETTINGS_ROUNDS.getConfigPath(), 15));
        list.add(new Pair<>(EnumItem.SETTINGS_BLOCK_REMOVER.getConfigPath(), 11));
        list.add(new Pair<>(EnumItem.SETTINGS_ATTACK_RANGE.getConfigPath(), 22));
        list.add(new Pair<>(EnumItem.BLOCK_REMOVER_OFF.getConfigPath(), 11));
        list.add(new Pair<>(EnumItem.BLOCK_REMOVER_NORMAL.getConfigPath(), 13));
        list.add(new Pair<>(EnumItem.BLOCK_REMOVER_DEATH_RESET.getConfigPath(), 15));
        list.add(new Pair<>(EnumItem.GADGETS_STICK.getConfigPath(), 11));
        list.add(new Pair<>(EnumItem.GADGETS_BLOCKS.getConfigPath(), 15));
        list.add(new Pair<>(EnumItem.GADGETS_CHAT_COLOR.getConfigPath(), 13));
        list.add(new Pair<>(EnumItem.SORTING_SAVE.getConfigPath(), 21));
        list.add(new Pair<>(EnumItem.SORTING_RESET.getConfigPath(), 23));
        list.add(new Pair<>(EnumItem.ROUNDS.getConfigPath(), 13));
        list.add(new Pair<>(EnumItem.STATS_WINS.getConfigPath(), 11));
        list.add(new Pair<>(EnumItem.STATS_LOSES.getConfigPath(), 15));
        list.add(new Pair<>(EnumItem.STATS_WIN_RATE.getConfigPath(), 22));
        list.add(new Pair<>(EnumItem.STATS_BEDS.getConfigPath(), 20));
        list.add(new Pair<>(EnumItem.STATS_RANKING.getConfigPath(), 13));
        list.add(new Pair<>(EnumItem.STATS_KILLS.getConfigPath(), 24));
        list.add(new Pair<>(EnumItem.STATS_DEATHS.getConfigPath(), 30));
        list.add(new Pair<>(EnumItem.STATS_PLACED_BLOCKS.getConfigPath(), 32));
        list.add(new Pair<>(EnumItem.QUEUE_2X1.getConfigPath(), 11));
        list.add(new Pair<>(EnumItem.QUEUE_4X1.getConfigPath(), 15));
        list.add(new Pair<>(EnumItem.SPECTATE_LEAVE.getConfigPath(), 4));
        list.add(new Pair<>(EnumItem.RANDOM_ITEM.getConfigPath(), 49));
        list.add(new Pair<>(EnumItem.ROUNDS_DECREASE.getConfigPath(), 12));
        list.add(new Pair<>(EnumItem.ROUNDS_INCREASE.getConfigPath(), 14));
        list.add(new Pair<>(EnumItem.ARROW_LEFT.getConfigPath(), 45));
        list.add(new Pair<>(EnumItem.ARROW_RIGHT.getConfigPath(), 53));
    }

    public int getSlot(final @NotNull EnumItem enumItem) {
        return getInt(enumItem.getConfigPath());
    }

    private boolean migrateSlot(final @NotNull EnumItem enumItem, final int oldSlot, final int newSlot) {
        if (oldSlot != newSlot && getInt(enumItem.getConfigPath()) == oldSlot) {
            getYamlConfiguration().set(enumItem.getConfigPath(), newSlot);
            return true;
        }
        return false;
    }

    private boolean setSlot(final @NotNull EnumItem enumItem, final int slot) {
        if (getInt(enumItem.getConfigPath()) != slot) {
            getYamlConfiguration().set(enumItem.getConfigPath(), slot);
            return true;
        }
        return false;
    }


}
