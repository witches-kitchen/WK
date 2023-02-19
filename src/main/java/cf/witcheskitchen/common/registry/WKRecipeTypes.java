package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.WitchesKitchenConfig;
import cf.witcheskitchen.api.registry.ObjectDefinition;
import cf.witcheskitchen.common.recipe.BarrelFermentingRecipe;
import cf.witcheskitchen.common.recipe.CauldronBrewingRecipe;
import cf.witcheskitchen.common.recipe.OvenCookingRecipe;
import cf.witcheskitchen.common.recipe.RitualRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface WKRecipeTypes {
    List<ObjectDefinition<RecipeSerializer<?>>> RECIPE_SERIALIZERS = new ArrayList<>();
    List<ObjectDefinition<RecipeType<?>>> RECIPE_TYPES = new ArrayList<>();

    RecipeSerializer<OvenCookingRecipe> WITCHES_OVEN_COOKING_RECIPE_SERIALIZER = register("oven_cooking", new OvenCookingRecipe.Serializer());
    RecipeSerializer<BarrelFermentingRecipe> BARREL_FERMENTING_RECIPE_SERIALIZER = register("fermenting", new BarrelFermentingRecipe.Serializer());
    RecipeSerializer<CauldronBrewingRecipe> CAULDRON_BREWING_RECIPE_SERIALIZER = register("cauldron_brewing", new CauldronBrewingRecipe.Serializer());
    RecipeSerializer<RitualRecipe> RITUAL_RECIPE_SERIALIZER = register("ritual", new RitualRecipe.Serializer());

    RecipeType<OvenCookingRecipe> WITCHES_OVEN_COOKING_RECIPE_TYPE = register("oven_cooking");
    RecipeType<BarrelFermentingRecipe> BARREL_FERMENTING_RECIPE_TYPE = register("fermenting");
    RecipeType<CauldronBrewingRecipe> CAULDRON_BREWING_RECIPE_TYPE = register("cauldron_brewing");
    RecipeType<RitualRecipe> RITUAL_RECIPE_TYPE = register("ritual");

    static <T extends Recipe<?>> RecipeSerializer<T> register(String name, RecipeSerializer<T> serializer) {
        final Identifier id = WitchesKitchen.id(name);
        final ObjectDefinition<RecipeSerializer<?>> definition = new ObjectDefinition<>(id, serializer);
        RECIPE_SERIALIZERS.add(definition);
        return serializer;
    }

    static <T extends Recipe<?>> RecipeType<T> register(String name) {
        final Identifier id = WitchesKitchen.id(name);
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

    static List<ObjectDefinition<RecipeSerializer<?>>> getSerializers() {
        return Collections.unmodifiableList(RECIPE_SERIALIZERS);
    }

    static List<ObjectDefinition<RecipeType<?>>> getTypes() {
        return Collections.unmodifiableList(RECIPE_TYPES);
    }

    static void register() {
        RECIPE_SERIALIZERS.forEach(entry -> Registry.register(Registry.RECIPE_SERIALIZER, entry.id(), entry.object()));
        RECIPE_TYPES.forEach(entry -> Registry.register(Registry.RECIPE_TYPE, entry.id(), entry.object()));
    }
}
