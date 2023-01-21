package cf.witcheskitchen.api.client.screen;

import cf.witcheskitchen.common.util.ItemUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.apache.commons.lang3.Range;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * WitchesKitchen's ScreenHandler base for devices.
 * <p>
 * It defines a default {@link #transferSlot(PlayerEntity, int)} implementation
 * which should work for the most of devices.
 * </p>
 *
 * <p>
 * You can build the slots by chaining {@link #builder} methods.
 * </p>
 */
public abstract class WKScreenHandler extends ScreenHandler {

    protected final List<Range<Integer>> playerRanges;
    protected final List<Range<Integer>> blockEntityRanges;
    private final PlayerInventory playerInventory;
    private final Inventory inventory;
    private final ScreenHandlerBuilder builder;

    protected WKScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(type, syncId);
        this.playerInventory = playerInventory;
        this.inventory = inventory;
        this.playerRanges = new ArrayList<>();
        this.blockEntityRanges = new ArrayList<>();
        this.builder = new ScreenHandlerBuilder(this);
        this.inventory.onOpen(playerInventory.player);
    }

    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack originalStack = ItemStack.EMPTY;
        final Slot slot = this.getSlot(index);
        if (slot != null && slot.hasStack()) {
            final ItemStack stackInSlot = slot.getStack();
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
            slot.onQuickTransfer(stackInSlot, originalStack);
            if (stackInSlot.getCount() <= 0) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
            if (stackInSlot.getCount() == originalStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTakeItem(player, stackInSlot);
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
            final ItemStack stackInSlot = slot.getStack();
            int maxCount = Math.min(stackToShift.getMaxCount(), slot.getMaxItemCount());

            if (!stackToShift.isEmpty() && slot.canInsert(stackToShift)) {
                if (ItemUtil.areItemsEqual(stackInSlot, stackToShift, true)) {
                    // Got 2 stacks that need merging
                    final int space = maxCount - stackInSlot.getCount();
                    if (space > 0) {
                        int transferAmount = Math.min(space, stackToShift.getCount());
                        stackInSlot.increment(transferAmount);
                        stackToShift.decrement(transferAmount);
                    }
                }
            }
        }

        // If not lets go find the next free slot to insert our remaining stack
        for (int slotIndex = start; stackToShift.getCount() > 0 && slotIndex < end; slotIndex++) {
            final Slot slot = this.slots.get(slotIndex);
            final ItemStack stackInSlot = slot.getStack();

            if (stackInSlot.isEmpty() && slot.canInsert(stackToShift)) {
                int maxCount = Math.min(stackToShift.getMaxCount(), slot.getMaxItemCount());

                int moveCount = Math.min(maxCount, stackToShift.getCount());
                ItemStack moveStack = stackToShift.copy();
                moveStack.setCount(moveCount);
                slot.setStack(moveStack);
                stackToShift.decrement(moveCount);
            }
        }

        //If we moved some, but still have more left over lets try again
        if (!stackToShift.isEmpty() && stackToShift.getCount() != inCount) {
            transferStack(stackToShift, start, end);
        }

        return stackToShift.getCount() != inCount;
    }

    @Override
    protected boolean insertItem(ItemStack stack, int startIndex, int endIndex, boolean fromLast) {
        throw new UnsupportedOperationException("Don't use this shit");
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        this.inventory.onClose(player);
    }

    public ScreenHandlerBuilder builder() {
        return builder;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    public void addPlayerRange(final Range<Integer> range) {
        this.playerRanges.add(range);
    }

    public void addContainerRange(final Range<Integer> range) {
        this.blockEntityRanges.add(range);
    }

    public PlayerInventory getPlayerInventory() {
        return playerInventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public Slot addSlot(Slot slot) {
        return super.addSlot(slot);
    }
}
