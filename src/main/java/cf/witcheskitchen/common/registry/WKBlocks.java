package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.api.registry.ObjectDefinition;
import cf.witcheskitchen.common.block.*;
import cf.witcheskitchen.common.block.sapling.WKSaplingBlock;
import cf.witcheskitchen.common.crop.AmaranthCropBlock;
import cf.witcheskitchen.common.crop.BelladonnaCropBlock;
import cf.witcheskitchen.common.crop.MintCropBlock;
import cf.witcheskitchen.common.crop.WormwoodCropBlock;
import cf.witcheskitchen.common.generator.WKSaplingGenerator;
import cf.witcheskitchen.common.variants.AmaranthTypes;
import cf.witcheskitchen.common.variants.BelladonnaTypes;
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

import static cf.witcheskitchen.common.registry.WKCreativeTabs.GENERAL_TAB;

public class WKBlocks {

    public static final ArrayList<Block> LEAF_BLOCKS = new ArrayList<>();
    private static final List<ObjectDefinition<Block>> BLOCKS = new ArrayList<>();
    private static final List<ObjectDefinition<Item>> ITEMS = new ArrayList<>();
    private static final Map<String, Block> TYPE_BLOCKS = new LinkedHashMap<>();


    public static final Block SALT_BLOCK = register("salt", Material.DECORATION, settings -> new SaltBlock(settings.noCollision().breakInstantly()));

