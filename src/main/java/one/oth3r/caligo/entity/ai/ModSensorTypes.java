package one.oth3r.caligo.entity.ai;

import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.sensor.TemptationsSensor;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import one.oth3r.caligo.Caligo;
import one.oth3r.caligo.entity.coppice.CoppiceBrain;
import one.oth3r.caligo.entity.coppice.CoppiceSpecificSensor;

import java.util.function.Supplier;

public class ModSensorTypes {
    public static final SensorType<CoppiceSpecificSensor> COPPICE_SPECIFIC_SENSOR = register("coppice_specific_sensor", CoppiceSpecificSensor::new);
    public static final SensorType<TemptationsSensor> COPPICE_TEMPTATIONS = register("coppice_temptations",
            () -> new TemptationsSensor(CoppiceBrain.getTemptItemPredicate()));

    private static <U extends Sensor<?>> SensorType<U> register(String id, Supplier<U> factory) {
        return Registry.register(Registries.SENSOR_TYPE, Identifier.of(Caligo.MOD_ID, id), new SensorType<>(factory));
    }
}
