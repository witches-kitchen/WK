package cf.witcheskitchen.common.event;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.common.registry.WKBlocks;
import cf.witcheskitchen.common.registry.WKItems;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import static cf.witcheskitchen.common.registry.WKBlocks.*;
import static cf.witcheskitchen.common.registry.WKItems.*;

public class WKItemGroupEvents {
    public static final ResourceKey<CreativeModeTab> GENERAL_TAB = ResourceKey.create(Registries.CREATIVE_MODE_TAB, WitchesKitchen.id("general"));
    public static final ResourceKey<CreativeModeTab> FOOD_TAB = ResourceKey.create(Registries.CREATIVE_MODE_TAB, WitchesKitchen.id("food"));

    public static void init() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, GENERAL_TAB, FabricItemGroup.builder()
                .title(Component.translatable(GENERAL_TAB.location().toLanguageKey("itemGroup")))
                .icon(() -> new ItemStack(WKBlocks.IRON_WITCHES_OVEN.asItem()))
                .build()
        );
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, FOOD_TAB, FabricItemGroup.builder()
                .title(Component.translatable(FOOD_TAB.location().toLanguageKey("itemGroup")))
                .icon(() -> new ItemStack(WKItems.ELDER_TEA)).build()
        );

        ItemGroupEvents.modifyEntriesEvent(GENERAL_TAB).register(WKItemGroupEvents::generalGroup);
        ItemGroupEvents.modifyEntriesEvent(FOOD_TAB).register(WKItemGroupEvents::foodGroup);
    }

    private static void generalGroup(FabricItemGroupEntries e) {
        e.accept(CHALK);
        e.accept(ENCHANTED_CHALK);
        e.accept(BONE_NEEDLE);
        e.accept(TAGLOCK);
        e.accept(WAYSTONE);
        e.accept(SALT_BLOCK);
        e.accept(HEART_OF_INNOCENCE);

        e.accept(BELLADONNA_BLOSSOM);
        e.accept(WORMWOOD_SPRIG);
        e.accept(ELDER_BLOSSOM);
        e.accept(CONEFLOWER_BLOSSOM);
        e.accept(SANGUINARY_BLOSSOM);
        e.accept(ST_JOHNS_WORT_BLOSSOM);
        e.accept(IRIS_BLOSSOM);
        e.accept(CHAMOMILE_BLOSSOM);
        e.accept(GINGER_ROOTS);
        e.accept(HELLEBORE_BLOSSOM);
        e.accept(FOXGLOVE_BLOSSOM);

        e.accept(CALEFACTION_BUNDLE);
        e.accept(CURSE_OF_MIDAS_BUNDLE);
        e.accept(FEAR_BUNDLE);
        e.accept(FIELD_GEISTER_HEX_BUNDLE);
        e.accept(HUNGRY_POCKETS_BUNDLE);
        e.accept(INEPTITUDE_BUNDLE);
        e.accept(MISPLACEMENT_BUNDLE);
        e.accept(NULLARDOR_BUNDLE);
        e.accept(PARANOIA_BUNDLE);
        e.accept(PERUNS_JEST_BUNDLE);

        e.accept(AMARANTH_SEEDS);
        e.accept(BELLADONNA_SEEDS);
        e.accept(BRIAR_SEEDS);
        e.accept(CAMELLIA_SEEDS);
        e.accept(CHAMOMILE_SEEDS);
        e.accept(CONEFLOWER_SEEDS);
        e.accept(FOXGLOVE_SEEDS);
        e.accept(HELLEBORE_SEEDS);
        e.accept(IRIS_SEEDS);
        e.accept(SANGUINARY_SEEDS);
        e.accept(ST_JOHNS_WORT_SEEDS);
        e.accept(WORMWOOD_SEEDS);

        e.accept(BLACKTHORN_LOG);
        e.accept(BLACKTHORN_WOOD);
        e.accept(STRIPPED_BLACKTHORN_LOG);
        e.accept(STRIPPED_BLACKTHORN_WOOD);
        e.accept(BLACKTHORN_PLANKS);
        e.accept(BLACKTHORN_STAIRS);
        e.accept(BLACKTHORN_SLAB);
        e.accept(BLACKTHORN_FENCE);
        e.accept(BLACKTHORN_FENCE_GATE);
        e.accept(BLACKTHORN_DOOR);
        e.accept(BLACKTHORN_PRESSURE_PLATE);
        e.accept(BLACKTHORN_BUTTON);
        e.accept(BLACKTHORN_LEAVES);
        e.accept(BLACKTHORN_SAPLING);

        e.accept(ELDER_LOG);
        e.accept(ELDER_WOOD);
        e.accept(STRIPPED_ELDER_LOG);
        e.accept(STRIPPED_ELDER_WOOD);
        e.accept(ELDER_PLANKS);
        e.accept(ELDER_STAIRS);
        e.accept(ELDER_SLAB);
        e.accept(ELDER_FENCE);
        e.accept(ELDER_FENCE_GATE);
        e.accept(ELDER_DOOR);
        e.accept(ELDER_PRESSURE_PLATE);
        e.accept(ELDER_BUTTON);
        e.accept(ELDER_LEAVES);
        e.accept(ELDER_SAPLING);

        e.accept(HAWTHORN_LOG);
        e.accept(HAWTHORN_WOOD);
        e.accept(STRIPPED_HAWTHORN_LOG);
        e.accept(STRIPPED_HAWTHORN_WOOD);
        e.accept(HAWTHORN_PLANKS);
        e.accept(HAWTHORN_STAIRS);
        e.accept(HAWTHORN_SLAB);
        e.accept(HAWTHORN_FENCE);
        e.accept(HAWTHORN_FENCE_GATE);
        e.accept(HAWTHORN_DOOR);
        e.accept(HAWTHORN_PRESSURE_PLATE);
        e.accept(HAWTHORN_BUTTON);
        e.accept(HAWTHORN_LEAVES);
        e.accept(HAWTHORN_SAPLING);

        e.accept(JUNIPER_LOG);
        e.accept(JUNIPER_WOOD);
        e.accept(STRIPPED_JUNIPER_LOG);
        e.accept(STRIPPED_JUNIPER_WOOD);
        e.accept(JUNIPER_PLANKS);
        e.accept(JUNIPER_STAIRS);
        e.accept(JUNIPER_SLAB);
        e.accept(JUNIPER_FENCE);
        e.accept(JUNIPER_FENCE_GATE);
        e.accept(JUNIPER_DOOR);
        e.accept(JUNIPER_PRESSURE_PLATE);
        e.accept(JUNIPER_BUTTON);
        e.accept(JUNIPER_LEAVES);
        e.accept(JUNIPER_SAPLING);

        e.accept(ROWAN_LOG);
        e.accept(ROWAN_WOOD);
        e.accept(STRIPPED_ROWAN_LOG);
        e.accept(STRIPPED_ROWAN_WOOD);
        e.accept(ROWAN_PLANKS);
        e.accept(ROWAN_STAIRS);
        e.accept(ROWAN_SLAB);
        e.accept(ROWAN_FENCE);
        e.accept(ROWAN_FENCE_GATE);
        e.accept(ROWAN_DOOR);
        e.accept(ROWAN_PRESSURE_PLATE);
        e.accept(ROWAN_BUTTON);
        e.accept(ROWAN_LEAVES);
        e.accept(ROWAN_SAPLING);

        e.accept(SUMAC_LOG);
        e.accept(SUMAC_WOOD);
        e.accept(STRIPPED_SUMAC_LOG);
        e.accept(STRIPPED_SUMAC_WOOD);
        e.accept(SUMAC_PLANKS);
        e.accept(SUMAC_STAIRS);
        e.accept(SUMAC_SLAB);
        e.accept(SUMAC_FENCE);
        e.accept(SUMAC_FENCE_GATE);
        e.accept(SUMAC_DOOR);
        e.accept(SUMAC_PRESSURE_PLATE);
        e.accept(SUMAC_BUTTON);
        e.accept(SUMAC_LEAVES);
        e.accept(SUMAC_SAPLING);

        e.accept(TEAPOT);
        e.accept(CAST_IRON_TEAPOT);
        e.accept(COPPER_TEAPOT);
        e.accept(WAXED_COPPER_TEAPOT);
        e.accept(EXPOSED_COPPER_TEAPOT);
        e.accept(WAXED_EXPOSED_COPPER_TEAPOT);
        e.accept(WEATHERED_COPPER_TEAPOT);
        e.accept(WAXED_WEATHERED_COPPER_TEAPOT);
        e.accept(OXIDIZED_COPPER_TEAPOT);
        e.accept(WAXED_OXIDIZED_COPPER_TEAPOT);

        e.accept(IRON_WITCHES_OVEN);
        e.accept(COPPER_WITCHES_OVEN);
        e.accept(WAXED_COPPER_WITCHES_OVEN);
        e.accept(EXPOSED_COPPER_WITCHES_OVEN);
        e.accept(WAXED_EXPOSED_COPPER_WITCHES_OVEN);
        e.accept(WEATHERED_COPPER_WITCHES_OVEN);
        e.accept(WAXED_WEATHERED_COPPER_WITCHES_OVEN);
        e.accept(OXIDIZED_COPPER_WITCHES_OVEN);
        e.accept(WAXED_OXIDIZED_COPPER_WITCHES_OVEN);
        e.accept(IRON_WITCHES_CAULDRON);
        e.accept(OAK_BREWING_BARREL);
        e.accept(SPRUCE_BREWING_BARREL);
        e.accept(BIRCH_BREWING_BARREL);
        e.accept(JUNGLE_BREWING_BARREL);
        e.accept(ACACIA_BREWING_BARREL);
        e.accept(DARK_OAK_BREWING_BARREL);
        e.accept(CRIMSON_BREWING_BARREL);
        e.accept(WARPED_BREWING_BARREL);

        e.accept(CU_SITH_SPAWN_EGG);
        e.accept(CHURCH_GRIM_SPAWN_EGG);
        e.accept(FERRET_SPAWN_EGG);
        e.accept(HEDGEHOG_SPAWN_EGG);
        e.accept(ROGGENWOLF_SPAWN_EGG);
    }

    private static void foodGroup(FabricItemGroupEntries e) {
        e.accept(AMARANTH_SPRIG);
        e.accept(MINT_SPRIG);
        e.accept(ROWAN_BERRIES);
        e.accept(SLOE_BERRIES);
        e.accept(JUNIPER_BERRIES);
        e.accept(BLACKBERRY);
        e.accept(HAWTHORN_BERRIES);
        e.accept(SUMAC_BERRIES);
        e.accept(BRIAR_HIPS);
        e.accept(TEA_LEAF);

        e.accept(BLACKBERRY_TEA);
        e.accept(CHAMOMILE_TEA);
        e.accept(DOGROSE_TEA);
        e.accept(ECHINACEA_TEA);
        e.accept(ELDER_TEA);
        e.accept(GINGER_TEA);
        e.accept(HAWTHORN_TEA);
        e.accept(ST_JOHNS_WORT_TEA);
        e.accept(MINT_TEA);
        e.accept(SUMAC_TEA);
        e.accept(YARROW_TEA);

        e.accept(ABSINTHE);
        e.accept(BLACKBERRY_LIQUEUR);
        e.accept(BRINJEVEC);
        e.accept(HOLUNDERSEKT);
        e.accept(JUNIPER_MEAD);
        e.accept(RUM);
        e.accept(TRAVARICA);
        e.accept(GROUND_BEEF);
        e.accept(GROUND_MUTTON);
        e.accept(GROUND_PORK);
        e.accept(HEART_PIE);
        e.accept(ROOTS_PLATTER);
        e.accept(DEMONIC_STEW);
        e.accept(MEATY_STEW);
        e.accept(VEGETABLE_STEW);
    }


}
