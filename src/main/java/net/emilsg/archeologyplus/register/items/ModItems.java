package net.emilsg.archeologyplus.register.items;

import net.emilsg.archeologyplus.ArcheologyPlus;
import net.emilsg.archeologyplus.register.items.custom.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class ModItems {

    /**
      To add a new Sherd-Item with Pattern:
      1. Make Item for Sherd
      2. Add Item to Tag
      3. Add Item to ItemGroup
      4. Add Item to DecoratedPotBlockEntityRendererMixin
      5. Add Item with String to ModPottery Map
      6. Add Textures (For Item and Pattern) + Model (For Item)
      7. Add Translation in Lang file
    **/

    public static final Item LOADER_POTTERY_SHERD = registerItem("loader_pottery_sherd", new Item(new FabricItemSettings()));
    public static final Item MASTER_POTTERY_SHERD = registerItem("master_pottery_sherd", new Item(new FabricItemSettings()));
    public static final Item MERCHANT_POTTERY_SHERD = registerItem("merchant_pottery_sherd", new Item(new FabricItemSettings()));
    public static final Item HOP_POTTERY_SHERD = registerItem("hop_pottery_sherd", new Item(new FabricItemSettings()));
    public static final Item LIGHT_POTTERY_SHERD = registerItem("light_pottery_sherd", new Item(new FabricItemSettings()));
    public static final Item MIGHT_POTTERY_SHERD = registerItem("might_pottery_sherd", new Item(new FabricItemSettings()));
    public static final Item FLIGHT_POTTERY_SHERD = registerItem("flight_pottery_sherd", new Item(new FabricItemSettings()));
    public static final Item SIGHT_POTTERY_SHERD = registerItem("sight_pottery_sherd", new Item(new FabricItemSettings()));
    public static final Item FRIGHT_POTTERY_SHERD = registerItem("fright_pottery_sherd", new Item(new FabricItemSettings()));
    public static final Item NIGHT_POTTERY_SHERD = registerItem("night_pottery_sherd", new Item(new FabricItemSettings()));
    public static final Item CHOMP_POTTERY_SHERD = registerItem("chomp_pottery_sherd", new Item(new FabricItemSettings()));
    public static final Item REVIVE_POTTERY_SHERD = registerItem("revive_pottery_sherd", new Item(new FabricItemSettings()));
    public static final Item BUTTERFLY_POTTERY_SHERD = registerItem("butterfly_pottery_sherd", new Item(new FabricItemSettings()));
    public static final Item NAUTILUS_POTTERY_SHERD = registerItem("nautilus_pottery_sherd", new Item(new FabricItemSettings()));
    public static final Item HALO_POTTERY_SHERD = registerItem("halo_pottery_sherd", new Item(new FabricItemSettings()));
    public static final Item DEVIL_POTTERY_SHERD = registerItem("devil_pottery_sherd", new Item(new FabricItemSettings()));

    public static final Item SUN_IDOL = registerItem("sun_idol", new DescriptionItem(new FabricItemSettings().maxCount(1), "description.item.archeologyplus.sun_idol", Formatting.BLUE, "subdescription.item.archeologyplus.sun_idol", Formatting.YELLOW));
    public static final Item MOON_IDOL = registerItem("moon_idol", new DescriptionItem(new FabricItemSettings().maxCount(1), "description.item.archeologyplus.moon_idol", Formatting.BLUE, "subdescription.item.archeologyplus.moon_idol", Formatting.YELLOW));
    public static final Item SEASHELL_IDOL = registerItem("seashell_idol", new DescriptionItem(new FabricItemSettings().maxCount(1), "description.item.archeologyplus.seashell_idol", Formatting.BLUE, "subdescription.item.archeologyplus.seashell_idol", Formatting.YELLOW));
    public static final Item RAIN_IDOL = registerItem("rain_idol", new RainIdolItem(new FabricItemSettings().maxCount(1), "description.item.archeologyplus.rain_idol", Formatting.BLUE));
    public static final Item FIRE_IDOL = registerItem("fire_idol", new FireIdolItem(new FabricItemSettings().maxCount(1), "description.item.archeologyplus.fire_idol", Formatting.BLUE));
    public static final Item HARVEST_IDOL = registerItem("harvest_idol", new HarvestIdol(new FabricItemSettings().maxCount(1).maxDamage(64), "description.item.archeologyplus.harvest_idol", Formatting.BLUE, "subdescription.item.archeologyplus.harvest_idol", Formatting.YELLOW));
    public static final Item GLIDING_IDOL = registerItem("gliding_idol", new DescriptionItem(new FabricItemSettings().maxCount(1).maxDamage(64), "description.item.archeologyplus.gliding_idol", Formatting.BLUE));
    public static final Item WITHER_IDOL = registerItem("wither_idol", new DescriptionItem(new FabricItemSettings().maxCount(1).maxDamage(64), "description.item.archeologyplus.anchor_idol", Formatting.BLUE));

    public static final Item CHISEL = registerItem("chisel", new ChiselItem(new FabricItemSettings().maxDamage(432), "description.item.archeologyplus.chisel", Formatting.BLUE, "subdescription.item.archeologyplus.chisel", Formatting.YELLOW));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(ArcheologyPlus.MOD_ID, name), item);
    }

    public static void registerModItems() {
    }
}
