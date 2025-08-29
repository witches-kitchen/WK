package cf.witcheskitchen.api.util;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;

public final class ItemUtil {

    private ItemUtil() {
        // Utility class not meant to be instantiated
    }

    /**
     * <p>
     * <b>Consumes</b> the Item at the given hand of the player
     * with the {@link Item#getCraftingRemainder()} or air if it doesn't exist.
     * </p>
     *
     * @param player PlayerEntity
     * @param hand   Hand
     */
    public static void consumeItem(Player player, InteractionHand hand) {
        final var stack = player.getItemInHand(hand);
        final var item = stack.getItem();
        final var hasRemainder = !item.getRecipeRemainder(stack).isEmpty();
        var remainder = hasRemainder ? item.getRecipeRemainder(stack) : ItemStack.EMPTY;
        if (item instanceof PotionItem && !hasRemainder) {
            remainder = new ItemStack(Items.GLASS_BOTTLE);
        }
        ItemUtil.replaceItem(player, hand, remainder);
    }

    /**
     * <p>
     * Replaces the Item at the given hand of the player, or nothing
     * if the player is on creative mode.
     * </p>
     * If the player inventory is full, the stack is going to be dropped
     * into the {@link net.minecraft.world.level.Level}
     *
     * @param player   PlayerEntity
     * @param hand     Hand
     * @param toInsert ItemStack to insert
     */
    public static void replaceItem(Player player, InteractionHand hand, ItemStack toInsert) {
        // Flag that determines whether we should insert the item into the PlayerInventory
        // Which means we decremented a stack (only 1 per call).
        // Or false if the stack only had 1 count and therefore was consumed/replaced by the passed stack
        var insert = false;
        // Stack in Hand
        final var stack = player.getItemInHand(hand);
        if (!player.isCreative()) {
            // If there is only 1 count
            // We want to replace it with the passed stack
            if (stack.getCount() == 1) {
                player.setItemInHand(hand, toInsert);
            } else {
                // Otherwise, we remove one and update the insert flag.
                stack.shrink(1);
                insert = true;
            }
            if (insert) {
                // Just in case the player inventory is full
                if (!player.getInventory().add(toInsert)) {
                    // We drop the item into the World.
                    player.drop(toInsert, false, true);
                }
            }
        }
    }


    /**
     * Checks that stackA and stackB are equal
     * For example to merge items on shift-click
     *
     * @param stackA   ItemStack
     * @param stackB   ItemStack
     * @param matchNBT boolean flag for whether you want to check the items are completely equal and not just partially .
     * @return Whether the given stacks are equal or not.
     */
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
            return ItemStack.isSameItemSameComponents(stackA, stackB);
        }
    }

    public static void addItemToInventoryAndConsume(Player player, InteractionHand hand, ItemStack toAdd) {
        boolean shouldAdd = false;
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getCount() == 1) {
            player.setItemInHand(hand, toAdd);
        } else {
            stack.shrink(1);
            shouldAdd = true;
        }
        if (shouldAdd) {
            if (!player.getInventory().add(toAdd)) {
                player.drop(toAdd, false, true);
            }
        }
    }
}
