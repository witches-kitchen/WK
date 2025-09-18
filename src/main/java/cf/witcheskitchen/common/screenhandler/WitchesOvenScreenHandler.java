package cf.witcheskitchen.common.screenhandler;

import cf.witcheskitchen.api.screen.WKScreenHandler;
import cf.witcheskitchen.common.registry.WKScreenHandlerTypes;
import cf.witcheskitchen.common.registry.WKTags;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;

public class WitchesOvenScreenHandler extends WKScreenHandler {

    private final ContainerData delegate;

    public WitchesOvenScreenHandler(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(4), new SimpleContainerData(4));
    }

    public WitchesOvenScreenHandler(int syncId, Inventory playerInventory, Container inventory, ContainerData delegate) {
        super(WKScreenHandlerTypes.WITCHES_OVEN, syncId, playerInventory, inventory);
        this.delegate = delegate;
        super.addDataSlots(this.delegate);
        this.builder().playerSetup()
            .input(0, 44, 55, stack -> !stack.is(WKTags.OVEN_BLACKLIST))//fuel
            .input(1, 44, 19, stack -> !stack.is(WKTags.OVEN_BLACKLIST))//input
            .output(2, 116, 19, playerInventory.player)//output
            .output(3, 116, 55, playerInventory.player)//extra output
            .build();
    }

    public boolean isBurning() {
        return this.delegate.get(0) > 0;
    }

    public int getBurnTimeScaled(int scale) {
        if (!isBurning()) {
            return 0;
        } else if (delegate.get(1) == 0) {
            return 0;
        } else {
            return this.delegate.get(0) * scale / delegate.get(1);
        }
    }

    public int getProgressScaled(int scale) {
        if (this.delegate.get(3) > 0) {
            return this.delegate.get(2) * scale / this.delegate.get(3);
        }
        return 0;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int fromIndex) {
        return ItemStack.EMPTY;
    }
}
