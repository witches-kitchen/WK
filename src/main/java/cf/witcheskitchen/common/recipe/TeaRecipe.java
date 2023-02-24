package cf.witcheskitchen.common.recipe;

import cf.witcheskitchen.common.registry.WKRecipeTypes;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;
import org.quiltmc.qsl.recipe.api.serializer.QuiltRecipeSerializer;

public class TeaRecipe implements Recipe<Inventory> {
    public final Identifier id;
    public final Ingredient input;
    public final ItemStack output;
    public final StatusEffect effect;

    public TeaRecipe(Identifier id, Ingredient input, ItemStack output, StatusEffect effect) {
        this.id = id;
        this.input = input;
        this.effect = effect;
        this.output = output;
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
        return true;
    }

    public Ingredient getInput() {
        return input;
    }

    @Override
    public ItemStack getOutput() {
        return output;
    }

    public StatusEffect getEffect() {
        return effect;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return WKRecipeTypes.TEA_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return WKRecipeTypes.TEA_RECIPE_TYPE;
    }

    public static class Serializer implements QuiltRecipeSerializer<TeaRecipe> {

        @Override
        public TeaRecipe read(Identifier id, JsonObject json) {
            Ingredient input = Ingredient.fromJson(JsonHelper.getObject(json, "ingredient"));
            StatusEffect effect = Registries.STATUS_EFFECT.get(new Identifier(JsonHelper.getString(json, "effect")));
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result"));
            return new TeaRecipe(id, input, output, effect);
        }

        @Override
        public TeaRecipe read(Identifier id, PacketByteBuf buf) {
            Ingredient input = Ingredient.fromPacket(buf);
            StatusEffect effect = Registries.STATUS_EFFECT.get(new Identifier(buf.readString()));
            ItemStack output = buf.readItemStack();
            return new TeaRecipe(id, input, output, effect);
        }

        @Override
        public void write(PacketByteBuf buf, TeaRecipe recipe) {
            recipe.getInput().write(buf);
            buf.writeItemStack(recipe.getOutput());
            buf.writeString(Registries.STATUS_EFFECT.getId(recipe.getEffect()).toString());
        }

        @Override
        public JsonObject toJson(TeaRecipe recipe) {
            JsonObject obj = new JsonObject();
            obj.add("type", new JsonPrimitive(recipe.getId().toString()));
            obj.add("ingredient", recipe.getInput().toJson());
            obj.add("result", new JsonPrimitive(recipe.getOutput().toString()));
            obj.add("effect", new JsonPrimitive(recipe.getEffect().toString()));
            return obj;
        }
    }
}
