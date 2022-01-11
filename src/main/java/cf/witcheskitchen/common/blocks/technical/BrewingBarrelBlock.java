package cf.witcheskitchen.common.blocks.technical;

import cf.witcheskitchen.common.blocks.entity.BrewingBarrelBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class BrewingBarrelBlock extends WKDeviceBlock {

    private static final VoxelShape SHAPE = VoxelShapes.union(
            createCuboidShape(13, 5, 1, 15, 11, 15),
            createCuboidShape(1, 5, 1, 3, 11, 15),
            createCuboidShape(11, 12, 1, 14, 14, 15),
            createCuboidShape(11, 2, 1, 14, 4, 15),
            createCuboidShape(11, 0, 3, 14, 2, 6),
            createCuboidShape(2, 0, 3, 5, 2, 6),
            createCuboidShape(11, 0, 10, 14, 2, 13),
            createCuboidShape(2, 0, 10, 5, 2, 13),
            createCuboidShape(12, 11, 1, 14, 12, 15),
            createCuboidShape(12, 4, 1, 14, 5, 15),
            createCuboidShape(5, 13, 1, 11, 15, 15),
            createCuboidShape(5, 1, 1, 11, 3, 15),
            createCuboidShape(3, 3, 2, 13, 13, 14),
            createCuboidShape(7, 4, 1, 9, 6, 2),
           // createCuboidShape(7.5, 4, 0, 8.5, 8, 1),
            createCuboidShape(2, 12, 1, 5, 14, 15),
            createCuboidShape(2, 2, 1, 5, 4, 15),
            createCuboidShape(2, 11, 1, 4, 12, 15),
            createCuboidShape(2, 4, 1, 4, 5, 15)
    );

    public BrewingBarrelBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BrewingBarrelBlockEntity(pos, state);
    }
}
