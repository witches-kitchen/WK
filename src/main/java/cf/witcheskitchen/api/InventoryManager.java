package cf.witcheskitchen.api;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.collection.DefaultedList;

/**
 *  Inventory Manager.
 *
 *  The standard way to store items in a BlockEntity is to make it an Inventory.
 *  Implementing Inventory is fairly simple, but is tedious and prone to error,
 *  so here is an Inventory Manager of it which only requires giving it an initial size
 *  or an already existing DefaultedList of ItemStacks.
 *
 */
public class InventoryManager <T extends BlockEntity> implements Inventory {

    /**
     * A DefaultedList keeps a list of objects that can't be null,
     * instead some "default value" is defined.
     *
     * A DefaultedList of ItemStack can be used as an easy way to store
     * stacks of items as it can be set for default as an EMPTY stack,
     * which is the proper way of saying that there is no item in the slot.
     *
     */
    private DefaultedList<ItemStack> inventory;


    /**
     * Parent BlockEntity, which is going to create the inventory
     * and keep track of all our items.
     */
    private final T blockEntity;

    /**
     * Creates a new Inventory Manager and DefaultedList of ItemStack with the specified size
     * @param size Integer (Inventory size)
     */
    public InventoryManager(T blockEntity, int size) {
        this(blockEntity, DefaultedList.ofSize(size, ItemStack.EMPTY));
    }

    /**
     * Creates a new Inventory Manager and DefaultedList of ItemStack
     * from an already existing one.
     * @param inventory DefaultedList of ItemStack
     */
    public InventoryManager(T blockEntity, DefaultedList<ItemStack> inventory) {
        this.inventory = inventory;
        this.blockEntity = blockEntity;
    }


    public void read(NbtCompound data) {
        read(data, "Items");
    }

    public void read(NbtCompound data, String tag) {
        deserializeNBT(data.getCompound(tag));
        markDirty();
    }

    public void write(NbtCompound data) {
        write(data, "Items");
    }

    public void write(NbtCompound data, String tag) {
        data.put(tag, serializeNBT());
    }

    public NbtElement serializeNBT() {
        final NbtCompound data = new NbtCompound();
        Inventories.writeNbt(data, this.inventory);
        return data;
    }

    public void deserializeNBT(NbtCompound tag) {
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(tag, this.inventory);
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
     * @param index Index of the stack you want to get.
     * @return The stack (ItemStack) in that specific index.
     */
    @Override
    public ItemStack getStack(int index) {
        return this.inventory.get(index);
    }
    /**
     * Removes an element from our DefaultedList of Stacks.
     * @param index Position of the stack you want to remove.
     * @param amount How many stacks you would like to remove.
     * If there are fewer stacks in the slot than what are requested,
     * then it takes all items in that slot.
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
     * @param index The slot to remove from.
     */
    @Override
    public ItemStack removeStack(int index) {
        return Inventories.removeStack(this.inventory, index);
    }

    /**
     * Replaces from the DefaultedList the stack at the given position
     * with the provided stack.
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
        return true;
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
     * @return BlockEntity
     */
    public T getContainer() {
        return this.blockEntity;
    }

    public DefaultedList<ItemStack> getStacks() {
        return this.inventory;
    }
}
