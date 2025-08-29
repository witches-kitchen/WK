package cf.witcheskitchen.common.statuseffect;

import cf.witcheskitchen.common.registry.WKStatusEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class CooldownStatusEffect extends MobEffect {
    public CooldownStatusEffect(MobEffectCategory type, int color) {
        super(type, color);
    }

    @Override
    public boolean applyEffectTick(ServerLevel world, LivingEntity entity, int amplifier) {
        if (entity.hasEffect(WKStatusEffects.DISROBING)) {
            entity.removeEffect(WKStatusEffects.DISROBING);
        }
        if (entity.hasEffect(WKStatusEffects.GROWTH)) {
            entity.removeEffect(WKStatusEffects.GROWTH);
        }
        return true;
    }
}
