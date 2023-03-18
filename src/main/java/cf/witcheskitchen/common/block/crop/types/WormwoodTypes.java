package cf.witcheskitchen.common.block.crop.types;

import java.util.Optional;

public enum WormwoodTypes {
    COMMON("", 0xffffff);

    private final int color;
    private final String type;

    WormwoodTypes(String type, int color) {
        this.type = type;
        this.color = color;
    }

    static Optional<WormwoodTypes> next(WormwoodTypes v) {
        WormwoodTypes[] variants = WormwoodTypes.values();
        int index = v.ordinal();
        if (variants.length == v.ordinal() + 1) {
            return Optional.empty();
        }
        int nextIndex = index + 1;
        nextIndex %= variants.length;
        return Optional.of(variants[nextIndex]);
    }

    public String getName() {
        return "wormwood";
    }

    public String getType() {
        return type;
    }

    public int getColor() {
        return color;
    }
}
