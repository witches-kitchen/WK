package cf.witcheskitchen.common.block.crop.types;

import java.util.Optional;

public enum ChamomileTypes {
    COMMON("", 0xffffff),
    VIRESCENT("virescent", 0xffffff),
    STARLETT("starlett", 0xffffff),
    DYEWORKS("dyeworks", 0xffffff);

    private final int color;
    private final String type;

    ChamomileTypes(String type, int color) {
        this.type = type;
        this.color = color;
    }

    public String getName() {
        return "chamomile";
    }

    public String getType() {
        return type;
    }

    public int getColor() {
        return color;
    }

    public Optional<ChamomileTypes> next(ChamomileTypes v) {
        ChamomileTypes[] variants = ChamomileTypes.values();
        int index = v.ordinal();
        if (variants.length == v.ordinal() + 1) {
            return Optional.empty();
        }
        int nextIndex = index + 1;
        nextIndex %= variants.length;
        return Optional.of(variants[nextIndex]);
    }
}
