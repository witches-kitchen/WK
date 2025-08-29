package cf.witcheskitchen.api.screen;

import cf.witcheskitchen.api.util.ItemUtil;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.Range;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * WitchesKitchen's ScreenHandler base for devices.
 * <p>
 * It defines a default {@link #transferSlot(Player, int)} implementation
 * which should work for the most of devices.
 * </p>
 *
 * <p>
 * You can build the slots by chaining {@link #builder} methods.
 * </p>
 */
public abstract class WKScreenHandler extends AbstractContainerMenu {

    protected final List<Range<Integer>> playerRanges;
    protected final List<Range<Integer>> blockEntityRanges;
    private final Inventory playerInventory;
    private final Container inventory;
    private final ScreenHandlerBuilder builder;

    protected WKScreenHandler(@Nullable MenuType<?> type, int syncId, Inventory playerInventory, Container inventory) {
        super(type, syncId);
        this.playerInventory = playerInventory;
        this.inventory = inventory;
        this.playerRanges = new ArrayList<>();
        this.blockEntityRanges = new ArrayList<>();
        this.builder = new ScreenHandlerBuilder(this);
        this.inventory.startOpen(playerInventory.player);
    }

    public ItemStack transferSlot(Player player, int index) {
        ItemStack originalStack = ItemStack.EMPTY;
        final Slot slot = this.getSlot(index);
        if (slot != null && slot.hasItem()) {
            final ItemStack stackInSlot = slot.getItem();
            originalStack = stackInSlot.copy();
            boolean shifted = false;
            for (final Range<Integer> range : this.playerRanges) {
                if (range.contains(index)) {
                    if (this.transferToBlockEntity(stackInSlot)) {
                        shifted = true;
                    }
                    break;
                }
            }
            if (!shifted) {
                for (final Range<Integer> range : this.blockEntityRanges) {
                    if (range.contains(index)) {
                        if (this.transferToPlayer(stackInSlot)) {
                            shifted = true;
                        }
                        break;
                    }
                }
            }
            slot.onQuickCraft(stackInSlot, originalStack);
            if (stackInSlot.getCount() <= 0) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (stackInSlot.getCount() == originalStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, stackInSlot);
        }
        return originalStack;
    }

    protected boolean transferToBlockEntity(final ItemStack stackToShift) {
        if (this.inventory == null) {
            return false;
        }
        for (final Range<Integer> range : this.blockEntityRanges) {
            if (this.transferStack(stackToShift, range.getMinimum(), range.getMaximum() + 1)) {
                return true;
            }
        }
        return false;
    }

    protected boolean transferToPlayer(final ItemStack stackToShift) {
        for (final Range<Integer> range : this.playerRanges) {
            if (this.transferStack(stackToShift, range.getMinimum(), range.getMaximum() + 1)) {
                return true;
            }
        }
        return false;
    }

    protected boolean transferStack(final ItemStack stackToShift, final int start, final int end) {
        if (stackToShift.isEmpty()) {
            return false;
        }
        int inCount = stackToShift.getCount();
        // First lets see if we have the same item in a slot to merge with
        for (int slotIndex = start; stackToShift.getCount() > 0 && slotIndex < end; slotIndex++) {
            final Slot slot = this.slots.get(slotIndex);
            final ItemStack stackInSlot = slot.getItem();
            int maxCount = Math.min(stackToShift.getMaxStackSize(), slot.getMaxStackSize());

            if (!stackToShift.isEmpty() && slot.mayPlace(stackToShift)) {
                if (ItemUtil.areItemsEqual(stackInSlot, stackToShift, true)) {
                    // Got 2 stacks that need merging
                    final int space = maxCount - stackInSlot.getCount();
                    if (space > 0) {
                        int transferAmount = Math.min(space, stackToShift.getCount());
                        stackInSlot.grow(transferAmount);
                        stackToShift.shrink(transferAmount);
                    }
                }
            }
        }

        // If not lets go find the next free slot to insert our remaining stack
        for (int slotIndex = start; stackToShift.getCount() > 0 && slotIndex < end; slotIndex++) {
            final Slot slot = this.slots.get(slotIndex);
            final ItemStack stackInSlot = slot.getItem();

            if (stackInSlot.isEmpty() && slot.mayPlace(stackToShift)) {
                int maxCount = Math.min(stackToShift.getMaxStackSize(), slot.getMaxStackSize());

                int moveCount = Math.min(maxCount, stackToShift.getCount());
                ItemStack moveStack = stackToShift.copy();
                moveStack.setCount(moveCount);
                slot.setByPlayer(moveStack);
                stackToShift.shrink(moveCount);
            }
        }

        //If we moved some, but still have more left over lets try again
        if (!stackToShift.isEmpty() && stackToShift.getCount() != inCount) {
            transferStack(stackToShift, start, end);
        }

        return stackToShift.getCount() != inCount;
    }

    @Override
    protected boolean moveItemStackTo(ItemStack stack, int startIndex, int endIndex, boolean fromLast) {
        throw new UnsupportedOperationException("Don't use this shit");
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.inventory.stopOpen(player);
    }

    public ScreenHandlerBuilder builder() {
        return builder;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.inventory.stillValid(player);
    }

    public void addPlayerRange(final Range<Integer> range) {
        this.playerRanges.add(range);
    }

    public void addContainerRange(final Range<Integer> range) {
        this.blockEntityRanges.add(range);
    }

    public Inventory getPlayerInventory() {
        return playerInventory;
    }

    public Container getInventory() {
        return inventory;
    }

    @Override
    public Slot addSlot(Slot slot) {
        return super.addSlot(slot);
    }
}
