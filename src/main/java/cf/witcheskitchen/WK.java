package cf.witcheskitchen;

import cf.witcheskitchen.common.registry.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WK implements ModInitializer {

    public static final String MODID = "witcheskitchen";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize(ModContainer mod) {
        WKConfig.init(MODID, WKConfig.class);
        WKCreativeTabs.register();
        WKPacketTypes.register(EnvType.SERVER);
        WKBlocks.register();
        registerOxidizableBlocks();
        WKItems.register();
        WKPotions.register();
        WKFoodComponents.register();
        WKTags.register();
        WKBlockEntityTypes.register();
        WKRecipeTypes.register();
        WKScreenHandlerTypes.register();
        WKParticleTypes.register();
        WKConfiguredFeatures.register();
        WKEventsRegistry.register(EnvType.SERVER);
        WKStatusEffects.register();
        WKSoundEvents.register();
        WKEntityTypes.register();
        WKDamageSources.register();
    }

    private static void registerOxidizableBlocks() {
        OxidizableBlocksRegistry.registerOxidizableBlockPair(WKBlocks.COPPER_WITCHES_OVEN, WKBlocks.EXPOSED_COPPER_WITCHES_OVEN);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(WKBlocks.EXPOSED_COPPER_WITCHES_OVEN, WKBlocks.WEATHERED_COPPER_WITCHES_OVEN);
        OxidizableBlocksRegistry.registerOxidizableBlockPair(WKBlocks.WEATHERED_COPPER_WITCHES_OVEN, WKBlocks.OXIDIZED_COPPER_WITCHES_OVEN);
        OxidizableBlocksRegistry.registerWaxableBlockPair(WKBlocks.COPPER_WITCHES_OVEN, WKBlocks.WAXED_COPPER_WITCHES_OVEN);
        OxidizableBlocksRegistry.registerWaxableBlockPair(WKBlocks.EXPOSED_COPPER_WITCHES_OVEN, WKBlocks.WAXED_EXPOSED_COPPER_WITCHES_OVEN);
        OxidizableBlocksRegistry.registerWaxableBlockPair(WKBlocks.WEATHERED_COPPER_WITCHES_OVEN, WKBlocks.WAXED_WEATHERED_COPPER_WITCHES_OVEN);
        OxidizableBlocksRegistry.registerWaxableBlockPair(WKBlocks.OXIDIZED_COPPER_WITCHES_OVEN, WKBlocks.WAXED_OXIDIZED_COPPER_WITCHES_OVEN);
    }

    public static Identifier id(String name){
        return new Identifier(MODID, name);
    }
}
