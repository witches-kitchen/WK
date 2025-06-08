package cf.witcheskitchen.common.statuseffect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;

public class BugSprayStatusEffect extends StatusEffect {
    public BugSprayStatusEffect(StatusEffectCategory type, int color) {
        super(type, color);
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        return true;
    }
}
