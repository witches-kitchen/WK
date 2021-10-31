package cf.witcheskitchen.common.statuseffect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.effect.StatusEffects;

//Todo: Learn shaders
public class DrunkStatusEffect extends StatusEffect {
    public DrunkStatusEffect(StatusEffectType type, int color) {
        super(type, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (amplifier >= 3) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 6000, 3));
        }
    }
}
