package net.emilsg.archeologyplus.datagen;

import net.emilsg.archeologyplus.register.blocks.ModBlocks;
import net.emilsg.archeologyplus.register.items.ModItems;
import net.emilsg.archeologyplus.util.ModProperties;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;

import java.util.Collections;

public class ModelDataGen extends FabricModelProvider {
    public ModelDataGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerBrushableBlock(ModBlocks.SUSPICIOUS_SOUL_SAND);
        blockStateModelGenerator.registerBrushableBlock(ModBlocks.SUSPICIOUS_RED_SAND);
        blockStateModelGenerator.registerBrushableBlock(ModBlocks.SUSPICIOUS_DIRT);

        registerIntPropertyCubeAllBlock(blockStateModelGenerator, ModBlocks.CRUMBLING_SANDSTONE, ModProperties.CRUMBLE_LEVEL);
        registerIntPropertyCubeAllBlock(blockStateModelGenerator, ModBlocks.CRUMBLING_RED_SANDSTONE, ModProperties.CRUMBLE_LEVEL);

        registerIntPropertyCubeColumnBlock(blockStateModelGenerator, ModBlocks.SANDSTONE_HIEROGLYPHS, ModProperties.VARIANT_3);
        registerIntPropertyCubeColumnBlock(blockStateModelGenerator, ModBlocks.RED_SANDSTONE_HIEROGLYPHS, ModProperties.VARIANT_3);
        registerIntPropertyCubeColumnBlock(blockStateModelGenerator, ModBlocks.STONE_BRICK_WRITINGS, ModProperties.VARIANT_3);
        registerIntPropertyCubeColumnBlock(blockStateModelGenerator, ModBlocks.MOSSY_STONE_BRICK_WRITINGS, ModProperties.VARIANT_3);

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.LOADER_POTTERY_SHERD, Models.GENERATED);
        itemModelGenerator.register(ModItems.MASTER_POTTERY_SHERD, Models.GENERATED);
        itemModelGenerator.register(ModItems.MERCHANT_POTTERY_SHERD, Models.GENERATED);
        itemModelGenerator.register(ModItems.HOP_POTTERY_SHERD, Models.GENERATED);
        itemModelGenerator.register(ModItems.SIGHT_POTTERY_SHERD, Models.GENERATED);
        itemModelGenerator.register(ModItems.NIGHT_POTTERY_SHERD, Models.GENERATED);
        itemModelGenerator.register(ModItems.MIGHT_POTTERY_SHERD, Models.GENERATED);
        itemModelGenerator.register(ModItems.FRIGHT_POTTERY_SHERD, Models.GENERATED);
        itemModelGenerator.register(ModItems.LIGHT_POTTERY_SHERD, Models.GENERATED);
        itemModelGenerator.register(ModItems.FLIGHT_POTTERY_SHERD, Models.GENERATED);
        itemModelGenerator.register(ModItems.CHOMP_POTTERY_SHERD, Models.GENERATED);
        itemModelGenerator.register(ModItems.REVIVE_POTTERY_SHERD, Models.GENERATED);
        itemModelGenerator.register(ModItems.BUTTERFLY_POTTERY_SHERD, Models.GENERATED);
        itemModelGenerator.register(ModItems.NAUTILUS_POTTERY_SHERD, Models.GENERATED);
        itemModelGenerator.register(ModItems.HALO_POTTERY_SHERD, Models.GENERATED);
        itemModelGenerator.register(ModItems.DEVIL_POTTERY_SHERD, Models.GENERATED);

        itemModelGenerator.register(ModItems.SUN_IDOL, Models.GENERATED);
        itemModelGenerator.register(ModItems.MOON_IDOL, Models.GENERATED);
        itemModelGenerator.register(ModItems.SEASHELL_IDOL, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAIN_IDOL, Models.GENERATED);
        itemModelGenerator.register(ModItems.FIRE_IDOL, Models.GENERATED);
        itemModelGenerator.register(ModItems.HARVEST_IDOL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.GLIDING_IDOL, Models.GENERATED);
        itemModelGenerator.register(ModItems.WITHER_IDOL, Models.GENERATED);

        itemModelGenerator.register(ModItems.CHISEL, Models.HANDHELD);
    }

    public final void registerIntPropertyCubeAllBlock(BlockStateModelGenerator blockStateModelGenerator, Block block, IntProperty property) {
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(BlockStateVariantMap.create(property).register((intProperty) -> {
            String string = "_" + intProperty;
            Identifier identifier = TextureMap.getSubId(block, string);
            return BlockStateVariant.create().put(VariantSettings.MODEL, Models.CUBE_ALL.upload(block, string, (new TextureMap()).put(TextureKey.ALL, identifier), blockStateModelGenerator.modelCollector));
        })));
        blockStateModelGenerator.registerParentedItemModel(block, TextureMap.getSubId(block, "_" + Collections.max(property.getValues())));
    }

    public final void registerIntPropertyCubeColumnBlock(BlockStateModelGenerator blockStateModelGenerator, Block block, IntProperty property) {
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(BlockStateVariantMap.create(property).register((intProperty) -> {
            String endString = "_end_" + intProperty;
            String sideString = "_" + intProperty;
            Identifier endIdentifier = TextureMap.getSubId(block, endString);
            Identifier sideIdentifier = TextureMap.getSubId(block, sideString);
            return BlockStateVariant.create().put(VariantSettings.MODEL, Models.CUBE_COLUMN.upload(block, sideString, (new TextureMap()).put(TextureKey.END, endIdentifier).put(TextureKey.SIDE, sideIdentifier), blockStateModelGenerator.modelCollector));
        })));
        blockStateModelGenerator.registerParentedItemModel(block, TextureMap.getSubId(block, "_" + Collections.max(property.getValues())));
    }

}
