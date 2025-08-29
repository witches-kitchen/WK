package cf.witcheskitchen.client.network.packet;

import cf.witcheskitchen.api.event.network.S2CPacketRegistryListener;
import cf.witcheskitchen.common.network.packet.SoundPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public class SoundPacketHandler implements S2CPacketRegistryListener<SoundPacket> {
    @Override
    public void handle(SoundPacket payload, ClientPlayNetworking.Context context) {
        final BlockPos pos = payload.pos();
        final ResourceLocation soundId = payload.sound();
        final SoundSource category = payload.category();
        context.client().execute(() -> {
            final ClientLevel world = context.client().level;
            if (world != null) {
                if (!soundId.toString().isEmpty()) {
                    final SoundEvent soundEvent = BuiltInRegistries.SOUND_EVENT.getValue(soundId);
                    world.playLocalSound(pos, soundEvent, category, 1.0F, 1.0F, false);
                }
            }
        });
    }

    @Override
    public CustomPacketPayload.TypeAndCodec<RegistryFriendlyByteBuf, SoundPacket> type() {
        return SoundPacket.TYPE;
    }
}
