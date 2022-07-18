package cf.witcheskitchen.common.generator;

import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.jetbrains.annotations.Nullable;

import net.minecraft.util.math.random.Random;
import java.util.function.Supplier;

//code currently thanks to croptopia as other attempts kept failing here
public class WKSaplingGenerator extends SaplingGenerator {

    private final Supplier<RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>>> tree;

    public WKSaplingGenerator(Supplier<RegistryEntry<ConfiguredFeature<TreeFeatureConfig, ?>>> tree) {
        this.tree = tree;
    }

    @Nullable
    @Override
    protected RegistryEntry<? extends ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return tree.get();
    }
}
