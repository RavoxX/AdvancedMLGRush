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

public enum BlockRemover {

    ROUND_RESET("Round Reset"),
    DEATH_RESET("Death Reset");

    private final String configName;

    BlockRemover(final String configName) {
        this.configName = configName;
    }

    public static BlockRemover fromConfig(final String value) {
        if (value != null) {
            final String normalizedValue = value.trim().replace('_', ' ').replace('-', ' ');
            for (final BlockRemover blockRemover : values()) {
                if (blockRemover.configName.equalsIgnoreCase(normalizedValue)) {
                    return blockRemover;
                }
            }
        }
        return ROUND_RESET;
    }
}
