package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

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
    TagKey<EntityType<?>> IS_CERVID = register(Registry.ENTITY_TYPE_KEY, "is_cervid");
    // Block
    TagKey<Block> HEATS_CAULDRON = register(Registry.BLOCK_KEY, "heats_cauldron");
    //Item
    TagKey<Item> CAULDRON_BLACKLIST = register(Registry.ITEM_KEY, "cauldron_blacklist");
    TagKey<Item> OVEN_BLACKLIST = register(Registry.ITEM_KEY, "oven_blacklist");
    TagKey<Item> BARREL_BLACKLIST = register(Registry.ITEM_KEY, "barrel_blacklist");
    TagKey<Item> TEA_BLACKLIST = register(Registry.ITEM_KEY, "tea_blacklist");
    TagKey<Item> VALID_BREW_ITEM = register(Registry.ITEM_KEY, "valid_brew_item");
    TagKey<Item> RESETS_CAULDRON = register(Registry.ITEM_KEY, "resets_cauldron");
    //Seeds
    TagKey<Item> SEED_AMARANTH = register(Registry.ITEM_KEY, "seeds/seed_amaranth");
    TagKey<Item> SEED_BELLADONNA = register(Registry.ITEM_KEY, "seeds/seed_belladonna");
    TagKey<Item> SEED_IRIS = register(Registry.ITEM_KEY, "seeds/seed_iris");
    TagKey<Item> SEED_GINGER = register(Registry.ITEM_KEY, "seeds/seed_ginger");
    TagKey<Item> SEED_CAMELLIA = register(Registry.ITEM_KEY, "seeds/seed_camellia");
    TagKey<Item> SEED_ST_JOHNS_WORT = register(Registry.ITEM_KEY, "seeds/seed_st_johns_wort");
    TagKey<Item> SEED_SANGUINARY = register(Registry.ITEM_KEY, "seeds/seed_sanguinary");
    TagKey<Item> SEED_WORMWOOD = register(Registry.ITEM_KEY, "seeds/seed_wormwood");
    TagKey<Item> SEED_FOXGLOVE = register(Registry.ITEM_KEY, "seeds/seed_foxglove");
    TagKey<Item> SEED_CONEFLOWER = register(Registry.ITEM_KEY, "seeds/seed_coneflower");
    TagKey<Item> SEED_CHAMOMILE = register(Registry.ITEM_KEY, "seeds/seed_chamomile");
    TagKey<Item> SEED_BRIAR = register(Registry.ITEM_KEY, "seeds/seed_briar");
    TagKey<Item> SEED_MINT = register(Registry.ITEM_KEY, "seeds/seed_mint");
    TagKey<Item> SEED_BLACKBERRY = register(Registry.ITEM_KEY, "seeds/seed_blackberry");

    static <T> TagKey<T> register(final RegistryKey<? extends Registry<T>> key, final String path) {
        final Identifier resourceLoc = WitchesKitchen.id(path);
        return TagKey.of(key, resourceLoc);

    }

    // Used to control in which order static constructors are called
    static void init() {

    }
}
