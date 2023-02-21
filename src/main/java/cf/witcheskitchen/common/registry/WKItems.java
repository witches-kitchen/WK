package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.api.registry.ObjectDefinition;
import cf.witcheskitchen.common.block.GlyphBlock;
import cf.witcheskitchen.common.item.ChalkItem;
import cf.witcheskitchen.common.item.CurseBundleItem;
import cf.witcheskitchen.common.item.TaglockItem;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.registry.Registry;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface WKItems {

    List<ObjectDefinition<Item>> ITEMS = new ArrayList<>();

    Item CHALK = register("chalk", new ChalkItem(settings(WitchesKitchen.GENERAL_TAB), WKBlocks.GLYPH));
    Item ENCHANTED_CHALK = register("enchanted_chalk", new ChalkItem(settings(WitchesKitchen.GENERAL_TAB), WKBlocks.ENCHANTED_GLYPH));
    Item BONE_NEEDLE = register("bone_needle");
    Item TAGLOCK = register("taglock", new TaglockItem(settings(WitchesKitchen.GENERAL_TAB)));

    Item CALEFACTION_BUNDLE = register("calefaction_bundle", new CurseBundleItem(settings(WitchesKitchen.GENERAL_TAB), 2));
    Item CURSE_OF_MIDAS_BUNDLE = register("curse_of_midas_bundle", new CurseBundleItem(settings(WitchesKitchen.GENERAL_TAB), 1));
    Item FEAR_BUNDLE = register("fear_bundle", new CurseBundleItem(settings(WitchesKitchen.GENERAL_TAB), 1));
    Item FIELD_GEISTER_HEX_BUNDLE = register("field_geister_hex_bundle", new CurseBundleItem(settings(WitchesKitchen.GENERAL_TAB), 1));
    Item HUNGRY_POCKETS_BUNDLE = register("hungry_pockets_bundle", new CurseBundleItem(settings(WitchesKitchen.GENERAL_TAB), 1));
    Item INEPTITUDE_BUNDLE = register("ineptitude_bundle", new CurseBundleItem(settings(WitchesKitchen.GENERAL_TAB), 1));
    Item MISPLACEMENT_BUNDLE = register("misplacement_bundle", new CurseBundleItem(settings(WitchesKitchen.GENERAL_TAB), 1));
    Item NULLARDOR_BUNDLE = register("nullardor_bundle", new CurseBundleItem(settings(WitchesKitchen.GENERAL_TAB), 1));
    Item PARANOIA_BUNDLE = register("paranoia_bundle", new CurseBundleItem(settings(WitchesKitchen.GENERAL_TAB), 3));
    Item PERUNS_JEST_BUNDLE = register("peruns_jest_bundle", new CurseBundleItem(settings(WitchesKitchen.GENERAL_TAB), 1));


    //Flowers
    Item BELLADONNA_BLOSSOM = register("belladonna_blossom");
    Item AMARANTH_SPRIG = register("amaranth_sprig", new Item(settings(WitchesKitchen.FOOD_TAB).food(WKFoodComponents.AMARANTH_GRAIN)));
    Item MINT_SPRIG = register("mint_sprig", new Item(settings(WitchesKitchen.FOOD_TAB).food(WKFoodComponents.MINT_LEAF)));
    Item WORMWOOD_SPRIG = register("wormwood_sprig");
    Item ROWAN_BERRIES = register("rowan_berries", new Item(settings(WitchesKitchen.FOOD_TAB).food(WKFoodComponents.BERRIES)));
    Item SLOE_BERRIES = register("sloe_berries", new Item(settings(WitchesKitchen.FOOD_TAB).food(WKFoodComponents.BERRIES)));
    Item JUNIPER_BERRIES = register("juniper_berries", new Item(settings(WitchesKitchen.FOOD_TAB).food(WKFoodComponents.BERRIES)));
    Item BLACKBERRY = register("blackberry", new Item(settings(WitchesKitchen.FOOD_TAB).food(WKFoodComponents.BERRIES)));
    Item HAWTHORN_BERRIES = register("hawthorn_berries", new Item(settings(WitchesKitchen.FOOD_TAB).food(WKFoodComponents.BERRIES)));
    Item SUMAC_BERRIES = register("sumac_berries", new Item(settings(WitchesKitchen.FOOD_TAB).food(WKFoodComponents.BERRIES)));
    Item BRIAR_HIPS = register("briar_hips", new Item(settings(WitchesKitchen.FOOD_TAB).food(WKFoodComponents.BERRIES)));
    Item ELDER_BLOSSOM = register("elder_blossom");
    Item CONEFLOWER_BLOSSOM = register("coneflower_blossom");
    Item SANGUINARY_BLOSSOM = register("sanguinary_blossom");
    Item ST_JOHNS_WORT_BLOSSOM = register("st_johns_wort_blossom");
    Item IRIS_BLOSSOM = register("iris_blossom");
    Item CHAMOMILE_BLOSSOM = register("chamomile_blossom");
    Item GINGER_RHIZOME = register("ginger_rhizome");
    Item TEA_LEAF = register("tea_leaf", new Item(settings(WitchesKitchen.FOOD_TAB).food(WKFoodComponents.TEA_LEAF)));
    Item HELLEBORE_BLOSSOM = register("hellebore_blossom");
    Item FOXGLOVE_BLOSSOM = register("foxglove_blossom");

    //Foodstuffs
    Item BLACKBERRY_TEA = register("blackberry_tea", new Item(settings(WitchesKitchen.FOOD_TAB).food(WKFoodComponents.TEA)));
    Item CHAMOMILE_TEA = register("chamomile_tea", new Item(settings(WitchesKitchen.FOOD_TAB).food(WKFoodComponents.TEA)));
    Item DOGROSE_TEA = register("dogrose_tea", new Item(settings(WitchesKitchen.FOOD_TAB).food(WKFoodComponents.TEA)));
    Item ECHINACEA_TEA = register("echinacea_tea", new Item(settings(WitchesKitchen.FOOD_TAB).food(WKFoodComponents.TEA)));
    Item ELDER_TEA = register("elder_tea", new Item(settings(WitchesKitchen.FOOD_TAB).food(WKFoodComponents.TEA)));
    Item GINGER_TEA = register("ginger_tea", new Item(settings(WitchesKitchen.FOOD_TAB).food(WKFoodComponents.TEA)));
    Item HAWTHORN_TEA = register("hawthorn_tea", new Item(settings(WitchesKitchen.FOOD_TAB).food(WKFoodComponents.TEA)));
    Item ST_JOHNS_WORT_TEA = register("st_johns_wort_tea", new Item(settings(WitchesKitchen.FOOD_TAB).food(WKFoodComponents.TEA)));
    Item MINT_TEA = register("mint_tea", new Item(settings(WitchesKitchen.FOOD_TAB).food(WKFoodComponents.TEA)));
    Item SUMAC_TEA = register("sumac_tea", new Item(settings(WitchesKitchen.FOOD_TAB).food(WKFoodComponents.TEA)));
    Item YARROW_TEA = register("yarrow_tea", new Item(settings(WitchesKitchen.FOOD_TAB).food(WKFoodComponents.TEA)));

    //Materials
    Item HEART_OF_INNOCENCE = register("heart_of_innocence");

    Item AMARANTH_SEEDS = register("amaranth_seeds", new AliasedBlockItem(WKBlocks.AMARANTH, settings(WitchesKitchen.FOOD_TAB)));
    Item BELLADONNA_SEEDS = register("belladonna_seeds", new AliasedBlockItem(WKBlocks.BELLADONNA, settings(WitchesKitchen.FOOD_TAB)));
    Item ST_JOHNS_WORT_SEEDS = register("st_johns_wort_seeds");
    Item BRIAR_SEEDS = register("briar_seeds");
    Item CAMELLIA_SEEDS = register("camellia_seeds", new AliasedBlockItem(WKBlocks.CAMELLIA, settings(WitchesKitchen.FOOD_TAB)));
    Item CHAMOMILE_SEEDS = register("chamomile_seeds", new AliasedBlockItem(WKBlocks.CHAMOMILE, settings(WitchesKitchen.FOOD_TAB)));
    Item CONEFLOWER_SEEDS = register("coneflower_seeds", new AliasedBlockItem(WKBlocks.CONEFLOWER, settings(WitchesKitchen.FOOD_TAB)));
    Item FOXGLOVE_SEEDS = register("foxglove_seeds", new AliasedBlockItem(WKBlocks.FOXGLOVE, settings(WitchesKitchen.FOOD_TAB)));
    Item HELLEBORE_SEEDS = register("hellebore_seeds", new AliasedBlockItem(WKBlocks.HELLEBORE, settings(WitchesKitchen.FOOD_TAB)));
    Item IRIS_SEEDS = register("iris_seeds",new AliasedBlockItem(WKBlocks.IRIS, settings(WitchesKitchen.FOOD_TAB)));
    Item SANGUINARY_SEEDS = register("sanguinary_seeds", new AliasedBlockItem(WKBlocks.SANGUINARY, settings(WitchesKitchen.FOOD_TAB)));
    Item WORMWOOD_SEEDS = register("wormwood_seeds", new AliasedBlockItem(WKBlocks.WORMWOOD, settings(WitchesKitchen.FOOD_TAB)));

    Item DOLLOP_OF_FROSTING = register("dollop_of_frosting", new Item(settings(WitchesKitchen.FOOD_TAB).food(WKFoodComponents.FROSTING)));
    Item CHOCOLATE_RUM_BALLS = register("chocolate_rum_balls", new Item(settings(WitchesKitchen.FOOD_TAB).food(WKFoodComponents.RUM_BALLS)));
    Item SUPER_BOOZE = register("super_booze", new Item(new Item.Settings().food(WKFoodComponents.SUPER_BOOZE)));

    Item CU_SITH_SPAWN_EGG = register("cu_sith_spawn_egg", new SpawnEggItem(WKEntityTypes.CUSITH, 0x343434, 0x355E3B, settings(WitchesKitchen.GENERAL_TAB)));
    Item FERRET_SPAWN_EGG = register("ferret_spawn_egg", new SpawnEggItem(WKEntityTypes.FERRET, 0x985C3A, 0x282625, settings(WitchesKitchen.GENERAL_TAB)));
    Item CHURCH_GRIM_SPAWN_EGG = register("church_grim_spawn_egg", new SpawnEggItem(WKEntityTypes.CHURCH_GRIM, 0xFFFAFA, 0x36454F, settings(WitchesKitchen.GENERAL_TAB)));


    static List<ObjectDefinition<Item>> getItems() {
        return Collections.unmodifiableList(ITEMS);
    }

    static QuiltItemSettings settings(ItemGroup group) {
        return new QuiltItemSettings().group(group);
    }

    static Item register(String string){
        return register(string, new Item(settings(WitchesKitchen.GENERAL_TAB)));
    }
    
    static <T extends Item> T register(String id, T item) {
        ObjectDefinition<Item> itemIdentifier = new ObjectDefinition<>(WitchesKitchen.id(id), item);
        ITEMS.add(itemIdentifier);
        return item;
    }

    static void init() {
        ITEMS.forEach(entry -> Registry.register(Registry.ITEM, entry.id(), entry.object()));
    }
}