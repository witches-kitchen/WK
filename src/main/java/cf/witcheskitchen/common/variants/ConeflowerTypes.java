package cf.witcheskitchen.common.variants;

import java.util.Optional;

public enum ConeflowerTypes {
    NONE(""),
    DANCING_LADIES("dancing_ladies"),
    VIOLET("violet"),
    QUEENS_DESIRE("queens_desire"),
    ROSE_DRESS("rose_dress"),
    SUITOR("suitor"),
    NETHER("nether"),
    LADYS_WISH("ladys_wish"),
    SUNGLOW("sunglow"),
    FLAME("flame"),
    GILDED("gilded"),
    MORNING_MIST("morning_mist"),
    FLEECE("fleece"),
    COMPANY("company"),
    MASQUERADE("masquerade"),
    PARTY_BLEND("party_blend");

    ConeflowerTypes(String s) {
    }

    public Optional<ConeflowerTypes> next(ConeflowerTypes v){
        ConeflowerTypes[] variants = ConeflowerTypes.values();
        int index = v.ordinal();
        if(variants.length == v.ordinal() + 1){
            return Optional.empty();
        }
        int nextIndex = index + 1;
        nextIndex %= variants.length;
        return Optional.of(variants[nextIndex]);
    }
}
