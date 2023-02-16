package cf.witcheskitchen.common.crop;

import cf.witcheskitchen.api.crop.WKTallCropBlock;
import cf.witcheskitchen.common.registry.WKItems;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.property.IntProperty;
import org.quiltmc.loader.api.minecraft.ClientOnly;

public class WormwoodCropBlock extends WKTallCropBlock {
    public static final int MAX_AGE = 7;
    private final Type type;

    public WormwoodCropBlock(Settings settings) {
        this(settings, Type.COMMON);
    }

    public WormwoodCropBlock(Settings settings, Type rarity) {
        super(settings);
        this.type = rarity;
    }

    @Override
    public IntProperty getAgeProperty() {
        return IntProperty.of("age", 0, MAX_AGE);
    }

    @ClientOnly
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
    public int doubleBlockAge() {
        return 3;
    }

    public enum Type {
        COMMON,
    }
}
