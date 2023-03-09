package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public interface WKTags {

    // EntityType
    TagKey<EntityType<?>> GREATER_DEMON = register(RegistryKeys.ENTITY_TYPE, "greater_demon");


    TagKey<EntityType<?>> LESSER_DEMON = register(RegistryKeys.ENTITY_TYPE, "lesser_demon");
    TagKey<EntityType<?>> GHOST = register(RegistryKeys.ENTITY_TYPE, "ghost");
    TagKey<EntityType<?>> TAGLOCK_BLACKLIST = register(RegistryKeys.ENTITY_TYPE, "taglock_blacklist");
    TagKey<EntityType<?>> COLD_IRON_WEAK = register(RegistryKeys.ENTITY_TYPE, "cold_iron_weak");
    TagKey<EntityType<?>> COLD_IRON_IMMUNE = register(RegistryKeys.ENTITY_TYPE, "cold_iron_immune");
    TagKey<EntityType<?>> SILVER_WEAK = register(RegistryKeys.ENTITY_TYPE, "silver_weak");
    TagKey<EntityType<?>> SILVER_IMMUNE = register(RegistryKeys.ENTITY_TYPE, "silver_immune");
    TagKey<EntityType<?>> RIGHT_HAND_WITCH_SUMMON = register(RegistryKeys.ENTITY_TYPE, "right_hand_witch_summon");
    TagKey<EntityType<?>> LEFT_HAND_WITCH_SUMMON = register(RegistryKeys.ENTITY_TYPE, "left_hand_witch_summon");

    // Block
    TagKey<Block> HEATS_CAULDRON = register(RegistryKeys.BLOCK, "heats_cauldron");

    //Item
    TagKey<Item> CAULDRON_BLACKLIST = register(RegistryKeys.ITEM, "cauldron_blacklist");
    TagKey<Item> OVEN_BLACKLIST = register(RegistryKeys.ITEM, "oven_blacklist");
    TagKey<Item> BARREL_BLACKLIST = register(RegistryKeys.ITEM, "barrel_blacklist");
    TagKey<Item> TEA_BLACKLIST = register(RegistryKeys.ITEM, "tea_blacklist");
    TagKey<Item> VALID_BREW_ITEM = register(RegistryKeys.ITEM, "valid_brew_item");
    TagKey<Item> RESETS_CAULDRON = register(RegistryKeys.ITEM, "resets_cauldron");
    TagKey<Item> WILL_OF_THE_BEAST_SOURCE = register(RegistryKeys.ITEM, "will_of_the_beast_source");
    TagKey<Item> ELDERS_GRACE_SOURCE = register(RegistryKeys.ITEM, "elders_grace_source");
    //todo: add the useless blue thing from konosuba
    TagKey<Item> GODDESS_TEARS_SOURCE = register(RegistryKeys.ITEM, "goddess_tears_source");
    TagKey<Item> LICK_O_FLAME_SOURCE = register(RegistryKeys.ITEM, "lick_o_flame_source");
    TagKey<Item> GAIAS_BREATH_SOURCE = register(RegistryKeys.ITEM, "gaias_breath_source");
    TagKey<Item> DEATHS_KISS_SOURCE = register(RegistryKeys.ITEM, "deaths_kiss_source");

    //Biome
    TagKey<Biome> HAS_BLACKTHORN = register(RegistryKeys.BIOME, "has_blackthorn");
    TagKey<Biome> HAS_ELDER = register(RegistryKeys.BIOME, "has_elder");
    TagKey<Biome> HAS_HAWTHORN = register(RegistryKeys.BIOME, "has_hawthorn");
    TagKey<Biome> HAS_JUNIPER = register(RegistryKeys.BIOME, "has_juniper");
    TagKey<Biome> HAS_ROWAN = register(RegistryKeys.BIOME, "has_rowan");
    TagKey<Biome> HAS_SUMAC = register(RegistryKeys.BIOME, "has_sumac");

    static <T> TagKey<T> register(RegistryKey<Registry<T>> key, String path) {
        final Identifier resourceLoc = WitchesKitchen.id(path);
        return TagKey.of(key, resourceLoc);
    }

    // Used to control in which order static constructors are called
    static void init() {

    }
}
