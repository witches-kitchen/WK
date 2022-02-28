package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.WKConfig;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WKTags {
    public static final Tag<EntityType<?>> GREATER_DEMON = TagFactory.ENTITY_TYPE.create(new Identifier(WK.MODID, "greater_demon"));
    public static final Tag<EntityType<?>> LESSER_DEMON = TagFactory.ENTITY_TYPE.create(new Identifier(WK.MODID, "lesser_demon"));
    public static final Tag<EntityType<?>> GHOST = TagFactory.ENTITY_TYPE.create(new Identifier(WK.MODID, "ghost"));
    public static final Tag<EntityType<?>> TAGLOCK_BLACKLIST = TagFactory.ENTITY_TYPE.create(new Identifier(WK.MODID, "taglock_blacklist"));
    public static final Tag<EntityType<?>> COLD_IRON_WEAK = TagFactory.ENTITY_TYPE.create(new Identifier(WK.MODID, "cold_iron_weak"));
    public static final Tag<EntityType<?>> COLD_IRON_IMMUNE = TagFactory.ENTITY_TYPE.create(new Identifier(WK.MODID, "cold_iron_immune"));
    public static final Tag<EntityType<?>> SILVER_WEAK = TagFactory.ENTITY_TYPE.create(new Identifier(WK.MODID, "silver_iron_weak"));
    public static final Tag<EntityType<?>> SILVER_IMMUNE = TagFactory.ENTITY_TYPE.create(new Identifier(WK.MODID, "silver_iron_immune"));
    public static final Tag<EntityType<?>> RIGHT_HAND_WITCH_SUMMON = TagFactory.ENTITY_TYPE.create(new Identifier(WK.MODID, "right_hand_witch_summon"));
    public static final TagKey<EntityType<?>> LEFT_HAND_WITCH_SUMMON = registerEntity("left_hand_witch_summon");

    public static final Tag<Block> HEATS_CAULDRON = TagFactory.BLOCK.create(new Identifier(WK.MODID, "heats_cauldron"));

    public static final Tag<Item> CAULDRON_BLACKLIST = TagFactory.ITEM.create(new Identifier(WK.MODID, "cauldron_blacklist"));
    public static final Tag<Item> OVEN_BLACKLIST = TagFactory.ITEM.create(new Identifier(WK.MODID, "oven_blacklist"));
    public static final Tag<Item> BARREL_BLACKLIST = TagFactory.ITEM.create(new Identifier(WK.MODID, "barrel_blacklist"));
    public static final Tag<Item> TEA_BLACKLIST = TagFactory.ITEM.create(new Identifier(WK.MODID, "tea_blacklist"));
    public static final Tag<Item> VALID_BREW_ITEM = TagFactory.ITEM.create(new Identifier(WK.MODID, "valid_brew_item"));
    public static final Tag<Item> RESETS_CAULDRON = TagFactory.ITEM.create(new Identifier(WK.MODID, "resets_cauldron"));

    private static TagKey<EntityType<?>> registerEntity(String id) {
        return TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(id));
    }

    private static TagKey<Item> registerItem(String id) {
        return TagKey.of(Registry.ITEM_KEY, new Identifier(id));
    }

    private static TagKey<Block> registerBlock(String id) {
        return TagKey.of(Registry.BLOCK_KEY, new Identifier(id));
    }

    static {
        if (WKConfig.get().debugMode) {
            WK.logger.info("Witches Kitchen Base Custom Tags: Successfully Loaded");
        }
    }
}
