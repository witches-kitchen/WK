package cf.witcheskitchen.common.blocks.entity;

import cf.witcheskitchen.api.fluid.FluidStack;
import cf.witcheskitchen.api.fluid.FluidTank;
import cf.witcheskitchen.api.fluid.IStorageHandler;
import cf.witcheskitchen.api.fluid.WKFluidAPI;
import cf.witcheskitchen.common.registry.WKBlockEntityTypes;
import cf.witcheskitchen.common.registry.WKTags;
import cf.witcheskitchen.common.util.TimeHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WitchesCauldronBlockEntity extends WKDeviceBlockEntity implements IStorageHandler {

    private int color;
    private static final int TICKS_TO_BOIL = TimeHelper.toTicks(5);
    private final FluidTank tank = new FluidTank(WKFluidAPI.BUCKET_VOLUME);
    private int ticksHeated;

    public WitchesCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(WKBlockEntityTypes.WITCHES_CAULDRON, pos, state, 3);
        this.color = 0x3f76e4;
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, WKDeviceBlockEntity blockEntity) {
        super.tick(world, pos, state, blockEntity);
        final BlockState belowState = world.getBlockState(pos.down());
        boolean sync = false;
        if (belowState.isIn(WKTags.HEATS_CAULDRON) && this.hasWater()) {
            if (this.ticksHeated < TICKS_TO_BOIL) {
                this.ticksHeated++;
                if (this.ticksHeated == TICKS_TO_BOIL) {
                    sync = true;
                }
            }
        } else if (this.ticksHeated != 0) {
            this.ticksHeated = 0;
            sync = true;
        }
        if (sync) {
            this.markDirty(true);
        }
    }

    @Environment(EnvType.CLIENT)
    public double getPercentFilled() {
        return ((((double) tank.getFluidAmount() / this.tank.getCapacity())));
    }


    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.tank.readStorage(nbt.getCompound("Tank"));
        this.ticksHeated = nbt.getInt("TicksHeated");
    }


    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.put("Tank", tank.writeStorage());
        nbt.putInt("TicksHeated", this.ticksHeated);
    }

    @Environment(EnvType.CLIENT)
    public int getColor() {
        return this.color;
//        if (this.hasWater()) {
//            return -1;//this.tank.getInternalNbt().getInt("Color";
//        } else {
//            return -1;
//        }
    }
    public boolean isBoiling() {
        return this.hasWater() && this.ticksHeated == TICKS_TO_BOIL;
    }

    public boolean hasWater() {
        return !this.tank.isEmpty();
    }

    public boolean addItem(World world) {
        if (!world.isClient) {
            NbtCompound data;
            if (tank.getStack().isFluidEqualTo(Fluids.WATER)) {

            }
        }
        return false;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        final NbtCompound data = super.toInitialChunkDataNbt();
        writeNbt(data);
        return data;
    }

    @Override
    public int fill(FluidStack stack, Direction side) {
        int fillAmount;
        FluidStack filledStack;
        if (this.tank.isEmpty()) {
            // returns the filled amount of fluid
            fillAmount = this.tank.fill(stack, side);
            filledStack = this.tank.getStack();
            if (!filledStack.isEmpty()) {
                filledStack.setNbt(stack.getNbt() == null ? new NbtCompound() : stack.getNbt().copy());
                this.markDirty(false);
            }
            return fillAmount;
            // Otherwise, if both are valid stacks
            // let's fill the remaining space
        } else if (stack.isEqualTo(this.tank.getStack())) {
            fillAmount = this.tank.fill(stack, side); // fills remaining space of the tank
            filledStack = this.tank.getStack();
            if (!filledStack.isEmpty()) {
                this.markDirty(false);
            }
            return fillAmount;
        } else {
            return 0;
        }
    }

    @Override
    public boolean canFill(FluidStack stack, Direction side) {
        return true;
    }

    @NotNull
    @Override
    public FluidStack getStackForTank(int tank) {
        if (tank == 0) {
            return this.tank.getStack();
        } else {
            return FluidStack.EMPTY;
        }
    }

    @NotNull
    @Override
    public FluidStack drain(FluidStack stack, Direction side) {
        return this.tank.drain(stack, side);
    }

    @NotNull
    @Override
    public FluidStack drain(int maxAmount, Direction side) {
        return this.tank.drain(maxAmount, side);
    }


}
