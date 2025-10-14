package cf.witcheskitchen.common.statuseffect;


import cf.witcheskitchen.common.registry.WKSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

//Todo: Work on this
public class HorrorStatusEffect extends MobEffect {
    public int timer = 650;

    public HorrorStatusEffect(MobEffectCategory type, int color) {
        super(type, color);
    }

    @Override
    public boolean isBeneficial() {
        return false;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    //Todo: Make sure only the victim can hear these sounds.
    //Todo: It would appear this potion cannot apply properly.
    @Override
    public boolean applyEffectTick(ServerLevel world, LivingEntity entity, int amplifier) {
        RandomSource rand = entity.getRandom();
        BlockPos pos = entity.blockPosition();
        if (!entity.hasEffect(MobEffects.BLINDNESS) && !entity.hasEffect(MobEffects.DARKNESS)) {
            entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
            entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
        }
        int i = rand.nextInt(100);
        if (timer > 0) timer--;
        if (i < 5 && timer == 0) {
            switch (rand.nextInt(28)) {
                case 0 -> {
                    world.playSound(null, pos, SoundEvents.WOODEN_BUTTON_CLICK_ON, SoundSource.HOSTILE, 1, 1);
                    world.playSound(null, pos, SoundEvents.TNT_PRIMED, SoundSource.HOSTILE, 1, 1);
                    if (amplifier == 0) {
                        timer = 650;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                    if (amplifier >= 1) {
                        timer = 350;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                }
                case 1 -> {
                    world.playSound(null, pos, SoundEvents.ENDERMAN_TELEPORT, SoundSource.HOSTILE, 1, 1);
                    world.playSound(null, pos, SoundEvents.ENDERMAN_AMBIENT, SoundSource.HOSTILE, 1, 1);
                    if (amplifier == 0) {
                        timer = 650;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                    if (amplifier >= 1) {
                        timer = 350;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                }
                case 2 -> {
                    world.playSound(null, pos, SoundEvents.POLAR_BEAR_WARNING, SoundSource.HOSTILE, 1, 1);
                    if (amplifier == 0) {
                        timer = 650;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                    if (amplifier >= 1) {
                        timer = 350;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                }
                case 3 -> {
                    world.playSound(null, pos, SoundEvents.SKELETON_AMBIENT, SoundSource.HOSTILE, 1, 1);
                    world.playSound(null, pos, SoundEvents.ARROW_SHOOT, SoundSource.HOSTILE, 1, 1);
                    if (amplifier == 0) {
                        timer = 650;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                    if (amplifier >= 1) {
                        timer = 350;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                }
                case 4 -> {
                    world.playSound(null, pos, SoundEvents.CREEPER_PRIMED, SoundSource.HOSTILE, 1, 1);
                    if (amplifier == 0) {
                        timer = 650;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                    if (amplifier >= 1) {
                        timer = 350;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                }
                case 5 -> {
                    world.playSound(null, pos, SoundEvents.PHANTOM_SWOOP, SoundSource.HOSTILE, 1, 1);
                    if (amplifier == 0) {
                        timer = 650;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                    if (amplifier >= 1) {
                        timer = 350;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                }
                case 6 -> {
                    world.playSound(null, pos, SoundEvents.ENDER_DRAGON_GROWL, SoundSource.HOSTILE, 1, 1);
                    if (amplifier == 0) {
                        timer = 650;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                    if (amplifier >= 1) {
                        timer = 350;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                }
                case 7 -> {
                    world.playSound(null, pos, SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.HOSTILE, 1, 1);
                    if (amplifier == 0) {
                        timer = 650;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                    if (amplifier >= 1) {
                        timer = 350;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                }
                case 8 -> {
                    world.playSound(null, pos, SoundEvents.PIGLIN_BRUTE_ANGRY, SoundSource.HOSTILE, 1, 1);
                    if (amplifier == 0) {
                        timer = 650;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                    if (amplifier >= 1) {
                        timer = 350;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                }
                case 9 -> {
                    world.playSound(null, pos, SoundEvents.AMBIENT_CAVE.value(), SoundSource.HOSTILE, 1, 1);
                    if (amplifier == 0) {
                        timer = 650;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                    if (amplifier >= 1) {
                        timer = 350;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                }
                case 10 -> {
                    world.playSound(null, pos, SoundEvents.PILLAGER_AMBIENT, SoundSource.HOSTILE, 1, 1);
                    world.playSound(null, pos, SoundEvents.CROSSBOW_LOADING_START.value(), SoundSource.HOSTILE, 1, 1);
                    if (amplifier == 0) {
                        timer = 650;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                    if (amplifier >= 1) {
                        timer = 350;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                }
                case 11 -> {
                    world.playSound(null, pos, SoundEvents.WITHER_SKELETON_AMBIENT, SoundSource.HOSTILE, 1, 1);
                    if (amplifier == 0) {
                        timer = 650;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                    if (amplifier >= 1) {
                        timer = 350;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                }
                case 12 -> {
                    world.playSound(null, pos, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.HOSTILE, 1, 1);
                    if (amplifier == 0) {
                        timer = 650;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                    if (amplifier >= 1) {
                        timer = 350;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                }
                case 13 -> {
                    world.playSound(null, pos, SoundEvents.STRAY_AMBIENT, SoundSource.HOSTILE, 1, 1);
                    if (amplifier == 0) {
                        timer = 650;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                    if (amplifier >= 1) {
                        timer = 350;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                }
                case 14 -> {
                    world.playSound(null, pos, SoundEvents.CREAKING_HEART_IDLE, SoundSource.HOSTILE, 1, 1);
                    if (amplifier == 0) {
                        timer = 650;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                    if (amplifier >= 1) {
                        timer = 350;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                }
                case 15 -> {
                    world.playSound(null, pos, SoundEvents.STONE_BREAK, SoundSource.HOSTILE, 1, 1);
                    if (amplifier == 0) {
                        timer = 650;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                    if (amplifier >= 1) {
                        timer = 350;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                }
                case 16 -> {
                    world.playSound(null, pos, WKSoundEvents.CUSITH_IDLE_EVENT, SoundSource.HOSTILE, 1, 1);
                    if (amplifier == 0) {
                        timer = 650;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                    if (amplifier >= 1) {
                        timer = 350;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                }
                case 17 -> {
                    world.playSound(null, pos, WKSoundEvents.CUSITH_HOWL_EVENT, SoundSource.HOSTILE, 1, 1);
                    if (amplifier == 0) {
                        timer = 650;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                    if (amplifier >= 1) {
                        timer = 350;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                }
                case 18 -> {
                    world.playSound(null, pos, SoundEvents.BEE_LOOP_AGGRESSIVE, SoundSource.HOSTILE, 1, 1);
                    if (amplifier == 0) {
                        timer = 650;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                    if (amplifier >= 1) {
                        timer = 350;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                }
                case 19 -> {
                    world.playSound(null, pos, SoundEvents.WARDEN_HEARTBEAT, SoundSource.HOSTILE, 1, 1);
                    world.playSound(null, pos, SoundEvents.WARDEN_LISTENING_ANGRY, SoundSource.HOSTILE, 1, 1);
                    if (amplifier == 0) {
                        timer = 650;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                    if (amplifier >= 1) {
                        timer = 350;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                }
                case 20 -> {
                    world.playSound(null, pos, SoundEvents.WOODEN_BUTTON_CLICK_ON, SoundSource.HOSTILE, 1, 1);
                    world.playSound(null, pos, SoundEvents.WOODEN_DOOR_OPEN, SoundSource.HOSTILE, 1, 1);
                    if (amplifier == 0) {
                        timer = 650;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                    if (amplifier >= 1) {
                        timer = 350;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                }
                case 21 -> {
                    world.playSound(null, pos, SoundEvents.CREAKING_AMBIENT, SoundSource.HOSTILE, 1, 1);
                    if (amplifier == 0) {
                        timer = 650;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                    if (amplifier >= 1) {
                        timer = 350;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                }
                case 22 -> {
                    world.playSound(null, pos, WKSoundEvents.HALLUCINATION_BREATH, SoundSource.HOSTILE, 1, 1);
                    if (amplifier == 0) {
                        timer = 650;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                    if (amplifier >= 1) {
                        timer = 350;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                }
                case 23 -> {
                    world.playSound(null, pos, SoundEvents.BOOK_PAGE_TURN, SoundSource.HOSTILE, 1, 1);
                    world.playSound(null, pos, SoundEvents.GHAST_SHOOT, SoundSource.HOSTILE, 1, 1);
                    if (amplifier == 0) {
                        timer = 650;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                    if (amplifier >= 1) {
                        timer = 350;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                }
                case 24 -> {
                    world.playSound(null, pos, SoundEvents.GHAST_SHOOT, SoundSource.HOSTILE, 1, 1);
                    if (amplifier == 0) {
                        timer = 650;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                    if (amplifier >= 1) {
                        timer = 350;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                }
                case 25 -> {
                    world.playSound(null, pos, SoundEvents.APPLY_EFFECT_BAD_OMEN, SoundSource.HOSTILE, 1, 1);
                    if (amplifier == 0) {
                        timer = 650;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                    if (amplifier >= 1) {
                        timer = 350;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                }
                case 26 -> {
                    world.playSound(null, pos, SoundEvents.PILLAGER_CELEBRATE, SoundSource.HOSTILE, 1, 1);
                    if (amplifier == 0) {
                        timer = 650;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                    if (amplifier >= 1) {
                        timer = 350;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                }
                default -> {
                    world.playSound(null, pos, SoundEvents.ZOMBIE_AMBIENT, SoundSource.HOSTILE, 1, 1);
                    if (amplifier == 0) {
                        timer = 650;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                    if (amplifier >= 1) {
                        timer = 350;
                        entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 9000, 3));
                        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 9000, 3));
                    }
                }
            }
        }

        return true;
    }
}
