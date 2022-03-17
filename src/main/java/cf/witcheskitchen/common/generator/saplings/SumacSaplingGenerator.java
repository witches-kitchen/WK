package cf.witcheskitchen.common.generator.saplings;

import cf.witcheskitchen.common.registry.WKGenerator;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import java.util.Random;

public class SumacSaplingGenerator extends WKSaplingGenerator {
    @Override
    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return RegistryEntry.of(WKGenerator.SUMAC_TREE);
    }
}
