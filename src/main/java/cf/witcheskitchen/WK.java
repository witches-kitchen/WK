package cf.witcheskitchen;

import cf.witcheskitchen.common.registry.*;
import com.google.common.collect.ImmutableMap;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.fabricmc.fabric.mixin.content.registry.AxeItemAccessor;
import net.minecraft.block.Block;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib3.GeckoLib;

import java.util.Map;

public class WK implements ModInitializer {

    public static final String MODID = "witcheskitchen";
    public static final String VERSION = WKVersion.getVersion();
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    public static final WKConfig CONFIG_FILE;

    static {
        AutoConfig.register(WKConfig.class, JanksonConfigSerializer::new);
        CONFIG_FILE = AutoConfig.getConfigHolder(WKConfig.class).getConfig();
    }

    private static void initialize() {
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
        GeckoLib.initialize();
        WKEntityTypes.register();
        modifyAxeBlockStripping();
        WKDamageSources.register();
        LOGGER.info("WitchesKitchen V{} Initialized", VERSION);
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

    private static void modifyAxeBlockStripping() {
        final Map<Block, Block> immutableBlocks = AxeItemAccessor.getStrippedBlocks();
        AxeItemAccessor.setStrippedBlocks(new ImmutableMap.Builder<Block, Block>()
                .putAll(immutableBlocks)
                .put(WKBlocks.BLACKTHORN_LOG, WKBlocks.STRIPPED_BLACKTHORN_LOG)
                .put(WKBlocks.BLACKTHORN_WOOD, WKBlocks.STRIPPED_BLACKTHORN_WOOD)
                .put(WKBlocks.ELDER_LOG, WKBlocks.STRIPPED_ELDER_LOG)
                .put(WKBlocks.ELDER_WOOD, WKBlocks.STRIPPED_ELDER_WOOD)
                .put(WKBlocks.HAWTHORN_LOG, WKBlocks.STRIPPED_HAWTHORN_LOG)
                .put(WKBlocks.HAWTHORN_WOOD, WKBlocks.STRIPPED_HAWTHORN_WOOD)
                .put(WKBlocks.JUNIPER_LOG, WKBlocks.STRIPPED_JUNIPER_LOG)
                .put(WKBlocks.JUNIPER_WOOD, WKBlocks.STRIPPED_JUNIPER_WOOD)
                .put(WKBlocks.ROWAN_LOG, WKBlocks.STRIPPED_ROWAN_LOG)
                .put(WKBlocks.ROWAN_WOOD, WKBlocks.STRIPPED_ROWAN_WOOD)
                .put(WKBlocks.SUMAC_LOG, WKBlocks.STRIPPED_SUMAC_LOG)
                .put(WKBlocks.SUMAC_WOOD, WKBlocks.STRIPPED_SUMAC_WOOD)
                .build());
    }

    public static WKConfig getConfigFile() {
        return CONFIG_FILE;
    }

    @Override
    public void onInitialize(ModContainer mod) {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        LOGGER.info("Remember when I told you how my");
        LOGGER.info("Kin is different in some ways?");
        LOGGER.info("It's a fact, she is exactly that!");
        LOGGER.info("A harbinger of death from the world of witchcraft,");
        LOGGER.info("And she's feeding them cakes and her ale to this innocent boy,");
        LOGGER.info("And her magic brings dismay!");
        LOGGER.info("I hear her in the wind, the bane of our town");
        LOGGER.info("Come with me, father, I'm to expose a heathen");
        WK.initialize();
        if (WKConfig.getInstance().debugMode) {
            LOGGER.info("Witches Kitchen Base: Successfully Loaded");
        }

    }
}
