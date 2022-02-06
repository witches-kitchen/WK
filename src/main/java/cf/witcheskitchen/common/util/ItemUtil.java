package cf.witcheskitchen.common.util;

import net.minecraft.item.ItemStack;

public final class ItemUtil {

    private ItemUtil() {

    }

    //Checks that stackA and stackB are equal
    //This is as check to merge items on shift-click
    public static boolean areItemsEqual(final ItemStack stackA, final ItemStack stackB, final boolean matchNBT) {
        if (stackA.isEmpty() && stackB.isEmpty()) {
            return true;//they are both air, fast return
        } else if (stackA.isEmpty() || stackB.isEmpty()) {
            return false;//one of them is air
        } else if (stackA.getItem() != stackB.getItem()) {
            return false; //They are different items
        } else {
            //Otherwise, they are the same items
            if (!matchNBT) { //return if we don't want to match nbt
                return true;
            }
            //Match nbt
            return ItemStack.areNbtEqual(stackA, stackB);
        }
    }

    public static ItemStack consumeStack(ItemStack stack) {
        if (stack.getCount() == 1) {
            return stack.getItem().hasRecipeRemainder() ? new ItemStack(stack.getItem().getRecipeRemainder()) : ItemStack.EMPTY;
        } else {
            stack.split(1);
            return stack;
        }
    }
}
