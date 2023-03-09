package cf.witcheskitchen.common.recipe;

import cf.witcheskitchen.api.util.RecipeUtils;
import cf.witcheskitchen.common.registry.WKRecipeTypes;
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
import org.quiltmc.qsl.recipe.api.serializer.QuiltRecipeSerializer;

public class CauldronBrewingRecipe implements Recipe<Inventory> {

    private final Identifier id;
    private final DefaultedList<Ingredient> ingredients;
    private final ItemStack result;
    private final int color;

    public CauldronBrewingRecipe(Identifier id, DefaultedList<Ingredient> ingredients, ItemStack result, int color) {
        this.id = id;
        this.ingredients = ingredients;
        this.result = result;
        this.color = color;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return RecipeUtils.matches(inventory, this.ingredients, 0, inventory.size());
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        return this.result.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getOutput() {
        return this.result;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return WKRecipeTypes.CAULDRON_BREWING_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return WKRecipeTypes.CAULDRON_BREWING_RECIPE_TYPE;
    }

    public int getColor() {
        return color;
    }


    public static class Serializer implements QuiltRecipeSerializer<CauldronBrewingRecipe> {

        @Override
        public CauldronBrewingRecipe read(Identifier id, JsonObject json) {
            final DefaultedList<Ingredient> ingredients = RecipeUtils.deserializeIngredients(JsonHelper.getArray(json, "ingredients"));
            if (ingredients.size() < 2) {
                throw new JsonParseException("Cauldron recipes must have at least 2 ingredients");
            } else if (ingredients.size() > 7) {
                throw new JsonParseException("Too many ingredients for Cauldron recipe");
            } else {
                return new CauldronBrewingRecipe(id, ingredients, RecipeUtils.deserializeStack(JsonHelper.getObject(json, "result")), JsonHelper.getInt(json, "color"));
            }
        }

        @Override
        public CauldronBrewingRecipe read(Identifier id, PacketByteBuf buf) {
            final DefaultedList<Ingredient> ingredients = DefaultedList.ofSize(buf.readVarInt(), Ingredient.EMPTY);
            ingredients.replaceAll(ignored -> Ingredient.fromPacket(buf));
            return new CauldronBrewingRecipe(id, ingredients, buf.readItemStack(), buf.readInt());
        }

        @Override
        public void write(PacketByteBuf buf, CauldronBrewingRecipe recipe) {
            buf.writeVarInt(recipe.getIngredients().size());
            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.write(buf);
            }
            buf.writeItemStack(recipe.getOutput());
            buf.writeInt(recipe.color);
        }

        @Override
        public JsonObject toJson(CauldronBrewingRecipe recipe) {
            return null;
        }
    }
}