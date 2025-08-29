package cf.witcheskitchen.data.worldgen;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.common.registry.WKBlocks;
import cf.witcheskitchen.common.world.generator.SumacFoliagePlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.BendingTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

import java.util.LinkedHashMap;
import java.util.Map;

public interface WKConfiguredFeatures {
    Map<ResourceLocation, ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = new LinkedHashMap<>();
    Map<ConfiguredFeature<?, ?>, ResourceKey<ConfiguredFeature<?, ?>>> CONFIGURED_FEATURE_KEYS = new LinkedHashMap<>();


    ConfiguredFeature<TreeConfiguration, ?> ELDER_TREE = register("elder_tree", new ConfiguredFeature<>(Feature.TREE,
            new TreeConfiguration.TreeConfigurationBuilder(
                    SimpleStateProvider.simple(WKBlocks.ELDER_LOG.defaultBlockState()),
                    new StraightTrunkPlacer(8, 4, 0),
                    new WeightedStateProvider(WeightedList.<BlockState>builder().add(WKBlocks.ELDER_LEAVES.defaultBlockState(), 1)),
                    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                    new TwoLayersFeatureSize(1, 0, 1))
                    .ignoreVines()
                    .build()));

    ConfiguredFeature<TreeConfiguration, ?> BLACKTHORN_TREE = register("blackthorn_tree", new ConfiguredFeature<>(Feature.TREE,
            new TreeConfiguration.TreeConfigurationBuilder(
                    SimpleStateProvider.simple(WKBlocks.BLACKTHORN_LOG.defaultBlockState()),
                    new StraightTrunkPlacer(4, 2, 0),
                    new WeightedStateProvider(WeightedList.<BlockState>builder().add(WKBlocks.BLACKTHORN_LEAVES.defaultBlockState(), 1)),
                    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                    new TwoLayersFeatureSize(1, 0, 1))
                    .ignoreVines()
                    .build()));

    ConfiguredFeature<TreeConfiguration, ?> HAWTHORN_TREE = register("hawthorn_tree", new ConfiguredFeature<>(Feature.TREE,
            new TreeConfiguration.TreeConfigurationBuilder(
                    SimpleStateProvider.simple(WKBlocks.HAWTHORN_LOG.defaultBlockState()),
                    new BendingTrunkPlacer(5, 2, 0, 3, UniformInt.of(1, 2)),
                    new WeightedStateProvider(WeightedList.<BlockState>builder().add(WKBlocks.HAWTHORN_LEAVES.defaultBlockState(), 1)),
                    new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(2), 50),
                    new TwoLayersFeatureSize(1, 0, 1))
                    .ignoreVines()
                    .build()));

    ConfiguredFeature<TreeConfiguration, ?> JUNIPER_TREE = register("juniper_tree", new ConfiguredFeature<>(Feature.TREE,
            new TreeConfiguration.TreeConfigurationBuilder(
                    SimpleStateProvider.simple(WKBlocks.JUNIPER_LOG.defaultBlockState()),
                    new StraightTrunkPlacer(4, 8, 0),
                    new WeightedStateProvider(WeightedList.<BlockState>builder().add(WKBlocks.JUNIPER_LEAVES.defaultBlockState(), 1)),
                    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                    new TwoLayersFeatureSize(2, 0, 1))
                    .ignoreVines()
                    .build()));

    ConfiguredFeature<TreeConfiguration, ?> ROWAN_TREE = register("rowan_tree", new ConfiguredFeature<>(Feature.TREE,
            new TreeConfiguration.TreeConfigurationBuilder(
                    SimpleStateProvider.simple(WKBlocks.ROWAN_LOG.defaultBlockState()),
                    new StraightTrunkPlacer(4, 2, 0),
                    new WeightedStateProvider(WeightedList.<BlockState>builder().add(WKBlocks.ROWAN_LEAVES.defaultBlockState(), 1)),
                    new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                    new TwoLayersFeatureSize(2, 0, 2))
                    .ignoreVines()
                    .build()));

    ConfiguredFeature<TreeConfiguration, ?> SUMAC_TREE = register("sumac_tree", new ConfiguredFeature<>(Feature.TREE,
            new TreeConfiguration.TreeConfigurationBuilder(
                    BlockStateProvider.simple(WKBlocks.SUMAC_LOG),
                    new StraightTrunkPlacer(5, 2, 2),
                    BlockStateProvider.simple(WKBlocks.SUMAC_LEAVES),
                    new SumacFoliagePlacer(ConstantInt.of(2),
                            ConstantInt.of(0)),
                    new TwoLayersFeatureSize(1, 1, 2))
                    .build()));

    static <C extends FeatureConfiguration, E extends Feature<C>, F extends ConfiguredFeature<C, E>> F register(String id, F feature) {
        ResourceLocation identifier = WitchesKitchen.id(id);
        CONFIGURED_FEATURES.put(identifier, feature);
        CONFIGURED_FEATURE_KEYS.put(feature, ResourceKey.create(Registries.CONFIGURED_FEATURE, identifier));
        return feature;
    }

    static void init(BootstrapContext<ConfiguredFeature<?, ?>> configured) {
        CONFIGURED_FEATURES.forEach((id, feature) -> {
            configured.register(ResourceKey.create(Registries.CONFIGURED_FEATURE, id), feature);
        });
    }
}
