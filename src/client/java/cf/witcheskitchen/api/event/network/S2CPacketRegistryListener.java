package cf.witcheskitchen.api.event.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public interface S2CPacketRegistryListener<T extends CustomPacketPayload> extends PacketRegistryListener<T> {
    @Environment(EnvType.CLIENT)
    void handle(T payload, ClientPlayNetworking.Context context);

    @Environment(EnvType.CLIENT)
    default void register() {
        ClientPlayNetworking.registerGlobalReceiver(this.type().type(), this::handle);
    }
}
