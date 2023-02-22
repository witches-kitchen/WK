package cf.witcheskitchen.api.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.world.World;
import org.quiltmc.loader.api.minecraft.ClientOnly;

//Todo: Revamp texture variants and their code
//Credit to AzureDoom for variant code
public abstract class WKHostileEntity extends HostileEntity implements Monster {
    /**
     * This allows the mod to assign a number of textural variants for a mob.
     * Please be sane with it.
     */
    public static final TrackedData<Integer> VARIANT = DataTracker.registerData(WKHostileEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public WKHostileEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.getDataTracker().startTracking(VARIANT, 0);
    }

    @Override
    @ClientOnly
    public boolean shouldRender(double distance) {
        return true;
    }

    /**
     * This allows the mod to assign a number of textural variants for a mob.
     * Please be sane with it.
     */
    public abstract int getVariants();
}
