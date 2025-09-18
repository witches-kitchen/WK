package cf.witcheskitchen.mixin.statuseffect;

import cf.witcheskitchen.common.registry.WKTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity {

    @Shadow
    @Final
    protected GoalSelector goalSelector;

    protected MobMixin(EntityType<? extends PathfinderMob> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Mob;registerGoals()V"))
    private void initFleeGoal(CallbackInfo ci) {
        if (!((Object) this instanceof PathfinderMob pathfinderMob))
            return;

        if (this.getType().is(WKTags.FLEEING_MOBS)) {
            this.goalSelector.addGoal(3, new AvoidEntityGoal<>(pathfinderMob, Player.class, 12.0F, 1.0D, 1.6D));
        }
    }
}
