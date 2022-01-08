package cf.witcheskitchen.common.blocks.entity;

import cf.witcheskitchen.api.InventoryManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
import net.minecraft.world.World;

import java.util.Random;

public class WKDeviceBlockEntity extends BlockEntity implements BlockEntityTicker<WKDeviceBlockEntity>, Inventory {

    private final InventoryManager<WKDeviceBlockEntity> manager;
    private boolean isUnderWater;

    public WKDeviceBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.manager = new InventoryManager<>(this, 4);
    }

    // Server-side Tick
    @Override
    public void tick(World world, BlockPos pos, BlockState state, WKDeviceBlockEntity blockEntity) {
        if (this.world != null && !this.world.isClient) {
            if (state.getBlock() instanceof Waterloggable) {
                this.isUnderWater = !state.getFluidState().isEmpty();
            }
        }
    }

    // Client-side Tick
    @Environment(EnvType.CLIENT)
    public void onClientTick(World world, BlockPos pos, BlockState state, Random random) {

    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.manager.read(nbt);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        this.manager.write(nbt);
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

    public boolean isUnderWater() {
        return isUnderWater;
    }

    public InventoryManager<WKDeviceBlockEntity> getMainManager() {
        return manager;
    }
}
