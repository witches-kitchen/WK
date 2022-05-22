package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.WKConfig;
import cf.witcheskitchen.WKIdentifier;
import cf.witcheskitchen.api.registry.ObjectDefinition;
import cf.witcheskitchen.common.recipe.BarrelFermentingRecipe;
import cf.witcheskitchen.common.recipe.CauldronBrewingRecipe;
import cf.witcheskitchen.common.recipe.OvenCookingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.*;

public class WKRecipeTypes {


    private static final List<ObjectDefinition<RecipeSerializer<?>>> RECIPE_SERIALIZERS = new ArrayList<>();
    private static final List<ObjectDefinition<RecipeType<?>>> RECIPE_TYPES = new ArrayList<>();

    public static final RecipeSerializer<OvenCookingRecipe> WITCHES_OVEN_COOKING_RECIPE_SERIALIZER = register("oven_cooking", new OvenCookingRecipe.Serializer());
    public static final RecipeSerializer<BarrelFermentingRecipe> BARREL_FERMENTING_RECIPE_SERIALIZER = register("fermenting", new BarrelFermentingRecipe.Serializer());
    public static final RecipeSerializer<CauldronBrewingRecipe> CAULDRON_BREWING_RECIPE_SERIALIZER = register("cauldron_brewing", new CauldronBrewingRecipe.Serializer());
    public static final RecipeType<OvenCookingRecipe> WITCHES_OVEN_COOKING_RECIPE_TYPE = register("oven_cooking");
    public static final RecipeType<BarrelFermentingRecipe> BARREL_FERMENTING_RECIPE_TYPE = register("fermenting");
    public static final RecipeType<CauldronBrewingRecipe> CAULDRON_BREWING_RECIPE_TYPE = register("cauldron_brewing");

    private static <T extends Recipe<?>> RecipeSerializer<T> register(String name, RecipeSerializer<T> serializer) {
        final Identifier id = new WKIdentifier(name);
        final ObjectDefinition<RecipeSerializer<?>> definition = new ObjectDefinition<>(id, serializer);
        RECIPE_SERIALIZERS.add(definition);
        return serializer;
    }

    private static <T extends Recipe<?>> RecipeType<T> register(String name) {
        final Identifier id = new WKIdentifier(name);
        final RecipeType<T> type = new RecipeType<>() {
            @Override
            public String toString() {
                return name;
            }
        };
        final ObjectDefinition<RecipeType<?>> definition = new ObjectDefinition<>(id, type);
        RECIPE_TYPES.add(definition);
        return type;
    }

    public static List<ObjectDefinition<RecipeSerializer<?>>> getSerializers() {
        return Collections.unmodifiableList(RECIPE_SERIALIZERS);
    }
    public static List<ObjectDefinition<RecipeType<?>>> getTypes() {
        return Collections.unmodifiableList(RECIPE_TYPES);
    }
    public static void register() {
        for (ObjectDefinition<RecipeSerializer<?>> entry : RECIPE_SERIALIZERS) {
            Registry.register(Registry.RECIPE_SERIALIZER, entry.id(), entry.object());
        }
        for (ObjectDefinition<RecipeType<?>> entry : RECIPE_TYPES) {
            Registry.register(Registry.RECIPE_TYPE, entry.id(), entry.object());
        }
        if (WKConfig.get().debugMode) {
            WK.logger.info("Witches Kitchen Base Custom Recipes: Successfully Loaded");
        }
    }
}
