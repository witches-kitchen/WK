package cf.witcheskitchen.data.worldgen;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.common.registry.WKBlocks;
import cf.witcheskitchen.common.registry.WKTags;
import net.minecraft.block.Block;
import net.minecraft.registry.Holder;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.RarityFilterPlacementModifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import org.quiltmc.qsl.registry.api.event.DynamicRegistryManagerSetupContext;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModification;
import org.quiltmc.qsl.worldgen.biome.api.BiomeModifications;
import org.quiltmc.qsl.worldgen.biome.api.ModificationPhase;

public interface WKPlacedFeatures {

    RegistryKey<PlacedFeature> BLACKTHORN = RegistryKey.of(RegistryKeys.PLACED_FEATURE, WitchesKitchen.id("blackthorn_tree"));
    RegistryKey<PlacedFeature> ELDER = RegistryKey.of(RegistryKeys.PLACED_FEATURE, WitchesKitchen.id("elder_tree"));
    RegistryKey<PlacedFeature> HAWTHORN = RegistryKey.of(RegistryKeys.PLACED_FEATURE, WitchesKitchen.id("hawthorn_tree"));
    RegistryKey<PlacedFeature> JUNIPER = RegistryKey.of(RegistryKeys.PLACED_FEATURE, WitchesKitchen.id("juniper_tree"));
    RegistryKey<PlacedFeature> ROWAN = RegistryKey.of(RegistryKeys.PLACED_FEATURE, WitchesKitchen.id("rowan_tree"));
    RegistryKey<PlacedFeature> SUMAC = RegistryKey.of(RegistryKeys.PLACED_FEATURE, WitchesKitchen.id("sumac_tree"));

    static void init() {

    }

    static void init(Registry<ConfiguredFeature<?, ?>> configured, DynamicRegistryManagerSetupContext.RegistryMap registryMap) {
        register(registryMap, "blackthorn_tree", configured.getHolder(WKConfiguredFeatures.CONFIGURED_FEATURE_KEYS.get(WKConfiguredFeatures.BLACKTHORN_TREE)).orElseThrow(), 10, WKBlocks.BLACKTHORN_SAPLING);
        register(registryMap, "elder_tree", configured.getHolder(WKConfiguredFeatures.CONFIGURED_FEATURE_KEYS.get(WKConfiguredFeatures.ELDER_TREE)).orElseThrow(), 10, WKBlocks.BLACKTHORN_SAPLING);
        register(registryMap, "hawthorn_tree", configured.getHolder(WKConfiguredFeatures.CONFIGURED_FEATURE_KEYS.get(WKConfiguredFeatures.HAWTHORN_TREE)).orElseThrow(), 10, WKBlocks.BLACKTHORN_SAPLING);
        register(registryMap, "juniper_tree", configured.getHolder(WKConfiguredFeatures.CONFIGURED_FEATURE_KEYS.get(WKConfiguredFeatures.JUNIPER_TREE)).orElseThrow(), 10, WKBlocks.BLACKTHORN_SAPLING);
        register(registryMap, "rowan_tree", configured.getHolder(WKConfiguredFeatures.CONFIGURED_FEATURE_KEYS.get(WKConfiguredFeatures.ROWAN_TREE)).orElseThrow(), 10, WKBlocks.BLACKTHORN_SAPLING);
        register(registryMap, "sumac_tree", configured.getHolder(WKConfiguredFeatures.CONFIGURED_FEATURE_KEYS.get(WKConfiguredFeatures.SUMAC_TREE)).orElseThrow(), 10, WKBlocks.BLACKTHORN_SAPLING);

        BiomeModification biomeMod = BiomeModifications.create(WitchesKitchen.id("worldgen"));

        addTree(biomeMod, WKTags.HAS_BLACKTHORN, BLACKTHORN);
        addTree(biomeMod, WKTags.HAS_ELDER, ELDER);
        addTree(biomeMod, WKTags.HAS_HAWTHORN, HAWTHORN);
        addTree(biomeMod, WKTags.HAS_JUNIPER, JUNIPER);
        addTree(biomeMod, WKTags.HAS_ROWAN, ROWAN);
        addTree(biomeMod, WKTags.HAS_SUMAC, SUMAC);
    }

    static void register(DynamicRegistryManagerSetupContext.RegistryMap registryMap, String id, Holder<ConfiguredFeature<?, ?>> feature, int rarity, Block sapling) {
        registryMap.register(RegistryKeys.PLACED_FEATURE, WitchesKitchen.id(id), new PlacedFeature(feature, VegetationPlacedFeatures.treePlacementModifiers(RarityFilterPlacementModifier.create(rarity), sapling)));
    }

    static void addTree(BiomeModification biomeMod, TagKey<Biome> tag, RegistryKey<PlacedFeature> featureRegistryKey) {
        biomeMod.add(ModificationPhase.ADDITIONS, b -> b.isIn(tag), context -> context.getGenerationSettings().addFeature(GenerationStep.Feature.TOP_LAYER_MODIFICATION, featureRegistryKey));
    }
}
