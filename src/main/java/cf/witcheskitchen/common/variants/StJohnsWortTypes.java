package cf.witcheskitchen.common.variants;

import java.util.Optional;

public enum StJohnsWortTypes {
    NONE("");

    StJohnsWortTypes(String s) {
    }

    public Optional<StJohnsWortTypes> next(StJohnsWortTypes v){
        StJohnsWortTypes[] variants = StJohnsWortTypes.values();
        int index = v.ordinal();
        if(variants.length == v.ordinal() + 1){
            return Optional.empty();
        }
        int nextIndex = index + 1;
        nextIndex %= variants.length;
        return Optional.of(variants[nextIndex]);
    }
}
