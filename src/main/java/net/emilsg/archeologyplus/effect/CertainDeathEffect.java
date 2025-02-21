package net.emilsg.archeologyplus.effect;

import net.emilsg.archeologyplus.util.ModDamageSources;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

import java.util.Objects;

public class CertainDeathEffect extends StatusEffect {
    private boolean hasDeathSoundPlayed = false;

    public CertainDeathEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.getWorld().isClient) return;

        if (entity.isAlive() && entity.canTakeDamage()) {

            if (!entity.hasStatusEffect(ModEffects.CERTAIN_DEATH)) return;

            int duration = Objects.requireNonNull(entity.getStatusEffect(ModEffects.CERTAIN_DEATH)).getDuration();

            if (duration <= 40 && !hasDeathSoundPlayed) {
                entity.getWorld().playSound(null, entity.getBlockPos(), SoundEvents.ENTITY_ENDERMAN_DEATH, SoundCategory.HOSTILE, 1.0f, 0.5f);
                hasDeathSoundPlayed = true;
            }

            if (duration <= 20) {
                entity.damage(ModDamageSources.getDamageSource(new DamageSources(entity.getWorld().getRegistryManager()), ModDamageSources.CERTAIN_DEATH), 100f);
                hasDeathSoundPlayed = false;
            }
        }
    }

}
