package cf.witcheskitchen.api;

import cf.witcheskitchen.api.entity.WKCreatureTypeEnum;
import cf.witcheskitchen.common.item.TaglockItem;
import cf.witcheskitchen.common.registry.WKTags;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class WKApi {

    /**
     * This allows the mod to track various beings of spiritual origin, as well as undead origin.
     * Use this if you wish to blanket-target such entities.
     */
    public static boolean isSpiritualEntity(LivingEntity entity) {
        return entity.isUndead() ||
                entity.getGroup() == WKCreatureTypeEnum.DEMONIC ||
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

    @Nullable
    public static LivingEntity getTaglockEntity(World world, ItemStack taglock) {
        if (world instanceof ServerWorld && taglock.getItem() instanceof TaglockItem && hasTaglock(taglock)) {
            UUID uuid = getTaglockUUID(taglock);
            if (uuid != null) {
                for (ServerWorld serverWorld : world.getServer().getWorlds()) {
                    if (serverWorld.getEntity(uuid) instanceof LivingEntity livingEntity) {
                        return livingEntity;
                    }
                }
            }
        }
        return null;
    }

    public static boolean hasTaglock(ItemStack stack) {
        return stack.hasNbt() && stack.getOrCreateNbt().contains("Uuid");
    }

    @Nullable
    public static UUID getTaglockUUID(ItemStack stack) {
        if (hasTaglock(stack)) {
            return stack.getOrCreateNbt().getUuid("Uuid");
        }
        return null;
    }
}
