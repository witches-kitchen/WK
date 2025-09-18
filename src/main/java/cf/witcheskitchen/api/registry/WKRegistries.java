package cf.witcheskitchen.api.registry;

import cf.witcheskitchen.api.curse.CurseEffect;
import cf.witcheskitchen.api.fortune.FortuneEffect;
import net.fabricmc.fabric.api.event.registry.FabricRegistry;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.core.Registry;

public class WKRegistries {
    public static final Registry<CurseEffect> CURSE_EFFECTS = FabricRegistryBuilder.createSimple(WKRegistryKeys.CURSE_EFFECTS)
        .attribute(RegistryAttribute.MODDED)
        .attribute(RegistryAttribute.SYNCED)
        .buildAndRegister();

    public static final Registry<FortuneEffect> FORTUNE_EFFECTS = FabricRegistryBuilder.createSimple(WKRegistryKeys.FORTUNE_EFFECTS)
        .attribute(RegistryAttribute.MODDED)
        .attribute(RegistryAttribute.SYNCED)
        .buildAndRegister();
}
