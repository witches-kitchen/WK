package cf.witcheskitchen.client;

import cf.witcheskitchen.client.gui.screen.WitchesOvenScreen;
import cf.witcheskitchen.common.registry.RenderRegistry;
import cf.witcheskitchen.common.registry.WKBlocks;
import cf.witcheskitchen.common.registry.WKScreenHandlerTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class WKClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(WKBlocks.IRON_WITCHES_OVEN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(WKBlocks.SALT_BLOCK, RenderLayer.getCutoutMipped());
        RenderRegistry.register();
        ScreenRegistry.register(WKScreenHandlerTypes.WITCHES_OVEN, WitchesOvenScreen::new);
    }
}
