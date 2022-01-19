package cf.witcheskitchen.common.crop;

import cf.witcheskitchen.api.WKTallCropBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.property.IntProperty;

public class AmaranthCropBlock extends WKTallCropBlock {
    public AmaranthCropBlock(Settings settings) {
        super(settings);
    }

    @Override
    public IntProperty getAgeRange() {
        return null;
    }

    @Override
    public ItemConvertible getSeedsItem() {
        return null;
    }

    @Override
    public int getMaxAge() {
        return 0;
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
