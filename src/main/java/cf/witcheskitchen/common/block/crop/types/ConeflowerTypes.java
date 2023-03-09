package cf.witcheskitchen.common.block.crop.types;

import java.util.Optional;

public enum ConeflowerTypes {
    COMMON("", 0xffffff),
    DANCING_LADIES("dancing_ladies", 0xffffff),
    VIOLET("violet", 0xffffff),
    QUEENS_DESIRE("queens_desire", 0xffffff),
    ROSE_DRESS("rose_dress", 0xffffff),
    SUITOR("suitor", 0xffffff),
    NETHER("nether", 0xffffff),
    LADYS_WISH("ladys_wish", 0xffffff),
    SUNGLOW("sunglow", 0xffffff),
    FLAME("flame", 0xffffff),
    GILDED("gilded", 0xffffff),
    MORNING_MIST("morning_mist", 0xffffff),
    FLEECE("fleece", 0xffffff),
    COMPANY("company", 0xffffff),
    MASQUERADE("masquerade", 0xffffff),
    PARTY_BLEND("party_blend", 0xffffff);

    private final int color;
    private final String type;

    ConeflowerTypes(String type, int color) {
        this.type = type;
        this.color = color;
    }

    public String getName() {
        return "coneflower";
    }

    public String getType() {
        return type;
    }

    public int getColor() {
        return color;
    }

    public Optional<ConeflowerTypes> next(ConeflowerTypes v) {
        ConeflowerTypes[] variants = ConeflowerTypes.values();
        int index = v.ordinal();
        if (variants.length == v.ordinal() + 1) {
            return Optional.empty();
        }
        int nextIndex = index + 1;
        nextIndex %= variants.length;
        return Optional.of(variants[nextIndex]);
    }
}
