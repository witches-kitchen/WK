package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.WKConfig;
import cf.witcheskitchen.WKIdentifier;
import cf.witcheskitchen.common.items.WKSeedItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.registry.Registry;

public class WKItems {

    // Flowers
    public static final Item BELLADONNA_BLOSSOM = new Item(new FabricItemSettings().group(WK.WK_SEED_GROUP));
    public static final Item AMARANTH_SPRIG = new Item(new FabricItemSettings().food(WKFoodComponents.AMARANTH_GRAIN).group(WK.WK_SEED_GROUP));
    public static final Item MINT_SPRIG = new Item(new FabricItemSettings().food(WKFoodComponents.MINT_LEAF).group(WK.WK_SEED_GROUP));
    public static final Item WORMWOOD_SPRIG = new Item(new FabricItemSettings().group(WK.WK_SEED_GROUP));
    public static final Item ROWAN_BERRIES = new Item(new FabricItemSettings().food(WKFoodComponents.BERRIES).group(WK.WK_SEED_GROUP));
    public static final Item SLOE_BERRIES = new Item(new FabricItemSettings().food(WKFoodComponents.BERRIES).group(WK.WK_SEED_GROUP));
    public static final Item JUNIPER_BERRIES = new Item(new FabricItemSettings().food(WKFoodComponents.BERRIES).group(WK.WK_SEED_GROUP));
    public static final Item BLACKBERRY = new Item(new FabricItemSettings().food(WKFoodComponents.BERRIES).group(WK.WK_SEED_GROUP));
    public static final Item HAWTHORN_BERRIES = new Item(new FabricItemSettings().food(WKFoodComponents.BERRIES).group(WK.WK_SEED_GROUP));
    public static final Item SUMAC_BERRIES = new Item(new FabricItemSettings().food(WKFoodComponents.BERRIES).group(WK.WK_SEED_GROUP));
    public static final Item BRIAR_HIPS = new Item(new FabricItemSettings().food(WKFoodComponents.BERRIES).group(WK.WK_SEED_GROUP));
    public static final Item ELDER_BLOSSOM = new Item(new FabricItemSettings().group(WK.WK_SEED_GROUP));
    public static final Item CONEFLOWER_BLOSSOM = new Item(new FabricItemSettings().group(WK.WK_SEED_GROUP));
    public static final Item SANGUINARY_BLOSSOM = new Item(new FabricItemSettings().group(WK.WK_SEED_GROUP));
    public static final Item SAINT_JOHNS_WORT_BLOSSOM = new Item(new FabricItemSettings().group(WK.WK_SEED_GROUP));
    public static final Item IRIS_BLOSSOM = new Item(new FabricItemSettings().group(WK.WK_SEED_GROUP));
    public static final Item CHAMOMILE_BLOSSOM = new Item(new FabricItemSettings().group(WK.WK_SEED_GROUP));
    public static final Item GINGER_RHIZOME = new Item(new FabricItemSettings().group(WK.WK_SEED_GROUP));
    public static final Item TEA_LEAF = new Item(new FabricItemSettings().food(WKFoodComponents.TEA_LEAF).group(WK.WK_SEED_GROUP));
    public static final Item HELLEBORE_BLOSSOM = new Item(new FabricItemSettings().group(WK.WK_SEED_GROUP));
    public static final Item FOXGLOVE_BLOSSOM = new Item(new FabricItemSettings().group(WK.WK_SEED_GROUP));

