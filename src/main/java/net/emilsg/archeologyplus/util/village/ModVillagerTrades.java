package net.emilsg.archeologyplus.util.village;

import net.emilsg.archeologyplus.register.blocks.ModBlocks;
import net.emilsg.archeologyplus.register.items.ModItems;
import net.emilsg.archeologyplus.util.tags.ModStructureTags;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapIcon;
import net.minecraft.item.map.MapState;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.world.gen.structure.Structure;

public class ModVillagerTrades {
    public static void registerTrades() {


        TradeOfferHelper.registerVillagerOffers(ModVillagers.ARCHEOLOGIST, 1,
                factories -> {

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 6),
                            new ItemStack(ModItems.CHISEL, 1), 4, 4, 0.05f
                    ));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 8),
                            new ItemStack(Items.BRUSH, 1), 4, 6, 0.05f
                    ));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 8),
                            new ItemStack(Items.MAP, 1), 4, 6, 0.05f
                    ));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 4),
                            new ItemStack(Items.SAND, 8), 4, 2, 0.05f
                    ));

                });

        TradeOfferHelper.registerVillagerOffers(ModVillagers.ARCHEOLOGIST, 2,
                factories -> {

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 4),
                            new ItemStack(ModBlocks.SANDSTONE_HIEROGLYPHS, 8), 4, 8, 0.05f
                    ));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 4),
                            new ItemStack(ModBlocks.RED_SANDSTONE_HIEROGLYPHS, 8), 4, 8, 0.05f
                    ));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 4),
                            new ItemStack(ModBlocks.STONE_BRICK_WRITINGS, 8), 4, 8, 0.05f
                    ));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 4),
                            new ItemStack(ModBlocks.MOSSY_STONE_BRICK_WRITINGS, 8), 4, 8, 0.05f
                    ));

                });

        TradeOfferHelper.registerVillagerOffers(ModVillagers.ARCHEOLOGIST, 3,
                factories -> {

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 4),
                            new ItemStack(ModBlocks.CRUMBLING_SANDSTONE, 4), 4, 10, 0.05f
                    ));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 4),
                            new ItemStack(ModBlocks.CRUMBLING_RED_SANDSTONE, 4), 4, 10, 0.05f
                    ));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 4),
                            new ItemStack(ModBlocks.CRUMBLING_STONE_BRICKS, 4), 4, 10, 0.05f
                    ));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 4),
                            new ItemStack(ModBlocks.CRUMBLING_MOSSY_STONE_BRICKS, 4), 4, 10, 0.05f
                    ));

                });

        TradeOfferHelper.registerVillagerOffers(ModVillagers.ARCHEOLOGIST, 4,
                factories -> {

                    factories.add((entity, random) -> createMapTrade(entity, random, ModStructureTags.IS_DESERT_TEMPLE, "filled_map.archeologyplus.desert_temple", 16, MapIcon.Type.BANNER_RED, 1, 16, 0.05f));

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 8),
                            new ItemStack(ModBlocks.SPIKE_TRAP, 1), 4, 10, 0.05f
                    ));

                });

        TradeOfferHelper.registerVillagerOffers(ModVillagers.ARCHEOLOGIST, 5,
                factories -> {

                    factories.add((entity, random) -> new TradeOffer(
                            new ItemStack(Items.EMERALD, 48),
                            new ItemStack(Items.GOLD_INGOT, 4),
                            new ItemStack(ModItems.IDOL_OF_PROTECTION, 1), 1, 20, 0.05f
                    ));

                });
    }

    private static TradeOffer createMapTrade(Entity entity, Random random, TagKey<Structure> structure, String translationKey, int price, MapIcon.Type iconType, int maxUses, int merchantExperience, float priceMultiplier) {
        if (!(entity.getWorld() instanceof ServerWorld serverWorld)) {
            return null;
        }

        BlockPos blockPos = serverWorld.locateStructure(structure, entity.getBlockPos(), 100, true);

        if (blockPos != null) {
            ItemStack map = FilledMapItem.createMap(serverWorld, blockPos.getX(), blockPos.getZ(), (byte) 2, true, true);
            FilledMapItem.fillExplorationMap(serverWorld, map);
            MapState.addDecorationsNbt(map, blockPos, "+", iconType);
            map.setCustomName(Text.translatable(translationKey));
            return new TradeOffer(new ItemStack(Items.EMERALD, price), new ItemStack(Items.COMPASS), map, maxUses, merchantExperience, priceMultiplier);
        }
        return null;
    }
}
