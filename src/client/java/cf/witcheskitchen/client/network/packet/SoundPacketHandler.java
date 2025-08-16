package cf.witcheskitchen.client.network.packet;

import cf.witcheskitchen.api.event.network.S2CPacketRegistryListener;
import cf.witcheskitchen.common.network.packet.SoundPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class SoundPacketHandler implements S2CPacketRegistryListener<SoundPacket> {
    @Override
    public void handle(SoundPacket payload, ClientPlayNetworking.Context context) {
        final BlockPos pos = payload.pos();
        final Identifier soundId = payload.sound();
        final SoundCategory category = payload.category();
        context.client().execute(() -> {
            final ClientWorld world = context.client().world;
            if (world != null) {
                if (!soundId.toString().isEmpty()) {
                    final SoundEvent soundEvent = Registries.SOUND_EVENT.get(soundId);
                    world.playSoundAtBlockCenterClient(pos, soundEvent, category, 1.0F, 1.0F, false);
                }
            }
        });
    }

    @Override
    public CustomPayload.Type<RegistryByteBuf, SoundPacket> type() {
        return SoundPacket.TYPE;
    }
}
