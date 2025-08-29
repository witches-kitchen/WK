package cf.witcheskitchen.common.recipe;

import cf.witcheskitchen.common.registry.WKRecipeTypes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public class TeaRecipe implements Recipe<SingleRecipeInput> {
    public final Ingredient input;
    public final ItemStack output;
    public final MobEffect effect;

    public TeaRecipe(Ingredient input, ItemStack output, MobEffect effect) {
        this.input = input;
        this.effect = effect;
        this.output = output;
    }

    @Override
    public boolean matches(SingleRecipeInput inventory, Level world) {
        return false;
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider lookup) {
        return ItemStack.EMPTY;
    }

    public Ingredient getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

    public MobEffect getEffect() {
        return effect;
    }

    @Override
    public RecipeSerializer<? extends Recipe<SingleRecipeInput>> getSerializer() {
        return WKRecipeTypes.TEA_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<? extends Recipe<SingleRecipeInput>> getType() {
        return WKRecipeTypes.TEA_RECIPE_TYPE;
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.create(this.input);
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        // TODO: use custom recipe book category
        return null;
    }

    public static class Serializer implements RecipeSerializer<TeaRecipe> {
        @Override
        public MapCodec<TeaRecipe> codec() {
            return RecordCodecBuilder.mapCodec(instance ->
                    instance.group(
                                    Ingredient.CODEC
                                            .fieldOf("ingredient")
                                            .forGetter(TeaRecipe::getInput),
                                    ItemStack.CODEC
                                            .fieldOf("result")
                                            .forGetter(TeaRecipe::getOutput),
                                    BuiltInRegistries.MOB_EFFECT.byNameCodec()
                                            .fieldOf("effect")
                                            .forGetter(TeaRecipe::getEffect)
                            )
                            .apply(instance, TeaRecipe::new)
            );
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, TeaRecipe> streamCodec() {
            return StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC, TeaRecipe::getInput,
                    ItemStack.STREAM_CODEC, TeaRecipe::getOutput,
                    ByteBufCodecs.registry(Registries.MOB_EFFECT), TeaRecipe::getEffect,
                    TeaRecipe::new
            );
        }
    }
}
