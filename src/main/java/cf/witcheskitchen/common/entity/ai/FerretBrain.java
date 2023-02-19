package cf.witcheskitchen.common.entity.ai;

import cf.witcheskitchen.common.entity.ai.task.DontMoveTask;
import cf.witcheskitchen.common.entity.ai.task.FerretMeleeAttackTask;
import cf.witcheskitchen.common.entity.ai.task.FollowOwnerTask;
import cf.witcheskitchen.common.entity.tameable.FerretEntity;
import cf.witcheskitchen.common.registry.WKMemoryModuleTypes;
import cf.witcheskitchen.common.registry.WKSensorTypes;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AxolotlBrain;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class FerretBrain {
    private static final List<SensorType<? extends Sensor<? super FerretEntity>>> SENSORS = List.of(
            SensorType.NEAREST_PLAYERS,
            SensorType.NEAREST_LIVING_ENTITIES,
            SensorType.HURT_BY,
            WKSensorTypes.TIME_OF_DAY,
            WKSensorTypes.TAMABLE
    );
    private static final List<MemoryModuleType<?>> MEMORIES = List.of(
            MemoryModuleType.MOBS,
            MemoryModuleType.VISIBLE_MOBS,
            MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER,
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.PATH,
            MemoryModuleType.ANGRY_AT,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.ATTACK_COOLING_DOWN,
            MemoryModuleType.NEAREST_ATTACKABLE,
            MemoryModuleType.PACIFIED,
            MemoryModuleType.NEAREST_REPELLENT,
            MemoryModuleType.AVOID_TARGET,
            WKMemoryModuleTypes.IS_NIGHT
    );


    public FerretBrain() {
    }

    public static Brain<?> create(FerretEntity ferretEntity, Dynamic<?> dynamic) {
        Brain.Profile<FerretEntity> profile = Brain.createProfile(MEMORIES, SENSORS);
        Brain<FerretEntity> brain = profile.deserialize(dynamic);
        addCoreActivities(brain);
        addIdleActivities(brain);
        addFightActivities(ferretEntity, brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    /**
     * These activities are always active. So, expect them to run every tick.
     */
    private static void addCoreActivities(Brain<FerretEntity> brain) {
        brain.setTaskList(
                Activity.CORE,
                0,
                ImmutableList.of(
                        new DontMoveTask(),
                        new StayAboveWaterTask(0.6f),
                        new LookAroundTask(45, 90),
                        new WanderAroundTask(),
                        new UpdateAttackTargetTask<>(FerretBrain::getAttackTarget)
                )
        );
    }

    /**
     * These will run whenever we don't have something better to do. Essentially walk and swim randomly, or do nothing.
     */
    private static void addIdleActivities(Brain<FerretEntity> brain) {
        brain.setTaskList(
                Activity.IDLE,
                ImmutableList.of(
                        Pair.of(0 , GoToRememberedPositionTask.toBlock(MemoryModuleType.NEAREST_REPELLENT, 1.0F, 8, true)),
                        Pair.of(1, new RandomTask<>(
                                ImmutableList.of(
                                        Pair.of(new StrollTask(0.6F), 2),
                                        Pair.of(new GoTowardsLookTarget(0.6F, 3), 2),
                                        Pair.of(new WaitTask(30, 60), 1)
                                ))
                        ),
                        Pair.of(2, new FollowOwnerTask())
                )
        );
    }

    private static void addFightActivities(FerretEntity ferretEntity, Brain<FerretEntity> brain) {
        brain.setTaskList(
                Activity.FIGHT,
                10,
                ImmutableList.of(
                        new RangedApproachTask(1.0F),
                        new FollowMobTask(mob -> isTarget(ferretEntity, mob), (float)ferretEntity.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE)),
                        new FerretMeleeAttackTask(20)
                ),
                MemoryModuleType.ATTACK_TARGET
        );
    }

    /**
     * This is what lets you switch activities. It should be in reverse order of the importance of the activity.
     */
    public static void updateActivities(FerretEntity ferretEntity) {
        ferretEntity.getBrain().resetPossibleActivities(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
        ferretEntity.setAttacking(ferretEntity.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET));
    }

    private static Optional<? extends LivingEntity> getAttackTarget(FerretEntity ferretEntity) {
        Brain<FerretEntity> brain = ferretEntity.getBrain();
        Optional<LivingEntity> optional = LookTargetUtil.getEntity(ferretEntity, MemoryModuleType.ANGRY_AT);
        if(optional.isPresent() && Sensor.testAttackableTargetPredicateIgnoreVisibility(ferretEntity, optional.get())){
            return optional;
        }
        return Optional.empty();
    }

    /**
     * Check if an entity is a target
     * @param ferretEntity the ferret
     * @param entity the potential target
     * @return if entity is target
     */
    private static boolean isTarget(FerretEntity ferretEntity, LivingEntity entity) {
        return ferretEntity.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).filter(targetedEntity -> targetedEntity == entity).isPresent();
    }
}
