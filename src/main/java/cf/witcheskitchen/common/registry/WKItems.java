package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.WKConfig;
import cf.witcheskitchen.WKIdentifier;
import cf.witcheskitchen.api.registry.ObjectDefinition;
import cf.witcheskitchen.common.items.WKSeedItem;
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

    public static List<ObjectDefinition<Item>> getItems() {
        return Collections.unmodifiableList(ITEMS);
    }

    /**
     * Use me for registering seeds!
     */

    static Item registerSeed(String id, Block block) {
        return register(id, settings -> new WKSeedItem(block, settings), WKCreativeTabs.SEED_TAB);
    }

    /**
     * Use me for registering items!
     */
    static Item registerItem(String id) {
        return register(id, Item::new, WKCreativeTabs.SEED_TAB);
    }

    /**
     * Use me for registering foodstuffs!
     */
    static <T extends Item> T registerFoodStuffs(String id, Function<QuiltItemSettings, T> factory) {
        return register(id, factory, WKCreativeTabs.SEED_TAB);
    }

    static Item register(String id) {
        return register(id, Item::new, WKCreativeTabs.SEED_TAB);
    }

    /**
     * Use me for registering spawn eggs!
     */
    static <T extends Item> T registerSpawnEgg(String id, Function<QuiltItemSettings, T> factory) {
        return register(id, factory, WKCreativeTabs.SEED_TAB);
    }

    static <T extends Item> T register(String id, Function<QuiltItemSettings, T> factory, ItemGroup tab) {
        final Identifier resource = new WKIdentifier(id);
        final T item = factory.apply(new QuiltItemSettings().group(tab));
        final ObjectDefinition<Item> itemIdentifier = new ObjectDefinition<>(resource, item);
        ITEMS.add(itemIdentifier);
        return item;
    }

    public static void register() {
        for (ObjectDefinition<Item> entry : ITEMS) {
            Registry.register(Registry.ITEM, entry.id(), entry.object());
        }
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

