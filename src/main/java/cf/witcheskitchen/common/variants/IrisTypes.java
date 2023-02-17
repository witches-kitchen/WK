package cf.witcheskitchen.common.variants;

import java.util.Optional;

public enum IrisTypes {
    NONE(""),
    OCEAN("ocean"),
    DEEP_SEA("deep_sea"),
    BLEEDING_HEART("bleeding_heart");

    IrisTypes(String s) {
    }

    public Optional<IrisTypes> next(IrisTypes v){
        IrisTypes[] variants = IrisTypes.values();
        int index = v.ordinal();
        if(variants.length == v.ordinal() + 1){
            return Optional.empty();
        }
        int nextIndex = index + 1;
        nextIndex %= variants.length;
        return Optional.of(variants[nextIndex]);
    }
}
