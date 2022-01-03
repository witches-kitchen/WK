package cf.witcheskitchen.common.generator.saplings;

import cf.witcheskitchen.common.registry.WKGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.Random;

public class JuniperSaplingGenerator extends WKLargeSaplingGenerator {
    @Override
    protected ConfiguredFeature<?, ?> getTreeFeature(Random random, boolean bees) {
        return WKGenerator.JUNIPER_TREE;
    }

    @Override
    protected ConfiguredFeature<?, ?> getLargeTreeFeature(Random random) {
        // return WKGenerator.MEGA_JUNIPER_TREE;
        return null;
    }
}