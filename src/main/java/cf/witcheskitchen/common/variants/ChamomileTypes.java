package cf.witcheskitchen.common.variants;

import java.util.Optional;

public enum ChamomileTypes {
    NONE(""),
    VIRESCENT("virescent"),
    STARLETT("starlett"),
    DYEWORKS("dyeworks");

    ChamomileTypes(String starlett) {
    }

    public Optional<ChamomileTypes> next(ChamomileTypes v){
        ChamomileTypes[] variants = ChamomileTypes.values();
        int index = v.ordinal();
        if(variants.length == v.ordinal() + 1){
            return Optional.empty();
        }
        int nextIndex = index + 1;
        nextIndex %= variants.length;
        return Optional.of(variants[nextIndex]);
    }
}
