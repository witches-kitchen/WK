package cf.witcheskitchen.common.entity.tameable;

import cf.witcheskitchen.api.entity.WKTameableEntity;
import cf.witcheskitchen.common.entity.ai.FerretBrain;
import cf.witcheskitchen.common.registry.WKEntityTypes;
import cf.witcheskitchen.common.registry.WKSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
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
import software.bernie.geckolib.animation.Animation;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.SplittableRandom;

public class FerretEntity extends WKTameableEntity implements GeoEntity, SmartBrainOwner<FerretEntity> {

    public static final EntityDataAccessor<Integer> TARGET_ID = SynchedEntityData.defineId(FerretEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Boolean> NIGHT = SynchedEntityData.defineId(FerretEntity.class, EntityDataSerializers.BOOLEAN);
    public static final Ingredient BREEDING_INGREDIENTS = Ingredient.of(Items.RABBIT, Items.COOKED_RABBIT, Items.CHICKEN, Items.COOKED_CHICKEN, Items.EGG, Items.RABBIT_FOOT, Items.TURTLE_EGG);
    public static final Item TAMING_INGREDIENT = Items.EGG;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public FerretEntity(EntityType<? extends TamableAnimal> entityType, Level world) {
        super(entityType, world);
        this.setTame(false, true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.MAX_HEALTH, 6.0)
                .add(Attributes.ATTACK_DAMAGE);
    }

    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData entityData) {
        int var = new SplittableRandom().nextInt(1, 13);
        this.setVariant(var);
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    @Override
    protected void customServerAiStep(ServerLevel world) {
        tickBrain(this);
        if (!this.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET) && this.getEntityData().get(TARGET_ID) != 0) {
            this.getEntityData().set(TARGET_ID, 0);
            stopRiding();
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(NIGHT, false);
        builder.define(TARGET_ID, 0);
    }

    @Override
    public void baseTick() {
        super.baseTick();
        LivingEntity target = getTargetFromData();
        if (target != null && !this.isPassenger() && !target.isVehicle() && this.distanceToSqr(target) < 6) {
            this.startRiding(target, true);
        }
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        if (target != null) {
            this.getEntityData().set(TARGET_ID, target.getId());
        } else {
            this.getEntityData().set(TARGET_ID, 0);
        }
        super.setTarget(target);
    }

    @Nullable
    public LivingEntity getTargetFromData() {
        return this.level().getEntity(this.getEntityData().get(TARGET_ID)) instanceof LivingEntity livingEntity ? livingEntity : null;
    }

    @Override
    public void addAdditionalSaveData(ValueOutput data) {
        super.addAdditionalSaveData(data);
        data.putBoolean("Sleep", this.isSleeping());
    }

    @Override
    public void readAdditionalSaveData(ValueInput data) {
        super.readAdditionalSaveData(data);
        this.setSleeping(data.getBooleanOr("Sleep", false));
    }


    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        final ItemStack stack = player.getItemInHand(hand);
        if (!isTame() && stack.is(TAMING_INGREDIENT)) {
            if (level().isClientSide()) {
                return InteractionResult.CONSUME;
            } else {
                if (!player.isCreative()) {
                    stack.shrink(1);
                }
                if (!level().isClientSide()) {
                    if (this.random.nextInt(3) == 0) {
                        super.setOwner(player);
                        this.navigation.recomputePath();
                        this.setTarget(null);
                        setOrderedToSit(true);
                        this.level().broadcastEntityEvent(this, (byte) 7);
                    } else {
                        this.level().broadcastEntityEvent(this, (byte) 6);
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }
        if (isTame() && !this.level().isClientSide() && hand == InteractionHand.MAIN_HAND) {
            setOrderedToSit(!isOrderedToSit());
            return InteractionResult.SUCCESS;
        }
        if (stack.is(TAMING_INGREDIENT)) {
            return InteractionResult.PASS;
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public int getVariants() {
        return 12;
    }

    @Override
    public boolean hurtServer(ServerLevel world, DamageSource source, float amount) {
        boolean bl = super.hurtServer(world, source, amount);
        if (bl && source.getEntity() instanceof LivingEntity l) {
            this.getBrain().setMemoryWithExpiry(MemoryModuleType.ANGRY_AT, l.getUUID(), 20 * 10);
        }

        return bl;
    }

    @Override
    public boolean canMate(Animal other) {
        if (!this.isTame()) {
            return false;
        } else if (!(other instanceof FerretEntity ferretEntity)) {
            return false;
        } else {
            return ferretEntity.isTame() && super.canMate(other);
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        FerretEntity ferretEntity = WKEntityTypes.FERRET.create(world, EntitySpawnReason.BREEDING);
        EntityReference<LivingEntity> owner = this.getOwnerReference();
        if (owner != null && ferretEntity != null) {
            ferretEntity.setOwnerReference(this.getOwnerReference());
            ferretEntity.setTame(true, true);
        }
        return ferretEntity;
    }

    @Override
    public boolean canBeLeashed() {
        return true;
    }

    @Override
    public boolean causeFallDamage(double fallDistance, float damagePerDistance, DamageSource damageSource) {
        return false;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return BREEDING_INGREDIENTS.test(stack);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (this.isTame()) {
            if (this.isInLove()) {
                return WKSoundEvents.FERRET_CHIRP_EVENT;
            }
        }
        return WKSoundEvents.FERRET_IDLE_EVENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.FOX_HURT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.WOLF_STEP, 0.35F, 0.57F);
    }

    @Override
    public List<ExtendedSensor<FerretEntity>> getSensors() {
        return FerretBrain.getSensors();
    }

    @Override
    public BrainActivityGroup<FerretEntity> getCoreTasks() {
        return FerretBrain.getCoreTasks();
    }

    @Override
    public BrainActivityGroup<FerretEntity> getIdleTasks() {
        return FerretBrain.getIdleTasks(this);
    }

    @Override
    public BrainActivityGroup<FerretEntity> getFightTasks() {
        return FerretBrain.getFightTasks(this);
    }

    @Override
    protected final void registerGoals() {
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controller) {
        controller.add(DefaultAnimations.genericIdleController()).add(new AnimationController<>("body", 0, this::predicate));
    }

    private PlayState predicate(AnimationTest<FerretEntity> state) {
        if (this.isOrderedToSit()) {
            state.setAnimation(RawAnimation.begin().then("sit", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else if (this.entityData.get(TARGET_ID) != 0 && this.isPassenger()) {
            state.setAnimation(RawAnimation.begin().then("gore", Animation.LoopType.LOOP));
        } else if (state.isMoving()) {
            state.setAnimation(RawAnimation.begin().then("run", Animation.LoopType.LOOP));
        }
        return PlayState.STOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
