package net.emilsg.archeologyplus.mixin;

import net.emilsg.archeologyplus.enchantment.ModEnchantments;
import net.minecraft.block.entity.BrushableBlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BrushItem;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BrushableBlockEntity.class)
public abstract class BrushableBlockEntityMixin {
    @Shadow
    private long nextBrushTime;

    @Unique
    public int getEnchantedBrushSpeed(PlayerEntity player) {
        int enchantmentLevel = EnchantmentHelper.getLevel(ModEnchantments.EXCAVATORS_PACE_ENCHANTMENT, player.getActiveItem());
        return 10 - ((enchantmentLevel * 2) + 1);
    }

    @Inject(method = "brush", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/BrushableBlockEntity;generateItem(Lnet/minecraft/entity/player/PlayerEntity;)V"))
    private void injectMethod(long worldTime, PlayerEntity player, Direction hitDirection, CallbackInfoReturnable<Boolean> cir){
        if(player.getActiveItem().getItem() instanceof BrushItem){
            this.nextBrushTime = this.nextBrushTime - getEnchantedBrushSpeed(player);
        }
    }

}
