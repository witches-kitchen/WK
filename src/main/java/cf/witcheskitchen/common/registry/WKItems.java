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

public interface WKItems {

    List<ObjectDefinition<Item>> ITEMS = new ArrayList<>();
    // Flowers
    Item BELLADONNA_BLOSSOM = registerItem("belladonna_blossom");
    Item AMARANTH_SPRIG = registerFoodStuffs("amaranth_sprig", settings -> new Item(settings.food(WKFoodComponents.AMARANTH_GRAIN)));
    Item MINT_SPRIG = registerFoodStuffs("mint_sprig", settings -> new Item(settings.food(WKFoodComponents.MINT_LEAF)));
    Item WORMWOOD_SPRIG = registerItem("wormwood_sprig");
    Item ROWAN_BERRIES = registerFoodStuffs("rowan_berries", settings -> new Item(settings.food(WKFoodComponents.BERRIES)));
    Item SLOE_BERRIES = registerFoodStuffs("sloe_berries", settings -> new Item(settings.food(WKFoodComponents.BERRIES)));
    Item JUNIPER_BERRIES = registerFoodStuffs("juniper_berries", settings -> new Item(settings.food(WKFoodComponents.BERRIES)));
    Item BLACKBERRY = registerFoodStuffs("blackberry", settings -> new Item(settings.food(WKFoodComponents.BERRIES)));
    Item HAWTHORN_BERRIES = registerFoodStuffs("hawthorn_berries", settings -> new Item(settings.food(WKFoodComponents.BERRIES)));
    Item SUMAC_BERRIES = registerFoodStuffs("sumac_berries", settings -> new Item(settings.food(WKFoodComponents.BERRIES)));
    Item BRIAR_HIPS = registerFoodStuffs("briar_hips", settings -> new Item(settings.food(WKFoodComponents.BERRIES)));
    Item ELDER_BLOSSOM = registerItem("elder_blossom");
    Item CONEFLOWER_BLOSSOM = registerItem("coneflower_blossom");
    Item SANGUINARY_BLOSSOM = registerItem("sanguinary_blossom");
    Item SAINT_JOHNS_WORT_BLOSSOM = registerItem("saint_johns_wort_blossom");
    Item IRIS_BLOSSOM = registerItem("iris_blossom");
    Item CHAMOMILE_BLOSSOM = registerItem("chamomile_blossom");
    Item GINGER_RHIZOME = registerItem("ginger_rhizome");
    Item TEA_LEAF = registerFoodStuffs("tea_leaf", settings -> new Item(settings.food(WKFoodComponents.TEA_LEAF)));
    Item HELLEBORE_BLOSSOM = registerItem("hellebore_blossom");
    Item FOXGLOVE_BLOSSOM = registerItem("foxglove_blossom");

    //Foodstuffs
    Item AMARANTH_COOKIES = registerFoodStuffs("amaranth_cookies", settings -> new Item(settings.food(WKFoodComponents.COOKIES)));

    //Materials
    Item HEART_OF_INNOCENCE = registerItem("heart_of_innocence");

    Item AMARANTH_SEEDS = registerSeed("amaranth_seeds", WKBlocks.AMARANTH);
    Item BELLADONNA_SEEDS = registerSeed("belladonna_seeds", WKBlocks.BELLADONNA);
    Item ST_JOHNS_WORT_SEEDS = registerItem("st_john_wort_seeds");
    Item BRIAR_SEEDS = registerItem("briar_seeds");
    Item CAMELLIA_SEEDS = registerItem("camellia_seeds");
    Item CHAMOMILE_SEEDS = registerItem("chamomile_seeds");
    Item CONEFLOWER_SEEDS = registerItem("coneflower_seeds");
    Item FOXGLOVE_SEEDS = registerItem("foxglove_seeds");
    Item HELLEBORE_SEEDS = registerItem("hellebore_seeds");
    Item IRIS_SEEDS = registerItem("iris_seeds");
    Item SANGUINARY_SEEDS = registerItem("sanguinary_seeds");
    Item WORMWOOD_SEEDS = registerItem("wormwood_seeds");

    Item DOLLOP_OF_FROSTING = registerFoodStuffs("dollop_of_frosting", settings -> new Item(settings.food(WKFoodComponents.FROSTING)));
    Item CHOCOLATE_RUM_BALLS = registerFoodStuffs("chocolate_rum_balls", settings -> new Item(settings.food(WKFoodComponents.RUM_BALLS)));
    Item SUPER_BOOZE = registerFoodStuffs("super_booze", settings -> new Item(new Item.Settings().food(WKFoodComponents.SUPER_BOOZE)));
    //Todo: Convert to usage of hex triplets fully
    Item CU_SITH_SPAWN_EGG = registerSpawnEgg("cu_sith_spawn_egg", settings -> new SpawnEggItem(WKEntityTypes.CUSITH, 3421236, 3497531, settings));
    Item FERRET_SPAWN_EGG = registerSpawnEgg("ferret_spawn_egg", settings -> new SpawnEggItem(WKEntityTypes.FERRET, 9985082, 2631205, settings));
    Item CHURCH_GRIM_SPAWN_EGG = registerSpawnEgg("church_grim_spawn_egg", settings -> new SpawnEggItem(WKEntityTypes.CHURCH_GRIM, 0xFFFAFA, 0x36454F, settings));

    /**
     * Returns an <a href="Collection.html#unmodview">read-only view</a> of the WitchesKitchen's Items
     */
    static List<ObjectDefinition<Item>> getItems() {
        return Collections.unmodifiableList(ITEMS);
    }

    /**
     * Use me for registering seeds!
     */

    static Item registerSeed(String id, Block block) {
        return register(id, settings -> new WKSeedItem(block, settings), WitchesKitchen.SEED_TAB);
    }

    /**
     * Use me for registering items!
     */
    static Item registerItem(String id) {
        return register(id, Item::new, WitchesKitchen.SEED_TAB);
    }

    /**
     * Use me for registering foodstuffs!
     */
    static <T extends Item> T registerFoodStuffs(String id, Function<QuiltItemSettings, T> factory) {
        return register(id, factory, WitchesKitchen.SEED_TAB);
    }

    static Item register(String id) {
        return register(id, Item::new, WitchesKitchen.SEED_TAB);
    }

    /**
     * Use me for registering spawn eggs!
     */
    static <T extends Item> T registerSpawnEgg(String id, Function<QuiltItemSettings, T> factory) {
        return register(id, factory, WitchesKitchen.GENERAL_TAB);
    }

    static <T extends Item> T register(String id, Function<QuiltItemSettings, T> factory, ItemGroup tab) {
        final Identifier resource = WitchesKitchen.id(id);
        final T item = factory.apply(itemBuilder(tab));
        final ObjectDefinition<Item> itemIdentifier = new ObjectDefinition<>(resource, item);
        ITEMS.add(itemIdentifier);
        return item;
    }

    static QuiltItemSettings itemBuilder(ItemGroup tab) {
        return new QuiltItemSettings().group(tab);
    }

    static void init() {
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
    }
}

