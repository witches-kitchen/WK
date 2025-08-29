package cf.witcheskitchen.common.world.generator;


//this will all need to be reworked to better suit our needs

import cf.witcheskitchen.data.worldgen.WKFoliagePlacers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class SumacFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<SumacFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(instance -> SumacFoliagePlacer.foliagePlacerParts(instance).apply(instance, SumacFoliagePlacer::new));

    public SumacFoliagePlacer(IntProvider intProvider, IntProvider intProvider2) {
        super(intProvider, intProvider2);
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return WKFoliagePlacers.SUMAC_FOLIAGE_PLACER;
    }

    @Override
    protected void createFoliage(LevelSimulatedReader world, FoliageSetter placer, RandomSource random, TreeConfiguration config, int trunkHeight, FoliageAttachment node, int foliageHeight, int radius, int offset) {
        boolean bl = node.doubleTrunk();
        BlockPos blockPos = node.pos().above(offset);
        this.placeLeavesRow(world, placer, random, config, blockPos, radius + node.radiusOffset(), -1 - foliageHeight, bl);
        this.placeLeavesRow(world, placer, random, config, blockPos, radius - 1, -foliageHeight, bl);
        this.placeLeavesRow(world, placer, random, config, blockPos, radius + node.radiusOffset() - 1, 0, bl);
    }

    @Override
    public int foliageHeight(RandomSource random, int trunkHeight, TreeConfiguration config) {
        return 0;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        if (y == 0) {
            return (dx > 1 || dz > 1) && dx != 0 && dz != 0;
        }
        return dx == radius && dz == radius && radius > 0;
    }
}