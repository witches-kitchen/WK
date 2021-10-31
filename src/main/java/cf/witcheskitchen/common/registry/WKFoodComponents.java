package cf.witcheskitchen.common.registry;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class WKFoodComponents {

    public static final FoodComponent FROSTING = new FoodComponent.Builder().hunger(1).saturationModifier(0.1f).snack().alwaysEdible().statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 250), 0.66F).build();
    public static final FoodComponent RUM_BALLS = new FoodComponent.Builder().hunger(4).saturationModifier(0.5f).snack().alwaysEdible().statusEffect(new StatusEffectInstance(WKStatusEffects.DRUNK, 400), 0.05F).build();
}
