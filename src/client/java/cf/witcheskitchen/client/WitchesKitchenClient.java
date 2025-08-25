package cf.witcheskitchen.client;

import cf.witcheskitchen.client.gui.screen.BrewingBarrelScreen;
import cf.witcheskitchen.client.gui.screen.WitchesOvenScreen;
import cf.witcheskitchen.client.particle.BubbleParticle;
import cf.witcheskitchen.client.particle.MagicSparkleParticle;
import cf.witcheskitchen.client.particle.WKSplashParticle;
import cf.witcheskitchen.client.registry.WKClientEventsRegistry;
import cf.witcheskitchen.client.registry.WKClientPacketTypes;
import cf.witcheskitchen.client.registry.WKColorProviderRegistry;
import cf.witcheskitchen.client.registry.WKRendererRegistry;
import cf.witcheskitchen.common.component.WKComponents;
import cf.witcheskitchen.common.registry.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class WitchesKitchenClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.getInstance().register(WKParticleTypes.BUBBLE, BubbleParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(WKParticleTypes.SPLASH, WKSplashParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(WKParticleTypes.MAGIC_SPARKLE, MagicSparkleParticle.Factory::new);

        HandledScreens.register(WKScreenHandlerTypes.WITCHES_OVEN, WitchesOvenScreen::new);
        HandledScreens.register(WKScreenHandlerTypes.BREWING_BARREL, BrewingBarrelScreen::new);

        WKBlocks.getBlocks().forEach(entry -> BlockRenderLayerMap.putBlock(entry.object(), BlockRenderLayer.CUTOUT));//TODO eyo what is this, bad code, fix this

        // TODO: implement with items JSON
        /*ModelPredicateProviderRegistry.register(WKItems.WAYSTONE, Identifier.of("bound"), ((itemStack, clientWorld, livingEntity, i) -> {
            if (itemStack.contains(WKComponents.BLOCK_POS)) {
                return 1.0f;
            } else {
                return 0.0f;
            }
        }));*/

        WKColorProviderRegistry.init();
        WKRendererRegistry.init();
        WKClientPacketTypes.init();
        WKClientEventsRegistry.init();
        WKEventsRegistry.init(EnvType.CLIENT);
    }
}
