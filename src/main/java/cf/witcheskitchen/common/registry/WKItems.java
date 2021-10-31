package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WKItems {

    static Item BELLADONNA_SEEDS = new Item(new FabricItemSettings().group(WK.WK_GROUP));
    static Item ST_JOHNS_WORT_SEEDS = new Item(new FabricItemSettings().group(WK.WK_GROUP));
    static Item AMARANTH_SEEDS = new Item(new FabricItemSettings().group(WK.WK_GROUP));
    static Item BRIAR_SEEDS = new Item(new FabricItemSettings().group(WK.WK_GROUP));
    static Item CAMELLIA_SEEDS = new Item(new FabricItemSettings().group(WK.WK_GROUP));
    static Item CHAMOMILE_SEEDS = new Item(new FabricItemSettings().group(WK.WK_GROUP));
    static Item CONEFLOWER_SEEDS = new Item(new FabricItemSettings().group(WK.WK_GROUP));
    static Item FOXGLOVE_SEEDS = new Item(new FabricItemSettings().group(WK.WK_GROUP));
    static Item HELLEBORE_SEEDS = new Item(new FabricItemSettings().group(WK.WK_GROUP));
    static Item IRIS_SEEDS = new Item(new FabricItemSettings().group(WK.WK_GROUP));
    static Item SANGUINARY_SEEDS = new Item(new FabricItemSettings().group(WK.WK_GROUP));
    static Item WORMWOOD_SEEDS = new Item(new FabricItemSettings().group(WK.WK_GROUP));

    static Item DOLLOP_OF_FROSTING = new Item(new FabricItemSettings().food(WKFoodComponents.FROSTING).group(WK.WK_GROUP));
    static Item CHOCOLATE_RUM_BALLS = new Item(new FabricItemSettings().food(WKFoodComponents.RUM_BALLS).group(WK.WK_GROUP));
    static Item SUPER_BOOZE = new Item(new FabricItemSettings().food(WKFoodComponents.SUPER_BOOZE).group(WK.WK_GROUP));

    public static void register() {
        registerItem("belladonna_seeds", BELLADONNA_SEEDS);
        registerItem("dollop_of_frosting", DOLLOP_OF_FROSTING);
        registerItem("chocolate_rum_balls", CHOCOLATE_RUM_BALLS);
        registerItem("super_booze", SUPER_BOOZE);
    }

    public static void registerItem(String id, Item item) {
        Registry.register(Registry.ITEM, new Identifier(WK.MODID, id), item);
    }
}

