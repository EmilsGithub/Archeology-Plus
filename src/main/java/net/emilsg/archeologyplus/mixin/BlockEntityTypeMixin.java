package net.emilsg.archeologyplus.mixin;

import com.google.common.collect.ImmutableSet;
import net.emilsg.archeologyplus.register.blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin {

    @Shadow
    public static BlockEntityType<?> BRUSHABLE_BLOCK;

    @Inject(method = "supports", at = @At("HEAD"), cancellable = true)
    public void supports(BlockState state, CallbackInfoReturnable<Boolean> cir) {

        if ((Object) this == BRUSHABLE_BLOCK) {
            ImmutableSet<Block> additionalBlocks = ImmutableSet.of(ModBlocks.SUSPICIOUS_SOUL_SAND, ModBlocks.SUSPICIOUS_RED_SAND, ModBlocks.SUSPICIOUS_DIRT);
            if (additionalBlocks.contains(state.getBlock())) {
                cir.setReturnValue(true);
            }
        }
    }
}
