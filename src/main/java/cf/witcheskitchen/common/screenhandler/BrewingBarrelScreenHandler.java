package cf.witcheskitchen.common.screenhandler;

import cf.witcheskitchen.api.screen.WKScreenHandler;
import cf.witcheskitchen.common.blockentity.BrewingBarrelBlockEntity;
import cf.witcheskitchen.common.registry.WKScreenHandlerTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;

public class BrewingBarrelScreenHandler extends WKScreenHandler {

    private final ContainerData delegate;

    public BrewingBarrelScreenHandler(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(6), new SimpleContainerData(1));
    }

    public BrewingBarrelScreenHandler(int syncId, Inventory playerInventory, Container inventory, ContainerData delegate) {
        super(WKScreenHandlerTypes.BREWING_BARREL, syncId, playerInventory, inventory);
        this.delegate = delegate;
        super.addDataSlots(delegate);
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

    @Override
    public ItemStack quickMoveStack(Player player, int fromIndex) {
        return ItemStack.EMPTY;
    }
}
