package cf.witcheskitchen.api.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.api.curse.CurseDefinition;
import cf.witcheskitchen.api.curse.CurseEffect;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class WKRegistryKeys {
    public static final ResourceKey<Registry<CurseDefinition>> CURSES = ResourceKey.createRegistryKey(WitchesKitchen.id("curses"));
    public static final ResourceKey<Registry<CurseEffect>> CURSE_EFFECTS = ResourceKey.createRegistryKey(WitchesKitchen.id("curse_effects"));
}
