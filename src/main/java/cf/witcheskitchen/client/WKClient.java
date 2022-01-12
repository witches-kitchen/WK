package cf.witcheskitchen.client;

import cf.witcheskitchen.client.gui.screen.BrewingBarrelScreen;
import cf.witcheskitchen.client.gui.screen.WitchesOvenScreen;
import cf.witcheskitchen.client.render.blockentity.BrewingBarrelBlockEntityRender;
import cf.witcheskitchen.client.render.blockentity.WitchesOvenBlockEntityRender;
import cf.witcheskitchen.common.blocks.WKLeafCropBlock;
import cf.witcheskitchen.common.registry.RenderRegistry;
import cf.witcheskitchen.common.registry.WKBlockEntityTypes;
import cf.witcheskitchen.common.registry.WKScreenHandlerTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.render.RenderLayer;

import static cf.witcheskitchen.WK.leafBlocks;
import static cf.witcheskitchen.WK.modBlocks;

@Environment(EnvType.CLIENT)
public class WKClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        modBlocks.forEach(this::registerModBlockLayer);
        BlockEntityRendererRegistry.register(WKBlockEntityTypes.WITCHES_OVEN, (ctx) -> new WitchesOvenBlockEntityRender());
        BlockEntityRendererRegistry.register(WKBlockEntityTypes.BREWING_BARREL, (ctx) -> new BrewingBarrelBlockEntityRender());
        ScreenRegistry.register(WKScreenHandlerTypes.WITCHES_OVEN, WitchesOvenScreen::new);
        ScreenRegistry.register(WKScreenHandlerTypes.BREWING_BARREL, BrewingBarrelScreen::new);
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
