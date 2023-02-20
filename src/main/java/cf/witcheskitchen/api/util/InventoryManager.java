package cf.witcheskitchen.api.util;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Vec3d;

import java.util.function.Predicate;

/**
 * Inventory Manager.
 * <p>
 * The standard way to store items in a BlockEntity is to make it an Inventory.
 * Implementing Inventory is fairly simple, but is tedious and prone to error,
 * so here is an Inventory Manager of it which only requires giving it an initial size
 * or an already existing DefaultedList of ItemStacks.
 */
public class InventoryManager<T extends BlockEntity> implements Inventory {

    /**
     * Parent BlockEntity, which is going to create the inventory
     * and keep track of all our items.
     */
    private final T blockEntity;
    /**
     * A DefaultedList keeps a list of objects that can't be null,
     * instead some "default value" is defined.
     * <p>
     * A DefaultedList of ItemStack can be used as an easy way to store
     * stacks of items as it can be set for default as an EMPTY stack,
     * which is the proper way of saying that there is no item in the slot.
     */
    private final DefaultedList<ItemStack> inventory;

    /**
     * Creates a new Inventory Manager and DefaultedList of ItemStack with the specified size
     *
     * @param size Integer (Inventory size)
     */
    public InventoryManager(T blockEntity, int size) {
        this(blockEntity, DefaultedList.ofSize(size, ItemStack.EMPTY));
    }

    /**
     * Creates a new Inventory Manager and DefaultedList of ItemStack
     * from an already existing one.
     *
     * @param inventory DefaultedList of ItemStack
     */
    public InventoryManager(T blockEntity, DefaultedList<ItemStack> inventory) {
        this.inventory = inventory;
        this.blockEntity = blockEntity;
    }

    /**
     * Reads the inventory data from {@link NbtCompound}
     * of your {@link BlockEntity}.This is typically invoked
     * when you load a {@link net.minecraft.world.World}
     * or you open the container.
     *
     * @param data {@link NbtCompound} from your {@link BlockEntity} readNbt().
     */
    public void readNbt(NbtCompound data) {
        this.clear();
        Inventories.readNbt(data, this.inventory);
    }

    /**
     * Writes the inventory data to {@link NbtCompound}.
     * This is typically invoked when you exit a {@link net.minecraft.world.World}
     * or the {@link Inventory} changes.
     *
     * @param data {@link NbtCompound} from your {@link BlockEntity} writeNbt().
     */
    public void writeNbt(NbtCompound data) {
        Inventories.writeNbt(data, this.inventory);
    }

    /**
     * Returns the size of our DefaultedList of Stacks.
     * In other words the Inventory size.
     */
    @Override
    public int size() {
        return this.inventory.size();
    }

    /**
     * Checks if the inventory is empty by iterating over all the elements.
     *
     * @return whether this Inventory has only EMPTY stacks.
     */
    @Override
    public boolean isEmpty() {
        for (int i = 0; i < this.size(); i++) {
            if (!this.getStack(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Retrieves an element from our DefaultedList.
     *
     * @param index Index of the stack you want to get.
     * @return The stack (ItemStack) in that specific index.
     */
    @Override
    public ItemStack getStack(int index) {
        return this.inventory.get(index);
    }

    /**
     * Removes an element from our DefaultedList of Stacks.
     *
     * @param index  Position of the stack you want to remove.
     * @param amount How many stacks you would like to remove.
     *               If there are fewer stacks in the slot than what are requested,
     *               then it takes all items in that slot.
     */

    @Override
    public ItemStack removeStack(int index, int amount) {
        final ItemStack stack = Inventories.splitStack(this.inventory, index, amount);
        if (!stack.isEmpty()) {
            markDirty();
        }
        return stack;
    }

    /**
     * Removes all stacks of the DefaultedList
     * at the given position.
     *
     * @param index The slot to remove from.
     */
    @Override
    public ItemStack removeStack(int index) {
        return Inventories.removeStack(this.inventory, index);
    }

    /**
     * Replaces from the DefaultedList the stack at the given position
     * with the provided stack.
     *
     * @param index Position of the stack
     * @param stack The replacing ItemStack. If the stack is too big for
     *              this inventory ({@link Inventory#getMaxCountPerStack()},
     *              it gets resized to this inventory's maximum amount.
     */

    @Override
    public void setStack(int index, ItemStack stack) {
        this.inventory.set(index, stack);
        if (stack.getCount() > this.getMaxCountPerStack()) {
            stack.setCount(this.getMaxCountPerStack());
        }
        markDirty();
    }

    /**
     * Marks the state as dirty.
     * Must be called after changes in the inventory, so that the game can properly save
     * the inventory contents and notify neighboring blocks of inventory changes.
     */
    @Override
    public void markDirty() {
        this.blockEntity.markDirty();
    }

    /**
     * @return Whether the player can access the inventory
     */
    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return canUse().test(player);
    }

    /**
     * Clears the DefaultedList (inventory).
     */
    @Override
    public void clear() {
        this.inventory.clear();
    }

    /**
     * Parent BlockEntity
     *
     * @return BlockEntity
     */
    public T getContainer() {
        return this.blockEntity;
    }

    /**
     * Checks that the {@link BlockEntity} is valid and corresponds to the given parent,
     * and that the {@link PlayerEntity} is at the required distance to open the container.
     *
     * @return a {@link Predicate} of {@link PlayerEntity}.
     */
    protected Predicate<PlayerEntity> canUse() {
        return player -> player.getWorld().getBlockEntity(this.getContainer().getPos()) == this.getContainer() && player.getPos().distanceTo(Vec3d.of(this.getContainer().getPos())) < 16;
    }

    /**
     * Getter for the {@link DefaultedList} of {@link ItemStack}
     * that this instance of the manager is currently using.
     *
     * @return a reference to the {@link DefaultedList}.
     */
    public DefaultedList<ItemStack> getStacks() {
        return this.inventory;
    }

    public int findAnyEmptySlot() {
        return InventoryHelper.findAnyIndexOf(this, ItemStack.EMPTY);
    }
}
