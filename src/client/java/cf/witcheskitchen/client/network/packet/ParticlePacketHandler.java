package cf.witcheskitchen.client.network.packet;

import cf.witcheskitchen.api.event.network.S2CPacketRegistryListener;
import cf.witcheskitchen.common.network.packet.ParticlePacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class ParticlePacketHandler implements S2CPacketRegistryListener<ParticlePacket> {
    @Environment(EnvType.CLIENT)
    @Override
    public void handle(ParticlePacket payload, ClientPlayNetworking.Context context) {
        final BlockPos pos = payload.pos();
        final ResourceLocation particleId = payload.particle();
        final ResourceLocation soundId = payload.sound();
        final byte range = payload.range();
        context.client().execute(() -> {
            final ClientLevel world = context.client().level;
            final ParticleType<?> particle = BuiltInRegistries.PARTICLE_TYPE.getValue(particleId);
            if (world != null) {
                for (int i = 0; i < range; i++) {
                    world.addParticle((ParticleOptions) particle, pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D, 0.5D, 0.5D, 0.5D);
                }
                if (!soundId.toString().isEmpty()) {
                    final SoundEvent soundEvent = BuiltInRegistries.SOUND_EVENT.getValue(soundId);
                    world.playLocalSound(pos, soundEvent, SoundSource.NEUTRAL, 1.0F, 1.0F, false);
                }
            }
        });
    }

    @Override
    public CustomPacketPayload.TypeAndCodec<RegistryFriendlyByteBuf, ParticlePacket> type() {
        return ParticlePacket.TYPE;
    }
}
