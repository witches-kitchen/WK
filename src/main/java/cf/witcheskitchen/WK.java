package cf.witcheskitchen;

import cf.witcheskitchen.registry.WKBlocks;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WK implements ModInitializer {
    public static final String MODID = "witcheskitchen";
    public static final Logger logger = LogManager.getLogger(MODID);

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        logger.info("Remember when I told you how my");
        logger.info("Kin is different in some ways?");

        logger.info("It's a fact, she is exactly that!");
        logger.info("A harbinger of death from the world of witchcraft,");
        logger.info("And she's feeding them cakes and her ale to this innocent boy,");
        logger.info("And her magic brings dismay!");

        logger.info("I hear her in the wind, the bane of our town");
        logger.info("Come with me, father, I'm to expose a heathen");
        WKBlocks.register();
    }
}
