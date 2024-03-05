package net.emilsg.archeologyplus.networking;

import net.emilsg.archeologyplus.ArcheologyPlus;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModMessages {
    public static final Identifier VERSION_HANDSHAKE_PACKET_ID = new Identifier(ArcheologyPlus.MOD_ID, "version_handshake");

    public static void registerHandshakePackets() {
        ServerPlayNetworking.registerGlobalReceiver(VERSION_HANDSHAKE_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            String clientVersion = buf.readString();

            if (!ArcheologyPlus.MOD_VERSION.equals(clientVersion)) {
                player.networkHandler.disconnect(Text.literal("Mismatched mod version! Please use Archeology Plus version " + ArcheologyPlus.MOD_VERSION + ". If you're using a Modpack please make sure it's the same version as the Server."));
            }
        });

    }
}
