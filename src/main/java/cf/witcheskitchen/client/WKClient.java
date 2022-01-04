package cf.witcheskitchen.client;

import cf.witcheskitchen.common.blocks.WKLeafCropBlock;
import cf.witcheskitchen.common.registry.RenderRegistry;
import cf.witcheskitchen.common.registry.WKBlocks;
import cf.witcheskitchen.common.registry.WKScreenHandlerTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
//import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry; //unsure if this will be needed at some point
import net.minecraft.client.render.RenderLayer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

import static cf.witcheskitchen.WK.*;

@Environment(EnvType.CLIENT)
public class WKClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        modBlocks.forEach(this::registerModBlockLayer);
        ScreenRegistry.register(WKScreenHandlerTypes.WITCHES_OVEN, WitchesOvenScreen::new);
        registerColorProvider();
        RenderRegistry.register();
    }
   
    //modified from croptopia
    public void registerModBlockLayer(Block block) {
        if (block instanceof WKLeafCropBlock) {
            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutoutMipped());
            return;
        } 
        BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutoutMipped());
    }

    //allows for color map modification of leaves based on biome
    public void registerColorProvider() {

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) ->
                world != null && pos != null
                        ? BiomeColors.getFoliageColor(world, pos)
                        : FoliageColors.getDefaultColor(), leafBlocks.toArray(new Block[]{}));
    }
}
