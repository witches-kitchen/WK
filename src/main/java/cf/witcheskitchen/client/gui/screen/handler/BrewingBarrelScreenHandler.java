package cf.witcheskitchen.client.gui.screen.handler;

import cf.witcheskitchen.common.blocks.entity.BrewingBarrelBlockEntity;
import cf.witcheskitchen.common.registry.WKScreenHandlerTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;

public class BrewingBarrelScreenHandler extends WKScreenHandler {

    private final PropertyDelegate delegate;

    public BrewingBarrelScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(6), new ArrayPropertyDelegate(1));
    }

    public BrewingBarrelScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate delegate) {
        super(WKScreenHandlerTypes.BREWING_BARREL, syncId, playerInventory, inventory);
        this.delegate = delegate;
        super.addProperties(delegate);
        this.builder()
                .playerSetup()
                // First Row
                .input(0, 56, 30)
                .input(1, 75, 30)
                .input(2, 94, 30)
                // Second Row
                .input(3, 56, 49)
                .input(4, 75, 49)
                .input(5, 94, 49)
                .build();
    }

    public boolean isFermenting() {
        return this.delegate.get(0) > 0;
    }

    public int getProgressScaled(int scale) {
        return this.delegate.get(0) * scale / BrewingBarrelBlockEntity.MAX_TIME;

    }

}
