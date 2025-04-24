package cf.witcheskitchen.common.recipe;

import cf.witcheskitchen.common.registry.WKRecipeTypes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;

public class TeaRecipe implements Recipe<SingleStackRecipeInput> {
    public final Ingredient input;
    public final ItemStack output;
    public final StatusEffect effect;

    public TeaRecipe(Ingredient input, ItemStack output, StatusEffect effect) {
        this.input = input;
        this.effect = effect;
        this.output = output;
    }

    @Override
    public boolean matches(SingleStackRecipeInput inventory, World world) {
        return false;
    }

    @Override
    public ItemStack craft(SingleStackRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return ItemStack.EMPTY;
    }

    public Ingredient getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

    public StatusEffect getEffect() {
        return effect;
    }

    @Override
    public RecipeSerializer<? extends Recipe<SingleStackRecipeInput>> getSerializer() {
        return WKRecipeTypes.TEA_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<? extends Recipe<SingleStackRecipeInput>> getType() {
        return WKRecipeTypes.TEA_RECIPE_TYPE;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        return IngredientPlacement.forSingleSlot(this.input);
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
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
                                    Registries.STATUS_EFFECT.getCodec()
                                            .fieldOf("effect")
                                            .forGetter(TeaRecipe::getEffect)
                            )
                            .apply(instance, TeaRecipe::new)
            );
        }

        @Override
        public PacketCodec<RegistryByteBuf, TeaRecipe> packetCodec() {
            return PacketCodec.tuple(
                    Ingredient.PACKET_CODEC, TeaRecipe::getInput,
                    ItemStack.PACKET_CODEC, TeaRecipe::getOutput,
                    PacketCodecs.registryValue(RegistryKeys.STATUS_EFFECT), TeaRecipe::getEffect,
                    TeaRecipe::new
            );
        }
    }
}
