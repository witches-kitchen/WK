package cf.witcheskitchen.common.variants;

import java.util.Optional;

public enum MintTypes {
    NONE("");

    MintTypes(String s) {
    }

    public Optional<MintTypes> next(MintTypes v){
        MintTypes[] variants = MintTypes.values();
        int index = v.ordinal();
        if(variants.length == v.ordinal() + 1){
            return Optional.empty();
        }
        int nextIndex = index + 1;
        nextIndex %= variants.length;
        return Optional.of(variants[nextIndex]);
    }
}
