package cf.witcheskitchen.common.item;

import cf.witcheskitchen.common.component.WKComponents;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class TaglockItem extends Item {
    public TaglockItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        if (stack.contains(WKComponents.TAGLOCK)) {
            var taglock = stack.get(WKComponents.TAGLOCK);
            textConsumer.accept(Text.literal(taglock.name()).setStyle(Style.EMPTY.withColor(0xF90C19)));
        }
    }
}
