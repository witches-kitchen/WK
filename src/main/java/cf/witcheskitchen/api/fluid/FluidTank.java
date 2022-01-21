package cf.witcheskitchen.api.fluid;

import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.NotNull;

public class FluidTank implements IFluidStorage {

    private final int capacity;
    @NotNull
    private FluidStack stack;

    public FluidTank(int capacity) {
        this.capacity = capacity;
        this.stack = FluidStack.EMPTY;
    }

    public void increase(int i) {
        this.stack.setAmount(this.getAmount() + i);
    }
    @NotNull
    public FluidStack getStack() {
        return this.stack;
    }

    @Override
    public int fill(FluidStack stack) {
        if (stack.isEmpty()) {
            return 0;
        } else if (this.stack.isEmpty()) {
            this.stack = new FluidStack(stack, Math.min(this.capacity, stack.getAmount()));
            // markDirty();
            return this.stack.getAmount();
        } else if (!stack.isEqualTo(this.stack)) {

        }
        return 0;
    }

    @NotNull
    @Override
    public FluidStack drain(int amount) {
        return null;
    }

    @NotNull
    @Override
    public FluidStack drain(FluidStack stack) {
        return null;
    }

    @Override
    public int getAmount() {
        return this.stack.getAmount();
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    public void readNbt(NbtCompound nbt) {
        System.out.println("========================READING NBT=================================");
        NbtCompound tankData = nbt.getCompound("Tank");
        this.stack = FluidStack.fromNbt(tankData);
    }

    public NbtCompound writeNbt(NbtCompound nbt) {
        System.out.println("========================WRITING NBT=================================");
        NbtCompound tankData = this.stack.writeNbt(nbt);
        nbt.put("Tank", tankData);
        return nbt;

    }

}
