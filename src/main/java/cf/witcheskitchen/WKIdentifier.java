package cf.witcheskitchen;

import net.minecraft.util.Identifier;

/**
 * An Identifier with the WitchesKitchen namespace.
 */
public class WKIdentifier extends Identifier {

    public WKIdentifier(String path) {
        super(WK.MODID, path);
    }
}
