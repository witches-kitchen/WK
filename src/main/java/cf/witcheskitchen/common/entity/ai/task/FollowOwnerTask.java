package cf.witcheskitchen.common.entity.ai.task;

import cf.witcheskitchen.common.registry.WKMemoryModuleTypes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.Optional;

public class FollowOwnerTask extends Task<PathAwareEntity> {
    public FollowOwnerTask() {
        super(ImmutableMap.of(
                MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED,
                MemoryModuleType.WALK_TARGET, MemoryModuleState.REGISTERED,
                WKMemoryModuleTypes.OWNER_PLAYER, MemoryModuleState.VALUE_PRESENT,
                WKMemoryModuleTypes.SHOULD_FOLLOW_OWNER, MemoryModuleState.VALUE_PRESENT
        ));
    }

    @Override
    protected boolean isTimeLimitExceeded(long time) {
        return false;
    }

    @Override
    protected boolean shouldRun(ServerWorld world, PathAwareEntity entity) {
        return entity.getBrain().getOptionalMemory(WKMemoryModuleTypes.SHOULD_FOLLOW_OWNER).get();
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, PathAwareEntity entity, long time) {
        return shouldRun(world, entity);
    }

    @Override
    protected void keepRunning(ServerWorld world, PathAwareEntity entity, long time) {
        Brain<?> brain = entity.getBrain();
        Optional<PlayerEntity> playerEntityOptional = brain.getOptionalMemory(WKMemoryModuleTypes.OWNER_PLAYER);
        playerEntityOptional.ifPresent(player -> brain.remember(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityLookTarget(player, false), 0.45F, 3)));
        super.keepRunning(world, entity, time);
    }
}