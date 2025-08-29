package cf.witcheskitchen.common.recipe;

import cf.witcheskitchen.api.event.network.CustomPacketCodecs;
import cf.witcheskitchen.api.util.RecipeUtils;
import cf.witcheskitchen.common.registry.WKRecipeTypes;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import java.util.List;

public class BarrelFermentingRecipe implements Recipe<MultipleStackRecipeInput> {

    private final List<Ingredient> inputs;
    private final ItemStack output;

    public BarrelFermentingRecipe(List<Ingredient> inputs, ItemStack output) {
        this.inputs = inputs;
        this.output = output;
    }

    @Override
    public boolean matches(MultipleStackRecipeInput inventory, Level world) {
        return RecipeUtils.matches(inventory, this.inputs, 0, 5);
    }

    @Override
    public ItemStack assemble(MultipleStackRecipeInput input, HolderLookup.Provider lookup) {
        return this.output.copy();
    }

    public List<Ingredient> getInputs() {
        return inputs;
    }

    public ItemStack getOutput() {
        return output;
    }

    @Override
    public RecipeSerializer<? extends Recipe<MultipleStackRecipeInput>> getSerializer() {
        return WKRecipeTypes.BARREL_FERMENTING_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<? extends Recipe<MultipleStackRecipeInput>> getType() {
        return WKRecipeTypes.BARREL_FERMENTING_RECIPE_TYPE;
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.create(this.getInputs());
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        // TODO: create custom recipe book category
        return null;
    }

    public static class Serializer implements RecipeSerializer<BarrelFermentingRecipe> {
        @Override
        public MapCodec<BarrelFermentingRecipe> codec() {
            return RecordCodecBuilder.mapCodec(instance ->
                    instance.group(
                                    Ingredient.CODEC.listOf()
                                            .fieldOf("ingredients")
                                            .validate(inputs -> {
                                                if (inputs.isEmpty()) {
                                                    return DataResult.error(() -> "No ingredients for fermenting recipe");
                                                } else if (inputs.size() > 6) {
                                                    return DataResult.error(() -> "Too many ingredients for fermenting recipe");
                                                }

                                                return DataResult.success(inputs);
                                            })
                                            .forGetter(BarrelFermentingRecipe::getInputs),
                                    ItemStack.CODEC
                                            .fieldOf("result")
                                            .validate(output -> {
                                                if (output.isEmpty()) {
                                                    return DataResult.error(() -> "No output for fermenting recipe");
                                                }

                                                return DataResult.success(output);
                                            })
                                            .forGetter(BarrelFermentingRecipe::getOutput)
                            )
                            .apply(instance, BarrelFermentingRecipe::new)
            );
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, BarrelFermentingRecipe> streamCodec() {
            return StreamCodec.composite(
                    CustomPacketCodecs.INGREDIENT_LIST, BarrelFermentingRecipe::getInputs,
                    ItemStack.STREAM_CODEC, BarrelFermentingRecipe::getOutput,

                    BarrelFermentingRecipe::new
            );
        }
    }
}