package cf.witcheskitchen.common.crop;

import cf.witcheskitchen.api.crop.WKTallCropBlock;
import cf.witcheskitchen.common.registry.WKItems;
import cf.witcheskitchen.common.variants.AmaranthTypes;
import cf.witcheskitchen.common.variants.ConeflowerTypes;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.property.IntProperty;
import org.quiltmc.loader.api.minecraft.ClientOnly;

public class ConeflowerCropBlock extends WKTallCropBlock {
    public static final int MAX_AGE = 6;
    private final ConeflowerTypes type;

    public ConeflowerCropBlock(Settings settings) {
        this(settings, ConeflowerTypes.COMMON);
    }

    public ConeflowerCropBlock(Settings settings, ConeflowerTypes rarity) {
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
        return  WKItems.CONEFLOWER_SEEDS;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    public int doubleBlockAge() {
        return 2;
    }
}
