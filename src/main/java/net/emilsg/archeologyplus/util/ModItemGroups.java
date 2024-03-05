package net.emilsg.archeologyplus.util;

import net.emilsg.archeologyplus.ArcheologyPlus;
import net.emilsg.archeologyplus.register.blocks.ModBlocks;
import net.emilsg.archeologyplus.register.items.ModItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup ARCHEOLOGY_PLUS = Registry.register(Registries.ITEM_GROUP,
            new Identifier(ArcheologyPlus.MOD_ID, "archeology_plus"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.archeology_plus"))
                    .icon(() -> new ItemStack(ModItems.LOADER_POTTERY_SHERD)).entries((displayContext, entries) -> {
                        entries.add(ModItems.LOADER_POTTERY_SHERD);
                        entries.add(ModItems.MASTER_POTTERY_SHERD);
                        entries.add(ModItems.MERCHANT_POTTERY_SHERD);
                        entries.add(ModItems.HOP_POTTERY_SHERD);
                        entries.add(ModItems.LIGHT_POTTERY_SHERD);
                        entries.add(ModItems.MIGHT_POTTERY_SHERD);
                        entries.add(ModItems.FLIGHT_POTTERY_SHERD);
                        entries.add(ModItems.SIGHT_POTTERY_SHERD);
                        entries.add(ModItems.FRIGHT_POTTERY_SHERD);
                        entries.add(ModItems.NIGHT_POTTERY_SHERD);
                        entries.add(ModItems.CHOMP_POTTERY_SHERD);
                        entries.add(ModItems.REVIVE_POTTERY_SHERD);
                        entries.add(ModItems.BUTTERFLY_POTTERY_SHERD);
                        entries.add(ModItems.NAUTILUS_POTTERY_SHERD);
                        entries.add(ModItems.HALO_POTTERY_SHERD);
                        entries.add(ModItems.DEVIL_POTTERY_SHERD);

                        entries.add(ModItems.CHISEL);

                        entries.add(ModItems.SUN_IDOL);
                        entries.add(ModItems.MOON_IDOL);
                        entries.add(ModItems.SEASHELL_IDOL);
                        entries.add(ModItems.RAIN_IDOL);
                        entries.add(ModItems.FIRE_IDOL);
                        entries.add(ModItems.HARVEST_IDOL);
                        entries.add(ModItems.GLIDING_IDOL);
                        entries.add(ModItems.WITHER_IDOL);

                        entries.add(ModBlocks.LOOT_POT);
                        entries.add(ModBlocks.SUSPICIOUS_DIRT);
                        entries.add(ModBlocks.SUSPICIOUS_RED_SAND);
                        entries.add(ModBlocks.SUSPICIOUS_SOUL_SAND);
                        entries.add(ModBlocks.CRUMBLING_SANDSTONE);
                        entries.add(ModBlocks.SANDSTONE_HIEROGLYPHS);
                        entries.add(ModBlocks.CRUMBLING_RED_SANDSTONE);
                        entries.add(ModBlocks.RED_SANDSTONE_HIEROGLYPHS);
                        entries.add(ModBlocks.STONE_BRICK_WRITINGS);
                        entries.add(ModBlocks.MOSSY_STONE_BRICK_WRITINGS);


                    }).build());

    public static void registerItemGroups() {

    }
}
