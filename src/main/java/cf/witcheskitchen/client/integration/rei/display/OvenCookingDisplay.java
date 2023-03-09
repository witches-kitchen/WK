package cf.witcheskitchen.client.integration.rei.display;

import cf.witcheskitchen.client.integration.rei.WKREIPlugin;
import cf.witcheskitchen.common.recipe.OvenCookingRecipe;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.Collections;
import java.util.List;

@ClientOnly
public class OvenCookingDisplay implements Display {

    private final EntryIngredient input;
    private final List<EntryIngredient> outputs;

    private final int time;
    private final float experience;

    public OvenCookingDisplay(OvenCookingRecipe recipe) {
        this.input = EntryIngredients.ofIngredient(recipe.getInput());
        this.outputs = Collections.singletonList(EntryIngredients.ofItemStacks(recipe.getOutputs()));
        this.time = recipe.getTime();
        this.experience = recipe.getXp();
    }

    public static void register(DisplayRegistry registry) {
        registry.registerFiller(OvenCookingRecipe.class, OvenCookingDisplay::new);
    }

    @Override
    public List<EntryIngredient> getInputEntries() {
        return List.of(input);
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return this.outputs;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return WKREIPlugin.OVEN_COOKING;
    }

    public float getExperience() {
        return experience;
    }

    public int getTime() {
        return time;
    }
}
