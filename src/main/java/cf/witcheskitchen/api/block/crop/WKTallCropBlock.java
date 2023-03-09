package cf.witcheskitchen.api.block.crop;

import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

/**
 * <p>
 * Represents a tall plant that has all the properties
 * of {@link WKCropBlock}.
 * </p>
 * <p>
 * <strong>WkTallCropBlock</strong> wraps a WKCropBlock and adds
 * all the features from vanilla {@link TallPlantBlock} to it.
 * </p>
 *
 * <p>
 * A TallCrop has the property to grow up to 2 blocks high.
 * This is represented with the {@link DoubleBlockHalf} enum property.
 * By default, the crop will spawn with age zero, and therefore occupying
 * only one block.
 * </p>
 * <p>
 * In order to implement your tall crop, you must define the following abstract
 * methods:
 * <br>
 * <br>
 * {@link #doubleBlockAge()}
 * Determines the age where the growing crop should be
 * considered as a "tall plant" and therefore start occupying a second block.
 * </p>
 *
 * <strong> IMPORTANT: </strong>
 * <p>
 * You <strong> MUST </strong> build the block properties overriding {@link #appendProperties(StateManager.Builder)}
 * </p>
 */
public abstract class WKTallCropBlock extends WKCropBlock {
    /**
     * A property that specifies whether a double height block is the upper or lower half.
     */
    public static final EnumProperty<DoubleBlockHalf> HALF = TallPlantBlock.HALF;

    public WKTallCropBlock(Settings settings) {
        super(settings);
    }

    /**
     * From {@link TallPlantBlock#onBreakInCreative(World, BlockPos, BlockState, PlayerEntity)}
     * Destroys a bottom half of a tall double block (such as a plant or a door)
     * without dropping an item when broken in creative.
     *
     * @see Block#onBreak(World, BlockPos, BlockState, PlayerEntity)
     */
    protected static void onBreakInCreative(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockPos blockPos;
        BlockState blockState;
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        if (doubleBlockHalf == DoubleBlockHalf.UPPER && (blockState = world.getBlockState(blockPos = pos.down())).isOf(state.getBlock()) && blockState.get(HALF) == DoubleBlockHalf.LOWER) {
            BlockState blockState2 = blockState.contains(Properties.WATERLOGGED) && blockState.get(Properties.WATERLOGGED) ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState();
            world.setBlockState(blockPos, blockState2, Block.NOTIFY_ALL | Block.SKIP_DROPS);
            world.syncWorldEvent(player, WorldEvents.BLOCK_BROKEN, blockPos, Block.getRawIdFromState(blockState));
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            if (state.get(getAgeProperty()) > doubleBlockAge()) {
                return getUpperShape()[state.get(getAgeProperty()) - doubleBlockAge() - 1];
            }
        } else {
            return getLowerShape()[state.get(getAgeProperty())];
        }
        return Block.createCuboidShape(0, 0, 0, 16, 16, 16);
    }

    /**
     * Override this to change shape
     *
     * @return
     */
    public abstract VoxelShape[] getLowerShape();

    /**
     * Override this to change shape
     *
     * @return
     */
    public abstract VoxelShape[] getUpperShape();

    /**
     * Age where the plant is going to begin
     * using a second block (upper part).
     *
     * @return Integer
     */
    public abstract int doubleBlockAge();

    public EnumProperty<DoubleBlockHalf> getHalfProperty() {
        return HALF;
    }

    @Override
    public long getRenderingSeed(BlockState state, BlockPos pos) {
        return MathHelper.hashCode(pos.getX(), pos.down(state.get(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ());
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient) {
            if (player.isCreative()) {
                WKTallCropBlock.onBreakInCreative(world, pos, state, player);
            }
        }
        super.onBreak(world, pos, state, player);
    }

    public BlockState withHalf(int age, DoubleBlockHalf half) {
        if (age < doubleBlockAge() && half == DoubleBlockHalf.UPPER) {
            throw new IllegalArgumentException("Upper part of the plant does not exists at age " + age);
        } else {
            return this.getDefaultState().with(getAgeProperty(), age).with(HALF, half);
        }
    }

    /**
     * Updates the crop age
     */
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random) {
        // Initial age
        final int age = this.getAge(state);
        // First we check if there is enough light
        if (world.getBaseLightLevel(pos, 0) >= 9) {
            // And the crop has not reached its last stage
            if (age < getMaxAge()) {
                // Vanilla algorithm to check the available moisture
                if (random.nextInt((int) (25.0f / (CropBlock.getAvailableMoisture(this, world, pos))) + 1) == 0) {
                    final int nextAge = age + 1;
                    world.setBlockState(pos, withHalf(nextAge, DoubleBlockHalf.LOWER), Block.NOTIFY_LISTENERS);
                    if (age >= doubleBlockAge()) {
                        world.setBlockState(pos.up(), withHalf(nextAge, DoubleBlockHalf.UPPER), Block.NOTIFY_LISTENERS);
                    }
                }
            }
        }
    }

    /**
     * BoneMeal Logic for WK tall crops.
     */
    @Override
    public void applyGrowth(World world, BlockPos pos, BlockState state) {
        final int maxAge;
        int age = this.getAge(state) + this.getGrowthAmount(world);
        if (age > (maxAge = this.getMaxAge())) {
            age = maxAge;
        }
        world.setBlockState(pos, this.withHalf(age, DoubleBlockHalf.LOWER), Block.NOTIFY_LISTENERS);
        if (age >= doubleBlockAge()) {
            world.setBlockState(pos.up(), this.withHalf(age, DoubleBlockHalf.UPPER), Block.NOTIFY_LISTENERS);
        }
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        final var blockPos = ctx.getBlockPos();
        final var world = ctx.getWorld();
        return blockPos.getY() < world.getTopY() - 1 && world.getBlockState(blockPos.up()).canReplace(ctx) ? super.getPlacementState(ctx) : null;
    }

    /**
     * Prevents the plant from being destroyed when a neighbor block triggers
     * the following method: {@link TallPlantBlock#getStateForNeighborUpdate(BlockState, Direction, BlockState, WorldAccess, BlockPos, BlockPos)}.
     * Do not override this unless you know what you are doing.
     */
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (state.get(HALF) != DoubleBlockHalf.UPPER) {
            return super.canPlaceAt(state, world, pos);
        } else {
            BlockState blockState = world.getBlockState(pos.down());
            return blockState.isOf(this) && blockState.get(HALF) == DoubleBlockHalf.LOWER;
        }
    }

    /**
     * Builds the properties of this crop
     */
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(getAgeProperty());
        builder.add(getHalfProperty());
    }
}
