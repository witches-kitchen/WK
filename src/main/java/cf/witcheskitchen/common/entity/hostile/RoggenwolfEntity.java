package cf.witcheskitchen.common.entity.hostile;

import cf.witcheskitchen.api.entity.WKHostileEntity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;

import java.util.SplittableRandom;

public class RoggenwolfEntity extends WKHostileEntity implements GeoEntity {
    public RoggenwolfEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return LivingEntity.createLivingAttributes().add(EntityAttributes.FOLLOW_RANGE, 16.0D)
                .add(EntityAttributes.MOVEMENT_SPEED, 1.25D)
                .add(EntityAttributes.MAX_HEALTH, 15).add(EntityAttributes.ARMOR, 0.0D)
                .add(EntityAttributes.ATTACK_DAMAGE, 3.5D).add(EntityAttributes.ATTACK_KNOCKBACK, 0.35D);
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        int var = new SplittableRandom().nextInt(1, 7);
        this.setVariant(var);
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    public int getVariants() {
        return 6;
    }

    public int getVariant() {
        return MathHelper.clamp(this.dataTracker.get(VARIANT), 1, 7);
    }

    public void setVariant(int variant) {
        this.dataTracker.set(VARIANT, variant);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public boolean shouldRender(double distance) {
        return true;
    }

    @Override
    public boolean isFireImmune() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        if (source.isOf(DamageTypes.FALLING_BLOCK)) {
            return false;
        }
        return super.damage(world, source, amount);
    }

    @Override
    public boolean handleFallDamage(double fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Nullable
    @Override
    public DamageSource getRecentDamageSource() {
        if (isOnFire()) {
            setOnFireFor(15);
            this.applyDamage((ServerWorld) this.getWorld(), this.getDamageSources().onFire(), 500);
        }
        return super.getRecentDamageSource();
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return null;
    }
}
