package cf.witcheskitchen.common.block.crop.types;

import java.util.Optional;

public enum BelladonnaTypes {
    COMMON("", 0xffffff),
    GLOW("glow", 0xffffff),
    NOCTURNAL("nocturnal", 0xffffff);

    private final int color;
    private final String type;

    BelladonnaTypes(String type, int color) {
        this.type = type;
        this.color = color;
    }

    public String getName() {
        return "belladonna";
    }

    public String getType() {
        return type;
    }

    public int getColor() {
        return color;
    }

    public Optional<BelladonnaTypes> next(BelladonnaTypes v) {
        BelladonnaTypes[] variants = BelladonnaTypes.values();
        int index = v.ordinal();
        if (variants.length == v.ordinal() + 1) {
            return Optional.empty();
        }
        int nextIndex = index + 1;
        nextIndex %= variants.length;
        return Optional.of(variants[nextIndex]);
    }


}
