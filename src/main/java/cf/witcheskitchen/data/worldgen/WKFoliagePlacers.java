package cf.witcheskitchen.data.worldgen;

import cf.witcheskitchen.common.world.generator.SumacFoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public interface WKFoliagePlacers {
    FoliagePlacerType<SumacFoliagePlacer> SUMAC_FOLIAGE_PLACER = FoliagePlacerType.register("sumac_foliage_placer", SumacFoliagePlacer.CODEC);

    static void init(){

    }
}
