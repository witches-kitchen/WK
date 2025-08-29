package cf.witcheskitchen.api.event.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public interface PacketRegistryListener<T extends CustomPacketPayload> {
    CustomPacketPayload.TypeAndCodec<RegistryFriendlyByteBuf, T> type();
}
