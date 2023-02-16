package cf.witcheskitchen.api.event.network;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.networking.api.PacketSender;

public interface S2CPacketRegistryListener {

    @ClientOnly
    void handle(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender);

    Identifier getChannelId();
}