package cf.witcheskitchen.common.entity.ai.task;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.Function;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.tslat.smartbrainlib.api.core.behaviour.DelayedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AnimatableMeleeAttack<E extends MobEntity> extends DelayedBehaviour<E> {
    private static final List<Pair<MemoryModuleType<?>, MemoryModuleState>> MEMORY_REQUIREMENTS =
            ObjectArrayList.of(
                    Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT),
                    Pair.of(MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleState.VALUE_ABSENT));

    protected Function<E, Integer> attackIntervalSupplier = entity -> 20;

    @Nullable
    protected LivingEntity target = null;

    public AnimatableMeleeAttack(int delayTicks) {
        super(delayTicks);
    }


    public AnimatableMeleeAttack<E> attackInterval(Function<E, Integer> supplier) {
        this.attackIntervalSupplier = supplier;

        return this;
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryModuleState>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    @Override
    protected boolean shouldRun(ServerWorld world, E entity) {
        this.target = BrainUtils.getTargetOfEntity(entity);

        return entity.getVisibilityCache().canSee(this.target) && entity.m_kxhmsdlh(this.target);
    }

    @Override
    protected void start(E entity) {
        entity.swingHand(Hand.MAIN_HAND);
        LookTargetUtil.lookAt(entity, this.target);
    }

    @Override
    protected void stop(E entity) {
        this.target = null;
    }

    @Override
    protected void doDelayedAction(E entity) {
        BrainUtils.setForgettableMemory(entity, MemoryModuleType.ATTACK_COOLING_DOWN, true, this.attackIntervalSupplier.apply(entity));

        if (this.target == null)
            return;

        if (!entity.getVisibilityCache().canSee(this.target) || !entity.m_kxhmsdlh(this.target))
            return;

        entity.tryAttack(this.target);
    }
}
