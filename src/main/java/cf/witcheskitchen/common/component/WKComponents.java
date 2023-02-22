package cf.witcheskitchen.common.component;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.common.component.entity.WKCurseComponent;
import cf.witcheskitchen.common.component.entity.WKPlayerComponent;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.entity.player.PlayerEntity;

public class WKComponents implements EntityComponentInitializer {
    public static final ComponentKey<WKPlayerComponent> PLAYER_COMPONENT = ComponentRegistry.getOrCreate(WitchesKitchen.id("curse"), WKPlayerComponent.class);
    public static final ComponentKey<WKCurseComponent> CURSE_COMPONENT = ComponentRegistry.getOrCreate(WitchesKitchen.id("curse"), WKCurseComponent.class);


    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.beginRegistration(PlayerEntity.class, PLAYER_COMPONENT).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(WKPlayerComponent::new);
        registry.beginRegistration(PlayerEntity.class, CURSE_COMPONENT).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(WKCurseComponent::new);

    }
}
