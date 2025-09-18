package cf.witcheskitchen.api.curse;

import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class CurseDefinition {
    public int level;

    public CurseDefinition(int level) {
        this.level = level;
    }

    public void tick(@NotNull Player player) {

    }

    /**
     * Effects of the curse on
     * first applying the curse
     */
    public void onRemoved(Player player) {

    }

    /**
     * Effects of the curse on
     * first applying the curse
     */
    public void onAdded(Player player) {

    }

    /**
     * Enum for curse strength
     * Hex is the weakest
     * Demonic is the strongest
     */
    public enum curseStrength {
        HEX,
        LESSER,
        GREATER,
        DEMONIC
    }
}
