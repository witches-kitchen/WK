package cf.witcheskitchen.common.entity.ai;

import cf.witcheskitchen.common.entity.ai.sensor.TamableSensor;
import cf.witcheskitchen.common.entity.ai.sensor.TimeOfDaySensor;
import cf.witcheskitchen.common.entity.ai.task.DontMoveTask;
import cf.witcheskitchen.common.entity.ai.task.FerretMeleeAttackTask;
import cf.witcheskitchen.common.entity.ai.task.FollowOwnerTask;
import cf.witcheskitchen.common.entity.tameable.FerretEntity;
import cf.witcheskitchen.common.registry.WKEntityTypes;
import cf.witcheskitchen.common.registry.WKMemoryModuleTypes;
import cf.witcheskitchen.common.registry.WKSensorTypes;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.VisibleLivingEntitiesCache;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AxolotlBrain;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class FerretBrain {

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
                new WanderAroundTask(),
                new UpdateAttackTargetTask<>(FerretBrain::getAttackTarget)
        );
    }

    public static BrainActivityGroup<FerretEntity> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                GoToRememberedPositionTask.toBlock(MemoryModuleType.NEAREST_REPELLENT, 1.0F, 8, true),
                new RandomTask<>(ImmutableList.of(
                        Pair.of(new StrollTask(0.6F), 2),
                        Pair.of(new GoTowardsLookTarget(0.6F, 3), 2),
                        Pair.of(new WaitTask(30, 60), 1)
                )),
                new FollowOwnerTask()
        );
    }

    public static BrainActivityGroup<FerretEntity> getFightTasks(FerretEntity ferret) {
        return BrainActivityGroup.fightTasks(
                new RangedApproachTask(1.0F),
                new FollowMobTask(mob -> FerretBrain.isTarget(ferret, mob), (float)ferret.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE)),
                new FerretMeleeAttackTask(20)
        );
    }

    public static Optional<? extends LivingEntity> getAttackTarget(FerretEntity ferretEntity) {
        Brain<?> brain = ferretEntity.getBrain();
        Optional<LivingEntity> optional = LookTargetUtil.getEntity(ferretEntity, MemoryModuleType.ANGRY_AT);
        if(optional.isPresent() && Sensor.testAttackableTargetPredicateIgnoreVisibility(ferretEntity, optional.get())){
            return optional;
        }
        if (brain.hasMemoryModule(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER)) {
            return brain.getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER);
        }
        if (brain.hasMemoryModule(MemoryModuleType.VISIBLE_MOBS)) {
            Optional<VisibleLivingEntitiesCache> visibleLivingEntitiesCache = ferretEntity.getBrain().getOptionalMemory(MemoryModuleType.VISIBLE_MOBS);
            if(visibleLivingEntitiesCache.isPresent()){
                return visibleLivingEntitiesCache.get().m_yzezovsk(entity -> entity.getType() != WKEntityTypes.FERRET && !entity.isSubmergedInWater());
            }
        }
        return Optional.empty();
    }

    /**
     * Check if an entity is a target
     * @param ferretEntity the ferret
     * @param entity the potential target
     * @return if entity is target
     */
    public static boolean isTarget(FerretEntity ferretEntity, LivingEntity entity) {
        return ferretEntity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).filter(targetedEntity -> targetedEntity == entity).isPresent();
    }
}
