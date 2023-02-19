package cf.witcheskitchen.client.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.WitchesKitchenConfig;
import cf.witcheskitchen.client.render.ChurchGrimRender;
import cf.witcheskitchen.client.render.CuSithRender;
import cf.witcheskitchen.client.render.FerretRender;
import cf.witcheskitchen.common.registry.WKEntityTypes;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class RenderRegistry {
    public static void register() {
        EntityRendererRegistry.register(WKEntityTypes.CUSITH, CuSithRender::new);
        EntityRendererRegistry.register(WKEntityTypes.FERRET, FerretRender::new);
        EntityRendererRegistry.register(WKEntityTypes.CHURCH_GRIM, ChurchGrimRender::new);
    }
}
