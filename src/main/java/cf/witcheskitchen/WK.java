package cf.witcheskitchen;

import cf.witcheskitchen.common.registry.*;
import cf.witcheskitchen.mixin.AxeAccess;
import com.google.common.collect.ImmutableMap;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.bernie.geckolib3.GeckoLib;

import java.util.ArrayList;
import java.util.Map;

public class WK implements ModInitializer {

    public static final String MODID = "witcheskitchen";
    public static final String VERSION = WKVersions.getVersion();
    public static final Logger logger = LoggerFactory.getLogger(MODID);

    public static final ArrayList<Block> LEAF_BLOCKS = new ArrayList<>();
    public static WKConfig config;

    public static WKIdentifier id(String name) {
        return new WKIdentifier(name);
    }

    private static void modifyAxeBlockStripping() {
        final Map<Block, Block> immutableBlocks = AxeAccess.getStrippedBlocks();
        AxeAccess.setStrippedBlocks(new ImmutableMap.Builder<Block, Block>()
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

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        AutoConfig.register(WKConfig.class, GsonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(WKConfig.class).getConfig();
        logger.info("Remember when I told you how my");
        logger.info("Kin is different in some ways?");
        logger.info("It's a fact, she is exactly that!");
        logger.info("A harbinger of death from the world of witchcraft,");
        logger.info("And she's feeding them cakes and her ale to this innocent boy,");
        logger.info("And her magic brings dismay!");
        logger.info("I hear her in the wind, the bane of our town");
        logger.info("Come with me, father, I'm to expose a heathen");
        registerAll();
        if (WKConfig.get().debugMode) {
            logger.info("Witches Kitchen Base: Successfully Loaded");
        }
        logger.info("WitchesKitchen V{} Initialized", VERSION);
    }

    private void registerAll() {
        WKCreativeTabs.register();
        WKPacketTypes.register(EnvType.SERVER);
        WKBlocks.register();
        WKItems.register();
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
//        WKBannerRegistry.registerBanner();
//        WKBannerRegistry.registerBannerClient();
        WKDamageSources.register();
    }
}
