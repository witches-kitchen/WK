package cf.witcheskitchen.data.worldgen;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.common.registry.WKBlocks;
import cf.witcheskitchen.common.world.generator.SumacFoliagePlacer;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.RandomSpreadFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.trunk.BendingTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.LinkedHashMap;
import java.util.Map;

public interface WKConfiguredFeatures {
    Map<Identifier, ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = new LinkedHashMap<>();
    Map<ConfiguredFeature<?, ?>, RegistryKey<ConfiguredFeature<?, ?>>> CONFIGURED_FEATURE_KEYS = new LinkedHashMap<>();


    ConfiguredFeature<TreeFeatureConfig, ?> ELDER_TREE = register("elder_tree", new ConfiguredFeature<>(Feature.TREE,
            new TreeFeatureConfig.Builder(
                    SimpleBlockStateProvider.of(WKBlocks.ELDER_LOG.getDefaultState()),
                    new StraightTrunkPlacer(8, 4, 0),
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(WKBlocks.ELDER_LEAVES.getDefaultState(), 1)),
                    new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
                    new TwoLayersFeatureSize(1, 0, 1))
                    .ignoreVines()
                    .build()));

    ConfiguredFeature<TreeFeatureConfig, ?> BLACKTHORN_TREE = register("blackthorn_tree", new ConfiguredFeature<>(Feature.TREE,
            new TreeFeatureConfig.Builder(
                    SimpleBlockStateProvider.of(WKBlocks.BLACKTHORN_LOG.getDefaultState()),
                    new StraightTrunkPlacer(4, 2, 0),
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(WKBlocks.BLACKTHORN_LEAVES.getDefaultState(), 1)),
                    new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
                    new TwoLayersFeatureSize(1, 0, 1))
                    .ignoreVines()
                    .build()));

    ConfiguredFeature<TreeFeatureConfig, ?> HAWTHORN_TREE = register("hawthorn_tree", new ConfiguredFeature<>(Feature.TREE,
            new TreeFeatureConfig.Builder(
                    SimpleBlockStateProvider.of(WKBlocks.HAWTHORN_LOG.getDefaultState()),
                    new BendingTrunkPlacer(5, 2, 0, 3, UniformIntProvider.create(1, 2)),
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(WKBlocks.HAWTHORN_LEAVES.getDefaultState(), 1)),
                    new RandomSpreadFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0), ConstantIntProvider.create(2), 50),
                    new TwoLayersFeatureSize(1, 0, 1))
                    .ignoreVines()
                    .build()));

    ConfiguredFeature<TreeFeatureConfig, ?> JUNIPER_TREE = register("juniper_tree", new ConfiguredFeature<>(Feature.TREE,
            new TreeFeatureConfig.Builder(
                    SimpleBlockStateProvider.of(WKBlocks.JUNIPER_LOG.getDefaultState()),
                    new StraightTrunkPlacer(4, 8, 0),
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(WKBlocks.JUNIPER_LEAVES.getDefaultState(), 1)),
                    new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
                    new TwoLayersFeatureSize(2, 0, 1))
                    .ignoreVines()
                    .build()));

    ConfiguredFeature<TreeFeatureConfig, ?> ROWAN_TREE = register("rowan_tree", new ConfiguredFeature<>(Feature.TREE,
            new TreeFeatureConfig.Builder(
                    SimpleBlockStateProvider.of(WKBlocks.ROWAN_LOG.getDefaultState()),
                    new StraightTrunkPlacer(4, 2, 0),
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(WKBlocks.ROWAN_LEAVES.getDefaultState(), 1)),
                    new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
                    new TwoLayersFeatureSize(2, 0, 2))
                    .ignoreVines()
                    .build()));

    ConfiguredFeature<TreeFeatureConfig, ?> SUMAC_TREE = register("sumac_tree", new ConfiguredFeature<>(Feature.TREE,
            new TreeFeatureConfig.Builder(
                    BlockStateProvider.of(WKBlocks.SUMAC_LOG),
                    new StraightTrunkPlacer(5, 2, 2),
                    BlockStateProvider.of(WKBlocks.SUMAC_LEAVES),
                    new SumacFoliagePlacer(ConstantIntProvider.create(2),
                            ConstantIntProvider.create(0)),
                    new TwoLayersFeatureSize(1, 1, 2))
                    .build()));

    static <C extends FeatureConfig, E extends Feature<C>, F extends ConfiguredFeature<C, E>> F register(String id, F feature) {
        CONFIGURED_FEATURES.put(WitchesKitchen.id(id), feature);
        return feature;
    }

    static void init(Registry<ConfiguredFeature<?, ?>> configured) {
        CONFIGURED_FEATURES.forEach((id, feature) -> {
            Registry.register(configured, id, feature);
            CONFIGURED_FEATURE_KEYS.put(feature, configured.getKey(feature).orElseThrow());
        });
    }
}
