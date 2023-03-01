package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.api.registry.ObjectDefinition;
import cf.witcheskitchen.api.ritual.Ritual;
import cf.witcheskitchen.common.ritual.FreezeRitual;
import cf.witcheskitchen.common.ritual.LightningRitual;
import cf.witcheskitchen.common.ritual.SummoningRitual;
import cf.witcheskitchen.common.ritual.VariantIncreaseRitual;
import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public interface WKRitualRegistry {
    List<ObjectDefinition<Ritual>> RITUALS = new ArrayList<>();

    Ritual DUMMY = register("test", new Ritual());
    Ritual SUMMON = register("summon", new SummoningRitual());
    Ritual FREEZE = register("freeze", new FreezeRitual());
    Ritual LIGHTNING = register("lightning", new LightningRitual());
    Ritual VARIANT = register("variant", new VariantIncreaseRitual());

    static <T extends Ritual> T register(String id, T ritual) {
        return Registry.register(WKRegistries.RITUAL, WitchesKitchen.id(id), ritual);
    }

    static void init() {

    }
}
