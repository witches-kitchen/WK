package cf.witcheskitchen.common.statuseffect.pehkui;

import cf.witcheskitchen.WK;
import virtuoel.pehkui.api.ScaleRegistries;
import virtuoel.pehkui.api.ScaleType;

public class WKSizeModifiers {

    public static final ScaleType PANTAGRUELIAN = ScaleRegistries.register(ScaleRegistries.SCALE_TYPES, WK.id("pantagruelian"), ScaleType.Builder.create().build());
    public static final ScaleType LILLIPUTIAN = ScaleRegistries.register(ScaleRegistries.SCALE_TYPES, WK.id("lilliputian"), ScaleType.Builder.create().build());
}
