package cf.witcheskitchen.common.recipe;

import cf.witcheskitchen.api.ritual.RitualCircle;
import cf.witcheskitchen.common.registry.WKRecipeTypes;
import cf.witcheskitchen.api.util.RecipeUtils;
import com.google.gson.JsonObject;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RitualRecipe implements Recipe<Inventory> {
    public final Identifier id;
    public final Set<RitualCircle> circleSet;
    public final DefaultedList<Ingredient> inputs;
    public final List<ItemStack> outputs;

    public RitualRecipe(Identifier id, Set<RitualCircle> circleSet, DefaultedList<Ingredient> inputs, List<ItemStack> outputs) {
        this.id = id;
        this.circleSet = circleSet;
        this.outputs = outputs;
        this.inputs = inputs;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return false;
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        return null;
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
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return WKRecipeTypes.RITUAL_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<?> getType() {
        return WKRecipeTypes.RITUAL_RECIPE_TYPE;
    }



    public static class Serializer implements RecipeSerializer<RitualRecipe> {

        @Override
        public RitualRecipe read(Identifier id, JsonObject json) {
            //Inputs
            DefaultedList<Ingredient> inputs = RecipeUtils.getIngredients(JsonHelper.getArray(json, "inputs"));

            //Outputs
            DefaultedList<ItemStack> outputs = RecipeUtils.deserializeStacks(JsonHelper.getArray(json, "outputs"));

            //Circles
            var circleArray = JsonHelper.getArray(json, "circles");
            Set<RitualCircle> circles = RecipeUtils.deserializeCircles(circleArray);

            return new RitualRecipe(id, circles, inputs, outputs);
        }

        @Override
        public RitualRecipe read(Identifier id, PacketByteBuf buf) {
            //Inputs
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readVarInt(), Ingredient.EMPTY);
            inputs.replaceAll(ignored -> Ingredient.fromPacket(buf));

            //Outputs
            DefaultedList<ItemStack> outputs = DefaultedList.ofSize(buf.readVarInt(), ItemStack.EMPTY);
            outputs.replaceAll(ignored -> buf.readItemStack());

            //Circles
            List<RitualCircle> list = new ArrayList<>();
            for (int i = 0; i < buf.readInt(); i++) {
                var size = RitualCircle.getSize(buf.readString());
                var type = RitualCircle.getType(buf.readString());
                RitualCircle ritualCircle = new RitualCircle(size, type);
                list.set(i, ritualCircle);
            }
            Set<RitualCircle> circles = new HashSet<>(list);


            return new RitualRecipe(id, circles, inputs, outputs);
        }

        @Override
        public void write(PacketByteBuf buf, RitualRecipe recipe) {
            //Inputs
            buf.writeVarInt(recipe.inputs.size());
            for (Ingredient ingredient : recipe.inputs) {
                ingredient.write(buf);
            }
            //Outputs
            buf.writeVarInt(recipe.outputs.size());
            for (ItemStack stack : recipe.outputs) {
                buf.writeItemStack(stack);
            }
            //Circles
            buf.writeVarInt(recipe.circleSet.size());
            for(RitualCircle circle : recipe.circleSet){
                buf.writeString(circle.getSize());
                buf.writeString(circle.getType());
            }
        }
    }
}
