package cf.witcheskitchen.common.statuseffect;

import cf.witcheskitchen.common.registry.WKStatusEffects;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;

import java.util.Random;

public class DisrobingStatusEffect extends StatusEffect {

    public DisrobingStatusEffect(StatusEffectType type, int color) {
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
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        Random rand = entity.getRandom();
        int i = rand.nextInt(100);
        if (i < 75) {
            switch (rand.nextInt(4)) {
                case 0 -> {
                    entity.dropItem(entity.getEquippedStack(EquipmentSlot.HEAD).getItem(), 1);
                    entity.getEquippedStack(EquipmentSlot.HEAD).decrement(1);
                    entity.removeStatusEffect(WKStatusEffects.DISROBING);
                }
                case 1 -> {
                    entity.dropItem(entity.getEquippedStack(EquipmentSlot.CHEST).getItem(), 1);
                    entity.getEquippedStack(EquipmentSlot.CHEST).decrement(1);
                    entity.removeStatusEffect(WKStatusEffects.DISROBING);
                }
                case 2 -> {
                    entity.dropItem(entity.getEquippedStack(EquipmentSlot.LEGS).getItem(), 1);
                    entity.getEquippedStack(EquipmentSlot.LEGS).decrement(1);
                    entity.removeStatusEffect(WKStatusEffects.DISROBING);
                }
                case 3 -> {
                    entity.dropItem(entity.getEquippedStack(EquipmentSlot.FEET).getItem(), 1);
                    entity.getEquippedStack(EquipmentSlot.FEET).decrement(1);
                    entity.removeStatusEffect(WKStatusEffects.DISROBING);

                }

            }
        }
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        entity.addStatusEffect(new StatusEffectInstance(WKStatusEffects.COOLDOWN, 6000, 0));
    }
}
