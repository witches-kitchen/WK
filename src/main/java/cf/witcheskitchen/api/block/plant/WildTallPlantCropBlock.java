package cf.witcheskitchen.api.block.plant;

import cf.witcheskitchen.api.block.crop.WKTallCropBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WildTallPlantCropBlock extends DoublePlantBlock {
    public static final VoxelShape[] UPPER_SHAPE;

    static {
        UPPER_SHAPE = new VoxelShape[]{
                Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
                Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
                Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
                Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
        };
    }

    private final int size;
    public WKTallCropBlock wkTallCropBlock;

    public WildTallPlantCropBlock(Properties settings, WKTallCropBlock wkTallCropBlock, int sizeInQuartersStartFromZero) {
        super(settings);
        this.wkTallCropBlock = wkTallCropBlock;
        this.size = Mth.clamp(sizeInQuartersStartFromZero, 0, 3);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            return UPPER_SHAPE[size];
        }
        return Block.box(0, 0, 0, 16, 16, 16);
    }
}
