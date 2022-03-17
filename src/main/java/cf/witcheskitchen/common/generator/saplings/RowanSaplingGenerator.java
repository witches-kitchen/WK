package cf.witcheskitchen.common.generator.saplings;

import cf.witcheskitchen.common.registry.WKGenerator;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;

import java.util.Random;

public class RowanSaplingGenerator
        extends WKSaplingGenerator {
    @Override
    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
//        if (random.nextInt(30) == 5 || random.nextInt(30) == 10) {
//            return bees ? TreeConfiguredFeatures.FANCY_OAK_BEES_005 : WKGenerator.FANCY_ROWAN_TREE;
//        }
//        if (random.nextInt(30) == 0) {
//            return bees ? TreeConfiguredFeatures.OAK_BEES_005 : WKGenerator.ELDER_ROWAN_TREE;
//        }
        return bees ? TreeConfiguredFeatures.OAK_BEES_005 : RegistryEntry.of(WKGenerator.ROWAN_TREE);
    }
}
