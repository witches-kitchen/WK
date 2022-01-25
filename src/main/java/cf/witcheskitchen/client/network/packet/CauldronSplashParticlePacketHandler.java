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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class CauldronSplashParticlePacketHandler implements S2CPacketRegistryListener {

    private static final Identifier CHANNEL = new Identifier(WK.MODID, "cauldron_splash_particle");

    public static void send(PlayerEntity player, BlockPos pos) {
        final PacketByteBuf data = PacketByteBufs.create();
        ServerPlayNetworking.send((ServerPlayerEntity) player, CHANNEL, data);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void handle(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        final BlockPos pos = buf.readBlockPos();
        client.execute(() -> {
            final ClientWorld world = client.world;
            if (world != null) {
                for (int i = 0; i < 8; i++) {
                    world.addParticle(ParticleTypes.SPLASH, pos.getX() + 0.5D, pos.getY() + 0.8D, pos.getZ() + 0.5D, 0.5D, 0.5D, 8);
                }
                world.playSound(pos, SoundEvents.ENTITY_PLAYER_SPLASH, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }
        });
    }

    @Override
    public Identifier getChannelId() {
        return CHANNEL;
    }
}
