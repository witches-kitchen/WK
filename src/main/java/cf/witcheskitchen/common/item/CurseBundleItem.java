package cf.witcheskitchen.common.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class CurseBundleItem extends Item {
    public int levels;

    public CurseBundleItem(Properties settings, int levels) {
        super(settings);
        this.levels = levels;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
        textConsumer.accept(Component.translatable("tooltip.witcheskitchen.bundle.potency", levels));
        super.appendHoverText(stack, context, displayComponent, textConsumer, type);
    }
}
