package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.WKConfig;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WKTags {
    public static final TagKey<EntityType<?>> GREATER_DEMON = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(WK.MODID, "greater_demon"));
    public static final TagKey<EntityType<?>> LESSER_DEMON = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(WK.MODID, "lesser_demon"));
    public static final TagKey<EntityType<?>> GHOST = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(WK.MODID, "ghost"));
    public static final TagKey<EntityType<?>> TAGLOCK_BLACKLIST = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(WK.MODID, "taglock_blacklist"));
    public static final TagKey<EntityType<?>> COLD_IRON_WEAK = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(WK.MODID, "cold_iron_weak"));
    public static final TagKey<EntityType<?>> COLD_IRON_IMMUNE = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(WK.MODID, "cold_iron_immune"));
    public static final TagKey<EntityType<?>> SILVER_WEAK = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(WK.MODID, "silver_weak"));
    public static final TagKey<EntityType<?>> SILVER_IMMUNE = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(WK.MODID, "silver_immune"));
    public static final TagKey<EntityType<?>> RIGHT_HAND_WITCH_SUMMON = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(WK.MODID, "right_hand_witch_summon"));
    public static final TagKey<EntityType<?>> LEFT_HAND_WITCH_SUMMON = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(WK.MODID, "left_hand_witch_summon"));
    public static final TagKey<EntityType<?>> IS_CERVID = TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(WK.MODID, "is_cervid"));

    public static final TagKey<Block> HEATS_CAULDRON = TagKey.of(Registry.BLOCK_KEY, new Identifier(WK.MODID, "heats_cauldron"));

    public static final TagKey<Item> CAULDRON_BLACKLIST = TagKey.of(Registry.ITEM_KEY, new Identifier(WK.MODID, "cauldron_blacklist"));
    public static final TagKey<Item> OVEN_BLACKLIST = TagKey.of(Registry.ITEM_KEY, new Identifier(WK.MODID, "oven_blacklist"));
    public static final TagKey<Item> BARREL_BLACKLIST = TagKey.of(Registry.ITEM_KEY, new Identifier(WK.MODID, "barrel_blacklist"));
    public static final TagKey<Item> TEA_BLACKLIST = TagKey.of(Registry.ITEM_KEY, new Identifier(WK.MODID, "tea_blacklist"));
    public static final TagKey<Item> VALID_BREW_ITEM = TagKey.of(Registry.ITEM_KEY, new Identifier(WK.MODID, "valid_brew_item"));
    public static final TagKey<Item> RESETS_CAULDRON = TagKey.of(Registry.ITEM_KEY, new Identifier(WK.MODID, "resets_cauldron"));

    static {
        if (WKConfig.get().debugMode) {
            WK.logger.info("Witches Kitchen Base Custom Tags: Successfully Loaded");
        }
    }
}
