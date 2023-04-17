package cf.witcheskitchen.common.entity.ai;

import cf.witcheskitchen.api.WKApi;
import cf.witcheskitchen.common.entity.ai.sensor.TamableSensor;
import cf.witcheskitchen.common.entity.ai.sensor.TimeOfDaySensor;
import cf.witcheskitchen.common.entity.ai.task.AnimatableMeleeAttack;
import cf.witcheskitchen.common.entity.ai.task.DontMoveTask;
import cf.witcheskitchen.common.entity.tameable.FerretEntity;
import cf.witcheskitchen.common.registry.WKEntityTypes;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.VisibleLivingEntitiesCache;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.task.LookAroundTask;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.StayAboveWaterTask;
import net.minecraft.entity.ai.brain.task.WanderAroundTask;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class FerretBrain {

    private static final Predicate<LivingEntity> UNTAMED_TARGET_PREDICATE = entity -> {
        final EntityType<?> entityType = entity.getType();
        return entityType == EntityType.RABBIT || entityType == EntityType.CHICKEN;
    };
    private static final Predicate<LivingEntity> FLEE_SUPERNATURAL = (entity) -> {
        EntityType<?> entityType = entity.getType();
        return entityType == WKEntityTypes.CUSITH || entityType == WKEntityTypes.ROGGENWOLF || WKApi.isGreaterDemon(entity);
    };

    public FerretBrain() {
    }

    public static List<ExtendedSensor<FerretEntity>> getSensors() {
        return ObjectArrayList.of(
                new NearbyPlayersSensor<>(),
                new NearbyLivingEntitySensor<>(),
                new HurtBySensor<>(),
                new TimeOfDaySensor<>(),
                new TamableSensor<>()

        );
    }

    public static BrainActivityGroup<FerretEntity> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new DontMoveTask(),
                new StayAboveWaterTask(0.6f),
                new LookAroundTask(45, 90),
                new WanderAroundTask()
        );
    }

    public static BrainActivityGroup<FerretEntity> getIdleTasks(FerretEntity ferret) {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<>(
                        //new TargetOrRetaliate<>().startCondition(e -> getAttackTarget(ferret).isPresent()),
                        new SetPlayerLookTarget<>()),
                //new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>().speedModifier(0.6f),
                        new Idle<>()
                )
        );
    }

    public static BrainActivityGroup<FerretEntity> getFightTasks(FerretEntity ferret) {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new AnimatableMeleeAttack<>(20)
        );
    }

    public static Optional<? extends LivingEntity> getAttackTarget(FerretEntity ferretEntity) {
        Brain<?> brain = ferretEntity.getBrain();
        Optional<LivingEntity> optional = LookTargetUtil.getEntity(ferretEntity, MemoryModuleType.ANGRY_AT);
        if (optional.isPresent() && Sensor.testAttackableTargetPredicateIgnoreVisibility(ferretEntity, optional.get())) {
            return optional;
        }
        if (brain.hasMemoryModule(MemoryModuleType.VISIBLE_MOBS)) {
            Optional<VisibleLivingEntitiesCache> visibleLivingEntitiesCache = ferretEntity.getBrain().getOptionalMemory(MemoryModuleType.VISIBLE_MOBS);
            if (visibleLivingEntitiesCache.isPresent()) {
                return visibleLivingEntitiesCache.get().m_yzezovsk(UNTAMED_TARGET_PREDICATE);
            }
        }
        return Optional.empty();
    }
}
