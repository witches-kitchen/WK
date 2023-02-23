package cf.witcheskitchen.data.worldgen;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.common.registry.WKBlocks;
import cf.witcheskitchen.common.world.generator.SumacFoliagePlacer;
import net.minecraft.block.BlockState;
import net.minecraft.util.Holder;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.feature.util.ConfiguredFeatureUtil;
import net.minecraft.world.gen.feature.util.PlacedFeatureUtil;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.foliage.RandomSpreadFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.trunk.BendingTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

public interface WKConfiguredFeatures {

    static void init(){

    }

    Holder<ConfiguredFeature<TreeFeatureConfig, ?>> ELDER_TREE = registerTree("elder_tree",
            new TreeFeatureConfig.Builder(
                    SimpleBlockStateProvider.of(WKBlocks.ELDER_LOG.getDefaultState()),
                    new StraightTrunkPlacer(8, 4, 0),
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(WKBlocks.ELDER_LEAVES.getDefaultState(), 1)),
                    new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
                    new TwoLayersFeatureSize(1, 0, 1))
                    .ignoreVines()
                    .build());

    Holder<ConfiguredFeature<TreeFeatureConfig, ?>> BLACKTHORN_TREE = registerTree("blackthorn_tree",
            new TreeFeatureConfig.Builder(
                    SimpleBlockStateProvider.of(WKBlocks.BLACKTHORN_LOG.getDefaultState()),
                    new StraightTrunkPlacer(4, 2, 0),
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(WKBlocks.BLACKTHORN_LEAVES.getDefaultState(), 1)),
                    new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
                    new TwoLayersFeatureSize(1, 0, 1))
                    .ignoreVines()
                    .build());

    Holder<ConfiguredFeature<TreeFeatureConfig, ?>> HAWTHORN_TREE = registerTree("hawthorn_tree",
            new TreeFeatureConfig.Builder(
                    SimpleBlockStateProvider.of(WKBlocks.HAWTHORN_LOG.getDefaultState()),
                    new BendingTrunkPlacer(5, 2, 0, 3, UniformIntProvider.create(1, 2)),
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(WKBlocks.HAWTHORN_LEAVES.getDefaultState(), 1)),
                    new RandomSpreadFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0), ConstantIntProvider.create(2), 50),
                    new TwoLayersFeatureSize(1, 0, 1))
                    .ignoreVines()
                    .build());

    Holder<ConfiguredFeature<TreeFeatureConfig, ?>> JUNIPER_TREE = registerTree("juniper_tree",
            new TreeFeatureConfig.Builder(
                    SimpleBlockStateProvider.of(WKBlocks.JUNIPER_LOG.getDefaultState()),
                    new StraightTrunkPlacer(4, 8, 0),
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(WKBlocks.JUNIPER_LEAVES.getDefaultState(), 1)),
                    new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
                    new TwoLayersFeatureSize(2, 0, 1))
                    .ignoreVines()
                    .build());

    Holder<ConfiguredFeature<TreeFeatureConfig, ?>> ROWAN_TREE = registerTree("rowan_tree",
            new TreeFeatureConfig.Builder(
                    SimpleBlockStateProvider.of(WKBlocks.ROWAN_LOG.getDefaultState()),
                    new StraightTrunkPlacer(4, 2, 0),
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(WKBlocks.ROWAN_LEAVES.getDefaultState(), 1)),
                    new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
                    new TwoLayersFeatureSize(2, 0, 2))
                    .ignoreVines()
                    .build());

    Holder<ConfiguredFeature<TreeFeatureConfig, ?>> SUMAC_TREE = registerTree("sumac_tree",
            new TreeFeatureConfig.Builder(
                    SimpleBlockStateProvider.of(WKBlocks.SUMAC_LOG.getDefaultState()),
                    new StraightTrunkPlacer(5, 2, 2),
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(WKBlocks.SUMAC_LEAVES.getDefaultState(), 1)),
                    new SumacFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0)),
                    new TwoLayersFeatureSize(1, 0, 1))
                    .ignoreVines()
                    .build());

    private static <F extends Feature<C>, C extends FeatureConfig> Holder<ConfiguredFeature<C, ?>> register(String id, F feature, C config) {
        return ConfiguredFeatureUtil.register(WitchesKitchen.MODID + ":" + id, feature, config);
    }

    private static Holder<ConfiguredFeature<TreeFeatureConfig, ?>> registerTree(String id, TreeFeatureConfig config) {
        BuiltinRegistries.PLACED_FEATURE.getCodec().fieldOf("feature").xmap(placedFeatureRegistryEntry -> null, o -> null).codec();
        return register(id, Feature.TREE, config);
    }

    static RandomPatchFeatureConfig createRandomPatchFeatureConfig(BlockStateProvider block, int tries) {
        return ConfiguredFeatureUtil.createRandomPatchFeatureConfig(tries, PlacedFeatureUtil.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(block)));
    }
}
