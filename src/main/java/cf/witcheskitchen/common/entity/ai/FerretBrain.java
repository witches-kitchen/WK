package cf.witcheskitchen.common.entity.ai;

import cf.witcheskitchen.api.WKApi;
import cf.witcheskitchen.common.entity.ai.sensor.TamableSensor;
import cf.witcheskitchen.common.entity.ai.sensor.TimeOfDaySensor;
import cf.witcheskitchen.common.entity.ai.task.AnimatableMeleeAttack;
import cf.witcheskitchen.common.entity.ai.task.DontMoveTask;
import cf.witcheskitchen.common.entity.tameable.FerretEntity;
import cf.witcheskitchen.common.registry.WKEntityTypes;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomLookAround;
import net.minecraft.world.entity.ai.behavior.Swim;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.Sensor;
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
            new Swim(0.6f),
            new RandomLookAround(ConstantInt.of(45), 90, -15, 15),
            new MoveToTargetSink()
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
        Optional<LivingEntity> optional = BehaviorUtils.getLivingEntityFromUUIDMemory(ferretEntity, MemoryModuleType.ANGRY_AT);
        if (optional.isPresent() && Sensor.isEntityAttackableIgnoringLineOfSight((ServerLevel) ferretEntity.level(), ferretEntity, optional.get())) {
            return optional;
        }
        if (brain.hasMemoryValue(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)) {
            Optional<NearestVisibleLivingEntities> visibleLivingEntitiesCache = ferretEntity.getBrain().getMemoryInternal(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES);
            if (visibleLivingEntitiesCache.isPresent()) {
                return visibleLivingEntitiesCache.get().findClosest(UNTAMED_TARGET_PREDICATE);
            }
        }
        return Optional.empty();
    }
}
