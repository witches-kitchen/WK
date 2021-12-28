package cf.witcheskitchen.common.entities.tameable;

import cf.witcheskitchen.api.WKTameableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class FerretEntity extends WKTameableEntity {
    public FerretEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public int getVariants() {
        return 0;
    }

    @Nullable
    @Override
    public UUID getOwnerUuid() {
        return null;
    }

    @Nullable
    @Override
    public Entity getOwner() {
        return null;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }
}
