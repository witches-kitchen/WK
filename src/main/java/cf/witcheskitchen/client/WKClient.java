package cf.witcheskitchen.client;

import cf.witcheskitchen.common.registry.RenderRegistry;
import cf.witcheskitchen.common.registry.WKBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class WKClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(WKBlocks.SALT_BLOCK, RenderLayer.getCutoutMipped());
        RenderRegistry.register();
    }
}
