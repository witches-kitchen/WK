package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public interface WKDamageSources {

    ResourceKey<DamageType> ON_OVEN = ResourceKey.create(Registries.DAMAGE_TYPE, WitchesKitchen.id("on_oven"));
    ResourceKey<DamageType> HOLY = ResourceKey.create(Registries.DAMAGE_TYPE, WitchesKitchen.id("holy"));
    ResourceKey<DamageType> HUGGING_BLACKTHORN = ResourceKey.create(Registries.DAMAGE_TYPE, WitchesKitchen.id("hugging_blackthorn"));
    ResourceKey<DamageType> PUNCHING_BLACKTHORN = ResourceKey.create(Registries.DAMAGE_TYPE, WitchesKitchen.id("punching_blackthorn"));

    // Used to control in which order static constructors are called
    static void init() {

    }
}
