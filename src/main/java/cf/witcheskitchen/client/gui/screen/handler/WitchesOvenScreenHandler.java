package cf.witcheskitchen.client.gui.screen.handler;

import cf.witcheskitchen.common.registry.WKScreenHandlerTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;

public class WitchesOvenScreenHandler extends WKScreenHandler {

    public WitchesOvenScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(5));
    }

    public WitchesOvenScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(WKScreenHandlerTypes.WITCHES_OVEN, syncId, playerInventory, inventory);
        this.builder().playerSetup()
                .input(0, 44, 55)//fuel
                .input(1, 80, 55)//jars
                .input(2, 44, 19)//input
                .output(3, 116, 19)//output
                .input(4, 116, 55)//fume output
                .build();
    }
}