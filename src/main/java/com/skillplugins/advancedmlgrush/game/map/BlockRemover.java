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

    OFF(0, "Off"),
    NORMAL(1, "Normal"),
    DEATH_RESET(2, "Death Reset");

    private final int id;
    private final String displayName;

    BlockRemover(final int id, final String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public int getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public BlockRemover next() {
        final BlockRemover[] modes = values();
        return modes[(ordinal() + 1) % modes.length];
    }

    public static BlockRemover fromId(final int id) {
        for (final BlockRemover blockRemover : values()) {
            if (blockRemover.id == id) {
                return blockRemover;
            }
        }
        return NORMAL;
    }
}
