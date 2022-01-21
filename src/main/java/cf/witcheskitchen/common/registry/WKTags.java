package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

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
    public static final Tag<EntityType<?>> LEFT_HAND_WITCH_SUMMON = TagFactory.ENTITY_TYPE.create(new Identifier(WK.MODID, "left_hand_witch_summon"));

    public static final Tag<Block> HEATS_CAULDRON = TagFactory.BLOCK.create(new Identifier(WK.MODID, "heats_cauldron"));

    public static final Tag<Item> CAULDRON_BLACKLIST = TagFactory.ITEM.create(new Identifier(WK.MODID, "cauldron_blacklist"));
    public static final Tag<Item> OVEN_BLACKLIST = TagFactory.ITEM.create(new Identifier(WK.MODID, "oven_blacklist"));
    public static final Tag<Item> BARREL_BLACKLIST = TagFactory.ITEM.create(new Identifier(WK.MODID, "barrel_blacklist"));
    public static final Tag<Item> TEA_BLACKLIST = TagFactory.ITEM.create(new Identifier(WK.MODID, "tea_blacklist"));
}
