package cf.witcheskitchen.api.block.plant;

import cf.witcheskitchen.api.block.crop.WKCropBlock;
import net.minecraft.block.PlantBlock;

public class WildPlantCropBlock extends PlantBlock {
    public WKCropBlock wkCropBlock;

    public WildPlantCropBlock(Settings settings, WKCropBlock wkCropBlock) {
        super(settings);
        this.wkCropBlock = wkCropBlock;
    }
}
