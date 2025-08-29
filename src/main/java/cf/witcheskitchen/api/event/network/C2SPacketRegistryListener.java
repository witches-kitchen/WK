package cf.witcheskitchen.api.event.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public interface C2SPacketRegistryListener<T extends CustomPacketPayload> extends PacketRegistryListener<T> {
    void handle(T payload, ServerPlayNetworking.Context context);

    default void register() {
        PayloadTypeRegistry.playC2S().register(this.type().type(), this.type().codec());
        ServerPlayNetworking.registerGlobalReceiver(this.type().type(), this::handle);
    }
}