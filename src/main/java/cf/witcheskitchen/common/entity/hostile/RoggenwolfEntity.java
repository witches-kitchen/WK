package cf.witcheskitchen.common.entity.hostile;

import cf.witcheskitchen.api.entity.WKHostileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;

public class RoggenwolfEntity extends WKHostileEntity implements GeoEntity {
    public RoggenwolfEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1.25D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 15).add(EntityAttributes.GENERIC_ARMOR, 0.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.5D).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.35D);
    }

    @Override
    public int getVariants() {
        return 0;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {

    }

    @Override
    public boolean isFireImmune() {
        return false;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return null;
    }
}
