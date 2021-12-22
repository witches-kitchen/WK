package cf.witcheskitchen.mixin;

import java.util.Random;
import java.util.function.BiConsumer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;

@Mixin(FoliagePlacer.class)
public interface FoliagePlacerInvoker {
    @Invoker
    void callGenerateSquare(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, TreeFeatureConfig config, BlockPos centerPos, int radius, int y, boolean giantTrunk);
}