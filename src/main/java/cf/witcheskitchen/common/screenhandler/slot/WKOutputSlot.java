package cf.witcheskitchen.common.screenhandler.slot;

import cf.witcheskitchen.api.block.entity.IExperienceHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class WKOutputSlot extends WKSlot {

    private final Player player;

    public WKOutputSlot(Container inventory, int index, int x, int y, Player player) {
        super(inventory, index, x, y, stack -> false);
        this.player = player;
    }

    @Override
    public void onTake(Player player, ItemStack stack) {
        super.onTake(player, stack);
        this.checkTakeAchievements(stack);
    }

    @Override
    protected void checkTakeAchievements(ItemStack stack) {
        //drop xp when the player takes the item from output
        if (!this.player.level().isClientSide && this.container instanceof IExperienceHandler xpHandler) {
            if (this.player.level() instanceof ServerLevel world) {
                xpHandler.dropExperience(world, this.player.position());
            }
        }
    }

}
