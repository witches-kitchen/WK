package cf.witcheskitchen.common.statuseffect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class ReinforcementStatusEffect extends StatusEffect {
    public ReinforcementStatusEffect(StatusEffectType type, int color) {
        super(type, color);
        this.addAttributeModifier(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, "e9b51c80-be93-11ed-afa1-0242ac120002", 2D, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
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
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        super.applyUpdateEffect(entity, amplifier);
    }
}
