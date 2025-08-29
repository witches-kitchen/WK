package cf.witcheskitchen.common.block.crop;

import cf.witcheskitchen.api.block.crop.WKTallCropBlock;
import cf.witcheskitchen.api.interfaces.CropVariants;
import cf.witcheskitchen.api.util.SeedTypeHelper;
import cf.witcheskitchen.common.block.crop.types.CamelliaTypes;
import cf.witcheskitchen.common.component.WKComponents;
import cf.witcheskitchen.common.registry.WKItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

public class CamelliaCropBlock extends WKTallCropBlock implements CropVariants {
    public static final VoxelShape[] LOWER_AGE_TO_SHAPE;
    public static final VoxelShape[] UPPER_AGE_TO_SHAPE;
    public static final int MAX_AGE = 7;
    private static final IntegerProperty AGE = IntegerProperty.create("age", 0, MAX_AGE);

    static {
        LOWER_AGE_TO_SHAPE = new VoxelShape[]{
                Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
                Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
                Block.box(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
                Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0),
                Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0),
                Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0),
                Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0),
                Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
        };

        UPPER_AGE_TO_SHAPE = new VoxelShape[]{
                Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
                Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
                Block.box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
                Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
                Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
                Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0)
        };
    }

    private final CamelliaTypes type;

    public CamelliaCropBlock(Properties settings) {
        this(settings, CamelliaTypes.COMMON);
    }

    public CamelliaCropBlock(Properties settings, CamelliaTypes type) {
        super(settings);
        this.type = type;
        this.registerDefaultState(this.defaultBlockState().setValue(getAgeProperty(), 0).setValue(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        Optional<CamelliaTypes> nextType = type.next(type);
        if (nextType.isPresent()) {
            var component = SeedTypeHelper.toComponent(nextType.get().getName(), nextType.get().getType(), nextType.get().getColor());
            getNextSeed(world, pos, component);
        }
        super.playerWillDestroy(world, pos, state, player);
        return state;
    }

    @Override
    public void growCrops(Level world, BlockPos pos, BlockState state) {
        int maxAge;
        int age = this.getAge(state) + this.getBonemealAgeIncrease(world);
        if (age > (maxAge = this.getMaxAge())) {
            age = maxAge;
        }
        world.setBlock(pos, this.withHalf(age == 5 ? age + 1 : age, DoubleBlockHalf.LOWER), Block.UPDATE_CLIENTS);
        if (age >= doubleBlockAge()) {
            world.setBlock(pos.above(), this.withHalf(age == 5 ? age + 1 : age, DoubleBlockHalf.UPPER), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        final int age = this.getAge(state);
        boolean bl = age == 4;
        if (world.getRawBrightness(pos, 0) >= 9) {
            if (age < getMaxAge()) {
                if (random.nextInt((int) (25.0f / (CropBlock.getGrowthSpeed(this, world, pos))) + 1) == 0) {
                    final int nextAge = age + 1;
                    world.setBlock(pos, withHalf(bl ? nextAge + 1 : nextAge, DoubleBlockHalf.LOWER), Block.UPDATE_CLIENTS);
                    if (age >= doubleBlockAge()) {
                        world.setBlock(pos.above(), withHalf(bl ? nextAge + 1 : nextAge, DoubleBlockHalf.UPPER), Block.UPDATE_CLIENTS);
                    }
                }
            }
        }
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            pos = pos.below();
        }

        if (player.getMainHandItem().isEmpty() && state.getValue(getAgeProperty()) == MAX_AGE) {
            world.setBlock(pos, this.withHalf(this.getAge(state) - 2, DoubleBlockHalf.LOWER), Block.UPDATE_CLIENTS);
            world.setBlock(pos.above(), this.withHalf(this.getAge(state) - 2, DoubleBlockHalf.UPPER), Block.UPDATE_CLIENTS);
        }
        return super.useWithoutItem(state, world, pos, player, hit);
    }

    @Override
    public VoxelShape[] getLowerShape() {
        return LOWER_AGE_TO_SHAPE;
    }

    @Override
    public VoxelShape[] getUpperShape() {
        return UPPER_AGE_TO_SHAPE;
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Environment(EnvType.CLIENT)
    @Override
    protected ItemStack getSeedsItemStack() {
        var component = SeedTypeHelper.toComponent(type.getName(), type.getType(), type.getColor());
        ItemStack seed = new ItemStack(WKItems.CAMELLIA_SEEDS);
        seed.set(WKComponents.SEED_TYPE, component);
        return seed;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    public int doubleBlockAge() {
        return 2;
    }
}
