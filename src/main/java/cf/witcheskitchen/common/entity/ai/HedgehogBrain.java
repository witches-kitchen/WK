package cf.witcheskitchen.common.entity.ai;

import cf.witcheskitchen.common.entity.ai.task.DontMoveTask;
import cf.witcheskitchen.common.entity.ai.task.FollowOwnerTask;
import cf.witcheskitchen.common.entity.tameable.HedgehogEntity;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.*;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;

import java.util.List;

public class HedgehogBrain {
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
                GoToRememberedPositionTask.toBlock(MemoryModuleType.NEAREST_REPELLENT, 1.0F, 8, true),
                new RandomTask<>(ImmutableList.of(
                        Pair.of(new StrollTask(0.6F), 2),
                        Pair.of(new GoTowardsLookTarget(0.6F, 3), 2),
                        Pair.of(new WaitTask(30, 60), 1)
                )),
                new FollowOwnerTask()
        );
    }

    public static BrainActivityGroup<HedgehogEntity> getFightTasks(HedgehogEntity hedgehogEntity) {
        return BrainActivityGroup.fightTasks();
    }
}
