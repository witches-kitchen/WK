package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.common.statuseffect.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class WKStatusEffects {
    private static final Map<StatusEffect, Identifier> STATUS_EFFECTS = new LinkedHashMap<>();

    public static final StatusEffect HORROR = create("horror", new HorrorStatusEffect(StatusEffectType.HARMFUL, 0x555D50));
    public static final StatusEffect HELLFIRE = create("hellfire", new HellfireStatusEffect(StatusEffectType.HARMFUL, 0xA91101));
    public static final StatusEffect FROSTBITE = create("frostbite", new FrostbiteStatusEffect(StatusEffectType.HARMFUL, 0xAFDBF5));
    public static final StatusEffect DRUNK = create("drunk", new DrunkStatusEffect(StatusEffectType.NEUTRAL, 0x7B3F00));
    public static final StatusEffect FIRE_SHIELD = create("fire_shield", new FireShieldStatusEffect(StatusEffectType.BENEFICIAL, 0xAF28500));
    public static final StatusEffect DISROBING = create("disrobing", new DisrobingStatusEffect(StatusEffectType.HARMFUL, 0xFDF5E6));

    private static <T extends StatusEffect> T create(String name, T effect) {
        STATUS_EFFECTS.put(effect, new Identifier(WK.MODID, name));
        return effect;
    }

    public static void register() {
        STATUS_EFFECTS.keySet().forEach(effect -> Registry.register(Registry.STATUS_EFFECT, STATUS_EFFECTS.get(effect), effect));
    }
}
