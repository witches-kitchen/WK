package cf.witcheskitchen.common.recipe;

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

public record OvenCookingRecipe(Ingredient input, List<ItemStack> outputs, int time,
                                float xp) implements Recipe<SingleRecipeInput> {

    @Override
    public boolean matches(SingleRecipeInput inventory, Level world) {
        return input.test(inventory.getItem(0));
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider lookup) {
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<? extends Recipe<SingleRecipeInput>> getSerializer() {
        return WKRecipeTypes.WITCHES_OVEN_COOKING_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<? extends Recipe<SingleRecipeInput>> getType() {
        return WKRecipeTypes.WITCHES_OVEN_COOKING_RECIPE_TYPE;
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.create(this.input());
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        // TODO: use recipe book category
        return null;
    }

    public static class Serializer implements RecipeSerializer<OvenCookingRecipe> {
        @Override
        public MapCodec<OvenCookingRecipe> codec() {
            return RecordCodecBuilder
                .mapCodec(instance ->
                    instance.group(
                            Ingredient.CODEC
                                .fieldOf("ingredient")
                                .forGetter(OvenCookingRecipe::input),
                            ItemStack.CODEC
                                .listOf()
                                .fieldOf("results")
                                .validate(outputs -> {
                                    if (outputs.isEmpty()) {
                                        return DataResult.error(() -> "No output for Witches' Oven recipe");
                                    } else if (outputs.size() > 2) {
                                        return DataResult.error(() -> "Too many outputs for Witches' Oven recipe");
                                    }

                                    return DataResult.success(outputs);
                                })
                                .forGetter(OvenCookingRecipe::outputs),
                            Codec.INT
                                .fieldOf("time")
                                .forGetter(OvenCookingRecipe::time),
                            Codec.FLOAT
                                .fieldOf("experience")
                                .forGetter(OvenCookingRecipe::xp)
                        )
                        .apply(instance, OvenCookingRecipe::new)
                );
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, OvenCookingRecipe> streamCodec() {
            return StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, OvenCookingRecipe::input,
                ByteBufCodecs.<RegistryFriendlyByteBuf, ItemStack>list().apply(ItemStack.STREAM_CODEC), OvenCookingRecipe::outputs,
                ByteBufCodecs.VAR_INT, OvenCookingRecipe::time,
                ByteBufCodecs.FLOAT, OvenCookingRecipe::xp,
                OvenCookingRecipe::new
            );
        }
    }
}
