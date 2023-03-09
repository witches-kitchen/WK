package cf.witcheskitchen.common.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CurseBundleItem extends Item {
    public int levels;

    public CurseBundleItem(Settings settings, int levels) {
        super(settings);
        this.levels = levels;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.witcheskitchen.bundle.potency" + " " + levels));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
