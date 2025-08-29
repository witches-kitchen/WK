package cf.witcheskitchen.api.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

//Todo: Revamp texture variants and their code
public abstract class WKTameableEntity extends TamableAnimal {
    /**
     * This allows the mod to assign a number of textural variants for a mob.
     * Please be sane with it.
     */
    public static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(WKTameableEntity.class, EntityDataSerializers.INT);
    /**
     * Pose Flags Indexes: 0 - Default, 1 - Sitting, 2 - Sleeping
     */
    private static final EntityDataAccessor<Byte> POSE_FLAGS = SynchedEntityData.defineId(WKTameableEntity.class, EntityDataSerializers.BYTE);

    public WKTameableEntity(EntityType<? extends TamableAnimal> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
        builder.define(POSE_FLAGS, (byte) 0b0000_0000);
    }

    @Override
    public void addAdditionalSaveData(ValueOutput data) {
        super.addAdditionalSaveData(data);
        data.putByte("Flags", entityData.get(POSE_FLAGS));
        data.putInt("Variant", this.getVariant());
    }

    @Override
    public void readAdditionalSaveData(ValueInput data) {
        super.readAdditionalSaveData(data);
        entityData.set(POSE_FLAGS, data.getByteOr("Flags", (byte) 0));
        this.setVariant(data.getIntOr("Variant", 0));
    }

    protected void setPoseFlag(int index, boolean value) {
        byte b = this.entityData.get(POSE_FLAGS);
        if (value) {
            this.entityData.set(POSE_FLAGS, (byte) (b | 1 << index));
        } else {
            this.entityData.set(POSE_FLAGS, (byte) (b & ~(1 << index)));
        }
    }

    protected boolean getPoseFlag(int index) {
        return (this.entityData.get(POSE_FLAGS) & 1 << index) != 0;
    }

    @Override
    public boolean isSleeping() {
        return getPoseFlag(2);
    }

    public void setSleeping(boolean sleeping) {
        setPoseFlag(2, sleeping);
    }

    @Override
    public boolean isOrderedToSit() {
        return getPoseFlag(1);
    }

    public void setOrderedToSit(boolean sitting) {
        setPoseFlag(1, sitting);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }

    /**
     * This allows the mod to assign a number of textural variants for a mob.
     * Please be sane with it.
     */
    public abstract int getVariants();

    public int getVariant() {
        return Mth.clamp(this.entityData.get(VARIANT), 1, getVariants());
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }
}
