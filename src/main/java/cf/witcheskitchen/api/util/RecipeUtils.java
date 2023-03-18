package cf.witcheskitchen.api.util;

import cf.witcheskitchen.api.CommandType;
import cf.witcheskitchen.api.ritual.RitualCircle;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class RecipeUtils {

    private RecipeUtils() {
        // Don't let anyone instantiate this
    }

    /**
     * <p>
     * This function finds out whether the given slots from the {@link Inventory}
     * do match the {@link DefaultedList} of Ingredients, which is used as a filter
     * for crafting recipes.
     * </p>
     *
     * @param inventory   Inventory
     * @param ingredients DefaultedList
     * @param startIndex  inclusive
     * @param endIndex    inclusive
     * @return If the inventory contains the inputs for the recipe.
     */
    public static boolean matches(final Inventory inventory, final DefaultedList<Ingredient> ingredients, final int startIndex, final int endIndex) {
        // Throw unexpected arguments away which lead to bugs
        Validate.isTrue(inventory != null);
        Validate.isTrue(ingredients != null);
        if (endIndex < startIndex)
            throw new IllegalArgumentException("End index %d expected to be > starting index %d".formatted(endIndex, startIndex));
        // Quick fail if there is no data at all
        if (inventory.isEmpty()) {
            return false;
        }
        // Same for the inputs
        if (ingredients.isEmpty()) {
            return false;
        }
        // This collection holds the valid stacks
        final var validStacks = new ArrayList<ItemStack>();
        for (int slot = startIndex; slot <= endIndex; slot += 1) {
            final ItemStack stackInSlot = inventory.getStack(slot);
            // All the empty stacks are discarded
            if (stackInSlot.isEmpty()) {
                continue;
            }
            validStacks.add(stackInSlot);
        }

        // Our collection now has all the available items from the inventory
        if (validStacks.size() != ingredients.size()) {
            // If they do not have the same size there is no point on doing more processing
            return false;
        }
        // Now we compare the valid stacks with the inputs
        for (Ingredient ingredient : ingredients) {
            boolean valid = false; // flag that tells if the stack is valid
            for (int entry = 0; entry < validStacks.size(); entry++) {
                if (ingredient.test(validStacks.get(entry))) {
                    valid = true;
                    validStacks.remove(entry);
                    break;
                }
            }
            // If the item is not valid, it fails.
            if (!valid) {
                return false;
            }
        }
        return true;
    }

    /**
     * Deserializes an array of {@link ItemStack} from a JsonArray.
     *
     * @param array JsonArray
     * @return DefaultedList of ItemStack
     */
    public static DefaultedList<ItemStack> deserializeStacks(JsonArray array) {
        if (array.isJsonArray()) {
            return arrayStream(array.getAsJsonArray()).map(entry -> deserializeStack(entry.getAsJsonObject())).collect(DefaultedListCollector.toList());
        } else {
            return DefaultedList.copyOf(deserializeStack(array.getAsJsonObject()));
        }
    }

    public static List<EntityType<?>> deserializeEntityTypes(JsonArray array) {
        if (array.isJsonArray()) {
            return arrayStream(array.getAsJsonArray()).map(entry -> deserializeEntityType(entry.getAsJsonObject())).collect(DefaultedListCollector.toList());
        } else {
            return DefaultedList.copyOf(deserializeEntityType(array.getAsJsonObject()));
        }
    }

    /**
     * Deserializes an array of {@link Ingredient} from a JsonArray.
     *
     * @param array JsonArray
     * @return DefaultedList of Ingredient
     */
    public static DefaultedList<Ingredient> deserializeIngredients(JsonArray array) {
        final DefaultedList<Ingredient> ingredients = DefaultedList.of();
        for (int i = 0; i < array.size(); i++) {
            final Ingredient input = Ingredient.fromJson(array.get(i));
            if (!input.isEmpty()) {
                ingredients.add(input);
            }
        }
        return ingredients;
    }

    public static Stream<JsonElement> arrayStream(JsonArray array) {
        return IntStream.range(0, array.size()).mapToObj(array::get);
    }

    /**
     * Deserializes a {@link ItemStack} from Json.
     * Supports "count" and "nbt" fields.
     *
     * @param object JsonObject
     * @return brand-new Item deserialized
     */
    public static @NotNull ItemStack deserializeStack(JsonObject object) {
        final Identifier id = new Identifier(JsonHelper.getString(object, "item"));
        final Item item = Registries.ITEM.get(id);
        if (Items.AIR == item) {
            throw new IllegalStateException("Invalid item: " + item);
        }
        int count = 1;
        if (object.has("count")) {
            count = JsonHelper.getInt(object, "count");
        }
        final ItemStack stack = new ItemStack(item, count);
        if (object.has("nbt")) {
            final NbtCompound tag = (NbtCompound) Dynamic.convert(JsonOps.INSTANCE, NbtOps.INSTANCE, object.get("nbt"));
            stack.setNbt(tag);
        }
        return stack;
    }

    public static @Nullable EntityType<?> deserializeEntityType(JsonObject object) {
        final Identifier id = new Identifier(JsonHelper.getString(object, "entity"));
        return Registries.ENTITY_TYPE.get(id);
    }

    public static @NotNull Set<RitualCircle> deserializeCircles(JsonArray array) {
        if (!array.isEmpty()) {
            return arrayStream(array.getAsJsonArray()).map(entry -> deserializeCircle(entry.getAsJsonObject())).collect(Collectors.toSet());
        }
        return Set.of();
    }

    public static @NotNull RitualCircle deserializeCircle(JsonObject object) {
        String sizeId = JsonHelper.getString(object, "size");
        String typeId = JsonHelper.getString(object, "type");
        return new RitualCircle(RitualCircle.getSize(sizeId), RitualCircle.getType(typeId));
    }

    public static DefaultedList<Ingredient> getIngredients(JsonArray json) {
        DefaultedList<Ingredient> ingredients = DefaultedList.of();
        for (int i = 0; i < json.size(); i++) {
            Ingredient ingredient = Ingredient.fromJson(json.get(i));
            if (!ingredient.isEmpty()) {
                ingredients.add(ingredient);
            }
        }
        return ingredients;
    }

    public static Set<CommandType> deserializeCommands(JsonArray array) {
        if (!array.isEmpty()) {
            return arrayStream(array.getAsJsonArray()).map(entry -> deserializeCommand(entry.getAsJsonObject())).collect(Collectors.toSet());
        }
        return Set.of();
    }

    public static @NotNull CommandType deserializeCommand(JsonObject object) {
        String command = JsonHelper.getString(object, "command");
        String type = JsonHelper.getString(object, "type");
        return new CommandType(command, type);
    }

    /**
     * We need this collector to "collect" into a "DefaultedList"
     * A Collector is specified by four functions that work together to accumulate entries into a mutable result container.
     *
     * @param <T>
     */
    public static class DefaultedListCollector<T> implements Collector<T, DefaultedList<T>, DefaultedList<T>> {

        private static final Set<Characteristics> CH_ID = Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));

        public static <T> DefaultedListCollector<T> toList() {
            return new DefaultedListCollector<>();
        }

        @Override
        public Supplier<DefaultedList<T>> supplier() {
            return DefaultedList::of;
        }

        @Override
        public BiConsumer<DefaultedList<T>, T> accumulator() {
            return DefaultedList::add;
        }

        @Override
        public BinaryOperator<DefaultedList<T>> combiner() {
            return (left, right) -> {
                left.addAll(right);
                return left;
            };
        }

        @Override
        public Function<DefaultedList<T>, DefaultedList<T>> finisher() {
            return i -> (DefaultedList<T>) i;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return CH_ID;
        }
    }
}
