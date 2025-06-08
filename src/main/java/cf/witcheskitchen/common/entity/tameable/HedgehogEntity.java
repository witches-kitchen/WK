package cf.witcheskitchen.common.entity.tameable;

import cf.witcheskitchen.api.entity.WKTameableEntity;
import cf.witcheskitchen.common.entity.ai.HedgehogBrain;
import cf.witcheskitchen.common.registry.WKEntityTypes;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.processing.AnimationController;
import software.bernie.geckolib.animatable.processing.AnimationTest;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.SplittableRandom;

public class HedgehogEntity extends WKTameableEntity implements GeoEntity, SmartBrainOwner<HedgehogEntity> {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public HedgehogEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.setTamed(false, true);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MOVEMENT_SPEED, 0.25)
                .add(EntityAttributes.MAX_HEALTH, 6.0)
                .add(EntityAttributes.ATTACK_DAMAGE);
    }

    @Override
    protected Brain.Profile<?> createBrainProfile() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    protected void mobTick(ServerWorld world) {
        tickBrain(this);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
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

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        HedgehogEntity hedgehogEntity = WKEntityTypes.HEDGEHOG.create(world, SpawnReason.BREEDING);
        LazyEntityReference<LivingEntity> owner = this.getOwnerReference();
        if (owner != null && hedgehogEntity != null) {
            hedgehogEntity.setOwner(owner);
            hedgehogEntity.setTamed(true, true);
        }
        return hedgehogEntity;
    }


    @Override
    public List<ExtendedSensor<HedgehogEntity>> getSensors() {
        return HedgehogBrain.getSensors();
    }

    @Override
    public BrainActivityGroup<HedgehogEntity> getCoreTasks() {
        return HedgehogBrain.getCoreTasks();
    }

    @Override
    public BrainActivityGroup<HedgehogEntity> getIdleTasks() {
        return HedgehogBrain.getIdleTasks();
    }

    @Override
    public BrainActivityGroup<HedgehogEntity> getFightTasks() {
        return HedgehogBrain.getFightTasks(this);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controller) {
        controller.add(DefaultAnimations.genericIdleController()).add(new AnimationController<HedgehogEntity>("move", 0, this::predicate));
    }

    private PlayState predicate(AnimationTest<HedgehogEntity> state) {
        if (this.isSitting()) {
            state.setAnimation(RawAnimation.begin().thenLoop("loaf"));
            return PlayState.CONTINUE;
        } else if (state.isMoving()) {
            state.setAnimation(RawAnimation.begin().thenLoop("walk"));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
