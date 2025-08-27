package cf.witcheskitchen.common.entity.tameable;

import cf.witcheskitchen.api.entity.WKTameableEntity;
import cf.witcheskitchen.common.entity.ai.FerretBrain;
import cf.witcheskitchen.common.registry.WKEntityTypes;
import cf.witcheskitchen.common.registry.WKSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
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
import software.bernie.geckolib.animation.Animation;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.SplittableRandom;

public class FerretEntity extends WKTameableEntity implements GeoEntity, SmartBrainOwner<FerretEntity> {

    public static final TrackedData<Integer> TARGET_ID = DataTracker.registerData(FerretEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Boolean> NIGHT = DataTracker.registerData(FerretEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final Ingredient BREEDING_INGREDIENTS = Ingredient.ofItems(Items.RABBIT, Items.COOKED_RABBIT, Items.CHICKEN, Items.COOKED_CHICKEN, Items.EGG, Items.RABBIT_FOOT, Items.TURTLE_EGG);
    public static final Item TAMING_INGREDIENT = Items.EGG;
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public FerretEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.setTamed(false, true);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.MOVEMENT_SPEED, 0.5)
                .add(EntityAttributes.MAX_HEALTH, 6.0)
                .add(EntityAttributes.ATTACK_DAMAGE);
    }

    @Override
    protected Brain.Profile<?> createBrainProfile() {
        return new SmartBrainProvider<>(this);
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        int var = new SplittableRandom().nextInt(1, 13);
        this.setVariant(var);
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    protected void mobTick(ServerWorld world) {
        tickBrain(this);
        if (!this.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET) && this.getDataTracker().get(TARGET_ID) != 0) {
            this.getDataTracker().set(TARGET_ID, 0);
            stopRiding();
        }
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(NIGHT, false);
        builder.add(TARGET_ID, 0);
    }

    @Override
    public void baseTick() {
        super.baseTick();
        LivingEntity target = getTargetFromData();
        if (target != null && !this.hasVehicle() && !target.hasPassengers() && this.squaredDistanceTo(target) < 6) {
            this.startRiding(target, true);
        }
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        if (target != null) {
            this.getDataTracker().set(TARGET_ID, target.getId());
        } else {
            this.getDataTracker().set(TARGET_ID, 0);
        }
        super.setTarget(target);
    }

    @Nullable
    public LivingEntity getTargetFromData() {
        return this.getWorld().getEntityById(this.getDataTracker().get(TARGET_ID)) instanceof LivingEntity livingEntity ? livingEntity : null;
    }

    @Override
    public void writeCustomData(WriteView data) {
        super.writeCustomData(data);
        data.putBoolean("Sleep", this.isSleeping());
    }

    @Override
    public void readCustomData(ReadView data) {
        super.readCustomData(data);
        this.setSleeping(data.getBoolean("Sleep", false));
    }


    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getStackInHand(hand);
        if (!isTamed() && stack.isOf(TAMING_INGREDIENT)) {
            if (getWorld().isClient()) {
                return ActionResult.CONSUME;
            } else {
                if (!player.isCreative()) {
                    stack.decrement(1);
                }
                if (!getWorld().isClient()) {
                    if (this.random.nextInt(3) == 0) {
                        super.setOwner(player);
                        this.navigation.recalculatePath();
                        this.setTarget(null);
                        setSitting(true);
                        this.getWorld().sendEntityStatus(this, (byte) 7);
                    } else {
                        this.getWorld().sendEntityStatus(this, (byte) 6);
                    }
                }
                return ActionResult.SUCCESS;
            }
        }
        if (isTamed() && !this.getWorld().isClient() && hand == Hand.MAIN_HAND) {
            setSitting(!isSitting());
            return ActionResult.SUCCESS;
        }
        if (stack.isOf(TAMING_INGREDIENT)) {
            return ActionResult.PASS;
        }
        return super.interactMob(player, hand);
    }

    @Override
    public int getVariants() {
        return 12;
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        boolean bl = super.damage(world, source, amount);
        if (bl && source.getAttacker() instanceof LivingEntity l) {
            this.getBrain().remember(MemoryModuleType.ANGRY_AT, l.getUuid(), 20 * 10);
        }

        return bl;
    }

    @Override
    public boolean canBreedWith(AnimalEntity other) {
        if (!this.isTamed()) {
            return false;
        } else if (!(other instanceof FerretEntity ferretEntity)) {
            return false;
        } else {
            return ferretEntity.isTamed() && super.canBreedWith(other);
        }
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        FerretEntity ferretEntity = WKEntityTypes.FERRET.create(world, SpawnReason.BREEDING);
        LazyEntityReference<LivingEntity> owner = this.getOwnerReference();
        if (owner != null && ferretEntity != null) {
            ferretEntity.setOwner(this.getOwnerReference());
            ferretEntity.setTamed(true, true);
        }
        return ferretEntity;
    }

    @Override
    public boolean canBeLeashed() {
        return true;
    }

    @Override
    public boolean handleFallDamage(double fallDistance, float damagePerDistance, DamageSource damageSource) {
        return false;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENTS.test(stack);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (this.isTamed()) {
            if (this.isInLove()) {
                return WKSoundEvents.FERRET_CHIRP_EVENT;
            }
        }
        return WKSoundEvents.FERRET_IDLE_EVENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_FOX_HURT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.35F, 0.57F);
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
    protected final void initGoals() {
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controller) {
        controller.add(DefaultAnimations.genericIdleController()).add(new AnimationController<>("body", 0, this::predicate));
    }

    private PlayState predicate(AnimationTest<FerretEntity> state) {
        if (this.isSitting()) {
            state.setAnimation(RawAnimation.begin().then("sit", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else if (this.dataTracker.get(TARGET_ID) != 0 && this.hasVehicle()) {
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
