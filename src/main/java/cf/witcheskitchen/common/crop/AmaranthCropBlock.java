package cf.witcheskitchen.common.crop;

import cf.witcheskitchen.api.crop.WKTallCropBlock;
import cf.witcheskitchen.common.registry.WKItems;
import cf.witcheskitchen.common.variants.AmaranthTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.quiltmc.loader.api.minecraft.ClientOnly;

public class AmaranthCropBlock extends WKTallCropBlock {
    public static final VoxelShape[] LOWER_AGE_TO_SHAPE;
    public static final VoxelShape[] UPPER_AGE_TO_SHAPE;
    public static final int MAX_AGE = 6;
    private final AmaranthTypes type;

    public AmaranthCropBlock(Settings settings) {
        this(settings, AmaranthTypes.COMMON);
    }

    public AmaranthCropBlock(Settings settings, AmaranthTypes type) {
        super(settings);
        this.type = type;
        this.setDefaultState(this.getDefaultState().with(getAgeProperty(), 0).with(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public IntProperty getAgeProperty() {
        return IntProperty.of("age", 0 , MAX_AGE);
    }

    @ClientOnly
    @Override
    protected ItemConvertible getSeedsItem() {
        return  WKItems.AMARANTH_SEEDS;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    public int doubleBlockAge() {
        return 5;
    }

    @Override
    public VoxelShape[] getLowerShape() {
        return LOWER_AGE_TO_SHAPE;
    }

    @Override
    public VoxelShape[] getUpperShape() {
        return UPPER_AGE_TO_SHAPE;
    }

    static {
        LOWER_AGE_TO_SHAPE = new VoxelShape[]{
                Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
                Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
                Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
                Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
                Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0),
                Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0),
                Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
        };
        UPPER_AGE_TO_SHAPE = new VoxelShape[]{
                Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 3.0, 16.0),
                Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 3.0, 16.0)
        };
    }
}
