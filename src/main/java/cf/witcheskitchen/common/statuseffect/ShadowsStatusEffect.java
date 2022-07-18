package cf.witcheskitchen.common.statuseffect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.math.BlockPos;

//Todo: This
public class ShadowsStatusEffect extends StatusEffect {
    public ShadowsStatusEffect(StatusEffectType category, int color) {
        super(category, color);
    }

    @Override
    public boolean isBeneficial() {
        return true;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }


    //Todo: Find methods to call for telling if an entity is moving. Also find values for average cave light level, and possibly create a mixin for making armor invisible.
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        BlockPos pos = entity.getBlockPos();
        if (entity.getWorld().isNight() || !entity.getWorld().isSkyVisible(pos)) {
            entity.setInvisible(true);
        } else {
            if (entity.getWorld().isDay()) {
                entity.getWorld().isSkyVisible(pos);
            }
        }
    }
}
