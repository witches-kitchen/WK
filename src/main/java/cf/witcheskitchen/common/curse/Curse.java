package cf.witcheskitchen.common.curse;

import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class Curse {
    public int level;

    public Curse(int level) {
        this.level = level;
    }

    public void tick(@NotNull Player player) {

    }

    public void onRemoved(Player player) {

    }

    public void onAdded(Player player) {

    }
}
