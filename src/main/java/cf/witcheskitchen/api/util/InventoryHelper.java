package cf.witcheskitchen.api.util;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashSet;
import java.util.Set;

public final class InventoryHelper {

    private InventoryHelper() {
        // Don't let anyone instantiate this
    }

    public static boolean containsItem(Container inventory, Item item) {
        return findAnyIndexOf(inventory, item) >= 0;
    }

    /**
     * Finds the position of the requested {@link ItemStack} (including empty stacks), or -1 if is not found.
     *
     * @param inventory {@link Container}
     * @param search    {@link ItemStack} to search.
     * @return index of the stack
     */
    public static int findAnyIndexOf(Container inventory, ItemStack search) {
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            if (ItemUtil.areItemsEqual(inventory.getItem(i), search, true)) {
                return i;
            }
        }
        return -1;
    }


    /**
     * Finds the position of the requested {@link Item} or -1 if is not found.
     *
     * @param inventory {@link Container}
     * @param search    {@link Item} to search.
     * @return index of the stack
     */
    public static int findAnyIndexOf(Container inventory, Item search) {
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            if (inventory.getItem(i).getItem() == search) {
                return i;
            }
        }
        return -1;
    }


    /**
     * Finds the amount of <b>NON-EMPTY</b> and <b>NON-DUPLICATED</b>
     * <i>stacks</i> in the given Defaulted List.
     *
     * @param stacks DefaultedList
     * @return the count of the Set of Items.
     */
    public static int countInSet(final NonNullList<ItemStack> stacks) {
        if (stacks.isEmpty()) {
            return 0;
        }
        final Set<Item> elements = createNonEmptySet(stacks);
        return elements.size();
    }

    /**
     * Creates an <b>NON EMPTY</b> {@link HashSet} of Items from the given inventory
     *
     * @param stacks DefaultedList of ItemStack
     * @return A populated HashSet instance
     */
    public static Set<Item> createNonEmptySet(final NonNullList<ItemStack> stacks) {
        final Set<Item> nonEmptySet = new HashSet<>();
        for (ItemStack entry : stacks) {
            if (!entry.isEmpty()) {
                nonEmptySet.add(entry.getItem());
            }
        }
        return nonEmptySet;
    }


}
