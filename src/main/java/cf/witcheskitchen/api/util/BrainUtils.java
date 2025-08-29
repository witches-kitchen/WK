package cf.witcheskitchen.api.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class BrainUtils {
    public static LivingEntity getAttackTarget(Mob entity) {
        return entity.getBrain().getMemoryInternal(MemoryModuleType.ATTACK_TARGET).get();
    }

    public static boolean isTarget(Mob mobEntity, LivingEntity entity) {
        return mobEntity.getBrain().getMemoryInternal(MemoryModuleType.ATTACK_TARGET).filter(targetedEntity -> targetedEntity == entity).isPresent();
    }

    public static void setTargetInvalid(Mob mobEntity, LivingEntity target) {
        mobEntity.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
        mobEntity.getBrain().eraseMemory(MemoryModuleType.ANGRY_AT);
    }
}
