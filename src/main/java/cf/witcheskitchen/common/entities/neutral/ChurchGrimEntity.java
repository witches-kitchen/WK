package cf.witcheskitchen.common.entities.neutral;

import cf.witcheskitchen.api.WKHostileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class ChurchGrimEntity extends WKHostileEntity implements IAnimatable {

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
}
