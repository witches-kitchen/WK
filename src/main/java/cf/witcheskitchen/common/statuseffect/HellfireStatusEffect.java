package cf.witcheskitchen.common.statuseffect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class HellfireStatusEffect extends StatusEffect {
    public HellfireStatusEffect(StatusEffectType type, int color) {
        super(type, color);
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!entity.isOnFire() && !entity.isFireImmune()) {
            entity.setOnFireFor(10);
        }
    }
}
