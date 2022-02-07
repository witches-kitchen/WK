package cf.witcheskitchen.api;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.WKConfig;
import cf.witcheskitchen.common.registry.WKTags;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.item.Item;

public class WKApi {

    //Todo: This could go with some improvements, but it is pretty early at the moment.

    static {
        if (WKConfig.get().debugMode) {
            WK.logger.info("Witches Kitchen API: Successfully Loaded");
        }
    }

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
     * This allows one to tell if something is immune to silver, and is used to target only such mobs. This is mainly for cross-mod compat,
     * as this mod won't have silver and thus, some players might want to see the two materials behave similar.
     * TODO: Make this work better with mobs
     */
    public static boolean isSilverImmune(LivingEntity livingEntity) {
        return WKTags.SILVER_IMMUNE.contains(livingEntity.getType());
    }

    /**
     * This allows one to tell if something is weak to silver, and is used to target only such mobs. This is mainly for cross-mod compat,
     * as this mod won't have silver and thus, some players might want to see the two materials behave similar.
     * TODO: Make this work better with mobs
     */
    public static boolean isSilverWeak(LivingEntity livingEntity) {
        return WKTags.SILVER_WEAK.contains(livingEntity.getType());
    }

    /**
     * This allows one to tell if something is a summon for a right-hand (good/light/cunning man) witch mob
     * TODO: Make this work better with mobs
     */
    public static boolean isRightHandSummon(LivingEntity livingEntity) {
        return WKTags.RIGHT_HAND_WITCH_SUMMON.contains(livingEntity.getType());
    }

    /**
     * This allows one to tell if something is a summon for a left-hand (evil/dark) witch mob
     * TODO: Make this work better with mobs
     */
    public static boolean isLeftHandSummon(LivingEntity livingEntity) {
        return WKTags.LEFT_HAND_WITCH_SUMMON.contains(livingEntity.getType());
    }

    /**
     * This allows one to blacklist a mob from being taglocked
     * TODO: Make this work better with mobs
     */
    public static boolean isTaglockBlacklisted(LivingEntity livingEntity) {
        return WKTags.TAGLOCK_BLACKLIST.contains(livingEntity.getType());
    }

    /**
     * This allows one to blacklist an item from being used in ovens
     */
    public static boolean isOvenBlacklisted(Item item) {
        return WKTags.OVEN_BLACKLIST.contains(item);
    }

    /**
     * This allows one to blacklist an item from being used in cauldrons
     */
    public static boolean isCauldronBlacklisted(Item item) {
        return WKTags.CAULDRON_BLACKLIST.contains(item);
    }

    /**
     * This allows one to blacklist an item from being used in tea pots
     */
    public static boolean isTeaBlacklisted(Item item) {
        return WKTags.TEA_BLACKLIST.contains(item);
    }

    /**
     * This allows one to blacklist an item from being used in fermenting barrels
     */
    public static boolean isBarrelBlacklisted(Item item) {
        return WKTags.BARREL_BLACKLIST.contains(item);
    }

    /**
     * This allows one to add items to cauldrons for brewing
     */
    public static boolean isValidBrewingItem(Item item) {
        return WKTags.VALID_BREW_ITEM.contains(item);
    }

    /**
     * This allows one to check if one is wearing full cold iron armor
     * Currently vacant at the moment.
     */
    public static boolean isWearingFullColdIron(LivingEntity entity) {
        return false;
    }

    /**
     * This allows one to check if one is wearing full witch robes
     * Currently vacant at the moment.
     */
    public static boolean isWearingFullWitchRobes(LivingEntity entity) {
        return false;
    }

    /**
     * This allows one to check if one is wearing full witch hunter robes
     * Currently vacant at the moment.
     */
    public static boolean isWearingFullWitchHunterRobes(LivingEntity entity) {
        return false;
    }
}
