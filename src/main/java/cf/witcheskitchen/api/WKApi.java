package cf.witcheskitchen.api;

import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.VexEntity;

public class WKApi {

    public static boolean isSpiritualEntity(LivingEntity entity) {
        return entity.isUndead() || entity.getGroup() == WKCreatureTypeEnum.DEMONIC || entity.getGroup() == WKCreatureTypeEnum.ANGELIC || entity.getGroup() == WKCreatureTypeEnum.FAE || entity.getGroup() == WKCreatureTypeEnum.ELEMENTAL || entity.getGroup() == WKCreatureTypeEnum.ENIGMATIC || entity.getGroup() == EntityGroup.UNDEAD || entity instanceof EndermanEntity || entity instanceof GhastEntity || entity instanceof BlazeEntity || entity instanceof VexEntity;
    }
}
