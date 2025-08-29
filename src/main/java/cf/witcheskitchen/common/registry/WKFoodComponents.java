package cf.witcheskitchen.common.registry;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

public interface WKFoodComponents {

    Pair<FoodProperties, Consumable> FROSTING = new FoodComponentBuilder(Consumables.defaultFood()).nutrition(1).saturationModifier(0.1f).snack().alwaysEdible().statusEffect(new MobEffectInstance(MobEffects.HUNGER, 250), 0.66F).build();
    Pair<FoodProperties, Consumable> RUM_BALLS = new FoodComponentBuilder(Consumables.defaultFood()).nutrition(4).saturationModifier(0.5f).snack().alwaysEdible().statusEffect(new MobEffectInstance(WKStatusEffects.DRUNK, 400), 0.05F).build();
    Pair<FoodProperties, Consumable> SUPER_BOOZE = new FoodComponentBuilder(Consumables.defaultDrink()).nutrition(4).saturationModifier(0.5f).snack().alwaysEdible().statusEffect(new MobEffectInstance(WKStatusEffects.DRUNK, 4000, 3), 1F).build();
    Pair<FoodProperties, Consumable> SUPER_STRONG_ALCOHOL = new FoodComponentBuilder(Consumables.defaultDrink()).nutrition(1).saturationModifier(1f).snack().alwaysEdible().statusEffect(new MobEffectInstance(WKStatusEffects.DRUNK, 4000, 2), 0.95F).build();
    Pair<FoodProperties, Consumable> STRONG_ALCOHOL = new FoodComponentBuilder(Consumables.defaultDrink()).nutrition(1).saturationModifier(1f).snack().alwaysEdible().statusEffect(new MobEffectInstance(WKStatusEffects.DRUNK, 3000, 2), 0.65F).build();
    Pair<FoodProperties, Consumable> AVERAGE_ALCOHOL = new FoodComponentBuilder(Consumables.defaultDrink()).nutrition(1).saturationModifier(1f).snack().alwaysEdible().statusEffect(new MobEffectInstance(WKStatusEffects.DRUNK, 2000, 1), 0.35F).build();
    Pair<FoodProperties, Consumable> WEAK_ALCOHOL = new FoodComponentBuilder(Consumables.defaultDrink()).nutrition(1).saturationModifier(1f).snack().alwaysEdible().statusEffect(new MobEffectInstance(WKStatusEffects.DRUNK, 1000), 0.15F).build();
    Pair<FoodProperties, Consumable> AMARANTH_GRAIN = new FoodComponentBuilder(Consumables.defaultFood()).nutrition(2).saturationModifier(0.5f).snack().alwaysEdible().build();
    Pair<FoodProperties, Consumable> MINT_LEAF = new FoodComponentBuilder(Consumables.defaultFood()).nutrition(1).saturationModifier(0.2f).snack().alwaysEdible().build();
    Pair<FoodProperties, Consumable> BERRIES = new FoodComponentBuilder(Consumables.defaultFood()).nutrition(2).saturationModifier(0.5f).snack().alwaysEdible().build();
    Pair<FoodProperties, Consumable> COOKIES = new FoodComponentBuilder(Consumables.defaultFood()).nutrition(3).saturationModifier(0.7f).snack().alwaysEdible().build();
    Pair<FoodProperties, Consumable> TEA_LEAF = new FoodComponentBuilder(Consumables.defaultFood()).nutrition(1).saturationModifier(0.3f).snack().alwaysEdible().statusEffect(new MobEffectInstance(MobEffects.HASTE, 100), 0.35F).statusEffect(new MobEffectInstance(MobEffects.SPEED, 100), 0.35F).build();

    FoodProperties TEA = new FoodProperties.Builder().nutrition(0).saturationModifier(0.5f).alwaysEdible().build();

    // Used to control in which order static constructors are called
    static void init() {

    }

    class FoodComponentBuilder {
        private final FoodProperties.Builder foodBuilder = new FoodProperties.Builder();
        private final Consumable.Builder consumableBuilder;

        public FoodComponentBuilder(Consumable.Builder builder) {
            this.consumableBuilder = builder;
        }

        public FoodComponentBuilder nutrition(int nutrition) {
            this.foodBuilder.nutrition(nutrition);
            return this;
        }

        public FoodComponentBuilder saturationModifier(float satMod) {
            this.foodBuilder.saturationModifier(satMod);
            return this;
        }

        public FoodComponentBuilder snack() {
            this.consumableBuilder.consumeSeconds(Consumable.DEFAULT_CONSUME_SECONDS / 2f);
            return this;
        }

        public FoodComponentBuilder alwaysEdible() {
            this.foodBuilder.alwaysEdible();
            return this;
        }

        public FoodComponentBuilder statusEffect(MobEffectInstance effectInstance, float probability) {
            this.consumableBuilder.onConsume(new ApplyStatusEffectsConsumeEffect(effectInstance, probability));
            return this;
        }

        public Pair<FoodProperties, Consumable> build() {
            return new Pair<>(this.foodBuilder.build(), this.consumableBuilder.build());
        }
    }
}
