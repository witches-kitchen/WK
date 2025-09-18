package cf.witcheskitchen.api.curse;

import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public abstract class CurseEffect {
    public void tick(@NotNull Player player) {

    }

    /**
     * Effects of the curse on
     * a player on first applying the curse
     */
    public void onAdded(Player player) {

    }

    /**
     * Effects of the curse on
     * a player on removing a curse
     */
    public void onRemoved(Player player) {

    }

    /**
     * Check if the player is a valid
     * target for the curse
     */
    public boolean isValid(Player player) {
        return true;
    }

    /**
     * Apply the curse if valid
     */
    public abstract boolean apply(Player player);
}
