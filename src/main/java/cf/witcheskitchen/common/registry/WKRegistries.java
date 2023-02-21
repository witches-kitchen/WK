package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.common.curses.Curse;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.util.registry.Registry;

public interface WKRegistries {
    Registry<Curse> CURSES = FabricRegistryBuilder.createSimple(Curse.class, WitchesKitchen.id("curses")).buildAndRegister();
}
