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
        ITEM_TEXTURES.put(ModItems.LOADER_POTTERY_SHERD, TexturedRenderLayers.getDecoratedPotPatternTextureId(ModPottery.fromSherd(ModItems.LOADER_POTTERY_SHERD)));
        ITEM_TEXTURES.put(ModItems.MASTER_POTTERY_SHERD, TexturedRenderLayers.getDecoratedPotPatternTextureId(ModPottery.fromSherd(ModItems.MASTER_POTTERY_SHERD)));
        ITEM_TEXTURES.put(ModItems.MERCHANT_POTTERY_SHERD, TexturedRenderLayers.getDecoratedPotPatternTextureId(ModPottery.fromSherd(ModItems.MERCHANT_POTTERY_SHERD)));
        ITEM_TEXTURES.put(ModItems.HOP_POTTERY_SHERD, TexturedRenderLayers.getDecoratedPotPatternTextureId(ModPottery.fromSherd(ModItems.HOP_POTTERY_SHERD)));
        ITEM_TEXTURES.put(ModItems.FRIGHT_POTTERY_SHERD, TexturedRenderLayers.getDecoratedPotPatternTextureId(ModPottery.fromSherd(ModItems.FRIGHT_POTTERY_SHERD)));
        ITEM_TEXTURES.put(ModItems.LIGHT_POTTERY_SHERD, TexturedRenderLayers.getDecoratedPotPatternTextureId(ModPottery.fromSherd(ModItems.LIGHT_POTTERY_SHERD)));
        ITEM_TEXTURES.put(ModItems.MIGHT_POTTERY_SHERD, TexturedRenderLayers.getDecoratedPotPatternTextureId(ModPottery.fromSherd(ModItems.MIGHT_POTTERY_SHERD)));
        ITEM_TEXTURES.put(ModItems.FLIGHT_POTTERY_SHERD, TexturedRenderLayers.getDecoratedPotPatternTextureId(ModPottery.fromSherd(ModItems.FLIGHT_POTTERY_SHERD)));
        ITEM_TEXTURES.put(ModItems.SIGHT_POTTERY_SHERD, TexturedRenderLayers.getDecoratedPotPatternTextureId(ModPottery.fromSherd(ModItems.SIGHT_POTTERY_SHERD)));
        ITEM_TEXTURES.put(ModItems.NIGHT_POTTERY_SHERD, TexturedRenderLayers.getDecoratedPotPatternTextureId(ModPottery.fromSherd(ModItems.NIGHT_POTTERY_SHERD)));
        ITEM_TEXTURES.put(ModItems.CHOMP_POTTERY_SHERD, TexturedRenderLayers.getDecoratedPotPatternTextureId(ModPottery.fromSherd(ModItems.CHOMP_POTTERY_SHERD)));
        ITEM_TEXTURES.put(ModItems.REVIVE_POTTERY_SHERD, TexturedRenderLayers.getDecoratedPotPatternTextureId(ModPottery.fromSherd(ModItems.REVIVE_POTTERY_SHERD)));
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
