package cf.witcheskitchen.common.entity.neutral;

import cf.witcheskitchen.api.entity.WKTameableEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.wolf.WolfSoundVariants;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.SplittableRandom;
import java.util.UUID;

//Todo: This once structures are in
public class ChurchGrimEntity extends WKTameableEntity implements GeoEntity, NeutralMob, OwnableEntity {
    private final int VARIANTS = 8;
    //Add a string or something here for a variant that is a white, short-haired dog and can appear if one is named Max
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public ChurchGrimEntity(EntityType<? extends TamableAnimal> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 32.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.85D)
            .add(Attributes.MAX_HEALTH, 35).add(Attributes.ARMOR, 2.5D)
            .add(Attributes.ATTACK_DAMAGE, 6.0D).add(Attributes.ATTACK_KNOCKBACK, 0.35D);
    }

    @Override
    public int getVariants() {
        return VARIANTS;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return 0;
    }

    @Override
    public void setRemainingPersistentAngerTime(int ticks) {

    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData entityData) {
        SplittableRandom random = new SplittableRandom();
        int var = random.nextInt(0, 9);
        this.setVariant(var);
        return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    public int getVariant() {
        return Mth.clamp(this.entityData.get(VARIANT), 0, VARIANTS);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.WOLF_STEP, 0.5F, 0.7F);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.WOLF_SOUNDS.get(WolfSoundVariants.SoundSet.CLASSIC).ambientSound().value();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.WOLF_SOUNDS.get(WolfSoundVariants.SoundSet.CLASSIC).deathSound().value();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.WOLF_SOUNDS.get(WolfSoundVariants.SoundSet.CLASSIC).hurtSound().value();
    }

    @Override
    public boolean canMate(Animal other) {
        return false;
    }

    @Override
    public void addAdditionalSaveData(ValueOutput data) {
        super.addAdditionalSaveData(data);
        data.putInt("Variant", this.getVariant());
        this.addPersistentAngerSaveData(data);
    }

    @Override
    public void readAdditionalSaveData(ValueInput data) {
        super.readAdditionalSaveData(data);
        this.setVariant(data.getIntOr("Variant", 0));
        this.readPersistentAngerSaveData(this.level(), data);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new LeapAtTargetGoal(this, 0.4f));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1, true));
        this.goalSelector.addGoal(4, new InteractGoal(this, Mob.class, 2.0f, 0.8f));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.8D, 1));
        this.targetSelector.addGoal(0, new HurtByTargetGoal(this).setAlertOthers());
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return null;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID uuid) {

    }

    @Override
    public void startPersistentAngerTimer() {

    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean hurtServer(ServerLevel world, DamageSource source, float amount) {
        if (source.is(DamageTypes.FALLING_BLOCK) || source.is(DamageTypeTags.IS_FIRE) || source.is(DamageTypeTags.IS_FALL)) {
            return false;
        }
        return super.hurtServer(world, source, amount);
    }

    @Override
    public boolean causeFallDamage(double fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected void jumpInLiquid(TagKey<Fluid> fluid) {
        super.jumpInLiquid(fluid);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        return null;
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controller) {
        controller.add(DefaultAnimations.genericIdleController());
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
