package cf.witcheskitchen.common.crop;

import cf.witcheskitchen.api.WKTallCropBlock;
import cf.witcheskitchen.common.registry.WKItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.property.IntProperty;

public class WormwoodCropBlock extends WKTallCropBlock {
    public static final int MAX_AGE = 6;
    private final Type type;

    public WormwoodCropBlock(Settings settings) {
        this(settings, Type.COMMON);
    }

    public WormwoodCropBlock(Settings settings, Type rarity) {
        super(settings);
        this.type = rarity;
    }

    @Override
    protected IntProperty getAgeRange() {
        return IntProperty.of("age", 0, MAX_AGE);
    }

    @Environment(EnvType.CLIENT)
    @Override
    protected ItemConvertible getSeedsItem() {
        return switch (this.type) {
            case COMMON -> WKItems.WORMWOOD_SEEDS;
        };
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    public int topLayerAge() {
        return 0;
    }

    public enum Type {
        COMMON,
    }
}