    public static final Block RAW_GINGERBREAD_BLOCK = registerGingerBread("raw_gingerbread_block");
    public static final Block RAW_CHISELED_GINGERBREAD_BLOCK = registerGingerBread("raw_chiseled_gingerbread_block");
    public static final Block GINGERBREAD_BEVELED_BLOCK = registerGingerBread("gingerbread_beveled_block");
    public static final Block GINGERBREAD_BLOCK = registerGingerBread("gingerbread_block");
    public static final Block RAW_GINGERBREAD_BLOCK_STAIRS = register("raw_gingerbread_block_stairs", Material.CAKE, settings -> new WKStairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    public static final Block RAW_CHISELED_GINGERBREAD_BLOCK_STAIRS = register("raw_chiseled_gingerbread_block_stairs", Material.CAKE, settings -> new WKStairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    public static final Block GINGERBREAD_BEVELED_BLOCK_STAIRS = register("gingerbread_beveled_block_stairs", Material.CAKE, settings -> new WKStairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    public static final Block GINGERBREAD_BLOCK_STAIRS = register("gingerbread_block_stairs", Material.CAKE, settings -> new WKStairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    public static final Block RAW_GINGERBREAD_BEVELED_BLOCK_STAIRS = register("raw_gingerbread_beveled_block_stairs", Material.CAKE, settings -> new WKStairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    public static final Block FROSTED_GINGERBREAD_BLOCK_STAIRS = register("frosted_gingerbread_block_stairs", Material.CAKE, settings -> new WKStairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    public static final Block FROSTED_BEVELED_GINGERBREAD_BLOCK_STAIRS = register("frosted_beveled_gingerbread_block_stairs", Material.CAKE, settings -> new WKStairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    public static final Block CHISELED_GINGERBREAD_BLOCK_STAIRS = register("chiseled_gingerbread_block_stairs", Material.CAKE, settings -> new WKStairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    public static final Block RAW_GINGERBREAD_TILED_BLOCK_STAIRS = register("raw_gingerbread_tiled_block_stairs", Material.CAKE, settings -> new WKStairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    public static final Block GINGERBREAD_TILED_BLOCK_STAIRS = register("gingerbread_tiled_block_stairs", Material.CAKE, settings -> new WKStairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    public static final Block FROSTING_BLOCK_STAIRS = register("frosting_block_stairs", Material.CAKE, settings -> new WKStairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    public static final Block FROSTED_GINGERBREAD_TILED_BLOCK_STAIRS = register("frosted_gingerbread_tiled_block_stairs", Material.CAKE, settings -> new WKStairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    public static final Block FROSTED_BEVELED_GINGERBREAD_BLOCK_YELLOW_STAIRS = register("frosted_beveled_gingerbread_block_yellow_stairs", Material.CAKE, settings -> new WKStairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    public static final Block FROSTED_BEVELED_GINGERBREAD_BLOCK_RED_STAIRS = register("frosted_beveled_gingerbread_block_red_stairs", Material.CAKE, settings -> new WKStairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    public static final Block FROSTED_BEVELED_GINGERBREAD_BLOCK_PURPLE_STAIRS = register("frosted_beveled_gingerbread_block_purple_stairs", Material.CAKE, settings -> new WKStairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    public static final Block FROSTED_BEVELED_GINGERBREAD_BLOCK_GREEN_STAIRS = register("frosted_beveled_gingerbread_block_green_stairs", Material.CAKE, settings -> new WKStairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    public static final Block FROSTED_TILED_GINGERBREAD_BLOCK_YELLOW_STAIRS = register("frosted_tiled_gingerbread_block_yellow_stairs", Material.CAKE, settings -> new WKStairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    public static final Block FROSTED_TILED_GINGERBREAD_BLOCK_RED_STAIRS = register("frosted_tiled_gingerbread_block_red_stairs", Material.CAKE, settings -> new WKStairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    public static final Block FROSTED_TILED_GINGERBREAD_BLOCK_PURPLE_STAIRS = register("frosted_tiled_gingerbread_block_purple_stairs", Material.CAKE, settings -> new WKStairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    public static final Block FROSTED_TILED_GINGERBREAD_BLOCK_GREEN_STAIRS = register("frosted_tiled_gingerbread_block_green_stairs", Material.CAKE, settings -> new WKStairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    public static final Block FROSTED_TILED_GINGERBREAD_BLOCK_VARIANT_STAIRS = register("frosted_tiled_gingerbread_block_variant_stairs", Material.CAKE, settings -> new WKStairsBlock(GINGERBREAD_BLOCK.getDefaultState(), settings));
    public static final Block RAW_GINGERBREAD_BEVELED_BLOCK = registerGingerBread("raw_gingerbread_beveled_block");
    public static final Block FROSTED_GINGERBREAD_BLOCK = registerGingerBread("frosted_gingerbread_block");
    public static final Block FROSTED_BEVELED_GINGERBREAD_BLOCK = registerGingerBread("frosted_beveled_gingerbread_block");
    public static final Block CHISELED_GINGERBREAD_BLOCK = registerGingerBread("chiseled_gingerbread_block");
    public static final Block RAW_GINGERBREAD_TILED_BLOCK = registerGingerBread("raw_gingerbread_tiled_block");
    public static final Block GINGERBREAD_TILED_BLOCK = registerGingerBread("gingerbread_tiled_block");
    public static final Block FROSTING_BLOCK = registerGingerBread("frosting_block");
    public static final Block FROSTED_GINGERBREAD_TILED_BLOCK = registerGingerBread("frosted_gingerbread_tiled_block");
    public static final Block FROSTED_BEVELED_GINGERBREAD_BLOCK_YELLOW = registerGingerBread("frosted_beveled_gingerbread_block_yellow");
    public static final Block FROSTED_BEVELED_GINGERBREAD_BLOCK_RED = registerGingerBread("frosted_beveled_gingerbread_block_red");
    public static final Block FROSTED_BEVELED_GINGERBREAD_BLOCK_PURPLE = registerGingerBread("frosted_beveled_gingerbread_block_purple");
    public static final Block FROSTED_BEVELED_GINGERBREAD_BLOCK_GREEN = registerGingerBread("frosted_beveled_gingerbread_block_green");
    public static final Block FROSTED_TILED_GINGERBREAD_BLOCK_YELLOW = registerGingerBread("frosted_tiled_gingerbread_block_yellow");
    public static final Block FROSTED_TILED_GINGERBREAD_BLOCK_RED = registerGingerBread("frosted_tiled_gingerbread_block_red");
    public static final Block FROSTED_TILED_GINGERBREAD_BLOCK_PURPLE = registerGingerBread("frosted_tiled_gingerbread_block_purple");
    public static final Block FROSTED_TILED_GINGERBREAD_BLOCK_GREEN = registerGingerBread("frosted_tiled_gingerbread_block_green");
    public static final Block FROSTED_TILED_GINGERBREAD_BLOCK_VARIANT = registerGingerBread("frosted_tiled_gingerbread_block_variant");
    public static final Block RAW_GINGERBREAD_BLOCK_SLAB = registerGingerBread("raw_gingerbread_block_slab");
    public static final Block RAW_CHISELED_GINGERBREAD_BLOCK_SLAB = registerGingerBread("raw_chiseled_gingerbread_block_slab");
    public static final Block GINGERBREAD_BEVELED_BLOCK_SLAB = registerGingerBread("gingerbread_beveled_block_slab");
    public static final Block GINGERBREAD_BLOCK_SLAB = registerGingerBread("gingerbread_block_slab");
    public static final Block RAW_GINGERBREAD_BEVELED_BLOCK_SLAB = registerGingerBread("raw_gingerbread_beveled_block_slab");
    public static final Block FROSTED_GINGERBREAD_BLOCK_SLAB = registerGingerBread("frosted_gingerbread_block_slab");
    public static final Block FROSTED_BEVELED_GINGERBREAD_BLOCK_SLAB = registerGingerBread("frosted_beveled_gingerbread_block_slab");
    public static final Block CHISELED_GINGERBREAD_BLOCK_SLAB = registerGingerBread("chiseled_gingerbread_block_slab");
    public static final Block RAW_GINGERBREAD_TILED_BLOCK_SLAB = registerGingerBread("raw_gingerbread_tiled_block_slab");
    public static final Block GINGERBREAD_TILED_BLOCK_SLAB = registerGingerBread("gingerbread_tiled_block_slab");
    public static final Block FROSTING_BLOCK_SLAB = registerGingerBread("frosting_block_slab");
    public static final Block FROSTED_GINGERBREAD_TILED_BLOCK_SLAB = registerGingerBread("frosted_gingerbread_tiled_block_slab");
    public static final Block FROSTED_BEVELED_GINGERBREAD_BLOCK_YELLOW_SLAB = registerGingerBread("frosted_beveled_gingerbread_block_yellow_slab");
    public static final Block FROSTED_BEVELED_GINGERBREAD_BLOCK_RED_SLAB = registerGingerBread("frosted_beveled_gingerbread_block_red_slab");
    public static final Block FROSTED_BEVELED_GINGERBREAD_BLOCK_PURPLE_SLAB = registerGingerBread("frosted_beveled_gingerbread_block_purple_slab");
    public static final Block FROSTED_BEVELED_GINGERBREAD_BLOCK_GREEN_SLAB = registerGingerBread("frosted_beveled_gingerbread_block_green_slab");
    public static final Block FROSTED_TILED_GINGERBREAD_BLOCK_YELLOW_SLAB = registerGingerBread("frosted_tiled_gingerbread_block_yellow_slab");
    public static final Block FROSTED_TILED_GINGERBREAD_BLOCK_RED_SLAB = registerGingerBread("frosted_tiled_gingerbread_block_red_slab");
    public static final Block FROSTED_TILED_GINGERBREAD_BLOCK_PURPLE_SLAB = registerGingerBread("frosted_tiled_gingerbread_block_purple_slab");
    public static final Block FROSTED_TILED_GINGERBREAD_BLOCK_GREEN_SLAB = registerGingerBread("frosted_tiled_gingerbread_block_green_slab");
    public static final Block FROSTED_TILED_GINGERBREAD_BLOCK_VARIANT_SLAB = registerGingerBread("frosted_tiled_gingerbread_block_variant_slab");

    public static final Block ELDER_PLANKS = register("elder_planks", Material.WOOD);
    public static final Block ELDER_STAIRS = registerWoodenStair("elder_stairs", ELDER_PLANKS);
    public static final Block SUMAC_PLANKS = register("sumac_planks", Material.WOOD);
    public static final Block SUMAC_STAIRS = registerWoodenStair("sumac_stairs", SUMAC_PLANKS);
    public static final Block HAWTHORN_PLANKS = register("hawthorn_planks", Material.WOOD);
    public static final Block HAWTHORN_STAIRS = registerWoodenStair("hawthorn_stairs", HAWTHORN_PLANKS);
    public static final Block BLACKTHORN_PLANKS = register("blackthorn_planks", Material.WOOD);
    public static final Block BLACKTHORN_STAIRS = registerWoodenStair("blackthorn_stairs", BLACKTHORN_PLANKS);
    public static final Block JUNIPER_PLANKS = register("juniper_planks", Material.WOOD);
    public static final Block JUNIPER_STAIRS = registerWoodenStair("juniper_stairs", JUNIPER_PLANKS);
    public static final Block ROWAN_PLANKS = register("rowan_planks", Material.WOOD);
    public static final Block ROWAN_STAIRS = registerWoodenStair("rowan_stairs", ROWAN_PLANKS);
    public static final Block ELDER_LOG = registerLog("elder_log", MapColor.PALE_YELLOW, MapColor.OAK_TAN);
    public static final Block SUMAC_LOG = registerLog("sumac_log", MapColor.DEEPSLATE_GRAY, MapColor.DARK_DULL_PINK);
    public static final Block HAWTHORN_LOG = registerLog("hawthorn_log", MapColor.PALE_YELLOW, MapColor.DIRT_BROWN);
    public static final Block BLACKTHORN_LOG = register("blackthorn_log", new BlackthornPillarBlock(logSettings(MapColor.TERRACOTTA_BROWN, MapColor.TERRACOTTA_BLACK)));
    public static final Block JUNIPER_LOG = registerLog("juniper_log", MapColor.DIRT_BROWN, MapColor.DEEPSLATE_GRAY);
    public static final Block ROWAN_LOG = registerLog("rowan_log", MapColor.TERRACOTTA_BLACK, MapColor.BROWN);
    public static final Block STRIPPED_BLACKTHORN_LOG = registerLog("stripped_blackthorn_log", MapColor.TERRACOTTA_BROWN, MapColor.TERRACOTTA_BLACK);
    public static final Block STRIPPED_ELDER_LOG = registerLog("stripped_elder_log", MapColor.PALE_YELLOW, MapColor.OAK_TAN);
    public static final Block STRIPPED_HAWTHORN_LOG = registerLog("stripped_hawthorn_log", MapColor.PALE_YELLOW, MapColor.DIRT_BROWN);
    public static final Block STRIPPED_JUNIPER_LOG = registerLog("stripped_juniper_log", MapColor.DIRT_BROWN, MapColor.DEEPSLATE_GRAY);
    public static final Block STRIPPED_ROWAN_LOG = registerLog("stripped_rowan_log", MapColor.TERRACOTTA_BLACK, MapColor.BROWN);
    public static final Block STRIPPED_SUMAC_LOG = registerLog("stripped_sumac_log", MapColor.DEEPSLATE_GRAY, MapColor.DARK_DULL_PINK);
    public static final Block BLACKTHORN_WOOD = registerWood("blackthorn_wood", MapColor.TERRACOTTA_BLACK);
    public static final Block STRIPPED_BLACKTHORN_WOOD = registerWood("stripped_blackthorn_wood", MapColor.TERRACOTTA_BLACK);
    public static final Block ELDER_WOOD = registerWood("elder_wood", MapColor.OAK_TAN);
    public static final Block STRIPPED_ELDER_WOOD = registerWood("stripped_elder_wood", MapColor.OAK_TAN);
    public static final Block HAWTHORN_WOOD = registerWood("hawthorn_wood", MapColor.DIRT_BROWN);
    public static final Block STRIPPED_HAWTHORN_WOOD = registerWood("stripped_hawthorn_wood", MapColor.DIRT_BROWN);
    public static final Block JUNIPER_WOOD = registerWood("juniper_wood", MapColor.DEEPSLATE_GRAY);
    public static final Block STRIPPED_JUNIPER_WOOD = registerWood("stripped_juniper_wood", MapColor.DEEPSLATE_GRAY);
    public static final Block ROWAN_WOOD = registerWood("rowan_wood", MapColor.TERRACOTTA_BLACK);
    public static final Block STRIPPED_ROWAN_WOOD = registerWood("stripped_rowan_wood", MapColor.TERRACOTTA_BLACK);
    public static final Block SUMAC_WOOD = registerWood("sumac_wood", MapColor.OAK_TAN);
    public static final Block STRIPPED_SUMAC_WOOD = registerWood("stripped_sumac_wood", MapColor.OAK_TAN);
    public static final Block ELDER_SLAB = registerSlab("elder_slab");
    public static final Block SUMAC_SLAB = registerSlab("sumac_slab");
    public static final Block HAWTHORN_SLAB = registerSlab("hawthorn_slab");
    public static final Block BLACKTHORN_SLAB = registerSlab("blackthorn_slab");
    public static final Block JUNIPER_SLAB = registerSlab("juniper_slab");
    public static final Block ROWAN_SLAB = registerSlab("rowan_slab");
    public static final Block ELDER_LEAVES = registerLeaf("elder_leaves");
    public static final Block ELDER_LEAVES_COLORED = registerLeaf("elder_leaves_colored");
    public static final Block SUMAC_LEAVES = registerLeaf("sumac_leaves");
    public static final Block HAWTHORN_LEAVES = registerLeaf("hawthorn_leaves");
    public static final Block HAWTHORN_LEAVES_COLORED = registerLeaf("hawthorn_leaves_colored");
    public static final Block BLACKTHORN_LEAVES = register("blackthorn_leaves", new BlackthornLeavesBlock(leavesSettings()));
    public static final Block JUNIPER_LEAVES = registerLeaf("juniper_leaves");
    public static final Block ROWAN_LEAVES = registerLeaf("rowan_leaves");
    //Tile Entities
    public static final Block TEAPOT = register("teapot", new TeapotBlock(QuiltBlockSettings.copyOf(Blocks.AMETHYST_BLOCK)));
    public static final Block IRON_WITCHES_OVEN = register("iron_witches_oven", new WitchesOvenBlock(AbstractBlock.Settings.of(Material.METAL).strength(4.0F, 5.0F).requiresTool().nonOpaque().luminance(state -> state.get(WitchesOvenBlock.LIT) ? 13 : 0)));
    public static final Block COPPER_WITCHES_OVEN = register("copper_witches_oven", new CopperWitchesOvenBlock(QuiltBlockSettings.copyOf(Blocks.COPPER_BLOCK).luminance(state -> state.get(WitchesOvenBlock.LIT) ? 13 : 0), Oxidizable.OxidizationLevel.UNAFFECTED));
    public static final Block WAXED_COPPER_WITCHES_OVEN = register("waxed_copper_witches_oven", new WitchesOvenBlock(QuiltBlockSettings.copy(COPPER_WITCHES_OVEN)));
    public static final Block EXPOSED_COPPER_WITCHES_OVEN = register("exposed_copper_witches_oven", new CopperWitchesOvenBlock(QuiltBlockSettings.copy(Blocks.EXPOSED_COPPER).luminance(state -> state.get(WitchesOvenBlock.LIT) ? 13 : 0), Oxidizable.OxidizationLevel.EXPOSED));
    public static final Block WAXED_EXPOSED_COPPER_WITCHES_OVEN = register("waxed_exposed_copper_witches_oven", new WitchesOvenBlock(QuiltBlockSettings.copy(EXPOSED_COPPER_WITCHES_OVEN)));
    public static final Block WEATHERED_COPPER_WITCHES_OVEN = register("weathered_copper_witches_oven", new CopperWitchesOvenBlock(QuiltBlockSettings.copy(Blocks.WEATHERED_COPPER).luminance(state -> state.get(WitchesOvenBlock.LIT) ? 13 : 0), Oxidizable.OxidizationLevel.WEATHERED));
    public static final Block WAXED_WEATHERED_COPPER_WITCHES_OVEN = register("waxed_weathered_copper_witches_oven", new WitchesOvenBlock(QuiltBlockSettings.copy(WEATHERED_COPPER_WITCHES_OVEN)));
    public static final Block OXIDIZED_COPPER_WITCHES_OVEN = register("oxidized_copper_witches_oven", new CopperWitchesOvenBlock(QuiltBlockSettings.copy(Blocks.OXIDIZED_COPPER).luminance(state -> state.get(WitchesOvenBlock.LIT) ? 13 : 0), Oxidizable.OxidizationLevel.OXIDIZED));
    public static final Block WAXED_OXIDIZED_COPPER_WITCHES_OVEN = register("waxed_oxidized_copper_witches_oven", new WitchesOvenBlock(QuiltBlockSettings.copy(OXIDIZED_COPPER_WITCHES_OVEN)));

    public static final Block IRON_WITCHES_CAULDRON = register("iron_witches_cauldron", new WitchesCauldronBlock(QuiltBlockSettings.copy(Blocks.CAULDRON).luminance(state -> state.get(WitchesCauldronBlock.LIT) ? 13 : 0)));
    public static final Block BLACKTHORN_SAPLING = registerSapling("blackthorn_sapling", WKConfiguredFeatures.BLACKTHORN_TREE);
    public static final Block POTTED_BLACKTHORN_SAPLING = registerPottedSapling("potted_blackthorn_sapling");
    public static final Block POTTED_ELDER_SAPLING = registerPottedSapling("potted_elder_sapling");
    public static final Block POTTED_HAWTHORN_SAPLING = registerPottedSapling("potted_hawthorn_sapling");
    public static final Block POTTED_JUNIPER_SAPLING = registerPottedSapling("potted_juniper_sapling");
    public static final Block POTTED_ROWAN_SAPLING = registerPottedSapling("potted_rowan_sapling");
    public static final Block POTTED_SUMAC_SAPLING = registerPottedSapling("potted_sumac_sapling");
    public static final Block ELDER_SAPLING = registerSapling("elder_sapling", WKConfiguredFeatures.ELDER_TREE);
    public static final Block HAWTHORN_SAPLING = registerSapling("hawthorn_sapling", WKConfiguredFeatures.HAWTHORN_TREE);
    public static final Block JUNIPER_SAPLING = registerSapling("juniper_sapling", WKConfiguredFeatures.JUNIPER_TREE);
    public static final Block ROWAN_SAPLING = registerSapling("rowan_sapling", WKConfiguredFeatures.ROWAN_TREE);
    public static final Block SUMAC_SAPLING = registerSapling("sumac_sapling", WKConfiguredFeatures.SUMAC_TREE);
    public static final Block OAK_BREWING_BARREL = registerBarrel("oak_brewing_barrel");
    public static final Block SPRUCE_BREWING_BARREL = registerBarrel("spruce_brewing_barrel");
    public static final Block BIRCH_BREWING_BARREL = registerBarrel("birch_brewing_barrel");
    public static final Block JUNGLE_BREWING_BARREL = registerBarrel("jungle_brewing_barrel");
    public static final Block ACACIA_BREWING_BARREL = registerBarrel("acacia_brewing_barrel");
    public static final Block DARK_OAK_BREWING_BARREL = registerBarrel("dark_oak_brewing_barrel");
    public static final Block CRIMSON_BREWING_BARREL = registerBarrel("crimson_brewing_barrel");
    public static final Block WARPED_BREWING_BARREL = registerBarrel("warped_brewing_barrel");
    //Crops
    public static final Block AMARANTH = registerWithType("amaranth", new AmaranthCropBlock(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP)));
    public static final Block AMARANTH_SWEETBERRY = registerWithType("amaranth_sweetberry", new AmaranthCropBlock(QuiltBlockSettings.copyOf(AMARANTH), AmaranthTypes.SWEETBERRY));
    public static final Block AMARANTH_TORCH = registerWithType("amaranth_torch", new AmaranthCropBlock(QuiltBlockSettings.copyOf(AMARANTH), AmaranthTypes.TORCH));
    public static final Block AMARANTH_SUNDEW = registerWithType("amaranth_sundew", new AmaranthCropBlock(QuiltBlockSettings.copyOf(AMARANTH), AmaranthTypes.SUNDEW));
    public static final Block AMARANTH_CREEPER = registerWithType("amaranth_creeper", new AmaranthCropBlock(QuiltBlockSettings.copyOf(AMARANTH), AmaranthTypes.CREEPER));
    public static final Block AMARANTH_VIRIDIAN = registerWithType("amaranth_viridian", new AmaranthCropBlock(QuiltBlockSettings.copyOf(AMARANTH), AmaranthTypes.VIRIDIAN));
    public static final Block AMARANTH_GRISELIN = registerWithType("amaranth_griselin", new AmaranthCropBlock(QuiltBlockSettings.copyOf(AMARANTH), AmaranthTypes.GRISELIN));
    public static final Block AMARANTH_CERISE = registerWithType("amaranth_cerise", new AmaranthCropBlock(QuiltBlockSettings.copyOf(AMARANTH), AmaranthTypes.CERISE));
    public static final Block AMARANTH_DARK_PASSION = registerWithType("amaranth_dark_passion", new AmaranthCropBlock(QuiltBlockSettings.copyOf(AMARANTH), AmaranthTypes.DARK_PASSION));
    public static final Block AMARANTH_FIREBIRD = registerWithType("amaranth_firebird", new AmaranthCropBlock(QuiltBlockSettings.copyOf(AMARANTH), AmaranthTypes.FIREBIRD));

    public static final Block BELLADONNA = registerWithType("belladonna", new BelladonnaCropBlock(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP)));
    public static final Block BELLADONNA_GLOW = registerWithType("belladonna_glow", new BelladonnaCropBlock(QuiltBlockSettings.copyOf(BELLADONNA), BelladonnaTypes.GLOW));
    public static final Block BELLADONNA_NOCTURNAL = registerWithType("belladonna_nocturnal", new BelladonnaCropBlock(QuiltBlockSettings.copyOf(BELLADONNA), BelladonnaTypes.NOCTURNAL));



    public static final Block MINT = register("mint", new MintCropBlock(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP)));
    public static final Block WORMWOOD = register("wormwood", new WormwoodCropBlock(AbstractBlock.Settings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP)));

    /**
     * Returns an <a href="Collection.html#unmodview">read-only view</a> of the WitchesKitchen's Blocks
     */
    public static List<ObjectDefinition<Block>> getBlocks() {
        return Collections.unmodifiableList(BLOCKS);
    }

    /**
     * Returns an <a href="Collection.html#unmodview">read-only view</a> of the WitchesKitchen's BlockItems
     */
    public static List<ObjectDefinition<Item>> getItems() {
        return Collections.unmodifiableList(ITEMS);
    }

    public static Map<String, Block> getTypeBlocks() {
        return TYPE_BLOCKS;
    }

    private static QuiltBlockSettings leavesSettings() {
        return QuiltBlockSettings.of(Material.LEAVES)
                .strength(0.2F)
                .ticksRandomly()
                .sounds(BlockSoundGroup.GRASS)
                .nonOpaque()
                .allowsSpawning((state, world, pos, type) -> type == EntityType.OCELOT || type == EntityType.PARROT)
                .suffocates((state, world, pos) -> false)
                .blockVision((state, world, pos) -> false);
    }

    private static Block registerBarrel(String path) {
        return register(path, Material.WOOD, settings -> new BrewingBarrelBlock(settings.nonOpaque().strength(2.5F)));
    }

    private static Block registerPottedSapling(String path) {
        final Block pottedSapling = new FlowerPotBlock(BLACKTHORN_SAPLING, QuiltBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
        return register(path, pottedSapling);

    }

    private static Block registerSapling(String path, ConfiguredFeature<TreeFeatureConfig, ?> feature) {
        final Block sapling = new WKSaplingBlock(new WKSaplingGenerator(() -> Holder.createDirect(feature)),
                QuiltBlockSettings.of(Material.PLANT)
                        .noCollision()
                        .ticksRandomly()
                        .breakInstantly()
                        .sounds(BlockSoundGroup.GRASS));
        return register(path, sapling);
    }

    private static Block registerLeaf(String path) {
        final LeavesBlock leaf = new LeavesBlock(leavesSettings());
        return register(path, leaf);
    }

    private static Block registerSlab(String path) {
        return register(path, Material.WOOD, SlabBlock::new);
    }

    private static Block registerWood(String path, MapColor color) {
        final PillarBlock wood = new PillarBlock(QuiltBlockSettings.of(Material.WOOD, color).strength(2.0f).sounds(BlockSoundGroup.WOOD));
        return register(path, wood);
    }

    private static AbstractBlock.Settings logSettings(MapColor topMapColor, MapColor sideMapColor) {
        return QuiltBlockSettings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor).strength(2.0f).sounds(BlockSoundGroup.WOOD).ticksRandomly();
    }

    private static PillarBlock registerLog(String path, MapColor topMapColor, MapColor sideMapColor) {
        final PillarBlock log = new PillarBlock(logSettings(topMapColor, sideMapColor));
        return register(path, log);
    }

    private static Block registerWoodenStair(String path, Block block) {
        return register(path, Material.WOOD, settings -> new WKStairsBlock(block.getDefaultState(), settings));
    }

    private static Block registerGingerBread(String path) {

        return register(path, Material.CAKE, Block::new, BlockItem::new, GENERAL_TAB);
    }

    private static Block register(String path, Material material) {
        return register(path, material, Block::new, BlockItem::new, GENERAL_TAB);
    }


    private static <T extends Block> T register(String path, Material material, Function<QuiltBlockSettings, T> blockFactory) {
        return register(path, material, blockFactory, BlockItem::new, GENERAL_TAB);
    }

    private static <T extends Block, E extends Item> T register(String path, Material material, Function<QuiltBlockSettings, T> blockFactory, BiFunction<T, QuiltItemSettings, E> itemFactory) {
        return register(path, material, blockFactory, itemFactory, GENERAL_TAB);
    }

    private static <T extends Block> T registerWithType(String name, T block) {
        TYPE_BLOCKS.put(name, block);
        return register(name, block);
    }

    private static <T extends Block> T register(String path, T block) {
        final Identifier id = WitchesKitchen.id(path);
        if (block == null) {
            throw new IllegalArgumentException("Block with id " + path + " returned null");
        }
        BLOCKS.add(new ObjectDefinition<>(id, block));
        ITEMS.add(new ObjectDefinition<>(id, new BlockItem(block, new Item.Settings().group(GENERAL_TAB))));
        return block;
    }

    private static <T extends Block, E extends Item> T register(String path, Material material, Function<QuiltBlockSettings, T> blockFactory, BiFunction<T, QuiltItemSettings, E> itemFactory, ItemGroup tab) {
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

    public static void register() {
        for (ObjectDefinition<Block> entry : BLOCKS) {
            Registry.register(Registry.BLOCK, entry.id(), entry.object());
        }
        for (ObjectDefinition<Item> entry : ITEMS) {
            Registry.register(Registry.ITEM, entry.id(), entry.object());
        }
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
