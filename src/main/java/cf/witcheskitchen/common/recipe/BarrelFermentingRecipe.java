package cf.witcheskitchen.common.recipe;

import cf.witcheskitchen.common.registry.WKRecipeTypes;
import cf.witcheskitchen.common.util.RecipeUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class BarrelFermentingRecipe implements Recipe<Inventory> {

    private final Identifier id;
    private final DefaultedList<Ingredient> inputs;
    private final ItemStack output;

    public BarrelFermentingRecipe(Identifier id, DefaultedList<Ingredient> inputs, ItemStack output) {
        this.id = id;
        this.inputs = inputs;
        this.output = output;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return RecipeUtil.matches(inventory, this.inputs);
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        return this.output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getOutput() {
        return this.output;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    public DefaultedList<Ingredient> getInputs() {
        return inputs;
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
            final DefaultedList<Ingredient> inputs = RecipeUtil.deserializeIngredients(JsonHelper.getArray(json, "ingredients"));
            if (inputs.isEmpty()) {
                throw new JsonParseException("No ingredients for fermenting recipe");
            } else if (inputs.size() > 6) {
                throw new JsonParseException("Too many ingredients for fermenting recipe");
            } else {
                final ItemStack output = RecipeUtil.deserializeStack(JsonHelper.getObject(json, "result"));
                if (output.isEmpty()) {
                    throw new JsonParseException("No output for fermenting recipe");
                } else {
                    return new BarrelFermentingRecipe(id, inputs, output);
                }
            }
        }

        @Override
        public BarrelFermentingRecipe read(Identifier id, PacketByteBuf buf) {
            final DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readVarInt(), Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromPacket(buf));
            }
            return new BarrelFermentingRecipe(id, inputs, buf.readItemStack());
        }

        @Override
        public void write(PacketByteBuf buf, BarrelFermentingRecipe recipe) {
            buf.writeVarInt(recipe.getInputs().size());
            for (var ingredient : recipe.getInputs()) {
                ingredient.write(buf);
            }
            buf.writeItemStack(recipe.getOutput());
        }
    }
}
