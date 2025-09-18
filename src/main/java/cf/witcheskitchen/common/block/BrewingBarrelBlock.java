package cf.witcheskitchen.common.block;

import cf.witcheskitchen.api.block.WKBlock;
import cf.witcheskitchen.common.blockentity.BrewingBarrelBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BrewingBarrelBlock extends WKBlock implements SimpleWaterloggedBlock {

    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape NORTH_SHAPE = Shapes.or(box(13, 5, 1, 15, 11, 15), box(1, 5, 1, 3, 11, 15), box(11, 12, 1, 14, 14, 15), box(11, 2, 1, 14, 4, 15), box(11, 0, 3, 14, 2, 6), box(2, 0, 3, 5, 2, 6), box(11, 0, 10, 14, 2, 13), box(2, 0, 10, 5, 2, 13), box(12, 11, 1, 14, 12, 15), box(12, 4, 1, 14, 5, 15), box(5, 13, 1, 11, 15, 15), box(5, 1, 1, 11, 3, 15), box(3, 3, 2, 13, 13, 14), box(2, 12, 1, 5, 14, 15), box(2, 2, 1, 5, 4, 15), box(2, 11, 1, 4, 12, 15), box(2, 4, 1, 4, 5, 15));
    private static final VoxelShape EAST_SHAPE = Shapes.or(box(1, 5, 1, 15, 11, 3), box(1, 5, 13, 15, 11, 15), box(1, 12, 2, 15, 14, 5), box(1, 2, 2, 15, 4, 5), box(3, 0, 2, 6, 2, 5), box(3, 0, 11, 6, 2, 14), box(10, 0, 2, 13, 2, 5), box(10, 0, 11, 13, 2, 14), box(1, 11, 2, 15, 12, 4), box(1, 4, 2, 15, 5, 4), box(1, 13, 5, 15, 15, 11), box(1, 1, 5, 15, 3, 11), box(2, 3, 3, 14, 13, 13), box(1, 12, 11, 15, 14, 14), box(1, 2, 11, 15, 4, 14), box(1, 11, 12, 15, 12, 14), box(1, 4, 12, 15, 5, 14));

    public BrewingBarrelBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        final var blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof BrewingBarrelBlockEntity barrel) {
            if (state.getValue(FACING) == hit.getDirection()) {
                if (barrel.hasFinished()) {
                    Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), barrel.getRenderStack());
                    barrel.reset();
                    return InteractionResult.SUCCESS;
                }
                if (!stack.isEmpty()) {
                    if (stack.is(Items.GLASS_BOTTLE)) {
                        if (barrel.insertBottle(stack)) {
                            return InteractionResult.SUCCESS;
                        }
                    }
                    if (stack.is(Items.WATER_BUCKET)) {
                        if (barrel.fillBarrel(stack)) {
                            player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.BUCKET)));
                            return InteractionResult.SUCCESS;
                        }
                    }
                    if (stack.is(Items.BUCKET)) {
                        if (barrel.emptyBarrel(stack)) {
                            player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.WATER_BUCKET)));
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
                if (player.isShiftKeyDown() && !barrel.getRenderStack().isEmpty()) {
                    barrel.removeBottle(player);
                    return InteractionResult.SUCCESS;
                }
                // Open GUI
                return super.useItemOn(stack, state, world, pos, player, hand, hit);
            }
        }
        return InteractionResult.TRY_WITH_EMPTY_HAND;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getNearestLookingDirection().getOpposite()).setValue(BlockStateProperties.WATERLOGGED, ctx.getLevel().getFluidState(ctx.getClickedPos()).getType() == Fluids.WATER);
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel world, BlockPos pos, boolean moved) {
        final BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof BrewingBarrelBlockEntity barrel) {
            Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), barrel.getRenderStack());
        }
        super.affectNeighborsAfterRemoval(state, world, pos, moved);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        final Direction dir = state.getValue(FACING);
        return dir == Direction.NORTH || dir == Direction.SOUTH ? NORTH_SHAPE : EAST_SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BrewingBarrelBlockEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, BlockStateProperties.WATERLOGGED);
    }

    @SuppressWarnings("deprecation")
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

}
