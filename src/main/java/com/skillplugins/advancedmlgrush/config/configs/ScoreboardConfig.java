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
import com.skillplugins.advancedmlgrush.miscellaneous.Constants;
import com.skillplugins.advancedmlgrush.placeholder.Placeholders;
import com.skillplugins.advancedmlgrush.placeholder.Replaceable;
import com.skillplugins.advancedmlgrush.util.Pair;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Singleton
public class ScoreboardConfig extends Configurable implements Replaceable {

    public static final String SCOREBOARD_TITLE = "scoreboard_title";
    public static final String LOBBY_SCOREBOARD = "lobby_scoreboard";
    public static final String MAP_2X1_SCOREBOARD = "map_2x1_scoreboard";
    public static final String MAP_4X1_SCOREBOARD = "map_4x1_scoreboard";
    public static final String ROUND_ACTIONBAR = "round_actionbar";
    private static final String GOONRUSH_SCOREBOARD_MIGRATED = "goonrush_scoreboard_migrated";
    private static final String GOONRUSH_BLUE_THEME_MIGRATED = "goonrush_blue_theme_migrated";
    private static final String GOONRUSH_GREEN_THEME_MIGRATED = "goonrush_green_theme_migrated";
    private static final String GOONRUSH_RED_THEME_MIGRATED = "goonrush_red_theme_migrated";
    private static final String GOONRUSH_RED_ACTIONBAR_MIGRATED = "goonrush_red_actionbar_migrated";

    private final Placeholders placeholders;

    @Inject
    public ScoreboardConfig(final @NotNull Placeholders placeholders) {
        this.placeholders = placeholders;
    }

    @PostConstruct
    public void initConfig() {
        super.init();
        if (!getBoolean(GOONRUSH_SCOREBOARD_MIGRATED)) {
            getYamlConfiguration().set(SCOREBOARD_TITLE, "&c&lGOONRUSH");
            getYamlConfiguration().set(MAP_2X1_SCOREBOARD, createRoundScoreboard());
            getYamlConfiguration().set(MAP_4X1_SCOREBOARD, createRoundScoreboard());
            getYamlConfiguration().set(GOONRUSH_SCOREBOARD_MIGRATED, true);
            saveConfig();
        }
        if (!getBoolean(GOONRUSH_BLUE_THEME_MIGRATED)) {
            getYamlConfiguration().set(SCOREBOARD_TITLE, "&c&lGOONRUSH");
            getYamlConfiguration().set(MAP_2X1_SCOREBOARD, createRoundScoreboard());
            getYamlConfiguration().set(MAP_4X1_SCOREBOARD, createRoundScoreboard());
            getYamlConfiguration().set(GOONRUSH_BLUE_THEME_MIGRATED, true);
            saveConfig();
        }
        if (!getBoolean(GOONRUSH_GREEN_THEME_MIGRATED)) {
            getYamlConfiguration().set(SCOREBOARD_TITLE, "&c&lGOONRUSH");
            getYamlConfiguration().set(MAP_2X1_SCOREBOARD, createRoundScoreboard());
            getYamlConfiguration().set(MAP_4X1_SCOREBOARD, createRoundScoreboard());
            getYamlConfiguration().set(GOONRUSH_GREEN_THEME_MIGRATED, true);
            saveConfig();
        }
        if (!getBoolean(GOONRUSH_RED_THEME_MIGRATED)) {
            getYamlConfiguration().set(SCOREBOARD_TITLE, "&c&lGOONRUSH");
            getYamlConfiguration().set(MAP_2X1_SCOREBOARD, createRoundScoreboard());
            getYamlConfiguration().set(MAP_4X1_SCOREBOARD, createRoundScoreboard());
            getYamlConfiguration().set(GOONRUSH_RED_THEME_MIGRATED, true);
            saveConfig();
        }
        if (!getBoolean(GOONRUSH_RED_ACTIONBAR_MIGRATED)) {
            getYamlConfiguration().set(ROUND_ACTIONBAR, createRoundActionbar());
            getYamlConfiguration().set(GOONRUSH_RED_ACTIONBAR_MIGRATED, true);
            saveConfig();
        }
    }

    @Override
    protected String filePath() {
        return Constants.SCOREBOARD_CONFIG_PATH;
    }

    @Override
    protected void configure(final @NotNull List<Pair<String, Object>> list) {
        list.add(new Pair<>(SCOREBOARD_TITLE, "&c&lGOONRUSH"));
        list.add(new Pair<>(ROUND_ACTIONBAR, createRoundActionbar()));
        list.add(new Pair<>(LOBBY_SCOREBOARD, new ArrayList<>(Arrays.asList(
                "&1",
                "&7Wins&8:",
                "&8» &e%stats_wins%",
                "&2",
                "&7Loses&8:",
                "&8» &e%stats_loses%",
                "&3",
                "&7Ranking&8:",
                "&8» &e#%stats_ranking%",
                "&4"
        ))));
        list.add(new Pair<>(MAP_2X1_SCOREBOARD, createRoundScoreboard()));
        list.add(new Pair<>(MAP_4X1_SCOREBOARD, createRoundScoreboard()));
        list.add(new Pair<>(GOONRUSH_SCOREBOARD_MIGRATED, false));
        list.add(new Pair<>(GOONRUSH_BLUE_THEME_MIGRATED, false));
        list.add(new Pair<>(GOONRUSH_GREEN_THEME_MIGRATED, false));
        list.add(new Pair<>(GOONRUSH_RED_THEME_MIGRATED, false));
        list.add(new Pair<>(GOONRUSH_RED_ACTIONBAR_MIGRATED, false));
    }

    private static String createRoundActionbar() {
        return "&c%player1% &8» &c%player1_score% &8| &c%player2% &8» &c%player2_score%";
    }

    private static ArrayList<String> createRoundScoreboard() {
        return new ArrayList<>(Arrays.asList(
                "&8&m----------------",
                "&7Map&8: &c%map_name%",
                "&1",
                "&7No Block Remover&8: %block_remover_off_status%",
                "&7Normal&8: %block_remover_normal_status%",
                "&7Death Reset&8: %block_remover_death_reset_status%",
                "&2",
                "&8&m----------------",
                "&f&oMLGRush-1"
        ));
    }

    @Override
    public String getString(final @NotNull Optional<Player> optionalPlayer, final @NotNull String path) {
        return placeholders.replace(optionalPlayer, getString(path));
    }
}
