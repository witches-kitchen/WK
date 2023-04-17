package cf.witcheskitchen.mixin.statuseffect;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.List;

@Mixin(MobEntity.class)
public abstract class ArthropodEntityMixin extends LivingEntity {

    @Shadow
    @Final
    protected GoalSelector goalSelector;

    protected ArthropodEntityMixin(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals", at = @At("HEAD"))
    private void initFleeGoal(CallbackInfo ci) {
        MobEntity mob = MobEntity.class.cast(this);

        List<Class<? extends MobEntity>> fleeEntities = Arrays.asList(SpiderEntity.class, SilverfishEntity.class, EndermiteEntity.class, BeeEntity.class);

        for (Class<? extends MobEntity> entityClass : fleeEntities) {
            if (entityClass.isInstance(mob)) {
                this.goalSelector.add(3, new FleeEntityGoal<>((PathAwareEntity) mob, PlayerEntity.class, 12.0F, 1.0D, 1.6D));
                break;
            }
        }
    }
}
