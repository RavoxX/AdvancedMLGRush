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
        String normalized = input.replace("\u00c2", "")
                .replaceAll("(?i)&[a16eb]", "&c")
                .replaceAll("(?i)\u00a7[a16eb]", "\u00a7c");

        // Decorative symbols always use dark gray, independent of the configured
        // color immediately before them.
        normalized = normalized.replaceAll(
                "(?i)(?:&[0-9a-fk-or]|\u00a7[0-9a-fk-or])+(?=[»«|•])", "&8");
        normalized = normalized.replaceAll(
                "(?i)(?<!&8)(?<!\u00a78)(?=[»«|•])", "&8");

        // Ranking hashes are gray while the following value returns to the
        // red theme color.
        normalized = normalized.replaceAll(
                "(?i)(?:&[0-9a-fk-or]|\u00a7[0-9a-fk-or])+(?=#)", "&8");
        normalized = normalized.replaceAll(
                "(?i)(?<!&8)(?<!\u00a78)(?=#)", "&8");
        normalized = normalized.replace("#", "#&c");

        // Plus/minus navigation buttons consist only of the symbol itself.
        if (normalized.matches("(?i)(?:&[0-9a-fk-or]|\u00a7[0-9a-fk-or])*[+-]")) {
            normalized = "&8" + normalized.substring(normalized.length() - 1);
        }

        return ChatColor.translateAlternateColorCodes('&', normalized);
    }
}
