package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.common.WKEventsHandler;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;

public class WKEventsRegistry {

    public static void register() {
        LootTableLoadingCallback.EVENT.register(new WKEventsHandler.LootTablesListener());
    }

}
