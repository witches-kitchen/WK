package cf.witcheskitchen.common.entities.tameable;

import cf.witcheskitchen.common.entities.hostile.WKHostileEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.world.World;

public abstract class WKTameableEntity extends TameableEntity {
    public static final TrackedData<Integer> VARIANT = DataTracker.registerData(WKTameableEntity.class,
            TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Integer> OVERLAY_VARIANTS = DataTracker.registerData(WKTameableEntity.class,
            TrackedDataHandlerRegistry.INTEGER);
    public WKTameableEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean shouldRender(double distance) {
        return true;
    }

    public abstract int getVariants();

    public abstract int getOverlayVariants();
}
