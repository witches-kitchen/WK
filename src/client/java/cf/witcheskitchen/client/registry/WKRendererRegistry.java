package cf.witcheskitchen.client.registry;

import cf.witcheskitchen.client.render.*;
import cf.witcheskitchen.client.render.blockentity.BrewingBarrelBlockEntityRender;
import cf.witcheskitchen.client.render.blockentity.WitchesCauldronBlockEntityRender;
import cf.witcheskitchen.client.render.blockentity.WitchesOvenBlockEntityRender;
import cf.witcheskitchen.common.registry.WKBlockEntityTypes;
import cf.witcheskitchen.common.registry.WKEntityTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

@Environment(EnvType.CLIENT)
public interface WKRendererRegistry {
    static void init() {
        BlockEntityRenderers.register(WKBlockEntityTypes.WITCHES_OVEN, (ctx) -> new WitchesOvenBlockEntityRender());
        BlockEntityRenderers.register(WKBlockEntityTypes.WITCHES_CAULDRON, (ctx) -> new WitchesCauldronBlockEntityRender());
        BlockEntityRenderers.register(WKBlockEntityTypes.BREWING_BARREL, (ctx) -> new BrewingBarrelBlockEntityRender());

        EntityRendererRegistry.register(WKEntityTypes.CUSITH, CuSithRender::new);
        EntityRendererRegistry.register(WKEntityTypes.ROGGENWOLF, RoggenwolfRender::new);
        EntityRendererRegistry.register(WKEntityTypes.FERRET, FerretRender::new);
        EntityRendererRegistry.register(WKEntityTypes.CHURCH_GRIM, ChurchGrimRender::new);
        EntityRendererRegistry.register(WKEntityTypes.HEDGEHOG, HedgehogRender::new);
    }
}
