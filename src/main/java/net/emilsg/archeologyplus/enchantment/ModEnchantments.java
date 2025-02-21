package net.emilsg.archeologyplus.enchantment;

import net.emilsg.archeologyplus.ArcheologyPlus;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEnchantments {

    public static final Enchantment EXCAVATORS_PACE_ENCHANTMENT = register("excavators_pace", new ExcavatorsPaceEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.MAINHAND));

    public static final Enchantment EXPULSION_ENCHANTMENT = register("expulsion", new ExpulsionEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.CHEST));

    public static final Enchantment BYPASS_ENCHANTMENT = register("bypass", new BypassEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlot.LEGS));

    private static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, new Identifier(ArcheologyPlus.MOD_ID, name), enchantment);
    }

    public static void registerModEnchantments() {

    }

}

