package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.WKConfig;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class WKFoodComponents {

    public static final FoodComponent FROSTING = new FoodComponent.Builder().hunger(1).saturationModifier(0.1f).snack().alwaysEdible().statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 250), 0.66F).build();
    public static final FoodComponent RUM_BALLS = new FoodComponent.Builder().hunger(4).saturationModifier(0.5f).snack().alwaysEdible().statusEffect(new StatusEffectInstance(WKStatusEffects.DRUNK, 400), 0.05F).build();
    public static final FoodComponent SUPER_BOOZE = new FoodComponent.Builder().hunger(4).saturationModifier(0.5f).snack().alwaysEdible().statusEffect(new StatusEffectInstance(WKStatusEffects.DRUNK, 4000, 3), 1F).build();
    public static final FoodComponent SUPER_STRONG_ALCOHOL = new FoodComponent.Builder().hunger(1).saturationModifier(1f).snack().alwaysEdible().statusEffect(new StatusEffectInstance(WKStatusEffects.DRUNK, 4000, 2), 0.95F).build();
    public static final FoodComponent STRONG_ALCOHOL = new FoodComponent.Builder().hunger(1).saturationModifier(1f).snack().alwaysEdible().statusEffect(new StatusEffectInstance(WKStatusEffects.DRUNK, 3000, 2), 0.65F).build();
    public static final FoodComponent AVERAGE_ALCOHOL = new FoodComponent.Builder().hunger(1).saturationModifier(1f).snack().alwaysEdible().statusEffect(new StatusEffectInstance(WKStatusEffects.DRUNK, 2000, 1), 0.35F).build();
    public static final FoodComponent WEAK_ALCOHOL = new FoodComponent.Builder().hunger(1).saturationModifier(1f).snack().alwaysEdible().statusEffect(new StatusEffectInstance(WKStatusEffects.DRUNK, 1000), 0.15F).build();
    public static final FoodComponent AMARANTH_GRAIN = new FoodComponent.Builder().hunger(2).saturationModifier(0.5f).snack().alwaysEdible().build();
    public static final FoodComponent MINT_LEAF = new FoodComponent.Builder().hunger(1).saturationModifier(0.2f).snack().alwaysEdible().build();
    public static final FoodComponent BERRIES = new FoodComponent.Builder().hunger(2).saturationModifier(0.5f).snack().alwaysEdible().build();
    public static final FoodComponent COOKIES = new FoodComponent.Builder().hunger(3).saturationModifier(0.7f).snack().alwaysEdible().build();
    public static final FoodComponent TEA_LEAF = new FoodComponent.Builder().hunger(1).saturationModifier(0.3f).snack().alwaysEdible().statusEffect(new StatusEffectInstance(StatusEffects.HASTE, 100), 0.35F).statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 100), 0.35F).build();

    static {
        if (WKConfig.get().debugMode) {
            WK.logger.info("Witches Kitchen Base Food Components: Successfully Loaded");
        }
    }

    // Used to control in which order static constructors are called
    public static void register() {

    }
}
