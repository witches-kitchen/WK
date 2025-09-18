package cf.witcheskitchen.api.fortune;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public abstract class FortuneDefinition {


    /**
     * Is the fortune bad?
     */
    public boolean isNegative;

    /**
     * Does the fortune fire instantly?
     */
    public boolean isInstant;

    /**
     * How many ticks at minimum
     * does it take for a non-instant
     * fortune to fire?
     */
    public int minTimeFrame;

    /**
     * How many ticks at maximum
     * does it take for a non-instant
     * fortune to fire?
     */
    public int maxTimeFrame;

    /**
     * Fortune definition
     * Needs more work
     */
    public Fortune(ResourceLocation id, boolean isInstant, boolean isNegative, int minTimeFrame, int maxTimeFrame) {
        this.isInstant = isInstant;
        this.isNegative = isNegative;
        this.minTimeFrame = minTimeFrame;
        this.maxTimeFrame = maxTimeFrame;
    }

    /**
     * Check if the player is a valid
     * target for the fortune
     */
    public boolean isValid(Player player) {
        return true;
    }

    /**
     * Apply the fortune
     */
    public abstract boolean apply(Player player);

}
