package net.emilsg.archeologyplus.register.blocks;

import net.emilsg.archeologyplus.ArcheologyPlus;
import net.emilsg.archeologyplus.mixin.DecoratedPotPatternsAccessor;
import net.emilsg.archeologyplus.register.items.ModItems;
import net.emilsg.archeologyplus.register.items.custom.SherdItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ModPottery {

    private static final Map<Item, RegistryKey<String>> SHERD_TO_PATTERN = new HashMap<>();

    public static final SherdItem[] SHERD_ITEMS = {
            (SherdItem) ModItems.LOADER_POTTERY_SHERD,
            (SherdItem) ModItems.MASTER_POTTERY_SHERD,
            (SherdItem) ModItems.MERCHANT_POTTERY_SHERD,
            (SherdItem) ModItems.HOP_POTTERY_SHERD,
            (SherdItem) ModItems.LIGHT_POTTERY_SHERD,
            (SherdItem) ModItems.MIGHT_POTTERY_SHERD,
            (SherdItem) ModItems.FLIGHT_POTTERY_SHERD,
            (SherdItem) ModItems.SIGHT_POTTERY_SHERD,
            (SherdItem) ModItems.FRIGHT_POTTERY_SHERD,
            (SherdItem) ModItems.NIGHT_POTTERY_SHERD,
            (SherdItem) ModItems.CHOMP_POTTERY_SHERD,
            (SherdItem) ModItems.REVIVE_POTTERY_SHERD,
            (SherdItem) ModItems.BUTTERFLY_POTTERY_SHERD,
            (SherdItem) ModItems.NAUTILUS_POTTERY_SHERD,
            (SherdItem) ModItems.HALO_POTTERY_SHERD,
            (SherdItem) ModItems.DEVIL_POTTERY_SHERD
    };

    static {
        for (SherdItem sherdItem : SHERD_ITEMS) {
            String patternName = sherdItem.getPatternName();
            RegistryKey<String> registryKey = withModPath(patternName + "_pottery_pattern");
            SHERD_TO_PATTERN.put(sherdItem, registryKey);
        }
    }

    public static void registerAndDefault(Registry<String> registry) {
        for (Map.Entry<Item, RegistryKey<String>> entry : SHERD_TO_PATTERN.entrySet()) {
            Registry.register(registry, entry.getValue(), entry.getValue().getValue().getPath());
        }
    }

    public static void matchSherdsWithPatterns() {
        for (Map.Entry<Item, RegistryKey<String>> entry : SHERD_TO_PATTERN.entrySet()) {
            DecoratedPotPatternsAccessor.getSherdToPattern().put(entry.getKey(), entry.getValue());
        }
    }

    @Nullable
    public static RegistryKey<String> fromSherd(Item sherd) {
        return SHERD_TO_PATTERN.get(sherd);
    }

    private static RegistryKey<String> withModPath(String path) {
        return RegistryKey.of(RegistryKeys.DECORATED_POT_PATTERN, new Identifier(ArcheologyPlus.MOD_ID, path));
    }

}
