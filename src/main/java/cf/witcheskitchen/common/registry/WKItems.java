package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.WKConfig;
import cf.witcheskitchen.WKIdentifier;
import cf.witcheskitchen.api.registry.ObjectDefinition;
import cf.witcheskitchen.common.item.WKSeedItem;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class WKItems {

    private static final List<ObjectDefinition<Item>> ITEMS = new ArrayList<>();
    // Flowers
    public static final Item BELLADONNA_BLOSSOM = registerItem("belladonna_blossom");
    public static final Item AMARANTH_SPRIG = registerFoodStuffs("amaranth_sprig", settings -> new Item(settings.food(WKFoodComponents.AMARANTH_GRAIN)));
    public static final Item MINT_SPRIG = registerFoodStuffs("mint_sprig", settings -> new Item(settings.food(WKFoodComponents.MINT_LEAF)));
    public static final Item WORMWOOD_SPRIG = registerItem("wormwood_sprig");
    public static final Item ROWAN_BERRIES = registerFoodStuffs("rowan_berries", settings -> new Item(settings.food(WKFoodComponents.BERRIES)));
    public static final Item SLOE_BERRIES = registerFoodStuffs("sloe_berries", settings -> new Item(settings.food(WKFoodComponents.BERRIES)));
    public static final Item JUNIPER_BERRIES = registerFoodStuffs("juniper_berries", settings -> new Item(settings.food(WKFoodComponents.BERRIES)));
    public static final Item BLACKBERRY = registerFoodStuffs("blackberry", settings -> new Item(settings.food(WKFoodComponents.BERRIES)));
    public static final Item HAWTHORN_BERRIES = registerFoodStuffs("hawthorn_berries", settings -> new Item(settings.food(WKFoodComponents.BERRIES)));
    public static final Item SUMAC_BERRIES = registerFoodStuffs("sumac_berries", settings -> new Item(settings.food(WKFoodComponents.BERRIES)));
    public static final Item BRIAR_HIPS = registerFoodStuffs("briar_hips", settings -> new Item(settings.food(WKFoodComponents.BERRIES)));
    public static final Item ELDER_BLOSSOM = registerItem("elder_blossom");
    public static final Item CONEFLOWER_BLOSSOM = registerItem("coneflower_blossom");
    public static final Item SANGUINARY_BLOSSOM = registerItem("sanguinary_blossom");
    public static final Item SAINT_JOHNS_WORT_BLOSSOM = registerItem("saint_johns_wort_blossom");
    public static final Item IRIS_BLOSSOM = registerItem("iris_blossom");
    public static final Item CHAMOMILE_BLOSSOM = registerItem("chamomile_blossom");
    public static final Item GINGER_RHIZOME = registerItem("ginger_rhizome");
    public static final Item TEA_LEAF = registerFoodStuffs("tea_leaf", settings -> new Item(settings.food(WKFoodComponents.TEA_LEAF)));
    public static final Item HELLEBORE_BLOSSOM = registerItem("hellebore_blossom");
    public static final Item FOXGLOVE_BLOSSOM = registerItem("foxglove_blossom");

    //Foodstuffs
    public static final Item AMARANTH_COOKIES = registerFoodStuffs("amaranth_cookies", settings -> new Item(settings.food(WKFoodComponents.COOKIES)));

    //Materials
    public static final Item HEART_OF_INNOCENCE = registerItem("heart_of_innocence");

    public static final Item AMARANTH_SEEDS = registerSeed("amaranth_seeds", WKBlocks.AMARANTH);
    public static final Item AMARANTH_SWEETBERRY_SEEDS = registerSeed("amaranth_sweetberry_seeds", WKBlocks.AMARANTH_SWEETBERRY);
    public static final Item AMARANTH_TORCH_SEEDS = registerSeed("amaranth_torch_seeds", WKBlocks.AMARANTH_TORCH);
    public static final Item AMARANTH_SUNDEW_SEEDS = registerSeed("amaranth_sundew_seeds", WKBlocks.AMARANTH_SUNDEW);
    public static final Item AMARANTH_CREEPER_SEEDS = registerSeed("amaranth_creeper_seeds", WKBlocks.AMARANTH_CREEPER);
    public static final Item AMARANTH_VIRIDIAN_SEEDS = registerSeed("amaranth_viridian_seeds", WKBlocks.AMARANTH_VIRIDIAN);
    public static final Item AMARANTH_GRISELIN_SEEDS = registerSeed("amaranth_griselin_seeds", WKBlocks.AMARANTH_GRISELIN);
    public static final Item AMARANTH_CERISE_SEEDS = registerSeed("amaranth_cerise_seeds", WKBlocks.AMARANTH_CERISE);
    public static final Item AMARANTH_DARK_PASSION_SEEDS = registerSeed("amaranth_dark_passion_seeds", WKBlocks.AMARANTH_DARK_PASSION);
    public static final Item AMARANTH_FIREBIRD_SEEDS = registerSeed("amaranth_firebird_seeds", WKBlocks.AMARANTH_FIREBIRD);
    public static final Item BELLADONNA_SEEDS = registerSeed("belladonna_seeds", WKBlocks.BELLADONNA);
    public static final Item BELLADONNA_GLOW_SEEDS = registerSeed("belladonna_glow_seeds", WKBlocks.BELLADONNA_GLOW);
    public static final Item BELLADONNA_NOCTURNAL_SEEDS = registerSeed("belladonna_nocturnal_seeds", WKBlocks.BELLADONNA_NOCTURNAL);
    public static final Item ST_JOHNS_WORT_SEEDS = registerItem("st_john_wort_seeds");
    public static final Item BRIAR_SEEDS = registerItem("briar_seeds");
    public static final Item CAMELLIA_SEEDS = registerItem("camellia_seeds");
    public static final Item CAMELLIA_BUTTERCREAM_SEEDS = registerItem("camellia_buttercream_seeds");
    public static final Item CAMELLIA_BISQUE_SEEDS = registerItem("camellia_bisque_seeds");
    public static final Item CAMELLIA_FLINT_SEEDS = registerItem("camellia_flint_seeds");
    public static final Item CAMELLIA_DEEP_LOVE_SEEDS = registerItem("camellia_deep_love_seeds");
    public static final Item CHAMOMILE_SEEDS = registerItem("chamomile_seeds");
    public static final Item CHAMOMILE_VIRESCENT_SEEDS = registerItem("chamomile_virescent_seeds");
    public static final Item CHAMOMILE_STARLETT_SEEDS = registerItem("chamomile_starlett_seeds");
    public static final Item CHAMOMILE_DYEWORKS_SEEDS = registerItem("chamomile_dyeworks_seeds");
    public static final Item CONEFLOWER_SEEDS = registerItem("coneflower_seeds");
    public static final Item CONEFLOWER_DANCING_LADIES_SEEDS = registerItem("coneflower_dancing_ladies_seeds");
    public static final Item CONEFLOWER_VIOLET_SEEDS = registerItem("coneflower_violet_seeds");
    public static final Item CONEFLOWER_QUEENS_DESIRE_SEEDS = registerItem("coneflower_queens_desire_seeds");
    public static final Item CONEFLOWER_ROSE_DRESS_SEEDS = registerItem("coneflower_rose_dress_seeds");
    public static final Item CONEFLOWER_SUITOR_SEEDS = registerItem("coneflower_suitor_seeds");
    public static final Item CONEFLOWER_NETHER_SEEDS = registerItem("coneflower_nether_seeds");
    public static final Item CONEFLOWER_LADYS_WISH_SEEDS = registerItem("coneflower_ladys_wish_seeds");
    public static final Item CONEFLOWER_SUNGLOW_SEEDS = registerItem("coneflower_sunglow_seeds");
    public static final Item CONEFLOWER_FLAME_SEEDS = registerItem("coneflower_flame_seeds");
    public static final Item CONEFLOWER_GILDED_SEEDS = registerItem("coneflower_gilded_seeds");
    public static final Item CONEFLOWER_MORNING_MIST_SEEDS = registerItem("coneflower_morning_mist_seeds");
    public static final Item CONEFLOWER_FLEECE_SEEDS = registerItem("coneflower_fleece_seeds");
    public static final Item CONEFLOWER_COMPANY_SEEDS = registerItem("coneflower_company_seeds");
    public static final Item CONEFLOWER_MASQUERADE_SEEDS = registerItem("coneflower_masquerade_seeds");
    public static final Item CONEFLOWER_PARTY_BLEND_SEEDS = registerItem("coneflower_party_blend_seeds");
    public static final Item FOXGLOVE_SEEDS = registerItem("foxglove_seeds");
    public static final Item FOXGLOVE_SMALT_SEEDS = registerItem("foxglove_smalt_seeds");
    public static final Item FOXGLOVE_TRANQUIL_EVENING_SEEDS = registerItem("foxglove_tranquil_evening_seeds");
    public static final Item FOXGLOVE_PURPUREA_SEEDS = registerItem("foxglove_purpurea_seeds");
    public static final Item FOXGLOVE_LOVELY_MORNING_SEEDS = registerItem("foxglove_lovely_morning_seeds");
    public static final Item FOXGLOVE_IANTHINE_SEEDS = registerItem("foxglove_ianthine_seeds");
    public static final Item FOXGLOVE_QUEENS_HAT_SEEDS = registerItem("foxglove_queens_hat_seeds");
    public static final Item FOXGLOVE_BLUSH_SEEDS = registerItem("foxglove_blush_seeds");
    public static final Item FOXGLOVE_ROYAL_BLANKET_SEEDS = registerItem("foxglove_royal_blanket_seeds");
    public static final Item FOXGLOVE_LOVE_SEEDS = registerItem("foxglove_love_seeds");
    public static final Item FOXGLOVE_BABYS_DRESS_SEEDS = registerItem("foxglove_babys_dress_seeds");
    public static final Item FOXGLOVE_STROLL_SEEDS = registerItem("foxglove_stroll_seeds");
    public static final Item FOXGLOVE_MAIDENS_PINK_SEEDS = registerItem("foxglove_maidens_pink_seeds");
    public static final Item FOXGLOVE_MORNING_FIELD_SEEDS = registerItem("foxglove_morning_field_seeds");
    public static final Item FOXGLOVE_SIGHE_GOWN_SEEDS = registerItem("foxglove_sighe_gown_seeds");
    public static final Item FOXGLOVE_CALAMINE_SEEDS = registerItem("foxglove_calamine_seeds");
    public static final Item FOXGLOVE_NETHERINE_SEEDS = registerItem("foxglove_netherine_seeds");
    public static final Item FOXGLOVE_SUNGLOW_SEEDS = registerItem("foxglove_sunglow_seeds");
    public static final Item FOXGLOVE_SANDSTONE_TEMPLE_SEEDS = registerItem("foxglove_sandstone_temple_seeds");
    public static final Item FOXGLOVE_FIERY_FIELD_SEEDS = registerItem("foxglove_fiery_field_seeds");
    public static final Item FOXGLOVE_PASSION_SEEDS = registerItem("foxglove_passion_seeds");
    public static final Item FOXGLOVE_BASTARD_AMBER_SEEDS = registerItem("foxglove_bastard_amber_seeds");
    public static final Item HELLEBORE_SEEDS = registerItem("hellebore_seeds");
    public static final Item HELLEBORE_MORNING_TEA_SEEDS = registerItem("hellebore_morning_tea_seeds");
    public static final Item HELLEBORE_CASANOVA_SEEDS = registerItem("hellebore_casanova_seeds");
    public static final Item HELLEBORE_BLUSHING_SEEDS = registerItem("hellebore_blushing_seeds");
    public static final Item HELLEBORE_CELADON_SEEDS = registerItem("hellebore_celadon_seeds");
    public static final Item HELLEBORE_FURY_SEEDS = registerItem("hellebore_fury_seeds");
    public static final Item HELLEBORE_ANGEL_SEEDS = registerItem("hellebore_angel_seeds");
    public static final Item HELLEBORE_TWILIGHT_SEEDS = registerItem("hellebore_twilight_seeds");
    public static final Item HELLEBORE_GRIMM_SEEDS = registerItem("hellebore_grimm_seeds");
    public static final Item HELLEBORE_NOCTURNE_SEEDS = registerItem("hellebore_nocturne_seeds");
    public static final Item IRIS_SEEDS = registerItem("iris_seeds");
    public static final Item IRIS_OCEAN_SEEDS = registerItem("iris_ocean_seeds");
    public static final Item IRIS_DEEP_SEA_SEEDS = registerItem("iris_deep_sea_seeds");
    public static final Item IRIS_BLEEDING_HEART_SEEDS = registerItem("iris_bleeding_heart_seeds");
    public static final Item SANGUINARY_SEEDS = registerItem("sanguinary_seeds");
    public static final Item SANGUINARY_MEADOW_SEEDS = registerItem("sanguinary_meadow_seeds");
    public static final Item SANGUINARY_BLUSHING_SEEDS = registerItem("sanguinary_blushing_seeds");
    public static final Item SANGUINARY_SUNSET_SEEDS = registerItem("sanguinary_sunset_seeds");
    public static final Item SANGUINARY_MADDER_SEEDS = registerItem("sanguinary_madder_seeds");
    public static final Item SANGUINARY_AUREOLIN_SEEDS = registerItem("sanguinary_aureolin_seeds");
    public static final Item WORMWOOD_SEEDS = registerItem("wormwood_seeds");
    public static final Item DOLLOP_OF_FROSTING = registerFoodStuffs("dollop_of_frosting", settings -> new Item(settings.food(WKFoodComponents.FROSTING)));
    public static final Item CHOCOLATE_RUM_BALLS = registerFoodStuffs("chocolate_rum_balls", settings -> new Item(settings.food(WKFoodComponents.RUM_BALLS)));
    public static final Item SUPER_BOOZE = registerFoodStuffs("super_booze", settings -> new Item(new Item.Settings().food(WKFoodComponents.SUPER_BOOZE)));
    //Todo: Convert to usage of hex triplets fully
    public static final Item CU_SITH_SPAWN_EGG = registerSpawnEgg("cu_sith_spawn_egg", settings -> new SpawnEggItem(WKEntityTypes.CUSITH, 3421236, 3497531, settings));
    public static final Item FERRET_SPAWN_EGG = registerSpawnEgg("ferret_spawn_egg", settings -> new SpawnEggItem(WKEntityTypes.FERRET, 9985082, 2631205, settings));
    public static final Item CHURCH_GRIM_SPAWN_EGG = registerSpawnEgg("church_grim_spawn_egg", settings -> new SpawnEggItem(WKEntityTypes.CHURCH_GRIM, 0xFFFAFA, 0x36454F, settings));

    /**
     * Returns an <a href="Collection.html#unmodview">read-only view</a> of the WitchesKitchen's Items
     */
    public static List<ObjectDefinition<Item>> getItems() {
        return Collections.unmodifiableList(ITEMS);
    }

    /**
     * Use me for registering seeds!
     */

    private static Item registerSeed(String id, Block block) {
        return register(id, settings -> new WKSeedItem(block, settings), WKCreativeTabs.SEED_TAB);
    }

    /**
     * Use me for registering items!
     */
    private static Item registerItem(String id) {
        return register(id, Item::new, WKCreativeTabs.SEED_TAB);
    }

    /**
     * Use me for registering foodstuffs!
     */
    private static <T extends Item> T registerFoodStuffs(String id, Function<QuiltItemSettings, T> factory) {
        return register(id, factory, WKCreativeTabs.SEED_TAB);
    }

    private static Item register(String id) {
        return register(id, Item::new, WKCreativeTabs.SEED_TAB);
    }

    /**
     * Use me for registering spawn eggs!
     */
    private static <T extends Item> T registerSpawnEgg(String id, Function<QuiltItemSettings, T> factory) {
        return register(id, factory, WKCreativeTabs.GENERAL_TAB);
    }

    private static <T extends Item> T register(String id, Function<QuiltItemSettings, T> factory, ItemGroup tab) {
        final Identifier resource = new WKIdentifier(id);
        final T item = factory.apply(itemBuilder(tab));
        final ObjectDefinition<Item> itemIdentifier = new ObjectDefinition<>(resource, item);
        ITEMS.add(itemIdentifier);
        return item;
    }

    private static QuiltItemSettings itemBuilder(ItemGroup tab) {
        return new QuiltItemSettings().group(tab);
    }

    public static void register() {
        ITEMS.forEach(entry -> Registry.register(Registry.ITEM, entry.id(), entry.object()));
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

        if (WKConfig.getInstance().debugMode) {
            WK.LOGGER.info("Witches Kitchen Base Items: Successfully Loaded");
        }
    }
}

