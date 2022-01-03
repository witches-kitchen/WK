package cf.witcheskitchen.common.generator.saplings;

import cf.witcheskitchen.common.registry.WKGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class HawthornSaplingGenerator extends WKSaplingGenerator {
    @Override
    @Nullable
    protected ConfiguredFeature<?, ?> getTreeFeature(Random random, boolean bees) {
        return WKGenerator.HAWTHORN_TREE;
    }
}
