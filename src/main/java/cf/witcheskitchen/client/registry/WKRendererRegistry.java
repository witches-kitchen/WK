package cf.witcheskitchen.client.registry;

import cf.witcheskitchen.client.render.*;
import cf.witcheskitchen.client.render.blockentity.BrewingBarrelBlockEntityRender;
import cf.witcheskitchen.client.render.blockentity.WitchesCauldronBlockEntityRender;
import cf.witcheskitchen.client.render.blockentity.WitchesOvenBlockEntityRender;
import cf.witcheskitchen.common.registry.WKBlockEntityTypes;
import cf.witcheskitchen.common.registry.WKEntityTypes;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public interface WKRendererRegistry {
    static void init() {
        BlockEntityRendererFactories.register(WKBlockEntityTypes.WITCHES_OVEN, (ctx) -> new WitchesOvenBlockEntityRender());
        BlockEntityRendererFactories.register(WKBlockEntityTypes.WITCHES_CAULDRON, (ctx) -> new WitchesCauldronBlockEntityRender());
        BlockEntityRendererFactories.register(WKBlockEntityTypes.BREWING_BARREL, (ctx) -> new BrewingBarrelBlockEntityRender());

        EntityRendererRegistry.register(WKEntityTypes.CUSITH, CuSithRender::new);
        EntityRendererRegistry.register(WKEntityTypes.ROGGENWOLF, RoggenwolfRender::new);
        EntityRendererRegistry.register(WKEntityTypes.FERRET, FerretRender::new);
        EntityRendererRegistry.register(WKEntityTypes.CHURCH_GRIM, ChurchGrimRender::new);
        EntityRendererRegistry.register(WKEntityTypes.HEDGEHOG, HedgehogRender::new);
    }
}
