package net.emilsg.archeologyplus;

import net.emilsg.archeologyplus.register.LootTableModifiers;
import net.emilsg.archeologyplus.register.items.ModItems;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArcheologyPlus implements ModInitializer {

	public static final String MOD_ID = "archeologyplus";
    public static final Logger LOGGER = LoggerFactory.getLogger("archeologyplus");

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		LootTableModifiers.modifyLootTables();

		if (FabricLoader.getInstance().isModLoaded("clutter")) {
			LootTableModifiers.conditionalModifyLootTable();
		}
	}
}