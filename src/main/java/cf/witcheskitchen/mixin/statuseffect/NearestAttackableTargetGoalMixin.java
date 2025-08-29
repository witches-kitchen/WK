package cf.witcheskitchen.mixin.statuseffect;

import cf.witcheskitchen.common.registry.WKStatusEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NearestAttackableTargetGoal.class)
public abstract class NearestAttackableTargetGoalMixin<T extends LivingEntity> extends TargetGoal {

    @Shadow
    @Nullable
    protected LivingEntity target;

    public NearestAttackableTargetGoalMixin(Mob mob, boolean checkVisibility) {
        super(mob, checkVisibility);
    }


    //Todo: This but with the bugs spray potion and arthropods
    @Inject(method = "canUse", at = @At("TAIL"), cancellable = true)
    private void canStart(CallbackInfoReturnable<Boolean> cir) {
        if (this.mob instanceof Creeper) {
            if (this.target instanceof Player player) {
                cir.setReturnValue(!player.hasEffect(WKStatusEffects.FELIFORM));
            }
        }
    }
}
