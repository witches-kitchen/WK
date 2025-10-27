package cf.witcheskitchen.common.statuseffect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Fox;

public class PhasingStatusEffect extends InstantenousMobEffect {
    public PhasingStatusEffect(MobEffectCategory category, int color) {
        super(category, color);
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
        if (!world.isClientSide()) {
            double d = entity.getX();
            double e = entity.getY();
            double f = entity.getZ();

            for (int i = 0; i < 16; ++i) {
                double g = entity.getX() + (entity.getRandom().nextDouble() - 0.5D) * 16.0D;
                double h = Mth.clamp(entity.getY() + (double) (entity.getRandom().nextInt(16) - 8), world.getMinY(), world.getMinY() + world.getHeight() - 1);
                double j = entity.getZ() + (entity.getRandom().nextDouble() - 0.5D) * 16.0D;
                if (entity.isPassenger()) {
                    entity.stopRiding();
                }

                if (entity.randomTeleport(g, h, j, true)) {
                    SoundEvent soundEvent = entity instanceof Fox ? SoundEvents.FOX_TELEPORT : SoundEvents.CHORUS_FRUIT_TELEPORT;
                    world.playSound(null, d, e, f, soundEvent, SoundSource.PLAYERS, 1.0F, 1.0F);
                    entity.playSound(soundEvent, 1.0F, 1.0F);
                    break;
                }
            }
        }
        return true;
    }
}
