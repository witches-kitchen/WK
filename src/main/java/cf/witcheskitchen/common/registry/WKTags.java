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
    public static final TagKey<EntityType<?>> GREATER_DEMON = registerEntity("greater_demon");
    public static final TagKey<EntityType<?>> LESSER_DEMON = registerEntity("lesser_demon");
    public static final TagKey<EntityType<?>> GHOST = registerEntity("ghost");
    public static final TagKey<EntityType<?>> TAGLOCK_BLACKLIST = registerEntity("taglock_blacklist");
    public static final TagKey<EntityType<?>> COLD_IRON_WEAK = registerEntity("cold_iron_weak");
    public static final TagKey<EntityType<?>> COLD_IRON_IMMUNE = registerEntity("cold_iron_immune");
    public static final TagKey<EntityType<?>> SILVER_WEAK = registerEntity("silver_iron_weak");
    public static final TagKey<EntityType<?>> SILVER_IMMUNE = registerEntity("silver_iron_immune");
    public static final TagKey<EntityType<?>> RIGHT_HAND_WITCH_SUMMON = registerEntity("right_hand_witch_summon");
    public static final TagKey<EntityType<?>> LEFT_HAND_WITCH_SUMMON = registerEntity("left_hand_witch_summon");

    public static final TagKey<Block> HEATS_CAULDRON = registerBlock("heats_cauldron");

    public static final TagKey<Item> CAULDRON_BLACKLIST = registerItem("cauldron_blacklist");
    public static final TagKey<Item> OVEN_BLACKLIST = registerItem("oven_blacklist");
    public static final TagKey<Item> BARREL_BLACKLIST = registerItem("barrel_blacklist");
    public static final TagKey<Item> TEA_BLACKLIST = registerItem("tea_blacklist");
    public static final TagKey<Item> VALID_BREW_ITEM = registerItem("valid_brew_item");
    public static final TagKey<Item> RESETS_CAULDRON = registerItem("resets_cauldron");

    static {
        if (WKConfig.get().debugMode) {
            WK.logger.info("Witches Kitchen Base Custom Tags: Successfully Loaded");
        }
    }

    private static TagKey<EntityType<?>> registerEntity(String id) {
        return TagKey.of(Registry.ENTITY_TYPE_KEY, new Identifier(id));
    }

    private static TagKey<Item> registerItem(String id) {
        return TagKey.of(Registry.ITEM_KEY, new Identifier(id));
    }

    private static TagKey<Block> registerBlock(String id) {
        return TagKey.of(Registry.BLOCK_KEY, new Identifier(id));
    }
}
