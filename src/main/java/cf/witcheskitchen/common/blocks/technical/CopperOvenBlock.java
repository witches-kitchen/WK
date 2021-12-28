package cf.witcheskitchen.common.blocks.technical;

import net.minecraft.block.Oxidizable;
import net.minecraft.block.Waterloggable;

public class CopperOvenBlock extends WKOvenBlock implements Waterloggable, Oxidizable {
    public CopperOvenBlock(Settings settings) {
        super(settings);
    }

    @Override
    public OxidationLevel getDegradationLevel() {
        return null;
    }
}
