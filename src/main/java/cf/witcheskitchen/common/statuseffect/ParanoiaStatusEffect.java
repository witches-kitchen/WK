package cf.witcheskitchen.common.statuseffect;


import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

//Todo: Work on this
public class ParanoiaStatusEffect extends StatusEffect {
    public int timer = 650;

    public ParanoiaStatusEffect(StatusEffectType type, int color) {
        super(type, color);
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 200, 4));
        Random rand = entity.getRandom();
        World world = entity.getEntityWorld();
        BlockPos pos = entity.getBlockPos();
        int i = rand.nextInt(100);
        if (timer > 0) timer--;
        if (i < 5 && timer == 0) {
            switch (rand.nextInt(4)) {
                case 0:
                    world.playSound(null, pos, SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_ON, SoundCategory.HOSTILE, 1, 1);
                    world.playSound(null, pos, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.HOSTILE, 1, 1);
                    timer = 650;
                    break;
                case 1:
                    world.playSound(null, pos, SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.HOSTILE, 1, 1);
                    world.playSound(null, pos, SoundEvents.ENTITY_ENDERMAN_AMBIENT, SoundCategory.HOSTILE, 1, 1);
                    timer = 650;
                    break;
            }
        }
    }
}
