package cf.witcheskitchen.common.statuseffect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PhasingStatusEffect extends InstantStatusEffect {
    public PhasingStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }

    @Override
    public boolean isInstant() {
        return true;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
        BlockPos pos = target.getBlockPos();
        World world = target.getEntityWorld();
        if (!world.isClient) {
            double d = target.getX();
            double e = target.getY();
            double f = target.getZ();

            for (int i = 0; i < 16; ++i) {
                double g = target.getX() + (target.getRandom().nextDouble() - 0.5D) * 16.0D;
                double h = MathHelper.clamp(target.getY() + (double) (target.getRandom().nextInt(16) - 8), world.getBottomY(), world.getBottomY() + ((ServerWorld) world).getLogicalHeight() - 1);
                double j = target.getZ() + (target.getRandom().nextDouble() - 0.5D) * 16.0D;
                if (target.hasVehicle()) {
                    target.stopRiding();
                }

                if (target.teleport(g, h, j, true)) {
                    SoundEvent soundEvent = target instanceof FoxEntity ? SoundEvents.ENTITY_FOX_TELEPORT : SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
                    world.playSound(null, d, e, f, soundEvent, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    target.playSound(soundEvent, 1.0F, 1.0F);
                    break;
                }
            }
        }
    }
}
