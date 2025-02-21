package net.emilsg.archeologyplus.util;

import net.emilsg.archeologyplus.ArcheologyPlus;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class ModDamageSources {

    public static final RegistryKey<DamageType> SPIKES = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(ArcheologyPlus.MOD_ID, "spikes"));
    public static final RegistryKey<DamageType> CERTAIN_DEATH = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(ArcheologyPlus.MOD_ID, "certain_death"));

    private static final Map<RegistryKey<DamageType>, DamageSource> sourceMap = new HashMap<>();

    public static DamageSource getDamageSource(DamageSources damageSources, RegistryKey<DamageType> damageType) {
        return sourceMap.computeIfAbsent(damageType, damageSources::create);
    }
}
