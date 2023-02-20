package cf.witcheskitchen.common.entity.tameable;

import cf.witcheskitchen.api.WKApi;
import cf.witcheskitchen.api.WKTameableEntity;
import cf.witcheskitchen.common.entity.ai.FerretBrain;
import cf.witcheskitchen.common.entity.ai.sensor.TamableSensor;
import cf.witcheskitchen.common.entity.ai.sensor.TimeOfDaySensor;
import cf.witcheskitchen.common.entity.ai.task.DontMoveTask;
import cf.witcheskitchen.common.entity.ai.task.FerretMeleeAttackTask;
import cf.witcheskitchen.common.entity.ai.task.FollowOwnerTask;
import cf.witcheskitchen.common.registry.WKEntityTypes;
import cf.witcheskitchen.common.registry.WKSoundEvents;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.TagKey;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.AvoidSun;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.List;
import java.util.Optional;
import java.util.SplittableRandom;
import java.util.UUID;
import java.util.function.Predicate;

public class FerretEntity extends WKTameableEntity implements IAnimatable, SmartBrainOwner<FerretEntity> {

    public static final TrackedData<Integer> TARGET_ID = DataTracker.registerData(FerretEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Boolean> NIGHT = DataTracker.registerData(FerretEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public static final Ingredient BREEDING_INGREDIENTS = Ingredient.ofItems(Items.RABBIT, Items.COOKED_RABBIT, Items.CHICKEN, Items.COOKED_CHICKEN, Items.EGG, Items.RABBIT_FOOT, Items.TURTLE_EGG);
    public static final Item TAMING_INGREDIENT = Items.EGG;

    public FerretEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.setTamed(false);
    }

    @Override
    protected Brain.Profile<?> createBrainProfile() {
        return new SmartBrainProvider<>(this);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.5)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE);
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        int var = new SplittableRandom().nextInt(0, 13);
        this.setVariant(var);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected void mobTick() {
        tickBrain(this);
        if(!this.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET) && this.getDataTracker().get(TARGET_ID) != 0){
            this.getDataTracker().set(TARGET_ID, 0);
            stopRiding();
        }
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(NIGHT, false);
        this.dataTracker.startTracking(TARGET_ID, 0);
    }

    @Override
    public void baseTick() {
        super.baseTick();
        LivingEntity target = getTargetFromData();
        if(target != null && !this.hasVehicle() && !target.hasPassengers() && this.squaredDistanceTo(target) < 6){
            this.startRiding(target, true);
        }
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        if(target != null){
            this.getDataTracker().set(TARGET_ID, target.getId());
        }else{
            this.getDataTracker().set(TARGET_ID, 0);
        }
        super.setTarget(target);
    }

    @Nullable
    public LivingEntity getTargetFromData(){
        return this.world.getEntityById(this.getDataTracker().get(TARGET_ID)) instanceof LivingEntity livingEntity ? livingEntity : null;
    }

    public int getVariant() {
        return MathHelper.clamp(this.dataTracker.get(VARIANT), 1, 13);
    }

    public void setVariant(int variant) {
        this.dataTracker.set(VARIANT, variant);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Sleep", this.isSleeping());
        nbt.putInt("Variant", this.getVariant());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setSleeping(nbt.getBoolean("Sleep"));
        this.setVariant(nbt.getInt("Variant"));
    }


    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getStackInHand(hand);
        if (!isTamed() && stack.isOf(TAMING_INGREDIENT)) {
            if (world.isClient()) {
                return ActionResult.CONSUME;
            } else {
                if (!player.isCreative()) {
                    stack.decrement(1);
                }
                if (!world.isClient()) {
                    if (this.random.nextInt(3) == 0) {
                        super.setOwner(player);
                        this.navigation.recalculatePath();
                        this.setTarget(null);
                        setSitting(true);
                        this.world.sendEntityStatus(this, (byte) 7);
                    } else {
                        this.world.sendEntityStatus(this, (byte) 6);
                    }
                }
                return ActionResult.SUCCESS;
            }
        }
        if (isTamed() && !this.world.isClient() && hand == Hand.MAIN_HAND) {
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
    public boolean damage(DamageSource source, float amount) {
        boolean bl = super.damage(source, amount);
        if (this.world.isClient) {
            return false;
        } else {
            if (bl && source.getAttacker() instanceof LivingEntity l) {
                this.getBrain().remember(MemoryModuleType.ANGRY_AT, l.getUuid(), 20 * 10);
            }

            return bl;
        }
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
        FerretEntity ferretEntity = WKEntityTypes.FERRET.create(world);
        UUID uUID = this.getOwnerUuid();
        if (uUID != null && ferretEntity != null) {
            ferretEntity.setOwnerUuid(uUID);
            ferretEntity.setTamed(true);
        }
        return ferretEntity;
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return true;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENTS.test(stack);
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        AnimationBuilder builder = new AnimationBuilder();
        if (this.isSitting()) {
            builder.addAnimation("sit", ILoopType.EDefaultLoopTypes.LOOP);
            event.getController().setAnimation(builder);
            return PlayState.CONTINUE;
        } else if (this.dataTracker.get(TARGET_ID) != 0 && this.hasVehicle()) {
            builder.addAnimation("gore", ILoopType.EDefaultLoopTypes.LOOP);
        } else if (event.isMoving()) {
            builder.addAnimation("run", ILoopType.EDefaultLoopTypes.LOOP);
        } else {
            builder.addAnimation("idle", ILoopType.EDefaultLoopTypes.LOOP);
        }
        event.getController().setAnimation(builder);
        return PlayState.CONTINUE;
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
        return FerretBrain.getIdleTasks();
    }

    @Override
    public BrainActivityGroup<FerretEntity> getFightTasks() {
        return FerretBrain.getFightTasks(this);
    }

    @Override
    protected final void initGoals() {}
}
