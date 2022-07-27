package cf.witcheskitchen.api.block.entity;

import cf.witcheskitchen.common.util.InventoryManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;

public class WKDeviceBlockEntity extends BlockEntity implements BlockEntityTicker<WKDeviceBlockEntity>, Inventory {

    protected final InventoryManager<WKDeviceBlockEntity> manager;

    public WKDeviceBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int size) {
        super(type, pos, state);
        this.manager = new InventoryManager<>(this, size);
    }

    // Server-side Tick
    @Override
    public void tick(World world, BlockPos pos, BlockState state, WKDeviceBlockEntity blockEntity) {

    }

    // Client-side Tick
    @Environment(EnvType.CLIENT)
    public void onClientTick(World world, BlockPos pos, BlockState state, RandomGenerator random) {

    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.manager.readNbt(nbt);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        this.manager.writeNbt(nbt);
    }


    public void markDirty(boolean syncToClient) {
        this.markDirty();
        if (this.world != null && syncToClient) {
            super.world.updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
        }
    }

    @Override
    public void markDirty() {
        super.markDirty();
    }


    @Override
    public int size() {
        return this.manager.size();
    }

    @Override
    public boolean isEmpty() {
        return this.manager.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.manager.getStack(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return this.manager.removeStack(slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return this.manager.removeStack(slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.manager.setStack(slot, stack);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return this.manager.canPlayerUse(player);
    }

    @Override
    public void clear() {
        this.manager.clear();
    }

}
