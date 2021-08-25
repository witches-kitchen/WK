package cf.witcheskitchen.registry;

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
    public static void register()
    {
        registerItem("belladonna_seeds", BELLADONNA_SEEDS);
    }
    public static void registerItem(String id, Item item)
    {
        Registry.register(Registry.ITEM, new Identifier(WK.MODID, id), item);
    }
}

