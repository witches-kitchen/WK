package cf.witcheskitchen.api.dowsing;

//Todo: Add java docs and other things
public record DowsingDefinition(
    boolean isFluid,
    boolean isOre,
    boolean isTreasure,
    boolean isFalseLead,
    boolean isAmbush,
    boolean isLivingBeing,
    boolean isStructure,

    int distanceFromPlayerMin,
    int distanceFromPlayerMax
) {
}
