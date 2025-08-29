package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import java.util.Optional;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.player.Player;

public interface WKMemoryModuleTypes {
    MemoryModuleType<Boolean> IS_NIGHT = register("is_night");
    MemoryModuleType<Player> OWNER_PLAYER = register("owner_player");
    MemoryModuleType<Boolean> SHOULD_FOLLOW_OWNER = register("should_follow_owner");

    static <U> MemoryModuleType<U> register(String id) {
        return Registry.register(BuiltInRegistries.MEMORY_MODULE_TYPE, WitchesKitchen.id(id), new MemoryModuleType<>(Optional.empty()));
    }

    static void init() {

    }
}
