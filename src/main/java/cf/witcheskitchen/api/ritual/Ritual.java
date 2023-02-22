package cf.witcheskitchen.api.ritual;

import cf.witcheskitchen.api.event.RitualEvent;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.Set;
import java.util.UUID;

public class Ritual {
    public final UUID playerUuid;
    public final Box boundingBox;
    public final Set<RitualCircle> circles;
    public final long id;

    public BlockPos ritualPos;
    public PlayerEntity activator;

    public Ritual(UUID uuid, long id, Box boundingBox, Set<RitualCircle> circles){
        this.playerUuid = uuid;
        this.boundingBox = boundingBox;
        this.circles = circles;
        this.id = id;
    }

    public void tick(World world, BlockPos blockPos, BlockState blockState) {
        RitualEvent.TICK.invoker().tick(this, world, blockPos);

    }

    public void onStart(World world, BlockPos pos, PlayerEntity player) {
        RitualEvent.START.invoker().start(this, world, pos, player);

    }

    public void onEnd(World world, BlockPos blockPos) {
        RitualEvent.END.invoker().end(this, world, blockPos);

    }
}
