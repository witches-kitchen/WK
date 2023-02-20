package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.common.entity.ai.sensor.TamableSensor;
import cf.witcheskitchen.common.entity.ai.sensor.TimeOfDaySensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.tslat.smartbrainlib.SBLConstants;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;

import java.util.function.Supplier;

public interface WKSensorTypes {
    Supplier<SensorType<TimeOfDaySensor<?>>> TIME_OF_DAY = register("time_of_day", TimeOfDaySensor::new);
    Supplier<SensorType<TamableSensor<?>>> TAMABLE = register("tamable", TamableSensor::new);

    private static <T extends ExtendedSensor<?>> Supplier<SensorType<T>> register(String id, Supplier<T> sensor) {
        return SBLConstants.SBL_LOADER.registerSensorType(id, sensor);
    }

    static void init() {

    }
}
