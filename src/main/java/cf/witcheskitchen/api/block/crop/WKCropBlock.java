package cf.witcheskitchen.api.block.crop;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.WorldChunk;

/**
 * <p>
 * A CropBlock represents a {@link Block} that can grow
 * and have different ages, which is represented as an {@link IntProperty}
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
 * in order to setup the {@link StateManager.Builder} correctly
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
 * an {@link IntProperty} of age 1.
 * </p>
 *
 * <p>
 * Do note that the age property of the crop always start at zero, no
 * matter what your max age is.
 * </p>
 *
 * <strong>IMPORTANT:</strong>
 * If your {@link IntProperty} property has a maxAge > 7
 * you'll have to add a different {@link VoxelShape} that the one
 * provided by the parent class {@link CropBlock#getOutlineShape(BlockState, BlockView, BlockPos, ShapeContext)},
 * this is due the limitation of 7 ages that it has.
 * </p>
 *
 * <p>
 * Due to the above mentioned this class is marked as abstract and cannot be instantiated
 * directly. You'll have to extend it and provide your own CropBlock implementation.
 * </p>
 */
public abstract class WKCropBlock extends CropBlock {

    public WKCropBlock(Settings settings) {
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
    public abstract IntProperty getAgeProperty();


    /**
     * Builds the IntProperty.
     */
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(getAgeProperty());
    }

    // The following methods were overridden purely for documentation

    /**
     * This is a filter that determines whether the CropBlock can be placed
     * above another. By default, you can only place it above a {@link net.minecraft.block.Blocks#FARMLAND},
     * but feel free to override this if needed.
     */
    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return super.canPlantOnTop(floor, world, pos);
    }

    /**
     * @return The current age at the given crop BlockState
     */
    @Override
    protected int getAge(BlockState state) {
        return super.getAge(state);
    }

    /**
     * Creates a new crop {@link BlockState} reference, with the {@link IntProperty}
     * already set.
     *
     * @param age {@link Integer} that represents the age of the new crop BlockState.
     * @return A new crop {@link BlockState}
     */
    @Override
    public BlockState withAge(int age) {
        return super.withAge(age);
    }

    /**
     * <p>
     * This method determines whether the crop is already at the max age
     * by doing a comparison between {@link WKCropBlock#getAgeProperty()}
     * and {@link WKCropBlock#getMaxAge()}.
     * </p>
     * It is used as a filter in different places, such as when the player
     * attempts to use a {@link net.minecraft.item.BoneMealItem} in the crop
     *
     * @return Whether the given crop is mature
     */
    @Override
    public boolean isMature(BlockState state) {
        return super.isMature(state);
    }

    /**
     * <p>
     * Determines whether the crop should randomly get
     * a "tick" code execution, by default it gets a
     * random tick as long as the crop is not matured.
     * </p>
     * <p>
     * This is triggered in {@link ServerWorld#tickChunk(WorldChunk, int)} and
     * if this returns false, {@link WKCropBlock#randomTick(BlockState, ServerWorld, BlockPos, RandomGenerator)}
     * will never get executed.
     * </p>
     */
    @Override
    public boolean hasRandomTicks(BlockState state) {
        return super.hasRandomTicks(state);
    }

    /**
     * <p>
     * The logic within this method gets executed in a tick, but randomly
     * whenever the {@link ServerWorld#tickChunk(WorldChunk, int)} decides to do so.
     * </p>
     * <p>
     * This place is useful to update the {@link BlockState} of the crop, because it is
     * constantly listening for chunk updates.
     * </p>
     * <p>
     * By default it is set update the crop block if there is enough light level,
     * {@link WKCropBlock#isMature(BlockState)} is false and they determined that there is
     * a good amount of available moisture.
     * </p>
     */
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, RandomGenerator random) {
        super.randomTick(state, world, pos, random);
    }

    /**
     * Updates the crop {@link BlockState}, by checking {@link CropBlock#getAge(BlockState)} is <  {@link CropBlock#getMaxAge()}.
     * <strong>NOTE:</strong> THIS METHOD IS ONLY TRIGGERED BY {@link WKCropBlock#grow(ServerWorld, RandomGenerator, BlockPos, BlockState)}
     */
    @Override
    public void applyGrowth(World world, BlockPos pos, BlockState state) {
        super.applyGrowth(world, pos, state);
    }


    /**
     * <p>
     * This method is a filter for {@link #grow(ServerWorld, RandomGenerator, BlockPos, BlockState)} and the BoneMeal grow method.
     * It is only used by {@link net.minecraft.item.BoneMealItem#useOnFertilizable(ItemStack, World, BlockPos)},
     * although when extending this class it will also be triggered by {@link #grow(ServerWorld, RandomGenerator, BlockPos, BlockState)}.
     * </p>
     * It is always returning true by the parent class unless overridden.
     */
    @Override
    public boolean canGrow(World world, RandomGenerator random, BlockPos pos, BlockState state) {
        return super.canGrow(world, random, pos, state);
    }

    /**
     * <p>
     * All what this method does is to call {@link WKCropBlock#applyGrowth(World, BlockPos, BlockState)}.
     * <strong>IMPORTANT</strong>:
     * <p>
     * This method is part of {@link net.minecraft.block.Fertilizable} interface, and it's only triggered
     * in the following places
     * </p>
     * {@link net.minecraft.item.BoneMealItem#useOnFertilizable(ItemStack, World, BlockPos)}
     * {@link BeeEntity.GrowCropsGoal#tick()}
     */
    @Override
    public void grow(ServerWorld world, RandomGenerator random, BlockPos pos, BlockState state) {
        if (canGrow(world, random, pos, state)) {
            super.grow(world, random, pos, state);
        }
    }

    /**
     * Gives you the context of the entity that collides with the block
     * This method only gets executed is the entity is colliding with the block.
     * See also {@link Entity#checkBlockCollision()}
     */
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
    }

    /**
     * Filter for {@link net.minecraft.block.PlantBlock#getStateForNeighborUpdate(BlockState, Direction, BlockState, WorldAccess, BlockPos, BlockPos)}
     * which destroys the crop if there is not enough light to place it.
     */
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return super.canPlaceAt(state, world, pos);
    }

    /**
     * This is the item that is spawned when the player
     * hits the mouse middle button.
     * Triggered on {@link MinecraftClient#doItemPick()}.
     */
    @Override
    protected ItemConvertible getSeedsItem() {
        return getSeedsItemStack().getItem();
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return getSeedsItemStack();
    }

    protected abstract ItemStack getSeedsItemStack();

    public void getNextSeed(World world, BlockPos pos, NbtCompound nbtCompound) {
        ItemStack itemStack2 = getSeedsItemStack();
        itemStack2.getOrCreateNbt().copyFrom(nbtCompound);
        ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), itemStack2);
    }
}