package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.common.items.WKSeedItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WKItems {

    // Flowers
    public static final Item BELLADONNA_BLOSSOM = new Item(new FabricItemSettings().group(WK.WK_GROUP));

    public static final Item BELLADONNA_SEEDS = new WKSeedItem(WKBlocks.BELLADONNA, new FabricItemSettings().group(WK.WK_GROUP));
    public static Item ST_JOHNS_WORT_SEEDS = new Item(new FabricItemSettings().group(WK.WK_GROUP));
    public static Item AMARANTH_SEEDS = new Item(new FabricItemSettings().group(WK.WK_GROUP));
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
    public static Item CU_SITH_SPAWN_EGG = new SpawnEggItem(WKEntities.CUSITH, 3421236, 3497531, new Item.Settings().group(WK.WK_GROUP));
    public static Item FERRET_SPAWN_EGG = new SpawnEggItem(WKEntities.FERRET, 9985082, 2631205, new Item.Settings().group(WK.WK_GROUP));

    public static void register() {
        registerItem("belladonna_blossom", BELLADONNA_BLOSSOM);
        registerItem("belladonna_seeds", BELLADONNA_SEEDS);
        registerItem("dollop_of_frosting", DOLLOP_OF_FROSTING);
        registerItem("chocolate_rum_balls", CHOCOLATE_RUM_BALLS);
        registerItem("super_booze", SUPER_BOOZE);
        registerItem("cu_sith_spawn_egg", CU_SITH_SPAWN_EGG);
        registerItem("ferret_spawn_egg", FERRET_SPAWN_EGG);
    }

    public static void registerItem(String id, Item item) {
        Registry.register(Registry.ITEM, new Identifier(WK.MODID, id), item);
    }
}

