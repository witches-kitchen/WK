package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.WKConfig;
import cf.witcheskitchen.WKIdentifier;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public class WKTags {

    // EntityType
    public static final TagKey<EntityType<?>> GREATER_DEMON = register(Registry.ENTITY_TYPE_KEY, "greater_demon");
    public static final TagKey<EntityType<?>> LESSER_DEMON = register(Registry.ENTITY_TYPE_KEY, "lesser_demon");
    public static final TagKey<EntityType<?>> GHOST = register(Registry.ENTITY_TYPE_KEY, "ghost");
    public static final TagKey<EntityType<?>> TAGLOCK_BLACKLIST = register(Registry.ENTITY_TYPE_KEY, "taglock_blacklist");
    public static final TagKey<EntityType<?>> COLD_IRON_WEAK = register(Registry.ENTITY_TYPE_KEY, "cold_iron_weak");
    public static final TagKey<EntityType<?>> COLD_IRON_IMMUNE = register(Registry.ENTITY_TYPE_KEY, "cold_iron_immune");
    public static final TagKey<EntityType<?>> SILVER_WEAK = register(Registry.ENTITY_TYPE_KEY, "silver_weak");
    public static final TagKey<EntityType<?>> SILVER_IMMUNE = register(Registry.ENTITY_TYPE_KEY, "silver_immune");
    public static final TagKey<EntityType<?>> RIGHT_HAND_WITCH_SUMMON = register(Registry.ENTITY_TYPE_KEY, "right_hand_witch_summon");
    public static final TagKey<EntityType<?>> LEFT_HAND_WITCH_SUMMON = register(Registry.ENTITY_TYPE_KEY, "left_hand_witch_summon");
    public static final TagKey<EntityType<?>> IS_CERVID = register(Registry.ENTITY_TYPE_KEY, "is_cervid");
    // Block
    public static final TagKey<Block> HEATS_CAULDRON = register(Registry.BLOCK_KEY, "heats_cauldron");
    //Item
    public static final TagKey<Item> CAULDRON_BLACKLIST = register(Registry.ITEM_KEY, "cauldron_blacklist");
    public static final TagKey<Item> OVEN_BLACKLIST = register(Registry.ITEM_KEY, "oven_blacklist");
    public static final TagKey<Item> BARREL_BLACKLIST = register(Registry.ITEM_KEY, "barrel_blacklist");
    public static final TagKey<Item> TEA_BLACKLIST = register(Registry.ITEM_KEY, "tea_blacklist");
    public static final TagKey<Item> VALID_BREW_ITEM = register(Registry.ITEM_KEY, "valid_brew_item");
    public static final TagKey<Item> RESETS_CAULDRON = register(Registry.ITEM_KEY, "resets_cauldron");
    //Seeds
    public static final TagKey<Item> SEED_AMARANTH = register(Registry.ITEM_KEY, "seeds/seed_amaranth");
    public static final TagKey<Item> SEED_BELLADONNA = register(Registry.ITEM_KEY, "seeds/seed_belladonna");
    public static final TagKey<Item> SEED_IRIS = register(Registry.ITEM_KEY, "seeds/seed_iris");
    public static final TagKey<Item> SEED_GINGER = register(Registry.ITEM_KEY, "seeds/seed_ginger");
    public static final TagKey<Item> SEED_CAMELLIA = register(Registry.ITEM_KEY, "seeds/seed_camellia");
    public static final TagKey<Item> SEED_ST_JOHNS_WORT = register(Registry.ITEM_KEY, "seeds/seed_st_johns_wort");
    public static final TagKey<Item> SEED_SANGUINARY = register(Registry.ITEM_KEY, "seeds/seed_sanguinary");
    public static final TagKey<Item> SEED_WORMWOOD = register(Registry.ITEM_KEY, "seeds/seed_wormwood");
    public static final TagKey<Item> SEED_FOXGLOVE = register(Registry.ITEM_KEY, "seeds/seed_foxglove");
    public static final TagKey<Item> SEED_CONEFLOWER = register(Registry.ITEM_KEY, "seeds/seed_coneflower");
    public static final TagKey<Item> SEED_CHAMOMILE = register(Registry.ITEM_KEY, "seeds/seed_chamomile");
    public static final TagKey<Item> SEED_BRIAR = register(Registry.ITEM_KEY, "seeds/seed_briar");
    public static final TagKey<Item> SEED_MINT = register(Registry.ITEM_KEY, "seeds/seed_mint");
    public static final TagKey<Item> SEED_BLACKBERRY = register(Registry.ITEM_KEY, "seeds/seed_blackberry");

    static {
        if (WKConfig.getInstance().debugMode) {
            WK.LOGGER.info("Witches Kitchen Base Custom Tags: Successfully Loaded");
        }
    }

    static <T> TagKey<T> register(final RegistryKey<? extends Registry<T>> key, final String path) {
        final Identifier resourceLoc = new WKIdentifier(path);
        return TagKey.of(key, resourceLoc);

    }

    // Used to control in which order static constructors are called
    public static void register() {

    }
}
