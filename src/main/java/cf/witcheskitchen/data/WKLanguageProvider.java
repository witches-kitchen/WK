package cf.witcheskitchen.data;

import cf.witcheskitchen.common.event.WKItemGroupEvents;
import cf.witcheskitchen.common.registry.WKBlocks;
import cf.witcheskitchen.common.registry.WKEntityTypes;
import cf.witcheskitchen.common.registry.WKItems;
import cf.witcheskitchen.common.registry.WKStatusEffects;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class WKLanguageProvider extends FabricLanguageProvider {
    protected WKLanguageProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateTranslations(TranslationBuilder builder) {
        //GROUPS
        builder.add(WKItemGroupEvents.GENERAL_TAB, "Witches' Kitchen General");
        builder.add(WKItemGroupEvents.FOOD_TAB, "Witches' Kitchen Food");

        //Tooltip
        builder.add("tooltip.witcheskitchen.glyph", "Glyph");
        builder.add("tooltip.witcheskitchen.enchanted_glyph", "Enchanted Glyph");
        builder.add("tooltip.witcheskitchen.bundle.potency", "Potency");
        builder.add("tooltip.witcheskitchen.blockpos", "Position: ");
        builder.add("tooltip.witcheskitchen.dimension", "Dimension: ");

        //BLOCKS
        builder.add(WKBlocks.GLYPH, "Glyph");
        builder.add(WKBlocks.ENCHANTED_GLYPH, "Glyph");

        builder.add(WKBlocks.SALT_BLOCK, "Salt");

        builder.add(WKBlocks.BLACKTHORN_LOG, "Blackthorn Log");
        builder.add(WKBlocks.BLACKTHORN_SAPLING, "Blackthorn Sapling");
        builder.add(WKBlocks.ELDER_SAPLING, "Elder Sapling");
        builder.add(WKBlocks.HAWTHORN_SAPLING, "Hawthorn Sapling");
        builder.add(WKBlocks.JUNIPER_SAPLING, "Juniper Sapling");
        builder.add(WKBlocks.ROWAN_SAPLING, "Rowan Sapling");
        builder.add(WKBlocks.SUMAC_SAPLING, "Sumac Sapling");
        builder.add(WKBlocks.POTTED_BLACKTHORN_SAPLING, "Potted Blackthorn Sapling");
        builder.add(WKBlocks.POTTED_ELDER_SAPLING, "Potted Elder Sapling");
        builder.add(WKBlocks.POTTED_HAWTHORN_SAPLING, "Potted Hawthorn Sapling");
        builder.add(WKBlocks.POTTED_JUNIPER_SAPLING, "Potted Juniper Sapling");

        builder.add(WKBlocks.POTTED_ROWAN_SAPLING, "Potted Rowan Sapling");
        builder.add(WKBlocks.POTTED_SUMAC_SAPLING, "Potted Sumac Sapling");
        builder.add(WKBlocks.BLACKTHORN_PLANKS, "Blackthorn Planks");
        builder.add(WKBlocks.ELDER_LOG, "Elder Log");
        builder.add(WKBlocks.ELDER_PLANKS, "Elder Planks");
        builder.add(WKBlocks.BLACKTHORN_STAIRS, "Blackthorn Stairs");
        builder.add(WKBlocks.ELDER_STAIRS, "Elder Stairs");
        builder.add(WKBlocks.BLACKTHORN_SLAB, "Blackthorn Slab");
        builder.add(WKBlocks.ELDER_SLAB, "Elder Slab");
        builder.add(WKBlocks.BLACKTHORN_LEAVES, "Blackthorn Leaves");
        builder.add(WKBlocks.ELDER_LEAVES, "Elder Leaves");

        builder.add(WKBlocks.HAWTHORN_LOG, "Hawthorn Log");
        builder.add(WKBlocks.JUNIPER_LOG, "Juniper Log");
        builder.add(WKBlocks.HAWTHORN_PLANKS, "Hawthorn Planks");
        builder.add(WKBlocks.JUNIPER_PLANKS, "Juniper Planks");
        builder.add(WKBlocks.HAWTHORN_STAIRS, "Hawthorn Stairs");
        builder.add(WKBlocks.JUNIPER_STAIRS, "Juniper Stairs");
        builder.add(WKBlocks.HAWTHORN_SLAB, "Hawthorn Slab");
        builder.add(WKBlocks.JUNIPER_SLAB, "Juniper Slab");
        builder.add(WKBlocks.HAWTHORN_LEAVES, "Hawthorn Leaves");

        builder.add(WKBlocks.JUNIPER_LEAVES, "Juniper Leaves");
        builder.add(WKBlocks.ROWAN_LOG, "Rowan Log");
        builder.add(WKBlocks.SUMAC_LOG, "Sumac Log");
        builder.add(WKBlocks.ROWAN_PLANKS, "Rowan Planks");
        builder.add(WKBlocks.SUMAC_PLANKS, "Sumac Planks");
        builder.add(WKBlocks.ROWAN_STAIRS, "Rowan Stairs");
        builder.add(WKBlocks.SUMAC_STAIRS, "Sumac Stairs");
        builder.add(WKBlocks.ROWAN_SLAB, "Rowan Slab");
        builder.add(WKBlocks.SUMAC_SLAB, "Sumac Slab");
        builder.add(WKBlocks.ROWAN_LEAVES, "Rowan Leaves");
        builder.add(WKBlocks.SUMAC_LEAVES, "Sumac Leaves");

        builder.add(WKBlocks.BLACKTHORN_PRESSURE_PLATE, "Blackthorn Pressure Plate");
        builder.add(WKBlocks.ELDER_PRESSURE_PLATE, "Elder Pressure Plate");
        builder.add(WKBlocks.HAWTHORN_PRESSURE_PLATE, "Hawthorn Pressure Plate");
        builder.add(WKBlocks.JUNIPER_PRESSURE_PLATE, "Juniper Pressure Plate");
        builder.add(WKBlocks.ROWAN_PRESSURE_PLATE, "Rowan Pressure Plate");
        builder.add(WKBlocks.SUMAC_PRESSURE_PLATE, "Sumac Pressure Plate");

        builder.add(WKBlocks.BLACKTHORN_FENCE, "Blackthorn Fence");
        builder.add(WKBlocks.ELDER_FENCE, "Elder Fence");
        builder.add(WKBlocks.HAWTHORN_FENCE, "Hawthorn Fence");
        builder.add(WKBlocks.JUNIPER_FENCE, "Juniper Fence");
        builder.add(WKBlocks.ROWAN_FENCE, "Rowan Fence");
        builder.add(WKBlocks.SUMAC_FENCE, "Sumac Fence");

        builder.add(WKBlocks.BLACKTHORN_DOOR, "Blackthorn Door");
        builder.add(WKBlocks.ELDER_DOOR, "Elder Door");
        builder.add(WKBlocks.HAWTHORN_DOOR, "Hawthorn Door");
        builder.add(WKBlocks.JUNIPER_DOOR, "Juniper Door");
        builder.add(WKBlocks.ROWAN_DOOR, "Rowan Door");
        builder.add(WKBlocks.SUMAC_DOOR, "Sumac Door");

        builder.add(WKBlocks.BLACKTHORN_FENCE_GATE, "Blackthorn Fence Gate");
        builder.add(WKBlocks.ELDER_FENCE_GATE, "Elder Fence Gate");
        builder.add(WKBlocks.HAWTHORN_FENCE_GATE, "Hawthorn Fence Gate");
        builder.add(WKBlocks.JUNIPER_FENCE_GATE, "Juniper Fence Gate");
        builder.add(WKBlocks.ROWAN_FENCE_GATE, "Rowan Fence Gate");
        builder.add(WKBlocks.SUMAC_FENCE_GATE, "Sumac Fence Gate");

        builder.add(WKBlocks.BLACKTHORN_BUTTON, "Blackthorn Button");
        builder.add(WKBlocks.ELDER_BUTTON, "Elder Button");
        builder.add(WKBlocks.HAWTHORN_BUTTON, "Hawthorn Button");
        builder.add(WKBlocks.JUNIPER_BUTTON, "Juniper Button");
        builder.add(WKBlocks.ROWAN_BUTTON, "Rowan Button");
        builder.add(WKBlocks.SUMAC_BUTTON, "Sumac Button");

        builder.add(WKBlocks.BLACKTHORN_WOOD, "Blackthorn Wood");
        builder.add(WKBlocks.ELDER_WOOD, "Elder Wood");
        builder.add(WKBlocks.HAWTHORN_WOOD, "Hawthorn Wood");
        builder.add(WKBlocks.JUNIPER_WOOD, "Juniper Wood");
        builder.add(WKBlocks.ROWAN_WOOD, "Rowan Wood");
        builder.add(WKBlocks.SUMAC_WOOD, "Sumac Wood");
        builder.add(WKBlocks.STRIPPED_BLACKTHORN_LOG, "Stripped Blackthorn Log");
        builder.add(WKBlocks.STRIPPED_ELDER_LOG, "Stripped Elder Log");
        builder.add(WKBlocks.STRIPPED_HAWTHORN_LOG, "Stripped Hawthorn Log");
        builder.add(WKBlocks.STRIPPED_JUNIPER_LOG, "Stripped Juniper Log");
        builder.add(WKBlocks.STRIPPED_ROWAN_LOG, "Stripped Rowan Log");

        builder.add(WKBlocks.STRIPPED_SUMAC_LOG, "Stripped Sumac Log");
        builder.add(WKBlocks.STRIPPED_BLACKTHORN_WOOD, "Stripped Blackthorn Wood");
        builder.add(WKBlocks.STRIPPED_ELDER_WOOD, "Stripped Elder Wood");
        builder.add(WKBlocks.STRIPPED_HAWTHORN_WOOD, "Stripped Hawthorn Wood");
        builder.add(WKBlocks.STRIPPED_JUNIPER_WOOD, "Stripped Juniper Wood");
        builder.add(WKBlocks.STRIPPED_ROWAN_WOOD, "Stripped Rowan Wood");
        builder.add(WKBlocks.STRIPPED_SUMAC_WOOD, "Stripped Sumac Wood");
        builder.add(WKBlocks.IRON_WITCHES_OVEN, "Iron Witches' Oven");
        builder.add(WKBlocks.COPPER_WITCHES_OVEN, "Copper Witches' Oven");
        builder.add(WKBlocks.EXPOSED_COPPER_WITCHES_OVEN, "Exposed Copper Witches' Oven");

        builder.add(WKBlocks.WEATHERED_COPPER_WITCHES_OVEN, "Weathered Copper Witches' Oven");
        builder.add(WKBlocks.OXIDIZED_COPPER_WITCHES_OVEN, "Oxidized Copper Witches' Oven");
        builder.add(WKBlocks.WAXED_COPPER_WITCHES_OVEN, "Waxed Copper Witches' Oven");
        builder.add(WKBlocks.WAXED_EXPOSED_COPPER_WITCHES_OVEN, "Waxed Exposed Copper Witches' Oven");
        builder.add(WKBlocks.WAXED_WEATHERED_COPPER_WITCHES_OVEN, "Waxed Weathered Copper Witches' Oven");
        builder.add(WKBlocks.WAXED_OXIDIZED_COPPER_WITCHES_OVEN, "Waxed Oxidized Copper Witches' Oven");
        builder.add(WKBlocks.IRON_WITCHES_CAULDRON, "Iron Witches' Cauldron");

        builder.add(WKBlocks.TEAPOT, "Teapot");
        builder.add(WKBlocks.WEATHERED_COPPER_TEAPOT, "Weathered Copper Teapot");
        builder.add(WKBlocks.OXIDIZED_COPPER_TEAPOT, "Oxidized Copper Teapot");
        builder.add(WKBlocks.WAXED_COPPER_TEAPOT, "Waxed Copper Teapot");
        builder.add(WKBlocks.COPPER_TEAPOT, "Copper Teapot");
        builder.add(WKBlocks.WAXED_EXPOSED_COPPER_TEAPOT, "Waxed Exposed Copper Teapot");
        builder.add(WKBlocks.EXPOSED_COPPER_TEAPOT, "Exposed Copper Teapot");
        builder.add(WKBlocks.WAXED_WEATHERED_COPPER_TEAPOT, "Waxed Weathered Copper Teapot");
        builder.add(WKBlocks.WAXED_OXIDIZED_COPPER_TEAPOT, "Waxed Oxidized Copper Teapot");
        builder.add(WKBlocks.CAST_IRON_TEAPOT, "Cast Iron Teapot");


        builder.add(WKBlocks.OAK_BREWING_BARREL, "Oak Brewing Barrel");
        builder.add(WKBlocks.SPRUCE_BREWING_BARREL, "Spruce Brewing Barrel");
        builder.add(WKBlocks.BIRCH_BREWING_BARREL, "Birch Brewing Barrel");

        builder.add(WKBlocks.JUNGLE_BREWING_BARREL, "Jungle Brewing Barrel");
        builder.add(WKBlocks.ACACIA_BREWING_BARREL, "Acacia Brewing Barrel");
        builder.add(WKBlocks.DARK_OAK_BREWING_BARREL, "Dark Oak Brewing Barrel");
        builder.add(WKBlocks.CRIMSON_BREWING_BARREL, "Crimson Brewing Barrel");
        builder.add(WKBlocks.WARPED_BREWING_BARREL, "Warped Brewing Barrel");
        builder.add(WKBlocks.BELLADONNA, "Belladonna");
        builder.add(WKBlocks.BELLADONNA_GLOW, "Belladonna Glow");
        builder.add(WKBlocks.BELLADONNA_NOCTURNAL, "Belladonna Nocturnal");

        builder.add(WKBlocks.AMARANTH_PLANT, "Amaranth");
        builder.add(WKBlocks.BELLADONNA_PLANT, "Belladonna");
        builder.add(WKBlocks.CHAMOMILE_PLANT, "Chamomile");
        builder.add(WKBlocks.CONEFLOWER_PLANT, "Coneflower");
        builder.add(WKBlocks.FOXGLOVE_PLANT, "Foxglove");
        builder.add(WKBlocks.HELLEBORE_PLANT, "Hellebore");
        builder.add(WKBlocks.IRIS_PLANT, "Iris");
        builder.add(WKBlocks.SANGUINARY_PLANT, "Sanguinary");

        //ENTITY TYPES
        builder.add(WKEntityTypes.CUSITH, "Cu-Sith");
        builder.add(WKEntityTypes.FERRET, "Ferret");

        //ITEMS
        builder.add(WKItems.BELLADONNA_BLOSSOM, "Belladonna Blossom");
        builder.add(WKItems.AMARANTH_SPRIG, "Amaranth Sprig");
        builder.add(WKItems.MINT_SPRIG, "Mint Sprig");
        builder.add(WKItems.WORMWOOD_SPRIG, "Wormwood Sprig");
        builder.add(WKItems.ROWAN_BERRIES, "Rowan Berries");
        builder.add(WKItems.SLOE_BERRIES, "Sloe Berries");
        builder.add(WKItems.JUNIPER_BERRIES, "Juniper Berries");
        builder.add(WKItems.BLACKBERRY, "Blackberry");
        builder.add(WKItems.HAWTHORN_BERRIES, "Hawthorn Berries");
        builder.add(WKItems.SUMAC_BERRIES, "Sumac Berries");
        builder.add(WKItems.BRIAR_HIPS, "Briar Hips");
        builder.add(WKItems.ELDER_BLOSSOM, "Elder Blossom");
        builder.add(WKItems.CONEFLOWER_BLOSSOM, "Coneflower Blossom");
        builder.add(WKItems.ST_JOHNS_WORT_BLOSSOM, "St Johns Wort Blossom");
        builder.add(WKItems.IRIS_BLOSSOM, "Iris Blossom");
        builder.add(WKItems.CHAMOMILE_BLOSSOM, "Chamomile Blossom");
        builder.add(WKItems.GINGER_ROOTS, "Ginger Roots");
        builder.add(WKItems.TEA_LEAF, "Tea Leaf");
        builder.add(WKItems.HELLEBORE_BLOSSOM, "Hellebore Blossom");
        builder.add(WKItems.FOXGLOVE_BLOSSOM, "Foxglove Blossom");
        builder.add(WKItems.SANGUINARY_BLOSSOM, "Sanguinary Blossom");
        builder.add(WKItems.HEART_OF_INNOCENCE, "Heart of Innocence");

        builder.add(WKItems.BLACKBERRY_TEA, "Blackberry Tea");
        builder.add(WKItems.CHAMOMILE_TEA, "Chamomile Tea");
        builder.add(WKItems.DOGROSE_TEA, "Dogrose Tea");
        builder.add(WKItems.ECHINACEA_TEA, "Echinacea Tea");
        builder.add(WKItems.ELDER_TEA, "Elder Tea");
        builder.add(WKItems.GINGER_TEA, "Ginger Tea");
        builder.add(WKItems.HAWTHORN_TEA, "Hawthorn Tea");
        builder.add(WKItems.MINT_TEA, "Mint Tea");
        builder.add(WKItems.ST_JOHNS_WORT_TEA, "St Johns Wort Tea");
        builder.add(WKItems.SUMAC_TEA, "Sumac Tea");
        builder.add(WKItems.YARROW_TEA, "Yarrow Tea");

        builder.add(WKItems.DOLLOP_OF_FROSTING, "Dollop of Frosting");
        builder.add(WKItems.CU_SITH_SPAWN_EGG, "Cu-Sith Spawn Egg");
        builder.add(WKItems.FERRET_SPAWN_EGG, "Ferret Spawn Egg");
        builder.add(WKItems.CHURCH_GRIM_SPAWN_EGG, "Church Grim Spawn Egg");
        builder.add(WKItems.HEDGEHOG_SPAWN_EGG, "Hedgehog Spawn Egg");
        builder.add(WKItems.ROGGENWOLF_SPAWN_EGG, "Roggenwolf Spawn Egg");
        builder.add(WKItems.CHOCOLATE_RUM_BALLS, "Chocolate Rum Balls");
        builder.add(WKItems.SUPER_BOOZE, "Super Booze");

        builder.add(WKItems.BONE_NEEDLE, "Bone Needle");
        builder.add(WKItems.CHALK, "Chalk");
        builder.add(WKItems.ENCHANTED_CHALK, "Enchanted Chalk");
        builder.add(WKItems.TAGLOCK, "Taglock");

        builder.add(WKItems.CALEFACTION_BUNDLE, "Calefaction Bundle");
        builder.add(WKItems.FEAR_BUNDLE, "Fear Bundle");
        builder.add(WKItems.CURSE_OF_MIDAS_BUNDLE, "Curse of Midas Bundle");
        builder.add(WKItems.FIELD_GEISTER_HEX_BUNDLE, "Field Geister Hex Bundle");
        builder.add(WKItems.HUNGRY_POCKETS_BUNDLE, "Hungry Pockets Bundle");
        builder.add(WKItems.INEPTITUDE_BUNDLE, "Ineptitude Bundle");
        builder.add(WKItems.MISPLACEMENT_BUNDLE, "Misplacement Bundle");
        builder.add(WKItems.NULLARDOR_BUNDLE, "Nullador Bundle");
        builder.add(WKItems.PARANOIA_BUNDLE, "Paranoia Bundle");
        builder.add(WKItems.PERUNS_JEST_BUNDLE, "Peruns Jest Bundle");

        builder.add(WKItems.ABSINTHE, "Absinthe");
        builder.add(WKItems.BLACKBERRY_LIQUEUR, "Blackberry Liqueur");
        builder.add(WKItems.BRINJEVEC, "Brinjevec");
        builder.add(WKItems.HOLUNDERSEKT, "Holdundersekt");
        builder.add(WKItems.RUM, "Rum");
        builder.add(WKItems.TRAVARICA, "Travarica");
        builder.add(WKItems.JUNIPER_MEAD, "Juniper Mead");

        builder.add(WKItems.GROUND_PORK, "Ground Pork");
        builder.add(WKItems.GROUND_MUTTON, "Ground Mutton");
        builder.add(WKItems.GROUND_BEEF, "Ground Beef");
        builder.add(WKItems.DEMONIC_STEW, "Demonic Stew");
        builder.add(WKItems.MEATY_STEW, "Meaty Stew");
        builder.add(WKItems.VEGETABLE_STEW, "Vegetable Stew");
        builder.add(WKItems.HEART_PIE, "Heart Pie");
        builder.add(WKItems.ROOTS_PLATTER, "Roots Platter");

        //SEEDS
        builder.add(WKItems.BELLADONNA_SEEDS, "Belladonna Seeds");
        builder.add(WKItems.AMARANTH_SEEDS, "Amaranth Seeds");
        builder.add(WKItems.ST_JOHNS_WORT_SEEDS, "St Johns Wort Seeds");
        builder.add(WKItems.BRIAR_SEEDS, "Briar Seeds");
        builder.add(WKItems.CAMELLIA_SEEDS, "Camellia Seeds");
        builder.add(WKItems.CHAMOMILE_SEEDS, "Chamomile Seeds");
        builder.add(WKItems.CONEFLOWER_SEEDS, "Coneflower Seeds");
        builder.add(WKItems.FOXGLOVE_SEEDS, "Foxglove Seeds");
        builder.add(WKItems.HELLEBORE_SEEDS, "Hellebore Seeds");
        builder.add(WKItems.IRIS_SEEDS, "Iris Seeds");
        builder.add(WKItems.SANGUINARY_SEEDS, "Sanguinary Seeds");
        builder.add(WKItems.WORMWOOD_SEEDS, "Wormwood Seeds");

        builder.add(WKItems.WAYSTONE, "Waystone");

        //POTIONS
        builder.add("item.minecraft.potion.effect.rum", "Rum Potion");
        builder.add("item.minecraft.splash_potion.effect.rum", "Rum Splash Potion");
        builder.add("item.minecraft.lingering_potion.effect.rum", "Rum Lingering Potion");

        //STATUS EFFECTS
        builder.add(WKStatusEffects.DRUNK, "Drunk");

        //DEATH MESSAGE
        builder.add("death.attack.on_oven", "%s cooked themselves to a crisp... they do not taste great");
        builder.add("death.attack.hugging_blackthorn", "%s forgot to use protection while hugging a tree");
        builder.add("death.attack.punching_blackthorn", "%s lost a fight with a blackthorn");

        //SCREEN
        builder.add("screen.title.witcheskitchen.witches_oven", "Witches' Oven");
        builder.add("screen.title.witcheskitchen.brewing_barrel", "Brewing Barrel");

        //REI
        builder.add("rei.witcheskitchen.oven_cooking", "Witches' Oven Cooking");
        builder.add("rei.witcheskitchen.fermenting", "Fermenting");
    }
}
