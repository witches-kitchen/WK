package cf.witcheskitchen.mixin.statuseffect;

import cf.witcheskitchen.common.registry.WKStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.effect.StatusEffect;
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

import java.util.HashMap;
import java.util.Map;

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
        Map<Class<? extends MobEntity>, StatusEffect> fleeEffects = new HashMap<>();
        fleeEffects.put(CreeperEntity.class, WKStatusEffects.FELIFORM);
        fleeEffects.put(SilverfishEntity.class, WKStatusEffects.BUG_SPRAY);
        fleeEffects.put(EndermiteEntity.class, WKStatusEffects.BUG_SPRAY);
        fleeEffects.put(BeeEntity.class, WKStatusEffects.BUG_SPRAY);
        fleeEffects.put(SpiderEntity.class, WKStatusEffects.BUG_SPRAY);
        fleeEffects.put(CaveSpiderEntity.class, WKStatusEffects.BUG_SPRAY);

        Class<? extends MobEntity> mobClass = this.mob.getClass();
        if (fleeEffects.containsKey(mobClass) && this.targetEntity instanceof PlayerEntity player) {
            StatusEffect effect = fleeEffects.get(mobClass);
            cir.setReturnValue(player.hasStatusEffect(effect));
        }
    }
}
