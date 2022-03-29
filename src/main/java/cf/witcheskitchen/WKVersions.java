package cf.witcheskitchen;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WKVersions {

    private static final String ID = WK.MODID;
    private static final Logger LOGGER = LoggerFactory.getLogger(WKVersions.class);
    private static final String WITCHES_KITCHEN_VERSION;

    static {
        final ModMetadata mod = FabricLoader.getInstance()
                .getModContainer(ID)
                .orElseThrow(() -> new RuntimeException("WitchesKitchen is not loaded, cannot continue"))
                .getMetadata();
        WITCHES_KITCHEN_VERSION = mod.getVersion().toString();
        LOGGER.info("Loading WitchesKitchen version {}", WITCHES_KITCHEN_VERSION);
    }

    public static String getVersion() {
        return WITCHES_KITCHEN_VERSION;
    }
}
