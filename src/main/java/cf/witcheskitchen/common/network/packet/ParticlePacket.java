package cf.witcheskitchen.common.network.packet;

import cf.witcheskitchen.WitchesKitchen;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

public record ParticlePacket(
        BlockPos pos,
        ResourceLocation particle,
        ResourceLocation sound,
        byte range
) implements CustomPacketPayload {
    public static final Type<ParticlePacket> ID = new Type<>(WitchesKitchen.id("particle"));
    public static final TypeAndCodec<RegistryFriendlyByteBuf, ParticlePacket> TYPE = new TypeAndCodec<>(ID, StreamCodec.composite(
            BlockPos.STREAM_CODEC, ParticlePacket::pos,
            ResourceLocation.STREAM_CODEC, ParticlePacket::particle,
            ResourceLocation.STREAM_CODEC, ParticlePacket::sound,
            ByteBufCodecs.BYTE, ParticlePacket::range,
            ParticlePacket::new
    ));

    public static void send(ServerPlayer player, final BlockPos pos, final ResourceLocation particle, final byte range) {
        send(player, pos, particle, null, range);
    }

    public static void send(ServerPlayer player, final BlockPos pos, final ResourceLocation particle, final @Nullable ResourceLocation sound, final byte range) {
        ServerPlayNetworking.send(player, new ParticlePacket(pos, particle, sound == null ? ResourceLocation.parse("") : sound, range));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
