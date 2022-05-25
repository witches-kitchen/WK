package cf.witcheskitchen.common.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WKStairsBlock extends StairsBlock implements Waterloggable {
    public WKStairsBlock(BlockState baseBlockState, Settings settings) {
        super(baseBlockState, settings);
    }
}
