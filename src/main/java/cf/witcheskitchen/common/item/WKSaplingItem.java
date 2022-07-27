package cf.witcheskitchen.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

public class WKSaplingItem extends AliasedBlockItem {

    private final Block saplingLeafBlock;
    private final Block baseLeafBlock;

    public WKSaplingItem(Block block, Block saplingLeafBlock, Block baseLeafBlock, Settings settings) {
        super(block, settings);
        this.saplingLeafBlock = saplingLeafBlock;
        this.baseLeafBlock = baseLeafBlock;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockState atPos = context.getWorld().getBlockState(context.getBlockPos());
        if (atPos.getBlock() == baseLeafBlock) {
            if (!context.getPlayer().isCreative()) {
                context.getStack().decrement(1);
            }
            context.getWorld().setBlockState(context.getBlockPos(), saplingLeafBlock.getDefaultState());
            return ActionResult.CONSUME;
        }
        return super.useOnBlock(context);
    }
}
