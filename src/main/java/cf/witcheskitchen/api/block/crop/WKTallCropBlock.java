package cf.witcheskitchen.api.block.crop;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

/**
 * <p>
 * Represents a tall plant that has all the properties
 * of {@link WKCropBlock}.
 * </p>
 * <p>
 * <strong>WkTallCropBlock</strong> wraps a WKCropBlock and adds
 * all the features from vanilla {@link DoublePlantBlock} to it.
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
 * You <strong> MUST </strong> build the block properties overriding {@link #createBlockStateDefinition(StateDefinition.Builder)}
 * </p>
 */
public abstract class WKTallCropBlock extends WKCropBlock {
    /**
     * A property that specifies whether a double height block is the upper or lower half.
     */
    public static final EnumProperty<DoubleBlockHalf> HALF = DoublePlantBlock.HALF;

    public WKTallCropBlock(Properties settings) {
        super(settings);
    }

    /**
     * From {@link DoublePlantBlock#preventDropFromBottomPart(Level, BlockPos, BlockState, Player)}
     * Destroys a bottom half of a tall double block (such as a plant or a door)
     * without dropping an item when broken in creative.
     *
     * @see Block#playerWillDestroy(Level, BlockPos, BlockState, Player)
     */
    protected static void onBreakInCreative(Level world, BlockPos pos, BlockState state, Player player) {
        BlockPos blockPos;
        BlockState blockState;
        DoubleBlockHalf doubleBlockHalf = state.getValue(HALF);
        if (doubleBlockHalf == DoubleBlockHalf.UPPER && (blockState = world.getBlockState(blockPos = pos.below())).is(state.getBlock()) && blockState.getValue(HALF) == DoubleBlockHalf.LOWER) {
            BlockState blockState2 = blockState.hasProperty(BlockStateProperties.WATERLOGGED) && blockState.getValue(BlockStateProperties.WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
            world.setBlock(blockPos, blockState2, Block.UPDATE_ALL | Block.UPDATE_SUPPRESS_DROPS);
            world.levelEvent(player, LevelEvent.PARTICLES_DESTROY_BLOCK, blockPos, Block.getId(blockState));
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            if (state.getValue(getAgeProperty()) > doubleBlockAge()) {
                return getUpperShape()[state.getValue(getAgeProperty()) - doubleBlockAge() - 1];
            }
        } else {
            return getLowerShape()[state.getValue(getAgeProperty())];
        }
        return Block.box(0, 0, 0, 16, 16, 16);
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
    public long getSeed(BlockState state, BlockPos pos) {
        return Mth.getSeed(pos.getX(), pos.below(state.getValue(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ());
    }

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        if (!world.isClientSide) {
            if (player.isCreative()) {
                WKTallCropBlock.onBreakInCreative(world, pos, state, player);
            }
        }
        return super.playerWillDestroy(world, pos, state, player);
    }

    public BlockState withHalf(int age, DoubleBlockHalf half) {
        if (age < doubleBlockAge() && half == DoubleBlockHalf.UPPER) {
            throw new IllegalArgumentException("Upper part of the plant does not exists at age " + age);
        } else {
            return this.defaultBlockState().setValue(getAgeProperty(), age).setValue(HALF, half);
        }
    }

    /**
     * Updates the crop age
     */
    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        // Initial age
        final int age = this.getAge(state);
        // First we check if there is enough light
        if (world.getRawBrightness(pos, 0) >= 9) {
            // And the crop has not reached its last stage
            if (age < getMaxAge()) {
                // Vanilla algorithm to check the available moisture
                if (random.nextInt((int) (25.0f / (CropBlock.getGrowthSpeed(this, world, pos))) + 1) == 0) {
                    final int nextAge = age + 1;
                    world.setBlock(pos, withHalf(nextAge, DoubleBlockHalf.LOWER), Block.UPDATE_CLIENTS);
                    if (age >= doubleBlockAge()) {
                        world.setBlock(pos.above(), withHalf(nextAge, DoubleBlockHalf.UPPER), Block.UPDATE_CLIENTS);
                    }
                }
            }
        }
    }

    /**
     * BoneMeal Logic for WK tall crops.
     */
    @Override
    public void growCrops(Level world, BlockPos pos, BlockState state) {
        final int maxAge;
        int age = this.getAge(state) + this.getBonemealAgeIncrease(world);
        if (age > (maxAge = this.getMaxAge())) {
            age = maxAge;
        }
        world.setBlock(pos, this.withHalf(age, DoubleBlockHalf.LOWER), Block.UPDATE_CLIENTS);
        if (age >= doubleBlockAge()) {
            world.setBlock(pos.above(), this.withHalf(age, DoubleBlockHalf.UPPER), Block.UPDATE_CLIENTS);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        final var blockPos = ctx.getClickedPos();
        final var world = ctx.getLevel();
        return blockPos.getY() < world.getMaxY() - 1 && world.getBlockState(blockPos.above()).canBeReplaced(ctx) ? super.getStateForPlacement(ctx) : null;
    }

    /**
     * Prevents the plant from being destroyed when a neighbor block triggers
     * the following method: {@link DoublePlantBlock#updateShape(BlockState, LevelReader, ScheduledTickAccess, BlockPos, Direction, BlockPos, BlockState, RandomSource)}.
     * Do not override this unless you know what you are doing.
     */
    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        if (state.getValue(HALF) != DoubleBlockHalf.UPPER) {
            return super.canSurvive(state, world, pos);
        } else {
            BlockState blockState = world.getBlockState(pos.below());
            return blockState.is(this) && blockState.getValue(HALF) == DoubleBlockHalf.LOWER;
        }
    }

    /**
     * Builds the properties of this crop
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(getAgeProperty());
        builder.add(getHalfProperty());
    }
}
