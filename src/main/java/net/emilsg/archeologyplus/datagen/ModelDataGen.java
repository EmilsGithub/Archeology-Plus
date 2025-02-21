package net.emilsg.archeologyplus.datagen;

import net.emilsg.archeologyplus.ArcheologyPlus;
import net.emilsg.archeologyplus.register.blocks.ModBlocks;
import net.emilsg.archeologyplus.register.items.ModItems;
import net.emilsg.archeologyplus.util.ModProperties;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.*;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;

import java.util.Collections;

public class ModelDataGen extends FabricModelProvider {
    public ModelDataGen(FabricDataOutput output) {
        super(output);
    }

    public static BlockStateSupplier createBooleanState(Block block, BooleanProperty property, Identifier isTrueModel, Identifier isFalseModel) {
        return VariantsBlockStateSupplier.create(block)
                .coordinate(BlockStateVariantMap.create(property)
                        .register(true, BlockStateVariant.create().put(VariantSettings.MODEL, isTrueModel))
                        .register(false, BlockStateVariant.create().put(VariantSettings.MODEL, isFalseModel))
                );
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerBrushableBlock(ModBlocks.SUSPICIOUS_SOUL_SAND);
        blockStateModelGenerator.registerBrushableBlock(ModBlocks.SUSPICIOUS_RED_SAND);
        blockStateModelGenerator.registerBrushableBlock(ModBlocks.SUSPICIOUS_DIRT);

        registerIntPropertyCubeAllBlock(blockStateModelGenerator, ModBlocks.CRUMBLING_SANDSTONE, ModProperties.CRUMBLE_LEVEL);
        registerIntPropertyCubeAllBlock(blockStateModelGenerator, ModBlocks.CRUMBLING_RED_SANDSTONE, ModProperties.CRUMBLE_LEVEL);
        registerIntPropertyCubeAllBlock(blockStateModelGenerator, ModBlocks.CRUMBLING_STONE_BRICKS, ModProperties.CRUMBLE_LEVEL);
        registerIntPropertyCubeAllBlock(blockStateModelGenerator, ModBlocks.CRUMBLING_MOSSY_STONE_BRICKS, ModProperties.CRUMBLE_LEVEL);

        registerIntPropertyCubeColumnBlock(blockStateModelGenerator, ModBlocks.SANDSTONE_HIEROGLYPHS, ModProperties.VARIANT_3);
        registerIntPropertyCubeColumnBlock(blockStateModelGenerator, ModBlocks.RED_SANDSTONE_HIEROGLYPHS, ModProperties.VARIANT_3);
        registerIntPropertyCubeColumnBlock(blockStateModelGenerator, ModBlocks.STONE_BRICK_WRITINGS, ModProperties.VARIANT_3);
        registerIntPropertyCubeColumnBlock(blockStateModelGenerator, ModBlocks.MOSSY_STONE_BRICK_WRITINGS, ModProperties.VARIANT_3);

        registerBooleanPropertyBlock(blockStateModelGenerator, ModBlocks.STONE_STEP_BREAK_BLOCK, ModProperties.WILL_BREAK);
        registerBooleanPropertyBlock(blockStateModelGenerator, ModBlocks.MOSSY_STONE_STEP_BREAK_BLOCK, ModProperties.WILL_BREAK);

        registerFakeBlock(blockStateModelGenerator, ModBlocks.FAKE_BLOCK, Blocks.STONE);
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
        itemModelGenerator.register(ModItems.IDOL_OF_PROTECTION, Models.GENERATED);

        itemModelGenerator.register(ModItems.ROPE_BUNDLE, Models.GENERATED);
        itemModelGenerator.register(ModBlocks.ROPE.asItem(), Models.GENERATED);

        itemModelGenerator.register(ModItems.CHISEL, Models.HANDHELD);
    }

    public final void registerFakeBlock(BlockStateModelGenerator generator, Block block, Block reference) {
        Identifier isTrue = generator.createSubModel(block, "", Models.CUBE_ALL, id -> referenceTextureMap(reference));
        Identifier isFalse = Identifier.of(ArcheologyPlus.MOD_ID,"block/fake_block_hidden");
        generator.blockStateCollector.accept(createBooleanState(block, ModProperties.HIDDEN, isFalse, isTrue));
        generator.registerParentedItemModel(block, isFalse);
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

    public final void registerBooleanPropertyBlock(BlockStateModelGenerator generator, Block block, BooleanProperty property) {
        Identifier isTrue = generator.createSubModel(block, "_true", Models.CUBE_ALL, TextureMap::all);
        Identifier isFalse = generator.createSubModel(block, "_false", Models.CUBE_ALL, TextureMap::all);
        generator.blockStateCollector.accept(createBooleanState(block, property, isTrue, isFalse));
        generator.registerParentedItemModel(block, isTrue);
    }

    public static TextureMap referenceTextureMap(Block reference) {
        Identifier planks = TextureMap.getId(reference);
        return new TextureMap().put(TextureKey.ALL, planks);
    }
}
