package cf.witcheskitchen.common.entities.hostile;

import cf.witcheskitchen.common.registry.WKSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
//import net.minecraft.entity.ai.*;
//import net.minecraft.entity.ai.brain.task.*;
//import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
//import net.minecraft.entity.mob.Monster;
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

    private final AnimationFactory factory = new AnimationFactory(this);


    public CuSithEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0d).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.45d);
    }

    @Override //this adds basic ai
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this)); //need to make this the ability to walk on the body of water's floor. 
        this.goalSelector.add(1, new WanderNearTargetGoal(this, 0.85d, 400));
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(3, new WanderAroundGoal(this, 0.85D));
        this.goalSelector.add(4, new StopAndLookAtEntityGoal(this, MobEntity.class, 2.0f, 0.8f));
        //this.goalSelector.add(5, new MeleeAttackGoal(this, 1.25D, true)); //will implement basic attack goal, but still breaks game
        this.targetSelector.add(1, (new RevengeGoal(this, new Class[] { WolfEntity.class })));
        this.targetSelector.add(2, (new RevengeGoal(this, new Class[] { CuSithEntity.class })));
        this.targetSelector.add(3, (new FollowTargetGoal<>(this, PlayerEntity.class, true)).setMaxTimeWithoutVisibility(400));//from grizzly bear mod
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
        if (!event.isMoving() && !this.isSwimming()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
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
