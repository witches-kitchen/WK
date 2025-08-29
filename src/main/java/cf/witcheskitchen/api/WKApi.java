package cf.witcheskitchen.api;

import cf.witcheskitchen.common.component.WKComponents;
import cf.witcheskitchen.common.item.TaglockItem;
import cf.witcheskitchen.common.registry.WKTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class WKApi {

    /**
     * This allows the mod to track various beings of spiritual origin, as well as undead origin.
     * Use this if you wish to blanket-target such entities.
     */
    public static boolean isSpiritualEntity(LivingEntity entity) {
        return entity.getType().is(EntityTypeTags.UNDEAD) ||
                entity.getType().is(WKTags.DEMONIC) ||
                entity instanceof EnderMan ||
                entity instanceof Ghast ||
                entity instanceof Blaze ||
                entity instanceof Vex ||
                entity instanceof Guardian;
    }

    /**
     * This allows one to tell if something is a lesser demon, i.e. not a boss.
     */
    public static boolean isLesserDemon(LivingEntity livingEntity) {
        return livingEntity.getType().is(WKTags.LESSER_DEMON);
    }

    /**
     * This allows one to tell if something is a greater demon, i.e. something on the level of a boss.
     */
    public static boolean isGreaterDemon(LivingEntity livingEntity) {
        return livingEntity.getType().is(WKTags.GREATER_DEMON);
    }

    /**
     * This allows one to tell if something is a ghost, and is used to target only such mobs.
     */
    public static boolean isGhost(LivingEntity livingEntity) {
        return livingEntity.getType().is(WKTags.GHOST);
    }

    /**
     * This allows one to tell if something is immune to cold iron, and is used to target only such mobs.
     */
    public static boolean isColdIronImmune(LivingEntity livingEntity) {
        return livingEntity.getType().is(WKTags.COLD_IRON_IMMUNE);
    }

    /**
     * This allows one to tell if something is weak to cold iron, and is used to target only such mobs.
     */
    public static boolean isColdIronWeak(LivingEntity livingEntity) {
        return livingEntity.getType().is(WKTags.COLD_IRON_WEAK);
    }

    /**
     * This allows one to tell if something is immune to silver, and is used to target only such mobs. This is mainly for cross-mod compat,
     * as this mod won't have silver and thus, some players might want to see the two materials behave similar.
     */
    public static boolean isSilverImmune(LivingEntity livingEntity) {
        return livingEntity.getType().is(WKTags.SILVER_IMMUNE);
    }

    /**
     * This allows one to tell if something is weak to silver, and is used to target only such mobs. This is mainly for cross-mod compat,
     * as this mod won't have silver and thus, some players might want to see the two materials behave similar.
     */
    public static boolean isSilverWeak(LivingEntity livingEntity) {
        return livingEntity.getType().is(WKTags.SILVER_WEAK);
    }

    /**
     * This allows one to tell if something is a summon for a right-hand (good/light/cunning man) witch mob
     */
    public static boolean isRightHandSummon(LivingEntity livingEntity) {
        return livingEntity.getType().is(WKTags.RIGHT_HAND_WITCH_SUMMON);
    }

    /**
     * This allows one to tell if something is a summon for a left-hand (evil/dark) witch mob
     */
    public static boolean isLeftHandSummon(LivingEntity livingEntity) {
        return livingEntity.getType().is(WKTags.LEFT_HAND_WITCH_SUMMON);
    }

    @Nullable
    public static LivingEntity getTaglockEntity(Level world, ItemStack taglock) {
        if (world instanceof ServerLevel && taglock.getItem() instanceof TaglockItem && hasTaglock(taglock)) {
            UUID uuid = getTaglockUUID(taglock);
            if (uuid != null) {
                for (ServerLevel serverWorld : world.getServer().getAllLevels()) {
                    if (serverWorld.getEntity(uuid) instanceof LivingEntity livingEntity) {
                        return livingEntity;
                    }
                }
            }
        }
        return null;
    }

    public static boolean hasTaglock(ItemStack stack) {
        return stack.has(WKComponents.TAGLOCK);
    }

    @Nullable
    public static UUID getTaglockUUID(ItemStack stack) {
        if (hasTaglock(stack)) {
            return stack.get(WKComponents.TAGLOCK).uuid();
        }
        return null;
    }
}
