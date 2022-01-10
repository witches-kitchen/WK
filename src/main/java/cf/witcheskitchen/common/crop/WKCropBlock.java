package cf.witcheskitchen.common.crop;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

public class WKCropBlock extends TallPlantBlock implements Fertilizable {

    public static final int MAX_AGE = 6;

    public static final IntProperty AGE = IntProperty.of("age", 0, MAX_AGE);

    public WKCropBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(HALF, DoubleBlockHalf.LOWER).with(AGE, 0));
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(AGE);
    }

    @Override
    public OffsetType getOffsetType() {
        return super.getOffsetType();
    }


    protected int getAge(BlockState state) {
        return state.get(AGE);
    }


    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true; // Why not ?
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int j;
        int i = this.getAge(state) + this.getGrowthAmount(world);
        if (i > (j = MAX_AGE)) {
            i = j;
        }
        world.setBlockState(pos, this.setWithAge(i), Block.NOTIFY_LISTENERS);
    }

    public BlockState setWithAge(int age) {
        return this.getDefaultState().with(AGE, age);
    }

    /**
     * {@return a random, uniformly distributed integer value between min and max
     */
    public int getGrowthAmount(World world) {
        return MathHelper.nextInt(world.random, 2, 5);
    }
}
