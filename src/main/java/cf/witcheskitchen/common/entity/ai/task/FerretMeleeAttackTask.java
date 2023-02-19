package cf.witcheskitchen.common.entity.ai.task;

import cf.witcheskitchen.common.entity.tameable.FerretEntity;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.item.Item;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;


public class FerretMeleeAttackTask extends Task<FerretEntity> {
    private final int interval;
    private LivingEntity target;

    public FerretMeleeAttackTask(int interval) {
        super(ImmutableMap.of(
                MemoryModuleType.LOOK_TARGET, MemoryModuleState.REGISTERED,
                MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT,
                MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleState.VALUE_ABSENT
        ), 100, 200);
        this.interval = interval;
    }

    protected boolean shouldRun(ServerWorld serverWorld, FerretEntity ferret) {
        LivingEntity livingEntity = this.getAttackTarget(ferret);
        return LookTargetUtil.isVisibleInMemory(ferret, livingEntity) && ferret.m_kxhmsdlh(livingEntity);
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, FerretEntity entity, long time) {
        return true;
    }

    @Override
    protected void run(ServerWorld world, FerretEntity ferret, long time) {
        LivingEntity target = this.getAttackTarget(ferret);
        this.target = target;
        if (ferret.getVehicle() != target){
            ferret.getDataTracker().set(FerretEntity.IS_ATTACKING, true);
            ferret.refreshPositionAndAngles(target.getX(), target.getY(), target.getZ(), target.getYaw(), 0.0F);
            ferret.startRiding(target, true);
            ferret.tryAttack(target);
        }
        super.run(world, ferret, time);
    }

    @Override
    protected void keepRunning(ServerWorld world, FerretEntity ferret, long time) {
        if (ferret.getVehicle() != null) {
            ferret.teleport(target.getX(), target.getY(), target.getZ());
        }
        super.keepRunning(world, ferret, time);
    }

    @Override
    protected void finishRunning(ServerWorld world, FerretEntity ferret, long time) {
        ferret.stopRiding();
        ferret.getDataTracker().set(FerretEntity.IS_ATTACKING, false);
        ferret.getBrain().remember(MemoryModuleType.ATTACK_COOLING_DOWN, true, this.interval);
        super.finishRunning(world, ferret, time);
    }

    private LivingEntity getAttackTarget(FerretEntity entity) {
        return entity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).get();
    }
}
