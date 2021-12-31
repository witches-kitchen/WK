package cf.witcheskitchen.api;

import net.minecraft.entity.EntityGroup;

//To be used by the main mod addons and eventual mobs
public class WKCreatureTypeEnum {

    /**
     * Demons
     */
    @SuppressWarnings("InstantiationOfUtilityClass")
    public static final EntityGroup DEMONIC = new EntityGroup();

    /**
     * Angels
     */
    @SuppressWarnings("InstantiationOfUtilityClass")
    public static final EntityGroup ANGELIC = new EntityGroup();

    /**
     * Fae
     */
    @SuppressWarnings("InstantiationOfUtilityClass")
    public static final EntityGroup FAE = new EntityGroup();

    /**
     * Elementals
     */
    @SuppressWarnings("InstantiationOfUtilityClass")
    public static final EntityGroup ELEMENTAL = new EntityGroup();

    /**
     * Eldritch horrors
     */
    @SuppressWarnings("InstantiationOfUtilityClass")
    public static final EntityGroup ENIGMATIC = new EntityGroup();

    /**
     * Golems and tulpas
     */
    @SuppressWarnings("InstantiationOfUtilityClass")
    public static final EntityGroup CONSTRUCT = new EntityGroup();
}
