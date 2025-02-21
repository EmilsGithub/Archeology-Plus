package net.emilsg.archeologyplus.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class SunsBlessingEffect extends StatusEffect {

    protected SunsBlessingEffect(StatusEffectCategory category, int color) {
        super(category, color);
        addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "5daf9023-b225-4cb1-9275-d19c40fa36bd", 0.01f, EntityAttributeModifier.Operation.ADDITION);
        addAttributeModifier(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, "a257daf4-2bc8-426a-bb97-002ce530bdca", 2f, EntityAttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.hasStatusEffect(ModEffects.MOONS_BLESSING)) entity.removeStatusEffect(ModEffects.MOONS_BLESSING);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
