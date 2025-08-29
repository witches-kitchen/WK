package cf.witcheskitchen.common.statuseffect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;

public class LoveStatusEffect extends MobEffect {

    public LoveStatusEffect(MobEffectCategory type, int color) {
        super(type, color);
    }

    @Override
    public boolean isInstantenous() {
        return false;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(ServerLevel world, LivingEntity entity, int amplifier) {
        if (entity instanceof Animal) {
            if (entity.isAlive() && !((Animal) entity).isInLove()) {
                ((Animal) entity).setInLoveTime(5000);
                ((Animal) entity).isInLove();
            }
        }
        return true;
    }
}
