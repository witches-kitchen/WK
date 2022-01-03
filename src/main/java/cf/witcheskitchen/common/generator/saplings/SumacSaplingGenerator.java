package cf.witcheskitchen.common.generator.saplings;

import java.util.Random;

import cf.witcheskitchen.common.registry.WKGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class SumacSaplingGenerator extends WKSaplingGenerator{
    @Override
    protected ConfiguredFeature<?, ?> getTreeFeature(Random random, boolean bees) {
        return WKGenerator.SUMAC_TREE;
    }
}
