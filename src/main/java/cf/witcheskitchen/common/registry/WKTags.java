package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.entity.EntityType;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class WKTags {
    public static final Tag<EntityType<?>> GREATER_DEMON = TagFactory.ENTITY_TYPE.create(new Identifier(WK.MODID, "greater_demon"));
    public static final Tag<EntityType<?>> LESSER_DEMON = TagFactory.ENTITY_TYPE.create(new Identifier(WK.MODID, "lesser_demon"));
    public static final Tag<EntityType<?>> GHOST = TagFactory.ENTITY_TYPE.create(new Identifier(WK.MODID, "ghost"));
    public static final Tag<EntityType<?>> TAGLOCK_BLACKLIST = TagFactory.ENTITY_TYPE.create(new Identifier(WK.MODID, "taglock_blacklist"));
}
