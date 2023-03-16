package cf.witcheskitchen.mixin.statuseffect;

import cf.witcheskitchen.common.registry.WKStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FleeEntityGoal.class)
public abstract class FleeEntityGoalMixin<T extends LivingEntity> extends Goal {

    @Shadow
    @Nullable
    protected T targetEntity;

    @Shadow
    @Final
    protected PathAwareEntity mob;

    @Inject(method = "canStart", at = @At("TAIL"), cancellable = true)
    private void canStart(CallbackInfoReturnable<Boolean> cir) {
        if (this.mob instanceof CreeperEntity && this.targetEntity instanceof PlayerEntity player) {
            cir.setReturnValue(player.hasStatusEffect(WKStatusEffects.FELIFORM));
        }
        if (this.mob instanceof SilverfishEntity && this.targetEntity instanceof PlayerEntity player) {
            cir.setReturnValue(player.hasStatusEffect(WKStatusEffects.BUG_SPRAY));
        }
        if (this.mob instanceof EndermiteEntity && this.targetEntity instanceof PlayerEntity player) {
            cir.setReturnValue(player.hasStatusEffect(WKStatusEffects.BUG_SPRAY));
        }
        if (this.mob instanceof BeeEntity && this.targetEntity instanceof PlayerEntity player) {
            cir.setReturnValue(player.hasStatusEffect(WKStatusEffects.BUG_SPRAY));
        }
        if (this.mob instanceof SpiderEntity && this.targetEntity instanceof PlayerEntity player) {
            cir.setReturnValue(player.hasStatusEffect(WKStatusEffects.BUG_SPRAY));
        }
        if (this.mob instanceof CaveSpiderEntity && this.targetEntity instanceof PlayerEntity player) {
            cir.setReturnValue(player.hasStatusEffect(WKStatusEffects.BUG_SPRAY));
        }
    }
}
