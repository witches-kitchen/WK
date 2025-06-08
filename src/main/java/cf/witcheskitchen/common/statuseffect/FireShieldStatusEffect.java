package cf.witcheskitchen.common.statuseffect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.world.ServerWorld;

public class FireShieldStatusEffect extends StatusEffect {
    public FireShieldStatusEffect(StatusEffectCategory type, int color) {
        super(type, color);
    }

    @Override
    public boolean isBeneficial() {
        return true;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        LivingEntity attacker = entity.getAttacker();
        if (entity.getAttacker() == attacker) {
            if (attacker != null) {
                attacker.setOnFireFor(10);
            }
        }
        return true;
    }
}
