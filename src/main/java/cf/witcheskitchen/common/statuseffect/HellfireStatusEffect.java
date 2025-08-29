package cf.witcheskitchen.common.statuseffect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class HellfireStatusEffect extends MobEffect {
    public HellfireStatusEffect(MobEffectCategory type, int color) {
        super(type, color);
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(ServerLevel world, LivingEntity entity, int amplifier) {
        if (!entity.isOnFire() && !entity.fireImmune()) {
            entity.igniteForSeconds(10);
        }
        return true;
    }
}
