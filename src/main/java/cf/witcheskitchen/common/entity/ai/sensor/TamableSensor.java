package cf.witcheskitchen.common.entity.ai.sensor;

import cf.witcheskitchen.api.entity.WKTameableEntity;
import cf.witcheskitchen.common.registry.WKMemoryModuleTypes;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;

import java.util.List;

public class TamableSensor<E extends WKTameableEntity> extends ExtendedSensor<E> {
    @Override
    public List<MemoryModuleType<?>> memoriesUsed() {
        return ObjectArrayList.of(WKMemoryModuleTypes.OWNER_PLAYER);
    }

    @Override
    public SensorType<? extends ExtendedSensor<?>> type() {
        return null;
    }

    @Override
    protected void sense(ServerWorld world, WKTameableEntity entity) {
        Box box = entity.getBoundingBox().expand(this.getHorizontalExpansion(), this.getHeightExpansion(), this.getHorizontalExpansion());
        List<PlayerEntity> list = world.getEntitiesByClass(PlayerEntity.class, box, LivingEntity::isAlive);
        if (entity.getOwner() instanceof PlayerEntity player && list.contains(player)) {
            Brain<?> brain = entity.getBrain();
            brain.remember(WKMemoryModuleTypes.OWNER_PLAYER, player);
        }
    }

    protected int getHorizontalExpansion() {
        return 24;
    }

    protected int getHeightExpansion() {
        return 16;
    }

}
