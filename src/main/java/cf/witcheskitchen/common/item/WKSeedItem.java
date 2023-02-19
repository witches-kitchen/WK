package cf.witcheskitchen.common.item;

import cf.witcheskitchen.WitchesKitchenConfig;
import cf.witcheskitchen.common.registry.WKBlocks;
import cf.witcheskitchen.common.util.TypeHelper;
import cf.witcheskitchen.common.variants.BelladonnaTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.QuiltLoader;

import java.util.List;
import java.util.Optional;

public class WKSeedItem extends AliasedBlockItem {

    public WKSeedItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(ItemPlacementContext context) {
        ItemStack itemStack = context.getStack();
        if(itemStack.getNbt() != null && itemStack.getNbt().contains("Variant")){
            Optional<Block> blockState = TypeHelper.getBlockFromNbt(itemStack.getNbt());
            return blockState.isPresent() && this.canPlace(context, blockState.get().getDefaultState()) ? blockState.get().getDefaultState() : null;
        }else{
            BlockState blockState = this.getBlock().getPlacementState(context);
            return blockState != null && this.canPlace(context, blockState) ? blockState : null;
        }
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
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        MutableText text = TypeHelper.getTypeText(stack);
        if(text != null){
            tooltip.add(text);
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}
