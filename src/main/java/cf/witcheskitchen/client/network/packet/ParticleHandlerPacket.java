package cf.witcheskitchen.client.network.packet;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.api.event.network.S2CPacketRegistryListener;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

public class ParticleHandlerPacket implements S2CPacketRegistryListener {

    private static final Identifier CHANNEL = new Identifier(WK.MODID, "particle_packet_handler");

    public static void send(ServerPlayerEntity player, final BlockPos pos, final Identifier particle, final byte range) {
        send(player, pos, particle, null, range);
    }

    public static void send(ServerPlayerEntity player, final BlockPos pos, final Identifier particle, final @Nullable Identifier sound, final byte range) {
        final PacketByteBuf data = PacketByteBufs.create();
        data.writeBlockPos(pos);
        data.writeIdentifier(particle);
        data.writeIdentifier(sound == null ? new Identifier("") : sound);
        data.writeByte(range);
        ServerPlayNetworking.send(player, CHANNEL, data);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void handle(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        final BlockPos pos = buf.readBlockPos();
        final Identifier particleId = buf.readIdentifier();
        final Identifier soundId = buf.readIdentifier();
        final byte range = buf.readByte();
        client.execute(() -> {
            final ClientWorld world = client.world;
            final ParticleType<?> particle =  Registry.PARTICLE_TYPE.get(particleId);
            if (world != null) {
                for (int i = 0; i < range; i++) {
                    world.addParticle((ParticleEffect) particle, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 0.5D, 0.5D, 0.5D);
                }
                if (!soundId.toString().isEmpty()) {
                    final SoundEvent soundEvent = Registry.SOUND_EVENT.get(soundId);
                    world.playSound(pos, soundEvent, SoundCategory.NEUTRAL, 1.0F, 1.0F, false);
                }
            }
        });
    }

    @Override
    public Identifier getChannelId() {
        return CHANNEL;
    }
}
