package cf.witcheskitchen.common.generator.saplings;

import java.util.Random;

import cf.witcheskitchen.common.registry.WKGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;

public class ElderSaplingGenerator extends WKSaplingGenerator{
    @Override
    protected ConfiguredFeature<?, ?> getTreeFeature(Random random, boolean bees) {
        if (random.nextInt(10) == 0) {
            return bees ? TreeConfiguredFeatures.OAK_BEES_005 : WKGenerator.ELDER_ELDER_TREE;
        }
        return bees ? TreeConfiguredFeatures.OAK_BEES_005 : WKGenerator.ELDER_TREE;
    }
}

