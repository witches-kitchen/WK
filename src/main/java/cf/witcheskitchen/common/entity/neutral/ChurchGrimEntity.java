package cf.witcheskitchen.common.entity.neutral;

import cf.witcheskitchen.api.entity.WKTameableEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfSoundVariants;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.DamageTypeTags;
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
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.SplittableRandom;
import java.util.UUID;

//Todo: This once structures are in
public class ChurchGrimEntity extends WKTameableEntity implements GeoEntity, Angerable, Tameable {
    private final int VARIANTS = 8;
    //Add a string or something here for a variant that is a white, short-haired dog and can appear if one is named Max
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public ChurchGrimEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return LivingEntity.createLivingAttributes().add(EntityAttributes.FOLLOW_RANGE, 32.0D)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.85D)
                .add(EntityAttributes.MAX_HEALTH, 35).add(EntityAttributes.ARMOR, 2.5D)
                .add(EntityAttributes.ATTACK_DAMAGE, 6.0D).add(EntityAttributes.ATTACK_KNOCKBACK, 0.35D);
    }

    @Override
    public int getVariants() {
        return VARIANTS;
    }

    @Override
    public int getAngerTime() {
        return 0;
    }

    @Override
    public void setAngerTime(int ticks) {

    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        SplittableRandom random = new SplittableRandom();
        int var = random.nextInt(0, 9);
        this.setVariant(var);
        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    public int getVariant() {
        return MathHelper.clamp(this.dataTracker.get(VARIANT), 0, VARIANTS);
    }

    public void setVariant(int variant) {
        this.dataTracker.set(VARIANT, variant);
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.5F, 0.7F);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.WOLF_SOUNDS.get(WolfSoundVariants.Type.CLASSIC).ambientSound().value();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.WOLF_SOUNDS.get(WolfSoundVariants.Type.CLASSIC).deathSound().value();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.WOLF_SOUNDS.get(WolfSoundVariants.Type.CLASSIC).hurtSound().value();
    }

    @Override
    public boolean canBreedWith(AnimalEntity other) {
        return false;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", this.getVariant());
        this.writeAngerToNbt(nbt);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        this.setVariant(tag.getInt("Variant").orElseThrow());
        this.readAngerFromNbt(this.getWorld(), tag);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new PounceAtTargetGoal(this, 0.4f));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1, true));
        this.goalSelector.add(4, new StopAndLookAtEntityGoal(this, MobEntity.class, 2.0f, 0.8f));
        this.goalSelector.add(6, new WanderAroundFarGoal(this, 0.8D, 1));
        this.targetSelector.add(0, new RevengeGoal(this).setGroupRevenge());
    }

    @Override
    public boolean shouldRender(double distance) {
        return true;
    }

    @Nullable
    @Override
    public UUID getAngryAt() {
        return null;
    }

    @Override
    public void setAngryAt(@Nullable UUID uuid) {

    }

    @Override
    public void chooseRandomAngerTime() {

    }

    @Override
    public boolean isFireImmune() {
        return true;
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        if (source.isOf(DamageTypes.FALLING_BLOCK) || source.isIn(DamageTypeTags.IS_FIRE) || source.isIn(DamageTypeTags.IS_FALL)) {
            return false;
        }
        return super.damage(world, source, amount);
    }

    @Override
    public boolean handleFallDamage(double fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    @Override
    protected void swimUpward(TagKey<Fluid> fluid) {
        super.swimUpward(fluid);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
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
