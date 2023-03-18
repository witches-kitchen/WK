package cf.witcheskitchen.common.recipe;

import cf.witcheskitchen.api.CommandType;
import cf.witcheskitchen.api.ritual.Ritual;
import cf.witcheskitchen.api.ritual.RitualCircle;
import cf.witcheskitchen.api.util.RecipeUtils;
import cf.witcheskitchen.common.registry.WKRecipeTypes;
import cf.witcheskitchen.common.registry.WKRegistries;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
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
    public final Ritual rite;
    public final Set<RitualCircle> circleSet;
    public final DefaultedList<Ingredient> inputs;
    public final List<ItemStack> outputs;
    public final List<EntityType<?>> summons;
    public final List<EntityType<?>> sacrifices;
    public final int duration;
    public final Set<CommandType> command;
    public final String energy;

    public RitualRecipe(Identifier id, Ritual rite, String energy, Set<RitualCircle> circleSet, @Nullable DefaultedList<Ingredient> inputs, @Nullable List<ItemStack> outputs, @Nullable List<EntityType<?>> sacrifices, @Nullable List<EntityType<?>> summons, int duration, Set<CommandType> command) {
        this.id = id;
        this.rite = rite;
        this.circleSet = circleSet;
        this.outputs = outputs;
        this.inputs = inputs;
        this.sacrifices = sacrifices;
        this.duration = duration;
        this.command = command;
        this.energy = energy;
        this.summons = summons;
    }

    public static boolean matches(Inventory inv, DefaultedList<Ingredient> input, List<EntityType<?>> sacrifices) {
        List<ItemStack> checklist = new ArrayList<>();
        for (int i = 0; i < inv.size(); i++) {
            ItemStack stack = inv.getStack(i);
            if (!stack.isEmpty()) {
                checklist.add(stack);
            }
        }
        if (input.size() != checklist.size()) {
            return false;
        }
        for (Ingredient ingredient : input) {
            boolean found = false;
            for (ItemStack stack : checklist) {
                if (ingredient.test(stack)) {
                    found = true;
                    checklist.remove(stack);
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return matches(inventory, inputs, sacrifices);
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
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
            //Rite
            Identifier ritualId = new Identifier(JsonHelper.getString(json, "ritual"));
            Ritual rite = WKRegistries.RITUAL.get(ritualId);

            //Environmental Energy
            String energy = JsonHelper.getString(json, "environment", "low");

            //Inputs
            DefaultedList<Ingredient> inputs = RecipeUtils.getIngredients(JsonHelper.getArray(json, "inputs"));

            //Outputs
            DefaultedList<ItemStack> outputs = RecipeUtils.deserializeStacks(JsonHelper.getArray(json, "outputs"));

            //Circles
            var circleArray = JsonHelper.getArray(json, "circles");
            Set<RitualCircle> circles = RecipeUtils.deserializeCircles(circleArray);
            if (circleArray.isEmpty()) {
                throw new JsonParseException("No circles");
            }

            //Sacrifices
            var sacrificeArray = JsonHelper.getArray(json, "sacrifices");
            List<EntityType<?>> sacrifices = RecipeUtils.deserializeEntityTypes(sacrificeArray);

            //Summons
            var summonArray = JsonHelper.getArray(json, "summons");
            List<EntityType<?>> summons = RecipeUtils.deserializeEntityTypes(summonArray);

            //Duration
            int duration = JsonHelper.getInt(json, "duration", 0);

            //Command
            JsonArray commandArray = JsonHelper.getArray(json, "commands");
            Set<CommandType> commands = RecipeUtils.deserializeCommands(commandArray);

            return new RitualRecipe(id, rite, energy, circles, inputs, outputs, sacrifices, summons, duration, commands);
        }

        @Override
        public RitualRecipe read(Identifier id, PacketByteBuf buf) {
            //Rite
            Ritual rite = WKRegistries.RITUAL.get(new Identifier(buf.readString()));

            //Environmental energy
            String energy = buf.readString();

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
            List<EntityType<?>> sacrificeList = IntStream.range(0, sacrificeSize).mapToObj(i -> Registries.ENTITY_TYPE.get(new Identifier(buf.readString()))).collect(Collectors.toList());

            //Summons
            int summonsSize = buf.readInt();
            List<EntityType<?>> summons = IntStream.range(0, summonsSize).mapToObj(i -> Registries.ENTITY_TYPE.get(new Identifier(buf.readString()))).collect(Collectors.toList());

            //Duration
            int duration = buf.readInt();

            //Commands
            Set<CommandType> commandTypeSet = new HashSet<>();
            for (int i = 0; i < buf.readInt(); i++) {
                commandTypeSet.add(new CommandType(buf.readString(), buf.readString()));
            }

            return new RitualRecipe(id, rite, energy, circles, inputs, outputs, sacrificeList, summons, duration, commandTypeSet);
        }

        @Override
        public void write(PacketByteBuf buf, RitualRecipe recipe) {
            //Rite
            buf.writeString(WKRegistries.RITUAL.getId(recipe.rite).toString());

            //Energy
            buf.writeString(recipe.energy);

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
            for (RitualCircle circle : recipe.circleSet) {
                buf.writeString(circle.getSize());
                buf.writeString(circle.getType());
            }
            //Sacrifices
            buf.writeInt(recipe.sacrifices.size());
            for (EntityType<?> entityType : recipe.sacrifices) {
                buf.writeString(Registries.ENTITY_TYPE.getId(entityType).toString());
            }

            //Summons
            buf.writeInt(recipe.summons.size());
            for (EntityType<?> entityType : recipe.summons) {
                buf.writeString(Registries.ENTITY_TYPE.getId(entityType).toString());
            }

            //Duration
            buf.writeInt(recipe.duration);

            //Commands
            buf.writeVarInt(recipe.command.size());
            for (CommandType commandType : recipe.command) {
                buf.writeString(commandType.getCommand());
                buf.writeString(commandType.getType());
            }
        }

        @Override
        public JsonObject toJson(RitualRecipe recipe) {
            return null;
        }
    }
}
