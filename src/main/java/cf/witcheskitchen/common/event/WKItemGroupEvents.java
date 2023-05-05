package cf.witcheskitchen.common.event;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.common.registry.WKBlocks;
import cf.witcheskitchen.common.registry.WKItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import static cf.witcheskitchen.common.registry.WKBlocks.*;
import static cf.witcheskitchen.common.registry.WKItems.*;

public class WKItemGroupEvents {
    public static final ItemGroup GENERAL_TAB = FabricItemGroup.builder(WitchesKitchen.id("general")).icon(() -> new ItemStack(WKBlocks.IRON_WITCHES_OVEN.asItem())).build();
    public static final ItemGroup FOOD_TAB = FabricItemGroup.builder(WitchesKitchen.id("food")).icon(() -> new ItemStack(WKItems.ELDER_TEA)).build();

    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(GENERAL_TAB).register(WKItemGroupEvents::generalGroup);
        ItemGroupEvents.modifyEntriesEvent(FOOD_TAB).register(WKItemGroupEvents::foodGroup);
    }

    private static void generalGroup(FabricItemGroupEntries e) {
        e.addItem(CHALK);
        e.addItem(ENCHANTED_CHALK);
        e.addItem(BONE_NEEDLE);
        e.addItem(TAGLOCK);
        e.addItem(WAYSTONE);
        e.addItem(SALT_BLOCK);
        e.addItem(HEART_OF_INNOCENCE);

        e.addItem(BELLADONNA_BLOSSOM);
        e.addItem(WORMWOOD_SPRIG);
        e.addItem(ELDER_BLOSSOM);
        e.addItem(CONEFLOWER_BLOSSOM);
        e.addItem(SANGUINARY_BLOSSOM);
        e.addItem(ST_JOHNS_WORT_BLOSSOM);
        e.addItem(IRIS_BLOSSOM);
        e.addItem(CHAMOMILE_BLOSSOM);
        e.addItem(GINGER_ROOTS);
        e.addItem(HELLEBORE_BLOSSOM);
        e.addItem(FOXGLOVE_BLOSSOM);

        e.addItem(CALEFACTION_BUNDLE);
        e.addItem(CURSE_OF_MIDAS_BUNDLE);
        e.addItem(FEAR_BUNDLE);
        e.addItem(FIELD_GEISTER_HEX_BUNDLE);
        e.addItem(HUNGRY_POCKETS_BUNDLE);
        e.addItem(INEPTITUDE_BUNDLE);
        e.addItem(MISPLACEMENT_BUNDLE);
        e.addItem(NULLARDOR_BUNDLE);
        e.addItem(PARANOIA_BUNDLE);
        e.addItem(PERUNS_JEST_BUNDLE);

        e.addItem(AMARANTH_SEEDS);
        e.addItem(BELLADONNA_SEEDS);
        e.addItem(BRIAR_SEEDS);
        e.addItem(CAMELLIA_SEEDS);
        e.addItem(CHAMOMILE_SEEDS);
        e.addItem(CONEFLOWER_SEEDS);
        e.addItem(FOXGLOVE_SEEDS);
        e.addItem(HELLEBORE_SEEDS);
        e.addItem(IRIS_SEEDS);
        e.addItem(SANGUINARY_SEEDS);
        e.addItem(ST_JOHNS_WORT_SEEDS);
        e.addItem(WORMWOOD_SEEDS);

        e.addItem(BLACKTHORN_LOG);
        e.addItem(BLACKTHORN_WOOD);
        e.addItem(STRIPPED_BLACKTHORN_LOG);
        e.addItem(STRIPPED_BLACKTHORN_WOOD);
        e.addItem(BLACKTHORN_PLANKS);
        e.addItem(BLACKTHORN_STAIRS);
        e.addItem(BLACKTHORN_SLAB);
        e.addItem(BLACKTHORN_FENCE);
        e.addItem(BLACKTHORN_FENCE_GATE);
        e.addItem(BLACKTHORN_DOOR);
        e.addItem(BLACKTHORN_PRESSURE_PLATE);
        e.addItem(BLACKTHORN_BUTTON);
        e.addItem(BLACKTHORN_LEAVES);
        e.addItem(BLACKTHORN_SAPLING);

        e.addItem(ELDER_LOG);
        e.addItem(ELDER_WOOD);
        e.addItem(STRIPPED_ELDER_LOG);
        e.addItem(STRIPPED_ELDER_WOOD);
        e.addItem(ELDER_PLANKS);
        e.addItem(ELDER_STAIRS);
        e.addItem(ELDER_SLAB);
        e.addItem(ELDER_FENCE);
        e.addItem(ELDER_FENCE_GATE);
        e.addItem(ELDER_DOOR);
        e.addItem(ELDER_PRESSURE_PLATE);
        e.addItem(ELDER_BUTTON);
        e.addItem(ELDER_LEAVES);
        e.addItem(ELDER_SAPLING);

        e.addItem(HAWTHORN_LOG);
        e.addItem(HAWTHORN_WOOD);
        e.addItem(STRIPPED_HAWTHORN_LOG);
        e.addItem(STRIPPED_HAWTHORN_WOOD);
        e.addItem(HAWTHORN_PLANKS);
        e.addItem(HAWTHORN_STAIRS);
        e.addItem(HAWTHORN_SLAB);
        e.addItem(HAWTHORN_FENCE);
        e.addItem(HAWTHORN_FENCE_GATE);
        e.addItem(HAWTHORN_DOOR);
        e.addItem(HAWTHORN_PRESSURE_PLATE);
        e.addItem(HAWTHORN_BUTTON);
        e.addItem(HAWTHORN_LEAVES);
        e.addItem(HAWTHORN_SAPLING);

        e.addItem(JUNIPER_LOG);
        e.addItem(JUNIPER_WOOD);
        e.addItem(STRIPPED_JUNIPER_LOG);
        e.addItem(STRIPPED_JUNIPER_WOOD);
        e.addItem(JUNIPER_PLANKS);
        e.addItem(JUNIPER_STAIRS);
        e.addItem(JUNIPER_SLAB);
        e.addItem(JUNIPER_FENCE);
        e.addItem(JUNIPER_FENCE_GATE);
        e.addItem(JUNIPER_DOOR);
        e.addItem(JUNIPER_PRESSURE_PLATE);
        e.addItem(JUNIPER_BUTTON);
        e.addItem(JUNIPER_LEAVES);
        e.addItem(JUNIPER_SAPLING);

        e.addItem(ROWAN_LOG);
        e.addItem(ROWAN_WOOD);
        e.addItem(STRIPPED_ROWAN_LOG);
        e.addItem(STRIPPED_ROWAN_WOOD);
        e.addItem(ROWAN_PLANKS);
        e.addItem(ROWAN_STAIRS);
        e.addItem(ROWAN_SLAB);
        e.addItem(ROWAN_FENCE);
        e.addItem(ROWAN_FENCE_GATE);
        e.addItem(ROWAN_DOOR);
        e.addItem(ROWAN_PRESSURE_PLATE);
        e.addItem(ROWAN_BUTTON);
        e.addItem(ROWAN_LEAVES);
        e.addItem(ROWAN_SAPLING);

        e.addItem(SUMAC_LOG);
        e.addItem(SUMAC_WOOD);
        e.addItem(STRIPPED_SUMAC_LOG);
        e.addItem(STRIPPED_SUMAC_WOOD);
        e.addItem(SUMAC_PLANKS);
        e.addItem(SUMAC_STAIRS);
        e.addItem(SUMAC_SLAB);
        e.addItem(SUMAC_FENCE);
        e.addItem(SUMAC_FENCE_GATE);
        e.addItem(SUMAC_DOOR);
        e.addItem(SUMAC_PRESSURE_PLATE);
        e.addItem(SUMAC_BUTTON);
        e.addItem(SUMAC_LEAVES);
        e.addItem(SUMAC_SAPLING);

        e.addItem(TEAPOT);
        e.addItem(CAST_IRON_TEAPOT);
        e.addItem(COPPER_TEAPOT);
        e.addItem(WAXED_COPPER_TEAPOT);
        e.addItem(EXPOSED_COPPER_TEAPOT);
        e.addItem(WAXED_EXPOSED_COPPER_TEAPOT);
        e.addItem(WEATHERED_COPPER_TEAPOT);
        e.addItem(WAXED_WEATHERED_COPPER_TEAPOT);
        e.addItem(OXIDIZED_COPPER_TEAPOT);
        e.addItem(WAXED_OXIDIZED_COPPER_TEAPOT);

        e.addItem(IRON_WITCHES_OVEN);
        e.addItem(COPPER_WITCHES_OVEN);
        e.addItem(WAXED_COPPER_WITCHES_OVEN);
        e.addItem(EXPOSED_COPPER_WITCHES_OVEN);
        e.addItem(WAXED_EXPOSED_COPPER_WITCHES_OVEN);
        e.addItem(WEATHERED_COPPER_WITCHES_OVEN);
        e.addItem(WAXED_WEATHERED_COPPER_WITCHES_OVEN);
        e.addItem(OXIDIZED_COPPER_WITCHES_OVEN);
        e.addItem(WAXED_OXIDIZED_COPPER_WITCHES_OVEN);
        e.addItem(IRON_WITCHES_CAULDRON);
        e.addItem(OAK_BREWING_BARREL);
        e.addItem(SPRUCE_BREWING_BARREL);
        e.addItem(BIRCH_BREWING_BARREL);
        e.addItem(JUNGLE_BREWING_BARREL);
        e.addItem(ACACIA_BREWING_BARREL);
        e.addItem(DARK_OAK_BREWING_BARREL);
        e.addItem(CRIMSON_BREWING_BARREL);
        e.addItem(WARPED_BREWING_BARREL);

        e.addItem(CU_SITH_SPAWN_EGG);
        e.addItem(CHURCH_GRIM_SPAWN_EGG);
        e.addItem(FERRET_SPAWN_EGG);
        e.addItem(HEDGEHOG_SPAWN_EGG);
        e.addItem(ROGGENWOLF_SPAWN_EGG);
    }

    private static void foodGroup(FabricItemGroupEntries e) {
        e.addItem(AMARANTH_SPRIG);
        e.addItem(MINT_SPRIG);
        e.addItem(ROWAN_BERRIES);
        e.addItem(SLOE_BERRIES);
        e.addItem(JUNIPER_BERRIES);
        e.addItem(BLACKBERRY);
        e.addItem(HAWTHORN_BERRIES);
        e.addItem(SUMAC_BERRIES);
        e.addItem(BRIAR_HIPS);
        e.addItem(TEA_LEAF);

        e.addItem(BLACKBERRY_TEA);
        e.addItem(CHAMOMILE_TEA);
        e.addItem(DOGROSE_TEA);
        e.addItem(ECHINACEA_TEA);
        e.addItem(ELDER_TEA);
        e.addItem(GINGER_TEA);
        e.addItem(HAWTHORN_TEA);
        e.addItem(ST_JOHNS_WORT_TEA);
        e.addItem(MINT_TEA);
        e.addItem(SUMAC_TEA);
        e.addItem(YARROW_TEA);

        e.addItem(ABSINTHE);
        e.addItem(BLACKBERRY_LIQUEUR);
        e.addItem(BRINJEVEC);
        e.addItem(HOLUNDERSEKT);
        e.addItem(JUNIPER_MEAD);
        e.addItem(RUM);
        e.addItem(TRAVARICA);
        e.addItem(GROUND_BEEF);
        e.addItem(GROUND_MUTTON);
        e.addItem(GROUND_PORK);
        e.addItem(HEART_PIE);
        e.addItem(ROOTS_PLATTER);
        e.addItem(DEMONIC_STEW);
        e.addItem(MEATY_STEW);
        e.addItem(VEGETABLE_STEW);
    }


}
