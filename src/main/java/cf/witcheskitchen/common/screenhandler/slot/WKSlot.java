package cf.witcheskitchen.common.screenhandler.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public class WKSlot extends Slot {

    private final Predicate<ItemStack> canInsert;

    public WKSlot(Container inventory, int index, int x, int y, Predicate<ItemStack> canInsert) {
        super(inventory, index, x, y);
        this.canInsert = canInsert;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return this.canInsert.test(stack);
    }
}
