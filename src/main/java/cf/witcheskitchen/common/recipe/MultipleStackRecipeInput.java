package cf.witcheskitchen.common.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;

import java.util.List;

public record MultipleStackRecipeInput(List<ItemStack> stacks) implements RecipeInput {
    @Override
    public ItemStack getStackInSlot(int slot) {
        return stacks.get(slot);
    }

    @Override
    public int size() {
        return stacks.size();
    }
}
