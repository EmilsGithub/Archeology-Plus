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
        addDrop(ModBlocks.SANDSTONE_HIEROGLYPHS);
    }
}
