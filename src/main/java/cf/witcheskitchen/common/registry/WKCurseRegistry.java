package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.api.registry.ObjectDefinition;
import cf.witcheskitchen.common.curse.Curse;
import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public interface WKCurseRegistry {
    List<ObjectDefinition<Curse>> CURSES = new ArrayList<>();

    Curse CALEFACTION = register("calefaction", new Curse(2));
    Curse CURSE_OF_MIDAS = register("curse_of_midas", new Curse(1));
    Curse FEAR = register("fear", new Curse(1));
    Curse FIELD_GEISTER_HEX = register("feild_geister_hex", new Curse(1));
    Curse HUNGRY_POCKETS = register("hungry_pockets", new Curse(1));
    Curse INEPTITUDE = register("ineptitude", new Curse(1));
    Curse MISPLACEMENT = register("misplacement", new Curse(1));
    Curse NULLARDOR = register("nullador", new Curse(1));
    Curse PARANOIA = register("paranoia", new Curse(3));
    Curse PERUNS_JEST = register("peruns_jest", new Curse(1));


    static <T extends Curse> T register(String name, T curse) {
        CURSES.add(new ObjectDefinition<>(WitchesKitchen.id(name), curse));
        return curse;
    }

    static void init() {
        CURSES.forEach(entry -> Registry.register(WKRegistries.CURSES, entry.id(), entry.object()));
    }
}