    public static final Item AMARANTH_SEEDS = new WKSeedItem(WKBlocks.AMARANTH, new FabricItemSettings().group(WK.WK_SEED_GROUP));
    public static final Item AMARANTH_SWEETBERRY_SEEDS = new WKSeedItem(WKBlocks.AMARANTH_SWEETBERRY, new FabricItemSettings().group(WK.WK_SEED_GROUP));
    public static final Item AMARANTH_TORCH_SEEDS = new WKSeedItem(WKBlocks.AMARANTH_TORCH, new FabricItemSettings().group(WK.WK_SEED_GROUP));
    public static final Item AMARANTH_SUNDEW_SEEDS = new WKSeedItem(WKBlocks.AMARANTH_SUNDEW, new FabricItemSettings().group(WK.WK_SEED_GROUP));
    public static final Item AMARANTH_CREEPER_SEEDS = new WKSeedItem(WKBlocks.AMARANTH_CREEPER, new FabricItemSettings().group(WK.WK_SEED_GROUP));
    public static final Item AMARANTH_VIRIDIAN_SEEDS = new WKSeedItem(WKBlocks.AMARANTH_VIRIDIAN, new FabricItemSettings().group(WK.WK_SEED_GROUP));
    public static final Item AMARANTH_GRISELIN_SEEDS = new WKSeedItem(WKBlocks.AMARANTH_GRISELIN, new FabricItemSettings().group(WK.WK_SEED_GROUP));
    public static final Item AMARANTH_CERISE_SEEDS = new WKSeedItem(WKBlocks.AMARANTH_CERISE, new FabricItemSettings().group(WK.WK_SEED_GROUP));
    public static final Item AMARANTH_DARK_PASSION_SEEDS = new WKSeedItem(WKBlocks.AMARANTH_DARK_PASSION, new FabricItemSettings().group(WK.WK_SEED_GROUP));
    public static final Item AMARANTH_FIREBIRD_SEEDS = new WKSeedItem(WKBlocks.AMARANTH_FIREBIRD, new FabricItemSettings().group(WK.WK_SEED_GROUP));
    public static final Item BELLADONNA_SEEDS = new WKSeedItem(WKBlocks.BELLADONNA, new FabricItemSettings().group(WK.WK_SEED_GROUP));
    public static final Item BELLADONNA_GLOW_SEEDS = new WKSeedItem(WKBlocks.BELLADONNA_GLOW, new FabricItemSettings().group(WK.WK_SEED_GROUP));
    public static final Item BELLADONNA_NOCTURNAL_SEEDS = new WKSeedItem(WKBlocks.BELLADONNA_NOCTURNAL, new FabricItemSettings().group(WK.WK_SEED_GROUP));
    public static Item ST_JOHNS_WORT_SEEDS = new Item(new FabricItemSettings().group(WK.WK_GROUP));
    public static Item BRIAR_SEEDS = new Item(new FabricItemSettings().group(WK.WK_GROUP));
    public static Item CAMELLIA_SEEDS = new Item(new FabricItemSettings().group(WK.WK_GROUP));
    public static Item CHAMOMILE_SEEDS = new Item(new FabricItemSettings().group(WK.WK_GROUP));
    public static Item CONEFLOWER_SEEDS = new Item(new FabricItemSettings().group(WK.WK_GROUP));
    public static Item FOXGLOVE_SEEDS = new Item(new FabricItemSettings().group(WK.WK_GROUP));
    public static Item HELLEBORE_SEEDS = new Item(new FabricItemSettings().group(WK.WK_GROUP));
    public static Item IRIS_SEEDS = new Item(new FabricItemSettings().group(WK.WK_GROUP));
    public static Item SANGUINARY_SEEDS = new Item(new FabricItemSettings().group(WK.WK_GROUP));
    public static Item WORMWOOD_SEEDS = new Item(new FabricItemSettings().group(WK.WK_GROUP));

    public static Item DOLLOP_OF_FROSTING = new Item(new FabricItemSettings().food(WKFoodComponents.FROSTING).group(WK.WK_GROUP));
    public static Item CHOCOLATE_RUM_BALLS = new Item(new FabricItemSettings().food(WKFoodComponents.RUM_BALLS).group(WK.WK_GROUP));
    public static Item SUPER_BOOZE = new Item(new FabricItemSettings().food(WKFoodComponents.SUPER_BOOZE).group(WK.WK_GROUP));
    //Todo: Convert to usage of hex triplets fully
    public static Item CU_SITH_SPAWN_EGG = new SpawnEggItem(WKEntities.CUSITH, 3421236, 3497531, new Item.Settings().group(WK.WK_GROUP));
    public static Item FERRET_SPAWN_EGG = new SpawnEggItem(WKEntities.FERRET, 9985082, 2631205, new Item.Settings().group(WK.WK_GROUP));
    public static Item CHURCH_GRIM_SPAWN_EGG = new SpawnEggItem(WKEntities.CHURCH_GRIM, 0xFFFAFA, 0x36454F, new Item.Settings().group(WK.WK_GROUP));

