package net.emilsg.archeologyplus.register.blocks;

import net.emilsg.archeologyplus.ArcheologyPlus;
import net.emilsg.archeologyplus.mixin.DecoratedPotPatternsAccessor;
import net.emilsg.archeologyplus.register.items.ModItems;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ModPottery {

    private static final Map<Item, RegistryKey<String>> SHERD_TO_PATTERN = new HashMap<>();

    private static final Map<Item, String> ITEM_TO_PATTERN_NAME;

    static {
        Map<Item, String> map = new HashMap<>();
        map.put(ModItems.LOADER_POTTERY_SHERD, "loader");
        map.put(ModItems.MASTER_POTTERY_SHERD, "master");
        map.put(ModItems.MERCHANT_POTTERY_SHERD, "merchant");
        map.put(ModItems.HOP_POTTERY_SHERD, "hop");
        map.put(ModItems.SIGHT_POTTERY_SHERD, "sight");
        map.put(ModItems.NIGHT_POTTERY_SHERD, "night");
        map.put(ModItems.MIGHT_POTTERY_SHERD, "might");
        map.put(ModItems.FRIGHT_POTTERY_SHERD, "fright");
        map.put(ModItems.LIGHT_POTTERY_SHERD, "light");
        map.put(ModItems.FLIGHT_POTTERY_SHERD, "flight");
        map.put(ModItems.CHOMP_POTTERY_SHERD, "chomp");
        map.put(ModItems.REVIVE_POTTERY_SHERD, "revive");
        ITEM_TO_PATTERN_NAME = Collections.unmodifiableMap(map);


        for (Map.Entry<Item, String> entry : ITEM_TO_PATTERN_NAME.entrySet()) {
            RegistryKey<String> registryKey = withModPath(entry.getValue() + "_pottery_pattern");
            SHERD_TO_PATTERN.put(entry.getKey(), registryKey);
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
