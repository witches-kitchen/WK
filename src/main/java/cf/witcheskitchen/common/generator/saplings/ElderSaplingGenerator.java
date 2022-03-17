package cf.witcheskitchen.common.generator.saplings;

import cf.witcheskitchen.common.registry.WKGenerator;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;

import java.util.Random;

public class ElderSaplingGenerator extends WKSaplingGenerator {
    @Override
    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        if (random.nextInt(10) == 0) {
            return bees ? TreeConfiguredFeatures.OAK_BEES_005 : RegistryEntry.of(WKGenerator.ELDER_ELDER_TREE);
        }
        return bees ? TreeConfiguredFeatures.OAK_BEES_005 : RegistryEntry.of(WKGenerator.ELDER_TREE);
    }
}

