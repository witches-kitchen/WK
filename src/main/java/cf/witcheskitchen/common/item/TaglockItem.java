package cf.witcheskitchen.common.item;

import cf.witcheskitchen.common.component.WKComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class TaglockItem extends Item {
    public TaglockItem(Properties settings) {
        super(settings);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
        if (stack.has(WKComponents.TAGLOCK)) {
            var taglock = stack.get(WKComponents.TAGLOCK);
            textConsumer.accept(Component.literal(taglock.name()).setStyle(Style.EMPTY.withColor(0xF90C19)));
        }
    }
}
