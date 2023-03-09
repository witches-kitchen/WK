package cf.witcheskitchen.common.block.crop.types;

import java.util.Optional;

public enum FoxgloveTypes {
    COMMON("", 0xffffff),
    SMALT("smalt", 0xffffff),
    TRANQUIL_EVENING("tranquil_evening", 0xffffff),
    PURPUREA("purpurea", 0xffffff),
    LOVELY_MORNING("lovely_morning", 0xffffff),
    IANTHINE("ianthine", 0xffffff),
    QUEENS_HAT("queens_hat", 0xffffff),
    BLUSH("blush", 0xffffff),
    ROYAL_BLANKET("royal_blanket", 0xffffff),
    LOVE("love", 0xffffff),
    BABYS_DRESS("babys_dress", 0xffffff),
    STROLL("stroll", 0xffffff),
    MAIDENS("maidens", 0xffffff),
    MORNING_FIELD("morning_field", 0xffffff),
    SIGHE_GOWN("sighe_gown", 0xffffff),
    CALAMINE("calamine", 0xffffff),
    NETHERINE("netherine", 0xffffff),
    SUNGLOW("sunglow", 0xffffff),
    SANDSTONE_TEMPLE("sandstone_temple", 0xffffff),
    FIERY_FIELD("fiery_field", 0xffffff),
    PASSION("passion", 0xffffff),
    BASTARD_AMBER("bastard_amber", 0xffffff),
    SUNDROP("sundrop", 0xffffff),
    AURULENT("aurulent", 0xffffff),
    IVORY("ivory", 0xffffff),
    NIVEOUS("niveous", 0xffffff),
    COWS_CREAM("cows_drwam", 0xffffff),
    SIGHE_MIST("sighe_mist", 0xffffff),
    PURITY("purify", 0xffffff);

    private final int color;
    private final String type;

    FoxgloveTypes(String type, int color) {
        this.type = type;
        this.color = color;
    }

    public String getName() {
        return "foxglove";
    }

    public String getType() {
        return type;
    }

    public int getColor() {
        return color;
    }

    public Optional<FoxgloveTypes> next(FoxgloveTypes v) {
        FoxgloveTypes[] variants = FoxgloveTypes.values();
        int index = v.ordinal();
        if (variants.length == v.ordinal() + 1) {
            return Optional.empty();
        }
        int nextIndex = index + 1;
        nextIndex %= variants.length;
        return Optional.of(variants[nextIndex]);
    }
}
