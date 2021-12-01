package cf.witcheskitchen.common.statuseffect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.passive.AnimalEntity;

public class LoveStatusEffect extends StatusEffect {

    public LoveStatusEffect(StatusEffectCategory type, int color) {
        super(type, color);
    }

    @Override
    public boolean isInstant() {
        return false;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof AnimalEntity) {
            if (entity.isAlive() && !((AnimalEntity) entity).isInLove()) {
                ((AnimalEntity) entity).setLoveTicks(5000);
                ((AnimalEntity) entity).isInLove();
            }
        }
    }
}
