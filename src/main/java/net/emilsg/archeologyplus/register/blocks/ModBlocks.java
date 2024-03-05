package net.emilsg.archeologyplus.register.blocks;

import net.emilsg.archeologyplus.ArcheologyPlus;
import net.emilsg.archeologyplus.register.blocks.custom.CrumblingBlock;
import net.emilsg.archeologyplus.register.blocks.custom.HieroglyphBlock;
import net.emilsg.archeologyplus.register.blocks.custom.LootPotBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.BrushableBlock;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block SUSPICIOUS_SOUL_SAND = registerBlock("suspicious_soul_sand", new BrushableBlock(Blocks.SOUL_SAND, FabricBlockSettings.copy(Blocks.SOUL_SAND).sounds(BlockSoundGroup.SUSPICIOUS_SAND).pistonBehavior(PistonBehavior.DESTROY), SoundEvents.ITEM_BRUSH_BRUSHING_SAND, SoundEvents.ITEM_BRUSH_BRUSHING_SAND_COMPLETE));
    public static final Block SUSPICIOUS_RED_SAND = registerBlock("suspicious_red_sand", new BrushableBlock(Blocks.RED_SAND, FabricBlockSettings.copy(Blocks.RED_SAND).sounds(BlockSoundGroup.SUSPICIOUS_SAND).pistonBehavior(PistonBehavior.DESTROY), SoundEvents.ITEM_BRUSH_BRUSHING_SAND, SoundEvents.ITEM_BRUSH_BRUSHING_SAND_COMPLETE));
    public static final Block SUSPICIOUS_DIRT = registerBlock("suspicious_dirt", new BrushableBlock(Blocks.DIRT, FabricBlockSettings.copy(Blocks.DIRT).sounds(BlockSoundGroup.SUSPICIOUS_GRAVEL).pistonBehavior(PistonBehavior.DESTROY), SoundEvents.ITEM_BRUSH_BRUSHING_GRAVEL, SoundEvents.ITEM_BRUSH_BRUSHING_GRAVEL_COMPLETE));

    public static final Block LOOT_POT = registerBlock("loot_pot", new LootPotBlock(FabricBlockSettings.copy(Blocks.DECORATED_POT).sounds(BlockSoundGroup.DECORATED_POT_SHATTER).breakInstantly()));

    public static final Block CRUMBLING_SANDSTONE = registerBlock("crumbling_sandstone", new CrumblingBlock(FabricBlockSettings.copy(Blocks.SANDSTONE).strength(0.6f)));
    public static final Block CRUMBLING_RED_SANDSTONE = registerBlock("crumbling_red_sandstone", new CrumblingBlock(FabricBlockSettings.copy(Blocks.RED_SANDSTONE).strength(0.6f)));

    public static final Block SANDSTONE_HIEROGLYPHS = registerBlock("sandstone_hieroglyphs", new HieroglyphBlock(FabricBlockSettings.copy(Blocks.SMOOTH_SANDSTONE)));
    public static final Block RED_SANDSTONE_HIEROGLYPHS = registerBlock("red_sandstone_hieroglyphs", new HieroglyphBlock(FabricBlockSettings.copy(Blocks.SMOOTH_RED_SANDSTONE)));

    public static final Block STONE_BRICK_WRITINGS = registerBlock("stone_brick_writings", new HieroglyphBlock(FabricBlockSettings.copy(Blocks.STONE_BRICKS)));
    public static final Block MOSSY_STONE_BRICK_WRITINGS = registerBlock("mossy_stone_brick_writings", new HieroglyphBlock(FabricBlockSettings.copy(Blocks.MOSSY_STONE_BRICKS)));


    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(ArcheologyPlus.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(ArcheologyPlus.MOD_ID, name), new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
    }
}
