package cf.witcheskitchen.client.network.packet;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.api.event.network.S2CPacketRegistryListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;

public class SoundPacket implements S2CPacketRegistryListener {
    private static final Identifier CHANNEL = new Identifier(WitchesKitchen.MODID, "sound_packet");

    public static void send(ServerPlayerEntity player, BlockPos pos, Identifier sound, SoundCategory category) {
        final PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(pos);
        buf.writeIdentifier(sound == null ? new Identifier("") : sound);
        buf.writeString(category.getName());
        ServerPlayNetworking.send(player, CHANNEL, buf);
    }

    @ClientOnly
    @Override
    public void handle(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        final BlockPos pos = buf.readBlockPos();
        final Identifier soundId = buf.readIdentifier();
        final SoundCategory category = SoundCategory.valueOf(buf.readString());
        client.execute(() -> {
            final ClientWorld world = client.world;
            if (world != null) {
                if (!soundId.toString().isEmpty()) {
                    final SoundEvent soundEvent = Registries.SOUND_EVENT.get(soundId);
                    world.playSound(pos, soundEvent, category, 1.0F, 1.0F, false);
                }
            }
        });
    }

    @Override
    public Identifier getChannelId() {
        return CHANNEL;
    }
}
