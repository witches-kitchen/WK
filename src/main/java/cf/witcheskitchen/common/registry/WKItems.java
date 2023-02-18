package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.WitchesKitchenConfig;
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
    public static final Item BELLADONNA_SEEDS = registerSeed("belladonna_seeds", WKBlocks.BELLADONNA);
    public static final Item ST_JOHNS_WORT_SEEDS = registerItem("st_john_wort_seeds");
    public static final Item BRIAR_SEEDS = registerItem("briar_seeds");
    public static final Item CAMELLIA_SEEDS = registerItem("camellia_seeds");
    public static final Item CHAMOMILE_SEEDS = registerItem("chamomile_seeds");
    public static final Item CONEFLOWER_SEEDS = registerItem("coneflower_seeds");
    public static final Item FOXGLOVE_SEEDS = registerItem("foxglove_seeds");
    public static final Item HELLEBORE_SEEDS = registerItem("hellebore_seeds");
    public static final Item IRIS_SEEDS = registerItem("iris_seeds");
    public static final Item SANGUINARY_SEEDS = registerItem("sanguinary_seeds");
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
        final Identifier resource = WitchesKitchen.id(id);
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
        CompostingChanceRegistry compostingChanceRegistry = CompostingChanceRegistry.INSTANCE;
        compostingChanceRegistry.add(AMARANTH_SPRIG, 0.65f);
        compostingChanceRegistry.add(AMARANTH_SEEDS, 0.3f);
        compostingChanceRegistry.add(BELLADONNA_BLOSSOM, 0.65f);
        compostingChanceRegistry.add(BELLADONNA_SEEDS, 0.3f);
        compostingChanceRegistry.add(WORMWOOD_SPRIG, 0.65f);
        compostingChanceRegistry.add(WORMWOOD_SEEDS, 0.3f);
        compostingChanceRegistry.add(MINT_SPRIG, 0.45f);
        compostingChanceRegistry.add(ROWAN_BERRIES, 0.45f);
        compostingChanceRegistry.add(SLOE_BERRIES, 0.45f);
        compostingChanceRegistry.add(JUNIPER_BERRIES, 0.45f);
        compostingChanceRegistry.add(BLACKBERRY, 0.45f);
        compostingChanceRegistry.add(HAWTHORN_BERRIES, 0.45f);
        compostingChanceRegistry.add(SUMAC_BERRIES, 0.45f);
        compostingChanceRegistry.add(BRIAR_HIPS, 0.45f);
        compostingChanceRegistry.add(ELDER_BLOSSOM, 0.45f);
        compostingChanceRegistry.add(CONEFLOWER_BLOSSOM, 0.45f);
        compostingChanceRegistry.add(SANGUINARY_BLOSSOM, 0.45f);
        compostingChanceRegistry.add(SAINT_JOHNS_WORT_BLOSSOM, 0.45f);
        compostingChanceRegistry.add(IRIS_BLOSSOM, 0.45f);
        compostingChanceRegistry.add(CHAMOMILE_BLOSSOM, 0.45f);
        compostingChanceRegistry.add(GINGER_RHIZOME, 0.45f);
        compostingChanceRegistry.add(TEA_LEAF, 0.45f);
        compostingChanceRegistry.add(HELLEBORE_BLOSSOM, 0.45f);
        compostingChanceRegistry.add(FOXGLOVE_BLOSSOM, 0.45f);

        if (WitchesKitchenConfig.debugMode) {
            WitchesKitchen.LOGGER.info("Witches Kitchen Base Items: Successfully Loaded");
        }
    }
}

