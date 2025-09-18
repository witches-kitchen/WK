package cf.witcheskitchen.api.ritual;

import cf.witcheskitchen.api.event.network.CustomPacketCodecs;
import cf.witcheskitchen.api.util.CodecUtils;
import cf.witcheskitchen.common.registry.WKBlocks;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class RitualCircle {
    public static final Codec<RitualCircle> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
                CodecUtils.createEnumCodec(Size::valueOf)
                    .fieldOf("size")
                    .forGetter(RitualCircle::getSize),
                CodecUtils.createEnumCodec(Type::valueOf)
                    .fieldOf("type")
                    .forGetter(RitualCircle::getType)
            )
            .apply(instance, RitualCircle::new)
    );

    public static final StreamCodec<FriendlyByteBuf, RitualCircle> PACKET_CODEC = StreamCodec.composite(
        CustomPacketCodecs.createEnumCodec(Size::valueOf), RitualCircle::getSize,
        CustomPacketCodecs.createEnumCodec(Type::valueOf), RitualCircle::getType,
        RitualCircle::new
    );

    public static final byte[][] small = {{0, 1, 1, 1, 0}, {1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}, {0, 1, 1, 1, 0},};
    public static final byte[][] medium = {{0, 0, 1, 1, 1, 1, 1, 0, 0}, {0, 1, 0, 0, 0, 0, 0, 1, 0}, {1, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 1}, {0, 1, 0, 0, 0, 0, 0, 1, 0}, {0, 0, 1, 1, 1, 1, 1, 0, 0}};
    public static final byte[][] large = {{0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0}, {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0}};
    public Size size;
    public Type type;

    public RitualCircle(Size size, Type type) {
        this.size = size;
        this.type = type;
    }

    public Size getSize() {
        return size;
    }

    public Type getType() {
        return type;
    }

    public enum Size {
        small(RitualCircle.small),
        medium(RitualCircle.medium),
        large(RitualCircle.large);

        Size(byte[][] size) {

        }
    }

    public enum Type {
        chalk(WKBlocks.GLYPH),
        salt(WKBlocks.SALT_BLOCK),
        candle(Blocks.CANDLE);

        Type(Block glyph) {
        }
    }
}
