package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.api.registry.ObjectDefinition;
import cf.witcheskitchen.common.block.*;
import cf.witcheskitchen.common.block.sapling.WKSaplingBlock;
import cf.witcheskitchen.common.crop.*;
import cf.witcheskitchen.common.generator.WKSaplingGenerator;
import cf.witcheskitchen.common.variants.*;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Holder;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface WKBlocks {

    ArrayList<Block> LEAF_BLOCKS = new ArrayList<>();
    List<ObjectDefinition<Block>> BLOCKS = new ArrayList<>();
    List<ObjectDefinition<Item>> ITEMS = new ArrayList<>();
    Map<String, Block> TYPE_BLOCKS = new LinkedHashMap<>();


    Block SALT_BLOCK = register("salt", Material.DECORATION, settings -> new SaltBlock(settings.noCollision().breakInstantly()));

    Block RAW_GINGERBREAD_BLOCK = registerGingerBread("raw_gingerbread_block");
    Block RAW_CHISELED_GINGERBREAD_BLOCK = registerGingerBread("raw_chiseled_gingerbread_block");
    Block GINGERBREAD_BEVELED_BLOCK = registerGingerBread("gingerbread_beveled_block");
    Block GINGERBREAD_BLOCK = registerGingerBread("gingerbread_block");
    Block RAW_GINGERBREAD_BLOCK_STAIRS = registerGingerTab("raw_gingerbread_block_stairs", Material.CAKE, settings -> new StairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    Block RAW_CHISELED_GINGERBREAD_BLOCK_STAIRS = registerGingerTab("raw_chiseled_gingerbread_block_stairs", Material.CAKE, settings -> new StairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    Block GINGERBREAD_BEVELED_BLOCK_STAIRS = registerGingerTab("gingerbread_beveled_block_stairs", Material.CAKE, settings -> new StairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    Block GINGERBREAD_BLOCK_STAIRS = registerGingerTab("gingerbread_block_stairs", Material.CAKE, settings -> new StairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    Block RAW_GINGERBREAD_BEVELED_BLOCK_STAIRS = registerGingerTab("raw_gingerbread_beveled_block_stairs", Material.CAKE, settings -> new StairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    Block FROSTED_GINGERBREAD_BLOCK_STAIRS = registerGingerTab("frosted_gingerbread_block_stairs", Material.CAKE, settings -> new StairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    Block FROSTED_BEVELED_GINGERBREAD_BLOCK_STAIRS = registerGingerTab("frosted_beveled_gingerbread_block_stairs", Material.CAKE, settings -> new StairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    Block CHISELED_GINGERBREAD_BLOCK_STAIRS = registerGingerTab("chiseled_gingerbread_block_stairs", Material.CAKE, settings -> new StairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    Block RAW_GINGERBREAD_TILED_BLOCK_STAIRS = registerGingerTab("raw_gingerbread_tiled_block_stairs", Material.CAKE, settings -> new StairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    Block GINGERBREAD_TILED_BLOCK_STAIRS = registerGingerTab("gingerbread_tiled_block_stairs", Material.CAKE, settings -> new StairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    Block FROSTING_BLOCK_STAIRS = registerGingerTab("frosting_block_stairs", Material.CAKE, settings -> new StairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    Block FROSTED_GINGERBREAD_TILED_BLOCK_STAIRS = registerGingerTab("frosted_gingerbread_tiled_block_stairs", Material.CAKE, settings -> new StairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    Block FROSTED_BEVELED_GINGERBREAD_BLOCK_YELLOW_STAIRS = registerGingerTab("frosted_beveled_gingerbread_block_yellow_stairs", Material.CAKE, settings -> new StairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    Block FROSTED_BEVELED_GINGERBREAD_BLOCK_RED_STAIRS = registerGingerTab("frosted_beveled_gingerbread_block_red_stairs", Material.CAKE, settings -> new StairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    Block FROSTED_BEVELED_GINGERBREAD_BLOCK_PURPLE_STAIRS = registerGingerTab("frosted_beveled_gingerbread_block_purple_stairs", Material.CAKE, settings -> new StairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    Block FROSTED_BEVELED_GINGERBREAD_BLOCK_GREEN_STAIRS = registerGingerTab("frosted_beveled_gingerbread_block_green_stairs", Material.CAKE, settings -> new StairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    Block FROSTED_TILED_GINGERBREAD_BLOCK_YELLOW_STAIRS = registerGingerTab("frosted_tiled_gingerbread_block_yellow_stairs", Material.CAKE, settings -> new StairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    Block FROSTED_TILED_GINGERBREAD_BLOCK_RED_STAIRS = registerGingerTab("frosted_tiled_gingerbread_block_red_stairs", Material.CAKE, settings -> new StairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    Block FROSTED_TILED_GINGERBREAD_BLOCK_PURPLE_STAIRS = registerGingerTab("frosted_tiled_gingerbread_block_purple_stairs", Material.CAKE, settings -> new StairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    Block FROSTED_TILED_GINGERBREAD_BLOCK_GREEN_STAIRS = registerGingerTab("frosted_tiled_gingerbread_block_green_stairs", Material.CAKE, settings -> new StairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    Block FROSTED_TILED_GINGERBREAD_BLOCK_VARIANT_STAIRS = registerGingerTab("frosted_tiled_gingerbread_block_variant_stairs", Material.CAKE, settings -> new StairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    Block RAW_GINGERBREAD_BEVELED_BLOCK = registerGingerBread("raw_gingerbread_beveled_block");
    Block FROSTED_GINGERBREAD_BLOCK = registerGingerBread("frosted_gingerbread_block");
    Block FROSTED_BEVELED_GINGERBREAD_BLOCK = registerGingerBread("frosted_beveled_gingerbread_block");
    Block CHISELED_GINGERBREAD_BLOCK = registerGingerBread("chiseled_gingerbread_block");
    Block RAW_GINGERBREAD_TILED_BLOCK = registerGingerBread("raw_gingerbread_tiled_block");
    Block GINGERBREAD_TILED_BLOCK = registerGingerBread("gingerbread_tiled_block");
    Block FROSTING_BLOCK = registerGingerBread("frosting_block");
    Block FROSTED_GINGERBREAD_TILED_BLOCK = registerGingerBread("frosted_gingerbread_tiled_block");
    Block FROSTED_BEVELED_GINGERBREAD_BLOCK_YELLOW = registerGingerBread("frosted_beveled_gingerbread_block_yellow");
    Block FROSTED_BEVELED_GINGERBREAD_BLOCK_RED = registerGingerBread("frosted_beveled_gingerbread_block_red");
    Block FROSTED_BEVELED_GINGERBREAD_BLOCK_PURPLE = registerGingerBread("frosted_beveled_gingerbread_block_purple");
    Block FROSTED_BEVELED_GINGERBREAD_BLOCK_GREEN = registerGingerBread("frosted_beveled_gingerbread_block_green");
    Block FROSTED_TILED_GINGERBREAD_BLOCK_YELLOW = registerGingerBread("frosted_tiled_gingerbread_block_yellow");
    Block FROSTED_TILED_GINGERBREAD_BLOCK_RED = registerGingerBread("frosted_tiled_gingerbread_block_red");
    Block FROSTED_TILED_GINGERBREAD_BLOCK_PURPLE = registerGingerBread("frosted_tiled_gingerbread_block_purple");
    Block FROSTED_TILED_GINGERBREAD_BLOCK_GREEN = registerGingerBread("frosted_tiled_gingerbread_block_green");
    Block FROSTED_TILED_GINGERBREAD_BLOCK_VARIANT = registerGingerBread("frosted_tiled_gingerbread_block_variant");
    Block RAW_GINGERBREAD_BLOCK_SLAB = registerGingerBread("raw_gingerbread_block_slab");
    Block RAW_CHISELED_GINGERBREAD_BLOCK_SLAB = registerGingerBread("raw_chiseled_gingerbread_block_slab");
    Block GINGERBREAD_BEVELED_BLOCK_SLAB = registerGingerBread("gingerbread_beveled_block_slab");
    Block GINGERBREAD_BLOCK_SLAB = registerGingerBread("gingerbread_block_slab");
    Block RAW_GINGERBREAD_BEVELED_BLOCK_SLAB = registerGingerBread("raw_gingerbread_beveled_block_slab");
    Block FROSTED_GINGERBREAD_BLOCK_SLAB = registerGingerBread("frosted_gingerbread_block_slab");
    Block FROSTED_BEVELED_GINGERBREAD_BLOCK_SLAB = registerGingerBread("frosted_beveled_gingerbread_block_slab");
    Block CHISELED_GINGERBREAD_BLOCK_SLAB = registerGingerBread("chiseled_gingerbread_block_slab");
    Block RAW_GINGERBREAD_TILED_BLOCK_SLAB = registerGingerBread("raw_gingerbread_tiled_block_slab");
    Block GINGERBREAD_TILED_BLOCK_SLAB = registerGingerBread("gingerbread_tiled_block_slab");
    Block FROSTING_BLOCK_SLAB = registerGingerBread("frosting_block_slab");
    Block FROSTED_GINGERBREAD_TILED_BLOCK_SLAB = registerGingerBread("frosted_gingerbread_tiled_block_slab");
    Block FROSTED_BEVELED_GINGERBREAD_BLOCK_YELLOW_SLAB = registerGingerBread("frosted_beveled_gingerbread_block_yellow_slab");
    Block FROSTED_BEVELED_GINGERBREAD_BLOCK_RED_SLAB = registerGingerBread("frosted_beveled_gingerbread_block_red_slab");
    Block FROSTED_BEVELED_GINGERBREAD_BLOCK_PURPLE_SLAB = registerGingerBread("frosted_beveled_gingerbread_block_purple_slab");
    Block FROSTED_BEVELED_GINGERBREAD_BLOCK_GREEN_SLAB = registerGingerBread("frosted_beveled_gingerbread_block_green_slab");
    Block FROSTED_TILED_GINGERBREAD_BLOCK_YELLOW_SLAB = registerGingerBread("frosted_tiled_gingerbread_block_yellow_slab");
    Block FROSTED_TILED_GINGERBREAD_BLOCK_RED_SLAB = registerGingerBread("frosted_tiled_gingerbread_block_red_slab");
    Block FROSTED_TILED_GINGERBREAD_BLOCK_PURPLE_SLAB = registerGingerBread("frosted_tiled_gingerbread_block_purple_slab");
    Block FROSTED_TILED_GINGERBREAD_BLOCK_GREEN_SLAB = registerGingerBread("frosted_tiled_gingerbread_block_green_slab");
    Block FROSTED_TILED_GINGERBREAD_BLOCK_VARIANT_SLAB = registerGingerBread("frosted_tiled_gingerbread_block_variant_slab");

    Block ELDER_PLANKS = register("elder_planks", Material.WOOD);
    Block ELDER_STAIRS = registerWoodenStair("elder_stairs", ELDER_PLANKS);
    Block SUMAC_PLANKS = register("sumac_planks", Material.WOOD);
    Block SUMAC_STAIRS = registerWoodenStair("sumac_stairs", SUMAC_PLANKS);
    Block HAWTHORN_PLANKS = register("hawthorn_planks", Material.WOOD);
    Block HAWTHORN_STAIRS = registerWoodenStair("hawthorn_stairs", HAWTHORN_PLANKS);
    Block BLACKTHORN_PLANKS = register("blackthorn_planks", Material.WOOD);
    Block BLACKTHORN_STAIRS = registerWoodenStair("blackthorn_stairs", BLACKTHORN_PLANKS);
    Block JUNIPER_PLANKS = register("juniper_planks", Material.WOOD);
    Block JUNIPER_STAIRS = registerWoodenStair("juniper_stairs", JUNIPER_PLANKS);
    Block ROWAN_PLANKS = register("rowan_planks", Material.WOOD);
    Block ROWAN_STAIRS = registerWoodenStair("rowan_stairs", ROWAN_PLANKS);
    Block ELDER_LOG = registerLog("elder_log", MapColor.PALE_YELLOW, MapColor.OAK_TAN);
    Block SUMAC_LOG = registerLog("sumac_log", MapColor.DEEPSLATE_GRAY, MapColor.DARK_DULL_PINK);
    Block HAWTHORN_LOG = registerLog("hawthorn_log", MapColor.PALE_YELLOW, MapColor.DIRT_BROWN);
    Block BLACKTHORN_LOG = register("blackthorn_log", new BlackthornPillarBlock(logSettings(MapColor.TERRACOTTA_BROWN, MapColor.TERRACOTTA_BLACK)),true);
    Block JUNIPER_LOG = registerLog("juniper_log", MapColor.DIRT_BROWN, MapColor.DEEPSLATE_GRAY);
    Block ROWAN_LOG = registerLog("rowan_log", MapColor.TERRACOTTA_BLACK, MapColor.BROWN);
    Block STRIPPED_BLACKTHORN_LOG = registerLog("stripped_blackthorn_log", MapColor.TERRACOTTA_BROWN, MapColor.TERRACOTTA_BLACK);
    Block STRIPPED_ELDER_LOG = registerLog("stripped_elder_log", MapColor.PALE_YELLOW, MapColor.OAK_TAN);
    Block STRIPPED_HAWTHORN_LOG = registerLog("stripped_hawthorn_log", MapColor.PALE_YELLOW, MapColor.DIRT_BROWN);
    Block STRIPPED_JUNIPER_LOG = registerLog("stripped_juniper_log", MapColor.DIRT_BROWN, MapColor.DEEPSLATE_GRAY);
    Block STRIPPED_ROWAN_LOG = registerLog("stripped_rowan_log", MapColor.TERRACOTTA_BLACK, MapColor.BROWN);
    Block STRIPPED_SUMAC_LOG = registerLog("stripped_sumac_log", MapColor.DEEPSLATE_GRAY, MapColor.DARK_DULL_PINK);
    Block BLACKTHORN_WOOD = registerWood("blackthorn_wood", MapColor.TERRACOTTA_BLACK);
    Block STRIPPED_BLACKTHORN_WOOD = registerWood("stripped_blackthorn_wood", MapColor.TERRACOTTA_BLACK);
    Block ELDER_WOOD = registerWood("elder_wood", MapColor.OAK_TAN);
    Block STRIPPED_ELDER_WOOD = registerWood("stripped_elder_wood", MapColor.OAK_TAN);
    Block HAWTHORN_WOOD = registerWood("hawthorn_wood", MapColor.DIRT_BROWN);
    Block STRIPPED_HAWTHORN_WOOD = registerWood("stripped_hawthorn_wood", MapColor.DIRT_BROWN);
    Block JUNIPER_WOOD = registerWood("juniper_wood", MapColor.DEEPSLATE_GRAY);
    Block STRIPPED_JUNIPER_WOOD = registerWood("stripped_juniper_wood", MapColor.DEEPSLATE_GRAY);
    Block ROWAN_WOOD = registerWood("rowan_wood", MapColor.TERRACOTTA_BLACK);
    Block STRIPPED_ROWAN_WOOD = registerWood("stripped_rowan_wood", MapColor.TERRACOTTA_BLACK);
    Block SUMAC_WOOD = registerWood("sumac_wood", MapColor.OAK_TAN);
    Block STRIPPED_SUMAC_WOOD = registerWood("stripped_sumac_wood", MapColor.OAK_TAN);
    Block ELDER_SLAB = registerSlab("elder_slab");
    Block SUMAC_SLAB = registerSlab("sumac_slab");
    Block HAWTHORN_SLAB = registerSlab("hawthorn_slab");
    Block BLACKTHORN_SLAB = registerSlab("blackthorn_slab");
    Block JUNIPER_SLAB = registerSlab("juniper_slab");
    Block ROWAN_SLAB = registerSlab("rowan_slab");
    Block ELDER_LEAVES = registerLeaf("elder_leaves");
    Block ELDER_LEAVES_COLORED = registerLeaf("elder_leaves_colored");
    Block SUMAC_LEAVES = registerLeaf("sumac_leaves");
    Block HAWTHORN_LEAVES = registerLeaf("hawthorn_leaves");
    Block HAWTHORN_LEAVES_COLORED = registerLeaf("hawthorn_leaves_colored");
    Block BLACKTHORN_LEAVES = register("blackthorn_leaves", new BlackthornLeavesBlock(leavesSettings()),true);
    Block JUNIPER_LEAVES = registerLeaf("juniper_leaves");
    Block ROWAN_LEAVES = registerLeaf("rowan_leaves");
    //Tile Entities
    Block TEAPOT = register("teapot", new TeapotBlock(QuiltBlockSettings.copyOf(Blocks.AMETHYST_BLOCK)),true);
    Block IRON_WITCHES_OVEN = register("iron_witches_oven", new WitchesOvenBlock(AbstractBlock.Settings.of(Material.METAL).strength(4.0F, 5.0F).requiresTool().nonOpaque().luminance(state -> state.get(WitchesOvenBlock.LIT) ? 13 : 0)),true);
    Block COPPER_WITCHES_OVEN = register("copper_witches_oven", new CopperWitchesOvenBlock(QuiltBlockSettings.copyOf(Blocks.COPPER_BLOCK).luminance(state -> state.get(WitchesOvenBlock.LIT) ? 13 : 0), Oxidizable.OxidizationLevel.UNAFFECTED),true);
    Block WAXED_COPPER_WITCHES_OVEN = register("waxed_copper_witches_oven", new WitchesOvenBlock(QuiltBlockSettings.copy(COPPER_WITCHES_OVEN)),true);
    Block EXPOSED_COPPER_WITCHES_OVEN = register("exposed_copper_witches_oven", new CopperWitchesOvenBlock(QuiltBlockSettings.copy(Blocks.EXPOSED_COPPER).luminance(state -> state.get(WitchesOvenBlock.LIT) ? 13 : 0), Oxidizable.OxidizationLevel.EXPOSED),true);
    Block WAXED_EXPOSED_COPPER_WITCHES_OVEN = register("waxed_exposed_copper_witches_oven", new WitchesOvenBlock(QuiltBlockSettings.copy(EXPOSED_COPPER_WITCHES_OVEN)),true);
    Block WEATHERED_COPPER_WITCHES_OVEN = register("weathered_copper_witches_oven", new CopperWitchesOvenBlock(QuiltBlockSettings.copy(Blocks.WEATHERED_COPPER).luminance(state -> state.get(WitchesOvenBlock.LIT) ? 13 : 0), Oxidizable.OxidizationLevel.WEATHERED),true);
    Block WAXED_WEATHERED_COPPER_WITCHES_OVEN = register("waxed_weathered_copper_witches_oven", new WitchesOvenBlock(QuiltBlockSettings.copy(WEATHERED_COPPER_WITCHES_OVEN)),true);
    Block OXIDIZED_COPPER_WITCHES_OVEN = register("oxidized_copper_witches_oven", new CopperWitchesOvenBlock(QuiltBlockSettings.copy(Blocks.OXIDIZED_COPPER).luminance(state -> state.get(WitchesOvenBlock.LIT) ? 13 : 0), Oxidizable.OxidizationLevel.OXIDIZED),true);
    Block WAXED_OXIDIZED_COPPER_WITCHES_OVEN = register("waxed_oxidized_copper_witches_oven", new WitchesOvenBlock(QuiltBlockSettings.copy(OXIDIZED_COPPER_WITCHES_OVEN)),true);
    Block GLYPH = register("glyph", new GlyphBlock(AbstractBlock.Settings.of(Material.STONE).breakInstantly()), false);

    Block IRON_WITCHES_CAULDRON = register("iron_witches_cauldron", new WitchesCauldronBlock(QuiltBlockSettings.copy(Blocks.CAULDRON).luminance(state -> state.get(WitchesCauldronBlock.LIT) ? 13 : 0)),true);
    Block BLACKTHORN_SAPLING = registerSapling("blackthorn_sapling", WKConfiguredFeatures.BLACKTHORN_TREE);
    Block POTTED_BLACKTHORN_SAPLING = registerPottedSapling("potted_blackthorn_sapling");
    Block POTTED_ELDER_SAPLING = registerPottedSapling("potted_elder_sapling");
    Block POTTED_HAWTHORN_SAPLING = registerPottedSapling("potted_hawthorn_sapling");
    Block POTTED_JUNIPER_SAPLING = registerPottedSapling("potted_juniper_sapling");
    Block POTTED_ROWAN_SAPLING = registerPottedSapling("potted_rowan_sapling");
    Block POTTED_SUMAC_SAPLING = registerPottedSapling("potted_sumac_sapling");
    Block ELDER_SAPLING = registerSapling("elder_sapling", WKConfiguredFeatures.ELDER_TREE);
    Block HAWTHORN_SAPLING = registerSapling("hawthorn_sapling", WKConfiguredFeatures.HAWTHORN_TREE);
    Block JUNIPER_SAPLING = registerSapling("juniper_sapling", WKConfiguredFeatures.JUNIPER_TREE);
    Block ROWAN_SAPLING = registerSapling("rowan_sapling", WKConfiguredFeatures.ROWAN_TREE);
    Block SUMAC_SAPLING = registerSapling("sumac_sapling", WKConfiguredFeatures.SUMAC_TREE);
    Block OAK_BREWING_BARREL = registerBarrel("oak_brewing_barrel");
    Block SPRUCE_BREWING_BARREL = registerBarrel("spruce_brewing_barrel");
    Block BIRCH_BREWING_BARREL = registerBarrel("birch_brewing_barrel");
    Block JUNGLE_BREWING_BARREL = registerBarrel("jungle_brewing_barrel");
    Block ACACIA_BREWING_BARREL = registerBarrel("acacia_brewing_barrel");
    Block DARK_OAK_BREWING_BARREL = registerBarrel("dark_oak_brewing_barrel");
    Block CRIMSON_BREWING_BARREL = registerBarrel("crimson_brewing_barrel");
    Block WARPED_BREWING_BARREL = registerBarrel("warped_brewing_barrel");
    //Crops
    Block AMARANTH = registerWithType("amaranth", new AmaranthCropBlock(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP)));
    Block AMARANTH_SWEETBERRY = registerWithType("amaranth_sweetberry", new AmaranthCropBlock(QuiltBlockSettings.copyOf(AMARANTH), AmaranthTypes.SWEETBERRY));
    Block AMARANTH_TORCH = registerWithType("amaranth_torch", new AmaranthCropBlock(QuiltBlockSettings.copyOf(AMARANTH), AmaranthTypes.TORCH));
    Block AMARANTH_SUNDEW = registerWithType("amaranth_sundew", new AmaranthCropBlock(QuiltBlockSettings.copyOf(AMARANTH), AmaranthTypes.SUNDEW));
    Block AMARANTH_CREEPER = registerWithType("amaranth_creeper", new AmaranthCropBlock(QuiltBlockSettings.copyOf(AMARANTH), AmaranthTypes.CREEPER));
    Block AMARANTH_VIRIDIAN = registerWithType("amaranth_viridian", new AmaranthCropBlock(QuiltBlockSettings.copyOf(AMARANTH), AmaranthTypes.VIRIDIAN));
    Block AMARANTH_GRISELIN = registerWithType("amaranth_griselin", new AmaranthCropBlock(QuiltBlockSettings.copyOf(AMARANTH), AmaranthTypes.GRISELIN));
    Block AMARANTH_CERISE = registerWithType("amaranth_cerise", new AmaranthCropBlock(QuiltBlockSettings.copyOf(AMARANTH), AmaranthTypes.CERISE));
    Block AMARANTH_DARK_PASSION = registerWithType("amaranth_dark_passion", new AmaranthCropBlock(QuiltBlockSettings.copyOf(AMARANTH), AmaranthTypes.DARK_PASSION));
    Block AMARANTH_FIREBIRD = registerWithType("amaranth_firebird", new AmaranthCropBlock(QuiltBlockSettings.copyOf(AMARANTH), AmaranthTypes.FIREBIRD));

    Block BELLADONNA = registerWithType("belladonna", new BelladonnaCropBlock(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP)));
    Block BELLADONNA_GLOW = registerWithType("belladonna_glow", new BelladonnaCropBlock(QuiltBlockSettings.copyOf(BELLADONNA), BelladonnaTypes.GLOW));
    Block BELLADONNA_NOCTURNAL = registerWithType("belladonna_nocturnal", new BelladonnaCropBlock(QuiltBlockSettings.copyOf(BELLADONNA), BelladonnaTypes.NOCTURNAL));

    Block CAMELLIA = registerWithType("camellia", new CamelliaCropBlock(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP)));
    Block CAMELLIA_BUTTERCREAM = registerWithType("camellia_buttercream", new CamelliaCropBlock(QuiltBlockSettings.copyOf(CAMELLIA), CamelliaTypes.BUTTERCREAM));
    Block CAMELLIA_BISQUE = registerWithType("camellia_bisque", new CamelliaCropBlock(QuiltBlockSettings.copyOf(CAMELLIA), CamelliaTypes.BISQUE));
    Block CAMELLIA_FLINT = registerWithType("camellia_flint", new CamelliaCropBlock(QuiltBlockSettings.copyOf(CAMELLIA), CamelliaTypes.FLINT));
    Block CAMELLIA_DEEP_LOVE = registerWithType("camellia_deep_love", new CamelliaCropBlock(QuiltBlockSettings.copyOf(CAMELLIA), CamelliaTypes.DEEP_LOVE));

    Block CHAMOMILE = registerWithType("chamomile", new ChamomileCropBlock(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP)));
    Block CHAMOMILE_VIRESCENT = registerWithType("chamomile_virescent", new ChamomileCropBlock(QuiltBlockSettings.copyOf(CHAMOMILE), ChamomileTypes.VIRESCENT));
    Block CHAMOMILE_STARLETT = registerWithType("chamomile_starlett", new ChamomileCropBlock(QuiltBlockSettings.copyOf(CHAMOMILE), ChamomileTypes.STARLETT));
    Block CHAMOMILE_DYEWORKS = registerWithType("chamomile_dyeworks", new ChamomileCropBlock(QuiltBlockSettings.copyOf(CHAMOMILE), ChamomileTypes.DYEWORKS));

    Block CONEFLOWER = registerWithType("coneflower", new ConeflowerCropBlock(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP)));
    Block CONEFLOWER_DANCING_LADIES = registerWithType("coneflower_dancing_ladies", new ConeflowerCropBlock(QuiltBlockSettings.copyOf(CONEFLOWER), ConeflowerTypes.DANCING_LADIES));
    Block CONEFLOWER_VIOLET = registerWithType("coneflower_violet", new ConeflowerCropBlock(QuiltBlockSettings.copyOf(CONEFLOWER), ConeflowerTypes.VIOLET));
    Block CONEFLOWER_QUEENS_DESIRE = registerWithType("coneflower_queens_desire", new ConeflowerCropBlock(QuiltBlockSettings.copyOf(CONEFLOWER), ConeflowerTypes.QUEENS_DESIRE));
    Block CONEFLOWER_ROSE_DRESS = registerWithType("coneflower_rose_dress", new ConeflowerCropBlock(QuiltBlockSettings.copyOf(CONEFLOWER), ConeflowerTypes.ROSE_DRESS));
    Block CONEFLOWER_SUITOR = registerWithType("coneflower_suitor", new ConeflowerCropBlock(QuiltBlockSettings.copyOf(CONEFLOWER), ConeflowerTypes.SUITOR));
    Block CONEFLOWER_NETHER = registerWithType("coneflower_nether", new ConeflowerCropBlock(QuiltBlockSettings.copyOf(CONEFLOWER), ConeflowerTypes.NETHER));
    Block CONEFLOWER_LADYS_WISH = registerWithType("coneflower_ladys_wish", new ConeflowerCropBlock(QuiltBlockSettings.copyOf(CONEFLOWER), ConeflowerTypes.LADYS_WISH));
    Block CONEFLOWER_SUNGLOW = registerWithType("coneflower_sunglow", new ConeflowerCropBlock(QuiltBlockSettings.copyOf(CONEFLOWER), ConeflowerTypes.SUNGLOW));
    Block CONEFLOWER_FLAME = registerWithType("coneflower_flame", new ConeflowerCropBlock(QuiltBlockSettings.copyOf(CONEFLOWER), ConeflowerTypes.FLAME));
    Block CONEFLOWER_GILDED = registerWithType("coneflower_gilded", new ConeflowerCropBlock(QuiltBlockSettings.copyOf(CONEFLOWER), ConeflowerTypes.GILDED));
    Block CONEFLOWER_MORNING_MIST = registerWithType("coneflower_morning_mist", new ConeflowerCropBlock(QuiltBlockSettings.copyOf(CONEFLOWER), ConeflowerTypes.MORNING_MIST));
    Block CONEFLOWER_FLEECE = registerWithType("coneflower_fleece", new ConeflowerCropBlock(QuiltBlockSettings.copyOf(CONEFLOWER), ConeflowerTypes.FLEECE));
    Block CONEFLOWER_COMPANY = registerWithType("coneflower_company", new ConeflowerCropBlock(QuiltBlockSettings.copyOf(CONEFLOWER), ConeflowerTypes.COMPANY));
    Block CONEFLOWER_MASQUERADE = registerWithType("coneflower_masquerade", new ConeflowerCropBlock(QuiltBlockSettings.copyOf(CONEFLOWER), ConeflowerTypes.MASQUERADE));
    Block CONEFLOWER_PARTY_BLEND = registerWithType("coneflower_party_blend", new ConeflowerCropBlock(QuiltBlockSettings.copyOf(CONEFLOWER), ConeflowerTypes.PARTY_BLEND));

    Block FOXGLOVE = registerWithType("foxglove", new FoxgloveCropBlock(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP)));
    Block FOXGLOVE_SMALT = registerWithType("foxglove_smalt", new FoxgloveCropBlock(QuiltBlockSettings.copyOf(FOXGLOVE), FoxgloveTypes.SMALT));
    Block FOXGLOVE_TRANQUIL_EVENING = registerWithType("foxglove_tranquil_evening", new FoxgloveCropBlock(QuiltBlockSettings.copyOf(FOXGLOVE), FoxgloveTypes.TRANQUIL_EVENING));
    Block FOXGLOVE_PURPUREA = registerWithType("foxglove_purpurea", new FoxgloveCropBlock(QuiltBlockSettings.copyOf(FOXGLOVE), FoxgloveTypes.PURPUREA));
    Block FOXGLOVE_LOVELY_MORNING = registerWithType("foxglove_lovely_morning", new FoxgloveCropBlock(QuiltBlockSettings.copyOf(FOXGLOVE), FoxgloveTypes.LOVELY_MORNING));
    Block FOXGLOVE_IANTHINE = registerWithType("foxglove_ianthine", new FoxgloveCropBlock(QuiltBlockSettings.copyOf(FOXGLOVE), FoxgloveTypes.IANTHINE));
    Block FOXGLOVE_QUEENS_HAT = registerWithType("foxglove_queens_hat", new FoxgloveCropBlock(QuiltBlockSettings.copyOf(FOXGLOVE), FoxgloveTypes.QUEENS_HAT));
    Block FOXGLOVE_BLUSH = registerWithType("foxglove_blush", new FoxgloveCropBlock(QuiltBlockSettings.copyOf(FOXGLOVE), FoxgloveTypes.BLUSH));
    Block FOXGLOVE_ROYAL_BLANKET = registerWithType("foxglove_royal_blanket", new FoxgloveCropBlock(QuiltBlockSettings.copyOf(FOXGLOVE), FoxgloveTypes.ROYAL_BLANKET));
    Block FOXGLOVE_LOVE = registerWithType("foxglove_love", new FoxgloveCropBlock(QuiltBlockSettings.copyOf(FOXGLOVE), FoxgloveTypes.LOVE));
    Block FOXGLOVE_BABYS_DRESS = registerWithType("foxglove_babys_dress", new FoxgloveCropBlock(QuiltBlockSettings.copyOf(FOXGLOVE), FoxgloveTypes.BABYS_DRESS));
    Block FOXGLOVE_STROLL = registerWithType("foxglove_stroll", new FoxgloveCropBlock(QuiltBlockSettings.copyOf(FOXGLOVE), FoxgloveTypes.STROLL));
    Block FOXGLOVE_MAIDENS = registerWithType("foxglove_maidens", new FoxgloveCropBlock(QuiltBlockSettings.copyOf(FOXGLOVE), FoxgloveTypes.MAIDENS));
    Block FOXGLOVE_MORNING_FIELD = registerWithType("foxglove_morning_field", new FoxgloveCropBlock(QuiltBlockSettings.copyOf(FOXGLOVE), FoxgloveTypes.MORNING_FIELD));
    Block FOXGLOVE_SIGHE_GOWN = registerWithType("foxglove_sighe_gown", new FoxgloveCropBlock(QuiltBlockSettings.copyOf(FOXGLOVE), FoxgloveTypes.SIGHE_GOWN));
    Block FOXGLOVE_CALAMINE = registerWithType("foxglove_calamine", new FoxgloveCropBlock(QuiltBlockSettings.copyOf(FOXGLOVE), FoxgloveTypes.CALAMINE));
    Block FOXGLOVE_NETHERINE = registerWithType("foxglove_netherine", new FoxgloveCropBlock(QuiltBlockSettings.copyOf(FOXGLOVE), FoxgloveTypes.NETHERINE));
    Block FOXGLOVE_SUNGLOW = registerWithType("foxglove_sunglow", new FoxgloveCropBlock(QuiltBlockSettings.copyOf(FOXGLOVE), FoxgloveTypes.SUNGLOW));
    Block FOXGLOVE_SANDSTONE_TEMPLE = registerWithType("foxglove_sandstone_temple", new FoxgloveCropBlock(QuiltBlockSettings.copyOf(FOXGLOVE), FoxgloveTypes.SANDSTONE_TEMPLE));
    Block FOXGLOVE_FIERY_FIELD = registerWithType("foxglove_fiery_field", new FoxgloveCropBlock(QuiltBlockSettings.copyOf(FOXGLOVE), FoxgloveTypes.FIERY_FIELD));
    Block FOXGLOVE_PASSION = registerWithType("foxglove_passion", new FoxgloveCropBlock(QuiltBlockSettings.copyOf(FOXGLOVE), FoxgloveTypes.PASSION));
    Block FOXGLOVE_BASTARD_AMBER = registerWithType("foxglove_bastard_amber", new FoxgloveCropBlock(QuiltBlockSettings.copyOf(FOXGLOVE), FoxgloveTypes.BASTARD_AMBER));
    Block FOXGLOVE_SUNDROP = registerWithType("foxglove_sundrop", new FoxgloveCropBlock(QuiltBlockSettings.copyOf(FOXGLOVE), FoxgloveTypes.SUNDROP));
    Block FOXGLOVE_AURULENT = registerWithType("foxglove_aurulent", new FoxgloveCropBlock(QuiltBlockSettings.copyOf(FOXGLOVE), FoxgloveTypes.AURULENT));
    Block FOXGLOVE_IVORY = registerWithType("foxglove_ivory", new FoxgloveCropBlock(QuiltBlockSettings.copyOf(FOXGLOVE), FoxgloveTypes.IVORY));
    Block FOXGLOVE_NIVEOUS = registerWithType("foxglove_niveous", new FoxgloveCropBlock(QuiltBlockSettings.copyOf(FOXGLOVE), FoxgloveTypes.NIVEOUS));
    Block FOXGLOVE_COWS_CREAM = registerWithType("foxglove_cows_drwam", new FoxgloveCropBlock(QuiltBlockSettings.copyOf(FOXGLOVE), FoxgloveTypes.COWS_CREAM));
    Block FOXGLOVE_SIDHE_MIST = registerWithType("foxglove_sidhe_mist", new FoxgloveCropBlock(QuiltBlockSettings.copyOf(FOXGLOVE), FoxgloveTypes.SIDHE_MIST));
    Block FOXGLOVE_PURITY = registerWithType("foxglove_purify", new FoxgloveCropBlock(QuiltBlockSettings.copyOf(FOXGLOVE), FoxgloveTypes.PURITY));

    Block HELLEBORE = registerWithType("hellebore", new HelleboreCropBlock(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP)));
    Block HELLEBORE_MORNING_TEA = registerWithType("hellebore_morning_tea", new HelleboreCropBlock(QuiltBlockSettings.copyOf(HELLEBORE), HelleboreTypes.MORNING_TEA));
    Block HELLEBORE_MORNING_CASANOVA = registerWithType("hellebore_casanova", new HelleboreCropBlock(QuiltBlockSettings.copyOf(HELLEBORE), HelleboreTypes.CASANOVA));
    Block HELLEBORE_MORNING_BLUSHING = registerWithType("hellebore_blushing", new HelleboreCropBlock(QuiltBlockSettings.copyOf(HELLEBORE), HelleboreTypes.BLUSHING));
    Block HELLEBORE_MORNING_CELADON = registerWithType("hellebore_celadon", new HelleboreCropBlock(QuiltBlockSettings.copyOf(HELLEBORE), HelleboreTypes.CELADON));
    Block HELLEBORE_MORNING_FURY = registerWithType("hellebore_fury", new HelleboreCropBlock(QuiltBlockSettings.copyOf(HELLEBORE), HelleboreTypes.FURY));
    Block HELLEBORE_MORNING_ANGEL = registerWithType("hellebore_angel", new HelleboreCropBlock(QuiltBlockSettings.copyOf(HELLEBORE), HelleboreTypes.ANGEL));
    Block HELLEBORE_MORNING_TWILIGHT = registerWithType("hellebore_twilight", new HelleboreCropBlock(QuiltBlockSettings.copyOf(HELLEBORE), HelleboreTypes.TWILIGHT));
    Block HELLEBORE_MORNING_GRIMM = registerWithType("hellebore_grimm", new HelleboreCropBlock(QuiltBlockSettings.copyOf(HELLEBORE), HelleboreTypes.GRIMM));
    Block HELLEBORE_MORNING_NOCTURNE = registerWithType("hellebore_nocturne", new HelleboreCropBlock(QuiltBlockSettings.copyOf(HELLEBORE), HelleboreTypes.NOCTURNE));

    Block IRIS = registerWithType("iris", new IrisCropBlock(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP)));
    Block IRIS_OCEAN = registerWithType("iris_ocean", new IrisCropBlock(QuiltBlockSettings.copyOf(IRIS), IrisTypes.OCEAN));
    Block IRIS_DEEP_SEA = registerWithType("iris_deep_sea", new IrisCropBlock(QuiltBlockSettings.copyOf(IRIS), IrisTypes.DEEP_SEA));
    Block IRIS_BLEEDING_HEART = registerWithType("iris_bleeding_heart", new IrisCropBlock(QuiltBlockSettings.copyOf(IRIS), IrisTypes.BLEEDING_HEART));

    Block SANGUINARY = registerWithType("sanguinary", new SanguinaryCropBlock(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP)));
    Block SANGUINARY_MEADOW = registerWithType("sanguinary_meadow", new SanguinaryCropBlock(QuiltBlockSettings.copyOf(SANGUINARY), SanguinaryTypes.MEADOW));
    Block SANGUINARY_BLUSHING = registerWithType("sanguinary_blushing", new SanguinaryCropBlock(QuiltBlockSettings.copyOf(SANGUINARY), SanguinaryTypes.BLUSHING));
    Block SANGUINARY_SUNSET = registerWithType("sanguinary_sunset", new SanguinaryCropBlock(QuiltBlockSettings.copyOf(SANGUINARY), SanguinaryTypes.SUNSET));
    Block SANGUINARY_MADDER = registerWithType("sanguinary_madder", new SanguinaryCropBlock(QuiltBlockSettings.copyOf(SANGUINARY), SanguinaryTypes.MADDER));
    Block SANGUINARY_AUREOLIN = registerWithType("sanguinary_aureolin", new SanguinaryCropBlock(QuiltBlockSettings.copyOf(SANGUINARY), SanguinaryTypes.AUREOLIN));


    Block MINT = register("mint", new MintCropBlock(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP)),false);
    Block WORMWOOD = register("wormwood", new WormwoodCropBlock(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP)), false);

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

    static QuiltBlockSettings leavesSettings() {
        return QuiltBlockSettings.of(Material.LEAVES)
                .strength(0.2F)
                .ticksRandomly()
                .sounds(BlockSoundGroup.GRASS)
                .nonOpaque()
                .allowsSpawning((state, world, pos, type) -> type == EntityType.OCELOT || type == EntityType.PARROT)
                .suffocates((state, world, pos) -> false)
                .blockVision((state, world, pos) -> false);
    }

    static Block registerBarrel(String path) {
        return register(path, Material.WOOD, settings -> new BrewingBarrelBlock(settings.nonOpaque().strength(2.5F)));
    }

    static Block registerPottedSapling(String path) {
        final Block pottedSapling = new FlowerPotBlock(BLACKTHORN_SAPLING, QuiltBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
        BLOCKS.add(new ObjectDefinition<>(WitchesKitchen.id(path), pottedSapling));
        return pottedSapling;
    }

    static Block registerSapling(String path, ConfiguredFeature<TreeFeatureConfig, ?> feature) {
        final Block sapling = new WKSaplingBlock(new WKSaplingGenerator(() -> Holder.createDirect(feature)),
                QuiltBlockSettings.of(Material.PLANT)
                        .noCollision()
                        .ticksRandomly()
                        .breakInstantly()
                        .sounds(BlockSoundGroup.GRASS));
        return register(path, sapling, true);
    }

    static Block registerLeaf(String path) {
        final LeavesBlock leaf = new LeavesBlock(leavesSettings());
        return register(path, leaf, true);
    }

    static Block registerSlab(String path) {
        return register(path, Material.WOOD, SlabBlock::new);
    }

    static Block registerWood(String path, MapColor color) {
        final PillarBlock wood = new PillarBlock(QuiltBlockSettings.of(Material.WOOD, color).strength(2.0f).sounds(BlockSoundGroup.WOOD));
        return register(path, wood, true);
    }

    static AbstractBlock.Settings logSettings(MapColor topMapColor, MapColor sideMapColor) {
        return QuiltBlockSettings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor).strength(2.0f).sounds(BlockSoundGroup.WOOD).ticksRandomly();
    }

    static PillarBlock registerLog(String path, MapColor topMapColor, MapColor sideMapColor) {
        final PillarBlock log = new PillarBlock(logSettings(topMapColor, sideMapColor));
        return register(path, log, true);
    }

    static Block registerWoodenStair(String path, Block block) {
        return register(path, Material.WOOD, settings -> new StairsBlock(block.getDefaultState(), settings));
    }

    static Block registerGingerBread(String path) {
        return register(path, Material.CAKE, Block::new, BlockItem::new, WitchesKitchen.GINGER_TAB);
    }

    static Block register(String path, Material material) {
        return register(path, material, Block::new, BlockItem::new, WitchesKitchen.GENERAL_TAB);
    }

    static <T extends Block> T registerGingerTab(String path, Material material, Function<QuiltBlockSettings, T> blockFactory) {
        return register(path, material, blockFactory, BlockItem::new, WitchesKitchen.GINGER_TAB);
    }

    static <T extends Block> T register(String path, Material material, Function<QuiltBlockSettings, T> blockFactory) {
        return register(path, material, blockFactory, BlockItem::new, WitchesKitchen.GENERAL_TAB);
    }

    static <T extends Block> T registerWithType(String name, T block) {
        TYPE_BLOCKS.put(name, block);
        final Identifier id = WitchesKitchen.id(name);
        BLOCKS.add(new ObjectDefinition<>(id, block));
        return block;
    }

    static <T extends Block> T register(String path, T block, boolean createItem) {
        final Identifier id = WitchesKitchen.id(path);
        if (block == null) {
            throw new IllegalArgumentException("Block with id " + path + " returned null");
        }
        BLOCKS.add(new ObjectDefinition<>(id, block));
        if(createItem){
            ITEMS.add(new ObjectDefinition<>(id, new BlockItem(block, new Item.Settings().group(WitchesKitchen.GENERAL_TAB))));
        }
        return block;
    }

    static <T extends Block, E extends Item> T register(String path, Material material, Function<QuiltBlockSettings, T> blockFactory, BiFunction<T, QuiltItemSettings, E> itemFactory, ItemGroup tab) {
        final Identifier id = WitchesKitchen.id(path);
        final T block = blockFactory.apply(QuiltBlockSettings.of(material));
        BLOCKS.add(new ObjectDefinition<>(id, block));
        if (itemFactory != null) {
            final E item = itemFactory.apply(block, new QuiltItemSettings().group(tab));
            if (item == null) {
                throw new IllegalArgumentException("BlockItem factory for " + id + " returned null");
            }
            ITEMS.add(new ObjectDefinition<>(id, item));
        }
        return block;
    }

    static void init() {
        BLOCKS.forEach(entry -> Registry.register(Registry.BLOCK, entry.id(), entry.object()));
        ITEMS.forEach(entry -> Registry.register(Registry.ITEM, entry.id(), entry.object()));
        CompostingChanceRegistry validBlockCompost = CompostingChanceRegistry.INSTANCE;
        validBlockCompost.add(BLACKTHORN_LEAVES, 0.3f);
        validBlockCompost.add(ELDER_LEAVES, 0.3f);
        validBlockCompost.add(HAWTHORN_LEAVES, 0.3f);
        validBlockCompost.add(JUNIPER_LEAVES, 0.3f);
        validBlockCompost.add(ROWAN_LEAVES, 0.3f);
        validBlockCompost.add(SUMAC_LEAVES, 0.3f);
        validBlockCompost.add(ELDER_LEAVES_COLORED, 0.3f);
        validBlockCompost.add(HAWTHORN_LEAVES_COLORED, 0.3f);
        validBlockCompost.add(BLACKTHORN_SAPLING, 0.3f);
        validBlockCompost.add(ELDER_SAPLING, 0.3f);
        validBlockCompost.add(HAWTHORN_SAPLING, 0.3f);
        validBlockCompost.add(JUNIPER_SAPLING, 0.3f);
        validBlockCompost.add(ROWAN_SAPLING, 0.3f);
        validBlockCompost.add(SUMAC_SAPLING, 0.3f);
    }
}
