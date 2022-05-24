package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.WKConfig;
import cf.witcheskitchen.WKIdentifier;
import cf.witcheskitchen.api.registry.ObjectDefinition;
import cf.witcheskitchen.common.statuseffect.*;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.Validate;

import java.util.*;

public class WKStatusEffects {
    private static final List<ObjectDefinition<StatusEffect>> STATUS_EFFECTS = new ArrayList<>();
    public static final StatusEffect HORROR = create("horror", new HorrorStatusEffect(StatusEffectCategory.HARMFUL, 0x555D50));
    public static final StatusEffect HELLFIRE = create("hellfire", new HellfireStatusEffect(StatusEffectCategory.HARMFUL, 0xA91101));
    public static final StatusEffect FROST_SHIELD = create("frost_shield", new FrostShieldStatusEffect(StatusEffectCategory.BENEFICIAL, 0xAFDBF5));
    public static final StatusEffect FROSTBITE = create("frostbite", new FrostbiteStatusEffect(StatusEffectCategory.HARMFUL, 0xAFDBF5));
    public static final StatusEffect DRUNK = create("drunk", new DrunkStatusEffect(StatusEffectCategory.NEUTRAL, 0x7B3F00));
    public static final StatusEffect FIRE_SHIELD = create("fire_shield", new FireShieldStatusEffect(StatusEffectCategory.BENEFICIAL, 0xAF28500));
    public static final StatusEffect DISROBING = create("disrobing", new DisrobingStatusEffect(StatusEffectCategory.HARMFUL, 0xFDF5E6));
    public static final StatusEffect COOLDOWN = create("cooldown", new CooldownStatusEffect(StatusEffectCategory.NEUTRAL, 0x1F75FE));
    public static final StatusEffect GROWTH = create("growth", new GrowthStatusEffect(StatusEffectCategory.BENEFICIAL, 0x4F7942));
    public static final StatusEffect LOVE = create("love", new LoveStatusEffect(StatusEffectCategory.BENEFICIAL, 0xFFB7C5));
    public static final StatusEffect PARALYSIS = create("paralysis", new ParalysisStatusEffect(StatusEffectCategory.HARMFUL, 0xFADA5E));
    public static final StatusEffect PHASING = create("phasing", new PhasingStatusEffect(StatusEffectCategory.NEUTRAL, 0x7851A9));
    public static final StatusEffect SHADOWS = create("shadows", new PhasingStatusEffect(StatusEffectCategory.BENEFICIAL, 0x86608E));
    public static final StatusEffect CORROSION = create("corrosion", new CorrosionStatusEffect(StatusEffectCategory.HARMFUL, 0x3FFF00)).addAttributeModifier(EntityAttributes.GENERIC_ARMOR, "e8506ffe-e2b4-4f19-8669-becb8e3eb666", -4D, EntityAttributeModifier.Operation.ADDITION).addAttributeModifier(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, "1845f14c-5411-4380-8be7-85e81317523a", -2D, EntityAttributeModifier.Operation.ADDITION);
    public static final StatusEffect REINFORCEMENT = create("reinforcement", new ReinforcementStatusEffect(StatusEffectCategory.BENEFICIAL, 0x4000FF)).addAttributeModifier(EntityAttributes.GENERIC_ARMOR, "52823351-ea91-4db3-958d-1b1ce3804dd6", 4D, EntityAttributeModifier.Operation.ADDITION).addAttributeModifier(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, "ec255b60-8b01-450b-8538-17a8a28e4aaf", 2D, EntityAttributeModifier.Operation.ADDITION);
    public static final StatusEffect FELIFORM = create("feliform", new FeliformStatusEffect(StatusEffectCategory.BENEFICIAL, 0x228B22));

    public static List<ObjectDefinition<StatusEffect>> getStatusEffects() {
        return Collections.unmodifiableList(STATUS_EFFECTS);
    }

    static <T extends StatusEffect> T create(String name, T effect) {
        Validate.isTrue(effect != null);
        final Identifier id = new WKIdentifier(name);
        final ObjectDefinition<StatusEffect> def = new ObjectDefinition<>(id, effect);
        STATUS_EFFECTS.add(def);
        return effect;
    }

    public static void register() {
        STATUS_EFFECTS.forEach(entry -> Registry.register(Registry.STATUS_EFFECT, entry.id(), entry.object()));
        if (WKConfig.get().debugMode) {
            WK.logger.info("Witches Kitchen Base Potions: Successfully Loaded");
        }
    }
}
