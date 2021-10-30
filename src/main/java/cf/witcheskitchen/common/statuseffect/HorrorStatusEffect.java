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
public class HorrorStatusEffect extends StatusEffect {
    public int timer = 650;

    public HorrorStatusEffect(StatusEffectType type, int color) {
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
        World world = entity.getEntityWorld();
        BlockPos pos = entity.getBlockPos();
        if (!entity.hasStatusEffect(StatusEffects.BLINDNESS)) {
            entity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 500, 3));
        }
        int i = rand.nextInt(100);
        if (timer > 0) timer--;
        if (i < 5 && timer == 0) {
            switch (rand.nextInt(8)) {
                case 0 -> {
                    world.playSound(null, pos, SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_ON, SoundCategory.HOSTILE, 1, 1);
                    world.playSound(null, pos, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.HOSTILE, 1, 1);
                    timer = 650;
                }
                case 1 -> {
                    world.playSound(null, pos, SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.HOSTILE, 1, 1);
                    world.playSound(null, pos, SoundEvents.ENTITY_ENDERMAN_AMBIENT, SoundCategory.HOSTILE, 1, 1);
                    timer = 650;
                }
                case 2 -> {
                    world.playSound(null, pos, SoundEvents.ENTITY_POLAR_BEAR_WARNING, SoundCategory.HOSTILE, 1, 1);
                    timer = 650;
                }
                case 3 -> {
                    world.playSound(null, pos, SoundEvents.ENTITY_SKELETON_AMBIENT, SoundCategory.HOSTILE, 1, 1);
                    world.playSound(null, pos, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.HOSTILE, 1, 1);
                    timer = 650;
                }
                case 4 -> {
                    world.playSound(null, pos, SoundEvents.ENTITY_CREEPER_PRIMED, SoundCategory.HOSTILE, 1, 1);
                    timer = 650;
                }
                case 5 -> {
                    world.playSound(null, pos, SoundEvents.ENTITY_PHANTOM_SWOOP, SoundCategory.HOSTILE, 1, 1);
                    timer = 650;
                }
                case 6 -> {
                    world.playSound(null, pos, SoundEvents.ENTITY_ENDER_DRAGON_GROWL, SoundCategory.HOSTILE, 1, 1);
                    timer = 650;
                }
                case 7 -> {
                    world.playSound(null, pos, SoundEvents.ENTITY_LIGHTNING_BOLT_THUNDER, SoundCategory.HOSTILE, 1, 1);
                    timer = 650;
                }
            }
        }
    }
}
