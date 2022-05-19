package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.WKConfig;
import cf.witcheskitchen.common.generator.SumacFoliagePlacer;
import cf.witcheskitchen.mixin.FoliagePlacerTypeInvoker;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.ThreeLayersFeatureSize;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.*;
import net.minecraft.world.gen.placementmodifier.BiomePlacementModifier;
import net.minecraft.world.gen.placementmodifier.BlockFilterPlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.treedecorator.AlterGroundTreeDecorator;
import net.minecraft.world.gen.trunk.BendingTrunkPlacer;
import net.minecraft.world.gen.trunk.DarkOakTrunkPlacer;
import net.minecraft.world.gen.trunk.GiantTrunkPlacer;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;

import java.util.List;
import java.util.OptionalInt;


public class WKGenerator {

    public static final FoliagePlacerType<SumacFoliagePlacer> SUMAC_FOLIAGE_PLACER = FoliagePlacerTypeInvoker.callRegister("sumac_foliage_placer", SumacFoliagePlacer.CODEC);

    public static final ConfiguredFeature<TreeFeatureConfig, ?> BLACKTHORN_TREE =
            new ConfiguredFeature<>(Feature.TREE, new TreeFeatureConfig.Builder(
                    SimpleBlockStateProvider.of(WKBlocks.BLACKTHORN_LOG),
                    new StraightTrunkPlacer(4, 2, 0),
                    BlockStateProvider.of(WKBlocks.BLACKTHORN_LEAVES),
                    new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
                    new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());


    public static final ConfiguredFeature<TreeFeatureConfig, ?> ELDER_TREE =
            new ConfiguredFeature<>(Feature.TREE, new TreeFeatureConfig.Builder(
                    SimpleBlockStateProvider.of(WKBlocks.ELDER_LOG),
                    new StraightTrunkPlacer(8, 4, 0),
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(WKBlocks.ELDER_LEAVES.getDefaultState(), 3).add(WKBlocks.ELDER_LEAVES_COLORED.getDefaultState(), 2)),
                    new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
                    new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());

    public static final ConfiguredFeature<TreeFeatureConfig, ?> ELDER_ELDER_TREE =
            new ConfiguredFeature<>(Feature.TREE, new TreeFeatureConfig.Builder(
                    SimpleBlockStateProvider.of(WKBlocks.ELDER_LOG),
                    new GiantTrunkPlacer(13, 2, 14),
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(WKBlocks.ELDER_LEAVES.getDefaultState(), 3).add(WKBlocks.ELDER_LEAVES_COLORED.getDefaultState(), 2)),
                    new MegaPineFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0), UniformIntProvider.create(13, 17)),
                    new TwoLayersFeatureSize(1, 1, 2)).decorators(ImmutableList.of(new AlterGroundTreeDecorator(BlockStateProvider.of(Blocks.PODZOL)))).build());


    public static final ConfiguredFeature<TreeFeatureConfig, ?> HAWTHORN_TREE =
            new ConfiguredFeature<>(Feature.TREE, new TreeFeatureConfig.Builder(
                    SimpleBlockStateProvider.of(WKBlocks.HAWTHORN_LOG),
                    new BendingTrunkPlacer(5, 2, 0, 3, UniformIntProvider.create(1, 2)),
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(WKBlocks.HAWTHORN_LEAVES.getDefaultState(), 3).add(WKBlocks.HAWTHORN_LEAVES_COLORED.getDefaultState(), 1)),
                    new RandomSpreadFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0), ConstantIntProvider.create(2), 50),
                    new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());


    public static final ConfiguredFeature<TreeFeatureConfig, ?> JUNIPER_TREE =
            new ConfiguredFeature<>(Feature.TREE, new TreeFeatureConfig.Builder(
                    SimpleBlockStateProvider.of(WKBlocks.JUNIPER_LOG.getDefaultState()),
                    new StraightTrunkPlacer(4, 8, 0),
                    BlockStateProvider.of(WKBlocks.JUNIPER_LEAVES),
                    new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
                    new TwoLayersFeatureSize(2, 0, 1)).ignoreVines().build());

    public static final ConfiguredFeature<TreeFeatureConfig, ?> ROWAN_TREE =
            new ConfiguredFeature<>(Feature.TREE, new TreeFeatureConfig.Builder(
                    SimpleBlockStateProvider.of(WKBlocks.ROWAN_LOG.getDefaultState()),
                    new StraightTrunkPlacer(4, 2, 0),
                    BlockStateProvider.of(WKBlocks.ROWAN_LEAVES),
                    new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
                    new TwoLayersFeatureSize(2, 0, 2)).ignoreVines().build());


    public static final ConfiguredFeature<TreeFeatureConfig, ?> ELDER_ROWAN =
            new ConfiguredFeature<>(Feature.TREE, new TreeFeatureConfig.Builder(
                    BlockStateProvider.of(WKBlocks.ROWAN_LOG),
                    new DarkOakTrunkPlacer(6, 2, 1),
                    BlockStateProvider.of(WKBlocks.ROWAN_LEAVES),
                    new DarkOakFoliagePlacer(ConstantIntProvider.create(0), ConstantIntProvider.create(0)),
                    new ThreeLayersFeatureSize(1, 1, 0, 1, 2, OptionalInt.empty())).ignoreVines().build());


    public static final ConfiguredFeature<TreeFeatureConfig, ?> SUMAC_TREE =
            new ConfiguredFeature<>(Feature.TREE, new TreeFeatureConfig.Builder(
                    SimpleBlockStateProvider.of(WKBlocks.SUMAC_LOG),
                    new StraightTrunkPlacer(5, 2, 2),
                    new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(WKBlocks.SUMAC_LEAVES.getDefaultState(), 90).build()),
                    new SumacFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0)),
                    new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());

    //TODO: change the way placed features are registered
    // They end up calling the parent block when it is still null
    // In other words before they get even initialized.
    // May 19 2022 - Daniking
