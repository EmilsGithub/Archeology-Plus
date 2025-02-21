package net.emilsg.archeologyplus;


import io.netty.buffer.Unpooled;
import net.emilsg.archeologyplus.networking.ModMessages;
import net.emilsg.archeologyplus.register.blocks.custom.ICutOut;
import net.emilsg.archeologyplus.register.entities.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;

public class ArcheologyPlusClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        registerConnectionEvents();

        for (Block block : Registries.BLOCK) {
            if (block instanceof ICutOut) BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
        }

        registerRenderers();
    }

    private void registerConnectionEvents() {
        ClientPlayConnectionEvents.JOIN.register((handler, client, isConnected) -> {
            handler.sendPacket(ClientPlayNetworking.createC2SPacket(ModMessages.VERSION_HANDSHAKE_PACKET_ID, new PacketByteBuf(Unpooled.buffer()).writeString(ArcheologyPlus.MOD_VERSION)));
        });
    }

    private void registerRenderers() {
        EntityRendererRegistry.register(ModEntities.THROWN_ROPE_PROJECTILE, FlyingItemEntityRenderer::new);
    }

}
