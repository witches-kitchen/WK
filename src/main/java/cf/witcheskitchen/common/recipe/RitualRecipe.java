package cf.witcheskitchen.common.recipe;

import cf.witcheskitchen.api.ritual.RitualCircle;
import cf.witcheskitchen.common.registry.WKRecipeTypes;
import cf.witcheskitchen.api.util.RecipeUtils;
import com.google.gson.JsonObject;
import net.minecraft.entity.EntityType;
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
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.recipe.api.serializer.QuiltRecipeSerializer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RitualRecipe implements Recipe<Inventory> {
    public final Identifier id;
    public final Set<RitualCircle> circleSet;
    public final DefaultedList<Ingredient> inputs;
    public final List<ItemStack> outputs;
    public final List<EntityType<?>> sacrifices;

    public RitualRecipe(Identifier id, Set<RitualCircle> circleSet, @Nullable DefaultedList<Ingredient> inputs, @Nullable List<ItemStack> outputs, @Nullable List<EntityType<?>> sacrifices) {
        this.id = id;
        this.circleSet = circleSet;
        this.outputs = outputs;
        this.inputs = inputs;
        this.sacrifices = sacrifices;
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



    public static class Serializer implements QuiltRecipeSerializer<RitualRecipe> {

        @Override
        public RitualRecipe read(Identifier id, JsonObject json) {
            //Inputs
            DefaultedList<Ingredient> inputs = RecipeUtils.getIngredients(JsonHelper.getArray(json, "inputs"));

            //Outputs
            DefaultedList<ItemStack> outputs = RecipeUtils.deserializeStacks(JsonHelper.getArray(json, "outputs"));

            //Circles
            var circleArray = JsonHelper.getArray(json, "circles");
            Set<RitualCircle> circles = RecipeUtils.deserializeCircles(circleArray);

            //Sacrifices
            var sacrificeArray = JsonHelper.getArray(json, "sacrifices");
            List<EntityType<?>> sacrifices = RecipeUtils.deserializeEntityTypes(sacrificeArray);


            return new RitualRecipe(id, circles, inputs, outputs, sacrifices);
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

            //Sacrifices
            int sacrificeSize = buf.readInt();
            List<EntityType<?>> sacrificeList = IntStream.range(0, sacrificeSize).mapToObj(i -> Registry.ENTITY_TYPE.get(new Identifier(buf.readString()))).collect(Collectors.toList());

            return new RitualRecipe(id, circles, inputs, outputs, sacrificeList);
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
            //Sacrifices
            buf.writeInt(recipe.sacrifices.size());
            for(EntityType<?> entityType : recipe.sacrifices){
                buf.writeString(Registry.ENTITY_TYPE.getId(entityType).toString());
            }
        }

        @Override
        public JsonObject toJson(RitualRecipe recipe) {
            return null;
        }
    }
}
