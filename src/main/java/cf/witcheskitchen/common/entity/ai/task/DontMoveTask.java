package cf.witcheskitchen.common.entity.ai.task;


import cf.witcheskitchen.api.entity.WKTameableEntity;
import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;


public class DontMoveTask extends Behavior<WKTameableEntity> {
    public DontMoveTask() {
        super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED, MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED), Integer.MAX_VALUE);
    }

    protected boolean shouldRun(ServerLevel serverWorld, WKTameableEntity tameable) {
        return tameable.isAlive() && tameable.isOrderedToSit();
    }

    protected boolean shouldKeepRunning(ServerLevel serverWorld, WKTameableEntity tameable, long l) {
        return this.shouldRun(serverWorld, tameable);
    }

    protected void run(ServerLevel serverWorld, WKTameableEntity tameable, long l) {
        this.update(tameable);
    }

    protected void finishRunning(ServerLevel serverWorld, WKTameableEntity tameable, long l) {
        Brain<?> brain = tameable.getBrain();
        brain.eraseMemory(MemoryModuleType.WALK_TARGET);
        brain.eraseMemory(MemoryModuleType.LOOK_TARGET);
    }

    protected void keepRunning(ServerLevel serverWorld, WKTameableEntity tameable, long l) {
        this.update(tameable);
    }

    protected boolean timedOut(long time) {
        return false;
    }

    private void update(WKTameableEntity tameable) {
        Brain<?> brain = tameable.getBrain();
        brain.eraseMemory(MemoryModuleType.WALK_TARGET);
    }
}
