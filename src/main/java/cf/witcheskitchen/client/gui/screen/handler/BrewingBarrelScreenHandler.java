package cf.witcheskitchen.client.gui.screen.handler;

import cf.witcheskitchen.common.registry.WKScreenHandlerTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;

public class BrewingBarrelScreenHandler extends WKScreenHandler {

    public BrewingBarrelScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(6));
    }

    public BrewingBarrelScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(WKScreenHandlerTypes.BREWING_BARREL, syncId, playerInventory, inventory);
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
}
