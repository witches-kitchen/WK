package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public interface WKTags {

    // EntityType
    TagKey<EntityType<?>> GREATER_DEMON = register(Registries.ENTITY_TYPE, "greater_demon");


    TagKey<EntityType<?>> LESSER_DEMON = register(Registries.ENTITY_TYPE, "lesser_demon");
    TagKey<EntityType<?>> GHOST = register(Registries.ENTITY_TYPE, "ghost");
    TagKey<EntityType<?>> TAGLOCK_BLACKLIST = register(Registries.ENTITY_TYPE, "taglock_blacklist");
    TagKey<EntityType<?>> COLD_IRON_WEAK = register(Registries.ENTITY_TYPE, "cold_iron_weak");
    TagKey<EntityType<?>> COLD_IRON_IMMUNE = register(Registries.ENTITY_TYPE, "cold_iron_immune");
    TagKey<EntityType<?>> SILVER_WEAK = register(Registries.ENTITY_TYPE, "silver_weak");
    TagKey<EntityType<?>> SILVER_IMMUNE = register(Registries.ENTITY_TYPE, "silver_immune");
    TagKey<EntityType<?>> RIGHT_HAND_WITCH_SUMMON = register(Registries.ENTITY_TYPE, "right_hand_witch_summon");
    TagKey<EntityType<?>> LEFT_HAND_WITCH_SUMMON = register(Registries.ENTITY_TYPE, "left_hand_witch_summon");
    TagKey<EntityType<?>> DEMONIC = register(Registries.ENTITY_TYPE, "demonic");
    TagKey<EntityType<?>> CONSTRUCT = register(Registries.ENTITY_TYPE, "construct");

    TagKey<EntityType<?>> FLEEING_MOBS = register(Registries.ENTITY_TYPE, "fleeing_mobs");
    TagKey<EntityType<?>> AVOIDS_BUG_SPRAY = register(Registries.ENTITY_TYPE, "avoids_bug_spray");
    TagKey<EntityType<?>> AVOIDS_FELIFORM = register(Registries.ENTITY_TYPE, "avoids_feliform");

    // Block
    TagKey<Block> HEATS_CAULDRON = register(Registries.BLOCK, "heats_cauldron");

    //Item
    TagKey<Item> CAULDRON_BLACKLIST = register(Registries.ITEM, "cauldron_blacklist");
    TagKey<Item> OVEN_BLACKLIST = register(Registries.ITEM, "oven_blacklist");
    TagKey<Item> BARREL_BLACKLIST = register(Registries.ITEM, "barrel_blacklist");
    TagKey<Item> TEA_BLACKLIST = register(Registries.ITEM, "tea_blacklist");
    TagKey<Item> VALID_BREW_ITEM = register(Registries.ITEM, "valid_brew_item");
    TagKey<Item> RESETS_CAULDRON = register(Registries.ITEM, "resets_cauldron");
    TagKey<Item> WILL_OF_THE_BEAST_SOURCE = register(Registries.ITEM, "will_of_the_beast_source");
    TagKey<Item> ELDERS_GRACE_SOURCE = register(Registries.ITEM, "elders_grace_source");
    //todo: add the useless blue thing from konosuba
    TagKey<Item> GODDESS_TEARS_SOURCE = register(Registries.ITEM, "goddess_tears_source");
    TagKey<Item> LICK_O_FLAME_SOURCE = register(Registries.ITEM, "lick_o_flame_source");
    TagKey<Item> GAIAS_BREATH_SOURCE = register(Registries.ITEM, "gaias_breath_source");
    TagKey<Item> DEATHS_KISS_SOURCE = register(Registries.ITEM, "deaths_kiss_source");

    //Biome
    TagKey<Biome> HAS_BLACKTHORN = register(Registries.BIOME, "has_blackthorn");
    TagKey<Biome> HAS_ELDER = register(Registries.BIOME, "has_elder");
    TagKey<Biome> HAS_HAWTHORN = register(Registries.BIOME, "has_hawthorn");
    TagKey<Biome> HAS_JUNIPER = register(Registries.BIOME, "has_juniper");
    TagKey<Biome> HAS_ROWAN = register(Registries.BIOME, "has_rowan");
    TagKey<Biome> HAS_SUMAC = register(Registries.BIOME, "has_sumac");

    static <T> TagKey<T> register(ResourceKey<Registry<T>> key, String path) {
        final ResourceLocation resourceLoc = WitchesKitchen.id(path);
        return TagKey.create(key, resourceLoc);
    }

    // Used to control in which order static constructors are called
    static void init() {

    }
}
