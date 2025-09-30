package cf.witcheskitchen.api.dowsing;

//Todo: Add java docs and other things


public record DowsingDefinition(

    /**
     * Is the outcome a fluid, like water, or oil?
     */
    boolean isFluid,

    /**
     * Is the outcome an ore?
     *
     */
    boolean isOre,

    /**
     * Is the outcome a treasure, like a long forgotten chest?
     */
    boolean isTreasure,

    /**
     * Is the outcome nothing at all?
     */
    boolean isFalseLead,

    /**
     * Is outcome an ambush by hostile mobs?
     */
    boolean isAmbush,

    /**
     * Is outcome a random mob?
     */
    boolean isLivingBeing,

    /**
     * Is outcome a structure?
     */
    boolean isStructure,

    /**
     * Minimum distance to find what your rods picked up
     * Measured in blocks.
     * Blocks are 1 cubic meter long.
     */
    int distanceFromPlayerMin,

    /**
     * Maximum distance to find what your rods picked up
     * Measured in blocks.
     * Blocks are 1 cubic meter long.
     */
    int distanceFromPlayerMax
) {
}
