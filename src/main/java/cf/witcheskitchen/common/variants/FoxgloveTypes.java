package cf.witcheskitchen.common.variants;

import java.util.Optional;

public enum FoxgloveTypes {
    NONE(""),
    SMALT("smalt"),
    TRANQUIL_EVENING("tranquil_evening"),
    PURPUREA("purpurea"),
    LOVELY_MORNING("lovely_morning"),
    IANTHINE("ianthine"),
    QUEENS_HAT("queens_hat"),
    BLUSH("blush"),
    ROYAL_BLANKET("royal_blanket"),
    LOVE("love"),
    BABYS_DRESS("babys_dress"),
    STROLL("stroll"),
    MAIDENS("maidens"),
    MORNING_FIELD("morning_field"),
    SIGHE_GOWN("sighe_gown"),
    CALAMINE("calamine"),
    NETHERINE("netherine"),
    SUNGLOW("sunglow"),
    SANDSTONE_TEMPLE("sandstone_temple"),
    FIERY_FIELD("fiery_field"),
    PASSION("passion"),
    BASTARD_AMBER("bastard_amber"),
    SUNDROP("sundrop"),
    AURULENT("aurulent"),
    IVORY("ivory"),
    NIVEOUS("niveous"),
    COWS_CREAM("cows_drwam"),
    SIDHE_MIST("sidhe_mist"),
    PURITY("purify");

    FoxgloveTypes(String string) {

    }

    public Optional<FoxgloveTypes> next(FoxgloveTypes v){
        FoxgloveTypes[] variants = FoxgloveTypes.values();
        int index = v.ordinal();
        if(variants.length == v.ordinal() + 1){
            return Optional.empty();
        }
        int nextIndex = index + 1;
        nextIndex %= variants.length;
        return Optional.of(variants[nextIndex]);
    }
}
