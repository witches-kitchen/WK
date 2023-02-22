package cf.witcheskitchen.common.entity.ai.task;


import cf.witcheskitchen.api.entity.WKTameableEntity;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;


public class DontMoveTask extends Task<WKTameableEntity> {
    public DontMoveTask() {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.REGISTERED, MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED), Integer.MAX_VALUE);
    }

    protected boolean shouldRun(ServerWorld serverWorld, WKTameableEntity tameable) {
        return tameable.isAlive() && tameable.isSitting();
    }

    protected boolean shouldKeepRunning(ServerWorld serverWorld, WKTameableEntity tameable, long l) {
        return this.shouldRun(serverWorld, tameable);
    }

    protected void run(ServerWorld serverWorld, WKTameableEntity tameable, long l) {
        this.update(tameable);
    }

    protected void finishRunning(ServerWorld serverWorld, WKTameableEntity tameable, long l) {
        Brain<?> brain = tameable.getBrain();
        brain.forget(MemoryModuleType.WALK_TARGET);
        brain.forget(MemoryModuleType.LOOK_TARGET);
    }

    protected void keepRunning(ServerWorld serverWorld, WKTameableEntity tameable, long l) {
        this.update(tameable);
    }

    protected boolean isTimeLimitExceeded(long time) {
        return false;
    }

    private void update(WKTameableEntity tameable) {
        Brain<?> brain = tameable.getBrain();
        brain.forget(MemoryModuleType.WALK_TARGET);
    }
}
