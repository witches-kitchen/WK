package cf.witcheskitchen.common.statuseffect;

import cf.witcheskitchen.common.registry.WKStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;

public class FrostbiteStatusEffect extends StatusEffect {
    public FrostbiteStatusEffect(StatusEffectCategory type, int color) {
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
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        if (!entity.isFrozen()) {
            if (amplifier == 0) {
                entity.damage(world, entity.getDamageSources().freeze(), 1f);
                entity.setInPowderSnow(true);
            }
            if (amplifier == 1) {
                entity.damage(world, entity.getDamageSources().freeze(), 2f);
                entity.setInPowderSnow(true);
            }
            if (amplifier >= 2) {
                entity.damage(world, entity.getDamageSources().freeze(), 4f);
                entity.setInPowderSnow(true);
            }
        }
        if (entity.isOnFire()) {
            entity.extinguish();
        }
        if (entity.isTouchingWaterOrRain()) {
            if (amplifier == 0) {
                entity.damage(world, entity.getDamageSources().freeze(), 2f);
                entity.setInPowderSnow(true);
            }
            if (amplifier == 1) {
                entity.damage(world, entity.getDamageSources().freeze(), 4f);
                entity.setInPowderSnow(true);
            }
            if (amplifier >= 2) {
                entity.damage(world, entity.getDamageSources().freeze(), 6f);
                entity.setInPowderSnow(true);
            }
        }
        if (entity.hasStatusEffect(WKStatusEffects.HELLFIRE)) {
            entity.removeStatusEffect(WKStatusEffects.HELLFIRE);
        }
        if (entity.hasStatusEffect(WKStatusEffects.FIRE_SHIELD)) {
            entity.removeStatusEffect(WKStatusEffects.FIRE_SHIELD);
        }

        return true;
    }
}
