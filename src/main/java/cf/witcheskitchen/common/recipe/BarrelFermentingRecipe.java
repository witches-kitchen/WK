package cf.witcheskitchen.common.recipe;

import cf.witcheskitchen.common.registry.WKRecipeTypes;
import com.google.gson.JsonObject;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class BarrelFermentingRecipe implements Recipe<Inventory> {

    private final Identifier id;
    private final DefaultedList<Ingredient> inputs;
    private final DefaultedList<ItemStack> outputs;

    public BarrelFermentingRecipe(Identifier id, DefaultedList<Ingredient> inputs, DefaultedList<ItemStack> outputs) {
        this.id = id;
        this.inputs = inputs;
        this.outputs = outputs;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return false;
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    public DefaultedList<Ingredient> getInputs() {
        return inputs;
    }

    public DefaultedList<ItemStack> getOutputs() {
        return outputs;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return WKRecipeTypes.BARREL_FERMENTING_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return WKRecipeTypes.BARREL_FERMENTING_RECIPE_TYPE;
    }

    public static class Serializer implements RecipeSerializer<BarrelFermentingRecipe> {


        @Override
        public BarrelFermentingRecipe read(Identifier id, JsonObject json) {
            return null;
        }

        @Override
        public BarrelFermentingRecipe read(Identifier id, PacketByteBuf buf) {
            return null;
        }

        @Override
        public void write(PacketByteBuf buf, BarrelFermentingRecipe recipe) {

        }
    }
}
