package cf.witcheskitchen.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;

import java.util.function.Consumer;

public class WKRecipeProvider extends FabricRecipeProvider {
    public WKRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateRecipes(Consumer<RecipeJsonProvider> exporter) {

    }
}
