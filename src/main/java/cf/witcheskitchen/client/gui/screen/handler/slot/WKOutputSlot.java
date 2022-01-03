package cf.witcheskitchen.client.gui.screen.handler.slot;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class WKOutputSlot extends WKSlot {

    public WKOutputSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y, stack -> false);
    }

    @Override
    protected void onCrafted(ItemStack stack) {
        super.onCrafted(stack);
        //TODO: Drop experience ?
    }
}
