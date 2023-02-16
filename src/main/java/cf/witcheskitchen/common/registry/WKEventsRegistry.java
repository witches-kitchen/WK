package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.WKConfig;
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
                MagicSparkleParticleEvent.PARTICLE_CONSTRUCTOR_EVENT.register(new WitchesCauldronBlockEntityRender.MagicalParticleEventHandler());
            }
            case CLIENT -> {
            }
        }
    }

    // used for client events
    public static void registerClient() {
        if (WKConfig.debugMode) {
            WK.LOGGER.info("Witches Kitchen Base Client Registry: Successfully Loaded");
        }
    }

}
