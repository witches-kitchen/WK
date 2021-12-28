package cf.witcheskitchen.common.entities.hostile;

import cf.witcheskitchen.api.WKHostileEntity;
import cf.witcheskitchen.common.registry.WKSounds;
import cf.witcheskitchen.common.registry.WKStatusEffects;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Random;
import java.util.SplittableRandom;

public class CuSithEntity extends WKHostileEntity implements IAnimatable {
    public static final TrackedData<Integer> VARIANT = DataTracker.registerData(WKHostileEntity.class,
            TrackedDataHandlerRegistry.INTEGER);
    public static final int EYE_VARIANTS = 7;
    private final AnimationFactory factory = new AnimationFactory(this);
    public int howlTimer = 1244;


    public CuSithEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.45D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20).add(EntityAttributes.GENERIC_ARMOR, 2.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.35D);
    }

    @Override //this adds basic ai
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this)); //need to make this the ability to walk on the body of water's floor.
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(2, new LookAtEntityGoal(this, VillagerEntity.class, 6.0f));
        this.goalSelector.add(2, new LookAtEntityGoal(this, GolemEntity.class, 6.0f));
        this.goalSelector.add(2, new LookAtEntityGoal(this, IllagerEntity.class, 6.0f));
        this.goalSelector.add(2, new LookAtEntityGoal(this, WitchEntity.class, 6.0f));
        this.goalSelector.add(2, new LookAtEntityGoal(this, CowEntity.class, 6.0f));
        this.goalSelector.add(2, new LookAtEntityGoal(this, PiglinEntity.class, 6.0f));
        this.goalSelector.add(2, new LookAtEntityGoal(this, PiglinBruteEntity.class, 6.0f));
        this.goalSelector.add(1, new PounceAtTargetGoal(this, 0.4f));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1, true));
        this.goalSelector.add(4, new StopAndLookAtEntityGoal(this, MobEntity.class, 2.0f, 0.8f));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 0.8D, 1));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, VillagerEntity.class, false));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, GolemEntity.class, false));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, WitchEntity.class, false));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, CowEntity.class, false));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, PiglinEntity.class, false));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, PiglinBruteEntity.class, false));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, LivingEntity.class, 10, false, false, entity -> entity.getGroup() == EntityGroup.ILLAGER));
        this.targetSelector.add(0, new RevengeGoal(this).setGroupRevenge());
    }

    @Override
    public float getEyeHeight(EntityPose pose) {
        return super.getEyeHeight(pose);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public void tick() {
        super.tick();
        if (!world.isClient && !hasCustomName() && world.isDay() && !world.isRaining() && !world.isThundering() && world.isSkyVisibleAllowingSea(getBlockPos())) {
            remove(Entity.RemovalReason.KILLED);
        }
    }

    @Override
    public boolean tryAttack(Entity target) {
        boolean flag = super.tryAttack(target);
        Random rand = new Random();
        int i = rand.nextInt(100);
        if (i <= 33) {
            if (target instanceof LivingEntity) {
                ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(WKStatusEffects.HORROR, 1000));
                this.playSound(WKSounds.CUSITH_HOWL_EVENT, 0.8F, 0.7F);
            }
        }
        return flag;
    }

    @Override
    public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {
        this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 4000, 1, true, true), this);
        this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 4000, 1, true, true), this);
        this.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 4000, 1, true, true), this);
        this.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 250, 1, true, true), this);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("run", true));
            return PlayState.CONTINUE;
        }
        Random rand = new Random();
        int i = rand.nextInt(100);
        if (howlTimer > 0) howlTimer--;
        if (i < 5 && howlTimer == 0) {
            switch (rand.nextInt(2)) {
                case 0 -> {
                    if (!event.isMoving() && !isAttacking()) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("howl", false));
                        howlTimer = 1244;
                        this.playSound(WKSounds.CUSITH_HOWL_EVENT, 0.8F, 0.7F);
                        return PlayState.CONTINUE;
                    }
                }
                case 1 -> {
                    if (!event.isMoving() && !isAttacking()) {
                        event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", false));
                        howlTimer = 616;
                        return PlayState.CONTINUE;
                    }
                }
            }
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    protected void swimUpward(Tag<Fluid> fluid) {
        super.swimUpward(fluid);
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        SplittableRandom random = new SplittableRandom();
        int var = random.nextInt(0, 8);
        this.setVariant(var);
        this.dataTracker.set(VARIANT, random.nextInt(EYE_VARIANTS));
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }


    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(VARIANT, 0);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", this.getVariant());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        this.setVariant(tag.getInt("Variant"));
    }

    public int getVariant() {
        return MathHelper.clamp(this.dataTracker.get(VARIANT), 1, 8);
    }

    public void setVariant(int variant) {
        this.dataTracker.set(VARIANT, variant);
    }

    @Override
    public int getVariants() {
        return 7;
    }

    //to-do: add subtitle info
    @Override
    protected SoundEvent getAmbientSound() {
        return WKSounds.CUSITH_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return WKSounds.CUSITH_DEATH_EVENT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.5F, 0.7F);
    }

    @Override
    public boolean isUndead() {
        return true;
    }

    @Override
    public boolean canBreatheInWater() {
        return true;
    }
}
