package cf.witcheskitchen.common.statuseffect;

import cf.witcheskitchen.api.interfaces.AlcoholEffect;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

//Todo: Learn shaders
public class DrunkStatusEffect extends MobEffect implements AlcoholEffect {

    public DrunkStatusEffect(MobEffectCategory type, int color) {
        super(type, color);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }

    //Todo: Increment to a max of level 4 if one drinks too much
    @Override
    public boolean applyEffectTick(ServerLevel world, LivingEntity entity, int amplifier) {
        if (amplifier == 1) {
            entity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 2000, 1));
            return true;
        }
        if (amplifier == 2) {
            entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 4000, 2));
            entity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 4000, 2));
            return true;
        }
        if (amplifier >= 3) {
            entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 6000, 3));
            entity.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 6000, 3));
            entity.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 6000, 3));
            return true;
        }
        return false;
    }

    @Override
    public void onDrink(Level world, ItemStack wine, LivingEntity entity) {

    }

    @Override
    public void onFinished(Level world, ItemStack wine, LivingEntity entity) {

    }

    @Override
    public int getDrunkChance() {
        return 40;
    }

    //NOTICE: Values are not final! These will change once the alcohol system is fully functional
    // FIXME: The entity is no longer provided to the onRemoved method, how do we do this now?
    /*@Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (amplifier >= 3) {
            entity.addStatusEffect(new StatusEffectInstance(WKStatusEffects.DRUNK, 3000, 2));
        }
        if (amplifier == 2) {
            entity.addStatusEffect(new StatusEffectInstance(WKStatusEffects.DRUNK, 2000, 1));
        }
        if (amplifier == 1) {
            entity.addStatusEffect(new StatusEffectInstance(WKStatusEffects.DRUNK, 1000, 0));
        }
    }*/
}
