package cf.witcheskitchen.common.variants;

import java.util.Optional;

public enum GingerTypes {
    NONE("");

    GingerTypes(String s) {
    }

    public Optional<GingerTypes> next(GingerTypes v){
        GingerTypes[] variants = GingerTypes.values();
        int index = v.ordinal();
        if(variants.length == v.ordinal() + 1){
            return Optional.empty();
        }
        int nextIndex = index + 1;
        nextIndex %= variants.length;
        return Optional.of(variants[nextIndex]);
    }
}
