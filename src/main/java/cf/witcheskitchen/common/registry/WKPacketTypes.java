package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.WitchesKitchenConfig;
import cf.witcheskitchen.api.event.network.C2SPacketRegistryListener;
import cf.witcheskitchen.api.event.network.S2CPacketRegistryListener;
import cf.witcheskitchen.client.network.packet.ParticlePacketHandler;
import cf.witcheskitchen.client.network.packet.SplashParticlePacketHandler;
import net.fabricmc.api.EnvType;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;

import java.util.LinkedHashMap;
import java.util.Map;

public class WKPacketTypes {

    // Packets that are sent by the client and received by the server
    private static final Map<Identifier, C2SPacketRegistryListener> SERVER_PACKETS = new LinkedHashMap<>();
    // Packets that are sent by the server and received by the client
    private static final Map<Identifier, S2CPacketRegistryListener> CLIENT_PACKETS = new LinkedHashMap<>();

    public static final S2CPacketRegistryListener PARTICLE_PACKET_HANDLER = registerClientPacket(new ParticlePacketHandler());
    public static final S2CPacketRegistryListener SPLASH_PARTICLE_HANDLER = registerClientPacket(new SplashParticlePacketHandler());

    static {
        if (WitchesKitchenConfig.debugMode) {
            WitchesKitchen.LOGGER.info("Witches Kitchen Base Packet Handlers: Successfully Loaded");
        }
    }

    private static <T extends C2SPacketRegistryListener> T registerServerPacket(T handler) {
        SERVER_PACKETS.put(handler.getChannelId(), handler);
        return handler;
    }

    private static <T extends S2CPacketRegistryListener> T registerClientPacket(T handler) {
        CLIENT_PACKETS.put(handler.getChannelId(), handler);
        return handler;
    }

    public static void register(EnvType side) {
        switch (side) {
            case SERVER ->
                    SERVER_PACKETS.keySet().forEach(id -> ServerPlayNetworking.registerGlobalReceiver(id, SERVER_PACKETS.get(id)::handle));
            case CLIENT ->
                    CLIENT_PACKETS.keySet().forEach(id -> ClientPlayNetworking.registerGlobalReceiver(id, CLIENT_PACKETS.get(id)::handle));
        }
    }
}
