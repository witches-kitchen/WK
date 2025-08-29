package cf.witcheskitchen.common.entity.hostile;

import cf.witcheskitchen.api.entity.WKHostileEntity;
import cf.witcheskitchen.common.entity.tameable.FerretEntity;
import cf.witcheskitchen.common.registry.WKSoundEvents;
import cf.witcheskitchen.common.registry.WKStatusEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.processing.AnimationController;
import software.bernie.geckolib.animatable.processing.AnimationTest;
import software.bernie.geckolib.animation.Animation;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.constant.DefaultAnimations;

import java.util.Random;
import java.util.SplittableRandom;

public class CuSithEntity extends WKHostileEntity implements GeoEntity {
    public static final int EYE_VARIANTS = 7;
    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);


    public CuSithEntity(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 25.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.45D)
                .add(Attributes.MAX_HEALTH, 20).add(Attributes.ARMOR, 2.0D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D).add(Attributes.ATTACK_KNOCKBACK, 0.35D);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }

    @Override
    public boolean causeFallDamage(double fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this)); //need to make this the ability to walk on the body of water's floor.
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Villager.class, 6.0f));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, AbstractGolem.class, 6.0f));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, AbstractIllager.class, 6.0f));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Witch.class, 6.0f));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Cow.class, 6.0f));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Piglin.class, 6.0f));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, PiglinBrute.class, 6.0f));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Sheep.class, 6.0f));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Goat.class, 6.0f));
        this.goalSelector.addGoal(1, new LeapAtTargetGoal(this, 0.4f));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1, true));
        this.goalSelector.addGoal(4, new InteractGoal(this, Mob.class, 2.0f, 0.8f));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.8D, 1));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Villager.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractGolem.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Witch.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Cow.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Piglin.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PiglinBrute.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Sheep.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Goat.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, FerretEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, false, false, (entity, world) -> entity.getType().is(EntityTypeTags.ILLAGER)));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this).setAlertOthers());
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide && !hasCustomName() && level().isBrightOutside() && !level().isRaining() && !level().isThundering() && level().canSeeSkyFromBelowWater(blockPosition())) {
            remove(Entity.RemovalReason.KILLED);
        }
    }

    @Override
    public boolean doHurtTarget(ServerLevel world, Entity target) {
        boolean flag = super.doHurtTarget(world, target);
        Random rand = new Random();
        int i = rand.nextInt(100);
        if (i <= 33) {
            if (target instanceof LivingEntity) {
                ((LivingEntity) target).addEffect(new MobEffectInstance(WKStatusEffects.HORROR, 1000));
                this.playSound(WKSoundEvents.CUSITH_HOWL_EVENT, 0.8F, 0.7F);
            }
        }
        return flag;
    }

    @Override
    public void thunderHit(ServerLevel world, LightningBolt lightning) {
        this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 4000, 1, true, true), this);
        this.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 4000, 1, true, true), this);
        this.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 4000, 1, true, true), this);
        this.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 250, 1, true, true), this);
    }

    @Override
    protected void jumpInLiquid(TagKey<Fluid> fluid) {
        super.jumpInLiquid(fluid);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData entityData) {
        SplittableRandom random = new SplittableRandom();
        int var = random.nextInt(0, 8);
        this.setVariant(var);
        this.entityData.set(VARIANT, random.nextInt(EYE_VARIANTS));
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    @Override
    public void addAdditionalSaveData(ValueOutput data) {
        super.addAdditionalSaveData(data);
        data.putInt("Variant", this.getVariant());
    }

    @Override
    public void readAdditionalSaveData(ValueInput data) {
        super.readAdditionalSaveData(data);
        this.setVariant(data.getIntOr("Variant", 0));
    }

    public int getVariant() {
        return Mth.clamp(this.entityData.get(VARIANT), 1, 8);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
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
        this.playSound(SoundEvents.WOLF_STEP, 0.5F, 0.7F);
    }

    @Override
    public boolean hurtServer(ServerLevel world, DamageSource source, float amount) {
        if (source.is(DamageTypes.FALLING_BLOCK) || source.is(DamageTypeTags.IS_FIRE) || source.is(DamageTypeTags.IS_FALL)) {
            return false;
        }
        return super.hurtServer(world, source, amount);
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericIdleController(), new AnimationController<>("move", 20, this::legTransform));
    }

    private PlayState legTransform(AnimationTest<CuSithEntity> state) {
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
