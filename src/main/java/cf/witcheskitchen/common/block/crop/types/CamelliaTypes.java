package cf.witcheskitchen.common.block.crop.types;

import java.util.Optional;

public enum CamelliaTypes {
    COMMON("", 0xffffff),
    BUTTERCREAM("buttercream", 0xffffff),
    BISQUE("bisque", 0xffffff),
    FLINT("flint", 0xffffff),
    DEEP_LOVE("deep_love", 0xffffff);

    private final int color;
    private final String type;

    CamelliaTypes(String type, int color) {
        this.type = type;
        this.color = color;
    }

    public String getName() {
        return "camellia";
    }

    public String getType() {
        return type;
    }

    public int getColor() {
        return color;
    }

    public Optional<CamelliaTypes> next(CamelliaTypes v) {
        CamelliaTypes[] variants = CamelliaTypes.values();
        int index = v.ordinal();
        if (variants.length == v.ordinal() + 1) {
            return Optional.empty();
        }
        int nextIndex = index + 1;
        nextIndex %= variants.length;
        return Optional.of(variants[nextIndex]);
    }
}
