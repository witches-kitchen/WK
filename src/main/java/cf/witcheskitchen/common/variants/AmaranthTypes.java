package cf.witcheskitchen.common.variants;

import java.util.Optional;

public enum AmaranthTypes {
    COMMON(""),
    SWEETBERRY("sweetberry"),
    TORCH("torch"),
    SUNDEW("sundew"),
    CREEPER("creeper"),
    VIRIDIAN("viridian"),
    GRISELIN("griselin"),
    CERISE("cerise"),
    DARK_PASSION("dark_passion"),
    FIREBIRD("firebird");

    AmaranthTypes(String s) {
    }

    public String getString(AmaranthTypes type){
        String name = "amaranth";
        return type.name().isEmpty() ? name : name + "_" + type.name();
    }

    public Optional<AmaranthTypes> next(AmaranthTypes v){
        AmaranthTypes[] variants = AmaranthTypes.values();
        int index = v.ordinal();
        if(variants.length == v.ordinal() + 1){
            return Optional.empty();
        }
        int nextIndex = index + 1;
        nextIndex %= variants.length;
        return Optional.of(variants[nextIndex]);
    }
}
