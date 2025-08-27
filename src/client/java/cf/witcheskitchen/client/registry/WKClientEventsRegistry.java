package cf.witcheskitchen.client.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.api.event.network.MagicSparkleParticleEvent;
import cf.witcheskitchen.client.event.WKClientEventsHandler;
import cf.witcheskitchen.client.render.blockentity.WitchesCauldronBlockEntityRender;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;

public interface WKClientEventsRegistry {
    static void init() {
        MagicSparkleParticleEvent.PARTICLE_CONSTRUCTOR_EVENT.register(new WitchesCauldronBlockEntityRender.MagicalParticleEventHandler());

        HudElementRegistry.attachElementAfter(VanillaHudElements.CHAT, WitchesKitchen.id("magic_hud"), new WKClientEventsHandler.MagicHudRender());
    }
}
