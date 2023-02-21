package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public interface WKTags {

    // EntityType
    TagKey<EntityType<?>> GREATER_DEMON = register(Registry.ENTITY_TYPE_KEY, "greater_demon");
    TagKey<EntityType<?>> LESSER_DEMON = register(Registry.ENTITY_TYPE_KEY, "lesser_demon");
    TagKey<EntityType<?>> GHOST = register(Registry.ENTITY_TYPE_KEY, "ghost");
    TagKey<EntityType<?>> TAGLOCK_BLACKLIST = register(Registry.ENTITY_TYPE_KEY, "taglock_blacklist");
    TagKey<EntityType<?>> COLD_IRON_WEAK = register(Registry.ENTITY_TYPE_KEY, "cold_iron_weak");
    TagKey<EntityType<?>> COLD_IRON_IMMUNE = register(Registry.ENTITY_TYPE_KEY, "cold_iron_immune");
    TagKey<EntityType<?>> SILVER_WEAK = register(Registry.ENTITY_TYPE_KEY, "silver_weak");
    TagKey<EntityType<?>> SILVER_IMMUNE = register(Registry.ENTITY_TYPE_KEY, "silver_immune");
    TagKey<EntityType<?>> RIGHT_HAND_WITCH_SUMMON = register(Registry.ENTITY_TYPE_KEY, "right_hand_witch_summon");
    TagKey<EntityType<?>> LEFT_HAND_WITCH_SUMMON = register(Registry.ENTITY_TYPE_KEY, "left_hand_witch_summon");

    // Block
    TagKey<Block> HEATS_CAULDRON = register(Registry.BLOCK_KEY, "heats_cauldron");

    //Item
    TagKey<Item> CAULDRON_BLACKLIST = register(Registry.ITEM_KEY, "cauldron_blacklist");
    TagKey<Item> OVEN_BLACKLIST = register(Registry.ITEM_KEY, "oven_blacklist");
    TagKey<Item> BARREL_BLACKLIST = register(Registry.ITEM_KEY, "barrel_blacklist");
    TagKey<Item> TEA_BLACKLIST = register(Registry.ITEM_KEY, "tea_blacklist");
    TagKey<Item> VALID_BREW_ITEM = register(Registry.ITEM_KEY, "valid_brew_item");
    TagKey<Item> RESETS_CAULDRON = register(Registry.ITEM_KEY, "resets_cauldron");

    //Biome
    TagKey<Biome> HAS_BLACKTHORN = register(Registry.BIOME_KEY, "has_blackthorn");
    TagKey<Biome> HAS_ELDER = register(Registry.BIOME_KEY, "has_elder");
    TagKey<Biome> HAS_HAWTHORN = register(Registry.BIOME_KEY, "has_hawthorn");
    TagKey<Biome> HAS_JUNIPER = register(Registry.BIOME_KEY, "has_juniper");
    TagKey<Biome> HAS_ROWAN = register(Registry.BIOME_KEY, "has_rowan");
    TagKey<Biome> HAS_SUMAC = register(Registry.BIOME_KEY, "has_sumac");

    static <T> TagKey<T> register(final RegistryKey<? extends Registry<T>> key, final String path) {
        final Identifier resourceLoc = WitchesKitchen.id(path);
        return TagKey.of(key, resourceLoc);

    }

    // Used to control in which order static constructors are called
    static void init() {

    }
}
