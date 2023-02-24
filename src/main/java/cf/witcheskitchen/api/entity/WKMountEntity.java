package cf.witcheskitchen.api.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.JumpingMount;
import net.minecraft.entity.Saddleable;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.minecraft.ClientOnly;

//Todo: Revamp texture variants and their code
public abstract class WKMountEntity extends AnimalEntity implements InventoryChangedListener, JumpingMount, Saddleable {
    public static final TrackedData<Integer> VARIANT = DataTracker.registerData(WKMountEntity.class,
            TrackedDataHandlerRegistry.INTEGER);

    public WKMountEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.getDataTracker().startTracking(VARIANT, 0);
    }

    @Override
    public void setJumpStrength(int strength) {

    }

    @Override
    public boolean canJump(PlayerEntity playerEntity) {
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
    @ClientOnly
    public boolean shouldRender(double distance) {
        return true;
    }

    public abstract int getVariants();
}
