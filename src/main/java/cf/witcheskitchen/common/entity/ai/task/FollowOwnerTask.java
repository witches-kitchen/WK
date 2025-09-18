package cf.witcheskitchen.common.entity.ai.task;

import cf.witcheskitchen.common.registry.WKMemoryModuleTypes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

public class FollowOwnerTask extends Behavior<PathfinderMob> {
    public FollowOwnerTask() {
        super(ImmutableMap.of(
            MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED,
            MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED,
            WKMemoryModuleTypes.OWNER_PLAYER, MemoryStatus.VALUE_PRESENT,
            WKMemoryModuleTypes.SHOULD_FOLLOW_OWNER, MemoryStatus.VALUE_PRESENT
        ));
    }

    @Override
    protected boolean timedOut(long time) {
        return false;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel world, PathfinderMob entity) {
        return entity.getBrain().getMemoryInternal(WKMemoryModuleTypes.SHOULD_FOLLOW_OWNER).get();
    }

    @Override
    protected boolean canStillUse(ServerLevel world, PathfinderMob entity, long time) {
        return checkExtraStartConditions(world, entity);
    }

    @Override
    protected void tick(ServerLevel world, PathfinderMob entity, long time) {
        Brain<?> brain = entity.getBrain();
        Optional<Player> playerEntityOptional = brain.getMemoryInternal(WKMemoryModuleTypes.OWNER_PLAYER);
        playerEntityOptional.ifPresent(player -> brain.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityTracker(player, false), 0.45F, 3)));
        super.tick(world, entity, time);
    }
}
