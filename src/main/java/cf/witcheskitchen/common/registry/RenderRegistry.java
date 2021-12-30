package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.client.render.CuSithRender;
import cf.witcheskitchen.client.render.FerretRender;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class RenderRegistry {
    public static void register() {
        EntityRendererRegistry.register(WKEntities.CUSITH, CuSithRender::new);
        EntityRendererRegistry.register(WKEntities.FERRET, FerretRender::new);
    }
}
