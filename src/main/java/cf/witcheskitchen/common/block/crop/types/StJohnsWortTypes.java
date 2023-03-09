package cf.witcheskitchen.common.block.crop.types;

import java.util.Optional;

public enum StJohnsWortTypes {
    COMMON("", 0xffffff);

    private final int color;
    private final String type;

    StJohnsWortTypes(String type, int color) {
        this.type = type;
        this.color = color;
    }

    public String getName() {
        return "st_johns_wort";
    }

    public String getType() {
        return type;
    }

    public int getColor() {
        return color;
    }

    public Optional<StJohnsWortTypes> next(StJohnsWortTypes v) {
        StJohnsWortTypes[] variants = StJohnsWortTypes.values();
        int index = v.ordinal();
        if (variants.length == v.ordinal() + 1) {
            return Optional.empty();
        }
        int nextIndex = index + 1;
        nextIndex %= variants.length;
        return Optional.of(variants[nextIndex]);
    }
}
