package cf.witcheskitchen.common.variants;

import java.util.Optional;

public enum BelladonnaTypes {
    COMMON("", 0xff2660),
    GLOW("glow", 0xff2660),
    NOCTURNAL("nocturne", 0xff2660);

    private final int color;
    private final String name;

    BelladonnaTypes(String name, int color) {
        this.name = name;
        this.color = color;
    }

    public String getFullName(){
        String id = "belladonna";
        return this.name.isEmpty() ? "" : id + "_" + this.name;
    }

    public String getName(){
        return name;
    }

    public int getColor() {
        return color;
    }

    public Optional<BelladonnaTypes> next(BelladonnaTypes v){
        BelladonnaTypes[] variants = BelladonnaTypes.values();
        int index = v.ordinal();
        if(variants.length == v.ordinal() + 1){
            return Optional.empty();
        }
        int nextIndex = index + 1;
        nextIndex %= variants.length;
        return Optional.of(variants[nextIndex]);
    }


}
