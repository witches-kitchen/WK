package cf.witcheskitchen.common.recipe;

import cf.witcheskitchen.common.registry.WKRecipeTypes;
import com.google.gson.JsonObject;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

public class WitchesOvenCookingRecipe implements Recipe<Inventory> {

    private final Identifier id;
    private final Ingredient input;
    private final ItemStack output;
    private final ItemStack extra;
    private final float xp;

    public WitchesOvenCookingRecipe(Identifier id, Ingredient input, ItemStack output, ItemStack extra, float xp) {
        this.id = id;
        this.input = input;
        this.output = output;
        this.extra = extra;
        this.xp = xp;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return false;
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

    public float getXp() {
        return xp;
    }

    public Ingredient getInput() {
        return input;
    }

    public ItemStack getExtra() {
        return extra;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return WKRecipeTypes.WITCHES_OVEN_COOKING_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return WKRecipeTypes.WITCHES_OVEN_COOKING_RECIPE_TYPE;
    }

    public static class Serializer implements RecipeSerializer<WitchesOvenCookingRecipe> {

        @Override
        public WitchesOvenCookingRecipe read(Identifier id, JsonObject json) {
            final Ingredient input = Ingredient.fromJson(JsonHelper.getObject(json, "input"));
            final ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "output"));
            final ItemStack extra = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "extra"));
            final float xp = JsonHelper.getFloat(json, "experience");
            return new WitchesOvenCookingRecipe(id, input, output,  extra, xp);
        }

        @Override
        public WitchesOvenCookingRecipe read(Identifier id, PacketByteBuf buf) {
            final Ingredient input = Ingredient.fromPacket(buf);
            final ItemStack output = buf.readItemStack();
            final ItemStack extra = buf.readItemStack();
            final float xp = buf.readFloat();
            return new WitchesOvenCookingRecipe(id, input, output, extra, xp);
        }

        @Override
        public void write(PacketByteBuf buf, WitchesOvenCookingRecipe recipe) {
            recipe.getInput().write(buf);
            buf.writeItemStack(recipe.getOutput());
            buf.writeItemStack(recipe.getExtra());
            buf.writeFloat(recipe.getXp());
        }
    }
}
