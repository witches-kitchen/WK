package cf.witcheskitchen.mixin.statuseffect;

import cf.witcheskitchen.common.registry.WKStatusEffects;
import cf.witcheskitchen.common.registry.WKTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AvoidEntityGoal.class)
public abstract class AvoidEntityGoalMixin<T extends LivingEntity> extends Goal {

    @Shadow
    @Nullable
    protected T toAvoid;

    @Shadow
    @Final
    protected PathfinderMob mob;

    @Inject(method = "canUse", at = @At("TAIL"), cancellable = true)
    private void canStart(CallbackInfoReturnable<Boolean> cir) {
        if (this.toAvoid instanceof Player player) {
            if (player.hasEffect(WKStatusEffects.FELIFORM) && this.mob.getType().is(WKTags.AVOIDS_FELIFORM)) {
                cir.setReturnValue(true);
            } else if (player.hasEffect(WKStatusEffects.BUG_SPRAY) && this.mob.getType().is(WKTags.AVOIDS_BUG_SPRAY)) {
                cir.setReturnValue(true);
            }
        }
    }
}
