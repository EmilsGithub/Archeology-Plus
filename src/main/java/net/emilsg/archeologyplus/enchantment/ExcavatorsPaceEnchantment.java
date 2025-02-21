package net.emilsg.archeologyplus.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.BrushItem;
import net.minecraft.item.ItemStack;

public class ExcavatorsPaceEnchantment extends Enchantment {

    public ExcavatorsPaceEnchantment(Enchantment.Rarity rarity, EquipmentSlot... slots) {
        super(rarity, EnchantmentTarget.BREAKABLE, slots);
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof BrushItem;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
