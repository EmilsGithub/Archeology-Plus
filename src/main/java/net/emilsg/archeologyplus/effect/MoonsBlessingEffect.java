package net.emilsg.archeologyplus.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class MoonsBlessingEffect extends StatusEffect {

    protected MoonsBlessingEffect(StatusEffectCategory category, int color) {
        super(category, color);
        this.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "a68c23c8-88a0-4434-978e-718881104c97", 0.01f, EntityAttributeModifier.Operation.ADDITION);
        this.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, "779fe9ac-a137-43ad-a81f-398a3f8ba086", 1f, EntityAttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.hasStatusEffect(ModEffects.SUNS_BLESSING)) entity.removeStatusEffect(ModEffects.SUNS_BLESSING);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
