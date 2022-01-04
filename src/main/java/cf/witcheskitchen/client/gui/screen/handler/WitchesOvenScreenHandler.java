package cf.witcheskitchen.client.gui.screen.handler;

import cf.witcheskitchen.common.registry.WKScreenHandlerTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;

public class WitchesOvenScreenHandler extends WKScreenHandler {

    private final PropertyDelegate delegate;

    public WitchesOvenScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(5), new ArrayPropertyDelegate(4));
    }

    public WitchesOvenScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate delegate) {
        super(WKScreenHandlerTypes.WITCHES_OVEN, syncId, playerInventory, inventory);
        this.delegate = delegate;
        super.addProperties(this.delegate);
        this.builder().playerSetup()
                .input(0, 44, 55)//fuel
                .input(1, 44, 19)//input
                .input(2, 80, 55)//jars
                .output(3, 116, 19)//output
                .input(4, 116, 55)//fume output
                .build();
    }

    public boolean isBurning() {
        return this.delegate.get(0) > 0;
    }

    public int getBurnTimeScaled(int scale) {
        if (this.delegate.get(0) == 0) {
            return 0;
        }
        return this.delegate.get(0) * scale / delegate.get(1);
    }

    public int getProgressScaled(int scale) {
        if (this.delegate.get(0) > 0) {
            return this.delegate.get(2) * scale / this.delegate.get(3);
        }
        return 0;
    }
}