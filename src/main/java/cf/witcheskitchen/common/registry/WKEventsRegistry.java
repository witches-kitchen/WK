package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.WitchesKitchenConfig;
import cf.witcheskitchen.api.event.network.MagicSparkleParticleEvent;
import cf.witcheskitchen.client.render.blockentity.WitchesCauldronBlockEntityRender;
import cf.witcheskitchen.common.WKEventsHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;

public class WKEventsRegistry {

    public static void register(EnvType type) {
        switch (type) {
            case SERVER -> {
                LootTableEvents.MODIFY.register(new WKEventsHandler.LootTablesListener());
            }
            case CLIENT -> {
                MagicSparkleParticleEvent.PARTICLE_CONSTRUCTOR_EVENT.register(new WitchesCauldronBlockEntityRender.MagicalParticleEventHandler());
            }
        }
    }

    // used for client events
    public static void registerClient() {
        if (WitchesKitchenConfig.debugMode) {
            WitchesKitchen.LOGGER.info("Witches Kitchen Base Client Registry: Successfully Loaded");
        }
    }

}
