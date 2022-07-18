package cf.witcheskitchen.common.blocks.sapling.generator;

import cf.witcheskitchen.common.registry.WKConfiguredFeatures;
import net.minecraft.util.Holder;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;

public class RowanSaplingGenerator
        extends WKSaplingGenerator {
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getTreeFeature(RandomGenerator random, boolean bees) {
//        if (random.nextInt(30) == 5 || random.nextInt(30) == 10) {
//            return bees ? TreeConfiguredFeatures.FANCY_OAK_BEES_005 : WKGenerator.FANCY_ROWAN_TREE;
//        }
//        if (random.nextInt(30) == 0) {
//            return bees ? TreeConfiguredFeatures.OAK_BEES_005 : WKGenerator.ELDER_ROWAN_TREE;
//        }
        return bees ? TreeConfiguredFeatures.OAK_BEES_005 : Holder.createDirect(WKConfiguredFeatures.ROWAN_TREE);
    }
}
