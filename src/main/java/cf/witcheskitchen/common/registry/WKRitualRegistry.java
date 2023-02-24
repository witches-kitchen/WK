package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.api.registry.ObjectDefinition;
import cf.witcheskitchen.api.ritual.Ritual;
import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public interface WKRitualRegistry {
    List<ObjectDefinition<Ritual>> RITUALS = new ArrayList<>();

    Ritual DUMMY = register("test", new Ritual());

    static <T extends Ritual> T register(String id, T ritual) {
        return Registry.register(WKRegistries.RITUAL, WitchesKitchen.id(id), ritual);
    }

    static void init() {

    }
}
