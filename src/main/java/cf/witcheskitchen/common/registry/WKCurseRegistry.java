package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.api.curse.CurseDefinition;
import cf.witcheskitchen.api.registry.ObjectDefinition;
import net.minecraft.core.Registry;

import java.util.ArrayList;
import java.util.List;

public interface WKCurseRegistry {
    List<ObjectDefinition<CurseDefinition>> CURSES = new ArrayList<>();

    CurseDefinition CALEFACTION = register("calefaction", new CurseDefinition(2));
    CurseDefinition CURSE_DEFINITION_OF_MIDAS = register("curse_of_midas", new CurseDefinition(1));
    CurseDefinition FEAR = register("fear", new CurseDefinition(1));
    CurseDefinition FIELD_GEISTER_HEX = register("feild_geister_hex", new CurseDefinition(1));
    CurseDefinition HUNGRY_POCKETS = register("hungry_pockets", new CurseDefinition(1));
    CurseDefinition INEPTITUDE = register("ineptitude", new CurseDefinition(1));
    CurseDefinition MISPLACEMENT = register("misplacement", new CurseDefinition(1));
    CurseDefinition NULLARDOR = register("nullador", new CurseDefinition(1));
    CurseDefinition PARANOIA = register("paranoia", new CurseDefinition(3));
    CurseDefinition PERUNS_JEST = register("peruns_jest", new CurseDefinition(1));


    static <T extends CurseDefinition> T register(String name, T curse) {
        CURSES.add(new ObjectDefinition<>(WitchesKitchen.id(name), curse));
        return curse;
    }

    static void init() {
        CURSES.forEach(entry -> Registry.register(WKRegistries.CURSES, entry.id(), entry.object()));
    }
}
