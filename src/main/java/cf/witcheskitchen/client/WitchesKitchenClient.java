package cf.witcheskitchen.client;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.client.gui.screen.BrewingBarrelScreen;
import cf.witcheskitchen.client.gui.screen.WitchesOvenScreen;
import cf.witcheskitchen.client.particle.BubbleParticle;
import cf.witcheskitchen.client.particle.MagicSparkleParticle;
import cf.witcheskitchen.client.particle.WKSplashParticle;
import cf.witcheskitchen.client.registry.WKColorProviderRegistry;
import cf.witcheskitchen.client.registry.WKRendererRegistry;
import cf.witcheskitchen.common.registry.*;
import cf.witcheskitchen.data.DimColorReloadListener;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.block.extensions.api.client.BlockRenderLayerMap;
import org.quiltmc.qsl.resource.loader.api.ResourceLoader;
import org.quiltmc.qsl.resource.loader.api.reloader.IdentifiableResourceReloader;

@ClientOnly
public class WitchesKitchenClient implements ClientModInitializer {

    @Override
    public void onInitializeClient(ModContainer mod) {
        ParticleFactoryRegistry.getInstance().register(WKParticleTypes.BUBBLE, BubbleParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(WKParticleTypes.SPLASH, WKSplashParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(WKParticleTypes.MAGIC_SPARKLE, MagicSparkleParticle.Factory::new);

        HandledScreens.register(WKScreenHandlerTypes.WITCHES_OVEN, WitchesOvenScreen::new);
        HandledScreens.register(WKScreenHandlerTypes.BREWING_BARREL, BrewingBarrelScreen::new);

        WKBlocks.getBlocks().forEach(entry -> BlockRenderLayerMap.put(RenderLayer.getCutout(), entry.object()));//TODO eyo what is this, bad code, fix this

        WKColorProviderRegistry.init();
        WKRendererRegistry.init();
        WKPacketTypes.init(EnvType.CLIENT);
        WKEventsRegistry.init(EnvType.CLIENT);
    }
}
