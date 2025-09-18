package cf.witcheskitchen.api.curse;

import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

//TODO: FACTOR IN MORE THINGS
public abstract class CurseDefinition {

    //TODO: What does this line do?
    public int level;

    public CurseDefinition(int level) {
        this.level = level;
    }

    public void tick(@NotNull Player player) {

    }

    /**
     * Can the curse be
     * sent back to the caster
     * via ritual?
     */
    public boolean canDeflectToCaster;

    /**
     * Can the curse be
     * negated, as opposed
     * to dispelling it,
     * rendering it useless
     * to either party?
     */
    public boolean canNegateCurse;

    /**
     * Can the curse be
     * dispelled before
     * it is over?
     */
    public boolean canDispelCurse;

    /**
     * Does the curse last
     * until it is dispelled
     * by the victim?
     */
    public boolean canDispelPermanentCurse;

    /**
     * Is the curse instant?
     */
    public boolean isCurseInstant;

    /**
     * How many ticks at minimum
     * does it take for a non-instant
     * curse to fire?
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
     * a curse last for
     * once fired?
     */
    public int curseLength;

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

    /**
     * Enum for curse strength
     * Hex is the weakest
     * Demonic is the strongest
     * This also dictates how
     * easy a curse is to
     * dispel off of a player
     */
    public enum curseStrength {
        HEX,
        LESSER,
        GREATER,
        DEMONIC
    }
}
