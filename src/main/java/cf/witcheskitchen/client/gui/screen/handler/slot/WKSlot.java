package cf.witcheskitchen.client.gui.screen.handler.slot;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

import java.util.function.Predicate;

public class WKSlot extends Slot {

    private final Predicate<ItemStack> canInsert;

    public WKSlot(Inventory inventory, int index, int x, int y, Predicate<ItemStack> canInsert) {
        super(inventory, index, x, y);
        this.canInsert = canInsert;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return this.canInsert.test(stack);
    }
}
