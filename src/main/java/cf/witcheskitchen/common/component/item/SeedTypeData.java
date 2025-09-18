package cf.witcheskitchen.common.component.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record SeedTypeData(
    String name, String type, int color
) {
    public static final Codec<SeedTypeData> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
                Codec.STRING
                    .fieldOf("name")
                    .forGetter(SeedTypeData::name),
                Codec.STRING
                    .fieldOf("type")
                    .forGetter(SeedTypeData::type),
                Codec.INT
                    .optionalFieldOf("color", 0xFFFFFF)
                    .forGetter(SeedTypeData::color)
            )
            .apply(instance, SeedTypeData::new)
    );

    public static final StreamCodec<FriendlyByteBuf, SeedTypeData> PACKET_CODEC = StreamCodec.composite(
        ByteBufCodecs.STRING_UTF8, SeedTypeData::name,
        ByteBufCodecs.STRING_UTF8, SeedTypeData::type,
        ByteBufCodecs.VAR_INT, SeedTypeData::color,
        SeedTypeData::new
    );

    public String getBlockId() {
        return this.name + "_" + this.type;
    }
}
