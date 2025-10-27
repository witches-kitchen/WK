package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.api.block.crop.WKCropBlock;
import cf.witcheskitchen.api.block.crop.WKTallCropBlock;
import cf.witcheskitchen.api.block.plant.WildPlantCropBlock;
import cf.witcheskitchen.api.block.plant.WildTallPlantCropBlock;
import cf.witcheskitchen.api.registry.ObjectDefinition;
import cf.witcheskitchen.common.block.*;
import cf.witcheskitchen.common.block.crop.*;
import cf.witcheskitchen.common.block.crop.types.*;
import cf.witcheskitchen.common.block.sapling.WKSaplingBlock;
import cf.witcheskitchen.data.worldgen.WKConfiguredFeatures;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.material.MapColor;

import java.util.*;

public interface WKBlocks {

    List<ObjectDefinition<Block>> BLOCKS = new ArrayList<>();
    List<ObjectDefinition<Item>> ITEMS = new ArrayList<>();
    Map<String, Block> TYPE_BLOCKS = new LinkedHashMap<>();
    Block ELDER_PLANKS = register("elder_planks", new Block(plankSettings()), true);
    Block ELDER_STAIRS = registerWoodenStair("elder_stairs", ELDER_PLANKS);
    Block SUMAC_PLANKS = register("sumac_planks", new Block(plankSettings()), true);
    Block SUMAC_STAIRS = registerWoodenStair("sumac_stairs", SUMAC_PLANKS);
    Block HAWTHORN_PLANKS = register("hawthorn_planks", new Block(plankSettings()), true);
    Block HAWTHORN_STAIRS = registerWoodenStair("hawthorn_stairs", HAWTHORN_PLANKS);
    Block BLACKTHORN_PLANKS = register("blackthorn_planks", new Block(plankSettings()), true);
    Block BLACKTHORN_STAIRS = registerWoodenStair("blackthorn_stairs", BLACKTHORN_PLANKS);
    Block JUNIPER_PLANKS = register("juniper_planks", new Block(plankSettings()), true);
    Block JUNIPER_STAIRS = registerWoodenStair("juniper_stairs", JUNIPER_PLANKS);
    Block ROWAN_PLANKS = register("rowan_planks", new Block(plankSettings()), true);
    Block ROWAN_STAIRS = registerWoodenStair("rowan_stairs", ROWAN_PLANKS);
    Block ELDER_LOG = registerLog("elder_log");
    Block SUMAC_LOG = registerLog("sumac_log");
    Block HAWTHORN_LOG = registerLog("hawthorn_log");
    Block BLACKTHORN_LOG = register("blackthorn_log", new BlackthornPillarBlock(logSettings()), true);
    Block JUNIPER_LOG = registerLog("juniper_log");
    Block ROWAN_LOG = registerLog("rowan_log");
    Block STRIPPED_BLACKTHORN_LOG = registerLog("stripped_blackthorn_log");
    Block STRIPPED_ELDER_LOG = registerLog("stripped_elder_log");
    Block STRIPPED_HAWTHORN_LOG = registerLog("stripped_hawthorn_log");
    Block STRIPPED_JUNIPER_LOG = registerLog("stripped_juniper_log");
    Block STRIPPED_ROWAN_LOG = registerLog("stripped_rowan_log");
    Block STRIPPED_SUMAC_LOG = registerLog("stripped_sumac_log");
    Block BLACKTHORN_WOOD = registerWood("blackthorn_wood", MapColor.TERRACOTTA_BLACK);
    Block STRIPPED_BLACKTHORN_WOOD = registerWood("stripped_blackthorn_wood", MapColor.TERRACOTTA_BLACK);
    Block ELDER_WOOD = registerWood("elder_wood", MapColor.WOOD);
    Block STRIPPED_ELDER_WOOD = registerWood("stripped_elder_wood", MapColor.WOOD);
    Block HAWTHORN_WOOD = registerWood("hawthorn_wood", MapColor.DIRT);
    Block STRIPPED_HAWTHORN_WOOD = registerWood("stripped_hawthorn_wood", MapColor.DIRT);
    Block JUNIPER_WOOD = registerWood("juniper_wood", MapColor.DEEPSLATE);
    Block STRIPPED_JUNIPER_WOOD = registerWood("stripped_juniper_wood", MapColor.DEEPSLATE);
    Block ROWAN_WOOD = registerWood("rowan_wood", MapColor.TERRACOTTA_BLACK);
    Block STRIPPED_ROWAN_WOOD = registerWood("stripped_rowan_wood", MapColor.TERRACOTTA_BLACK);
    Block SUMAC_WOOD = registerWood("sumac_wood", MapColor.WOOD);
    Block STRIPPED_SUMAC_WOOD = registerWood("stripped_sumac_wood", MapColor.WOOD);
    Block ELDER_SLAB = registerSlab("elder_slab");
    Block SUMAC_SLAB = registerSlab("sumac_slab");
    Block HAWTHORN_SLAB = registerSlab("hawthorn_slab");
    Block BLACKTHORN_SLAB = registerSlab("blackthorn_slab");
    Block JUNIPER_SLAB = registerSlab("juniper_slab");
    Block ROWAN_SLAB = registerSlab("rowan_slab");
    Block ELDER_LEAVES = registerLeaf("elder_leaves");
    Block SUMAC_LEAVES = registerLeaf("sumac_leaves");
    Block HAWTHORN_LEAVES = registerLeaf("hawthorn_leaves");
    Block BLACKTHORN_LEAVES = register("blackthorn_leaves", new BlackthornLeavesBlock(leavesSettings()), true);
    Block JUNIPER_LEAVES = registerLeaf("juniper_leaves");
    Block ROWAN_LEAVES = registerLeaf("rowan_leaves");
    Block BLACKTHORN_DOOR = register("blackthorn_door", createDoorBlock(plankSettings()), true);
    Block ELDER_DOOR = register("elder_door", createDoorBlock(plankSettings()), true);
    Block HAWTHORN_DOOR = register("hawthorn_door", createDoorBlock(plankSettings()), true);
    Block JUNIPER_DOOR = register("juniper_door", createDoorBlock(plankSettings()), true);
    Block ROWAN_DOOR = register("rowan_door", createDoorBlock(plankSettings()), true);
    Block SUMAC_DOOR = register("sumac_door", createDoorBlock(plankSettings()), true);
    Block BLACKTHORN_FENCE = register("blackthorn_fence", new FenceBlock(plankSettings()), true);
    Block ELDER_FENCE = register("elder_fence", new FenceBlock(plankSettings()), true);
    Block HAWTHORN_FENCE = register("hawthorn_fence", new FenceBlock(plankSettings()), true);
    Block JUNIPER_FENCE = register("juniper_fence", new FenceBlock(plankSettings()), true);
    Block ROWAN_FENCE = register("rowan_fence", new FenceBlock(plankSettings()), true);
    Block SUMAC_FENCE = register("sumac_fence", new FenceBlock(plankSettings()), true);
    Block BLACKTHORN_FENCE_GATE = register("blackthorn_fence_gate", new FenceGateBlock(WoodType.OAK, plankSettings()), true);
    Block ELDER_FENCE_GATE = register("elder_fence_gate", new FenceGateBlock(WoodType.OAK, plankSettings()), true);
    Block HAWTHORN_FENCE_GATE = register("hawthorn_fence_gate", new FenceGateBlock(WoodType.OAK, plankSettings()), true);
    Block JUNIPER_FENCE_GATE = register("juniper_fence_gate", new FenceGateBlock(WoodType.OAK, plankSettings()), true);
    Block ROWAN_FENCE_GATE = register("rowan_fence_gate", new FenceGateBlock(WoodType.OAK, plankSettings()), true);
    Block SUMAC_FENCE_GATE = register("sumac_fence_gate", new FenceGateBlock(WoodType.OAK, plankSettings()), true);
    Block BLACKTHORN_PRESSURE_PLATE = register("blackthorn_pressure_plate", new PressurePlateBlock(BlockSetType.OAK, plankSettings()), true);
    Block ELDER_PRESSURE_PLATE = register("elder_pressure_plate", new PressurePlateBlock(BlockSetType.OAK, plankSettings()), true);
    Block HAWTHORN_PRESSURE_PLATE = register("hawthorn_pressure_plate", new PressurePlateBlock(BlockSetType.OAK, plankSettings()), true);
    Block JUNIPER_PRESSURE_PLATE = register("juniper_pressure_plate", new PressurePlateBlock(BlockSetType.OAK, plankSettings()), true);
    Block ROWAN_PRESSURE_PLATE = register("rowan_pressure_plate", new PressurePlateBlock(BlockSetType.OAK, plankSettings()), true);
    Block SUMAC_PRESSURE_PLATE = register("sumac_pressure_plate", new PressurePlateBlock(BlockSetType.OAK, plankSettings()), true);
    Block BLACKTHORN_BUTTON = register("blackthorn_button", new ButtonBlock(BlockSetType.OAK, 30, plankSettings()), true);
    Block ELDER_BUTTON = register("elder_button", new ButtonBlock(BlockSetType.OAK, 30, plankSettings()), true);
    Block HAWTHORN_BUTTON = register("hawthorn_button", new ButtonBlock(BlockSetType.OAK, 30, plankSettings()), true);
    Block JUNIPER_BUTTON = register("juniper_button", new ButtonBlock(BlockSetType.OAK, 30, plankSettings()), true);
    Block ROWAN_BUTTON = register("rowan_button", new ButtonBlock(BlockSetType.OAK, 30, plankSettings()), true);
    Block SUMAC_BUTTON = register("sumac_button", new ButtonBlock(BlockSetType.OAK, 30, plankSettings()), true);
    //Tile Entities
    Block TEAPOT = register("teapot", new TeapotBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TERRACOTTA).noOcclusion()), true);
    Block CAST_IRON_TEAPOT = register("cast_iron_teapot", new TeapotBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion()), true);
    Block COPPER_TEAPOT = register("copper_teapot", new CopperTeapotBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).noOcclusion(), WeatheringCopper.WeatherState.UNAFFECTED), true);
    Block WAXED_COPPER_TEAPOT = register("waxed_copper_teapot", new TeapotBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).noOcclusion()), true);
    Block EXPOSED_COPPER_TEAPOT = register("exposed_copper_teapot", new CopperTeapotBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.EXPOSED_COPPER).noOcclusion(), WeatheringCopper.WeatherState.EXPOSED), true);
    Block WAXED_EXPOSED_COPPER_TEAPOT = register("waxed_exposed_copper_teapot", new TeapotBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).noOcclusion()), true);
    Block WEATHERED_COPPER_TEAPOT = register("weathered_copper_teapot", new CopperTeapotBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WEATHERED_COPPER).noOcclusion(), WeatheringCopper.WeatherState.WEATHERED), true);
    Block WAXED_WEATHERED_COPPER_TEAPOT = register("waxed_weathered_copper_teapot", new TeapotBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).noOcclusion()), true);
    Block OXIDIZED_COPPER_TEAPOT = register("oxidized_copper_teapot", new CopperTeapotBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OXIDIZED_COPPER).noOcclusion(), WeatheringCopper.WeatherState.OXIDIZED), true);
    Block WAXED_OXIDIZED_COPPER_TEAPOT = register("waxed_oxidized_copper_teapot", new TeapotBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).noOcclusion()), true);
    Block IRON_WITCHES_OVEN = register("iron_witches_oven", new WitchesOvenBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).strength(4.0F, 5.0F).requiresCorrectToolForDrops().noOcclusion().lightLevel(state -> state.getValue(WitchesOvenBlock.LIT) ? 13 : 0)), true);
    Block COPPER_WITCHES_OVEN = register("copper_witches_oven", new CopperWitchesOvenBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).lightLevel(state -> state.getValue(WitchesOvenBlock.LIT) ? 13 : 0), WeatheringCopper.WeatherState.UNAFFECTED), true);
    Block WAXED_COPPER_WITCHES_OVEN = register("waxed_copper_witches_oven", new WitchesOvenBlock(BlockBehaviour.Properties.ofFullCopy(COPPER_WITCHES_OVEN)), true);
    Block EXPOSED_COPPER_WITCHES_OVEN = register("exposed_copper_witches_oven", new CopperWitchesOvenBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.EXPOSED_COPPER).lightLevel(state -> state.getValue(WitchesOvenBlock.LIT) ? 13 : 0), WeatheringCopper.WeatherState.EXPOSED), true);
    Block WAXED_EXPOSED_COPPER_WITCHES_OVEN = register("waxed_exposed_copper_witches_oven", new WitchesOvenBlock(BlockBehaviour.Properties.ofFullCopy(EXPOSED_COPPER_WITCHES_OVEN)), true);
    Block WEATHERED_COPPER_WITCHES_OVEN = register("weathered_copper_witches_oven", new CopperWitchesOvenBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WEATHERED_COPPER).lightLevel(state -> state.getValue(WitchesOvenBlock.LIT) ? 13 : 0), WeatheringCopper.WeatherState.WEATHERED), true);
    Block WAXED_WEATHERED_COPPER_WITCHES_OVEN = register("waxed_weathered_copper_witches_oven", new WitchesOvenBlock(BlockBehaviour.Properties.ofFullCopy(WEATHERED_COPPER_WITCHES_OVEN)), true);
    Block OXIDIZED_COPPER_WITCHES_OVEN = register("oxidized_copper_witches_oven", new CopperWitchesOvenBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OXIDIZED_COPPER).lightLevel(state -> state.getValue(WitchesOvenBlock.LIT) ? 13 : 0), WeatheringCopper.WeatherState.OXIDIZED), true);
    Block WAXED_OXIDIZED_COPPER_WITCHES_OVEN = register("waxed_oxidized_copper_witches_oven", new WitchesOvenBlock(BlockBehaviour.Properties.ofFullCopy(OXIDIZED_COPPER_WITCHES_OVEN)), true);
    Block GLYPH = register("glyph", new GlyphBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT).noCollision().noLootTable().strength(1, 0)), false);
    Block ENCHANTED_GLYPH = register("enchanted_glyph", new GlyphBlock(BlockBehaviour.Properties.ofFullCopy(GLYPH)), false);
    Block IRON_WITCHES_CAULDRON = register("iron_witches_cauldron", new WitchesCauldronBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CAULDRON).lightLevel(state -> state.getValue(WitchesCauldronBlock.LIT) ? 13 : 0)), true);
    Block OAK_BREWING_BARREL = registerBarrel("oak_brewing_barrel");
    Block SPRUCE_BREWING_BARREL = registerBarrel("spruce_brewing_barrel");
    Block BIRCH_BREWING_BARREL = registerBarrel("birch_brewing_barrel");
    Block JUNGLE_BREWING_BARREL = registerBarrel("jungle_brewing_barrel");
    Block ACACIA_BREWING_BARREL = registerBarrel("acacia_brewing_barrel");
    Block DARK_OAK_BREWING_BARREL = registerBarrel("dark_oak_brewing_barrel");
    Block CRIMSON_BREWING_BARREL = registerBarrel("crimson_brewing_barrel");
    Block WARPED_BREWING_BARREL = registerBarrel("warped_brewing_barrel");
    //Sapling
    Block BLACKTHORN_SAPLING = registerSapling("blackthorn_sapling", WKConfiguredFeatures.BLACKTHORN_TREE);
    Block POTTED_BLACKTHORN_SAPLING = registerPottedSapling("potted_blackthorn_sapling", BLACKTHORN_SAPLING);
    Block ELDER_SAPLING = registerSapling("elder_sapling", WKConfiguredFeatures.ELDER_TREE);
    Block POTTED_ELDER_SAPLING = registerPottedSapling("potted_elder_sapling", ELDER_SAPLING);
    Block HAWTHORN_SAPLING = registerSapling("hawthorn_sapling", WKConfiguredFeatures.HAWTHORN_TREE);
    Block POTTED_HAWTHORN_SAPLING = registerPottedSapling("potted_hawthorn_sapling", HAWTHORN_SAPLING);
    Block JUNIPER_SAPLING = registerSapling("juniper_sapling", WKConfiguredFeatures.JUNIPER_TREE);
    Block POTTED_JUNIPER_SAPLING = registerPottedSapling("potted_juniper_sapling", JUNIPER_SAPLING);
    Block ROWAN_SAPLING = registerSapling("rowan_sapling", WKConfiguredFeatures.ROWAN_TREE);
    Block POTTED_ROWAN_SAPLING = registerPottedSapling("potted_rowan_sapling", ROWAN_SAPLING);
    Block SUMAC_SAPLING = registerSapling("sumac_sapling", WKConfiguredFeatures.SUMAC_TREE);
    Block POTTED_SUMAC_SAPLING = registerPottedSapling("potted_sumac_sapling", SUMAC_SAPLING);
    //Crops
    WKTallCropBlock AMARANTH = registerWithType("amaranth", new AmaranthCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).noCollision().randomTicks().instabreak().sound(SoundType.CROP)));
    WKTallCropBlock AMARANTH_SWEETBERRY = registerWithType("amaranth_sweetberry", new AmaranthCropBlock(BlockBehaviour.Properties.ofFullCopy(AMARANTH), AmaranthTypes.SWEETBERRY));
    WKTallCropBlock AMARANTH_TORCH = registerWithType("amaranth_torch", new AmaranthCropBlock(BlockBehaviour.Properties.ofFullCopy(AMARANTH), AmaranthTypes.TORCH));
    WKTallCropBlock AMARANTH_SUNDEW = registerWithType("amaranth_sundew", new AmaranthCropBlock(BlockBehaviour.Properties.ofFullCopy(AMARANTH), AmaranthTypes.SUNDEW));
    WKTallCropBlock AMARANTH_CREEPER = registerWithType("amaranth_creeper", new AmaranthCropBlock(BlockBehaviour.Properties.ofFullCopy(AMARANTH), AmaranthTypes.CREEPER));
    WKTallCropBlock AMARANTH_VIRIDIAN = registerWithType("amaranth_viridian", new AmaranthCropBlock(BlockBehaviour.Properties.ofFullCopy(AMARANTH), AmaranthTypes.VIRIDIAN));
    WKTallCropBlock AMARANTH_GRISELIN = registerWithType("amaranth_griselin", new AmaranthCropBlock(BlockBehaviour.Properties.ofFullCopy(AMARANTH), AmaranthTypes.GRISELIN));
    WKTallCropBlock AMARANTH_CERISE = registerWithType("amaranth_cerise", new AmaranthCropBlock(BlockBehaviour.Properties.ofFullCopy(AMARANTH), AmaranthTypes.CERISE));
    WKTallCropBlock AMARANTH_DARK_PASSION = registerWithType("amaranth_dark_passion", new AmaranthCropBlock(BlockBehaviour.Properties.ofFullCopy(AMARANTH), AmaranthTypes.DARK_PASSION));
    WKTallCropBlock AMARANTH_FIREBIRD = registerWithType("amaranth_firebird", new AmaranthCropBlock(BlockBehaviour.Properties.ofFullCopy(AMARANTH), AmaranthTypes.FIREBIRD));
    //Plants
    Block AMARANTH_PLANT = register("amaranth_plant", new WildTallPlantCropBlock(getCropSettings(), AMARANTH, 0), false);
    WKTallCropBlock BELLADONNA = registerWithType("belladonna", new BelladonnaCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CORNFLOWER).noCollision().randomTicks().instabreak().sound(SoundType.CROP)));
    WKTallCropBlock BELLADONNA_GLOW = registerWithType("belladonna_glow", new BelladonnaCropBlock(BlockBehaviour.Properties.ofFullCopy(BELLADONNA), BelladonnaTypes.GLOW));
    WKTallCropBlock BELLADONNA_NOCTURNAL = registerWithType("belladonna_nocturnal", new BelladonnaCropBlock(BlockBehaviour.Properties.ofFullCopy(BELLADONNA), BelladonnaTypes.NOCTURNAL));
    Block BELLADONNA_PLANT = register("belladonna_plant", new WildTallPlantCropBlock(getCropSettings(), BELLADONNA, 0), false);
    WKTallCropBlock CAMELLIA = registerWithType("camellia", new CamelliaCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CORNFLOWER).noCollision().randomTicks().instabreak().sound(SoundType.CROP)));
    WKTallCropBlock CAMELLIA_BUTTERCREAM = registerWithType("camellia_buttercream", new CamelliaCropBlock(BlockBehaviour.Properties.ofFullCopy(CAMELLIA), CamelliaTypes.BUTTERCREAM));
    WKTallCropBlock CAMELLIA_BISQUE = registerWithType("camellia_bisque", new CamelliaCropBlock(BlockBehaviour.Properties.ofFullCopy(CAMELLIA), CamelliaTypes.BISQUE));
    WKTallCropBlock CAMELLIA_FLINT = registerWithType("camellia_flint", new CamelliaCropBlock(BlockBehaviour.Properties.ofFullCopy(CAMELLIA), CamelliaTypes.FLINT));
    WKTallCropBlock CAMELLIA_DEEP_LOVE = registerWithType("camellia_deep_love", new CamelliaCropBlock(BlockBehaviour.Properties.ofFullCopy(CAMELLIA), CamelliaTypes.DEEP_LOVE));
    WKCropBlock CHAMOMILE = registerWithType("chamomile", new ChamomileCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CORNFLOWER).noCollision().randomTicks().instabreak().sound(SoundType.CROP)));
    WKCropBlock CHAMOMILE_VIRESCENT = registerWithType("chamomile_virescent", new ChamomileCropBlock(BlockBehaviour.Properties.ofFullCopy(CHAMOMILE), ChamomileTypes.VIRESCENT));
    WKCropBlock CHAMOMILE_STARLETT = registerWithType("chamomile_starlett", new ChamomileCropBlock(BlockBehaviour.Properties.ofFullCopy(CHAMOMILE), ChamomileTypes.STARLETT));
    WKCropBlock CHAMOMILE_DYEWORKS = registerWithType("chamomile_dyeworks", new ChamomileCropBlock(BlockBehaviour.Properties.ofFullCopy(CHAMOMILE), ChamomileTypes.DYEWORKS));
    Block CHAMOMILE_PLANT = register("chamomile_plant", new WildPlantCropBlock(getCropSettings(), CHAMOMILE), false);
    WKTallCropBlock CONEFLOWER = registerWithType("coneflower", new ConeflowerCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CORNFLOWER).noCollision().randomTicks().instabreak().sound(SoundType.CROP)));
    WKTallCropBlock CONEFLOWER_DANCING_LADIES = registerWithType("coneflower_dancing_ladies", new ConeflowerCropBlock(BlockBehaviour.Properties.ofFullCopy(CONEFLOWER), ConeflowerTypes.DANCING_LADIES));
    WKTallCropBlock CONEFLOWER_VIOLET = registerWithType("coneflower_violet", new ConeflowerCropBlock(BlockBehaviour.Properties.ofFullCopy(CONEFLOWER), ConeflowerTypes.VIOLET));
    WKTallCropBlock CONEFLOWER_QUEENS_DESIRE = registerWithType("coneflower_queens_desire", new ConeflowerCropBlock(BlockBehaviour.Properties.ofFullCopy(CONEFLOWER), ConeflowerTypes.QUEENS_DESIRE));
    WKTallCropBlock CONEFLOWER_ROSE_DRESS = registerWithType("coneflower_rose_dress", new ConeflowerCropBlock(BlockBehaviour.Properties.ofFullCopy(CONEFLOWER), ConeflowerTypes.ROSE_DRESS));
    WKTallCropBlock CONEFLOWER_SUITOR = registerWithType("coneflower_suitor", new ConeflowerCropBlock(BlockBehaviour.Properties.ofFullCopy(CONEFLOWER), ConeflowerTypes.SUITOR));
    WKTallCropBlock CONEFLOWER_NETHER = registerWithType("coneflower_nether", new ConeflowerCropBlock(BlockBehaviour.Properties.ofFullCopy(CONEFLOWER), ConeflowerTypes.NETHER));
    WKTallCropBlock CONEFLOWER_LADYS_WISH = registerWithType("coneflower_ladys_wish", new ConeflowerCropBlock(BlockBehaviour.Properties.ofFullCopy(CONEFLOWER), ConeflowerTypes.LADYS_WISH));
    WKTallCropBlock CONEFLOWER_SUNGLOW = registerWithType("coneflower_sunglow", new ConeflowerCropBlock(BlockBehaviour.Properties.ofFullCopy(CONEFLOWER), ConeflowerTypes.SUNGLOW));
    WKTallCropBlock CONEFLOWER_FLAME = registerWithType("coneflower_flame", new ConeflowerCropBlock(BlockBehaviour.Properties.ofFullCopy(CONEFLOWER), ConeflowerTypes.FLAME));
    WKTallCropBlock CONEFLOWER_GILDED = registerWithType("coneflower_gilded", new ConeflowerCropBlock(BlockBehaviour.Properties.ofFullCopy(CONEFLOWER), ConeflowerTypes.GILDED));
    WKTallCropBlock CONEFLOWER_MORNING_MIST = registerWithType("coneflower_morning_mist", new ConeflowerCropBlock(BlockBehaviour.Properties.ofFullCopy(CONEFLOWER), ConeflowerTypes.MORNING_MIST));
    WKTallCropBlock CONEFLOWER_FLEECE = registerWithType("coneflower_fleece", new ConeflowerCropBlock(BlockBehaviour.Properties.ofFullCopy(CONEFLOWER), ConeflowerTypes.FLEECE));
    WKTallCropBlock CONEFLOWER_COMPANY = registerWithType("coneflower_company", new ConeflowerCropBlock(BlockBehaviour.Properties.ofFullCopy(CONEFLOWER), ConeflowerTypes.COMPANY));
    WKTallCropBlock CONEFLOWER_MASQUERADE = registerWithType("coneflower_masquerade", new ConeflowerCropBlock(BlockBehaviour.Properties.ofFullCopy(CONEFLOWER), ConeflowerTypes.MASQUERADE));
    WKTallCropBlock CONEFLOWER_PARTY_BLEND = registerWithType("coneflower_party_blend", new ConeflowerCropBlock(BlockBehaviour.Properties.ofFullCopy(CONEFLOWER), ConeflowerTypes.PARTY_BLEND));
    Block CONEFLOWER_PLANT = register("coneflower_plant", new WildTallPlantCropBlock(getCropSettings(), CONEFLOWER, 3), false);
    WKTallCropBlock FOXGLOVE = registerWithType("foxglove", new FoxgloveCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CORNFLOWER).noCollision().randomTicks().instabreak().sound(SoundType.CROP)));
    WKTallCropBlock FOXGLOVE_SMALT = registerWithType("foxglove_smalt", new FoxgloveCropBlock(BlockBehaviour.Properties.ofFullCopy(FOXGLOVE), FoxgloveTypes.SMALT));
    WKTallCropBlock FOXGLOVE_TRANQUIL_EVENING = registerWithType("foxglove_tranquil_evening", new FoxgloveCropBlock(BlockBehaviour.Properties.ofFullCopy(FOXGLOVE), FoxgloveTypes.TRANQUIL_EVENING));
    WKTallCropBlock FOXGLOVE_PURPUREA = registerWithType("foxglove_purpurea", new FoxgloveCropBlock(BlockBehaviour.Properties.ofFullCopy(FOXGLOVE), FoxgloveTypes.PURPUREA));
    WKTallCropBlock FOXGLOVE_LOVELY_MORNING = registerWithType("foxglove_lovely_morning", new FoxgloveCropBlock(BlockBehaviour.Properties.ofFullCopy(FOXGLOVE), FoxgloveTypes.LOVELY_MORNING));
    WKTallCropBlock FOXGLOVE_IANTHINE = registerWithType("foxglove_ianthine", new FoxgloveCropBlock(BlockBehaviour.Properties.ofFullCopy(FOXGLOVE), FoxgloveTypes.IANTHINE));
    WKTallCropBlock FOXGLOVE_QUEENS_HAT = registerWithType("foxglove_queens_hat", new FoxgloveCropBlock(BlockBehaviour.Properties.ofFullCopy(FOXGLOVE), FoxgloveTypes.QUEENS_HAT));
    WKTallCropBlock FOXGLOVE_BLUSH = registerWithType("foxglove_blush", new FoxgloveCropBlock(BlockBehaviour.Properties.ofFullCopy(FOXGLOVE), FoxgloveTypes.BLUSH));
    WKTallCropBlock FOXGLOVE_ROYAL_BLANKET = registerWithType("foxglove_royal_blanket", new FoxgloveCropBlock(BlockBehaviour.Properties.ofFullCopy(FOXGLOVE), FoxgloveTypes.ROYAL_BLANKET));
    WKTallCropBlock FOXGLOVE_LOVE = registerWithType("foxglove_love", new FoxgloveCropBlock(BlockBehaviour.Properties.ofFullCopy(FOXGLOVE), FoxgloveTypes.LOVE));
    WKTallCropBlock FOXGLOVE_BABYS_DRESS = registerWithType("foxglove_babys_dress", new FoxgloveCropBlock(BlockBehaviour.Properties.ofFullCopy(FOXGLOVE), FoxgloveTypes.BABYS_DRESS));
    WKTallCropBlock FOXGLOVE_STROLL = registerWithType("foxglove_stroll", new FoxgloveCropBlock(BlockBehaviour.Properties.ofFullCopy(FOXGLOVE), FoxgloveTypes.STROLL));
    WKTallCropBlock FOXGLOVE_MAIDENS_PINK = registerWithType("foxglove_maidens_pink", new FoxgloveCropBlock(BlockBehaviour.Properties.ofFullCopy(FOXGLOVE), FoxgloveTypes.MAIDENS));
    WKTallCropBlock FOXGLOVE_MORNING_FIELD = registerWithType("foxglove_morning_field", new FoxgloveCropBlock(BlockBehaviour.Properties.ofFullCopy(FOXGLOVE), FoxgloveTypes.MORNING_FIELD));
    WKTallCropBlock FOXGLOVE_SIGHE_GOWN = registerWithType("foxglove_sighe_gown", new FoxgloveCropBlock(BlockBehaviour.Properties.ofFullCopy(FOXGLOVE), FoxgloveTypes.SIGHE_GOWN));
    WKTallCropBlock FOXGLOVE_CALAMINE = registerWithType("foxglove_calamine", new FoxgloveCropBlock(BlockBehaviour.Properties.ofFullCopy(FOXGLOVE), FoxgloveTypes.CALAMINE));
    WKTallCropBlock FOXGLOVE_NETHERINE = registerWithType("foxglove_netherine", new FoxgloveCropBlock(BlockBehaviour.Properties.ofFullCopy(FOXGLOVE), FoxgloveTypes.NETHERINE));
    WKTallCropBlock FOXGLOVE_SUNGLOW = registerWithType("foxglove_sunglow", new FoxgloveCropBlock(BlockBehaviour.Properties.ofFullCopy(FOXGLOVE), FoxgloveTypes.SUNGLOW));
    WKTallCropBlock FOXGLOVE_SANDSTONE_TEMPLE = registerWithType("foxglove_sandstone_temple", new FoxgloveCropBlock(BlockBehaviour.Properties.ofFullCopy(FOXGLOVE), FoxgloveTypes.SANDSTONE_TEMPLE));
    WKTallCropBlock FOXGLOVE_FIERY_FIELD = registerWithType("foxglove_fiery_field", new FoxgloveCropBlock(BlockBehaviour.Properties.ofFullCopy(FOXGLOVE), FoxgloveTypes.FIERY_FIELD));
    WKTallCropBlock FOXGLOVE_PASSION = registerWithType("foxglove_passion", new FoxgloveCropBlock(BlockBehaviour.Properties.ofFullCopy(FOXGLOVE), FoxgloveTypes.PASSION));
    WKTallCropBlock FOXGLOVE_BASTARD_AMBER = registerWithType("foxglove_bastard_amber", new FoxgloveCropBlock(BlockBehaviour.Properties.ofFullCopy(FOXGLOVE), FoxgloveTypes.BASTARD_AMBER));
    WKTallCropBlock FOXGLOVE_SUNDROP = registerWithType("foxglove_sundrop", new FoxgloveCropBlock(BlockBehaviour.Properties.ofFullCopy(FOXGLOVE), FoxgloveTypes.SUNDROP));
    WKTallCropBlock FOXGLOVE_AURULENT = registerWithType("foxglove_aurulent", new FoxgloveCropBlock(BlockBehaviour.Properties.ofFullCopy(FOXGLOVE), FoxgloveTypes.AURULENT));
    WKTallCropBlock FOXGLOVE_IVORY = registerWithType("foxglove_ivory", new FoxgloveCropBlock(BlockBehaviour.Properties.ofFullCopy(FOXGLOVE), FoxgloveTypes.IVORY));
    WKTallCropBlock FOXGLOVE_NIVEOUS = registerWithType("foxglove_niveous", new FoxgloveCropBlock(BlockBehaviour.Properties.ofFullCopy(FOXGLOVE), FoxgloveTypes.NIVEOUS));
    WKTallCropBlock FOXGLOVE_COWS_CREAM = registerWithType("foxglove_cows_cream", new FoxgloveCropBlock(BlockBehaviour.Properties.ofFullCopy(FOXGLOVE), FoxgloveTypes.COWS_CREAM));
    WKTallCropBlock FOXGLOVE_SIGHE_MIST = registerWithType("foxglove_sighe_mist", new FoxgloveCropBlock(BlockBehaviour.Properties.ofFullCopy(FOXGLOVE), FoxgloveTypes.SIGHE_MIST));
    WKTallCropBlock FOXGLOVE_PURITY = registerWithType("foxglove_purity", new FoxgloveCropBlock(BlockBehaviour.Properties.ofFullCopy(FOXGLOVE), FoxgloveTypes.PURITY));
    Block FOXGLOVE_PLANT = register("foxglove_plant", new WildTallPlantCropBlock(getCropSettings(), FOXGLOVE, 2), false);
    WKCropBlock HELLEBORE = registerWithType("hellebore", new HelleboreCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CORNFLOWER).noCollision().randomTicks().instabreak().sound(SoundType.CROP)));
    WKCropBlock HELLEBORE_MORNING_TEA = registerWithType("hellebore_morning_tea", new HelleboreCropBlock(BlockBehaviour.Properties.ofFullCopy(HELLEBORE), HelleboreTypes.MORNING_TEA));
    WKCropBlock HELLEBORE_MORNING_CASANOVA = registerWithType("hellebore_casanova", new HelleboreCropBlock(BlockBehaviour.Properties.ofFullCopy(HELLEBORE), HelleboreTypes.CASANOVA));
    WKCropBlock HELLEBORE_MORNING_BLUSHING = registerWithType("hellebore_blushing", new HelleboreCropBlock(BlockBehaviour.Properties.ofFullCopy(HELLEBORE), HelleboreTypes.BLUSHING));
    WKCropBlock HELLEBORE_MORNING_CELADON = registerWithType("hellebore_celadon", new HelleboreCropBlock(BlockBehaviour.Properties.ofFullCopy(HELLEBORE), HelleboreTypes.CELADON));
    WKCropBlock HELLEBORE_MORNING_FURY = registerWithType("hellebore_fury", new HelleboreCropBlock(BlockBehaviour.Properties.ofFullCopy(HELLEBORE), HelleboreTypes.FURY));
    WKCropBlock HELLEBORE_MORNING_ANGEL = registerWithType("hellebore_angel", new HelleboreCropBlock(BlockBehaviour.Properties.ofFullCopy(HELLEBORE), HelleboreTypes.ANGEL));
    WKCropBlock HELLEBORE_MORNING_TWILIGHT = registerWithType("hellebore_twilight", new HelleboreCropBlock(BlockBehaviour.Properties.ofFullCopy(HELLEBORE), HelleboreTypes.TWILIGHT));
    WKCropBlock HELLEBORE_MORNING_GRIMM = registerWithType("hellebore_grimm", new HelleboreCropBlock(BlockBehaviour.Properties.ofFullCopy(HELLEBORE), HelleboreTypes.GRIMM));
    WKCropBlock HELLEBORE_MORNING_NOCTURNE = registerWithType("hellebore_nocturne", new HelleboreCropBlock(BlockBehaviour.Properties.ofFullCopy(HELLEBORE), HelleboreTypes.NOCTURNE));
    Block HELLEBORE_PLANT = register("hellebore_plant", new WildPlantCropBlock(getCropSettings(), HELLEBORE), false);
    WKTallCropBlock IRIS = registerWithType("iris", new IrisCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CORNFLOWER).noCollision().randomTicks().instabreak().sound(SoundType.CROP)));
    WKTallCropBlock IRIS_OCEAN = registerWithType("iris_ocean", new IrisCropBlock(BlockBehaviour.Properties.ofFullCopy(IRIS), IrisTypes.OCEAN));
    WKTallCropBlock IRIS_DEEP_SEA = registerWithType("iris_deep_sea", new IrisCropBlock(BlockBehaviour.Properties.ofFullCopy(IRIS), IrisTypes.DEEP_SEA));
    WKTallCropBlock IRIS_BLEEDING_HEART = registerWithType("iris_bleeding_heart", new IrisCropBlock(BlockBehaviour.Properties.ofFullCopy(IRIS), IrisTypes.BLEEDING_HEART));
    Block IRIS_PLANT = register("iris_plant", new WildTallPlantCropBlock(getCropSettings(), IRIS, 2), false);
    WKCropBlock SANGUINARY = registerWithType("sanguinary", new SanguinaryCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CORNFLOWER).noCollision().randomTicks().instabreak().sound(SoundType.CROP)));
    WKCropBlock SANGUINARY_MEADOW = registerWithType("sanguinary_meadow", new SanguinaryCropBlock(BlockBehaviour.Properties.ofFullCopy(SANGUINARY), SanguinaryTypes.MEADOW));
    WKCropBlock SANGUINARY_BLUSHING = registerWithType("sanguinary_blushing", new SanguinaryCropBlock(BlockBehaviour.Properties.ofFullCopy(SANGUINARY), SanguinaryTypes.BLUSHING));
    WKCropBlock SANGUINARY_SUNSET = registerWithType("sanguinary_sunset", new SanguinaryCropBlock(BlockBehaviour.Properties.ofFullCopy(SANGUINARY), SanguinaryTypes.SUNSET));
    WKCropBlock SANGUINARY_MADDER = registerWithType("sanguinary_madder", new SanguinaryCropBlock(BlockBehaviour.Properties.ofFullCopy(SANGUINARY), SanguinaryTypes.MADDER));
    WKCropBlock SANGUINARY_AUREOLIN = registerWithType("sanguinary_aureolin", new SanguinaryCropBlock(BlockBehaviour.Properties.ofFullCopy(SANGUINARY), SanguinaryTypes.AUREOLIN));
    Block SANGUINARY_PLANT = register("sanguinary_plant", new WildPlantCropBlock(getCropSettings(), SANGUINARY), false);
    Block MINT = register("mint", new MintCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CORNFLOWER).noCollision().randomTicks().instabreak().sound(SoundType.CROP)), false);
    Block WORMWOOD = register("wormwood", new WormwoodCropBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CORNFLOWER).noCollision().randomTicks().instabreak().sound(SoundType.CROP)), false);
    Block SALT_BLOCK = register("salt", new SaltBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FLOWER_POT).noCollision().instabreak()), true);

    static BlockBehaviour.Properties getCropSettings() {
        return BlockBehaviour.Properties.ofFullCopy(Blocks.CORNFLOWER).noCollision().instabreak().sound(SoundType.CROP);
    }

    static DoorBlock createDoorBlock(BlockBehaviour.Properties settings) {
        return new DoorBlock(BlockSetType.OAK, settings);
    }

    /**
     * Returns an <a href="Collection.html#unmodview">read-only view</a> of the WitchesKitchen's Blocks
     */
    static List<ObjectDefinition<Block>> getBlocks() {
        return Collections.unmodifiableList(BLOCKS);
    }

    /**
     * Returns an <a href="Collection.html#unmodview">read-only view</a> of the WitchesKitchen's BlockItems
     */
    static List<ObjectDefinition<Item>> getItems() {
        return Collections.unmodifiableList(ITEMS);
    }

    static Map<String, Block> getTypeBlocks() {
        return TYPE_BLOCKS;
    }

    static BlockBehaviour.Properties leavesSettings() {
        return BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)
            .strength(0.2F)
            .randomTicks()
            .sound(SoundType.GRASS)
            .noOcclusion()
            .isValidSpawn(Blocks::ocelotOrParrot)
            .isSuffocating(Blocks::never)
            .isViewBlocking(Blocks::never);
    }

    static BlockBehaviour.Properties plankSettings() {
        return BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0f);
    }

    static BlockBehaviour.Properties logSettings() {
        return BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).strength(2.0f);
    }

    private static Block registerBarrel(String path) {
        return register(path, new BrewingBarrelBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BARREL).noOcclusion().strength(2.5F)), true);
    }

    private static Block registerPottedSapling(String path, Block block) {
        final Block pottedSapling = new FlowerPotBlock(block, BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_OAK_SAPLING).instabreak().noOcclusion());
        BLOCKS.add(new ObjectDefinition<>(WitchesKitchen.id(path), pottedSapling));
        return pottedSapling;
    }

    private static Block registerSapling(String path, ConfiguredFeature<TreeConfiguration, ?> feature) {
        final Block sapling = new WKSaplingBlock(new TreeGrower(path, Optional.empty(), Optional.of(WKConfiguredFeatures.CONFIGURED_FEATURE_KEYS.get(feature)), Optional.empty()),
            BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)
                .noCollision()
                .randomTicks()
                .instabreak()
                .sound(SoundType.GRASS));
        return register(path, sapling, true);
    }

    private static Block registerLeaf(String path) {
        return register(path, new UntintedParticleLeavesBlock(0.0f, ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, 16777215), leavesSettings()), true);
    }

    private static Block registerWood(String path, MapColor color) {
        final RotatedPillarBlock wood = new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).mapColor(color).strength(2.0f).sound(SoundType.WOOD));
        return register(path, wood, true);
    }

    private static RotatedPillarBlock registerLog(String path) {
        return register(path, new RotatedPillarBlock(logSettings()), true);
    }

    private static Block registerSlab(String path) {
        return register(path, new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB)), true);
    }

    private static Block registerWoodenStair(String path, Block block) {
        return register(path, new StairBlock(block.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS)), true);
    }

    static <T extends Block> T registerWithType(String name, T block) {
        TYPE_BLOCKS.put(name, block);
        BLOCKS.add(new ObjectDefinition<>(WitchesKitchen.id(name), block));
        return block;
    }

    static <T extends Block> T register(String path, T block, boolean createItem) {
        ResourceLocation id = WitchesKitchen.id(path);
        BLOCKS.add(new ObjectDefinition<>(id, block));
        if (createItem) {
            ITEMS.add(new ObjectDefinition<>(id, new BlockItem(block, new Item.Properties())));
        }
        return block;
    }

    static void init() {
        BLOCKS.forEach(entry -> Registry.register(BuiltInRegistries.BLOCK, entry.id(), entry.object()));
        ITEMS.forEach(entry -> Registry.register(BuiltInRegistries.ITEM, entry.id(), entry.object()));
    }
}
