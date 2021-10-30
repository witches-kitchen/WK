package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.common.statuseffect.HellfireStatusEffect;
import cf.witcheskitchen.common.statuseffect.HorrorStatusEffect;
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

    private static <T extends StatusEffect> T create(String name, T effect) {
        STATUS_EFFECTS.put(effect, new Identifier(WK.MODID, name));
        return effect;
    }

    public static void register() {
        STATUS_EFFECTS.keySet().forEach(effect -> Registry.register(Registry.STATUS_EFFECT, STATUS_EFFECTS.get(effect), effect));
    }
}
