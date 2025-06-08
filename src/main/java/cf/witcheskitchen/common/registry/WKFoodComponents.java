package cf.witcheskitchen.common.registry;

import com.mojang.datafixers.util.Pair;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.consume.ApplyEffectsConsumeEffect;

public interface WKFoodComponents {

    Pair<FoodComponent, ConsumableComponent> FROSTING = new FoodComponentBuilder(ConsumableComponents.food()).nutrition(1).saturationModifier(0.1f).snack().alwaysEdible().statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 250), 0.66F).build();
    Pair<FoodComponent, ConsumableComponent> RUM_BALLS = new FoodComponentBuilder(ConsumableComponents.food()).nutrition(4).saturationModifier(0.5f).snack().alwaysEdible().statusEffect(new StatusEffectInstance(WKStatusEffects.DRUNK, 400), 0.05F).build();
    Pair<FoodComponent, ConsumableComponent> SUPER_BOOZE = new FoodComponentBuilder(ConsumableComponents.drink()).nutrition(4).saturationModifier(0.5f).snack().alwaysEdible().statusEffect(new StatusEffectInstance(WKStatusEffects.DRUNK, 4000, 3), 1F).build();
    Pair<FoodComponent, ConsumableComponent> SUPER_STRONG_ALCOHOL = new FoodComponentBuilder(ConsumableComponents.drink()).nutrition(1).saturationModifier(1f).snack().alwaysEdible().statusEffect(new StatusEffectInstance(WKStatusEffects.DRUNK, 4000, 2), 0.95F).build();
    Pair<FoodComponent, ConsumableComponent> STRONG_ALCOHOL = new FoodComponentBuilder(ConsumableComponents.drink()).nutrition(1).saturationModifier(1f).snack().alwaysEdible().statusEffect(new StatusEffectInstance(WKStatusEffects.DRUNK, 3000, 2), 0.65F).build();
    Pair<FoodComponent, ConsumableComponent> AVERAGE_ALCOHOL = new FoodComponentBuilder(ConsumableComponents.drink()).nutrition(1).saturationModifier(1f).snack().alwaysEdible().statusEffect(new StatusEffectInstance(WKStatusEffects.DRUNK, 2000, 1), 0.35F).build();
    Pair<FoodComponent, ConsumableComponent> WEAK_ALCOHOL = new FoodComponentBuilder(ConsumableComponents.drink()).nutrition(1).saturationModifier(1f).snack().alwaysEdible().statusEffect(new StatusEffectInstance(WKStatusEffects.DRUNK, 1000), 0.15F).build();
    Pair<FoodComponent, ConsumableComponent> AMARANTH_GRAIN = new FoodComponentBuilder(ConsumableComponents.food()).nutrition(2).saturationModifier(0.5f).snack().alwaysEdible().build();
    Pair<FoodComponent, ConsumableComponent> MINT_LEAF = new FoodComponentBuilder(ConsumableComponents.food()).nutrition(1).saturationModifier(0.2f).snack().alwaysEdible().build();
    Pair<FoodComponent, ConsumableComponent> BERRIES = new FoodComponentBuilder(ConsumableComponents.food()).nutrition(2).saturationModifier(0.5f).snack().alwaysEdible().build();
    Pair<FoodComponent, ConsumableComponent> COOKIES = new FoodComponentBuilder(ConsumableComponents.food()).nutrition(3).saturationModifier(0.7f).snack().alwaysEdible().build();
    Pair<FoodComponent, ConsumableComponent> TEA_LEAF = new FoodComponentBuilder(ConsumableComponents.food()).nutrition(1).saturationModifier(0.3f).snack().alwaysEdible().statusEffect(new StatusEffectInstance(StatusEffects.HASTE, 100), 0.35F).statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 100), 0.35F).build();

    FoodComponent TEA = new FoodComponent.Builder().nutrition(0).saturationModifier(0.5f).alwaysEdible().build();

    // Used to control in which order static constructors are called
    static void init() {

    }

    class FoodComponentBuilder {
        private final FoodComponent.Builder foodBuilder = new FoodComponent.Builder();
        private final ConsumableComponent.Builder consumableBuilder;

        public FoodComponentBuilder(ConsumableComponent.Builder builder) {
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
            this.consumableBuilder.consumeSeconds(ConsumableComponent.DEFAULT_CONSUME_SECONDS / 2f);
            return this;
        }

        public FoodComponentBuilder alwaysEdible() {
            this.foodBuilder.alwaysEdible();
            return this;
        }

        public FoodComponentBuilder statusEffect(StatusEffectInstance effectInstance, float probability) {
            this.consumableBuilder.consumeEffect(new ApplyEffectsConsumeEffect(effectInstance, probability));
            return this;
        }

        public Pair<FoodComponent, ConsumableComponent> build() {
            return new Pair<>(this.foodBuilder.build(), this.consumableBuilder.build());
        }
    }
}
