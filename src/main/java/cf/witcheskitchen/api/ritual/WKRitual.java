package cf.witcheskitchen.api.ritual;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.util.Set;
import java.util.UUID;

public class WKRitual {
    public final UUID playerUuid;
    public final Box boundingBox;
    public final Set<RitualCircle> circles;
    public final Rite rite;
    public final long id;

    public BlockPos ritualPos;
    public PlayerEntity activator;

    public WKRitual(Rite rite, UUID uuid, long id, Box boundingBox, Set<RitualCircle> circles){
        this.playerUuid = uuid;
        this.rite = rite;
        this.boundingBox = boundingBox;
        this.circles = circles;
        this.id = id;

    }
}
