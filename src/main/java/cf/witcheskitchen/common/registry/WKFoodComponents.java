package cf.witcheskitchen.common.registry;

import net.minecraft.item.FoodComponent;

public class WKFoodComponents {

    public static final FoodComponent FROSTING = new FoodComponent.Builder().hunger(1).saturationModifier(0.1f).snack().alwaysEdible().build();
}
