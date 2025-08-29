package cf.witcheskitchen.api.util;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

/**
 * Inventory Manager.
 * <p>
 * The standard way to store items in a BlockEntity is to make it an Inventory.
 * Implementing Inventory is fairly simple, but is tedious and prone to error,
 * so here is an Inventory Manager of it which only requires giving it an initial size
 * or an already existing DefaultedList of ItemStacks.
 */
public class InventoryManager<T extends BlockEntity> implements Container {

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
    private final NonNullList<ItemStack> inventory;

    /**
     * Creates a new Inventory Manager and DefaultedList of ItemStack with the specified size
     *
     * @param size Integer (Inventory size)
     */
    public InventoryManager(T blockEntity, int size) {
        this(blockEntity, NonNullList.withSize(size, ItemStack.EMPTY));
    }

    /**
     * Creates a new Inventory Manager and DefaultedList of ItemStack
     * from an already existing one.
     *
     * @param inventory DefaultedList of ItemStack
     */
    public InventoryManager(T blockEntity, NonNullList<ItemStack> inventory) {
        this.inventory = inventory;
        this.blockEntity = blockEntity;
    }

    /**
     * Reads the inventory data from {@link CompoundTag}
     * of your {@link BlockEntity}.This is typically invoked
     * when you load a {@link net.minecraft.world.level.Level}
     * or you open the container.
     *
     * @param data {@link ValueInput} from your {@link BlockEntity} readData().
     */
    public void readData(ValueInput data) {
        this.clearContent();
        ContainerHelper.loadAllItems(data, this.inventory);
    }

    /**
     * Writes the inventory data to {@link CompoundTag}.
     * This is typically invoked when you exit a {@link net.minecraft.world.level.Level}
     * or the {@link Container} changes.
     *
     * @param data {@link CompoundTag} from your {@link BlockEntity} writeData().
     */
    public void writeData(ValueOutput data) {
        ContainerHelper.saveAllItems(data, this.inventory);
    }

    /**
     * Returns the size of our DefaultedList of Stacks.
     * In other words the Inventory size.
     */
    @Override
    public int getContainerSize() {
        return this.inventory.size();
    }

    /**
     * Checks if the inventory is empty by iterating over all the elements.
     *
     * @return whether this Inventory has only EMPTY stacks.
     */
    @Override
    public boolean isEmpty() {
        for (int i = 0; i < this.getContainerSize(); i++) {
            if (!this.getItem(i).isEmpty()) {
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
    public ItemStack getItem(int index) {
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
    public ItemStack removeItem(int index, int amount) {
        final ItemStack stack = ContainerHelper.removeItem(this.inventory, index, amount);
        if (!stack.isEmpty()) {
            setChanged();
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
    public ItemStack removeItemNoUpdate(int index) {
        return ContainerHelper.takeItem(this.inventory, index);
    }

    /**
     * Replaces from the DefaultedList the stack at the given position
     * with the provided stack.
     *
     * @param index Position of the stack
     * @param stack The replacing ItemStack. If the stack is too big for
     *              this inventory ({@link Container#getMaxStackSize()},
     *              it gets resized to this inventory's maximum amount.
     */

    @Override
    public void setItem(int index, ItemStack stack) {
        this.inventory.set(index, stack);
        if (stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }
        setChanged();
    }

    /**
     * Marks the state as dirty.
     * Must be called after changes in the inventory, so that the game can properly save
     * the inventory contents and notify neighboring blocks of inventory changes.
     */
    @Override
    public void setChanged() {
        this.blockEntity.setChanged();
    }

    /**
     * @return Whether the player can access the inventory
     */
    @Override
    public boolean stillValid(Player player) {
        return canUse().test(player);
    }

    /**
     * Clears the DefaultedList (inventory).
     */
    @Override
    public void clearContent() {
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
     * and that the {@link Player} is at the required distance to open the container.
     *
     * @return a {@link Predicate} of {@link Player}.
     */
    protected Predicate<Player> canUse() {
        return player -> player.level().getBlockEntity(this.getContainer().getBlockPos()) == this.getContainer() && player.position().distanceTo(Vec3.atLowerCornerOf(this.getContainer().getBlockPos())) < 16;
    }

    /**
     * Getter for the {@link NonNullList} of {@link ItemStack}
     * that this instance of the manager is currently using.
     *
     * @return a reference to the {@link NonNullList}.
     */
    public NonNullList<ItemStack> getStacks() {
        return this.inventory;
    }

    public int findAnyEmptySlot() {
        return InventoryHelper.findAnyIndexOf(this, ItemStack.EMPTY);
    }
}
