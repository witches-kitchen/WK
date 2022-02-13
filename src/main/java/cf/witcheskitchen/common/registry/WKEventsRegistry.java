package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.WKConfig;
import cf.witcheskitchen.api.event.network.MagicSparkleParticleEvent;
import cf.witcheskitchen.client.render.blockentity.WitchesCauldronBlockEntityRender;
import cf.witcheskitchen.common.WKEventsHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;

public class WKEventsRegistry {

    public static void register(EnvType type) {
        switch (type) {
            case SERVER -> {
                LootTableLoadingCallback.EVENT.register(new WKEventsHandler.LootTablesListener());
                MagicSparkleParticleEvent.PARTICLE_CONSTRUCTOR_EVENT.register(new WitchesCauldronBlockEntityRender.MagicalParticleEventHandler());
            }
            case CLIENT -> {
            }
        }
    }

    // used for client events
    public static void registerClient() {
        if (WKConfig.get().debugMode) {
            WK.logger.info("Witches Kitchen Base Client Registry: Successfully Loaded");
        }
    }

}
