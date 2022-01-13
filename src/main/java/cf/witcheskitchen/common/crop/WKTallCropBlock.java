package cf.witcheskitchen.common.crop;


import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

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
        if (i >= topLayerAge()) {
            world.setBlockState(pos.up(), nextState.with(TALL_PLANT, DoubleBlockHalf.UPPER), Block.NOTIFY_ALL);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        // Minecraft check for random plant tick
        if (world.getBaseLightLevel(pos, 0) >= 9 && this.getAge(state) < this.getMaxAge() && random.nextInt((int)(25.0f / CropBlock.getAvailableMoisture(this, world, pos)) + 1) == 0) {
            this.applyGrowth(world, pos, state);
        }
    }

    protected boolean isUpperState(World world, BlockPos pos) {
        return world.getBlockState(pos.down()).getBlock() == this;
    }


    // Do not want to drop loot twice (upper plant and lower plant)
    // Instead if the upper part exists, we skip the drop.
    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        final BlockState drop = this.getAge(state) >= this.topLayerAge() ? Blocks.AIR.getDefaultState() : state;
        super.afterBreak(world, player, pos, drop, blockEntity, stack);
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
