package cf.witcheskitchen.common.item;

import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class CurseBundleItem extends Item {
    public int levels;

    public CurseBundleItem(Settings settings, int levels) {
        super(settings);
        this.levels = levels;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        textConsumer.accept(Text.translatable("tooltip.witcheskitchen.bundle.potency", levels));
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
    }
}
