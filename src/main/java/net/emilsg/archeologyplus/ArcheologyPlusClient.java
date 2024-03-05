package net.emilsg.archeologyplus;


import io.netty.buffer.Unpooled;
import net.emilsg.archeologyplus.networking.ModMessages;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.PacketByteBuf;

public class ArcheologyPlusClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        registerConnectionEvents();
    }

    private void registerConnectionEvents() {
        ClientPlayConnectionEvents.JOIN.register((handler, client, isConnected) -> {
            handler.sendPacket(ClientPlayNetworking.createC2SPacket(ModMessages.VERSION_HANDSHAKE_PACKET_ID, new PacketByteBuf(Unpooled.buffer()).writeString(ArcheologyPlus.MOD_VERSION)));
        });
    }

}
