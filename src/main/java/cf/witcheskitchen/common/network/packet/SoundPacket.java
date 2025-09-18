package cf.witcheskitchen.common.network.packet;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.api.event.network.CustomPacketCodecs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;

public record SoundPacket(
    BlockPos pos,
    ResourceLocation sound,
    SoundSource category
) implements CustomPacketPayload {
    public static final Type<SoundPacket> ID = new Type<>(WitchesKitchen.id("sound"));
    public static final TypeAndCodec<RegistryFriendlyByteBuf, SoundPacket> TYPE = new TypeAndCodec<>(ID, StreamCodec.composite(
        BlockPos.STREAM_CODEC, SoundPacket::pos,
        ResourceLocation.STREAM_CODEC, SoundPacket::sound,
        CustomPacketCodecs.SOUND_CATEGORY, SoundPacket::category,
        SoundPacket::new
    ));

    public static void send(ServerPlayer player, BlockPos pos, ResourceLocation sound, SoundSource category) {
        ServerPlayNetworking.send(player, new SoundPacket(pos, sound == null ? ResourceLocation.parse("") : sound, category));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
