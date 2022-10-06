package cf.witcheskitchen.common.block.sapling.generator;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Holder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;


public abstract class WKSaplingGenerator extends SaplingGenerator {
    protected abstract Holder<? extends ConfiguredFeature<?, ?>> getTreeFeature(RandomGenerator var1, boolean var2);

    public boolean generate(ServerWorld world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, RandomGenerator random) {
        Holder<? extends ConfiguredFeature<?, ?>> registryEntry = this.getTreeFeature(random, this.areFlowersNearby(world, pos));
        if (registryEntry == null) {
            return false;
        }
        ConfiguredFeature<?, ?> configuredFeature = registryEntry.value();
        world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NO_REDRAW);
        if (configuredFeature.generate(world, chunkGenerator, random, pos)) {
            return true;
        }
        world.setBlockState(pos, state, Block.NO_REDRAW);
        return false;
    }

    private boolean areFlowersNearby(WorldAccess world, BlockPos pos) {
        for (BlockPos blockPos : BlockPos.Mutable.iterate(pos.down().north(2).west(2), pos.up().south(2).east(2))) {
            if (!world.getBlockState(blockPos).isIn(BlockTags.FLOWERS)) continue;
            return true;
        }
        return false;
    }
}
