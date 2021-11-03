package cf.witcheskitchen.common.statuseffect;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

import java.util.Random;

public class DisrobingStatusEffect extends StatusEffect {
    int percentage = 25;

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
        if (i < 25 && percentage <= 25) {
            switch (rand.nextInt(5)) {
                entity.dropItem(entity.getEquippedStack(EquipmentSlot.HEAD), 1);
                percentage = 26;
            }
        }

    }
}
