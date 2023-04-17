package cf.witcheskitchen.common.entity.ai;

import cf.witcheskitchen.api.WKApi;
import cf.witcheskitchen.common.entity.ai.task.DontMoveTask;
import cf.witcheskitchen.common.entity.ai.task.FollowOwnerTask;
import cf.witcheskitchen.common.entity.tameable.HedgehogEntity;
import cf.witcheskitchen.common.registry.WKEntityTypes;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.LookAroundTask;
import net.minecraft.entity.ai.brain.task.StayAboveWaterTask;
import net.minecraft.entity.ai.brain.task.WanderAroundTask;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;

import java.util.List;
import java.util.function.Predicate;

public class HedgehogBrain {
    private static final Predicate<LivingEntity> FLEE_SUPERNATURAL = (entity) -> {
        EntityType<?> entityType = entity.getType();
        return entityType == WKEntityTypes.CUSITH || entityType == WKEntityTypes.ROGGENWOLF || WKApi.isGreaterDemon(entity);
    };

    public static List<ExtendedSensor<HedgehogEntity>> getSensors() {
        return ObjectArrayList.of();
    }

    public static BrainActivityGroup<HedgehogEntity> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new DontMoveTask(),
                new StayAboveWaterTask(0.6f),
                new LookAroundTask(45, 90),
                new WanderAroundTask()
        );
    }

    public static BrainActivityGroup<HedgehogEntity> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new OneRandomBehaviour<>(
                        new SetPlayerLookTarget<>()),
                new Idle<>(),
                new FollowOwnerTask()
        );
    }

    public static BrainActivityGroup<HedgehogEntity> getFightTasks(HedgehogEntity hedgehogEntity) {
        return BrainActivityGroup.fightTasks();
    }
}
