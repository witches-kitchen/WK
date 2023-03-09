package cf.witcheskitchen.common.entity.ai.sensor;

import cf.witcheskitchen.common.registry.WKMemoryModuleTypes;
import cf.witcheskitchen.common.registry.WKSensorTypes;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.server.world.ServerWorld;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.PredicateSensor;
import net.tslat.smartbrainlib.util.BrainUtils;

import java.util.List;

public class TimeOfDaySensor<E extends LivingEntity> extends PredicateSensor<E, E> {
    private static final List<MemoryModuleType<?>> MEMORIES = ObjectArrayList.of(WKMemoryModuleTypes.IS_NIGHT);

    public TimeOfDaySensor() {
        super((entity2, entity) -> entity.world.isNight());
    }

    @Override
    public List<MemoryModuleType<?>> memoriesUsed() {
        return MEMORIES; // Return our memory list
    }

    @Override
    public SensorType<? extends ExtendedSensor<?>> type() {
        return WKSensorTypes.TIME_OF_DAY.get();
    }

    @Override
    protected void sense(ServerWorld level, E entity) {
        if (predicate().test(entity, entity)) {
            BrainUtils.setMemory(entity, WKMemoryModuleTypes.IS_NIGHT, true);
        } else {
            BrainUtils.clearMemory(entity, WKMemoryModuleTypes.IS_NIGHT);
        }
    }
}
