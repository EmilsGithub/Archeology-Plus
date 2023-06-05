package net.emilsg.archeologyplus;

import net.emilsg.archeologyplus.datagen.LootTableDataGen;
import net.emilsg.archeologyplus.datagen.ModelDataGen;
import net.emilsg.archeologyplus.datagen.RecipeDataGen;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ArcheologyPlusDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(LootTableDataGen::new);
		pack.addProvider(ModelDataGen::new);
		pack.addProvider(RecipeDataGen::new);
	}
}
