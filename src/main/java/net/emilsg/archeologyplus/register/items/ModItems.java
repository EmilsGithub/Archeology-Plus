package net.emilsg.archeologyplus.register.items;

import net.emilsg.archeologyplus.ArcheologyPlus;
import net.emilsg.archeologyplus.register.blocks.ModBlocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.item.trim.ArmorTrimPatterns;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item LOADER_POTTERY_SHERD = registerItem("loader_pottery_sherd", new Item(new FabricItemSettings()), ItemGroups.INGREDIENTS);
    public static final Item MASTER_POTTERY_SHERD = registerItem("master_pottery_sherd", new Item(new FabricItemSettings()), ItemGroups.INGREDIENTS);
    public static final Item MERCHANT_POTTERY_SHERD = registerItem("merchant_pottery_sherd", new Item(new FabricItemSettings()), ItemGroups.INGREDIENTS);
    public static final Item HOP_POTTERY_SHERD = registerItem("hop_pottery_sherd", new Item(new FabricItemSettings()), ItemGroups.INGREDIENTS);
    public static final Item LIGHT_POTTERY_SHERD = registerItem("light_pottery_sherd", new Item(new FabricItemSettings()), ItemGroups.INGREDIENTS);
    public static final Item MIGHT_POTTERY_SHERD = registerItem("might_pottery_sherd", new Item(new FabricItemSettings()), ItemGroups.INGREDIENTS);
    public static final Item FRIGHT_POTTERY_SHERD = registerItem("fright_pottery_sherd", new Item(new FabricItemSettings()), ItemGroups.INGREDIENTS);
    public static final Item FLIGHT_POTTERY_SHERD = registerItem("flight_pottery_sherd", new Item(new FabricItemSettings()), ItemGroups.INGREDIENTS);
    public static final Item SIGHT_POTTERY_SHERD = registerItem("sight_pottery_sherd", new Item(new FabricItemSettings()), ItemGroups.INGREDIENTS);
    public static final Item NIGHT_POTTERY_SHERD = registerItem("night_pottery_sherd", new Item(new FabricItemSettings()), ItemGroups.INGREDIENTS);
    public static final Item CHOMP_POTTERY_SHERD = registerItem("chomp_pottery_sherd", new Item(new FabricItemSettings()), ItemGroups.INGREDIENTS);
    public static final Item REVIVE_POTTERY_SHERD = registerItem("revive_pottery_sherd", new Item(new FabricItemSettings()), ItemGroups.INGREDIENTS);

    private static Item registerItem(String name, Item item, RegistryKey<ItemGroup> group) {
        Item registeredItem = Registry.register(Registries.ITEM, new Identifier(ArcheologyPlus.MOD_ID, name), item);
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(registeredItem));
        return registeredItem;
    }

    public static void registerModItems() {
        ArcheologyPlus.LOGGER.info("Registering Mod Items for " + ArcheologyPlus.MOD_ID);
    }
}
