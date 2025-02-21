package net.emilsg.archeologyplus.register.blocks;

import net.emilsg.archeologyplus.ArcheologyPlus;
import net.emilsg.archeologyplus.register.blocks.custom.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block ROPE = registerBlock("rope", new RopeBlock(FabricBlockSettings.copy(Blocks.BROWN_WOOL).nonOpaque().breakInstantly().pistonBehavior(PistonBehavior.DESTROY)));

    public static final Block SUSPICIOUS_SOUL_SAND = registerBlock("suspicious_soul_sand", new BrushableBlock(Blocks.SOUL_SAND, FabricBlockSettings.copy(Blocks.SOUL_SAND).sounds(BlockSoundGroup.SUSPICIOUS_SAND).pistonBehavior(PistonBehavior.DESTROY), SoundEvents.ITEM_BRUSH_BRUSHING_SAND, SoundEvents.ITEM_BRUSH_BRUSHING_SAND_COMPLETE));
    public static final Block SUSPICIOUS_RED_SAND = registerBlock("suspicious_red_sand", new BrushableBlock(Blocks.RED_SAND, FabricBlockSettings.copy(Blocks.RED_SAND).sounds(BlockSoundGroup.SUSPICIOUS_SAND).pistonBehavior(PistonBehavior.DESTROY), SoundEvents.ITEM_BRUSH_BRUSHING_SAND, SoundEvents.ITEM_BRUSH_BRUSHING_SAND_COMPLETE));
    public static final Block SUSPICIOUS_DIRT = registerBlock("suspicious_dirt", new BrushableBlock(Blocks.DIRT, FabricBlockSettings.copy(Blocks.DIRT).sounds(BlockSoundGroup.SUSPICIOUS_GRAVEL).pistonBehavior(PistonBehavior.DESTROY), SoundEvents.ITEM_BRUSH_BRUSHING_GRAVEL, SoundEvents.ITEM_BRUSH_BRUSHING_GRAVEL_COMPLETE));

    public static final Block LOOT_POT = registerBlock("loot_pot", new SmallLootPotBlock(FabricBlockSettings.copy(Blocks.DECORATED_POT).dynamicBounds().sounds(BlockSoundGroup.DECORATED_POT_SHATTER).offset(AbstractBlock.OffsetType.XZ).breakInstantly()));
    public static final Block MEDIUM_LOOT_POT = registerBlock("medium_loot_pot", new MediumLootPotBlock(FabricBlockSettings.copy(Blocks.DECORATED_POT).dynamicBounds().sounds(BlockSoundGroup.DECORATED_POT_SHATTER).offset(AbstractBlock.OffsetType.XZ).breakInstantly()));

    public static final Block CRUMBLING_SANDSTONE = registerBlock("crumbling_sandstone", new CrumblingBlock(FabricBlockSettings.copy(Blocks.SANDSTONE).strength(0.5f)));
    public static final Block CRUMBLING_RED_SANDSTONE = registerBlock("crumbling_red_sandstone", new CrumblingBlock(FabricBlockSettings.copy(Blocks.RED_SANDSTONE).strength(0.5f)));

    public static final Block SANDSTONE_HIEROGLYPHS = registerBlock("sandstone_hieroglyphs", new HieroglyphBlock(FabricBlockSettings.copy(Blocks.SMOOTH_SANDSTONE)));
    public static final Block RED_SANDSTONE_HIEROGLYPHS = registerBlock("red_sandstone_hieroglyphs", new HieroglyphBlock(FabricBlockSettings.copy(Blocks.SMOOTH_RED_SANDSTONE)));

    public static final Block CRUMBLING_STONE_BRICKS = registerBlock("crumbling_stone_bricks", new CrumblingBlock(FabricBlockSettings.copy(Blocks.STONE_BRICKS).strength(0.6f)));
    public static final Block CRUMBLING_MOSSY_STONE_BRICKS = registerBlock("crumbling_mossy_stone_bricks", new CrumblingBlock(FabricBlockSettings.copy(Blocks.MOSSY_STONE_BRICKS).strength(0.6f)));

    public static final Block STONE_BRICK_WRITINGS = registerBlock("stone_brick_writings", new HieroglyphBlock(FabricBlockSettings.copy(Blocks.STONE_BRICKS)));
    public static final Block MOSSY_STONE_BRICK_WRITINGS = registerBlock("mossy_stone_brick_writings", new HieroglyphBlock(FabricBlockSettings.copy(Blocks.MOSSY_STONE_BRICKS)));

    public static final Block STONE_STEP_BREAK_BLOCK = registerBlock("stone_step_break_block", new StepBreakBlock(FabricBlockSettings.copy(Blocks.STONE_BRICKS)));
    public static final Block MOSSY_STONE_STEP_BREAK_BLOCK = registerBlock("mossy_stone_step_break_block", new StepBreakBlock(FabricBlockSettings.copy(Blocks.MOSSY_STONE_BRICKS)));

    public static final Block SPIKE_TRAP = registerBlock("spike_trap", new SpikeTrapBlock(FabricBlockSettings.copy(Blocks.DISPENSER)));
    public static final Block SPIKES = registerBlockWithoutItem("spikes", new SpikeBlock(FabricBlockSettings.create().mapColor(MapColor.BROWN).noCollision().noBlockBreakParticles().strength(1000, 1000)));

    public static final Block ARCHEOLOGY_TABLE = registerBlock("archeology_table", new ArcheologyTableBlock(FabricBlockSettings.copy(Blocks.STONE).nonOpaque()));

    public static final Block FAKE_BLOCK = registerBlock("fake_block", new FakeBlockBlock(FabricBlockSettings.copy(Blocks.STONE).nonOpaque()));


    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(ArcheologyPlus.MOD_ID, name), block);
    }

    private static Block registerBlockWithoutItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(ArcheologyPlus.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(ArcheologyPlus.MOD_ID, name), new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
    }
}
