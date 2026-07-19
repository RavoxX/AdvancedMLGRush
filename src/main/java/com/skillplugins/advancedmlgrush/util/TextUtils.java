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

package com.skillplugins.advancedmlgrush.util;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public final class TextUtils {

    private TextUtils() {
    }

    public static String colorize(final @NotNull String input) {
        // Older builds compiled UTF-8 source as Windows-1252 and could persist
        // an extra \u00c2 before section signs and arrow characters in YAML files.
        final String normalized = input.replace("\u00c2", "");
        return ChatColor.translateAlternateColorCodes('&', normalized);
    }
}
