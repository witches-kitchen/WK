package cf.witcheskitchen.common.crop;

import cf.witcheskitchen.api.WKTallCropBlock;
import cf.witcheskitchen.common.registry.WKItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.property.IntProperty;

public class AmaranthCropBlock extends WKTallCropBlock {
    public static final int MAX_AGE = 6;
    private final AmaranthCropBlock.Type type;

    public AmaranthCropBlock(Settings settings) {
        this(settings, Type.COMMON);
    }

    public AmaranthCropBlock(Settings settings, AmaranthCropBlock.Type rarity) {
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
            case COMMON -> WKItems.AMARANTH_SEEDS;
            case SWEETBERRY -> WKItems.AMARANTH_SWEETBERRY_SEEDS;
            case TORCH -> WKItems.AMARANTH_TORCH_SEEDS;
            case SUNDEW -> WKItems.AMARANTH_SUNDEW_SEEDS;
            case CREEPER -> WKItems.AMARANTH_CREEPER_SEEDS;
            case VIRIDIAN -> WKItems.AMARANTH_VIRIDIAN_SEEDS;
            case GRISELIN -> WKItems.AMARANTH_GRISELIN_SEEDS;
            case CERISE -> WKItems.AMARANTH_CERISE_SEEDS;
            case DARK_PASSION -> WKItems.AMARANTH_DARK_PASSION_SEEDS;
            case FIREBIRD -> WKItems.AMARANTH_FIREBIRD_SEEDS;
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
        SWEETBERRY,
        TORCH,
        SUNDEW,
        CREEPER,
        VIRIDIAN,
        GRISELIN,
        CERISE,
        DARK_PASSION,
        FIREBIRD
    }
}
