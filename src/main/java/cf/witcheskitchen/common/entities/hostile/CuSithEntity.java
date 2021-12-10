package cf.witcheskitchen.common.entities.hostile;

import cf.witcheskitchen.api.WKHostileEntity;
import cf.witcheskitchen.common.registry.WKSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.NbtCompound;
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

import java.util.SplittableRandom;

public class CuSithEntity extends WKHostileEntity implements IAnimatable {
    public static final TrackedData<Integer> VARIANT = DataTracker.registerData(WKHostileEntity.class,
            TrackedDataHandlerRegistry.INTEGER);
    public static final int EYE_VARIANTS = 7;

    private final AnimationFactory factory = new AnimationFactory(this);


    public CuSithEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20).add(EntityAttributes.GENERIC_ARMOR, 2.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.35D);
    }

    @Override //this adds basic ai
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this)); //need to make this the ability to walk on the body of water's floor. 
        this.goalSelector.add(1, new WanderNearTargetGoal(this, 0.45d, 64));
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(3, new WanderAroundGoal(this, 0.35D));
        this.goalSelector.add(1, new PounceAtTargetGoal(this, 0.4f));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1, true));
        this.goalSelector.add(4, new StopAndLookAtEntityGoal(this, MobEntity.class, 2.0f, 0.8f));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, MerchantEntity.class, false));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, IronGolemEntity.class, true));
        this.targetSelector.add(0, new RevengeGoal(this).setGroupRevenge());
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving() && !this.isSwimming()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("run", true));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
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

    protected int getInterval(PathAwareEntity mob) {
        return 1;
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
