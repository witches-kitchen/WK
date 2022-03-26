package cf.witcheskitchen.api.fluid;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Predicate;

/**
 * WitchesKitchen FluidTank implementation of a {@link IFluidStorage}.
 *
 * <p>The FluidTank is used to make easier interactions with fluid containers.</p>
 *
 * <p>It is important to notice that each FluidTank instance can only hold
 * one {@link FluidStack}, that can mutate the current data, very similar to
 * {@link net.minecraft.item.ItemStack} <p>
 */
public class FluidTank implements IFluidStorage {

    /**
     * The <b>Max</b> capacity of fluid this instance of Tank can hold (in MilliBuckets).
     * This value <b>CANNOT BE CHANGED</b> after the tank is created.
     */
    protected final int capacity;
    /**
     * Gives you the flexibility of filter a condition
     * for whether tank can be filled
     */
    protected final Predicate<FluidStack> filterValidator;
    /**
     * Empty {@link FluidStack} for empty Tanks
     * As annotated, an empty {@link FluidStack} is never null.
     */
    @Nonnull
    protected FluidStack stack = FluidStack.EMPTY;


    public FluidTank(int capacity) {
        this(capacity, filter -> true);
    }

    // Default Constructor
    public FluidTank(int capacity, Predicate<FluidStack> filterValidator) {
        this.capacity = capacity;
        this.filterValidator = filterValidator;
    }

    /**
     * Fills the current tank.
     * Look at {@link IFluidStorage} for more info.
     *
     * @param stack {@link FluidStack} to insert
     * @param side  {@link Direction} where is coming from
     * @return The amount of fluid inserted.
     */
    @Override
    public int fill(FluidStack stack, Direction side) {
        if (!canFill(stack)) {
            return 0;
        } else if (this.stack.isEmpty()) {
            this.stack = FluidStack.fromStack(stack, Math.min(stack.getAmount(), this.capacity));
            // TODO: markDirty
            return this.stack.getAmount();
        } else {
            final int filledAmount;
            if (stack.getAmount() < this.getFreeSpace()) {
                this.grow(stack.getAmount());
                filledAmount = stack.getAmount();
            } else {
                final int remainingSpace = this.getFreeSpace();
                this.stack.setAmount(capacity);
                filledAmount = remainingSpace;
            }
            if (filledAmount > 0) {
                // TODO: markDirty
            }
            return filledAmount;
        }
    }

    /**
     * Drains the current tank.
     * Look at {@link IFluidStorage} for more info.
     *
     * @param amount Max amount of fluid it is requested to drain.
     * @param side   {@link Direction} where it is going to drain
     * @return The {@link FluidStack} drained.
     */
    @Override
    public @NotNull
    FluidStack drain(int amount, @Nullable Direction side) {
        final int drained = Math.min(this.stack.getAmount(), amount);
        FluidStack newStack = FluidStack.fromStack(this.stack, drained);
        if (drained > 0) {
            this.shrink(drained);
            //TODO: markDirty();
        }
        return newStack;
    }

    /**
     * Drains the current tank.
     * Look at {@link IFluidStorage} for more info.
     *
     * @param stack {@link FluidStack} that it is requested to drain.
     * @param side  {@link Direction} where it is going to drain
     * @return The {@link FluidStack} drained.
     */
    @NotNull
    @Override
    public FluidStack drain(FluidStack stack, @Nullable Direction side) {
        if (stack.isEmpty() || !stack.isEqualIgnoreNbt(this.stack)) {
            return FluidStack.EMPTY;
        }
        return drain(stack.getAmount(), side);
    }

    /**
     * Returns true if the tank can be filled with a specific {@link FluidStack}.
     * Used as a filter for filling.
     */
    public boolean canFill(FluidStack stack) {
        if (!stack.isEmpty()) {
            return true;
        } else if (this.stack.isEmpty()) {
            return true;
        } else {
            // stack and tank are not empty
            if (!this.filterValidator.test(stack)) {
                return false;
            } else if (!stack.isEqualIgnoreNbt(this.stack)) {
                return false;
            } else {
                return stack.getAmount() <= this.getFreeSpace();
            }
        }
    }

    /**
     * Reads the content of this tank.
     * Must be read from {@link net.minecraft.block.entity.BlockEntity#readNbt(NbtCompound)}
     *
     * @param data {@link NbtCompound}
     */
    @Override
    public void readStorage(@NotNull NbtCompound data) {
        this.stack = FluidStack.fromNbt(data);
    }

    /**
     * Writes the content of this tank.
     * Must be written from {@link net.minecraft.block.entity.BlockEntity#writeNbt(NbtCompound)}
     *
     * @return {@link NbtCompound} that contains the data of the {@link FluidStack} of the tank
     */
    @Override
    public NbtCompound writeStorage() {
        final NbtCompound data = new NbtCompound();
        this.stack.writeToNbt(data);
        return data;
    }

    /**
     * Increases the internal fluid of the {@link FluidStack} of the tank
     *
     * @param amount Amount of fluid (in MilliBuckets).
     */
    public void grow(int amount) {
        this.stack.setAmount(this.stack.getAmount() + amount);
    }

    /**
     * Decrements the internal fluid of the {@link FluidStack} of the tank
     *
     * @param amount Amount of fluid (in MilliBuckets).
     */
    public void shrink(int amount) {
        this.grow(-amount);
    }

    /**
     * @return The remaining space available to fill in the tank
     */
    public int getFreeSpace() {
        return this.capacity - this.stack.getAmount();
    }

    /**
     * @return The internal {@link NbtCompound} of the {@link FluidStack} in the tank
     */
    public NbtCompound getInternalNbt() {
        return this.stack.getNbt();
    }

    /**
     * @return The Amount of fluid the current {@link FluidStack} has.
     */
    @Override
    public int getFluidAmount() {
        return this.stack.getAmount();
    }

    /**
     * @return The <b>MAX</b> capacity of fluid (in MilliBuckets)
     * this current Tank can hold.
     */
    @Override
    public int getCapacity() {
        return capacity;
    }

    /**
     * @return Whether the internal storage (from {@link FluidStack}) is empty
     * and therefore, this tank can also be considered as empty.
     */
    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    /**
     * @return Current {@link FluidStack} in the tank
     */
    @Nonnull
    public FluidStack getStack() {
        return stack;
    }
}
