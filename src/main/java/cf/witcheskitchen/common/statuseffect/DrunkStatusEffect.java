package cf.witcheskitchen.common.statuseffect;

import cf.witcheskitchen.api.interfaces.AlcoholEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

//Todo: Learn shaders
public class DrunkStatusEffect extends StatusEffect implements AlcoholEffect {

    public DrunkStatusEffect(StatusEffectCategory type, int color) {
        super(type, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }

    //Todo: Increment to a max of level 4 if one drinks too much
    @Override
    public boolean applyUpdateEffect(ServerWorld world, LivingEntity entity, int amplifier) {
        if (amplifier == 1) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 2000, 1));
            return true;
        }
        if (amplifier == 2) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 4000, 2));
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 4000, 2));
            return true;
        }
        if (amplifier >= 3) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 6000, 3));
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 6000, 3));
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 6000, 3));
            return true;
        }
        return false;
    }

    @Override
    public void onDrink(World world, ItemStack wine, LivingEntity entity) {

    }

    @Override
    public void onFinished(World world, ItemStack wine, LivingEntity entity) {

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
