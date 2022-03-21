package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.WKConfig;
import cf.witcheskitchen.client.render.ChurchGrimRender;
import cf.witcheskitchen.client.render.CuSithRender;
import cf.witcheskitchen.client.render.FerretRender;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class RenderRegistry {
    public static void register() {
        EntityRendererRegistry.register(WKEntities.CUSITH, CuSithRender::new);
        EntityRendererRegistry.register(WKEntities.FERRET, FerretRender::new);
        EntityRendererRegistry.register(WKEntities.CHURCH_GRIM, ChurchGrimRender::new);

        if (WKConfig.get().debugMode) {
            WK.logger.info("Witches Kitchen Base Renderer: Successfully Loaded");
        }
    }
}
