package net.emilsg.archeologyplus.register;

import net.emilsg.archeologyplus.register.blocks.ModBlocks;
import net.emilsg.archeologyplus.util.ModProperties;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class LootTableModifiers {
    private static final Identifier LOOT_POT_ID = new Identifier("archeologyplus", "blocks/loot_pot");
    private static final Identifier MEDIUM_LOOT_POT_ID = new Identifier("archeologyplus", "blocks/medium_loot_pot");

    private static final Identifier SNIFFER_DIGGING = new Identifier("minecraft", "gameplay/sniffer_digging");

    public static void modifyLootTables() {
        addCoinsWhenClutter();

        //if (id.equals(SNIFFER_DIGGING)) {
        //    tableBuilder.modifyPools(builder -> builder
        //            .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.MERCHANT_POTTERY_SHERD).weight(2)))
        //            .with(AlternativeEntry.builder(ItemEntry.builder(ModItems.SIGHT_POTTERY_SHERD).weight(2)))
        //    );
        //}
    }


    private static void addCoinsWhenClutter() {
        LootTableEvents.MODIFY.register(((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (FabricLoader.getInstance().isModLoaded("clutter")) {
                Item clutterCopperCoinItem = Registries.ITEM.get(new Identifier("clutter", "copper_coin"));

                if (id.equals(LOOT_POT_ID)) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1f))
                            .conditionally(BlockStatePropertyLootCondition.builder(ModBlocks.LOOT_POT)
                                    .properties(StatePredicate.Builder.create().exactMatch(ModProperties.DROPS_LOOT, true)))
                            .conditionally(RandomChanceLootCondition.builder(0.33f))
                            .with(ItemEntry.builder(clutterCopperCoinItem))
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 2f)).build());
                    tableBuilder.pool(poolBuilder.build());
                }

                if (id.equals(MEDIUM_LOOT_POT_ID)) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1f))
                            .conditionally(BlockStatePropertyLootCondition.builder(ModBlocks.MEDIUM_LOOT_POT)
                                    .properties(StatePredicate.Builder.create().exactMatch(ModProperties.DROPS_LOOT, true)))
                            .conditionally(RandomChanceLootCondition.builder(0.33f))
                            .with(ItemEntry.builder(clutterCopperCoinItem))
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1f, 3f)).build());
                    tableBuilder.pool(poolBuilder.build());
                }
            }
        }));
    }
}
