package cf.witcheskitchen.api.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.api.curse.CurseDefinition;
import cf.witcheskitchen.api.curse.CurseEffect;
import cf.witcheskitchen.api.fortune.FortuneDefinition;
import cf.witcheskitchen.api.fortune.FortuneEffect;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class WKRegistryKeys {
    public static final ResourceKey<Registry<CurseDefinition>> CURSES = ResourceKey.createRegistryKey(WitchesKitchen.id("curses"));
    public static final ResourceKey<Registry<CurseEffect>> CURSE_EFFECTS = ResourceKey.createRegistryKey(WitchesKitchen.id("curse_effects"));

    public static final ResourceKey<Registry<FortuneDefinition>> FORTUNES = ResourceKey.createRegistryKey(WitchesKitchen.id("fortunes"));
    public static final ResourceKey<Registry<FortuneEffect>> FORTUNE_EFFECTS = ResourceKey.createRegistryKey(WitchesKitchen.id("fortune_effects"));
}
