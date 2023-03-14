package cf.witcheskitchen.mixin.statuseffect;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreeperEntity.class)
public abstract class ArthropodEntityMixin extends HostileEntity {

    protected ArthropodEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    //Fixme: Figure out a better way of doing this. There clearly is, but I'm not sure how to do it.
    @Inject(method = "initGoals", at = @At("HEAD"))
    private void initFleeGoal(CallbackInfo ci) {
        this.goalSelector.add(3, new FleeEntityGoal<>(((SpiderEntity) (Object) this), PlayerEntity.class, 12.0F, 1.0D, 1.6D));
        this.goalSelector.add(3, new FleeEntityGoal<>(((CaveSpiderEntity) (Object) this), PlayerEntity.class, 12.0F, 1.0D, 1.6D));
        this.goalSelector.add(3, new FleeEntityGoal<>(((SilverfishEntity) (Object) this), PlayerEntity.class, 12.0F, 1.0D, 1.6D));
        this.goalSelector.add(3, new FleeEntityGoal<>(((EndermiteEntity) (Object) this), PlayerEntity.class, 12.0F, 1.0D, 1.6D));
        this.goalSelector.add(3, new FleeEntityGoal<>(((BeeEntity) (Object) this), PlayerEntity.class, 12.0F, 1.0D, 1.6D));
    }
}
