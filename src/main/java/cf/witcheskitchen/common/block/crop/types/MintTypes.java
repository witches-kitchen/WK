package cf.witcheskitchen.common.block.crop.types;

import java.util.Optional;

public enum MintTypes {
    COMMON("", 0xffffff);

    private final int color;
    private final String type;

    MintTypes(String type, int color) {
        this.type = type;
        this.color = color;
    }

    public String getName() {
        return "mint";
    }

    public String getType() {
        return type;
    }

    public int getColor() {
        return color;
    }

    public Optional<MintTypes> next(MintTypes v) {
        MintTypes[] variants = MintTypes.values();
        int index = v.ordinal();
        if (variants.length == v.ordinal() + 1) {
            return Optional.empty();
        }
        int nextIndex = index + 1;
        nextIndex %= variants.length;
        return Optional.of(variants[nextIndex]);
    }
}
