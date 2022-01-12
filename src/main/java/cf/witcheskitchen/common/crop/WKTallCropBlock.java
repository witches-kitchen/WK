package cf.witcheskitchen.common.crop;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * This object gives you all properties of a tall plant
 * while keeping the stages of a crop block
 */
public abstract class WKTallCropBlock extends WKCropBlock {

    /**
     * A property that specifies whether a double height block is the upper or lower half.
     */
    public static final EnumProperty<DoubleBlockHalf> TALL_PLANT = TallPlantBlock.HALF;

    public WKTallCropBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(this.getAgeRange(), 0).with(TALL_PLANT, DoubleBlockHalf.LOWER));
    }

    /**
     * Destroys a bottom half of a tall double block (such as a plant or a door)
     * without dropping an item when broken in creative.
     *
     * @see Block#onBreak(World, BlockPos, BlockState, PlayerEntity)
     */
    public static void onBreakInCreative(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockPos blockPos;
        BlockState blockState;
        DoubleBlockHalf half = state.get(TALL_PLANT);
        if (half == DoubleBlockHalf.UPPER && (blockState = world.getBlockState(blockPos = pos.down())).isOf(state.getBlock()) && blockState.get(TALL_PLANT) == DoubleBlockHalf.LOWER) {
            final BlockState blockState2 = blockState.contains(Properties.WATERLOGGED) && blockState.get(Properties.WATERLOGGED) ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState();
            world.setBlockState(blockPos, blockState2, Block.NOTIFY_ALL | Block.SKIP_DROPS);
            world.syncWorldEvent(player, WorldEvents.BLOCK_BROKEN, blockPos, Block.getRawIdFromState(blockState));
        }
    }

    /**
     * Age where the plant is going to start
     * using a second texture (upper plant part)
     *
     * @return Integer (age)
     */
    public abstract int topLayerAge();

    @Override
    public void applyGrowth(World world, BlockPos pos, BlockState state) {
        // Stop if this is the second layer
        if (isUpperState(world, pos)) {
            return;
        }
        int i = this.getAge(state) + 1;
        final BlockState nextState = this.withAge(i);
        world.setBlockState(pos, nextState, Block.NOTIFY_LISTENERS);
        if (i >= 4) {
            world.setBlockState(pos.up(), nextState.with(TALL_PLANT, DoubleBlockHalf.UPPER), Block.NOTIFY_ALL);
        }
    }

    protected boolean isUpperState(World world, BlockPos pos) {
        return world.getBlockState(pos.down()).getBlock() == this;
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient) {
            if (player.isCreative()) {
                WKTallCropBlock.onBreakInCreative(world, pos, state, player);
            } else {
                // Meaning this block does not have an upper part
                if (this.getAge(state) < 4) {
                    Block.dropStacks(state, world, pos, null, player, player.getMainHandStack());
                }
            }
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
        return super.getDroppedStacks(state, builder);
    }

    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack) {
        super.onStacksDropped(state, world, pos, stack);
    }

    // Do not drop seed from here
    // We handle this onBreak()
    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        super.afterBreak(world, player, pos, Blocks.AIR.getDefaultState(), blockEntity, stack);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder); // Appends age property
        builder.add(TALL_PLANT);
    }

    /**
     * Break the upper block, if the state below is empty.
     * From {@link TallPlantBlock}.
     */
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf doubleBlockHalf = state.get(TALL_PLANT);
        if (!(direction.getAxis() != Direction.Axis.Y || doubleBlockHalf == DoubleBlockHalf.LOWER != (direction == Direction.UP) || neighborState.isOf(this) && neighborState.get(TALL_PLANT) != doubleBlockHalf)) {
            return Blocks.AIR.getDefaultState();
        }
        if (doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    /**
     * Checks that the plants has the required states to place it in the world.
     * From {@link TallPlantBlock}
     */
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (state.get(TALL_PLANT) == DoubleBlockHalf.UPPER) {
            BlockState downState = world.getBlockState(pos.down());
            return downState.isOf(this) && downState.get(TALL_PLANT) == DoubleBlockHalf.LOWER;
        }
        return super.canPlaceAt(state, world, pos);
    }

}
