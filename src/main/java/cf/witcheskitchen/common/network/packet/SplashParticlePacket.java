package cf.witcheskitchen.common.network.packet;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.api.event.network.CustomPacketCodecs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public record SplashParticlePacket(
        BlockPos pos,
        Vec3 rgb,
        Vec3 offset,
        byte amount
) implements CustomPacketPayload {
    public static final Type<SplashParticlePacket> ID = new Type<>(WitchesKitchen.id("splash_particle"));
    public static final TypeAndCodec<RegistryFriendlyByteBuf, SplashParticlePacket> TYPE = new TypeAndCodec<>(ID, StreamCodec.composite(
            BlockPos.STREAM_CODEC, SplashParticlePacket::pos,
            CustomPacketCodecs.VECTOR3D, SplashParticlePacket::rgb,
            CustomPacketCodecs.VECTOR3D, SplashParticlePacket::offset,
            ByteBufCodecs.BYTE, SplashParticlePacket::amount,
            SplashParticlePacket::new
    ));

    public static void send(ServerPlayer player, BlockPos pos, double r, double g, double b) {
        send(player, pos, r, g, b, 0, 0, 0, (byte) 1);
    }

    public static void send(ServerPlayer player, BlockPos pos, double r, double g, double b, double offsetX, double offsetY, double offsetZ, byte amount) {
        ServerPlayNetworking.send(player, new SplashParticlePacket(pos, new Vec3(r, g, b), new Vec3(offsetX, offsetY, offsetZ), amount));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
