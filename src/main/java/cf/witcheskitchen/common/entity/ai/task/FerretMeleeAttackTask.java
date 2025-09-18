package cf.witcheskitchen.common.entity.ai.task;

import cf.witcheskitchen.common.entity.tameable.FerretEntity;
import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class FerretMeleeAttackTask extends Behavior<FerretEntity> {
    private final int interval;
    private LivingEntity target;
    private int coolDown = 0;

    public FerretMeleeAttackTask(int interval) {
        super(ImmutableMap.of(
            MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED,
            MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT,
            MemoryModuleType.ATTACK_COOLING_DOWN, MemoryStatus.VALUE_ABSENT
        ), 100, 200);
        this.interval = interval;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel serverWorld, FerretEntity ferret) {
        LivingEntity livingEntity = this.getAttackTarget(ferret);
        return BehaviorUtils.canSee(ferret, livingEntity) && ferret.isWithinMeleeAttackRange(livingEntity);
    }

    @Override
    protected boolean canStillUse(ServerLevel world, FerretEntity entity, long time) {
        return target.isAlive();
    }

    @Override
    protected void start(ServerLevel world, FerretEntity ferret, long time) {
        this.target = this.getAttackTarget(ferret);
        if (ferret.getVehicle() != target) {
            ferret.getEntityData().set(FerretEntity.TARGET_ID, target.getId());
            ferret.doHurtTarget(world, target);
        }
        super.start(world, ferret, time);
    }

    @Override
    protected void tick(ServerLevel world, FerretEntity ferret, long time) {
        if (coolDown % 20 == 0 && target != null) {
            ferret.doHurtTarget(world, target);
        }
        coolDown++;
        super.tick(world, ferret, time);
    }

    @Override
    protected void stop(ServerLevel world, FerretEntity ferret, long time) {
        ferret.getEntityData().set(FerretEntity.TARGET_ID, 0);
        ferret.stopRiding();
        ferret.getBrain().setMemoryWithExpiry(MemoryModuleType.ATTACK_COOLING_DOWN, true, this.interval);
        super.stop(world, ferret, time);
    }

    private LivingEntity getAttackTarget(FerretEntity entity) {
        return entity.getBrain().getMemoryInternal(MemoryModuleType.ATTACK_TARGET).get();
    }
}
