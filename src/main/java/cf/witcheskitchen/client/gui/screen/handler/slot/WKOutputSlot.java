package cf.witcheskitchen.client.gui.screen.handler.slot;

import cf.witcheskitchen.api.IDeviceExperienceHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;

public class WKOutputSlot extends WKSlot {

    private final PlayerEntity player;

    public WKOutputSlot(Inventory inventory, int index, int x, int y, PlayerEntity player) {
        super(inventory, index, x, y, stack -> false);
        this.player = player;
    }

    @Override
    public void onTakeItem(PlayerEntity player, ItemStack stack) {
        super.onTakeItem(player, stack);
        this.onCrafted(stack);
    }

    @Override
    protected void onCrafted(ItemStack stack) {
        //drop xp when the player takes the item from output
        if (!this.player.world.isClient && this.inventory instanceof IDeviceExperienceHandler xpHandler) {
            if (this.player.world instanceof ServerWorld world) {
                xpHandler.dropExperience(world, this.player.getPos());
            }
        }
    }

}
