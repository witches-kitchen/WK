package cf.witcheskitchen.common.generator.foliage;
 /*package cf.witcheskitchen.common.generator;

import com.mojang.datafixers.kinds.Applicative;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import cf.witcheskitchen.common.registry.WKGenerator;

import java.util.Random;
import java.util.function.BiConsumer;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

public class JuniperFoliagePlacer extends FoliagePlacer {
    public static final Codec<JuniperFoliagePlacer> CODEC = RecordCodecBuilder.create(instance -> JuniperFoliagePlacer.fillFoliagePlacerFields(instance).and(((MapCodec)IntProvider.createValidatingCodec(0, 24).fieldOf("trunk_height")).forGetter(placer -> placer.trunkHeight)).apply((Applicative<JuniperFoliagePlacer, ?>)instance, JuniperFoliagePlacer::new));
    private final IntProvider trunkHeight;

    public JuniperFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider trunkHeight) {
        super(radius, offset);
        this.trunkHeight = trunkHeight;
    }

    @Override
    protected FoliagePlacerType<?> getType() {
        return WKGenerator.JUNIPER_FOLIAGE_PLACER;
    }

    @Override
    protected void generate(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, TreeFeatureConfig config, int trunkHeight, FoliagePlacer.TreeNode treeNode, int foliageHeight, int radius, int offset) {
        BlockPos blockPos = treeNode.getCenter();
        int i = random.nextInt(2);
        int j = 1;
        int k = 0;
        for (int l = offset; l >= -foliageHeight; --l) {
            this.generateSquare(world, replacer, random, config, blockPos, i, l, treeNode.isGiantTrunk());
            if (i >= j) {
                i = k;
                k = 1;
                j = Math.min(j + 1, radius + treeNode.getFoliageRadius());
                continue;
            }
            ++i;
        }
    }

    @Override
    public int getRandomHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return Math.max(4, trunkHeight - this.trunkHeight.get(random));
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int dx, int y, int dz, int radius, boolean giantTrunk) {
        return dx == radius && dz == radius && radius > 0;
    } 
}*/ 
