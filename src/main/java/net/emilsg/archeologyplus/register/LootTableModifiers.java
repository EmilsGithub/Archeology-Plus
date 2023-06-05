package net.emilsg.archeologyplus.register;

import net.emilsg.archeologyplus.register.items.ModItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.Identifier;

public class LootTableModifiers {

    private static final Identifier DESERT_PYRAMID= new Identifier("minecraft", "archaeology/desert_pyramid");
    private static final Identifier DESERT_WELL = new Identifier("minecraft", "archaeology/desert_well");
    private static final Identifier OCEAN_RUIN_COLD = new Identifier("minecraft", "archaeology/ocean_ruin_cold");
    private static final Identifier OCEAN_RUIN_WARM = new Identifier("minecraft", "archaeology/ocean_ruin_warm");
    private static final Identifier TRAIL_RUINS_COMMON = new Identifier("minecraft", "archaeology/trail_ruins_common");
    private static final Identifier TRAIL_RUINS_RARE = new Identifier("minecraft", "archaeology/trail_ruins_rare");

    private static final Identifier SNIFFER_DIGGING = new Identifier("minecraft", "gameplay/sniffer_digging");

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (id.equals(DESERT_WELL)) {
                tableBuilder.modifyPools(builder -> builder
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.MERCHANT_POTTERY_SHERD).weight(2)))
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.SIGHT_POTTERY_SHERD).weight(2)))
                );
            }

            if (id.equals(DESERT_PYRAMID)) {
                tableBuilder.modifyPools(builder -> builder
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.MASTER_POTTERY_SHERD).weight(2)))
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.LIGHT_POTTERY_SHERD).weight(2)))
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.MERCHANT_POTTERY_SHERD).weight(2))));
            }

            if (id.equals(TRAIL_RUINS_COMMON)) {
                tableBuilder.modifyPools(builder -> builder
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.HOP_POTTERY_SHERD).weight(2)))
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.MIGHT_POTTERY_SHERD).weight(2)))
                );
            }

            if (id.equals(OCEAN_RUIN_COLD)) {
                tableBuilder.modifyPools(builder -> builder
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.FRIGHT_POTTERY_SHERD).weight(2)))
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.NIGHT_POTTERY_SHERD).weight(2))));
            }

            if (id.equals(OCEAN_RUIN_WARM)) {
                tableBuilder.modifyPools(builder -> builder
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.CHOMP_POTTERY_SHERD).weight(2)))
                );
            }

            if (id.equals(TRAIL_RUINS_RARE)) {
                tableBuilder.modifyPools(builder -> builder
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.REVIVE_POTTERY_SHERD)))
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.FLIGHT_POTTERY_SHERD)))
                        .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.LOADER_POTTERY_SHERD)))
                );
            }
        }));
    }
}
