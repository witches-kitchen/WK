package cf.witcheskitchen;

import eu.midnightdust.lib.config.MidnightConfig;

public class WitchesKitchenConfig extends MidnightConfig {
    @MidnightConfig.Comment public static MidnightConfig.Comment debug;
    @Entry public static boolean debugMode = false;

}
