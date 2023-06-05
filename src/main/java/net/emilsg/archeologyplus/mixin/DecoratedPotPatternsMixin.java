package net.emilsg.archeologyplus.mixin;

import net.emilsg.archeologyplus.register.items.ModItems;
import net.emilsg.archeologyplus.register.blocks.ModPottery;
import net.minecraft.block.DecoratedPotPatterns;
import net.minecraft.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DecoratedPotPatterns.class)
public class DecoratedPotPatternsMixin {
    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(CallbackInfo ci) {
        DecoratedPotPatternsAccessor.getSherdToPattern().put(ModItems.LOADER_POTTERY_SHERD, ModPottery.LOADER_POTTERY_PATTERN_KEY);
        DecoratedPotPatternsAccessor.getSherdToPattern().put(ModItems.MASTER_POTTERY_SHERD, ModPottery.MASTER_POTTERY_PATTERN_KEY);
        DecoratedPotPatternsAccessor.getSherdToPattern().put(ModItems.MERCHANT_POTTERY_SHERD, ModPottery.MERCHANT_POTTERY_PATTERN_KEY);
        DecoratedPotPatternsAccessor.getSherdToPattern().put(ModItems.HOP_POTTERY_SHERD, ModPottery.HOP_POTTERY_PATTERN_KEY);
        DecoratedPotPatternsAccessor.getSherdToPattern().put(ModItems.FRIGHT_POTTERY_SHERD, ModPottery.FRIGHT_POTTERY_PATTERN_KEY);
        DecoratedPotPatternsAccessor.getSherdToPattern().put(ModItems.FLIGHT_POTTERY_SHERD, ModPottery.FLIGHT_POTTERY_PATTERN_KEY);
        DecoratedPotPatternsAccessor.getSherdToPattern().put(ModItems.REVIVE_POTTERY_SHERD, ModPottery.REVIVE_POTTERY_PATTERN_KEY);
        DecoratedPotPatternsAccessor.getSherdToPattern().put(ModItems.CHOMP_POTTERY_SHERD, ModPottery.CHOMP_POTTERY_PATTERN_KEY);
        DecoratedPotPatternsAccessor.getSherdToPattern().put(ModItems.NIGHT_POTTERY_SHERD, ModPottery.NIGHT_POTTERY_PATTERN_KEY);
        DecoratedPotPatternsAccessor.getSherdToPattern().put(ModItems.MIGHT_POTTERY_SHERD, ModPottery.MIGHT_POTTERY_PATTERN_KEY);
        DecoratedPotPatternsAccessor.getSherdToPattern().put(ModItems.SIGHT_POTTERY_SHERD, ModPottery.SIGHT_POTTERY_PATTERN_KEY);
        DecoratedPotPatternsAccessor.getSherdToPattern().put(ModItems.LIGHT_POTTERY_SHERD, ModPottery.LIGHT_POTTERY_PATTERN_KEY);
    }

    @Inject(method = "registerAndGetDefault", at = @At("HEAD"))
    private static void onRegisterAndGetDefault(Registry<String> registry, CallbackInfoReturnable<String> cir) {
        Registry.register(registry, ModPottery.LOADER_POTTERY_PATTERN_KEY, "loader_pottery_pattern");
        Registry.register(registry, ModPottery.MASTER_POTTERY_PATTERN_KEY, "master_pottery_pattern");
        Registry.register(registry, ModPottery.MERCHANT_POTTERY_PATTERN_KEY, "merchant_pottery_pattern");
        Registry.register(registry, ModPottery.HOP_POTTERY_PATTERN_KEY, "hop_pottery_pattern");
        Registry.register(registry, ModPottery.FRIGHT_POTTERY_PATTERN_KEY, "fright_pottery_pattern");
        Registry.register(registry, ModPottery.FLIGHT_POTTERY_PATTERN_KEY, "flight_pottery_pattern");
        Registry.register(registry, ModPottery.REVIVE_POTTERY_PATTERN_KEY, "revive_pottery_pattern");
        Registry.register(registry, ModPottery.CHOMP_POTTERY_PATTERN_KEY, "chomp_pottery_pattern");
        Registry.register(registry, ModPottery.NIGHT_POTTERY_PATTERN_KEY, "night_pottery_pattern");
        Registry.register(registry, ModPottery.MIGHT_POTTERY_PATTERN_KEY, "might_pottery_pattern");
        Registry.register(registry, ModPottery.SIGHT_POTTERY_PATTERN_KEY, "sight_pottery_pattern");
        Registry.register(registry, ModPottery.LIGHT_POTTERY_PATTERN_KEY, "light_pottery_pattern");
    }
}
