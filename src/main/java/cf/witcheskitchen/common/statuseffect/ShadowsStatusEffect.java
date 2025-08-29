package cf.witcheskitchen.common.statuseffect;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

//Todo: This
public class ShadowsStatusEffect extends MobEffect {
    public ShadowsStatusEffect(MobEffectCategory category, int color) {
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


    //Todo: Find methods to call for telling if an entity is moving. Also find values for average cave light level, and possibly create a mixin for making armor invisible.
    @Override
    public boolean applyEffectTick(ServerLevel world, LivingEntity entity, int amplifier) {
        BlockPos pos = entity.blockPosition();
        if (entity.level().isDarkOutside() || !entity.level().canSeeSky(pos)) {
            entity.setInvisible(true);
        } else {
            if (entity.level().isBrightOutside()) {
                entity.level().canSeeSky(pos);
            }
        }
        return true;
    }
}
