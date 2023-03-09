package cf.witcheskitchen.common.block.crop.types;

import java.util.Optional;

public enum IrisTypes {
    COMMON("", 0xffffff),
    OCEAN("ocean", 0xffffff),
    DEEP_SEA("deep_sea", 0xffffff),
    BLEEDING_HEART("bleeding_heart", 0xffffff);

    private final int color;
    private final String type;

    IrisTypes(String type, int color) {
        this.type = type;
        this.color = color;
    }

    public String getName() {
        return "iris";
    }

    public String getType() {
        return type;
    }

    public int getColor() {
        return color;
    }

    public Optional<IrisTypes> next(IrisTypes v) {
        IrisTypes[] variants = IrisTypes.values();
        int index = v.ordinal();
        if (variants.length == v.ordinal() + 1) {
            return Optional.empty();
        }
        int nextIndex = index + 1;
        nextIndex %= variants.length;
        return Optional.of(variants[nextIndex]);
    }
}
