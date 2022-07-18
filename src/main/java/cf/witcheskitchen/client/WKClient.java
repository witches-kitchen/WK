package cf.witcheskitchen.client;

import cf.witcheskitchen.client.gui.screen.BrewingBarrelScreen;
import cf.witcheskitchen.client.gui.screen.WitchesOvenScreen;
import cf.witcheskitchen.client.particle.BubbleParticle;
import cf.witcheskitchen.client.particle.MagicSparkleParticle;
import cf.witcheskitchen.client.particle.WKSplashParticle;
import cf.witcheskitchen.client.registry.RenderRegistry;
import cf.witcheskitchen.client.render.blockentity.BrewingBarrelBlockEntityRender;
import cf.witcheskitchen.client.render.blockentity.WitchesCauldronBlockEntityRender;
import cf.witcheskitchen.client.render.blockentity.WitchesOvenBlockEntityRender;
import cf.witcheskitchen.common.registry.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;

import static cf.witcheskitchen.WK.LEAF_BLOCKS;

@Environment(EnvType.CLIENT)
public class WKClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WKPacketTypes.register(EnvType.CLIENT);
        ParticleFactoryRegistry.getInstance().register(WKParticleTypes.BUBBLE, BubbleParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(WKParticleTypes.SPLASH, WKSplashParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(WKParticleTypes.MAGIC_SPARKLE, MagicSparkleParticle.Factory::new);
        WKEventsRegistry.registerClient();
        BlockEntityRendererRegistry.register(WKBlockEntityTypes.WITCHES_OVEN, (ctx) -> new WitchesOvenBlockEntityRender());
        BlockEntityRendererRegistry.register(WKBlockEntityTypes.WITCHES_CAULDRON, (ctx) -> new WitchesCauldronBlockEntityRender());
        BlockEntityRendererRegistry.register(WKBlockEntityTypes.BREWING_BARREL, (ctx) -> new BrewingBarrelBlockEntityRender());
        HandledScreens.register(WKScreenHandlerTypes.WITCHES_OVEN, WitchesOvenScreen::new);
        HandledScreens.register(WKScreenHandlerTypes.BREWING_BARREL, BrewingBarrelScreen::new);
        WKBlocks.getBlocks().forEach(entry -> BlockRenderLayerMap.INSTANCE.putBlock(entry.object(), RenderLayer.getCutout()));
        registerColorProvider();
        RenderRegistry.register();
       // WKBannerRegistry.registerBanner();
       // WKBannerRegistry.registerBannerClient();
    }

    //allows for color map modification of leaves based on biome
    public void registerColorProvider() {

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) ->
                world != null && pos != null
                        ? BiomeColors.getFoliageColor(world, pos)
                        : FoliageColors.getDefaultColor(), LEAF_BLOCKS.toArray(new Block[]{}));
    }
}
