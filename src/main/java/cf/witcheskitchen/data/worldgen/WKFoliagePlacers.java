package cf.witcheskitchen.data.worldgen;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.common.world.generator.SumacFoliagePlacer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public interface WKFoliagePlacers {
    FoliagePlacerType<SumacFoliagePlacer> SUMAC_FOLIAGE_PLACER = Registry.register(BuiltInRegistries.FOLIAGE_PLACER_TYPE, WitchesKitchen.id("sumac_foliage_placer"), new FoliagePlacerType<>(SumacFoliagePlacer.CODEC));

    static void init() {

    }
}
