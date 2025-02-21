package net.emilsg.archeologyplus;

import net.emilsg.archeologyplus.config.APConfig;
import net.emilsg.archeologyplus.effect.ModEffects;
import net.emilsg.archeologyplus.enchantment.ModEnchantments;
import net.emilsg.archeologyplus.networking.ModMessages;
import net.emilsg.archeologyplus.register.LootTableModifiers;
import net.emilsg.archeologyplus.register.blocks.ModBlocks;
import net.emilsg.archeologyplus.register.blocks.entity.ModBlockEntities;
import net.emilsg.archeologyplus.register.items.ModItems;
import net.emilsg.archeologyplus.util.ModItemGroups;
import net.emilsg.archeologyplus.util.ModUtil;
import net.emilsg.archeologyplus.util.village.AlterVillageStructures;
import net.emilsg.archeologyplus.util.village.ModVillagers;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArcheologyPlus implements ModInitializer {
    public static final String MOD_VERSION = "2.1.0";
    public static final String MOD_ID = "archeologyplus";
    public static final Logger LOGGER = LoggerFactory.getLogger("ArcheologyPlus");

    @Override
    public void onInitialize() {
        APConfig.init();

        ModEffects.registerEffects();
        ModBlocks.registerModBlocks();
        ModItems.registerModItems();
        ModBlockEntities.registerModBlockEntities();
        ModEnchantments.registerModEnchantments();
        ModItemGroups.registerItemGroups();

        ModVillagers.registerVillagers();
        ModUtil.registerModUtil();

        LootTableModifiers.modifyLootTables();

        AlterVillageStructures.addHouses();

        ModMessages.registerHandshakePackets();
        LOGGER.info("[Clutter] Finished initializing.");
    }

}