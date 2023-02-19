package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.common.entity.ai.sensor.TamableSensor;
import cf.witcheskitchen.common.entity.ai.sensor.TimeOfDaySensor;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public interface WKSensorTypes {
    SensorType<TimeOfDaySensor> TIME_OF_DAY = register("time_of_dat", TimeOfDaySensor::new);
    SensorType<TamableSensor> TAMABLE = register("tamable", TamableSensor::new);

    static <U extends Sensor<?>> SensorType<U> register(String id, Supplier<U> factory) {
        return Registry.register(Registry.SENSOR_TYPE, WitchesKitchen.id(id), new SensorType<>(factory));
    }

    static void init() {

    }
}
