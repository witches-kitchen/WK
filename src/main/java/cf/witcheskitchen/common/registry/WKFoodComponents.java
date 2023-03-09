package cf.witcheskitchen.common.registry;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public interface WKFoodComponents {

    FoodComponent FROSTING = new FoodComponent.Builder().hunger(1).saturationModifier(0.1f).snack().alwaysEdible().statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 250), 0.66F).build();
    FoodComponent RUM_BALLS = new FoodComponent.Builder().hunger(4).saturationModifier(0.5f).snack().alwaysEdible().statusEffect(new StatusEffectInstance(WKStatusEffects.DRUNK, 400), 0.05F).build();
    FoodComponent SUPER_BOOZE = new FoodComponent.Builder().hunger(4).saturationModifier(0.5f).snack().alwaysEdible().statusEffect(new StatusEffectInstance(WKStatusEffects.DRUNK, 4000, 3), 1F).build();
    FoodComponent SUPER_STRONG_ALCOHOL = new FoodComponent.Builder().hunger(1).saturationModifier(1f).snack().alwaysEdible().statusEffect(new StatusEffectInstance(WKStatusEffects.DRUNK, 4000, 2), 0.95F).build();
    FoodComponent STRONG_ALCOHOL = new FoodComponent.Builder().hunger(1).saturationModifier(1f).snack().alwaysEdible().statusEffect(new StatusEffectInstance(WKStatusEffects.DRUNK, 3000, 2), 0.65F).build();
    FoodComponent AVERAGE_ALCOHOL = new FoodComponent.Builder().hunger(1).saturationModifier(1f).snack().alwaysEdible().statusEffect(new StatusEffectInstance(WKStatusEffects.DRUNK, 2000, 1), 0.35F).build();
    FoodComponent WEAK_ALCOHOL = new FoodComponent.Builder().hunger(1).saturationModifier(1f).snack().alwaysEdible().statusEffect(new StatusEffectInstance(WKStatusEffects.DRUNK, 1000), 0.15F).build();
    FoodComponent AMARANTH_GRAIN = new FoodComponent.Builder().hunger(2).saturationModifier(0.5f).snack().alwaysEdible().build();
    FoodComponent MINT_LEAF = new FoodComponent.Builder().hunger(1).saturationModifier(0.2f).snack().alwaysEdible().build();
    FoodComponent BERRIES = new FoodComponent.Builder().hunger(2).saturationModifier(0.5f).snack().alwaysEdible().build();
    FoodComponent COOKIES = new FoodComponent.Builder().hunger(3).saturationModifier(0.7f).snack().alwaysEdible().build();
    FoodComponent TEA_LEAF = new FoodComponent.Builder().hunger(1).saturationModifier(0.3f).snack().alwaysEdible().statusEffect(new StatusEffectInstance(StatusEffects.HASTE, 100), 0.35F).statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 100), 0.35F).build();

    FoodComponent TEA = new FoodComponent.Builder().hunger(0).saturationModifier(0.5f).alwaysEdible().build();

    // Used to control in which order static constructors are called
    static void init() {

    }
}
