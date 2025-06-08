package cf.witcheskitchen.common.item;

import cf.witcheskitchen.api.util.SeedTypeHelper;
import cf.witcheskitchen.common.component.WKComponents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;

public class VariantSeedItem extends BlockItem {

    public VariantSeedItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(ItemPlacementContext context) {
        ItemStack itemStack = context.getStack();
        if (itemStack.contains(WKComponents.SEED_TYPE)) {
            Optional<Block> blockState = SeedTypeHelper.getBlockFromComponent(itemStack.get(WKComponents.SEED_TYPE));
            if (blockState.isPresent() && this.canPlace(context, blockState.get().getDefaultState())) {
                return blockState.get().getDefaultState();
            }
        }
        BlockState blockState = this.getBlock().getPlacementState(context);
        return blockState != null && this.canPlace(context, blockState) ? blockState : null;
    }

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

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        MutableText text = SeedTypeHelper.getSeedTypeText(stack);
        if (text != null) {
            textConsumer.accept(text);
        }
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
    }
}
