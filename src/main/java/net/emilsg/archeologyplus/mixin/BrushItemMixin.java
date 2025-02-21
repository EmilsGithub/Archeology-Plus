package net.emilsg.archeologyplus.mixin;

import net.emilsg.archeologyplus.enchantment.ModEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BrushItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BrushItem.class)
public abstract class BrushItemMixin extends Item {

    public BrushItemMixin(Settings settings) {
        super(settings);
    }

    @ModifyVariable(method = "usageTick", at = @At(value = "STORE"), ordinal = 0)
    private boolean modifyBrushingInterval(boolean bl, World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        int enchantmentLevel = EnchantmentHelper.getLevel(ModEnchantments.EXCAVATORS_PACE_ENCHANTMENT, stack);
        int modifiedFrequency = 10 - enchantmentLevel + 1;
        if (modifiedFrequency < 1) modifiedFrequency = 1;

        int halfwayPoint = modifiedFrequency / 2;
        int i = this.getMaxUseTime(stack) - remainingUseTicks + 1;
        return i % modifiedFrequency == halfwayPoint;
    }
}
