package cf.witcheskitchen.api;

import cf.witcheskitchen.common.registry.WKTags;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.*;

public class WKApi {

    /**
     * This allows the mod to track various beings of spiritual origin, as well as undead origin.
     * Use this if you wish to blanket-target such entities.
     */
    public static boolean isSpiritualEntity(LivingEntity entity) {
        return entity.isUndead() || entity.getGroup() == WKCreatureTypeEnum.DEMONIC || entity.getGroup() == WKCreatureTypeEnum.ANGELIC || entity.getGroup() == WKCreatureTypeEnum.FAE || entity.getGroup() == WKCreatureTypeEnum.ELEMENTAL || entity.getGroup() == WKCreatureTypeEnum.ENIGMATIC || entity.getGroup() == EntityGroup.UNDEAD || entity instanceof EndermanEntity || entity instanceof GhastEntity || entity instanceof BlazeEntity || entity instanceof VexEntity || entity instanceof GuardianEntity || entity instanceof ElderGuardianEntity;
    }

    /**
     * This allows one to tell if something is a lesser demon, i.e. not a boss.
     * TODO: Make this work better with mobs
     */
    public static boolean isLesserDemon(LivingEntity livingEntity) {
        return WKTags.LESSER_DEMON.contains(livingEntity.getType());
    }

    /**
     * This allows one to tell if something is a greater demon, i.e. something on the level of a boss.
     * TODO: Make this work better with mobs
     */
    public static boolean isGreaterDemon(LivingEntity livingEntity) {
        return WKTags.GREATER_DEMON.contains(livingEntity.getType());
    }

    /**
     * This allows one to tell if something is a ghost, and is used to target only such mobs.
     * TODO: Make this work better with mobs
     */
    public static boolean isGhost(LivingEntity livingEntity) {
        return WKTags.GHOST.contains(livingEntity.getType());
    }

    /**
     * This allows one to tell if something is immune to cold iron, and is used to target only such mobs.
     * TODO: Make this work better with mobs
     */
    public static boolean isColdIronImmune(LivingEntity livingEntity) {
        return WKTags.COLD_IRON_IMMUNE.contains(livingEntity.getType());
    }

    /**
     * This allows one to tell if something is weak to cold iron, and is used to target only such mobs.
     * TODO: Make this work better with mobs
     */
    public static boolean isColdIronWeak(LivingEntity livingEntity) {
        return WKTags.COLD_IRON_WEAK.contains(livingEntity.getType());
    }

    /**
     * This allows one to tell if something is immune to silver, and is used to target only such mobs.
     * TODO: Make this work better with mobs
     */
    public static boolean isSilverImmune(LivingEntity livingEntity) {
        return WKTags.SILVER_IMMUNE.contains(livingEntity.getType());
    }

    /**
     * This allows one to tell if something is weak to cold iron, and is used to target only such mobs.
     * TODO: Make this work better with mobs
     */
    public static boolean isSilverWeak(LivingEntity livingEntity) {
        return WKTags.SILVER_WEAK.contains(livingEntity.getType());
    }

    /**
     * This allows one to blacklist a mob from being taglocked
     * TODO: Make this work better with mobs
     */
    public static boolean isTaglockBlacklisted(LivingEntity livingEntity) {
        return WKTags.TAGLOCK_BLACKLIST.contains(livingEntity.getType());
    }
}
