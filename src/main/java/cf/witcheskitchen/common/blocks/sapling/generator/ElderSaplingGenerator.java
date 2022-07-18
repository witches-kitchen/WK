package cf.witcheskitchen.common.blocks.sapling.generator;

import cf.witcheskitchen.common.registry.WKConfiguredFeatures;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;

import net.minecraft.util.math.random.Random;

public class ElderSaplingGenerator extends WKSaplingGenerator {
    @Override
    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        if (random.nextInt(10) == 0) {
            return bees ? TreeConfiguredFeatures.OAK_BEES_005 : RegistryEntry.of(WKConfiguredFeatures.ELDER_ELDER_TREE);
        }
        return bees ? TreeConfiguredFeatures.OAK_BEES_005 : RegistryEntry.of(WKConfiguredFeatures.ELDER_TREE);
    }
}

