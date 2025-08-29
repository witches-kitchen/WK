package cf.witcheskitchen.common.entity.ai.sensor;

import cf.witcheskitchen.api.entity.WKTameableEntity;
import cf.witcheskitchen.common.registry.WKMemoryModuleTypes;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
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
    protected void doTick(ServerLevel world, WKTameableEntity entity) {
        AABB box = entity.getBoundingBox().inflate(this.getHorizontalExpansion(), this.getHeightExpansion(), this.getHorizontalExpansion());
        List<Player> list = world.getEntitiesOfClass(Player.class, box, LivingEntity::isAlive);
        if (entity.getOwner() instanceof Player player && list.contains(player)) {
            Brain<?> brain = entity.getBrain();
            brain.setMemory(WKMemoryModuleTypes.OWNER_PLAYER, player);
        }
    }

    protected int getHorizontalExpansion() {
        return 24;
    }

    protected int getHeightExpansion() {
        return 16;
    }

}
