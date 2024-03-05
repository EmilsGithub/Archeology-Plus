package net.emilsg.archeologyplus.mixin;

import net.emilsg.archeologyplus.register.blocks.ModPottery;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.DecoratedPotBlockEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(DecoratedPotBlockEntityRenderer.class)
public class DecoratedPotBlockEntityRendererMixin {

    @Unique
    private static final Map<Item, SpriteIdentifier> SHERD_ITEM_TEXTURES = new HashMap<>();

    static {
        for (Item item : ModPottery.SHERD_ITEMS) {
            SHERD_ITEM_TEXTURES.put(item, TexturedRenderLayers.getDecoratedPotPatternTextureId(ModPottery.fromSherd(item)));
        }
    }

    @Inject(method = "getTextureIdFromSherd", at = @At("HEAD"), cancellable = true)
    private static void getTextureIdFromSherd(Item item, CallbackInfoReturnable<SpriteIdentifier> cir) {
        SpriteIdentifier texture = SHERD_ITEM_TEXTURES.get(item);
        if (texture != null) {
            cir.setReturnValue(texture);
            cir.cancel();
        }
    }
}
