package cf.witcheskitchen.api.block.crop;

import cf.witcheskitchen.common.component.WKComponents;
import cf.witcheskitchen.common.component.item.SeedTypeData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * <p>
 * A CropBlock represents a {@link Block} that can grow
 * and have different ages, which is represented as an {@link IntegerProperty}
 * with a certain range of numbers.
 * </p>
 *
 * <p>
 * The <strong>WKCropBlock</strong> class wraps the {@link CropBlock} and removes the
 * default property of seven ages and instead provides a way of specifying a certain
 * age range for the crop.
 * </p>
 *
 * <p>
 * In addition, this class provides abstraction for the age property
 * in order to setup the {@link StateDefinition.Builder} correctly
 * and some useful methods to determine the growing behaviour.
 * </p>
 *
 * <p>
 * When your Block is a {@link CropBlock}, you need to make sure +
 * you provide the right model variants in the <strong>JSON</strong> file, whose
 * property identifier is <strong>ALWAYS</strong> fixed to <strong>"age"</strong>
 * </p>
 *
 * <p>
 * As an example, you could consider the following json model:
 * {
 * "variants": {
 * "age=0": {
 * "model": "modid:block/custom_crop_stage0"
 * },
 * "age=1": {
 * "model": "minecraft:block/custom_crop_stage1"
 * }
 * }
 * It declares a CropBlock with 2 ages, which means it will only have 2 stages
 * and will only be considered as matured when the {@link BlockState} returns
 * an {@link IntegerProperty} of age 1.
 * </p>
 *
 * <p>
 * Do note that the age property of the crop always start at zero, no
 * matter what your max age is.
 * </p>
 *
 * <strong>IMPORTANT:</strong>
 * If your {@link IntegerProperty} property has a maxAge > 7
 * you'll have to add a different {@link VoxelShape} that the one
 * provided by the parent class {@link CropBlock#getShape(BlockState, BlockGetter, BlockPos, CollisionContext)},
 * this is due the limitation of 7 ages that it has.
 * </p>
 *
 * <p>
 * Due to the above mentioned this class is marked as abstract and cannot be instantiated
 * directly. You'll have to extend it and provide your own CropBlock implementation.
 * </p>
 */
public abstract class WKCropBlock extends CropBlock {

    public WKCropBlock(Properties settings) {
        super(settings);
    }

    /**
     * Max age of this crop
     *
     * @return Integer
     */
    @Override
    public abstract int getMaxAge();

    /**
     * Overrides the default age property
     *
     * @return IntProperty
     */
    @Override
    public abstract IntegerProperty getAgeProperty();


    /**
     * Builds the IntProperty.
     */
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(getAgeProperty());
    }

    // The following methods were overridden purely for documentation

    /**
     * This is a filter that determines whether the CropBlock can be placed
     * above another. By default, you can only place it above a {@link net.minecraft.world.level.block.Blocks#FARMLAND},
     * but feel free to override this if needed.
     */
    @Override
    protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
        return super.mayPlaceOn(floor, world, pos);
    }

    /**
     * @return The current age at the given crop BlockState
     */
    @Override
    public int getAge(BlockState state) {
        return super.getAge(state);
    }

    /**
     * Creates a new crop {@link BlockState} reference, with the {@link IntegerProperty}
     * already set.
     *
     * @param age {@link Integer} that represents the age of the new crop BlockState.
     * @return A new crop {@link BlockState}
     */
    @Override
    public BlockState getStateForAge(int age) {
        return super.getStateForAge(age);
    }

    /**
     * <p>
     * This method determines whether the crop is already at the max age
     * by doing a comparison between {@link WKCropBlock#getAgeProperty()}
     * and {@link WKCropBlock#getMaxAge()}.
     * </p>
     * It is used as a filter in different places, such as when the player
     * attempts to use a {@link net.minecraft.world.item.BoneMealItem} in the crop
     *
     * @return Whether the given crop is mature
     */
    @Override
    public boolean isMaxAge(BlockState state) {
        return super.isMaxAge(state);
    }

    /**
     * <p>
     * Determines whether the crop should randomly get
     * a "tick" code execution, by default it gets a
     * random tick as long as the crop is not matured.
     * </p>
     * <p>
     * This is triggered in {@link ServerLevel#tickChunk(LevelChunk, int)} and
     * if this returns false, {@link WKCropBlock#randomTick(BlockState, ServerLevel, BlockPos, RandomSource)}
     * will never get executed.
     * </p>
     */
    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return super.isRandomlyTicking(state);
    }

    /**
     * <p>
     * The logic within this method gets executed in a tick, but randomly
     * whenever the {@link ServerLevel#tickChunk(LevelChunk, int)} decides to do so.
     * </p>
     * <p>
     * This place is useful to update the {@link BlockState} of the crop, because it is
     * constantly listening for chunk updates.
     * </p>
     * <p>
     * By default it is set update the crop block if there is enough light level,
     * {@link WKCropBlock#isMaxAge(BlockState)} is false and they determined that there is
     * a good amount of available moisture.
     * </p>
     */
    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        super.randomTick(state, world, pos, random);
    }

    /**
     * Updates the crop {@link BlockState}, by checking {@link CropBlock#getAge(BlockState)} is <  {@link CropBlock#getMaxAge()}.
     * <strong>NOTE:</strong> THIS METHOD IS ONLY TRIGGERED BY {@link WKCropBlock#performBonemeal(ServerLevel, RandomSource, BlockPos, BlockState)}
     */
    @Override
    public void growCrops(Level world, BlockPos pos, BlockState state) {
        super.growCrops(world, pos, state);
    }


    /**
     * <p>
     * This method is a filter for {@link #performBonemeal(ServerLevel, RandomSource, BlockPos, BlockState)} and the BoneMeal grow method.
     * It is only used by {@link net.minecraft.world.item.BoneMealItem#growCrop(ItemStack, Level, BlockPos)},
     * although when extending this class it will also be triggered by {@link #performBonemeal(ServerLevel, RandomSource, BlockPos, BlockState)}.
     * </p>
     * It is always returning true by the parent class unless overridden.
     */
    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return super.isBonemealSuccess(world, random, pos, state);
    }

    /**
     * <p>
     * All what this method does is to call {@link WKCropBlock#growCrops(Level, BlockPos, BlockState)}.
     * <strong>IMPORTANT</strong>:
     * <p>
     * This method is part of {@link net.minecraft.world.level.block.BonemealableBlock} interface, and it's only triggered
     * in the following places
     * </p>
     * {@link net.minecraft.world.item.BoneMealItem#growCrop(ItemStack, Level, BlockPos)}
     * {@link Bee.BeeGrowCropGoal#tick()}
     */
    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        if (isBonemealSuccess(world, random, pos, state)) {
            super.performBonemeal(world, random, pos, state);
        }
    }

    /**
     * Gives you the context of the entity that collides with the block
     * This method only gets executed is the entity is colliding with the block.
     * See also {@link Entity#checkInsideBlocks()}
     */
    @Override
    protected void entityInside(BlockState state, Level world, BlockPos pos, Entity entity, InsideBlockEffectApplier handler) {
        super.entityInside(state, world, pos, entity, handler);
    }

    /**
     * Filter for {@link net.minecraft.world.level.block.VegetationBlock#updateShape(BlockState, Direction, BlockState, LevelAccessor, BlockPos, BlockPos)}
     * which destroys the crop if there is not enough light to place it.
     */
    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return super.canSurvive(state, world, pos);
    }

    /**
     * This is the item that is spawned when the player
     * hits the mouse middle button.
     * Triggered on {@link MinecraftClient#doItemPick()}.
     */
    @Override
    protected ItemLike getBaseSeedId() {
        return getSeedsItemStack().getItem();
    }

    @Override
    protected ItemStack getCloneItemStack(LevelReader world, BlockPos pos, BlockState state, boolean includeData) {
        return getSeedsItemStack();
    }

    protected abstract ItemStack getSeedsItemStack();

    public void getNextSeed(Level world, BlockPos pos, SeedTypeData data) {
        ItemStack itemStack2 = getSeedsItemStack();
        itemStack2.set(WKComponents.SEED_TYPE, data);
        Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), itemStack2);
    }
}