//    public static final PlacedFeature BLACKTHORN_TREE_CONFIGURED =
//            new PlacedFeature(RegistryEntry.of(BLACKTHORN_TREE), List.of(SquarePlacementModifier.of(), PlacedFeatures.createCountExtraModifier(0, 0.1F, 1), VegetationPlacedFeatures.NOT_IN_SURFACE_WATER_MODIFIER, PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(WKBlocks.BLACKTHORN_SAPLING.getDefaultState(), BlockPos.ORIGIN)), BiomePlacementModifier.of()));
//
//    public static final PlacedFeature ELDER_TREE_CONFIGURED =
//            new PlacedFeature(RegistryEntry.of(ELDER_TREE), List.of(SquarePlacementModifier.of(), PlacedFeatures.createCountExtraModifier(0, 0.1F, 1), VegetationPlacedFeatures.NOT_IN_SURFACE_WATER_MODIFIER, PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(WKBlocks.ELDER_SAPLING.getDefaultState(), BlockPos.ORIGIN)), BiomePlacementModifier.of()));

//dont know if this method needs to be changed. didn't want to debug this atm. will do 
/* public static final PlacedFeature ELDER_ELDER_TREE_CONFIGURED =
         ELDER_ELDER_TREE.withPlacement(SquarePlacementModifier.of(), PlacedFeatures.createCountExtraModifier(0, 0.1F, 1), VegetationPlacedFeatures.NOT_IN_SURFACE_WATER_MODIFIER, PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(WKBlocks.ELDER_SAPLING.getDefaultState(), BlockPos.ORIGIN)), BiomePlacementModifier.of());*/
//
//    public static final PlacedFeature HAWTHORN_TREE_CONFIGURED =
//            new PlacedFeature(RegistryEntry.of(HAWTHORN_TREE), List.of(SquarePlacementModifier.of(), PlacedFeatures.createCountExtraModifier(0, 0.1F, 1), VegetationPlacedFeatures.NOT_IN_SURFACE_WATER_MODIFIER, PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(WKBlocks.HAWTHORN_SAPLING.getDefaultState(), BlockPos.ORIGIN)), BiomePlacementModifier.of()));
//
//    public static final PlacedFeature JUNIPER_TREE_CONFIGURED =
//            new PlacedFeature(RegistryEntry.of(JUNIPER_TREE), List.of(SquarePlacementModifier.of(), PlacedFeatures.createCountExtraModifier(0, 0.1F, 1), VegetationPlacedFeatures.NOT_IN_SURFACE_WATER_MODIFIER, PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(WKBlocks.JUNIPER_SAPLING.getDefaultState(), BlockPos.ORIGIN)), BiomePlacementModifier.of()));
//
//    public static final PlacedFeature ROWAN_TREE_CONFIGURED =
//            new PlacedFeature(RegistryEntry.of(ROWAN_TREE), List.of(SquarePlacementModifier.of(), PlacedFeatures.createCountExtraModifier(0, 0.1F, 1), VegetationPlacedFeatures.NOT_IN_SURFACE_WATER_MODIFIER, PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(WKBlocks.ROWAN_SAPLING.getDefaultState(), BlockPos.ORIGIN)), BiomePlacementModifier.of()));
//
//    public static final PlacedFeature SUMAC_TREE_CONFIGURED =
//            new PlacedFeature(RegistryEntry.of(SUMAC_TREE), List.of(SquarePlacementModifier.of(), PlacedFeatures.createCountExtraModifier(0, 0.1F, 1), VegetationPlacedFeatures.NOT_IN_SURFACE_WATER_MODIFIER, PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(WKBlocks.SUMAC_SAPLING.getDefaultState(), BlockPos.ORIGIN)), BiomePlacementModifier.of()));
//

    public static void register() {
        RegistryKey<ConfiguredFeature<?, ?>> blackthornTree = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier("witcheskitchen", "tree_blackthorn"));
        RegistryKey<ConfiguredFeature<?, ?>> elderTree = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier("witcheskitchen", "tree_elder"));
        RegistryKey<ConfiguredFeature<?, ?>> elderElderTree = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier("witcheskitchen", "tree_elder_elder"));
        RegistryKey<ConfiguredFeature<?, ?>> hawthornTree = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier("witcheskitchen", "tree_hawthorn"));
        RegistryKey<ConfiguredFeature<?, ?>> juniperTree = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier("witcheskitchen", "tree_juniper"));
        RegistryKey<ConfiguredFeature<?, ?>> rowanTree = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier("witcheskitchen", "tree_rowan"));
        RegistryKey<ConfiguredFeature<?, ?>> sumacTree = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, new Identifier("witcheskitchen", "tree_sumac"));

        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, blackthornTree.getValue(), BLACKTHORN_TREE);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, elderTree.getValue(), ELDER_TREE);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, elderElderTree.getValue(), ELDER_ELDER_TREE);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, hawthornTree.getValue(), HAWTHORN_TREE);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, juniperTree.getValue(), JUNIPER_TREE);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, rowanTree.getValue(), ROWAN_TREE);
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, sumacTree.getValue(), SUMAC_TREE);

        if (WKConfig.get().debugMode) {
            WK.logger.info("Witches Kitchen Base Generation: Successfully Loaded");

        }
    }
}

  

