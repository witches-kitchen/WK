package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.api.registry.ObjectDefinition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

public interface WKPotions {

    List<ObjectDefinition<Potion>> POTIONS = new ArrayList<>();

    Potion DRUNK = register("rum", new Potion("rum", withEffect(WKStatusEffects.DRUNK, 200)));

    static <T extends Potion> T register(String path, T potion) {
        final ResourceLocation resource = WitchesKitchen.id(path);
        POTIONS.add(new ObjectDefinition<>(resource, potion));
        return potion;
    }


    static MobEffectInstance withEffect(Holder<MobEffect> effect, int duration) {
        return new MobEffectInstance(effect, duration, 0);
    }

    static MobEffectInstance withEffect(Holder<MobEffect> effect, int duration, int amplifier) {
        return new MobEffectInstance(effect, duration, amplifier);
    }

    static void init() {
        POTIONS.forEach(entry -> Registry.register(BuiltInRegistries.POTION, entry.id(), entry.object()));
    }

    static List<ObjectDefinition<Potion>> getPotions() {
        return Collections.unmodifiableList(POTIONS);
    }

}
