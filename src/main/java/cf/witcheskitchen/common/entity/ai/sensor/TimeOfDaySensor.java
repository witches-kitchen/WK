package cf.witcheskitchen.common.entity.ai.sensor;

import cf.witcheskitchen.common.registry.WKMemoryModuleTypes;
import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.server.world.ServerWorld;

import java.util.Set;

public class TimeOfDaySensor extends Sensor<LivingEntity> {
    public TimeOfDaySensor(){

    }

    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return ImmutableSet.of(WKMemoryModuleTypes.IS_NIGHT);
    }

    @Override
    protected void sense(ServerWorld world, LivingEntity entity) {

    }


}
