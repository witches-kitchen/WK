package cf.witcheskitchen.client.registry;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.WKConfig;
import cf.witcheskitchen.client.render.ChurchGrimRender;
import cf.witcheskitchen.client.render.CuSithRender;
import cf.witcheskitchen.client.render.FerretRender;
import cf.witcheskitchen.common.registry.WKEntityTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class RenderRegistry {
    public static void register() {
        EntityRendererRegistry.register(WKEntityTypes.CUSITH, CuSithRender::new);
        EntityRendererRegistry.register(WKEntityTypes.FERRET, FerretRender::new);
        EntityRendererRegistry.register(WKEntityTypes.CHURCH_GRIM, ChurchGrimRender::new);

        if (WKConfig.get().debugMode) {
            WK.logger.info("Witches Kitchen Base Renderer: Successfully Loaded");
        }
    }
}
