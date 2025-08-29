package cf.witcheskitchen.common.component.blockentity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record WitchesCauldronData(
        CompoundTag tankData,
        int ticksHeated,
        int color,
        boolean powered
) {
    public static final Codec<WitchesCauldronData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                            CompoundTag.CODEC
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

    public static final StreamCodec<RegistryFriendlyByteBuf, WitchesCauldronData> PACKET_CODEC = StreamCodec.composite(
            ByteBufCodecs.COMPOUND_TAG, WitchesCauldronData::tankData,
            ByteBufCodecs.VAR_INT, WitchesCauldronData::ticksHeated,
            ByteBufCodecs.VAR_INT, WitchesCauldronData::color,
            ByteBufCodecs.BOOL, WitchesCauldronData::powered,
            WitchesCauldronData::new
    );
}
