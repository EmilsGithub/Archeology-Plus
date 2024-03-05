package net.emilsg.archeologyplus;

import net.emilsg.archeologyplus.register.LootTableModifiers;
import net.emilsg.archeologyplus.register.blocks.ModBlocks;
import net.emilsg.archeologyplus.register.items.ModItems;
import net.emilsg.archeologyplus.util.ModItemGroups;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArcheologyPlus implements ModInitializer {
	public static final String MOD_VERSION = "2.1.0";
	public static final String MOD_ID = "archeologyplus";
    public static final Logger LOGGER = LoggerFactory.getLogger("archeologyplus");

	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModItemGroups.registerItemGroups();

		LootTableModifiers.modifyLootTables();
	}

}