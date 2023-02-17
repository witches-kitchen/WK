package cf.witcheskitchen.common.variants;

import java.util.Optional;

public enum HelleboreTypes {
    NONE(""),
    MORNING_TEA("morning_tea"),
    CASANOVA("casanova"),
    BLUSHING("blushing"),
    CELADON("celadon"),
    FURY("fury"),
    ANGEL("angel"),
    TWILIGHT("twilight"),
    GRIMM("grimm"),
    NOCTURNE("nocturne");


    HelleboreTypes(String s) {
    }

    public Optional<HelleboreTypes> next(HelleboreTypes v){
        HelleboreTypes[] variants = HelleboreTypes.values();
        int index = v.ordinal();
        if(variants.length == v.ordinal() + 1){
            return Optional.empty();
        }
        int nextIndex = index + 1;
        nextIndex %= variants.length;
        return Optional.of(variants[nextIndex]);
    }
}
