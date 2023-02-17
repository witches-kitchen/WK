package cf.witcheskitchen.common.variants;

import java.util.Optional;

public enum SanguinaryTypes {
    NONE(""),
    MEADOW("meadow"),
    BLUSHING("blushing"),
    SUNSET("sunset"),
    MADDER("madder"),
    AUREOLIN("aureolin");

    SanguinaryTypes(String s) {
    }

    public Optional<SanguinaryTypes> next(SanguinaryTypes v){
        SanguinaryTypes[] variants = SanguinaryTypes.values();
        int index = v.ordinal();
        if(variants.length == v.ordinal() + 1){
            return Optional.empty();
        }
        int nextIndex = index + 1;
        nextIndex %= variants.length;
        return Optional.of(variants[nextIndex]);
    }
}
