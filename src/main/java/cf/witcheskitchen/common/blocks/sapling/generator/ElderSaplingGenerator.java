package cf.witcheskitchen.common.blocks.sapling.generator;

import cf.witcheskitchen.common.registry.WKConfiguredFeatures;
import net.minecraft.util.Holder;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeConfiguredFeatures;


public class ElderSaplingGenerator extends WKSaplingGenerator {
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getTreeFeature(RandomGenerator random, boolean bees) {
        if (random.nextInt(10) == 0) {
            return bees ? TreeConfiguredFeatures.OAK_BEES_005 : Holder.createDirect(WKConfiguredFeatures.ELDER_ELDER_TREE);
        }
        return bees ? TreeConfiguredFeatures.OAK_BEES_005 : Holder.createDirect(WKConfiguredFeatures.ELDER_TREE);
    }

}

