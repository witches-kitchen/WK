package cf.witcheskitchen.datagen.worldgen;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.common.registry.WKBlocks;
import cf.witcheskitchen.common.registry.WKTags;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.tag.BiomeTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Holder;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.RarityFilterPlacementModifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.util.PlacedFeatureUtil;
import org.quiltmc.qsl.worldgen.biome.api.*;

import java.util.List;
import java.util.function.Predicate;

public interface WKPlacedFeatures {

    static void init(){
        BiomeModification worldGen = BiomeModifications.create(WitchesKitchen.id("world_features"));
        addTreeToBiomeTag(worldGen, WKTags.HAS_BLACKTHORN, BLACKTHORN_TREE);
        addTreeToBiomeTag(worldGen, WKTags.HAS_ELDER, ELDER_TREE);
        addTreeToBiomeTag(worldGen, WKTags.HAS_HAWTHORN, HAWTHORN_TREE);
        addTreeToBiomeTag(worldGen, WKTags.HAS_JUNIPER, JUNIPER_TREE);
        addTreeToBiomeTag(worldGen, WKTags.HAS_ROWAN, ROWAN_TREE);
        addTreeToBiomeTag(worldGen, WKTags.HAS_SUMAC, SUMAC_TREE);

    }

    Holder<PlacedFeature> BLACKTHORN_TREE = registerBasicTree("blackthorn_tree", WKConfiguredFeatures.BLACKTHORN_TREE, 8);
    Holder<PlacedFeature> ELDER_TREE = registerBasicTree("elder_tree", WKConfiguredFeatures.ELDER_TREE, 8);
    Holder<PlacedFeature> HAWTHORN_TREE = registerBasicTree("hawthorn_tree", WKConfiguredFeatures.HAWTHORN_TREE, 8);
    Holder<PlacedFeature> JUNIPER_TREE = registerBasicTree("juniper_tree", WKConfiguredFeatures.JUNIPER_TREE, 8);
    Holder<PlacedFeature> ROWAN_TREE = registerBasicTree("rowan_tree", WKConfiguredFeatures.ROWAN_TREE, 8);
    Holder<PlacedFeature> SUMAC_TREE = registerBasicTree("sumac_tree", WKConfiguredFeatures.SUMAC_TREE, 8);


    static <F extends FeatureConfig> Holder<PlacedFeature> register(String id, Holder<ConfiguredFeature<F, ?>> entry, List<PlacementModifier> modifers) {
        return PlacedFeatureUtil.register(WitchesKitchen.MODID + ":" + id, entry, modifers);
    }

    static void addTreeToBiomeTag(BiomeModification worldGen, TagKey<Biome> biomes, Holder<PlacedFeature> placedFeature){
        worldGen.add(ModificationPhase.ADDITIONS, BiomeSelectors.isIn(biomes), c -> c.getGenerationSettings()
                .addBuiltInFeature(GenerationStep.Feature.VEGETAL_DECORATION, placedFeature.value()));
    }

    static <F extends FeatureConfig> Holder<PlacedFeature> registerBasicTree(String id, Holder<ConfiguredFeature<F, ?>> feature, int rarity){
        return register(id, feature, VegetationPlacedFeatures.treePlacementModifiers(RarityFilterPlacementModifier.create(rarity), WKBlocks.BLACKTHORN_SAPLING));
    }
}
