package cf.witcheskitchen.common.item;

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

import java.util.List;
import java.util.Optional;

public class WKSeedItem extends AliasedBlockItem {

    public WKSeedItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Nullable
    @Override
    protected BlockState getPlacementState(ItemPlacementContext context) {
        //BlockState blockState = this.getBlock().getPlacementState(context);
        ItemStack itemStack = context.getStack();
        if(itemStack.getNbt() != null && itemStack.getNbt().contains("Variant")){
            Optional<Block> blockState = TypeHelper.getBlockFromNbt(itemStack.getNbt());
            return blockState.isPresent() && this.canPlace(context, blockState.get().getDefaultState()) ? blockState.get().getDefaultState() : null;
        }
        return null;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getMainHandStack();
        if(!itemStack.hasNbt()){
            NbtCompound nbtCompound = new NbtCompound();
            TypeHelper.toNbt(nbtCompound, BelladonnaTypes.GLOW.getFullName(), BelladonnaTypes.GLOW.getColor());
            itemStack.getOrCreateNbt().copyFrom(nbtCompound);
        }else{
            System.out.println(itemStack.getNbt());
        }
        return super.use(world, user, hand);
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
        NbtList nbtList = stack.getOrCreateNbt().getList("Variant", NbtElement.COMPOUND_TYPE);
        if(!nbtList.isEmpty()){
            String name = nbtList.getCompound(0).getString("Name");
            if(name.contains("_")){
                name = name.substring(name.lastIndexOf("_") + 1);
            }
            String formatName = name.substring(0, 1).toUpperCase() + name.substring(1);
            NbtCompound colorNbt = nbtList.getCompound(1);
            int color = colorNbt.getInt("Color");
            tooltip.add(Text.translatable(formatName).setStyle(Style.EMPTY.withColor(color)));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}
