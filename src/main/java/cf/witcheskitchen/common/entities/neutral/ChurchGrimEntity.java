package cf.witcheskitchen.common.entities.neutral;

import cf.witcheskitchen.api.WKTameableEntity;
import dev.onyxstudios.cca.api.v3.component.ComponentContainer;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.UUID;

//Todo: This once structures are in
public class ChurchGrimEntity extends WKTameableEntity implements IAnimatable, Angerable, Tameable {

    //Add a string or something here for a variant that is a white, short-haired dog and can appear if named Max
    private final AnimationFactory factory = new AnimationFactory(this);

    public ChurchGrimEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public int getVariants() {
        return 0;
    }

    @Override
    public int getAngerTime() {
        return 0;
    }

    @Override
    public void setAngerTime(int ticks) {

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
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void chooseRandomAngerTime() {

    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    public boolean isUndead() {
        return true;
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }

    @Override
    public void registerControllers(AnimationData animationData) {

    }

    @Override
    public ComponentContainer getComponentContainer() {
        return null;
    }
}
