package cf.witcheskitchen.common.block.crop.types;

import java.util.Optional;

public enum SanguinaryTypes {
    COMMON("", 0xffffff),
    MEADOW("meadow", 0xffffff),
    BLUSHING("blushing", 0xffffff),
    SUNSET("sunset", 0xffffff),
    MADDER("madder", 0xffffff),
    AUREOLIN("aureolin", 0xffffff);

    private final int color;
    private final String type;

    SanguinaryTypes(String type, int color) {
        this.type = type;
        this.color = color;
    }

    public String getName() {
        return "sanguinary";
    }

    public String getType() {
        return type;
    }

    public int getColor() {
        return color;
    }

    public Optional<SanguinaryTypes> next(SanguinaryTypes v) {
        SanguinaryTypes[] variants = SanguinaryTypes.values();
        int index = v.ordinal();
        if (variants.length == v.ordinal() + 1) {
            return Optional.empty();
        }
        int nextIndex = index + 1;
        nextIndex %= variants.length;
        return Optional.of(variants[nextIndex]);
    }
}
