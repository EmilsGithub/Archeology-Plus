package net.emilsg.archeologyplus.datagen;

import net.emilsg.archeologyplus.register.blocks.ModBlocks;
import net.emilsg.archeologyplus.register.items.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.state.property.IntProperty;

import java.util.Properties;

public class ModelDataGen extends FabricModelProvider {
    public ModelDataGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

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
    }
}
