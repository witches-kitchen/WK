package cf.witcheskitchen.api.fortune;

import net.minecraft.world.entity.player.Player;

public abstract class FortuneEffect {
    /**
     * Effects of the fortune on
     * a player on first applying the fortune
     */
    public void onAdded(Player player) {

    }

    /**
     * Effects of the fortune on
     * a player on removing a fortune
     */
    public void onRemoved(Player player) {

    }

    /**
     * Check if the player is a valid
     * target for the fortune
     */
    public boolean isValid(Player player) {
        return true;
    }

    /**
     * Apply the fortune if valid
     */
    public abstract boolean apply(Player player);
}
