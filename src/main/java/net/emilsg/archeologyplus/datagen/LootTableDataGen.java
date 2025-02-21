package net.emilsg.archeologyplus.datagen;

import net.emilsg.archeologyplus.register.blocks.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

public class LootTableDataGen extends FabricBlockLootTableProvider {

    public LootTableDataGen(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.CRUMBLING_SANDSTONE);
        addDrop(ModBlocks.CRUMBLING_RED_SANDSTONE);
        addDrop(ModBlocks.CRUMBLING_STONE_BRICKS);
        addDrop(ModBlocks.CRUMBLING_MOSSY_STONE_BRICKS);
        addDrop(ModBlocks.RED_SANDSTONE_HIEROGLYPHS);
        addDrop(ModBlocks.SANDSTONE_HIEROGLYPHS);
        addDrop(ModBlocks.STONE_BRICK_WRITINGS);
        addDrop(ModBlocks.MOSSY_STONE_BRICK_WRITINGS);
        addDrop(ModBlocks.ROPE);
        addDrop(ModBlocks.SPIKE_TRAP);
        addDrop(ModBlocks.ARCHEOLOGY_TABLE);
    }
}
