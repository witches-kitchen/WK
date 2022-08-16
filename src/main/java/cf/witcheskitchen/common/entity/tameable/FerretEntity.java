package cf.witcheskitchen.common.entity.tameable;

import cf.witcheskitchen.api.WKApi;
import cf.witcheskitchen.api.WKTameableEntity;
import cf.witcheskitchen.common.registry.WKEntityTypes;
import cf.witcheskitchen.common.registry.WKSoundEvents;
import com.google.common.collect.Sets;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Set;
import java.util.SplittableRandom;
import java.util.UUID;
import java.util.function.Predicate;

public class FerretEntity extends WKTameableEntity implements IAnimatable, IAnimationTickable, Angerable {

    //FIXME: Figure out why this won't breed!
    //FIXME: This entire damn mob
    public static final Ingredient BREEDING_INGREDIENTS;
    public static final Set<Item> TAMING_INGREDIENTS;
    public static final Predicate<LivingEntity> FLEE_SUPERNATURAL;
    public static final TrackedData<Integer> VARIANT = DataTracker.registerData(FerretEntity.class,
            TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Boolean> NIGHT = DataTracker.registerData(FerretEntity.class,
            TrackedDataHandlerRegistry.BOOLEAN);
    private static final UniformIntProvider ANGER_TIME_RANGE;
    private static final TrackedData<Integer> ANGER_TIME;

    static {
        ANGER_TIME = DataTracker.registerData(FerretEntity.class, TrackedDataHandlerRegistry.INTEGER);
        ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
        BREEDING_INGREDIENTS = Ingredient.ofItems(Items.RABBIT, Items.COOKED_RABBIT, Items.CHICKEN, Items.COOKED_CHICKEN, Items.EGG, Items.RABBIT_FOOT, Items.TURTLE_EGG);
        TAMING_INGREDIENTS = Sets.newHashSet(Items.RABBIT, Items.COOKED_RABBIT, Items.CHICKEN, Items.COOKED_CHICKEN, Items.EGG, Items.RABBIT_FOOT, Items.TURTLE_EGG);
        FLEE_SUPERNATURAL = (entity) -> {
            EntityType<?> entityType = entity.getType();
            return entityType == WKEntityTypes.CUSITH || WKApi.isGreaterDemon(entity);
        };
    }

    private final AnimationFactory factory = new AnimationFactory(this);
    @Nullable
    private UUID targetUuid;

    public FerretEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
        this.setTamed(false);
    }


