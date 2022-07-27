package cf.witcheskitchen.common.block.sapling.generator;

import cf.witcheskitchen.common.registry.WKConfiguredFeatures;
import net.minecraft.util.Holder;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class SumacSaplingGenerator extends WKSaplingGenerator {
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getTreeFeature(RandomGenerator random, boolean bees) {
        return Holder.createDirect(WKConfiguredFeatures.SUMAC_TREE);
    }
}
