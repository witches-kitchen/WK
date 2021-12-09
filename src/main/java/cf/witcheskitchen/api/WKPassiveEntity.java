package cf.witcheskitchen.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class WKPassiveEntity extends PassiveEntity {
    public static final TrackedData<Integer> VARIANT = DataTracker.registerData(WKPassiveEntity.class,
            TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Integer> OVERLAY_VARIANTS = DataTracker.registerData(WKPassiveEntity.class,
            TrackedDataHandlerRegistry.INTEGER);

    public WKPassiveEntity(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean shouldRender(double distance) {
        return true;
    }

    public abstract int getVariants();
}
