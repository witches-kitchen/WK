package cf.witcheskitchen.api.event.network;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.networking.api.PacketSender;

public interface C2SPacketRegistryListener {

    void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender);

    Identifier getChannelId();
}