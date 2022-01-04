package cf.witcheskitchen.common.blocks.technical;

import cf.witcheskitchen.common.blocks.entity.WitchesOvenBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class WitchesOvenBlock extends WKDeviceBlock {

    public static final BooleanProperty LIT = Properties.LIT;
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    public static final VoxelShape SHAPE = VoxelShapes.union(
            createCuboidShape(0, 14, 0, 16, 16, 16),
            createCuboidShape(0, 2, 1, 16, 13, 16),
            createCuboidShape(0, 11, 0, 16, 13, 1),
            createCuboidShape(13, 2, 0, 16, 4, 1),
            createCuboidShape(0, 2, 0, 3, 4, 1),
            createCuboidShape(3, 2, 0, 13, 8, 1),
            createCuboidShape(5, 8, 0, 11, 9, 1),
            createCuboidShape(1, 13, 2, 15, 14, 15),
            createCuboidShape(13, 0, 1, 15, 2, 3),
            createCuboidShape(13, 0, 13, 15, 2, 15),
            createCuboidShape(1, 0, 13, 3, 2, 15),
            createCuboidShape(1, 0, 1, 3, 2, 3)
    );

    public WitchesOvenBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH).with(LIT, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(LIT, FACING);
    }


    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WitchesOvenBlockEntity(pos, state);
    }
}
