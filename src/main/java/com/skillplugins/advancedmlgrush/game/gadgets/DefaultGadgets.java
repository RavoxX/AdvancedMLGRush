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
                        "&cUnlocked",
                        " "
                ),
                new Gadget(
                        "&8» &eBlaze Rod",
                        XMaterial.BLAZE_ROD.name(),
                        "gadgets.vip",
                        "&cUnlocked",
                        "&cRequires &6VIP"
                ),
                new Gadget(
                        "&8» &eFeather",
                        XMaterial.FEATHER.name(),
                        "gadgets.vip",
                        "&cUnlocked",
                        "&cRequires &6VIP"
                ),
                new Gadget(
                        "&8» &eArrow",
                        XMaterial.ARROW.name(),
                        "gadgets.mvp",
                        "&cUnlocked",
                        "&cRequires &bMVP"
                ),
                new Gadget(
                        "&8» &eShears",
                        XMaterial.SHEARS.name(),
                        "gadgets.mvp",
                        "&cUnlocked",
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
                        "&cUnlocked &8• &7Ancient and precise",
                        " "
                ),
                new Gadget(
                        "&8» &cSlime Scepter",
                        XMaterial.SLIME_BALL.name(),
                        "none",
                        "&cUnlocked &8• &7Bouncy knockback energy",
                        " "
                ),
                new Gadget(
                        "&8» &6Ember Shard",
                        XMaterial.BLAZE_POWDER.name(),
                        "none",
                        "&cUnlocked &8• &7Forged in blaze fire",
                        " "
                ),
                new Gadget(
                        "&8» &cMagma Core",
                        XMaterial.MAGMA_CREAM.name(),
                        "gadgets.vip",
                        "&cUnlocked &8• &7Hot to the touch",
                        "&cRequires &6VIP"
                ),
                new Gadget(
                        "&8» &fIron Baton",
                        XMaterial.IRON_INGOT.name(),
                        "gadgets.vip",
                        "&cUnlocked &8• &7Simple, heavy, effective",
                        "&cRequires &6VIP"
                ),
                new Gadget(
                        "&8» &6Golden Katana",
                        XMaterial.GOLDEN_SWORD.name(),
                        "gadgets.vip",
                        "&cUnlocked &8• &7A flashy golden edge",
                        "&cRequires &6VIP"
                ),
                new Gadget(
                        "&8» &3Prismarine Wand",
                        XMaterial.PRISMARINE_SHARD.name(),
                        "gadgets.mvp",
                        "&cUnlocked &8• &7Power from the deep sea",
                        "&cRequires &bMVP"
                ),
                new Gadget(
                        "&8» &bDiamond Scythe",
                        XMaterial.DIAMOND_HOE.name(),
                        "gadgets.mvp",
                        "&cUnlocked &8• &7Sharp and unmistakable",
                        "&cRequires &bMVP"
                ),
                new Gadget(
                        "&8» &fGhast Relic",
                        XMaterial.GHAST_TEAR.name(),
                        "gadgets.legend",
                        "&cUnlocked &8• &7A relic from the Nether",
                        "&cRequires &dLEGEND"
                ),
                new Gadget(
                        "&8» &dStar Scepter",
                        XMaterial.NETHER_STAR.name(),
                        "gadgets.legend",
                        "&cUnlocked &8• &7The rarest knockback perk",
                        "&cRequires &dLEGEND"
                ),
                new Gadget(
                        "&8» &fFrost Crystal",
                        XMaterial.PRISMARINE_CRYSTALS.name(),
                        "none",
                        "&cUnlocked &8• &7A shard of frozen precision",
                        " "
                ),
                new Gadget(
                        "&8» &dLucky Paw",
                        XMaterial.RABBIT_FOOT.name(),
                        "none",
                        "&cUnlocked &8• &7Fast, light and unexpectedly strong",
                        " "
                ),
                new Gadget(
                        "&8» &2Venom Fang",
                        XMaterial.FERMENTED_SPIDER_EYE.name(),
                        "none",
                        "&cUnlocked &8• &7A poisonous-looking dueling relic",
                        " "
                ),
                new Gadget(
                        "&8» &fQuartz Rapier",
                        XMaterial.QUARTZ.name(),
                        "gadgets.vip",
                        "&cUnlocked &8• &7Cut from pure Nether quartz",
                        "&cRequires &6VIP"
                ),
                new Gadget(
                        "&8» &8Shadow Flint",
                        XMaterial.FLINT.name(),
                        "gadgets.vip",
                        "&cUnlocked &8• &7Small, dark and razor sharp",
                        "&cRequires &6VIP"
                ),
                new Gadget(
                        "&8» &cRush Rod",
                        XMaterial.SUGAR.name(),
                        "gadgets.vip",
                        "&cUnlocked &8• &7Built for lightning-fast combos",
                        "&cRequires &6VIP"
                ),
                new Gadget(
                        "&8» &4Nether Thorn",
                        XMaterial.NETHER_WART.name(),
                        "gadgets.mvp",
                        "&cUnlocked &8• &7Grown in the hottest Nether fortress",
                        "&cRequires &bMVP"
                ),
                new Gadget(
                        "&8» &3Inkblade",
                        XMaterial.INK_SAC.name(),
                        "gadgets.mvp",
                        "&cUnlocked &8• &7Leaves only a dark blur behind",
                        "&cRequires &bMVP"
                ),
                new Gadget(
                        "&8» &6Gilded Reaper",
                        XMaterial.GOLDEN_HOE.name(),
                        "gadgets.legend",
                        "&cUnlocked &8• &7A ceremonial weapon of champions",
                        "&cRequires &dLEGEND"
                ),
                new Gadget(
                        "&8» &5Dragonbreaker",
                        XMaterial.DIAMOND_AXE.name(),
                        "gadgets.legend",
                        "&cUnlocked &8• &7Heavy enough to shake the arena",
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
                        "&cUnlocked",
                        "&cRequires &6VIP"
                ),
                new Gadget(
                        "&8» &eGold Block",
                        XMaterial.GOLD_BLOCK.name(),
                        "gadgets.vip",
                        "&cUnlocked",
                        "&cRequires &6VIP"
                ),
                new Gadget(
                        "&8» &eGlass",
                        XMaterial.GLASS.name(),
                        "gadgets.vip",
                        "&cUnlocked",
                        "&cRequires &6VIP"
                ),
                new Gadget(
                        "&8» &eCobblestone",
                        XMaterial.COBBLESTONE.name(),
                        "gadgets.mvp",
                        "&cUnlocked",
                        "&cRequires &bMVP"
                ),
                new Gadget(
                        "&8» &eNether Brick",
                        XMaterial.NETHER_BRICKS.name(),
                        "gadgets.mvp",
                        "&cUnlocked",
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
                        "&cUnlocked &8• &7Clean white bridges",
                        " "
                ),
                new Gadget(
                        "&8» &cRed Bricks",
                        XMaterial.BRICKS.name(),
                        "none",
                        "&cUnlocked &8• &7Classic brickwork",
                        " "
                ),
                new Gadget(
                        "&8» &eSunstone",
                        XMaterial.GLOWSTONE.name(),
                        "none",
                        "&cUnlocked &8• &7A bridge that glows",
                        " "
                ),
                new Gadget(
                        "&8» &bFrosted Ice",
                        XMaterial.PACKED_ICE.name(),
                        "gadgets.vip",
                        "&cUnlocked &8• &7Cold blue speed lines",
                        "&cRequires &6VIP"
                ),
                new Gadget(
                        "&8» &3Sea Prism",
                        XMaterial.PRISMARINE.name(),
                        "gadgets.vip",
                        "&cUnlocked &8• &7Ocean monument style",
                        "&cRequires &6VIP"
                ),
                new Gadget(
                        "&8» &5Void Obsidian",
                        XMaterial.OBSIDIAN.name(),
                        "gadgets.vip",
                        "&cUnlocked &8• &7Dark and indestructible-looking",
                        "&cRequires &6VIP"
                ),
                new Gadget(
                        "&8» &cRuby Block",
                        XMaterial.REDSTONE_BLOCK.name(),
                        "gadgets.mvp",
                        "&cUnlocked &8• &7Bright red bridges",
                        "&cRequires &bMVP"
                ),
                new Gadget(
                        "&8» &9Sapphire Block",
                        XMaterial.LAPIS_BLOCK.name(),
                        "gadgets.mvp",
                        "&cUnlocked &8• &7Deep blue bridges",
                        "&cRequires &bMVP"
                ),
                new Gadget(
                        "&8» &cEmerald Block",
                        XMaterial.EMERALD_BLOCK.name(),
                        "gadgets.legend",
                        "&cUnlocked &8• &7A rich green finish",
                        "&cRequires &dLEGEND"
                ),
                new Gadget(
                        "&8» &bDiamond Block",
                        XMaterial.DIAMOND_BLOCK.name(),
                        "gadgets.legend",
                        "&cUnlocked &8• &7The ultimate block perk",
                        "&cRequires &dLEGEND"
                ),
                new Gadget(
                        "&8» &7Castle Stone",
                        XMaterial.STONE_BRICKS.name(),
                        "none",
                        "&cUnlocked &8• &7Build bridges fit for a fortress",
                        " "
                ),
                new Gadget(
                        "&8» &2Moss Ruins",
                        XMaterial.MOSSY_COBBLESTONE.name(),
                        "none",
                        "&cUnlocked &8• &7Ancient overgrown bridgework",
                        " "
                ),
                new Gadget(
                        "&8» &4Nether Ore",
                        XMaterial.NETHER_QUARTZ_ORE.name(),
                        "none",
                        "&cUnlocked &8• &7Raw crystals straight from the Nether",
                        " "
                ),
                new Gadget(
                        "&8» &8Blacksteel",
                        XMaterial.COAL_BLOCK.name(),
                        "gadgets.vip",
                        "&cUnlocked &8• &7A sleek pitch-black bridge",
                        "&cRequires &6VIP"
                ),
                new Gadget(
                        "&8» &fTitan Iron",
                        XMaterial.IRON_BLOCK.name(),
                        "gadgets.vip",
                        "&cUnlocked &8• &7Industrial strength with a clean finish",
                        "&cRequires &6VIP"
                ),
                new Gadget(
                        "&8» &6Harvest Bale",
                        XMaterial.HAY_BLOCK.name(),
                        "gadgets.vip",
                        "&cUnlocked &8• &7Warm golden lanes across the void",
                        "&cRequires &6VIP"
                ),
                new Gadget(
                        "&8» &2Melon Rush",
                        XMaterial.MELON.name(),
                        "gadgets.mvp",
                        "&cUnlocked &8• &7Bright stripes for fearless rushers",
                        "&cRequires &bMVP"
                ),
                new Gadget(
                        "&8» &eAncient Sponge",
                        XMaterial.SPONGE.name(),
                        "gadgets.mvp",
                        "&cUnlocked &8• &7A rare monument treasure",
                        "&cRequires &bMVP"
                ),
                new Gadget(
                        "&8» &bMoon Lantern",
                        XMaterial.SEA_LANTERN.name(),
                        "gadgets.legend",
                        "&cUnlocked &8• &7Turns every bridge into a light trail",
                        "&cRequires &dLEGEND"
                ),
                new Gadget(
                        "&8» &dEnder Stone",
                        XMaterial.END_STONE.name(),
                        "gadgets.legend",
                        "&cUnlocked &8• &7Forged at the edge of the End",
                        "&cRequires &dLEGEND"
                )
        ));
    }

}
