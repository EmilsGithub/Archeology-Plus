package net.emilsg.archeologyplus.mixin;

import net.emilsg.archeologyplus.register.items.ModItems;
import net.emilsg.archeologyplus.util.playerticking.IdolTicking;
import net.emilsg.archeologyplus.util.playerticking.PlayerTicking;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {


    @Inject(method = "tick()V", at = @At("HEAD"))
    public void tickIdolItems(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        IdolTicking.tickIdols(player);
        PlayerTicking.doPlayerTick(player);
    }

    @Inject(method = "getBlockBreakingSpeed", at = @At("RETURN"), cancellable = true)
    private void modifyBlockBreakingSpeed(BlockState block, CallbackInfoReturnable<Float> cirF) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        ItemStack mainHand = player.getMainHandStack();
        ItemStack offHand = player.getOffHandStack();
        float originalSpeed = cirF.getReturnValue();
        boolean hasAquaAffinity = EnchantmentHelper.hasAquaAffinity(player);
        boolean isHoldingSeashellItem = (mainHand.isOf(ModItems.SEASHELL_IDOL) || offHand.isOf(ModItems.SEASHELL_IDOL));

        if (player.isSubmergedIn(FluidTags.WATER) && !hasAquaAffinity && !isHoldingSeashellItem) {
            originalSpeed /= 5.0F;
        }

        cirF.setReturnValue(originalSpeed);
    }
}
