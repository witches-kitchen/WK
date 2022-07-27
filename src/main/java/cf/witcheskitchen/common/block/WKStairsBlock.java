package cf.witcheskitchen.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.Waterloggable;

public class WKStairsBlock extends StairsBlock implements Waterloggable {
    public WKStairsBlock(BlockState baseBlockState, Settings settings) {
        super(baseBlockState, settings);
    }
}
