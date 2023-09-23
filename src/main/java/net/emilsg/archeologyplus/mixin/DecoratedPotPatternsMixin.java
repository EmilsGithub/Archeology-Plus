package net.emilsg.archeologyplus.mixin;

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
        ModPottery.matchSherdsWithPatterns();
    }

    @Inject(method = "registerAndGetDefault", at = @At("HEAD"))
    private static void onRegisterAndGetDefault(Registry<String> registry, CallbackInfoReturnable<String> cir) {
        ModPottery.registerAndDefault(registry);
    }
}
