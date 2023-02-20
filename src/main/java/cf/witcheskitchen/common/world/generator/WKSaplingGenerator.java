package cf.witcheskitchen.common.world.generator;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.util.Holder;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import java.util.function.Supplier;

public class WKSaplingGenerator extends SaplingGenerator {

    private final Supplier<Holder<ConfiguredFeature<TreeFeatureConfig, ?>>> tree;

    public WKSaplingGenerator(Supplier<Holder<ConfiguredFeature<TreeFeatureConfig, ?>>> tree) {
        this.tree = tree;
    }

    @Override
    protected Holder<ConfiguredFeature<TreeFeatureConfig, ?>> getTreeFeature(RandomGenerator random, boolean bees) {
        return tree.get();
    }
}
