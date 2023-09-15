package net.emilsg.archeologyplus.mixin;

import net.minecraft.block.DecoratedPotPatterns;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(DecoratedPotPatterns.class)
public class DecoratedPotPatternsAccessor {
    @Accessor("SHERD_TO_PATTERN")
    static Map<Item, RegistryKey<String>> getSherdToPattern() {
        throw new AssertionError();
    }
}
