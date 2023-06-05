package net.emilsg.archeologyplus.register.blocks;

import net.emilsg.archeologyplus.register.items.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ModPottery {

    public static final RegistryKey<String> LOADER_POTTERY_PATTERN_KEY = of("loader_pottery_pattern");
    public static final RegistryKey<String> MASTER_POTTERY_PATTERN_KEY = of("master_pottery_pattern");
    public static final RegistryKey<String> MERCHANT_POTTERY_PATTERN_KEY = of("merchant_pottery_pattern");
    public static final RegistryKey<String> HOP_POTTERY_PATTERN_KEY = of("hop_pottery_pattern");
    public static final RegistryKey<String> SIGHT_POTTERY_PATTERN_KEY = of("sight_pottery_pattern");
    public static final RegistryKey<String> NIGHT_POTTERY_PATTERN_KEY = of("night_pottery_pattern");
    public static final RegistryKey<String> MIGHT_POTTERY_PATTERN_KEY = of("might_pottery_pattern");
    public static final RegistryKey<String> FRIGHT_POTTERY_PATTERN_KEY = of("fright_pottery_pattern");
    public static final RegistryKey<String> LIGHT_POTTERY_PATTERN_KEY = of("light_pottery_pattern");
    public static final RegistryKey<String> FLIGHT_POTTERY_PATTERN_KEY = of("flight_pottery_pattern");
    public static final RegistryKey<String> CHOMP_POTTERY_PATTERN_KEY = of("chomp_pottery_pattern");
    public static final RegistryKey<String> REVIVE_POTTERY_PATTERN_KEY = of("revive_pottery_pattern");


    private static final Map<Item, RegistryKey<String>> SHERD_TO_PATTERN;

    private static RegistryKey<String> of(String path) {
        return RegistryKey.of(RegistryKeys.DECORATED_POT_PATTERN, new Identifier(path));
    }

    @Nullable
    public static RegistryKey<String> fromSherd(Item sherd) {
        return SHERD_TO_PATTERN.get(sherd);
    }

    static {
        SHERD_TO_PATTERN = Map.ofEntries(Map.entry(Items.BRICK, LOADER_POTTERY_PATTERN_KEY),
                Map.entry(ModItems.LOADER_POTTERY_SHERD, LOADER_POTTERY_PATTERN_KEY),
                Map.entry(ModItems.MASTER_POTTERY_SHERD, MASTER_POTTERY_PATTERN_KEY),
                Map.entry(ModItems.MERCHANT_POTTERY_SHERD, MERCHANT_POTTERY_PATTERN_KEY),
                Map.entry(ModItems.HOP_POTTERY_SHERD, HOP_POTTERY_PATTERN_KEY),
                Map.entry(ModItems.SIGHT_POTTERY_SHERD, SIGHT_POTTERY_PATTERN_KEY),
                Map.entry(ModItems.NIGHT_POTTERY_SHERD, NIGHT_POTTERY_PATTERN_KEY),
                Map.entry(ModItems.LIGHT_POTTERY_SHERD, LIGHT_POTTERY_PATTERN_KEY),
                Map.entry(ModItems.FLIGHT_POTTERY_SHERD, FLIGHT_POTTERY_PATTERN_KEY),
                Map.entry(ModItems.MIGHT_POTTERY_SHERD, MIGHT_POTTERY_PATTERN_KEY),
                Map.entry(ModItems.FRIGHT_POTTERY_SHERD, FRIGHT_POTTERY_PATTERN_KEY),
                Map.entry(ModItems.CHOMP_POTTERY_SHERD, CHOMP_POTTERY_PATTERN_KEY),
                Map.entry(ModItems.REVIVE_POTTERY_SHERD, REVIVE_POTTERY_PATTERN_KEY)
        );
    }
}
