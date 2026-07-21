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

package com.skillplugins.advancedmlgrush.game.map;

import com.google.common.collect.BiMap;
import com.google.inject.assistedinject.Assisted;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface MapInstanceFactory {

    MapInstance create(final @NotNull MapTemplate mapTemplate,
                       final @NotNull MapData mapData,
                       final @NotNull BiMap<Player, Integer> players,
                       final @Assisted("rounds") int rounds,
                       final @NotNull BlockRemover blockRemover,
                       final @Assisted("attackRange") int attackRange);

}
