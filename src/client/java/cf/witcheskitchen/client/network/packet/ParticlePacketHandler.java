package cf.witcheskitchen.client.network.packet;

import cf.witcheskitchen.api.event.network.S2CPacketRegistryListener;
import cf.witcheskitchen.common.network.packet.ParticlePacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class ParticlePacketHandler implements S2CPacketRegistryListener<ParticlePacket> {
    @Environment(EnvType.CLIENT)
    @Override
    public void handle(ParticlePacket payload, ClientPlayNetworking.Context context) {
        final BlockPos pos = payload.pos();
        final Identifier particleId = payload.particle();
        final Identifier soundId = payload.sound();
        final byte range = payload.range();
        context.client().execute(() -> {
            final ClientWorld world = context.client().world;
            final ParticleType<?> particle = Registries.PARTICLE_TYPE.get(particleId);
            if (world != null) {
                for (int i = 0; i < range; i++) {
                    world.addParticleClient((ParticleEffect) particle, pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, 0.5D, 0.5D, 0.5D);
                }
                if (!soundId.toString().isEmpty()) {
                    final SoundEvent soundEvent = Registries.SOUND_EVENT.get(soundId);
                    world.playSoundAtBlockCenterClient(pos, soundEvent, SoundCategory.NEUTRAL, 1.0F, 1.0F, false);
                }
            }
        });
    }

    @Override
    public CustomPayload.Type<RegistryByteBuf, ParticlePacket> type() {
        return ParticlePacket.TYPE;
    }
}
