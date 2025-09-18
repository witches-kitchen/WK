package cf.witcheskitchen.api.fortune;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;


//TODO: FACTOR IN MORE THINGS
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
     * How many ticks does
     * a fortune last for
     * once fired?
     */
    public int fortuneLength;

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
     * Can curses negate
     * this particular fortune?
     */
    public boolean canCursesNegateFortune;

    /**
     * Can this fortune
     * negate an existing curse?
     */
    public boolean canFortuneCureCurse;

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
     * Apply the fortune if valid
     */
    public abstract boolean apply(Player player);
}
