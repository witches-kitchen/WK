package cf.witcheskitchen.common.entity.hostile;

import cf.witcheskitchen.api.entity.WKHostileEntity;
import cf.witcheskitchen.common.entity.tameable.FerretEntity;
import cf.witcheskitchen.common.registry.WKSoundEvents;
import cf.witcheskitchen.common.registry.WKStatusEffects;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.util.Random;
import java.util.SplittableRandom;

public class CuSithEntity extends WKHostileEntity implements GeoEntity {
    public static final int EYE_VARIANTS = 7;
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);


    public CuSithEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.45D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20).add(EntityAttributes.GENERIC_ARMOR, 2.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.35D);
    }

    @Override
    public boolean shouldRender(double distance) {
        return true;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
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
        this.goalSelector.add(2, new LookAtEntityGoal(this, SheepEntity.class, 6.0f));
        this.goalSelector.add(2, new LookAtEntityGoal(this, GoatEntity.class, 6.0f));
        this.goalSelector.add(1, new PounceAtTargetGoal(this, 0.4f));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1, true));
        this.goalSelector.add(4, new StopAndLookAtEntityGoal(this, MobEntity.class, 2.0f, 0.8f));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 0.8D, 1));
        this.targetSelector.add(3, new TargetGoal<>(this, PlayerEntity.class, false));
        this.targetSelector.add(3, new TargetGoal<>(this, VillagerEntity.class, false));
        this.targetSelector.add(3, new TargetGoal<>(this, GolemEntity.class, false));
        this.targetSelector.add(3, new TargetGoal<>(this, WitchEntity.class, false));
        this.targetSelector.add(3, new TargetGoal<>(this, CowEntity.class, false));
        this.targetSelector.add(3, new TargetGoal<>(this, PiglinEntity.class, false));
        this.targetSelector.add(3, new TargetGoal<>(this, PiglinBruteEntity.class, false));
        this.targetSelector.add(3, new TargetGoal<>(this, SheepEntity.class, false));
        this.targetSelector.add(3, new TargetGoal<>(this, GoatEntity.class, false));
        this.targetSelector.add(3, new TargetGoal<>(this, FerretEntity.class, false));
        this.targetSelector.add(3, new TargetGoal<>(this, LivingEntity.class, 10, false, false, entity -> entity.getGroup() == EntityGroup.ILLAGER));
        this.targetSelector.add(0, new RevengeGoal(this).setGroupRevenge());
    }

    @Override
    public float getEyeHeight(EntityPose pose) {
        return super.getEyeHeight(pose);
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }

    @Override
    public boolean isPushable() {
        return false;
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
                this.playSound(WKSoundEvents.CUSITH_HOWL_EVENT, 0.8F, 0.7F);
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

    @Override
    protected void swimUpward(TagKey<Fluid> fluid) {
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

    @Override
    protected SoundEvent getAmbientSound() {
        return WKSoundEvents.CUSITH_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return WKSoundEvents.CUSITH_DEATH_EVENT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.5F, 0.7F);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.isFallingBlock() || source.isFire() || source.isFromFalling()) {
            return false;
        }
        return super.damage(source, amount);
    }

    @Override
    public boolean isFireImmune() {
        return true;
    }

    @Override
    public boolean isUndead() {
        return true;
    }

    @Override
    public boolean canBreatheInWater() {
        return true;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericIdleController(this), new AnimationController<>(this, "move", 20, this::legTransform));
    }

    private PlayState legTransform(AnimationState<CuSithEntity> state) {
        if (state.isMoving()) {
            state.setAnimation(RawAnimation.begin().then("run", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
