package cf.witcheskitchen.client;

import cf.witcheskitchen.client.render.CuSithRender;
import cf.witcheskitchen.common.registry.WKBlocks;
import cf.witcheskitchen.common.registry.WKEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

public class WKClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(WKBlocks.SALT_BLOCK, RenderLayer.getCutoutMipped());
        EntityRendererRegistry.register(WKEntities.CUSITH, (context) ->
        {
            return new CuSithRender(context);
        });
    }
}
