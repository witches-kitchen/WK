package cf.witcheskitchen.api.block.plant;

import cf.witcheskitchen.api.block.crop.WKTallCropBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class WildTallPlantCropBlock extends TallPlantBlock {
    public static final VoxelShape[] UPPER_SHAPE;

    static {
        UPPER_SHAPE = new VoxelShape[]{
                Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
                Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
                Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
                Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
        };
    }

    private final int size;
    public WKTallCropBlock wkTallCropBlock;

    public WildTallPlantCropBlock(Settings settings, WKTallCropBlock wkTallCropBlock, int sizeInQuartersStartFromZero) {
        super(settings);
        this.wkTallCropBlock = wkTallCropBlock;
        this.size = MathHelper.clamp(sizeInQuartersStartFromZero, 0, 3);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            return UPPER_SHAPE[size];
        }
        return Block.createCuboidShape(0, 0, 0, 16, 16, 16);
    }
}
