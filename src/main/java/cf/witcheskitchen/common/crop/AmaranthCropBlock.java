package cf.witcheskitchen.common.crop;

import cf.witcheskitchen.api.crop.WKTallCropBlock;
import cf.witcheskitchen.common.registry.WKItems;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.property.IntProperty;
import org.quiltmc.loader.api.minecraft.ClientOnly;

public class AmaranthCropBlock extends WKTallCropBlock {
    public static final int MAX_AGE = 6;
    private final Type type;

    public AmaranthCropBlock(Settings settings) {
        this(settings, Type.COMMON);
    }



    public AmaranthCropBlock(Settings settings, Type rarity) {
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
    public int doubleBlockAge() {
        return 2;
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
