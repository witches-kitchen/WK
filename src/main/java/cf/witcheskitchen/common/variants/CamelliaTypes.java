package cf.witcheskitchen.common.variants;

import java.util.Optional;

public enum CamelliaTypes {
    NONE(""),
    BUTTERCREAM("buttercream"),
    BISQUE("bisque"),
    FLINT("flint"),
    DEEP_LOVE("deep_love");

    CamelliaTypes(String s) {
    }

    public Optional<CamelliaTypes> next(CamelliaTypes v){
        CamelliaTypes[] variants = CamelliaTypes.values();
        int index = v.ordinal();
        if(variants.length == v.ordinal() + 1){
            return Optional.empty();
        }
        int nextIndex = index + 1;
        nextIndex %= variants.length;
        return Optional.of(variants[nextIndex]);
    }
}
