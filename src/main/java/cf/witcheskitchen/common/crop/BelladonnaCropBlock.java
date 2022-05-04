package cf.witcheskitchen.common.crop;

import cf.witcheskitchen.api.crop.WKTallCropBlock;
import cf.witcheskitchen.common.registry.WKItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.property.IntProperty;

public class BelladonnaCropBlock extends WKTallCropBlock {

    public static final int MAX_AGE = 6;
    private static final IntProperty AGE = IntProperty.of("age", 0, MAX_AGE);
    private final Type type;

    public BelladonnaCropBlock(Settings settings) {
        this(settings, Type.COMMON);
    }

    public BelladonnaCropBlock(Settings settings, Type type) {
        super(settings);
        this.type = type;
        this.setDefaultState(this.getDefaultState().with(AGE, 0).with(HALF, DoubleBlockHalf.LOWER));
    }

    @Environment(EnvType.CLIENT)
    @Override
    protected ItemConvertible getSeedsItem() {
        return switch (this.type) {
            case COMMON -> WKItems.BELLADONNA_SEEDS;
            case GLOW -> WKItems.BELLADONNA_GLOW_SEEDS;
            case NOCTURNAL -> WKItems.BELLADONNA_NOCTURNAL_SEEDS;
        };
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    public IntProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int doubleBlockAge() {
        return 4;
    }

    public enum Type {
        COMMON,
        GLOW,
        NOCTURNAL
    }
}
