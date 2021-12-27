package cf.witcheskitchen.common.blocks.technical;

import net.minecraft.block.Block;
import net.minecraft.block.Oxidizable;
import net.minecraft.block.Waterloggable;

public class OvenBlock extends Block implements Waterloggable, Oxidizable {
    public OvenBlock(Settings settings) {
        super(settings);
    }

    @Override
    public OxidationLevel getDegradationLevel() {
        return null;
    }
}
