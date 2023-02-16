package cf.witcheskitchen.common.block;

import cf.witcheskitchen.api.block.WKBlock;
import cf.witcheskitchen.common.blockentity.BrewingBarrelBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BrewingBarrelBlock extends WKBlock implements Waterloggable {

    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    private static final VoxelShape NORTH_SHAPE = VoxelShapes.union(createCuboidShape(13, 5, 1, 15, 11, 15), createCuboidShape(1, 5, 1, 3, 11, 15), createCuboidShape(11, 12, 1, 14, 14, 15), createCuboidShape(11, 2, 1, 14, 4, 15), createCuboidShape(11, 0, 3, 14, 2, 6), createCuboidShape(2, 0, 3, 5, 2, 6), createCuboidShape(11, 0, 10, 14, 2, 13), createCuboidShape(2, 0, 10, 5, 2, 13), createCuboidShape(12, 11, 1, 14, 12, 15), createCuboidShape(12, 4, 1, 14, 5, 15), createCuboidShape(5, 13, 1, 11, 15, 15), createCuboidShape(5, 1, 1, 11, 3, 15), createCuboidShape(3, 3, 2, 13, 13, 14), createCuboidShape(2, 12, 1, 5, 14, 15), createCuboidShape(2, 2, 1, 5, 4, 15), createCuboidShape(2, 11, 1, 4, 12, 15), createCuboidShape(2, 4, 1, 4, 5, 15));
    private static final VoxelShape EAST_SHAPE = VoxelShapes.union(createCuboidShape(1, 5, 1, 15, 11, 3), createCuboidShape(1, 5, 13, 15, 11, 15), createCuboidShape(1, 12, 2, 15, 14, 5), createCuboidShape(1, 2, 2, 15, 4, 5), createCuboidShape(3, 0, 2, 6, 2, 5), createCuboidShape(3, 0, 11, 6, 2, 14), createCuboidShape(10, 0, 2, 13, 2, 5), createCuboidShape(10, 0, 11, 13, 2, 14), createCuboidShape(1, 11, 2, 15, 12, 4), createCuboidShape(1, 4, 2, 15, 5, 4), createCuboidShape(1, 13, 5, 15, 15, 11), createCuboidShape(1, 1, 5, 15, 3, 11), createCuboidShape(2, 3, 3, 14, 13, 13), createCuboidShape(1, 12, 11, 15, 14, 14), createCuboidShape(1, 2, 11, 15, 4, 14), createCuboidShape(1, 11, 12, 15, 12, 14), createCuboidShape(1, 4, 12, 15, 5, 14));

    public BrewingBarrelBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(Properties.WATERLOGGED, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        final var blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof BrewingBarrelBlockEntity barrel) {
            if (state.get(FACING) == hit.getSide()) {
                if (barrel.hasFinished()) {
                    ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), ((BrewingBarrelBlockEntity) blockEntity).getRenderStack());
                    barrel.reset();
                    return ActionResult.SUCCESS;
                }
                final ItemStack stack = player.isCreative() ? player.getStackInHand(hand).copy() : player.getStackInHand(hand);
                if (!stack.isEmpty()) {
                    if (stack.isOf(Items.GLASS_BOTTLE)) {
                        if (barrel.insertBottle(stack)) {
                            return ActionResult.SUCCESS;
                        }
                    }
                    if (stack.isOf(Items.WATER_BUCKET)) {
                        if (barrel.fillBarrel(stack)) {
                            player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.BUCKET)));
                            return ActionResult.SUCCESS;
                        }
                    }
                    if (stack.isOf(Items.BUCKET)) {
                        if (barrel.emptyBarrel(stack)) {
                            player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(Items.WATER_BUCKET)));
                            return ActionResult.SUCCESS;
                        }
                    }
                }
                if (player.isSneaking() && !barrel.getRenderStack().isEmpty()) {
                    barrel.removeBottle(player);
                    return ActionResult.SUCCESS;
                }
                // Open GUI
                return super.onUse(state, world, pos, player, hand, hit);
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite()).with(Properties.WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            final BlockEntity entity = world.getBlockEntity(pos);
            if (entity instanceof BrewingBarrelBlockEntity barrel) {
                ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), barrel.getRenderStack());
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        final Direction dir = state.get(FACING);
        return dir == Direction.NORTH || dir == Direction.SOUTH ? NORTH_SHAPE : EAST_SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BrewingBarrelBlockEntity(pos, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, Properties.WATERLOGGED);
    }

    @SuppressWarnings("deprecation")
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

}
