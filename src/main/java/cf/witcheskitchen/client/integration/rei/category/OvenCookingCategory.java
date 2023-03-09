package cf.witcheskitchen.client.integration.rei.category;

import cf.witcheskitchen.client.integration.rei.WKREIPlugin;
import cf.witcheskitchen.client.integration.rei.display.OvenCookingDisplay;
import cf.witcheskitchen.common.registry.WKBlocks;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@ClientOnly
public class OvenCookingCategory implements DisplayCategory<OvenCookingDisplay> {

    public static final EntryStack<ItemStack> ICON = EntryStacks.of(WKBlocks.IRON_WITCHES_OVEN);
    public static final Text TITLE = Text.translatable("rei.witcheskitchen.oven_cooking");

    public static void register(CategoryRegistry registry) {
        registry.add(new OvenCookingCategory());
        registry.addWorkstations(WKREIPlugin.OVEN_COOKING, ICON, EntryStacks.of(WKBlocks.COPPER_WITCHES_OVEN), EntryStacks.of(WKBlocks.EXPOSED_COPPER_WITCHES_OVEN), EntryStacks.of(WKBlocks.WEATHERED_COPPER_WITCHES_OVEN), EntryStacks.of(WKBlocks.OXIDIZED_COPPER_WITCHES_OVEN));
    }

    @Override
    public Renderer getIcon() {
        return ICON;
    }

    @Override
    public Text getTitle() {
        return TITLE;
    }

    @Override
    public CategoryIdentifier<? extends OvenCookingDisplay> getCategoryIdentifier() {
        return WKREIPlugin.OVEN_COOKING;
    }

    @Override
    public List<Widget> setupDisplay(OvenCookingDisplay display, Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 41, bounds.y + 16);
        final double time = display.getTime();
        final DecimalFormat df = new DecimalFormat("###.##");
        final List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 61, startPoint.y + 9)));
        widgets.add(Widgets.createBurningFire(new Point(startPoint.x + 1, startPoint.y + 20)).animationDurationMS(10000));
        widgets.add(Widgets.createLabel(new Point(bounds.getX() + bounds.getWidth() - 5, bounds.getY() + 5), Text.translatable("category.rei.cooking.time&xp", df.format(display.getExperience()), df.format(time / 20d))).noShadow().rightAligned().color(0xFF404040, 0xFFBBBBBB));
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 24, startPoint.y + 8)).animationDurationTicks(time));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 1, startPoint.y + 1)).entries(display.getInputEntries().get(0)).markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 61, startPoint.y + 9)).entries(display.getOutputEntries().get(0)).disableBackground().markOutput());
        return widgets;
    }
}
