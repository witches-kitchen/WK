package cf.witcheskitchen.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class WKSeedItem extends AliasedBlockItem {

    //private List<Category> category;

    public WKSeedItem(Block block, Settings settings) {
        super(block, settings);
    }

//    public WKSeedItem(Block block, Settings settings, List<Category> category) {
//        super(block, settings);
//        //   ((WKCropBlock) block).setSeedsItem(this);
//       // this.category = category;
//    }

//    public WKSeedItem(Block block, Settings settings, Category... category) {
//        this(block, settings, List.of(category));
//    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos hitPos = context.getBlockPos();
        World world = context.getWorld();
        BlockState state = world.getBlockState(hitPos);
        if (state.getBlock() instanceof FarmlandBlock && context.getSide() == Direction.UP) {
            return super.useOnBlock(context);
        }
        return ActionResult.FAIL;
    }

    /*@Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("info.croptopia.seed").setStyle(Style.EMPTY.withColor(Formatting.GRAY))
                .append(" ").append(category.get(0).asString().toLowerCase(Locale.ROOT)));
    }*/

//    @SuppressWarnings("DeprecatedIsStillUsed")
//    @Deprecated(forRemoval = true)
//    public List<Category> getCategory() {
//        return category;
//    }
//
//    @Deprecated(forRemoval = true)
//    public void setCategory(List<Category> category) {
//        this.category = category;
//    }
}
