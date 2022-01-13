package cf.witcheskitchen.common.crop;

import cf.witcheskitchen.common.registry.WKItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.property.IntProperty;

public class BelladonnaCropBlock extends WKTallCropBlock {

    public static final int MAX_AGE = 6;
    private final Type type;

    public BelladonnaCropBlock(Settings settings) {
        this(settings, Type.COMMON);
    }

    public BelladonnaCropBlock(Settings settings, Type rarity) {
        super(settings);
        this.type = rarity;
    }

    @Override
    public int topLayerAge() {
        return 4;
    }

    @Override
    protected IntProperty getAgeRange() {
        return IntProperty.of("age", 0, MAX_AGE);
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

    public enum Type {
        COMMON,
        GLOW,
        NOCTURNAL
    }
}
