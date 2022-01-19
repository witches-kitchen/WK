package cf.witcheskitchen.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.world.World;

//Todo: Revamp texture variants and their code
//Credit to AzureDoom for variant code
public abstract class WKHostileEntity extends HostileEntity implements Monster {
    /**
     * This allows the mod to assign a number of textural variants for a mob.
     * Please be sane with it.
     */
    public static final TrackedData<Integer> VARIANT = DataTracker.registerData(WKHostileEntity.class,
            TrackedDataHandlerRegistry.INTEGER);

    public WKHostileEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean shouldRender(double distance) {
        return true;
    }

    /**
     * This allows the mod to assign a number of textural variants for a mob.
     * Please be sane with it.
     */
    public abstract int getVariants();
}
