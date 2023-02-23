package cf.witcheskitchen.common.item;

import cf.witcheskitchen.api.util.TextUtils;
import cf.witcheskitchen.data.DimColorReloadListener;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public class WaystoneItem extends Item {
    public WaystoneItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ItemStack stack = context.getStack();
        if(!context.getWorld().isClient()){
            stack.getOrCreateNbt().putLong("BlockPos", context.getBlockPos().offset(context.getSide()).asLong());
            stack.getOrCreateNbt().putString("Dimension", context.getWorld().getRegistryKey().getValue().toString());
        }
        return super.useOnBlock(context);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(stack.getNbt() != null && stack.getOrCreateNbt().contains("BlockPos") && stack.getOrCreateNbt().contains("Dimension")){
            String dimension = stack.getOrCreateNbt().getString("Dimension");
            int color = 0xffffff;
            for(Pair<String, Integer> dimData : DimColorReloadListener.COLOR_DATA){
                if(dimData.getFirst().equals(dimension)){
                    color = dimData.getSecond();
                }
            }

            String formattedDim = TextUtils.capitalizeString(dimension.substring(dimension.indexOf(":") + 1));
            BlockPos pos = BlockPos.fromLong(stack.getNbt().getLong("BlockPos"));

            tooltip.add(TextUtils.formattedFromTwoStrings("Dimension", formattedDim, 0xFFAA00, color));
            tooltip.add(TextUtils.formattedFromTwoStrings("Position", pos.getX() + " " + pos.getY() + " " + pos.getZ(), 0xFFAA00, 0x55FFFF));
        }
    }
}
