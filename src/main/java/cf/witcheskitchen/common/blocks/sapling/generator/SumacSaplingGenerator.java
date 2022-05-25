package cf.witcheskitchen.common.blocks.sapling.generator;

import cf.witcheskitchen.common.registry.WKConfiguredFeatures;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.Random;

public class SumacSaplingGenerator extends WKSaplingGenerator {
    @Override
    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return RegistryEntry.of(WKConfiguredFeatures.SUMAC_TREE);
    }
}
