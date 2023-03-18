package cf.witcheskitchen.api.ritual;

import cf.witcheskitchen.common.registry.WKBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import org.jetbrains.annotations.Nullable;

public class RitualCircle {
    public static final byte[][] small = {{0, 1, 1, 1, 0}, {1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}, {1, 0, 0, 0, 1}, {0, 1, 1, 1, 0},};
    public static final byte[][] medium = {{0, 0, 1, 1, 1, 1, 1, 0, 0}, {0, 1, 0, 0, 0, 0, 0, 1, 0}, {1, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 1}, {0, 1, 0, 0, 0, 0, 0, 1, 0}, {0, 0, 1, 1, 1, 1, 1, 0, 0}};
    public static final byte[][] large = {{0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0}, {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0}};
    public Size size;
    public Type type;

    public RitualCircle(Size size, Type type) {
        this.size = size;
        this.type = type;
    }

    @Nullable
    public static Size getSize(String string) {
        return Size.valueOf(string);
    }

    @Nullable
    public static Type getType(String string) {
        return Type.valueOf(string);
    }

    public String getSize() {
        return size.name();
    }

    public String getType() {
        return type.name();
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
