package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.api.registry.ObjectDefinition;
import cf.witcheskitchen.common.statuseffect.*;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface WKStatusEffects {
    List<ObjectDefinition<MobEffect>> STATUS_EFFECTS = new ArrayList<>();

    Holder<MobEffect> HORROR = create("horror", new HorrorStatusEffect(MobEffectCategory.HARMFUL, 0x555D50));
    Holder<MobEffect> HELLFIRE = create("hellfire", new HellfireStatusEffect(MobEffectCategory.HARMFUL, 0xA91101));
    Holder<MobEffect> FROST_SHIELD = create("frost_shield", new FrostShieldStatusEffect(MobEffectCategory.BENEFICIAL, 0xAFDBF5));
    Holder<MobEffect> FROSTBITE = create("frostbite", new FrostbiteStatusEffect(MobEffectCategory.HARMFUL, 0xAFDBF5));
    //RegistryEntry<StatusEffect> DRUNK = create("drunk", new DrunkStatusEffect(StatusEffectCategory.NEUTRAL, 0x7B3F00));
    Holder<MobEffect> FIRE_SHIELD = create("fire_shield", new FireShieldStatusEffect(MobEffectCategory.BENEFICIAL, 0xAF28500));
    Holder<MobEffect> DISROBING = create("disrobing", new DisrobingStatusEffect(MobEffectCategory.HARMFUL, 0xFDF5E6));
    Holder<MobEffect> COOLDOWN = create("cooldown", new CooldownStatusEffect(MobEffectCategory.NEUTRAL, 0x1F75FE));

    Holder<MobEffect> PANTAGRUELIAN = create("pantagruelian", new CooldownStatusEffect(MobEffectCategory.NEUTRAL, 0x1F75FE));

    Holder<MobEffect> LILLIPUTIAN = create("lilliputian", new CooldownStatusEffect(MobEffectCategory.NEUTRAL, 0x1F75FE));

    Holder<MobEffect> GROWTH = create("growth", new GrowthStatusEffect(MobEffectCategory.BENEFICIAL, 0x4F7942));
    Holder<MobEffect> LOVE = create("love", new LoveStatusEffect(MobEffectCategory.BENEFICIAL, 0xFFB7C5));
    Holder<MobEffect> PARALYSIS = create("paralysis", new ParalysisStatusEffect(MobEffectCategory.HARMFUL, 0xFADA5E)
        .addAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE, WitchesKitchen.id("paralysis_decreased_reach"), -9.0D, AttributeModifier.Operation.ADD_VALUE)
        .addAttributeModifier(Attributes.ENTITY_INTERACTION_RANGE, WitchesKitchen.id("paralysis_decreased_entity_reach"), -9.D, AttributeModifier.Operation.ADD_VALUE));
    Holder<MobEffect> PHASING = create("phasing", new PhasingStatusEffect(MobEffectCategory.NEUTRAL, 0x7851A9));
    Holder<MobEffect> SHADOWS = create("shadows", new PhasingStatusEffect(MobEffectCategory.BENEFICIAL, 0x86608E));
    Holder<MobEffect> CORROSION = create("corrosion", new CorrosionStatusEffect(MobEffectCategory.HARMFUL, 0x3FFF00)
        .addAttributeModifier(Attributes.ARMOR, WitchesKitchen.id("corrosion_armor_decrease"), -4D, AttributeModifier.Operation.ADD_VALUE)
        .addAttributeModifier(Attributes.ARMOR_TOUGHNESS, WitchesKitchen.id("corrosion_armor_toughness_decrease"), -2D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    Holder<MobEffect> REINFORCEMENT = create("reinforcement", new ReinforcementStatusEffect(MobEffectCategory.BENEFICIAL, 0x4000FF)
        .addAttributeModifier(Attributes.ARMOR, WitchesKitchen.id("reinforcement_armor"), 4D, AttributeModifier.Operation.ADD_VALUE)
        .addAttributeModifier(Attributes.ARMOR_TOUGHNESS, WitchesKitchen.id("reinforcement_armor_toughness"), 2D, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    Holder<MobEffect> FELIFORM = create("feliform", new FeliformStatusEffect(MobEffectCategory.BENEFICIAL, 0x228B22));

    Holder<MobEffect> DRUNK = create("drunk", new DrunkStatusEffect(MobEffectCategory.HARMFUL, 0x228B22));
    Holder<MobEffect> BUG_SPRAY = create("bug_spray", new BugSprayStatusEffect(MobEffectCategory.BENEFICIAL, 0x32CD32));
    Holder<MobEffect> LONG_REACH = create("long_reach", new LongReachStatusEffect(MobEffectCategory.BENEFICIAL, 0x964a6e)
        .addAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE, WitchesKitchen.id("long_reach"), 5.5D, AttributeModifier.Operation.ADD_VALUE)
        .addAttributeModifier(Attributes.ENTITY_INTERACTION_RANGE, WitchesKitchen.id("long_attack_reach"), 4.5D, AttributeModifier.Operation.ADD_VALUE));
    Holder<MobEffect> LONG_STRIDE = create("long_stride", new LongStrideStatusEffect(MobEffectCategory.BENEFICIAL, 0xB7410E)
        .addAttributeModifier(Attributes.STEP_HEIGHT, WitchesKitchen.id("long_stride"), 1.25D, AttributeModifier.Operation.ADD_VALUE));

    static List<ObjectDefinition<MobEffect>> getStatusEffects() {
        return Collections.unmodifiableList(STATUS_EFFECTS);
    }

    static <T extends MobEffect> Holder<MobEffect> create(String name, T effect) {
        Validate.isTrue(effect != null);
        final ResourceLocation id = WitchesKitchen.id(name);
        final ObjectDefinition<MobEffect> def = new ObjectDefinition<>(id, effect);
        STATUS_EFFECTS.add(def);
        Registry.register(BuiltInRegistries.MOB_EFFECT, id, effect);
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect);
    }

    static void init() {
        STATUS_EFFECTS.forEach(entry -> Registry.register(BuiltInRegistries.MOB_EFFECT, entry.id(), entry.object()));
    }
}
