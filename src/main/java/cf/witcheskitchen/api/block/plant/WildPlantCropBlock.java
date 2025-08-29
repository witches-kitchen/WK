package cf.witcheskitchen.api.block.plant;

import cf.witcheskitchen.api.block.crop.WKCropBlock;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.VegetationBlock;

public class WildPlantCropBlock extends VegetationBlock {
    public WKCropBlock wkCropBlock;

    public WildPlantCropBlock(Properties settings, WKCropBlock wkCropBlock) {
        super(settings);
        this.wkCropBlock = wkCropBlock;
    }

    @Override
    protected MapCodec<? extends VegetationBlock> codec() {
        return null;
    }
}