    public static DefaultAttributeContainer.Builder createAttributes() {
        return LivingEntity.createLivingAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 25.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.65D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10).add(EntityAttributes.GENERIC_ARMOR, 0.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0D).add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.15D);
    }

    public boolean isSleeping() {
        return this.dataTracker.get(NIGHT);
    }

    public void setSleeping(boolean sleeping) {
        this.dataTracker.set(NIGHT, sleeping);
    }

    //Todo: Custom goal where ferrets and other tameables from this mod flee greater demons, which are defined by a tag.
    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new LookAtEntityGoal(this, RabbitEntity.class, 12.0f));
        this.goalSelector.add(1, new LookAtEntityGoal(this, ChickenEntity.class, 12.0f));
        this.goalSelector.add(3, new AnimalMateGoal(this, 1.0D));
        this.goalSelector.add(3, new FollowParentGoal(this, 1.25D));
        this.goalSelector.add(5, new AttackGoal(this));
        this.goalSelector.add(5, new SitGoal(this));
        this.goalSelector.add(7, new FollowMobGoal(this, 1.0D, 3.0F, 7.0F));
        this.goalSelector.add(7, new FollowOwnerGoal(this, 1.0D, 3.0F, 10.0F, false));
        this.goalSelector.add(9, new MeleeAttackGoal(this, 1, true));
        this.goalSelector.add(10, new StopAndLookAtEntityGoal(this, MobEntity.class, 2.0f, 0.8f));
        this.goalSelector.add(11, new WanderAroundFarGoal(this, 0.8D, 1.0000001E-5F));
        this.goalSelector.add(12, new FleeEntityGoal<>(this, LivingEntity.class, 16, 1, 3, FLEE_SUPERNATURAL));
        this.targetSelector.add(1, new TargetGoal<>(this, PlayerEntity.class, 10, true, false, this::shouldAngerAt));
        this.targetSelector.add(2, new UntamedTargetGoal<>(this, RabbitEntity.class, false, null));
        this.targetSelector.add(2, new UntamedTargetGoal<>(this, ChickenEntity.class, false, null));
        this.targetSelector.add(4, new RevengeGoal(this).setGroupRevenge());
        this.targetSelector.add(5, new UniversalAngerGoal<>(this, true));
        this.targetSelector.add(6, new TrackOwnerAttackerGoal(this));
        this.targetSelector.add(7, new AttackWithOwnerGoal(this));
    }

    @Override
    public int getVariants() {
        return 12;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        FerretEntity ferretEntity = WKEntityTypes.FERRET.create(world);
        UUID uUID = this.getOwnerUuid();
        if (uUID != null) {
            ferretEntity.setOwnerUuid(uUID);
            ferretEntity.setTamed(true);
        }

        return ferretEntity;
    }

    @Override
    public boolean tryAttack(Entity target) {
        return target.damage(DamageSource.mob(this), 3.0F);
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        if (tamed) {
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(15.0D);
            this.setHealth(15.0F);
        } else {
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(8.0D);
        }

        this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(4.0D);
    }


    @Override
    protected void swimUpward(TagKey<Fluid> fluid) {
        super.swimUpward(fluid);
    }

    public int getVariant() {
        return MathHelper.clamp(this.dataTracker.get(VARIANT), 1, 13);
    }

    public void setVariant(int variant) {
        this.dataTracker.set(VARIANT, variant);
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        SplittableRandom random = new SplittableRandom();
        int var = random.nextInt(0, 13);
        this.setVariant(var);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Sleep", this.isSleeping());
        nbt.putInt("Variant", this.getVariant());
        this.writeAngerToNbt(nbt);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        this.setSleeping(tag.getBoolean("Sleep"));
        this.setVariant(tag.getInt("Variant"));
        this.readAngerFromNbt(this.world, tag);
    }

    @Override
    public float getEyeHeight(EntityPose pose) {
        return super.getEyeHeight(pose);
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

    @Override
    public boolean shouldRender(double distance) {
        return true;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            Entity entity = source.getAttacker();
            this.setSitting(false);
            if (entity != null && !(entity instanceof PlayerEntity) && !(entity instanceof PersistentProjectileEntity)) {
                amount = (amount + 1.0F) / 2.0F;
            }

            return super.damage(source, amount);
        }
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return true;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(NIGHT, false);
        this.dataTracker.startTracking(VARIANT, 0);
        this.dataTracker.startTracking(ANGER_TIME, 0);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (!this.isTamed() && TAMING_INGREDIENTS.contains(itemStack.getItem())) {
            if (!player.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }

            if (!this.isSilent()) {
                this.world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_FOX_EAT, this.getSoundCategory(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
            }

            if (!this.world.isClient) {
                if (this.random.nextInt(10) == 0) {
                    this.setOwner(player);
                    this.world.sendEntityStatus(this, (byte) 7);
                } else {
                    this.world.sendEntityStatus(this, (byte) 6);
                }
            }

            return ActionResult.success(this.world.isClient);
        } else if (this.isTamed() && this.isOwner(player)) {
            if (!this.world.isClient) {
                this.setSitting(!this.isSitting());
            }

            return ActionResult.success(this.world.isClient);
        } else {
            return super.interactMob(player, hand);
        }
    }


    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENTS.test(stack);
    }

    @Override
    public boolean canAttackWithOwner(LivingEntity target, LivingEntity owner) {
        if (!(target instanceof CreeperEntity) && !(target instanceof GhastEntity)) {
            if (target instanceof FerretEntity ferretEntity) {
                return !ferretEntity.isTamed() || ferretEntity.getOwner() != owner;
            } else if (target instanceof PlayerEntity && owner instanceof PlayerEntity && !((PlayerEntity) owner).shouldDamagePlayer((PlayerEntity) target)) {
                return false;
            } else if (target instanceof HorseBaseEntity && ((HorseBaseEntity) target).isTame()) {
                return false;
            } else {
                return !(target instanceof TameableEntity) || !((TameableEntity) target).isTamed();
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean canAvoidTraps() {
        return true;
    }

    //TODO: Rework animations for sitting at night and day
    //FIXME: Make it sit during the day, wait during the night. Jesus fucking Christ this thing is cursed.
    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving() && !isSitting()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("run", true));
            return PlayState.CONTINUE;
        }
        if (!event.isMoving() && !isAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
            return PlayState.CONTINUE;
        }
        if (!event.isMoving() && isSitting()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("sit", true));
            return PlayState.CONTINUE;
        }
        //Todo: When this gets in game, figure out how to set up the proper conditions for it to appear. (Around the ankles of other mobs)
        if (isAttacking() && !isSitting()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("gore", true));
            return PlayState.CONTINUE;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    //FIXME: For some reason, this makes ferrets float
    //@Override
    //public void tickMovement() {
    //    this.setSleeping(this.world.isNight());
    //}

    @Override
    public int tickTimer() {
        return age;
    }

    public void tick() {
        super.tick();
    }

    @Override
    public int getAngerTime() {
        return this.dataTracker.get(ANGER_TIME);
    }

    @Override
    public void setAngerTime(int ticks) {
        this.dataTracker.set(ANGER_TIME, ticks);
    }

    @Nullable
    @Override
    public UUID getAngryAt() {
        return this.targetUuid;
    }

    @Override
    public void setAngryAt(@Nullable UUID uuid) {
        this.targetUuid = uuid;
    }

    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
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

    /*@Override
    protected SoundEvent getDeathSound() {
        return WKSounds.;
    }*/

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.35F, 0.57F);
    }
}
