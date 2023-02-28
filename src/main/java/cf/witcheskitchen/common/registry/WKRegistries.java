package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.api.ritual.Ritual;
import cf.witcheskitchen.common.curse.Curse;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registry;

public interface WKRegistries {
    Registry<Curse> CURSES = FabricRegistryBuilder.createSimple(Curse.class, WitchesKitchen.id("curses")).buildAndRegister();
    Registry<Ritual> RITUAL = FabricRegistryBuilder.createSimple(Ritual.class, WitchesKitchen.id("ritual")).buildAndRegister();
}
