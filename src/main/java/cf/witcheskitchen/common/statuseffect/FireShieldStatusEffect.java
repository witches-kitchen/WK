package cf.witcheskitchen.common.statuseffect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class FireShieldStatusEffect extends MobEffect {
    public FireShieldStatusEffect(MobEffectCategory type, int color) {
        super(type, color);
    }

    @Override
    public boolean isBeneficial() {
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(ServerLevel world, LivingEntity entity, int amplifier) {
        LivingEntity attacker = entity.getLastHurtByMob();
        if (entity.getLastHurtByMob() == attacker) {
            if (attacker != null) {
                attacker.igniteForSeconds(10);
            }
        }
        return true;
    }
}
