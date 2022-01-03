package cf.witcheskitchen.client.gui.screen.handler;

import cf.witcheskitchen.client.gui.screen.builder.ScreenHandlerBuilder;
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

public abstract class WKScreenHandler extends ScreenHandler {

    private final PlayerInventory playerInventory;
    private final Inventory inventory;
    protected final List<Range<Integer>> playerRanges;
    protected final List<Range<Integer>> blockEntityRanges;
    private final ScreenHandlerBuilder builder;
    public WKScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(type, syncId);
        this.playerInventory = playerInventory;
        this.inventory = inventory;
        this.playerRanges = new ArrayList<>();
        this.blockEntityRanges = new ArrayList<>();
        this.builder = new ScreenHandlerBuilder(this);
    }

    @Override
    public ItemStack transferSlot(final PlayerEntity player, final int index) {
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
            if (this.transferItem(stackToShift, range.getMinimum(), range.getMaximum() + 1)) {
                return true;
            }
        }
        return false;
    }

    protected boolean transferToPlayer(final ItemStack stackToShift) {
        for (final Range<Integer> range : this.playerRanges) {
            if (this.transferItem(stackToShift, range.getMinimum(), range.getMaximum() + 1)) {
                return true;
            }
        }
        return false;
    }

    protected boolean transferItem(final ItemStack stack, final int start, final int end) {

        if (stack.isEmpty()) {
            return false;
        }
        final int stackCount = stack.getCount();

        if (stackCount <= 0) {
            return false;
        }
        //case where the container is empty
        for (int i = start; i < end; i++) {
            final Slot slotAt = this.getSlot(i);//container slots
            final ItemStack stackInSlot = slotAt.getStack();
            if (stackInSlot.isEmpty() && slotAt.canInsert(stack)) {
                final int maxCount = Math.min(stack.getMaxCount(), slotAt.getMaxItemCount());
                final int insertCount = Math.min(maxCount, stack.getCount());
                final ItemStack moveStack = stack.copy();
                moveStack.setCount(insertCount);
                slotAt.setStack(moveStack);
                stack.decrement(insertCount);
            }
        }

        //case where there container is not empty
        for (int i = start; i < end; i++) {
            final Slot slotAt = this.getSlot(i);//container slots
            final ItemStack stackInSlot = slotAt.getStack();
            if (slotAt.canInsert(stack) && ItemStack.areItemsEqual(stackInSlot, stack)) {
                //what is the max count for the stack
                final int maxCount = Math.min(stack.getMaxCount(), slotAt.getMaxItemCount());
                final int remainingSpace = (maxCount - stackInSlot.getCount());
                if (remainingSpace > 0) {
                    final int insertCount = Math.min(remainingSpace, stackInSlot.getCount());
                    stackInSlot.increment(insertCount);
                    stack.decrement(insertCount);
                }
            }
        }

        //If we moved some, but still have more left over lets try again
        if (!stack.isEmpty() && stack.getCount() != stackCount) {
            transferItem(stack, start, end);
        }
        return stack.getCount() != stackCount;//means we successfully inserted the stack
    }

    @Override
    protected boolean insertItem(ItemStack stack, int startIndex, int endIndex, boolean fromLast) {
        throw new UnsupportedOperationException("Don't use this shit");
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

    //was protected
    @Override
    public Slot addSlot(Slot slot) {
        return super.addSlot(slot);
    }
}
