package net.emilsg.archeologyplus.mixin;

import net.emilsg.archeologyplus.register.items.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "damage", at = @At("HEAD"))
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;

        if (!(source.getAttacker() instanceof PlayerEntity attackingPlayer)) return;

        boolean playerHoldingItem = attackingPlayer.isHolding(ModItems.FIRE_IDOL) && !attackingPlayer.getItemCooldownManager().isCoolingDown(ModItems.FIRE_IDOL);

        if (playerHoldingItem && !self.getWorld().isClient && self.getFireTicks() <= 0) {
            self.setFireTicks(60);
            attackingPlayer.getItemCooldownManager().set(ModItems.FIRE_IDOL, 80);
        }
    }

}
