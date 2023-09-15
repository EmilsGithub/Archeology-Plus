package net.emilsg.archeologyplus.datagen;

import net.emilsg.archeologyplus.register.blocks.ModBlocks;
import net.emilsg.archeologyplus.register.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
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
                .criterion(FabricRecipeProvider.hasItem(Items.BRICK),
                        FabricRecipeProvider.conditionsFromItem(Items.BRICK))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModBlocks.LOOT_POT)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.CHISEL)
                .pattern("  N")
                .pattern(" I ")
                .pattern("S  ")
                .input('N', Items.IRON_NUGGET)
                .input('I', Items.IRON_INGOT)
                .input('S', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(Items.IRON_INGOT),
                        FabricRecipeProvider.conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, new Identifier(FabricRecipeProvider.getRecipeName(ModItems.CHISEL)));
    }
}
