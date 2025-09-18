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

public record TeaRecipe(Ingredient input, ItemStack output, MobEffect effect) implements Recipe<SingleRecipeInput> {

    @Override
    public boolean matches(SingleRecipeInput inventory, Level world) {
        return false;
    }

    @Override
    public ItemStack assemble(SingleRecipeInput input, HolderLookup.Provider lookup) {
        return ItemStack.EMPTY;
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
                            .forGetter(TeaRecipe::input),
                        ItemStack.CODEC
                            .fieldOf("result")
                            .forGetter(TeaRecipe::output),
                        BuiltInRegistries.MOB_EFFECT.byNameCodec()
                            .fieldOf("effect")
                            .forGetter(TeaRecipe::effect)
                    )
                    .apply(instance, TeaRecipe::new)
            );
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, TeaRecipe> streamCodec() {
            return StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, TeaRecipe::input,
                ItemStack.STREAM_CODEC, TeaRecipe::output,
                ByteBufCodecs.registry(Registries.MOB_EFFECT), TeaRecipe::effect,
                TeaRecipe::new
            );
        }
    }
}
