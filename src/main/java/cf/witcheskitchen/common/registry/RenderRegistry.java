package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.client.render.CuSithRender;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class RenderRegistry {
    public static void register() {
        EntityRendererRegistry.register(WKEntities.CUSITH, (ctx) -> new CuSithRender(ctx));
    }
}
