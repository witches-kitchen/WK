package cf.witcheskitchen.common.variants;

import java.util.Optional;

public enum BlackberryTypes {
    NONE("");

    BlackberryTypes(String s) {
    }

    public Optional<BlackberryTypes> next(BlackberryTypes v){
        BlackberryTypes[] variants = BlackberryTypes.values();
        int index = v.ordinal();
        if(variants.length == v.ordinal() + 1){
            return Optional.empty();
        }
        int nextIndex = index + 1;
        nextIndex %= variants.length;
        return Optional.of(variants[nextIndex]);
    }
}
