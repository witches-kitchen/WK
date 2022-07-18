package cf.witcheskitchen;

import org.quiltmc.loader.api.ModMetadata;
import org.quiltmc.loader.api.QuiltLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Finds the Current WitchesKitchen version statically
 * invoked before it gets loaded.
 */
public class WKVersion {

    private static final String ID = WK.MODID;
    private static final Logger LOGGER = LoggerFactory.getLogger(WKVersion.class);
    private static final String WITCHES_KITCHEN_VERSION;

    static {
        final ModMetadata mod = QuiltLoader.getModContainer(ID)
                .orElseThrow(() -> new RuntimeException("WitchesKitchen is not loaded, cannot continue"))
                .metadata();
        WITCHES_KITCHEN_VERSION = mod.version().toString();
        LOGGER.info("Loading WitchesKitchen version {}", WITCHES_KITCHEN_VERSION);
    }

    public static String getVersion() {
        return WITCHES_KITCHEN_VERSION;
    }
}
