package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.WKConfig;
import cf.witcheskitchen.common.recipe.BarrelFermentingRecipe;
import cf.witcheskitchen.common.recipe.CauldronBrewingRecipe;
import cf.witcheskitchen.common.recipe.OvenCookingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class WKRecipeTypes {

    private static final Map<RecipeSerializer<?>, Identifier> RECIPE_SERIALIZERS = new HashMap<>();
    public static final RecipeSerializer<OvenCookingRecipe> WITCHES_OVEN_COOKING_RECIPE_SERIALIZER = register("oven_cooking", new OvenCookingRecipe.Serializer());
    public static final RecipeSerializer<BarrelFermentingRecipe> BARREL_FERMENTING_RECIPE_SERIALIZER = register("fermenting", new BarrelFermentingRecipe.Serializer());
    public static final RecipeSerializer<CauldronBrewingRecipe> CAULDRON_BREWING_RECIPE_SERIALIZER = register("cauldron_brewing", new CauldronBrewingRecipe.Serializer());
    private static final Map<RecipeType<?>, Identifier> RECIPE_TYPES = new HashMap<>();
    public static final RecipeType<OvenCookingRecipe> WITCHES_OVEN_COOKING_RECIPE_TYPE = register("oven_cooking");
    public static final RecipeType<BarrelFermentingRecipe> BARREL_FERMENTING_RECIPE_TYPE = register("fermenting");
    public static final RecipeType<CauldronBrewingRecipe> CAULDRON_BREWING_RECIPE_TYPE = register("cauldron_brewing");

    private static <T extends Recipe<?>> RecipeSerializer<T> register(String name, RecipeSerializer<T> serializer) {
        RECIPE_SERIALIZERS.put(serializer, new Identifier(WK.MODID, name));
        return serializer;
    }

    private static <T extends Recipe<?>> RecipeType<T> register(String name) {
        RecipeType<T> type = new RecipeType<>() {
            @Override
            public String toString() {
                return name;
            }
        };
        RECIPE_TYPES.put(type, new Identifier(WK.MODID, name));
        return type;
    }

    public static void register() {
        RECIPE_SERIALIZERS.keySet().forEach(recipeSerializer -> Registry.register(Registry.RECIPE_SERIALIZER, RECIPE_SERIALIZERS.get(recipeSerializer), recipeSerializer));
        RECIPE_TYPES.keySet().forEach(recipeType -> Registry.register(Registry.RECIPE_TYPE, RECIPE_TYPES.get(recipeType), recipeType));

        if (WKConfig.get().debugMode) {
            WK.logger.info("Witches Kitchen Base Custom Recipes: Successfully Loaded");
        }
    }
}
