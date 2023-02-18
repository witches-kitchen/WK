package cf.witcheskitchen.client.network.packet;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.api.event.network.S2CPacketRegistryListener;
import cf.witcheskitchen.common.registry.WKParticleTypes;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;

public class SplashParticlePacketHandler implements S2CPacketRegistryListener {

    private static final Identifier SPLASH_PARTICLE_CHANNEL = new Identifier(WitchesKitchen.MODID, "splash_particle");

    public static void send(ServerPlayerEntity player, BlockPos pos, double r, double g, double b) {
        send(player, pos, r, g, b, 0, 0, 0, (byte) 1);
    }

    public static void send(ServerPlayerEntity player, BlockPos pos, double r, double g, double b, double offsetX, double offsetY, double offsetZ, byte amount) {
        PacketByteBuf data = PacketByteBufs.create();
        data.writeBlockPos(pos);
        data.writeDouble(r);
        data.writeDouble(g);
        data.writeDouble(b);
        data.writeDouble(offsetX);
        data.writeDouble(offsetY);
        data.writeDouble(offsetZ);
        data.writeByte(amount);
        ServerPlayNetworking.send(player, SPLASH_PARTICLE_CHANNEL, data);
    }

    @Override
    public void handle(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        final BlockPos pos = buf.readBlockPos();
        final double r = buf.readDouble();
        final double g = buf.readDouble();
        final double b = buf.readDouble();
        final double offsetX = buf.readDouble();
        final double offsetY = buf.readDouble();
        final double offsetZ = buf.readDouble();
        final byte i = buf.readByte();
        client.execute(() -> {
            final ClientWorld world = client.world;
            if (world != null) {
                for (int j = 0; j < i; j++) {
                    world.addParticle((ParticleEffect) WKParticleTypes.SPLASH, pos.getX() + offsetX, pos.getY() + offsetY, pos.getZ() + offsetZ, r, g, b);
                }
            }
        });
    }

    @Override
    public Identifier getChannelId() {
        return SPLASH_PARTICLE_CHANNEL;
    }
}
