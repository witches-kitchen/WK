package cf.witcheskitchen.mixin;

import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(net.minecraft.recipe.BrewingRecipeRegistry.class)
public interface BrewingRecipeRegistryMixin {

    @Invoker("registerPotionRecipe")
    static void callRegisterPotionRecipe(Potion input, Item item, Potion output) {
        throw new AssertionError();
    }
}
