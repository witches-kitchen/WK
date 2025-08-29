package cf.witcheskitchen.common.item;

import cf.witcheskitchen.api.util.SeedTypeHelper;
import cf.witcheskitchen.common.component.WKComponents;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;

public class VariantSeedItem extends BlockItem {

    public VariantSeedItem(Block block, Properties settings) {
        super(block, settings);
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(BlockPlaceContext context) {
        ItemStack itemStack = context.getItemInHand();
        if (itemStack.has(WKComponents.SEED_TYPE)) {
            Optional<Block> blockState = SeedTypeHelper.getBlockFromComponent(itemStack.get(WKComponents.SEED_TYPE));
            if (blockState.isPresent() && this.canPlace(context, blockState.get().defaultBlockState())) {
                return blockState.get().defaultBlockState();
            }
        }
        BlockState blockState = this.getBlock().getStateForPlacement(context);
        return blockState != null && this.canPlace(context, blockState) ? blockState : null;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos hitPos = context.getClickedPos();
        Level world = context.getLevel();
        BlockState state = world.getBlockState(hitPos);
        if (state.getBlock() instanceof FarmBlock && context.getClickedFace() == Direction.UP) {
            return super.useOn(context);
        }
        return InteractionResult.FAIL;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
        MutableComponent text = SeedTypeHelper.getSeedTypeText(stack);
        if (text != null) {
            textConsumer.accept(text);
        }
        super.appendHoverText(stack, context, displayComponent, textConsumer, type);
    }
}
