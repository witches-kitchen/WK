package cf.witcheskitchen.common.statuseffect;

import cf.witcheskitchen.common.registry.WKStatusEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class FrostbiteStatusEffect extends MobEffect {
    public FrostbiteStatusEffect(MobEffectCategory type, int color) {
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
        if (!entity.isFullyFrozen()) {
            if (amplifier == 0) {
                entity.hurtServer(world, entity.damageSources().freeze(), 1f);
                entity.setIsInPowderSnow(true);
            }
            if (amplifier == 1) {
                entity.hurtServer(world, entity.damageSources().freeze(), 2f);
                entity.setIsInPowderSnow(true);
            }
            if (amplifier >= 2) {
                entity.hurtServer(world, entity.damageSources().freeze(), 4f);
                entity.setIsInPowderSnow(true);
            }
        }
        if (entity.isOnFire()) {
            entity.clearFire();
        }
        if (entity.isInWaterOrRain()) {
            if (amplifier == 0) {
                entity.hurtServer(world, entity.damageSources().freeze(), 2f);
                entity.setIsInPowderSnow(true);
            }
            if (amplifier == 1) {
                entity.hurtServer(world, entity.damageSources().freeze(), 4f);
                entity.setIsInPowderSnow(true);
            }
            if (amplifier >= 2) {
                entity.hurtServer(world, entity.damageSources().freeze(), 6f);
                entity.setIsInPowderSnow(true);
            }
        }
        if (entity.hasEffect(WKStatusEffects.HELLFIRE)) {
            entity.removeEffect(WKStatusEffects.HELLFIRE);
        }
        if (entity.hasEffect(WKStatusEffects.FIRE_SHIELD)) {
            entity.removeEffect(WKStatusEffects.FIRE_SHIELD);
        }

        return true;
    }
}
