package cf.witcheskitchen.api.fluid;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

/**
 * WitchesKitchen FluidTank implementation of a {@link IFluidStorage}.
 *
 * <p>The FluidTank is used to make easier interactions with fluid containers.</p>
 *
 * <p>It is important to notice that each FluidTank instance can only hold
 * one {@link FluidStack}, that can mutate the current data, very similar to
 * {@link net.minecraft.world.item.ItemStack} <p>
 */
public class FluidTank implements IFluidStorage {
    // TODO: is this what we want?
    public static final Codec<FluidTank> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
                Codec.INT
                    .fieldOf("capacity")
                    .forGetter(FluidTank::getCapacity),
                FluidStack.CODEC
                    .fieldOf("stack")
                    .forGetter(FluidTank::getStack)
            )
            .apply(instance, FluidTank::fromCodec)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, FluidTank> PACKET_CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_INT, FluidTank::getCapacity,
        FluidStack.PACKET_CODEC, FluidTank::getStack,
        FluidTank::fromCodec
    );
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
    @NotNull
    protected FluidStack stack = FluidStack.EMPTY;

    public FluidTank(int capacity) {
        this(capacity, filter -> true);
    }


    // Default Constructor
    public FluidTank(int capacity, Predicate<FluidStack> filterValidator) {
        this.capacity = capacity;
        this.filterValidator = filterValidator;
    }

    public static FluidTank fromCodec(int capacity, FluidStack stack) {
        var tank = new FluidTank(capacity);
        tank.stack = stack;
        return tank;
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
     * Must be read from {@link net.minecraft.world.level.block.entity.BlockEntity#loadAdditional(ValueInput)}
     *
     * @param data {@link CompoundTag}
     */
    @Override
    public void readStorage(@NotNull ValueInput data) {
        this.stack = FluidStack.fromData(data);
    }

    /**
     * Writes the content of this tank.
     * Must be written from {@link net.minecraft.world.level.block.entity.BlockEntity#saveAdditional(net.minecraft.world.level.storage.ValueOutput)}
     *
     * @return {@link CompoundTag} that contains the data of the {@link FluidStack} of the tank
     */
    @Override
    public void writeStorage(@NotNull ValueOutput data) {
        this.stack.writeToData(data);
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
     * @return The internal {@link CompoundTag} of the {@link FluidStack} in the tank
     */
    public CompoundTag getInternalNbt() {
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
    @NotNull
    public FluidStack getStack() {
        return stack;
    }
}
