package cf.witcheskitchen.common.block.sapling.generator;

import cf.witcheskitchen.common.registry.WKConfiguredFeatures;
import net.minecraft.util.Holder;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class JuniperSaplingGenerator extends WKLargeSaplingGenerator {
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getTreeFeature(RandomGenerator random, boolean bees) {
        return Holder.createDirect(WKConfiguredFeatures.JUNIPER_TREE);
    }

    @Override
    protected @Nullable ConfiguredFeature<?, ?> getLargeTreeFeature(RandomGenerator var1) {
        return null;
    }
}