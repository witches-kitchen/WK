package cf.witcheskitchen.common.component.blockentity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record WitchesCauldronData(
        NbtCompound tankData,
        int ticksHeated,
        int color,
        boolean powered
) {
    public static final Codec<WitchesCauldronData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                            NbtCompound.CODEC
                                    .fieldOf("tank")
                                    .forGetter(WitchesCauldronData::tankData),
                            Codec.INT
                                    .fieldOf("ticksHeated")
                                    .forGetter(WitchesCauldronData::ticksHeated),
                            Codec.INT
                                    .fieldOf("color")
                                    .forGetter(WitchesCauldronData::color),
                            Codec.BOOL
                                    .fieldOf("powered")
                                    .forGetter(WitchesCauldronData::powered)
                    )
                    .apply(instance, WitchesCauldronData::new)
    );

    public static final PacketCodec<RegistryByteBuf, WitchesCauldronData> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.NBT_COMPOUND, WitchesCauldronData::tankData,
            PacketCodecs.VAR_INT, WitchesCauldronData::ticksHeated,
            PacketCodecs.VAR_INT, WitchesCauldronData::color,
            PacketCodecs.BOOLEAN, WitchesCauldronData::powered,
            WitchesCauldronData::new
    );
}
