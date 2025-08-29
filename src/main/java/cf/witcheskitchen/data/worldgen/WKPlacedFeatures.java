package cf.witcheskitchen.data.worldgen;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.common.registry.WKBlocks;
import cf.witcheskitchen.common.registry.WKTags;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public interface WKPlacedFeatures {

    ResourceKey<PlacedFeature> BLACKTHORN = ResourceKey.create(Registries.PLACED_FEATURE, WitchesKitchen.id("blackthorn_tree"));
    ResourceKey<PlacedFeature> ELDER = ResourceKey.create(Registries.PLACED_FEATURE, WitchesKitchen.id("elder_tree"));
    ResourceKey<PlacedFeature> HAWTHORN = ResourceKey.create(Registries.PLACED_FEATURE, WitchesKitchen.id("hawthorn_tree"));
    ResourceKey<PlacedFeature> JUNIPER = ResourceKey.create(Registries.PLACED_FEATURE, WitchesKitchen.id("juniper_tree"));
    ResourceKey<PlacedFeature> ROWAN = ResourceKey.create(Registries.PLACED_FEATURE, WitchesKitchen.id("rowan_tree"));
    ResourceKey<PlacedFeature> SUMAC = ResourceKey.create(Registries.PLACED_FEATURE, WitchesKitchen.id("sumac_tree"));

    static void init(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configured = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, "blackthorn_tree", configured.get(WKConfiguredFeatures.CONFIGURED_FEATURE_KEYS.get(WKConfiguredFeatures.BLACKTHORN_TREE)).orElseThrow(), 10, WKBlocks.BLACKTHORN_SAPLING);
        register(context, "elder_tree", configured.get(WKConfiguredFeatures.CONFIGURED_FEATURE_KEYS.get(WKConfiguredFeatures.ELDER_TREE)).orElseThrow(), 10, WKBlocks.BLACKTHORN_SAPLING);
        register(context, "hawthorn_tree", configured.get(WKConfiguredFeatures.CONFIGURED_FEATURE_KEYS.get(WKConfiguredFeatures.HAWTHORN_TREE)).orElseThrow(), 10, WKBlocks.BLACKTHORN_SAPLING);
        register(context, "juniper_tree", configured.get(WKConfiguredFeatures.CONFIGURED_FEATURE_KEYS.get(WKConfiguredFeatures.JUNIPER_TREE)).orElseThrow(), 10, WKBlocks.BLACKTHORN_SAPLING);
        register(context, "rowan_tree", configured.get(WKConfiguredFeatures.CONFIGURED_FEATURE_KEYS.get(WKConfiguredFeatures.ROWAN_TREE)).orElseThrow(), 10, WKBlocks.BLACKTHORN_SAPLING);
        register(context, "sumac_tree", configured.get(WKConfiguredFeatures.CONFIGURED_FEATURE_KEYS.get(WKConfiguredFeatures.SUMAC_TREE)).orElseThrow(), 10, WKBlocks.BLACKTHORN_SAPLING);

        BiomeModification biomeMod = BiomeModifications.create(WitchesKitchen.id("worldgen"));

        addTree(biomeMod, WKTags.HAS_BLACKTHORN, BLACKTHORN);
        addTree(biomeMod, WKTags.HAS_ELDER, ELDER);
        addTree(biomeMod, WKTags.HAS_HAWTHORN, HAWTHORN);
        addTree(biomeMod, WKTags.HAS_JUNIPER, JUNIPER);
        addTree(biomeMod, WKTags.HAS_ROWAN, ROWAN);
        addTree(biomeMod, WKTags.HAS_SUMAC, SUMAC);
    }

    static void register(BootstrapContext<PlacedFeature> registry, String id, Holder<ConfiguredFeature<?, ?>> feature, int rarity, Block sapling) {
        registry.register(ResourceKey.create(Registries.PLACED_FEATURE, WitchesKitchen.id(id)), new PlacedFeature(feature, VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(rarity), sapling)));
    }

    static void addTree(BiomeModification biomeMod, TagKey<Biome> tag, ResourceKey<PlacedFeature> featureRegistryKey) {
        biomeMod.add(ModificationPhase.ADDITIONS, b -> b.hasTag(tag), context -> context.getGenerationSettings().addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, featureRegistryKey));
    }
}
