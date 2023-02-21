package cf.witcheskitchen.api;

import cf.witcheskitchen.common.registry.WKTags;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.item.Item;

public class WKApi {

    /**
     * This allows the mod to track various beings of spiritual origin, as well as undead origin.
     * Use this if you wish to blanket-target such entities.
     */
    public static boolean isSpiritualEntity(LivingEntity entity) {
        return entity.isUndead() ||
                entity.getGroup() == WKCreatureTypeEnum.DEMONIC ||
                entity.getGroup() == WKCreatureTypeEnum.ANGELIC ||
                entity.getGroup() == WKCreatureTypeEnum.FAE ||
                entity.getGroup() == WKCreatureTypeEnum.ELEMENTAL ||
                entity.getGroup() == WKCreatureTypeEnum.ENIGMATIC ||
                entity.getGroup() == EntityGroup.UNDEAD ||
                entity instanceof EndermanEntity ||
                entity instanceof GhastEntity ||
                entity instanceof BlazeEntity ||
                entity instanceof VexEntity ||
                entity instanceof GuardianEntity;
    }

    /**
     * This allows one to tell if something is a lesser demon, i.e. not a boss.
     */
    public static boolean isLesserDemon(LivingEntity livingEntity) {
        return livingEntity.getType().isIn(WKTags.LESSER_DEMON);
    }

    /**
     * This allows one to tell if something is a greater demon, i.e. something on the level of a boss.
     */
    public static boolean isGreaterDemon(LivingEntity livingEntity) {
        return livingEntity.getType().isIn(WKTags.GREATER_DEMON);
    }

    /**
     * This allows one to tell if something is a ghost, and is used to target only such mobs.
     */
    public static boolean isGhost(LivingEntity livingEntity) {
        return livingEntity.getType().isIn(WKTags.GHOST);
    }

    /**
     * This allows one to tell if something is immune to cold iron, and is used to target only such mobs.
     */
    public static boolean isColdIronImmune(LivingEntity livingEntity) {
        return livingEntity.getType().isIn(WKTags.COLD_IRON_IMMUNE);
    }

    /**
     * This allows one to tell if something is weak to cold iron, and is used to target only such mobs.
     */
    public static boolean isColdIronWeak(LivingEntity livingEntity) {
        return livingEntity.getType().isIn(WKTags.COLD_IRON_WEAK);
    }

    /**
     * This allows one to tell if something is immune to silver, and is used to target only such mobs. This is mainly for cross-mod compat,
     * as this mod won't have silver and thus, some players might want to see the two materials behave similar.
     */
    public static boolean isSilverImmune(LivingEntity livingEntity) {
        return livingEntity.getType().isIn(WKTags.SILVER_IMMUNE);
    }

    /**
     * This allows one to tell if something is weak to silver, and is used to target only such mobs. This is mainly for cross-mod compat,
     * as this mod won't have silver and thus, some players might want to see the two materials behave similar.
     */
    public static boolean isSilverWeak(LivingEntity livingEntity) {
        return livingEntity.getType().isIn(WKTags.SILVER_WEAK);
    }

    /**
     * This allows one to tell if something is a summon for a right-hand (good/light/cunning man) witch mob
     */
    public static boolean isRightHandSummon(LivingEntity livingEntity) {
        return livingEntity.getType().isIn(WKTags.RIGHT_HAND_WITCH_SUMMON);
    }

    /**
     * This allows one to tell if something is a summon for a left-hand (evil/dark) witch mob
     */
    public static boolean isLeftHandSummon(LivingEntity livingEntity) {
        return livingEntity.getType().isIn(WKTags.LEFT_HAND_WITCH_SUMMON);
    }

    /**
     * This allows one to blacklist a mob from being taglocked
     */
    public static boolean isTaglockBlacklisted(LivingEntity livingEntity) {
        return livingEntity.getType().isIn(WKTags.TAGLOCK_BLACKLIST);
    }

    /**
     * This allows one to blacklist an item from being used in ovens
     */
    public static boolean isOvenBlacklisted(Item item) {
        return item.getDefaultStack().isIn((WKTags.OVEN_BLACKLIST));
    }

    /**
     * This allows one to blacklist an item from being used in cauldrons
     */
    public static boolean isCauldronBlacklisted(Item item) {
        return item.getDefaultStack().isIn((WKTags.CAULDRON_BLACKLIST));
    }

    /**
     * This allows one to blacklist an item from being used in tea pots
     */
    public static boolean isTeaBlacklisted(Item item) {
        return item.getDefaultStack().isIn(WKTags.TEA_BLACKLIST);
    }

    /**
     * This allows one to blacklist an item from being used in fermenting barrels
     */
    public static boolean isBarrelBlacklisted(Item item) {
        return item.getDefaultStack().isIn(WKTags.BARREL_BLACKLIST);
    }

    /**
     * This allows one to add items to cauldrons for brewing
     */
    public static boolean isValidBrewingItem(Item item) {
        return item.getDefaultStack().isIn(WKTags.VALID_BREW_ITEM);
    }
}
