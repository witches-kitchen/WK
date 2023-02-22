package cf.witcheskitchen.common.entity.tameable;

import cf.witcheskitchen.api.WKTameableEntity;
import cf.witcheskitchen.common.entity.ai.HedgehogBrain;
import cf.witcheskitchen.common.registry.WKEntityTypes;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.List;
import java.util.SplittableRandom;
import java.util.UUID;

public class HedgehogEntity extends WKTameableEntity implements IAnimatable, SmartBrainOwner<HedgehogEntity> {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public HedgehogEntity(EntityType<? extends TameableEntity> entityType, World world) {
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

    @Override
    protected void mobTick() {
        tickBrain(this);
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        int var = new SplittableRandom().nextInt(1, 7);
        this.setVariant(var);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public int getVariants() {
        return 6;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        HedgehogEntity hedgehogEntity = WKEntityTypes.HEDGEHOG.create(world);
        UUID uUID = this.getOwnerUuid();
        if (uUID != null && hedgehogEntity != null) {
            hedgehogEntity.setOwnerUuid(uUID);
            hedgehogEntity.setTamed(true);
        }
        return hedgehogEntity;
    }


    @Override
    public List<ExtendedSensor<HedgehogEntity>> getSensors() {
        return HedgehogBrain.getSensors();
    }

    @Override
    public BrainActivityGroup<HedgehogEntity> getCoreTasks() {
        return HedgehogBrain.getCoreTasks();
    }

    @Override
    public BrainActivityGroup<HedgehogEntity> getIdleTasks() {
        return HedgehogBrain.getIdleTasks();
    }

    @Override
    public BrainActivityGroup<HedgehogEntity> getFightTasks() {
        return HedgehogBrain.getFightTasks(this);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        AnimationBuilder builder = new AnimationBuilder();
        if (this.isSitting()) {
            builder.addAnimation("loaf", ILoopType.EDefaultLoopTypes.LOOP);
            event.getController().setAnimation(builder);
            return PlayState.CONTINUE;
        } else if (event.isMoving()) {
            builder.addAnimation("walk", ILoopType.EDefaultLoopTypes.LOOP);
        } else {
            builder.addAnimation("idle", ILoopType.EDefaultLoopTypes.LOOP);
        }
        event.getController().setAnimation(builder);
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
