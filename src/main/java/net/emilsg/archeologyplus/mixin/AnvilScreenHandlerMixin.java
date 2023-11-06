package net.emilsg.archeologyplus.mixin;

import net.emilsg.archeologyplus.register.items.custom.HarvestIdol;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {

    public AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }

    @ModifyVariable(method = "updateResult()V", at = @At(value = "STORE"), ordinal = 0)
    public ItemStack modifyOutput(ItemStack itemStack2) {
        if (this.input.getStack(0).getItem() instanceof HarvestIdol && (EnchantmentHelper.get(this.input.getStack(1)).containsKey(Enchantments.MENDING) || EnchantmentHelper.get(this.input.getStack(1)).containsKey(Enchantments.UNBREAKING))) {
            return ItemStack.EMPTY;
        }
        return itemStack2;
    }
}
