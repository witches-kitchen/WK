package cf.witcheskitchen.api.block.entity;

import cf.witcheskitchen.api.util.InventoryManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class WKBlockEntityWithInventory extends WKBlockEntity implements Container {

    protected final InventoryManager<WKBlockEntityWithInventory> manager;

    public WKBlockEntityWithInventory(BlockEntityType<?> type, BlockPos pos, BlockState state, int size) {
        super(type, pos, state);
        this.manager = new InventoryManager<>(this, size);
    }

    @Override
    protected void loadAdditional(ValueInput data) {
        super.loadAdditional(data);
        this.manager.readData(data);
    }

    @Override
    protected void saveAdditional(ValueOutput data) {
        super.saveAdditional(data);
        this.manager.writeData(data);
    }

    @Override
    public int getContainerSize() {
        return this.manager.getContainerSize();
    }

    @Override
    public boolean isEmpty() {
        return this.manager.isEmpty();
    }

    @Override
    public ItemStack getItem(int slot) {
        return this.manager.getItem(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return this.manager.removeItem(slot, amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return this.manager.removeItemNoUpdate(slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        this.manager.setItem(slot, stack);
    }

    @Override
    public boolean stillValid(Player player) {
        return this.manager.stillValid(player);
    }

    @Override
    public void clearContent() {
        this.manager.clearContent();
    }
}
