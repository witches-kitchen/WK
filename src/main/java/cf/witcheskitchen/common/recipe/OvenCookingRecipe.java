package cf.witcheskitchen.common.recipe;

import cf.witcheskitchen.common.registry.WKRecipeTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

import java.util.List;

public record OvenCookingRecipe(Ingredient input, List<ItemStack> outputs, int time,
                                float xp) implements Recipe<SingleStackRecipeInput> {

    @Override
    public boolean matches(SingleStackRecipeInput inventory, World world) {
        return input.test(inventory.getStackInSlot(0));
    }

    @Override
    public ItemStack craft(SingleStackRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<? extends Recipe<SingleStackRecipeInput>> getSerializer() {
        return WKRecipeTypes.WITCHES_OVEN_COOKING_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<? extends Recipe<SingleStackRecipeInput>> getType() {
        return WKRecipeTypes.WITCHES_OVEN_COOKING_RECIPE_TYPE;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        return IngredientPlacement.forSingleSlot(this.input());
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
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
        public PacketCodec<RegistryByteBuf, OvenCookingRecipe> packetCodec() {
            return PacketCodec.tuple(
                    Ingredient.PACKET_CODEC, OvenCookingRecipe::input,
                    PacketCodecs.<RegistryByteBuf, ItemStack>toList().apply(ItemStack.PACKET_CODEC), OvenCookingRecipe::outputs,
                    PacketCodecs.VAR_INT, OvenCookingRecipe::time,
                    PacketCodecs.FLOAT, OvenCookingRecipe::xp,
                    OvenCookingRecipe::new
            );
        }
    }
}
