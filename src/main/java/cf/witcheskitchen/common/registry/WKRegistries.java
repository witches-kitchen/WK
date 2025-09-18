package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.api.curse.CurseDefinition;
import cf.witcheskitchen.api.ritual.Ritual;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;

public interface WKRegistries {
    Registry<CurseDefinition> CURSES = FabricRegistryBuilder.createSimple(CurseDefinition.class, WitchesKitchen.id("curses")).buildAndRegister();
    Registry<Ritual> RITUAL = FabricRegistryBuilder.createSimple(Ritual.class, WitchesKitchen.id("ritual")).buildAndRegister();
}
