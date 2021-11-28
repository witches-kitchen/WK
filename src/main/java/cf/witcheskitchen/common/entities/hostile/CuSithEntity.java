package cf.witcheskitchen.common.entities.hostile;

import cf.witcheskitchen.common.registry.WKSounds;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.Tag;
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

    private final AnimationFactory factory = new AnimationFactory(this);


    public CuSithEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 20).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.65);
    }

    //code for the below two groups inspired by or modified from grizzly bear mod, capybara mod, and adventurez mod
   /* public static DefaultAttributeContainer.Builder createCuSithAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 55.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.85D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0D).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 2.2D).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 46.0D);
    }*/

    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new EscapeDangerGoal(this, 2.0d));
        this.goalSelector.add(1, new WanderAroundGoal(this, 0.85D));
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(3, new FollowTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(1, (new FollowTargetGoal<>(this, PlayerEntity.class, true)).setMaxTimeWithoutVisibility(300));//from grizzly bear mod
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
        return PlayState.STOP;
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
        int var = random.nextInt(0, 7);
        this.setVariant(var);
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
        return MathHelper.clamp(this.dataTracker.get(VARIANT), 1, 6);
    }


    public void setVariant(int variant) {
        this.dataTracker.set(VARIANT, variant);
    }

    @Override
    public int getVariants() {
        return 6;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return WKSounds.CUSITH_IDLE_EVENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return WKSounds.CUSITH_DEATH_EVENT;
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
