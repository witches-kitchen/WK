package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WKIdentifier;
import cf.witcheskitchen.api.registry.ObjectDefinition;
import cf.witcheskitchen.mixin.BrewingRecipeRegistryMixin;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WKPotions {

    private static final List<ObjectDefinition<Potion>> POTIONS = new ArrayList<>();

    public static final Potion RUM = register("rum", new Potion(withEffect(WKStatusEffects.DRUNK, 200)));

    private static <T extends Potion> T register(String path, T potion) {
        final Identifier resource = new WKIdentifier(path);
        POTIONS.add(new ObjectDefinition<>(resource, potion));
        return potion;
    }


    private static StatusEffectInstance withEffect(StatusEffect effect, int duration) {
        return new StatusEffectInstance(effect, duration, 0);
    }

    private static StatusEffectInstance withEffect(StatusEffect effect, int duration, int amplifier) {
        return new StatusEffectInstance(effect, duration, amplifier);
    }

    public static void register() {
        POTIONS.forEach(entry -> Registry.register(Registry.POTION, entry.id(), entry.object()));
        BrewingRecipeRegistryMixin.callRegisterPotionRecipe(Potions.AWKWARD, Items.COPPER_INGOT, RUM);
    }

    public static List<ObjectDefinition<Potion>> getPotions() {
        return Collections.unmodifiableList(POTIONS);
    }

}
