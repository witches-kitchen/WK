package cf.witcheskitchen.common.statuseffect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.math.BlockPos;

public class ShadowsStatusEffect extends StatusEffect {
    public ShadowsStatusEffect(StatusEffectCategory category, int color) {
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
       // BlockPos pos = entity.getBlockPos();
        //if (entity.getEntityWorld().isNight() || entity.getEntityWorld().getLightLevel(pos, )) {
       //     entity.setInvisible(true);
       // }
        //if (entity.getEntityWorld().isDay() || entity.getEntityWorld().getLightLevel(pos, )) {
        // entity.setInvisible(true);
       // }
    }
}