    public static void register() {
        registerItem("st_john_wort_seeds", ST_JOHNS_WORT_SEEDS);
        registerItem("briar_seeds", BRIAR_SEEDS);
        registerItem("camellia_seeds", CAMELLIA_SEEDS);
        registerItem("chamomile_seeds", CHAMOMILE_SEEDS);
        registerItem("coneflower_seeds", CONEFLOWER_SEEDS);
        registerItem("foxglove_seeds", FOXGLOVE_SEEDS);
        registerItem("hellebore_seeds", HELLEBORE_SEEDS);
        registerItem("iris_seeds", IRIS_SEEDS);
        registerItem("sanguinary_seeds", SANGUINARY_SEEDS);
        registerItem("amaranth_seeds", AMARANTH_SEEDS);
        registerItem("amaranth_sweetberry_seeds", AMARANTH_SWEETBERRY_SEEDS);
        registerItem("amaranth_torch_seeds", AMARANTH_TORCH_SEEDS);
        registerItem("amaranth_sundew_seeds", AMARANTH_SUNDEW_SEEDS);
        registerItem("amaranth_creeper_seeds", AMARANTH_CREEPER_SEEDS);
        registerItem("amaranth_viridian_seeds", AMARANTH_VIRIDIAN_SEEDS);
        registerItem("amaranth_griselin_seeds", AMARANTH_GRISELIN_SEEDS);
        registerItem("amaranth_cerise_seeds", AMARANTH_CERISE_SEEDS);
        registerItem("amaranth_dark_passion_seeds", AMARANTH_DARK_PASSION_SEEDS);
        registerItem("amaranth_firebird_seeds", AMARANTH_FIREBIRD_SEEDS);
        registerItem("belladonna_blossom", BELLADONNA_BLOSSOM);
        registerItem("amaranth_sprig", AMARANTH_SPRIG);
        registerItem("mint_sprig", MINT_SPRIG);
        registerItem("belladonna_seeds", BELLADONNA_SEEDS);
        registerItem("belladonna_glow_seeds", BELLADONNA_GLOW_SEEDS);
        registerItem("belladonna_nocturnal_seeds", BELLADONNA_NOCTURNAL_SEEDS);
        registerItem("dollop_of_frosting", DOLLOP_OF_FROSTING);
        registerItem("chocolate_rum_balls", CHOCOLATE_RUM_BALLS);
        registerItem("super_booze", SUPER_BOOZE);
        registerItem("cu_sith_spawn_egg", CU_SITH_SPAWN_EGG);
        registerItem("ferret_spawn_egg", FERRET_SPAWN_EGG);
        registerItem("church_grim_spawn_egg", CHURCH_GRIM_SPAWN_EGG);
        registerItem("wormwood_sprig", WORMWOOD_SPRIG);
        registerItem("rowan_berries", ROWAN_BERRIES);
        registerItem("sloe_berries", SLOE_BERRIES);
        registerItem("juniper_berries", JUNIPER_BERRIES);
        registerItem("blackberry", BLACKBERRY);
        registerItem("hawthorn_berries", HAWTHORN_BERRIES);
        registerItem("sumac_berries", SUMAC_BERRIES);
        registerItem("briar_hips", BRIAR_HIPS);
        registerItem("elder_blossom", ELDER_BLOSSOM);
        registerItem("coneflower_blossom", CONEFLOWER_BLOSSOM);
        registerItem("sanguinary_blossom", SANGUINARY_BLOSSOM);
        registerItem("saint_johns_wort_blossom", SAINT_JOHNS_WORT_BLOSSOM);
        registerItem("iris_blossom", IRIS_BLOSSOM);
        registerItem("chamomile_blossom", CHAMOMILE_BLOSSOM);
        registerItem("ginger_rhizome", GINGER_RHIZOME);
        registerItem("tea_leaf", TEA_LEAF);
        registerItem("hellebore_blossom", HELLEBORE_BLOSSOM);
        registerItem("foxglove_blossom", FOXGLOVE_BLOSSOM);
        registerItem("wormwood_seeds", WORMWOOD_SEEDS);


        CompostingChanceRegistry validItemCompost = CompostingChanceRegistry.INSTANCE;
        validItemCompost.add(AMARANTH_SPRIG, 0.65f);
        validItemCompost.add(AMARANTH_SEEDS, 0.3f);
        validItemCompost.add(AMARANTH_CERISE_SEEDS, 0.3f);
        validItemCompost.add(AMARANTH_SWEETBERRY_SEEDS, 0.3f);
        validItemCompost.add(AMARANTH_TORCH_SEEDS, 0.3f);
        validItemCompost.add(AMARANTH_SUNDEW_SEEDS, 0.3f);
        validItemCompost.add(AMARANTH_CREEPER_SEEDS, 0.3f);
        validItemCompost.add(AMARANTH_VIRIDIAN_SEEDS, 0.3f);
        validItemCompost.add(AMARANTH_GRISELIN_SEEDS, 0.3f);
        validItemCompost.add(AMARANTH_DARK_PASSION_SEEDS, 0.3f);
        validItemCompost.add(AMARANTH_FIREBIRD_SEEDS, 0.3f);
        validItemCompost.add(BELLADONNA_BLOSSOM, 0.65f);
        validItemCompost.add(BELLADONNA_SEEDS, 0.3f);
        validItemCompost.add(BELLADONNA_GLOW_SEEDS, 0.3f);
        validItemCompost.add(BELLADONNA_NOCTURNAL_SEEDS, 0.3f);
        validItemCompost.add(WORMWOOD_SPRIG, 0.65f);
        validItemCompost.add(WORMWOOD_SEEDS, 0.3f);
        validItemCompost.add(MINT_SPRIG, 0.45f);
        validItemCompost.add(ROWAN_BERRIES, 0.45f);
        validItemCompost.add(SLOE_BERRIES, 0.45f);
        validItemCompost.add(JUNIPER_BERRIES, 0.45f);
        validItemCompost.add(BLACKBERRY, 0.45f);
        validItemCompost.add(HAWTHORN_BERRIES, 0.45f);
        validItemCompost.add(SUMAC_BERRIES, 0.45f);
        validItemCompost.add(BRIAR_HIPS, 0.45f);
        validItemCompost.add(ELDER_BLOSSOM, 0.45f);
        validItemCompost.add(CONEFLOWER_BLOSSOM, 0.45f);
        validItemCompost.add(SANGUINARY_BLOSSOM, 0.45f);
        validItemCompost.add(SAINT_JOHNS_WORT_BLOSSOM, 0.45f);
        validItemCompost.add(IRIS_BLOSSOM, 0.45f);
        validItemCompost.add(CHAMOMILE_BLOSSOM, 0.45f);
        validItemCompost.add(GINGER_RHIZOME, 0.45f);
        validItemCompost.add(TEA_LEAF, 0.45f);
        validItemCompost.add(HELLEBORE_BLOSSOM, 0.45f);
        validItemCompost.add(FOXGLOVE_BLOSSOM, 0.45f);

        if (WKConfig.get().debugMode) {
            WK.logger.info("Witches Kitchen Base Items: Successfully Loaded");
        }
    }

    public static void registerItem(String id, Item item) {
        Registry.register(Registry.ITEM, new WKIdentifier(id), item);
    }
}

