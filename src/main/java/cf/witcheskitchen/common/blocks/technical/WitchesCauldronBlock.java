package cf.witcheskitchen.common.blocks.technical;

import cf.witcheskitchen.api.WKBlockEntityProvider;
import cf.witcheskitchen.common.blocks.entity.WitchesCauldronBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class WitchesCauldronBlock extends WKBlockEntityProvider implements Waterloggable {

    public static final BooleanProperty HANGING = BooleanProperty.of("hanging");
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final IntProperty WATER_LEVELS = IntProperty.of("water_levels", 0, 5);

    public static final VoxelShape SHAPE = VoxelShapes.union(
            createCuboidShape(2, 9,1, 14, 11, 2),
            createCuboidShape(2,9,14, 14, 11, 15),
            createCuboidShape(14, 9, 2, 15, 11, 14),
            createCuboidShape(1, 9, 2, 2, 11, 14),
            createCuboidShape(2, 8, 13, 14, 9, 14),
            createCuboidShape(2, 8, 3, 3, 9, 13),
            createCuboidShape(13, 8, 3, 14, 9, 13),
            createCuboidShape(1, 2, 2, 3, 8, 14),
            createCuboidShape(2, 2, 13, 14, 8, 15),
            createCuboidShape(13, 2, 2, 15, 8, 14),
            createCuboidShape(2, 2, 1, 14, 8, 3),
            createCuboidShape(2, 1, 2, 14, 2, 14),
            createCuboidShape(3, 0, 3, 13, 1,13)
    );

    public WitchesCauldronBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH).with(Properties.WATERLOGGED, false).with(HANGING, false));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing()).with(Properties.WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(Properties.WATERLOGGED)) {
            return Fluids.WATER.getStill(false);
        } else {
            return super.getFluidState(state);
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        world.setBlockState(pos, state.with(HANGING, !world.getBlockState(pos.up()).isAir()), Block.NOTIFY_ALL); // Triggers Hanging state
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WitchesCauldronBlockEntity(pos, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING, WATER_LEVELS, HANGING, Properties.WATERLOGGED);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }
}
