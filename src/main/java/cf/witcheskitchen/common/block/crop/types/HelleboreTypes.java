package cf.witcheskitchen.common.block.crop.types;

import java.util.Optional;

public enum HelleboreTypes {
    COMMON("", 0xffffff),
    MORNING_TEA("morning_tea", 0xffffff),
    CASANOVA("casanova", 0xffffff),
    BLUSHING("blushing", 0xffffff),
    CELADON("celadon", 0xffffff),
    FURY("fury", 0xffffff),
    ANGEL("angel", 0xffffff),
    TWILIGHT("twilight", 0xffffff),
    GRIMM("grimm", 0xffffff),
    NOCTURNE("nocturne", 0xffffff);


    private final int color;
    private final String type;

    HelleboreTypes(String type, int color) {
        this.type = type;
        this.color = color;
    }

    public String getName() {
        return "hellebore";
    }

    public String getType() {
        return type;
    }

    public int getColor() {
        return color;
    }

    public Optional<HelleboreTypes> next(HelleboreTypes v) {
        HelleboreTypes[] variants = HelleboreTypes.values();
        int index = v.ordinal();
        if (variants.length == v.ordinal() + 1) {
            return Optional.empty();
        }
        int nextIndex = index + 1;
        nextIndex %= variants.length;
        return Optional.of(variants[nextIndex]);
    }
}
