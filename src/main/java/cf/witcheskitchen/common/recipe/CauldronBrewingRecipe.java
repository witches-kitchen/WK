package cf.witcheskitchen.common.recipe;

import cf.witcheskitchen.api.event.network.CustomPacketCodecs;
import cf.witcheskitchen.api.util.RecipeUtils;
import cf.witcheskitchen.common.registry.WKRecipeTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.List;

public class CauldronBrewingRecipe implements Recipe<MultipleStackRecipeInput> {

    private final List<Ingredient> ingredients;
    private final ItemStack result;
    private final int color;

    public CauldronBrewingRecipe(List<Ingredient> ingredients, ItemStack result, int color) {
        this.ingredients = ingredients;
        this.result = result;
        this.color = color;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public boolean matches(MultipleStackRecipeInput inventory, Level world) {
        return RecipeUtils.matches(inventory, this.ingredients, 0, inventory.size());
    }

    @Override
    public ItemStack assemble(MultipleStackRecipeInput input, HolderLookup.Provider lookup) {
        return this.result.copy();
    }

    public ItemStack getResult() {
        return this.result;
    }

    public List<Ingredient> getInputs() {
        return this.ingredients;
    }

    @Override
    public RecipeSerializer<? extends Recipe<MultipleStackRecipeInput>> getSerializer() {
        return WKRecipeTypes.CAULDRON_BREWING_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<? extends Recipe<MultipleStackRecipeInput>> getType() {
        return WKRecipeTypes.CAULDRON_BREWING_RECIPE_TYPE;
    }

    public int getColor() {
        return color;
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.create(this.getInputs());
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        // TODO: use custom recipe book category
        return null;
    }

    public static class Serializer implements RecipeSerializer<CauldronBrewingRecipe> {
        @Override
        public MapCodec<CauldronBrewingRecipe> codec() {
            return RecordCodecBuilder.mapCodec(instance ->
                    instance.group(
                                    Ingredient.CODEC.listOf()
                                            .fieldOf("ingredients")
                                            .validate(ingredients -> {
                                                if (ingredients.size() < 2) {
                                                    return DataResult.error(() -> "Cauldron recipes must have at least 2 ingredients");
                                                } else if (ingredients.size() > 7) {
                                                    return DataResult.error(() -> "Too many ingredients for Cauldron recipe");
                                                }

                                                return DataResult.success(ingredients);
                                            })
                                            .forGetter(CauldronBrewingRecipe::getInputs),
                                    ItemStack.CODEC
                                            .fieldOf("result")
                                            .forGetter(CauldronBrewingRecipe::getResult),
                                    Codec.INT
                                            .fieldOf("color")
                                            .forGetter(CauldronBrewingRecipe::getColor)
                            )
                            .apply(instance, CauldronBrewingRecipe::new)
            );
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CauldronBrewingRecipe> streamCodec() {
            return StreamCodec.composite(
                    CustomPacketCodecs.INGREDIENT_LIST, CauldronBrewingRecipe::getInputs,
                    ItemStack.STREAM_CODEC, CauldronBrewingRecipe::getResult,
                    ByteBufCodecs.VAR_INT, CauldronBrewingRecipe::getColor,
                    CauldronBrewingRecipe::new
            );
        }
    }
}