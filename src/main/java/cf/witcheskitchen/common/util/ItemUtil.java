package cf.witcheskitchen.common.util;

import net.minecraft.item.ItemStack;

public class ItemUtil {

    public static ItemStack consumeStack(ItemStack stack) {
        if (stack.getCount() == 1) {
            return stack.getItem().hasRecipeRemainder() ? new ItemStack(stack.getItem().getRecipeRemainder()) : ItemStack.EMPTY;
        } else {
            stack.split(1);
            return stack;
        }
    }
}
