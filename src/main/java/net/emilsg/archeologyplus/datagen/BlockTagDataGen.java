package net.emilsg.archeologyplus.datagen;

import net.emilsg.archeologyplus.register.blocks.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class BlockTagDataGen extends FabricTagProvider.BlockTagProvider {

    public BlockTagDataGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE)
                .add(ModBlocks.SUSPICIOUS_SOUL_SAND);

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(
                    ModBlocks.CRUMBLING_SANDSTONE, ModBlocks.CRUMBLING_RED_SANDSTONE,
                    ModBlocks.SANDSTONE_HIEROGLYPHS, ModBlocks.RED_SANDSTONE_HIEROGLYPHS,
                    ModBlocks.STONE_BRICK_WRITINGS
                );

        getOrCreateTagBuilder(BlockTags.SOUL_FIRE_BASE_BLOCKS)
                .add(ModBlocks.SUSPICIOUS_SOUL_SAND);

        getOrCreateTagBuilder(BlockTags.SOUL_SPEED_BLOCKS)
                .add(ModBlocks.SUSPICIOUS_SOUL_SAND);
    }
}
