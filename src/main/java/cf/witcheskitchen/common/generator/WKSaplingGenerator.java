package cf.witcheskitchen.common.generator;

import java.util.Random;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

//code currently thanks to croptopia as other attempts kept failing here
public class WKSaplingGenerator extends SaplingGenerator {

    private final Supplier<ConfiguredFeature<TreeFeatureConfig, ?>> tree;

    public WKSaplingGenerator(Supplier<ConfiguredFeature<TreeFeatureConfig, ?>> tree) {
        this.tree = tree;
    }

    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean bees) {
        return tree.get();
    }
}
