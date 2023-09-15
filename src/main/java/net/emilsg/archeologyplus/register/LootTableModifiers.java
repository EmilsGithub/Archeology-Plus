package net.emilsg.archeologyplus.register;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.util.Identifier;

public class LootTableModifiers {
    private static final Identifier SNIFFER_DIGGING = new Identifier("minecraft", "gameplay/sniffer_digging");

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) -> {
            //if (id.equals(SNIFFER_DIGGING)) {
            //    tableBuilder.modifyPools(builder -> builder
            //            .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.MERCHANT_POTTERY_SHERD).weight(2)))
            //            .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.SIGHT_POTTERY_SHERD).weight(2)))
            //    );
            //}
        }));
    }
}
