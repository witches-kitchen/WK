package cf.witcheskitchen.common.entities.neutral;

import cf.witcheskitchen.api.WKHostileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.UUID;

public class ChurchGrimEntity extends WKHostileEntity implements IAnimatable, Angerable {

    //Add a string or something here for a variant that is a white, short-haired dog and can appear if named Max
    public ChurchGrimEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public int getVariants() {
        return 0;
    }

    @Override
    public void registerControllers(AnimationData animationData) {

    }

    @Override
    public AnimationFactory getFactory() {
        return null;
    }

    @Override
    public int getAngerTime() {
        return 0;
    }

    @Override
    public void setAngerTime(int ticks) {

    }

    @Override
    public boolean isUndead() {
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
}
