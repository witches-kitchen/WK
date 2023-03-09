package cf.witcheskitchen.client.integration.rei.category;

import cf.witcheskitchen.client.integration.rei.WKREIPlugin;
import cf.witcheskitchen.client.integration.rei.display.FermentingDisplay;
import cf.witcheskitchen.common.registry.WKBlocks;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.ArrayList;
import java.util.List;

@ClientOnly
public class FermentingCategory implements DisplayCategory<FermentingDisplay> {

    public static final EntryStack<ItemStack> ICON = EntryStacks.of(WKBlocks.OAK_BREWING_BARREL);
    public static final Text TITLE = Text.translatable("rei.witcheskitchen.fermenting");

    public static void register(CategoryRegistry registry) {
        registry.add(new FermentingCategory());
        registry.addWorkstations(WKREIPlugin.FERMENTING, ICON, EntryStacks.of(WKBlocks.SPRUCE_BREWING_BARREL), EntryStacks.of(WKBlocks.BIRCH_BREWING_BARREL), EntryStacks.of(WKBlocks.JUNGLE_BREWING_BARREL), EntryStacks.of(WKBlocks.ACACIA_BREWING_BARREL), EntryStacks.of(WKBlocks.DARK_OAK_BREWING_BARREL), EntryStacks.of(WKBlocks.CRIMSON_BREWING_BARREL), EntryStacks.of(WKBlocks.WARPED_BREWING_BARREL));
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
    public CategoryIdentifier<? extends FermentingDisplay> getCategoryIdentifier() {
        return WKREIPlugin.FERMENTING;
    }

    @Override
    public List<Widget> setupDisplay(FermentingDisplay display, Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 64, bounds.getCenterY() - 16);
        final Point outputPoint = new Point(startPoint.x + 90, startPoint.y + 8);
        final List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 57, startPoint.y + 8)));
        List<EntryIngredient> inputs = display.getInputEntries();
        this.buildContainer(widgets, inputs, startPoint);
        widgets.add(Widgets.createResultSlotBackground(outputPoint));
        widgets.add(Widgets.createSlot(outputPoint).entries(display.getOutputEntries().get(0)).disableBackground().markOutput());
        return widgets;
    }

    private void buildContainer(final List<Widget> widgets, List<EntryIngredient> inputs, Point startPoint) {
        widgets.add(Widgets.createSlot(new Point(startPoint.x, startPoint.y)).entries(inputs.get(0)).markInput());
        if (inputs.size() > 1) {
            widgets.add(Widgets.createSlot(new Point(startPoint.x + 18, startPoint.y)).entries(inputs.get(1)).markInput());
            if (inputs.size() > 2) {
                widgets.add(Widgets.createSlot(new Point(startPoint.x + 36, startPoint.y)).entries(inputs.get(2)).markInput());
                if (inputs.size() > 3) {
                    widgets.add(Widgets.createSlot(new Point(startPoint.x, startPoint.y + 18)).entries(inputs.get(3)).markInput());
                    if (inputs.size() > 4) {
                        widgets.add(Widgets.createSlot(new Point(startPoint.x + 18, startPoint.y + 18)).entries(inputs.get(4)).markInput());
                        if (inputs.size() > 5) {
                            widgets.add(Widgets.createSlot(new Point(startPoint.x + 36, startPoint.y + 18)).entries(inputs.get(5)).markInput());
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getDisplayHeight() {
        return 52;
    }
}
