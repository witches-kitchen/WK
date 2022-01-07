package cf.witcheskitchen.common.blocks.entity;

import cf.witcheskitchen.api.InventoryManager;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Objects;
import java.util.function.Predicate;

public abstract class WKDeviceBlockEntity extends BlockEntity implements BlockEntityTicker<WKDeviceBlockEntity>, Inventory {

    private final InventoryManager<WKDeviceBlockEntity> manager;
    private boolean isUsable;

    public WKDeviceBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int size) {
        super(type, pos, state);
        this.manager = new InventoryManager<>(this, size);
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, WKDeviceBlockEntity blockEntity) {
        if (state.getBlock() instanceof Waterloggable) {
            this.isUsable = state.getFluidState().isEmpty();
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.manager.read(nbt);
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
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
        if (this.manager.canPlayerUse(player)) {
            return this.canUse().test(player);
        } else {
            return false;
        }
    }

    protected Predicate<PlayerEntity> canUse() {
        return player -> (Objects.requireNonNull(this.getWorld()).getBlockEntity(this.getPos()) == this && player.getPos().distanceTo(Vec3d.of(this.getPos())) < 16);
    }

    @Override
    public void clear() {
        this.manager.clear();
    }

    public boolean isUsable() {
        return isUsable;
    }
}
