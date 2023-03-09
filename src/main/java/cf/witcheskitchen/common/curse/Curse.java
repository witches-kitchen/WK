package cf.witcheskitchen.common.curse;

import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;

public class Curse {
    public int level;

    public Curse(int level) {
        this.level = level;
    }

    public void tick(@NotNull PlayerEntity player) {

    }

    public void onRemoved(PlayerEntity player) {

    }

    public void onAdded(PlayerEntity player) {

    }
}
