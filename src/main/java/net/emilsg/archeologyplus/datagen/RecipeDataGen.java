package net.emilsg.archeologyplus.datagen;

import net.emilsg.archeologyplus.ArcheologyPlus;
import net.emilsg.archeologyplus.register.blocks.ModBlocks;
import net.emilsg.archeologyplus.register.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class RecipeDataGen extends FabricRecipeProvider {
    public RecipeDataGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.LOOT_POT)
                .pattern(" B ")
                .pattern(" B ")
                .input('B', Items.BRICK)
                .criterion(FabricRecipeProvider.hasItem(Items.BRICK), FabricRecipeProvider.conditionsFromItem(Items.BRICK))
                .offerTo(exporter, new Identifier(ArcheologyPlus.MOD_ID, FabricRecipeProvider.getRecipeName(ModBlocks.LOOT_POT)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.CHISEL)
                .pattern("  N")
                .pattern(" I ")
                .pattern("S  ")
                .input('N', Items.IRON_NUGGET)
                .input('I', Items.IRON_INGOT)
                .input('S', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(Items.IRON_INGOT), FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, new Identifier(ArcheologyPlus.MOD_ID, FabricRecipeProvider.getRecipeName(ModItems.CHISEL)));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.MOSSY_STONE_BRICK_WRITINGS)
                .input(ModBlocks.STONE_BRICK_WRITINGS)
                .input(Blocks.MOSS_BLOCK)
                .group("mossy_stone_brick_writings")
                .criterion(FabricRecipeProvider.hasItem(ModBlocks.STONE_BRICK_WRITINGS), FabricRecipeProvider.conditionsFromItem(ModBlocks.STONE_BRICK_WRITINGS))
                .criterion(FabricRecipeProvider.hasItem(Blocks.MOSS_BLOCK), FabricRecipeProvider.conditionsFromItem(Blocks.MOSS_BLOCK))
                .offerTo(exporter, new Identifier(ArcheologyPlus.MOD_ID, FabricRecipeProvider.getRecipeName(ModBlocks.MOSSY_STONE_BRICK_WRITINGS) + "_from_moss"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.MOSSY_STONE_BRICK_WRITINGS)
                .input(ModBlocks.STONE_BRICK_WRITINGS)
                .input(Blocks.VINE)
                .group("mossy_stone_brick_writings")
                .criterion(FabricRecipeProvider.hasItem(ModBlocks.STONE_BRICK_WRITINGS), FabricRecipeProvider.conditionsFromItem(ModBlocks.STONE_BRICK_WRITINGS))
                .criterion(FabricRecipeProvider.hasItem(Blocks.VINE), FabricRecipeProvider.conditionsFromItem(Blocks.VINE))
                .offerTo(exporter, new Identifier(ArcheologyPlus.MOD_ID, FabricRecipeProvider.getRecipeName(ModBlocks.MOSSY_STONE_BRICK_WRITINGS) + "_from_vine"));
    }
}
