package cf.witcheskitchen.common.blocks.sapling.generator;

import cf.witcheskitchen.common.registry.WKConfiguredFeatures;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import net.minecraft.util.math.random.Random;

public class JuniperSaplingGenerator extends WKLargeSaplingGenerator {
    @Override
    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return RegistryEntry.of(WKConfiguredFeatures.JUNIPER_TREE);
    }

    @Override
    protected ConfiguredFeature<?, ?> getLargeTreeFeature(Random random) {
        // return WKGenerator.MEGA_JUNIPER_TREE;
        return null;
    }
}