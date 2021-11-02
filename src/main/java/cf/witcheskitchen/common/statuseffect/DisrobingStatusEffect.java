package cf.witcheskitchen.common.statuseffect;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
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
        List<ItemStack> list = new ArrayList<>(); {
            entity.getEquippedStack(EquipmentSlot.FEET).decrement(1);
            entity.getEquippedStack(EquipmentSlot.LEGS).decrement(1);
            entity.getEquippedStack(EquipmentSlot.CHEST).decrement(1);
            entity.getEquippedStack(EquipmentSlot.HEAD).decrement(1);
        }

    }
}
