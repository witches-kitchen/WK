package cf.witcheskitchen.api.entity;

import net.minecraft.entity.EntityGroup;

//To be used by the main mod addons and eventual mobs
public class WKCreatureTypeEnum {

    /**
     * Demons
     */
    @SuppressWarnings("InstantiationOfUtilityClass")
    public static final EntityGroup DEMONIC = new EntityGroup();

    /**
     * Golems and tulpas
     */
    @SuppressWarnings("InstantiationOfUtilityClass")
    public static final EntityGroup CONSTRUCT = new EntityGroup();
}
