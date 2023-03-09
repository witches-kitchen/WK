package cf.witcheskitchen.client.integration.rei.display;

import cf.witcheskitchen.client.integration.rei.WKREIPlugin;
import cf.witcheskitchen.common.recipe.BarrelFermentingRecipe;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.Collections;
import java.util.List;

@ClientOnly
public class FermentingDisplay implements Display {

    private final List<EntryIngredient> inputs;
    private final List<EntryIngredient> output;

    public FermentingDisplay(BarrelFermentingRecipe recipe) {
        this.inputs = EntryIngredients.ofIngredients(recipe.getInputs());
        this.output = Collections.singletonList(EntryIngredients.of(recipe.getOutput()));
    }

    public static void register(DisplayRegistry registry) {
        registry.registerFiller(BarrelFermentingRecipe.class, FermentingDisplay::new);
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return this.inputs;
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return this.output;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return WKREIPlugin.FERMENTING;
    }
}