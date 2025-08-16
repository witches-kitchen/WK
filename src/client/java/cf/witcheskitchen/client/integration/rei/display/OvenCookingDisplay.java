package cf.witcheskitchen.client.integration.rei.display;

import cf.witcheskitchen.client.integration.rei.WKREIPlugin;
import cf.witcheskitchen.common.recipe.OvenCookingRecipe;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Environment(EnvType.CLIENT)
public class OvenCookingDisplay implements Display {

    private final EntryIngredient input;
    private final List<EntryIngredient> outputs;

    private final int time;
    private final float experience;

    public OvenCookingDisplay(OvenCookingRecipe recipe) {
        this.input = EntryIngredients.ofIngredient(recipe.input());
        this.outputs = Collections.singletonList(EntryIngredients.ofItemStacks(recipe.outputs()));
        this.time = recipe.time();
        this.experience = recipe.xp();
    }

    public static void register(DisplayRegistry registry) {
        registry.beginFiller(OvenCookingRecipe.class)
            .fill(OvenCookingDisplay::new);
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

    // TODO: impl
    @Override
    public Optional<Identifier> getDisplayLocation() {
        return Optional.empty();
    }

    @Override
    public @Nullable DisplaySerializer<? extends Display> getSerializer() {
        return null;
    }

    public float getExperience() {
        return experience;
    }

    public int getTime() {
        return time;
    }
}
