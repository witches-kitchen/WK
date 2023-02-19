package cf.witcheskitchen.common.crop;

import cf.witcheskitchen.api.crop.WKTallCropBlock;
import cf.witcheskitchen.common.registry.WKItems;
import cf.witcheskitchen.common.variants.AmaranthTypes;
import cf.witcheskitchen.common.variants.ConeflowerTypes;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.shape.VoxelShape;
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
        this.setDefaultState(this.getDefaultState().with(getAgeProperty(), 0).with(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public VoxelShape[] getLowerShape() {
        return AmaranthCropBlock.LOWER_AGE_TO_SHAPE;
    }

    @Override
    public VoxelShape[] getUpperShape() {
        return AmaranthCropBlock.UPPER_AGE_TO_SHAPE;
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
