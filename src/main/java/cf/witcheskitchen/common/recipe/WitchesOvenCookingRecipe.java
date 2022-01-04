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
    private final Ingredient jar;
    private final ItemStack output;
    private final ItemStack fume;
    private final int time;
    private final float xp;

    public WitchesOvenCookingRecipe(Identifier id, Ingredient input, Ingredient jar, ItemStack output, ItemStack fume, int time, float xp) {
        this.id = id;
        this.input = input;
        this.jar = jar;
        this.output = output;
        this.fume = fume;
        this.time = time;
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

    public int getTime() {
        return time;
    }

    public float getXp() {
        return xp;
    }

    public Ingredient getInput() {
        return input;
    }

    public Ingredient getJar() {
        return jar;
    }

    public ItemStack getFume() {
        return fume;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return null;
    }

    @Override
    public RecipeType<?> getType() {
        return WKRecipeTypes.WITCHES_OVEN_COOKING_RECIPE_TYPE;
    }

    public static class Serializer implements RecipeSerializer<WitchesOvenCookingRecipe> {

        @Override
        public WitchesOvenCookingRecipe read(Identifier id, JsonObject json) {
            final Ingredient input = Ingredient.fromJson(JsonHelper.getObject(json, "input"));
            final Ingredient jar  = Ingredient.fromJson(JsonHelper.getObject(json, "jar"));
            final ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "output"));
            final ItemStack fume = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "fume"));
            final int time = JsonHelper.getInt(json, "time");
            final float xp = JsonHelper.getFloat(json, "experience");
            return new WitchesOvenCookingRecipe(id, input, jar, output, fume, time, xp);
        }

        @Override
        public WitchesOvenCookingRecipe read(Identifier id, PacketByteBuf buf) {
            final Ingredient input = Ingredient.fromPacket(buf);
            final Ingredient jar = Ingredient.fromPacket(buf);
            final ItemStack output = buf.readItemStack();
            final ItemStack fume = buf.readItemStack();
            final int time = buf.readInt();
            final float xp = buf.readFloat();
            return new WitchesOvenCookingRecipe(id, input, jar, output, fume, time, xp);
        }

        @Override
        public void write(PacketByteBuf buf, WitchesOvenCookingRecipe recipe) {
            recipe.getInput().write(buf);
            recipe.getJar().write(buf);
            buf.writeItemStack(recipe.getOutput());
            buf.writeItemStack(recipe.getFume());
            buf.writeInt(recipe.getTime());
            buf.writeFloat(recipe.getXp());
        }
    }
}
