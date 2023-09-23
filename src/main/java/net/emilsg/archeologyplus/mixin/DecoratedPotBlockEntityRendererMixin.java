package net.emilsg.archeologyplus.mixin;

import net.emilsg.archeologyplus.register.items.ModItems;
import net.emilsg.archeologyplus.register.blocks.ModPottery;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.DecoratedPotBlockEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(DecoratedPotBlockEntityRenderer.class)
public class DecoratedPotBlockEntityRendererMixin {

    private static final Map<Item, SpriteIdentifier> ITEM_TEXTURES = new HashMap<>();

    static {
        Item[] sherds = {
                ModItems.LOADER_POTTERY_SHERD,
                ModItems.MASTER_POTTERY_SHERD,
                ModItems.MERCHANT_POTTERY_SHERD,
                ModItems.HOP_POTTERY_SHERD,
                ModItems.FRIGHT_POTTERY_SHERD,
                ModItems.LIGHT_POTTERY_SHERD,
                ModItems.MIGHT_POTTERY_SHERD,
                ModItems.FLIGHT_POTTERY_SHERD,
                ModItems.SIGHT_POTTERY_SHERD,
                ModItems.NIGHT_POTTERY_SHERD,
                ModItems.CHOMP_POTTERY_SHERD,
                ModItems.REVIVE_POTTERY_SHERD
        };

        for (Item item : sherds) {
            ITEM_TEXTURES.put(item, TexturedRenderLayers.getDecoratedPotPatternTextureId(ModPottery.fromSherd(item)));
        }
    }

    @Inject(method = "getTextureIdFromSherd", at = @At("HEAD"), cancellable = true)
    private static void getTextureIdFromSherd(Item item, CallbackInfoReturnable<SpriteIdentifier> cir) {
        SpriteIdentifier texture = ITEM_TEXTURES.get(item);
        if (texture != null) {
            cir.setReturnValue(texture);
            cir.cancel();
        }
    }
}
