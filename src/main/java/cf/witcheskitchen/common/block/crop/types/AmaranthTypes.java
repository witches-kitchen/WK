package cf.witcheskitchen.common.block.crop.types;

import java.util.Optional;

public enum AmaranthTypes {
    COMMON("", 0xffffff),
    SWEETBERRY("sweetberry", 0xffffff),
    TORCH("torch", 0xffffff),
    SUNDEW("sundew", 0xffffff),
    CREEPER("creeper", 0xffffff),
    VIRIDIAN("viridian", 0xffffff),
    GRISELIN("griselin", 0xffffff),
    CERISE("cerise", 0xffffff),
    DARK_PASSION("dark_passion", 0xffffff),
    FIREBIRD("firebird", 0xffffff);

    private final int color;
    private final String type;

    AmaranthTypes(String type, int color) {
        this.type = type;
        this.color = color;
    }

    public String getName() {
        return "amaranth";
    }

    public String getType() {
        return type;
    }

    public int getColor() {
        return color;
    }

    public Optional<AmaranthTypes> next(AmaranthTypes v) {
        AmaranthTypes[] variants = AmaranthTypes.values();
        int index = v.ordinal();
        if (variants.length == v.ordinal() + 1) {
            return Optional.empty();
        }
        int nextIndex = index + 1;
        nextIndex %= variants.length;
        return Optional.of(variants[nextIndex]);
    }
}
