package cf.witcheskitchen.common.variants;

import java.util.Optional;

public enum BriarTypes {
    NONE("");

    BriarTypes(String s) {
    }

    public Optional<BriarTypes> next(BriarTypes v){
        BriarTypes[] variants = BriarTypes.values();
        int index = v.ordinal();
        if(variants.length == v.ordinal() + 1){
            return Optional.empty();
        }
        int nextIndex = index + 1;
        nextIndex %= variants.length;
        return Optional.of(variants[nextIndex]);
    }
}
