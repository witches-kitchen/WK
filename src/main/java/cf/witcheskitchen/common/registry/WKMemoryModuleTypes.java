package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.Optional;

public interface WKMemoryModuleTypes {
    MemoryModuleType<Boolean> IS_NIGHT = register("is_night");
    MemoryModuleType<PlayerEntity> OWNER_PLAYER = register("owner_player");
    MemoryModuleType<Boolean> SHOULD_FOLLOW_OWNER = register("should_follow_owner");

    static <U> MemoryModuleType<U> register(String id) {
        return Registry.register(Registries.MEMORY_MODULE_TYPE, WitchesKitchen.id(id), new MemoryModuleType<>(Optional.empty()));
    }

    static void init() {

    }
}
