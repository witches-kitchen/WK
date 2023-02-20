package cf.witcheskitchen.api.block.entity;

import cf.witcheskitchen.api.util.InventoryManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;

public class WKBlockEntityWithInventory extends WKBlockEntity implements Inventory {

    protected final InventoryManager<WKBlockEntityWithInventory> manager;

    public WKBlockEntityWithInventory(BlockEntityType<?> type, BlockPos pos, BlockState state, int size) {
        super(type, pos, state);
        this.manager = new InventoryManager<>(this, size);
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
