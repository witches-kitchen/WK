package cf.witcheskitchen.common.entity.tameable;

import cf.witcheskitchen.api.entity.WKTameableEntity;
import cf.witcheskitchen.common.entity.ai.HedgehogBrain;
import cf.witcheskitchen.common.registry.WKEntityTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
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

    public HedgehogEntity(EntityType<? extends TamableAnimal> entityType, Level world) {
        super(entityType, world);
        this.setTame(false, true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.MAX_HEALTH, 6.0)
                .add(Attributes.ATTACK_DAMAGE);
    }

    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    protected void customServerAiStep(ServerLevel world) {
        tickBrain(this);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData entityData) {
        int var = new SplittableRandom().nextInt(1, 7);
        this.setVariant(var);
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    @Override
    public int getVariants() {
        return 6;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        HedgehogEntity hedgehogEntity = WKEntityTypes.HEDGEHOG.create(world, EntitySpawnReason.BREEDING);
        EntityReference<LivingEntity> owner = this.getOwnerReference();
        if (owner != null && hedgehogEntity != null) {
            hedgehogEntity.setOwnerReference(owner);
            hedgehogEntity.setTame(true, true);
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
        if (this.isOrderedToSit()) {
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
