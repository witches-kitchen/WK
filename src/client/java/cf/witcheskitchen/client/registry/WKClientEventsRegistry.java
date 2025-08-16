package cf.witcheskitchen.client.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.api.event.network.MagicSparkleParticleEvent;
import cf.witcheskitchen.client.event.WKClientEventsHandler;
import cf.witcheskitchen.client.render.blockentity.WitchesCauldronBlockEntityRender;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.client.MinecraftClient;

public interface WKClientEventsRegistry {
    static void init() {
        MagicSparkleParticleEvent.PARTICLE_CONSTRUCTOR_EVENT.register(new WitchesCauldronBlockEntityRender.MagicalParticleEventHandler());

        HudLayerRegistrationCallback.EVENT.register(layeredDrawer -> {
           layeredDrawer.attachLayerBefore(IdentifiedLayer.CHAT, IdentifiedLayer.of(WitchesKitchen.id("magic_hud"), new WKClientEventsHandler.MagicHudRender()));
        });
    }
}
