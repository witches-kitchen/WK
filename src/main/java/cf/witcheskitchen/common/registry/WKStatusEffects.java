package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.api.registry.ObjectDefinition;
import cf.witcheskitchen.common.statuseffect.*;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface WKStatusEffects {
    List<ObjectDefinition<StatusEffect>> STATUS_EFFECTS = new ArrayList<>();

    RegistryEntry<StatusEffect> HORROR = create("horror", new HorrorStatusEffect(StatusEffectCategory.HARMFUL, 0x555D50));
    RegistryEntry<StatusEffect> HELLFIRE = create("hellfire", new HellfireStatusEffect(StatusEffectCategory.HARMFUL, 0xA91101));
    RegistryEntry<StatusEffect> FROST_SHIELD = create("frost_shield", new FrostShieldStatusEffect(StatusEffectCategory.BENEFICIAL, 0xAFDBF5));
    RegistryEntry<StatusEffect> FROSTBITE = create("frostbite", new FrostbiteStatusEffect(StatusEffectCategory.HARMFUL, 0xAFDBF5));
    //RegistryEntry<StatusEffect> DRUNK = create("drunk", new DrunkStatusEffect(StatusEffectCategory.NEUTRAL, 0x7B3F00));
    RegistryEntry<StatusEffect> FIRE_SHIELD = create("fire_shield", new FireShieldStatusEffect(StatusEffectCategory.BENEFICIAL, 0xAF28500));
    RegistryEntry<StatusEffect> DISROBING = create("disrobing", new DisrobingStatusEffect(StatusEffectCategory.HARMFUL, 0xFDF5E6));
    RegistryEntry<StatusEffect> COOLDOWN = create("cooldown", new CooldownStatusEffect(StatusEffectCategory.NEUTRAL, 0x1F75FE));

    RegistryEntry<StatusEffect> PANTAGRUELIAN = create("pantagruelian", new CooldownStatusEffect(StatusEffectCategory.NEUTRAL, 0x1F75FE));

    RegistryEntry<StatusEffect> LILLIPUTIAN = create("lilliputian", new CooldownStatusEffect(StatusEffectCategory.NEUTRAL, 0x1F75FE));

    RegistryEntry<StatusEffect> GROWTH = create("growth", new GrowthStatusEffect(StatusEffectCategory.BENEFICIAL, 0x4F7942));
    RegistryEntry<StatusEffect> LOVE = create("love", new LoveStatusEffect(StatusEffectCategory.BENEFICIAL, 0xFFB7C5));
    RegistryEntry<StatusEffect> PARALYSIS = create("paralysis", new ParalysisStatusEffect(StatusEffectCategory.HARMFUL, 0xFADA5E)
            .addAttributeModifier(EntityAttributes.BLOCK_INTERACTION_RANGE, WitchesKitchen.id("paralysis_decreased_reach"), -9.0D, EntityAttributeModifier.Operation.ADD_VALUE)
            .addAttributeModifier(EntityAttributes.ENTITY_INTERACTION_RANGE, WitchesKitchen.id("paralysis_decreased_entity_reach"), -9.D, EntityAttributeModifier.Operation.ADD_VALUE));
    RegistryEntry<StatusEffect> PHASING = create("phasing", new PhasingStatusEffect(StatusEffectCategory.NEUTRAL, 0x7851A9));
    RegistryEntry<StatusEffect> SHADOWS = create("shadows", new PhasingStatusEffect(StatusEffectCategory.BENEFICIAL, 0x86608E));
    RegistryEntry<StatusEffect> CORROSION = create("corrosion", new CorrosionStatusEffect(StatusEffectCategory.HARMFUL, 0x3FFF00)
            .addAttributeModifier(EntityAttributes.ARMOR, WitchesKitchen.id("corrosion_armor_decrease"), -4D, EntityAttributeModifier.Operation.ADD_VALUE)
            .addAttributeModifier(EntityAttributes.ARMOR_TOUGHNESS, WitchesKitchen.id("corrosion_armor_toughness_decrease"), -2D, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    RegistryEntry<StatusEffect> REINFORCEMENT = create("reinforcement", new ReinforcementStatusEffect(StatusEffectCategory.BENEFICIAL, 0x4000FF)
            .addAttributeModifier(EntityAttributes.ARMOR, WitchesKitchen.id("reinforcement_armor"), 4D, EntityAttributeModifier.Operation.ADD_VALUE)
            .addAttributeModifier(EntityAttributes.ARMOR_TOUGHNESS, WitchesKitchen.id("reinforcement_armor_toughness"), 2D, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    RegistryEntry<StatusEffect> FELIFORM = create("feliform", new FeliformStatusEffect(StatusEffectCategory.BENEFICIAL, 0x228B22));

    RegistryEntry<StatusEffect> DRUNK = create("drunk", new DrunkStatusEffect(StatusEffectCategory.HARMFUL, 0x228B22));
    RegistryEntry<StatusEffect> BUG_SPRAY = create("bug_spray", new BugSprayStatusEffect(StatusEffectCategory.BENEFICIAL, 0x32CD32));
    RegistryEntry<StatusEffect> LONG_REACH = create("long_reach", new LongReachStatusEffect(StatusEffectCategory.BENEFICIAL, 0x964a6e)
            .addAttributeModifier(EntityAttributes.BLOCK_INTERACTION_RANGE, WitchesKitchen.id("long_reach"), 5.5D, EntityAttributeModifier.Operation.ADD_VALUE)
            .addAttributeModifier(EntityAttributes.ENTITY_INTERACTION_RANGE, WitchesKitchen.id("long_attack_reach"), 4.5D, EntityAttributeModifier.Operation.ADD_VALUE));
    RegistryEntry<StatusEffect> LONG_STRIDE = create("long_stride", new LongStrideStatusEffect(StatusEffectCategory.BENEFICIAL, 0xB7410E)
            .addAttributeModifier(EntityAttributes.STEP_HEIGHT, WitchesKitchen.id("long_stride"), 1.25D, EntityAttributeModifier.Operation.ADD_VALUE));

    static List<ObjectDefinition<StatusEffect>> getStatusEffects() {
        return Collections.unmodifiableList(STATUS_EFFECTS);
    }

    static <T extends StatusEffect> RegistryEntry<StatusEffect> create(String name, T effect) {
        Validate.isTrue(effect != null);
        final Identifier id = WitchesKitchen.id(name);
        final ObjectDefinition<StatusEffect> def = new ObjectDefinition<>(id, effect);
        STATUS_EFFECTS.add(def);
        Registry.register(Registries.STATUS_EFFECT, id, effect);
        return Registries.STATUS_EFFECT.getEntry(effect);
    }

    static void init() {
        STATUS_EFFECTS.forEach(entry -> Registry.register(Registries.STATUS_EFFECT, entry.id(), entry.object()));
    }
}
