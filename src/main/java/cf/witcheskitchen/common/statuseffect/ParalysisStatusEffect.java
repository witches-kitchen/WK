package cf.witcheskitchen.common.statuseffect;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class ParalysisStatusEffect extends MobEffect {
    public ParalysisStatusEffect(MobEffectCategory type, int color) {
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
    public boolean isBeneficial() {
        return false;
    }

    @Override
    public boolean applyEffectTick(ServerLevel world, LivingEntity entity, int amplifier) {
        BlockPos pos = entity.blockPosition();
        entity.setDeltaMovement(0, 0, 0);
        entity.setSprinting(false);
        entity.setSpeed(0);
        if (entity.isSwimming()) {
            entity.hurtServer(world, entity.damageSources().drown(), 2f);
            entity.setAirSupply(0);
            world.playSound(null, pos, SoundEvents.PLAYER_HURT_DROWN, SoundSource.HOSTILE, 1, 1);
        }

        return true;
    }
}
