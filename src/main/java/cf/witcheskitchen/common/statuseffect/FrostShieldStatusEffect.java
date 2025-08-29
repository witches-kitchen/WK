package cf.witcheskitchen.common.statuseffect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class FrostShieldStatusEffect extends MobEffect {
    public FrostShieldStatusEffect(MobEffectCategory category, int color) {
        super(category, color);
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
        if (amplifier == 0) {
            if (entity.getLastHurtByMob() == attacker) {
                if (attacker != null) {
                    attacker.setTicksFrozen(10);
                    attacker.isFullyFrozen();
                    attacker.hurtServer(world, entity.damageSources().freeze(), 2f);
                    attacker.setIsInPowderSnow(true);
                }
            }
        } else if (amplifier >= 1) {
            if (entity.getLastHurtByMob() == attacker) {
                if (attacker != null) {
                    attacker.setTicksFrozen(10);
                    attacker.isFullyFrozen();
                    attacker.hurtServer(world, entity.damageSources().freeze(), 4f);
                    attacker.setIsInPowderSnow(true);
                }
            }
        }

        return true;
    }
}
