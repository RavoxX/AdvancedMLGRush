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

package com.skillplugins.advancedmlgrush.game.gadgets;

import com.skillplugins.advancedmlgrush.lib.xseries.XMaterial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultGadgets {

    private DefaultGadgets() {
    }

    public static List<Gadget> getDefaultSticks() {
        final List<Gadget> gadgets = new ArrayList<>(Arrays.asList(
                new Gadget(
                        "&8» &eStick",
                        XMaterial.STICK.name(),
                        "none",
                        "&aUnlocked",
                        " "
                ),
                new Gadget(
                        "&8» &eBlaze Rod",
                        XMaterial.BLAZE_ROD.name(),
                        "gadgets.vip",
                        "&aUnlocked",
                        "&cRequires &6VIP"
                ),
                new Gadget(
                        "&8» &eFeather",
                        XMaterial.FEATHER.name(),
                        "gadgets.vip",
                        "&aUnlocked",
                        "&cRequires &6VIP"
                ),
                new Gadget(
                        "&8» &eArrow",
                        XMaterial.ARROW.name(),
                        "gadgets.mvp",
                        "&aUnlocked",
                        "&cRequires &bMVP"
                ),
                new Gadget(
                        "&8» &eShears",
                        XMaterial.SHEARS.name(),
                        "gadgets.mvp",
                        "&aUnlocked",
                        "&cRequires &bMVP"
                )
        ));
        gadgets.addAll(getAdditionalSticks());
        return gadgets;
    }

    public static List<Gadget> getAdditionalSticks() {
        return new ArrayList<>(Arrays.asList(
                new Gadget(
                        "&8» &fBone Wand",
                        XMaterial.BONE.name(),
                        "none",
                        "&aUnlocked &8• &7Ancient and precise",
                        " "
                ),
                new Gadget(
                        "&8» &aSlime Scepter",
                        XMaterial.SLIME_BALL.name(),
                        "none",
                        "&aUnlocked &8• &7Bouncy knockback energy",
                        " "
                ),
                new Gadget(
                        "&8» &6Ember Shard",
                        XMaterial.BLAZE_POWDER.name(),
                        "none",
                        "&aUnlocked &8• &7Forged in blaze fire",
                        " "
                ),
                new Gadget(
                        "&8» &cMagma Core",
                        XMaterial.MAGMA_CREAM.name(),
                        "gadgets.vip",
                        "&aUnlocked &8• &7Hot to the touch",
                        "&cRequires &6VIP"
                ),
                new Gadget(
                        "&8» &fIron Baton",
                        XMaterial.IRON_INGOT.name(),
                        "gadgets.vip",
                        "&aUnlocked &8• &7Simple, heavy, effective",
                        "&cRequires &6VIP"
                ),
                new Gadget(
                        "&8» &6Golden Katana",
                        XMaterial.GOLDEN_SWORD.name(),
                        "gadgets.vip",
                        "&aUnlocked &8• &7A flashy golden edge",
                        "&cRequires &6VIP"
                ),
                new Gadget(
                        "&8» &3Prismarine Wand",
                        XMaterial.PRISMARINE_SHARD.name(),
                        "gadgets.mvp",
                        "&aUnlocked &8• &7Power from the deep sea",
                        "&cRequires &bMVP"
                ),
                new Gadget(
                        "&8» &bDiamond Scythe",
                        XMaterial.DIAMOND_HOE.name(),
                        "gadgets.mvp",
                        "&aUnlocked &8• &7Sharp and unmistakable",
                        "&cRequires &bMVP"
                ),
                new Gadget(
                        "&8» &fGhast Relic",
                        XMaterial.GHAST_TEAR.name(),
                        "gadgets.legend",
                        "&aUnlocked &8• &7A relic from the Nether",
                        "&cRequires &dLEGEND"
                ),
                new Gadget(
                        "&8» &dStar Scepter",
                        XMaterial.NETHER_STAR.name(),
                        "gadgets.legend",
                        "&aUnlocked &8• &7The rarest knockback perk",
                        "&cRequires &dLEGEND"
                )
        ));
    }

    public static List<Gadget> getDefaultBlocks() {
        final List<Gadget> gadgets = new ArrayList<>(Arrays.asList(
                new Gadget(
                        "&8» &eSandstone",
                        XMaterial.SANDSTONE.name(),
                        "none",
                        "&aUnlocked",
                        "&cRequires &6VIP"
                ),
                new Gadget(
                        "&8» &eGold Block",
                        XMaterial.GOLD_BLOCK.name(),
                        "gadgets.vip",
                        "&aUnlocked",
                        "&cRequires &6VIP"
                ),
                new Gadget(
                        "&8» &eGlass",
                        XMaterial.GLASS.name(),
                        "gadgets.vip",
                        "&aUnlocked",
                        "&cRequires &6VIP"
                ),
                new Gadget(
                        "&8» &eCobblestone",
                        XMaterial.COBBLESTONE.name(),
                        "gadgets.mvp",
                        "&aUnlocked",
                        "&cRequires &bMVP"
                ),
                new Gadget(
                        "&8» &eNether Brick",
                        XMaterial.NETHER_BRICKS.name(),
                        "gadgets.mvp",
                        "&aUnlocked",
                        "&cRequires &bMVP"
                )
        ));
        gadgets.addAll(getAdditionalBlocks());
        return gadgets;
    }

    public static List<Gadget> getAdditionalBlocks() {
        return new ArrayList<>(Arrays.asList(
                new Gadget(
                        "&8» &fMarble",
                        XMaterial.QUARTZ_BLOCK.name(),
                        "none",
                        "&aUnlocked &8• &7Clean white bridges",
                        " "
                ),
                new Gadget(
                        "&8» &cRed Bricks",
                        XMaterial.BRICKS.name(),
                        "none",
                        "&aUnlocked &8• &7Classic brickwork",
                        " "
                ),
                new Gadget(
                        "&8» &eSunstone",
                        XMaterial.GLOWSTONE.name(),
                        "none",
                        "&aUnlocked &8• &7A bridge that glows",
                        " "
                ),
                new Gadget(
                        "&8» &bFrosted Ice",
                        XMaterial.PACKED_ICE.name(),
                        "gadgets.vip",
                        "&aUnlocked &8• &7Cold blue speed lines",
                        "&cRequires &6VIP"
                ),
                new Gadget(
                        "&8» &3Sea Prism",
                        XMaterial.PRISMARINE.name(),
                        "gadgets.vip",
                        "&aUnlocked &8• &7Ocean monument style",
                        "&cRequires &6VIP"
                ),
                new Gadget(
                        "&8» &5Void Obsidian",
                        XMaterial.OBSIDIAN.name(),
                        "gadgets.vip",
                        "&aUnlocked &8• &7Dark and indestructible-looking",
                        "&cRequires &6VIP"
                ),
                new Gadget(
                        "&8» &cRuby Block",
                        XMaterial.REDSTONE_BLOCK.name(),
                        "gadgets.mvp",
                        "&aUnlocked &8• &7Bright red bridges",
                        "&cRequires &bMVP"
                ),
                new Gadget(
                        "&8» &9Sapphire Block",
                        XMaterial.LAPIS_BLOCK.name(),
                        "gadgets.mvp",
                        "&aUnlocked &8• &7Deep blue bridges",
                        "&cRequires &bMVP"
                ),
                new Gadget(
                        "&8» &aEmerald Block",
                        XMaterial.EMERALD_BLOCK.name(),
                        "gadgets.legend",
                        "&aUnlocked &8• &7A rich green finish",
                        "&cRequires &dLEGEND"
                ),
                new Gadget(
                        "&8» &bDiamond Block",
                        XMaterial.DIAMOND_BLOCK.name(),
                        "gadgets.legend",
                        "&aUnlocked &8• &7The ultimate block perk",
                        "&cRequires &dLEGEND"
                )
        ));
    }

}
