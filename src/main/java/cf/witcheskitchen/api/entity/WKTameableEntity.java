package cf.witcheskitchen.api.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

//Todo: Revamp texture variants and their code
public abstract class WKTameableEntity extends TameableEntity {
    /**
     * This allows the mod to assign a number of textural variants for a mob.
     * Please be sane with it.
     */
    public static final TrackedData<Integer> VARIANT = DataTracker.registerData(WKTameableEntity.class, TrackedDataHandlerRegistry.INTEGER);
    /**
     * Pose Flags Indexes: 0 - Default, 1 - Sitting, 2 - Sleeping
     */
    private static final TrackedData<Byte> POSE_FLAGS = DataTracker.registerData(WKTameableEntity.class, TrackedDataHandlerRegistry.BYTE);

    public WKTameableEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(VARIANT, 0);
        builder.add(POSE_FLAGS, (byte) 0b0000_0000);
    }

    @Override
    public void writeCustomData(WriteView data) {
        super.writeCustomData(data);
        data.putByte("Flags", dataTracker.get(POSE_FLAGS));
        data.putInt("Variant", this.getVariant());
    }

    @Override
    public void readCustomData(ReadView data) {
        super.readCustomData(data);
        dataTracker.set(POSE_FLAGS, data.getByte("Flags", (byte) 0));
        this.setVariant(data.getInt("Variant", 0));
    }

    protected void setPoseFlag(int index, boolean value) {
        byte b = this.dataTracker.get(POSE_FLAGS);
        if (value) {
            this.dataTracker.set(POSE_FLAGS, (byte) (b | 1 << index));
        } else {
            this.dataTracker.set(POSE_FLAGS, (byte) (b & ~(1 << index)));
        }
    }

    protected boolean getPoseFlag(int index) {
        return (this.dataTracker.get(POSE_FLAGS) & 1 << index) != 0;
    }

    @Override
    public boolean isSleeping() {
        return getPoseFlag(2);
    }

    public void setSleeping(boolean sleeping) {
        setPoseFlag(2, sleeping);
    }

    @Override
    public boolean isSitting() {
        return getPoseFlag(1);
    }

    public void setSitting(boolean sitting) {
        setPoseFlag(1, sitting);
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

    public int getVariant() {
        return MathHelper.clamp(this.dataTracker.get(VARIANT), 1, getVariants());
    }

    public void setVariant(int variant) {
        this.dataTracker.set(VARIANT, variant);
    }
}
