package net.emilsg.archeologyplus.effect;

import net.emilsg.archeologyplus.ArcheologyPlus;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEffects {
    public static StatusEffect MOONS_BLESSING = registerEffect("moons_blessing", new MoonsBlessingEffect(StatusEffectCategory.BENEFICIAL, 8355779));

    public static StatusEffect SUNS_BLESSING = registerEffect("suns_blessing", new SunsBlessingEffect(StatusEffectCategory.BENEFICIAL, 16448100));

    public static StatusEffect CERTAIN_DEATH = registerEffect("certain_death", new CertainDeathEffect(StatusEffectCategory.HARMFUL, 657930));

    private static StatusEffect registerEffect(String name, StatusEffect effect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(ArcheologyPlus.MOD_ID, name), effect);
    }

    public static void registerEffects() {

    }
}
