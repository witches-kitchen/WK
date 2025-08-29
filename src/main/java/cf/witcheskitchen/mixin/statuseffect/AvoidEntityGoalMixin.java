package cf.witcheskitchen.mixin.statuseffect;

import cf.witcheskitchen.common.registry.WKStatusEffects;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

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
        Map<Class<? extends Mob>, Holder<MobEffect>> fleeEffects = new HashMap<>();
        fleeEffects.put(Creeper.class, WKStatusEffects.FELIFORM);
        fleeEffects.put(Silverfish.class, WKStatusEffects.BUG_SPRAY);
        fleeEffects.put(Endermite.class, WKStatusEffects.BUG_SPRAY);
        fleeEffects.put(Bee.class, WKStatusEffects.BUG_SPRAY);
        fleeEffects.put(Spider.class, WKStatusEffects.BUG_SPRAY);
        fleeEffects.put(CaveSpider.class, WKStatusEffects.BUG_SPRAY);

        Class<? extends Mob> mobClass = this.mob.getClass();
        if (fleeEffects.containsKey(mobClass) && this.toAvoid instanceof Player player) {
            Holder<MobEffect> effect = fleeEffects.get(mobClass);
            cir.setReturnValue(player.hasEffect(effect));
        }
    }
}
