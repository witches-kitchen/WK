package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.api.registry.ObjectDefinition;
import cf.witcheskitchen.common.potion.RumPotion;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface WKPotions {

    List<ObjectDefinition<Potion>> POTIONS = new ArrayList<>();

    Potion RUM = register("rum", new RumPotion(withEffect(WKStatusEffects.DRUNK, 200)));

    static <T extends Potion> T register(String path, T potion) {
        final Identifier resource = WitchesKitchen.id(path);
        POTIONS.add(new ObjectDefinition<>(resource, potion));
        return potion;
    }


    static StatusEffectInstance withEffect(StatusEffect effect, int duration) {
        return new StatusEffectInstance(effect, duration, 0);
    }

    static StatusEffectInstance withEffect(StatusEffect effect, int duration, int amplifier) {
        return new StatusEffectInstance(effect, duration, amplifier);
    }

    static void init() {
        POTIONS.forEach(entry -> Registry.register(Registry.POTION, entry.id(), entry.object()));
    }

    static List<ObjectDefinition<Potion>> getPotions() {
        return Collections.unmodifiableList(POTIONS);
    }

}
