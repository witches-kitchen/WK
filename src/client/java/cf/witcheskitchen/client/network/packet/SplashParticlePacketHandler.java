package cf.witcheskitchen.client.network.packet;

import cf.witcheskitchen.api.event.network.S2CPacketRegistryListener;
import cf.witcheskitchen.common.network.packet.SplashParticlePacket;
import cf.witcheskitchen.common.registry.WKParticleTypes;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;

public class SplashParticlePacketHandler implements S2CPacketRegistryListener<SplashParticlePacket> {
    @Override
    public void handle(SplashParticlePacket payload, ClientPlayNetworking.Context context) {
        final BlockPos pos = payload.pos();
        final double r = payload.rgb().x;
        final double g = payload.rgb().y;
        final double b = payload.rgb().z;
        final double offsetX = payload.offset().x;
        final double offsetY = payload.offset().y;
        final double offsetZ = payload.offset().z;
        final byte i = payload.amount();
        context.client().execute(() -> {
            final ClientWorld world = context.client().world;
            if (world != null) {
                for (int j = 0; j < i; j++) {
                    world.addParticleClient((ParticleEffect) WKParticleTypes.SPLASH, pos.getX() + offsetX, pos.getY() + offsetY, pos.getZ() + offsetZ, r, g, b);
                }
            }
        });
    }

    @Override
    public CustomPayload.Type<RegistryByteBuf, SplashParticlePacket> type() {
        return SplashParticlePacket.TYPE;
    }
}
