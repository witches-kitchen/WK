package cf.witcheskitchen.common.entity.ai.sensor;

import cf.witcheskitchen.common.registry.WKMemoryModuleTypes;
import cf.witcheskitchen.common.registry.WKSensorTypes;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.PredicateSensor;
import net.tslat.smartbrainlib.util.BrainUtil;

import java.util.List;

public class TimeOfDaySensor<E extends LivingEntity> extends PredicateSensor<E, E> {
    private static final List<MemoryModuleType<?>> MEMORIES = ObjectArrayList.of(WKMemoryModuleTypes.IS_NIGHT);

    public TimeOfDaySensor() {
        super((entity2, entity) -> entity.level().isDarkOutside());
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
    protected void doTick(ServerLevel level, E entity) {
        if (predicate().test(entity, entity)) {
            BrainUtil.setMemory(entity, WKMemoryModuleTypes.IS_NIGHT, true);
        } else {
            BrainUtil.clearMemory(entity, WKMemoryModuleTypes.IS_NIGHT);
        }
    }
}
