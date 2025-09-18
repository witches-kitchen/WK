package cf.witcheskitchen.client.integration.rei.display;

import cf.witcheskitchen.client.integration.rei.WKREIPlugin;
import cf.witcheskitchen.common.recipe.BarrelFermentingRecipe;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Environment(EnvType.CLIENT)
public class FermentingDisplay implements Display {

    private final List<EntryIngredient> inputs;
    private final List<EntryIngredient> output;

    public FermentingDisplay(BarrelFermentingRecipe recipe) {
        this.inputs = EntryIngredients.ofIngredients(recipe.inputs());
        this.output = Collections.singletonList(EntryIngredients.of(recipe.output()));
    }

    public static void register(DisplayRegistry registry) {
        registry.beginFiller(BarrelFermentingRecipe.class)
            .fill(FermentingDisplay::new);
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

    // TODO: impl
    @Override
    public Optional<ResourceLocation> getDisplayLocation() {
        return Optional.empty();
    }

    @Override
    public @Nullable DisplaySerializer<? extends Display> getSerializer() {
        return null;
    }
}
