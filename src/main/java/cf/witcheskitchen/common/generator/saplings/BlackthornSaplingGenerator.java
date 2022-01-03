package cf.witcheskitchen.common.generator.saplings;

import java.util.Random;

import org.jetbrains.annotations.Nullable;

import cf.witcheskitchen.common.registry.WKGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class BlackthornSaplingGenerator extends WKSaplingGenerator{
    @Nullable
    protected ConfiguredFeature<?,?> getTreeFeature (Random random, boolean bees){
        return WKGenerator.BLACKTHORN_TREE;
    }
}
