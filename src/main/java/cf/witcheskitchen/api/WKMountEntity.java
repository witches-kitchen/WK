package cf.witcheskitchen.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.JumpingMount;
import net.minecraft.entity.Saddleable;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class WKMountEntity extends AnimalEntity implements InventoryChangedListener, JumpingMount, Saddleable {
    public static final TrackedData<Integer> VARIANT = DataTracker.registerData(WKMountEntity.class,
            TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Integer> OVERLAY_VARIANTS = DataTracker.registerData(WKMountEntity.class,
            TrackedDataHandlerRegistry.INTEGER);

    public WKMountEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void setJumpStrength(int strength) {

    }

    @Override
    public boolean canJump() {
        return false;
    }

    @Override
    public void startJumping(int height) {

    }

    @Override
    public void stopJumping() {

    }

    @Override
    public boolean canBeSaddled() {
        return false;
    }

    @Override
    public void saddle(@Nullable SoundCategory sound) {

    }

    @Override
    public boolean isSaddled() {
        return false;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public void onInventoryChanged(Inventory sender) {

    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean shouldRender(double distance) {
        return true;
    }

    public abstract int getVariants();

    public abstract int getOverlayVariants();
}
