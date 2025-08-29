package cf.witcheskitchen.api.util;

import cf.witcheskitchen.api.CommandType;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
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
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeInput;

public final class RecipeUtils {

    private RecipeUtils() {
        // Don't let anyone instantiate this
    }

    /**
     * <p>
     * This function finds out whether the given slots from the {@link Container}
     * do match the {@link NonNullList} of Ingredients, which is used as a filter
     * for crafting recipes.
     * </p>
     *
     * @param inventory   Inventory
     * @param ingredients DefaultedList
     * @param startIndex  inclusive
     * @param endIndex    inclusive
     * @return If the inventory contains the inputs for the recipe.
     */
    public static boolean matches(final RecipeInput inventory, final List<Ingredient> ingredients, final int startIndex, final int endIndex) {
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
            final ItemStack stackInSlot = inventory.getItem(slot);
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
    public static NonNullList<ItemStack> deserializeStacks(JsonArray array) {
        if (array.isJsonArray()) {
            return arrayStream(array.getAsJsonArray()).map(entry -> deserializeStack(entry.getAsJsonObject())).collect(DefaultedListCollector.toList());
        } else {
            return NonNullList.of(deserializeStack(array.getAsJsonObject()));
        }
    }

    public static List<EntityType<?>> deserializeEntityTypes(JsonArray array) {
        if (array.isJsonArray()) {
            return arrayStream(array.getAsJsonArray()).map(entry -> deserializeEntityType(entry.getAsJsonObject())).collect(DefaultedListCollector.toList());
        } else {
            return NonNullList.of(deserializeEntityType(array.getAsJsonObject()));
        }
    }

    /**
     * Deserializes an array of {@link Ingredient} from a JsonArray.
     *
     * @param array JsonArray
     * @return DefaultedList of Ingredient
     */
    public static NonNullList<Ingredient> deserializeIngredients(JsonArray array) {
        final NonNullList<Ingredient> ingredients = NonNullList.create();
        for (int i = 0; i < array.size(); i++) {
            final Ingredient input = Ingredient.CODEC.parse(JsonOps.INSTANCE, array.get(i)).getOrThrow();
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
        ItemStack stack = ItemStack.CODEC.parse(JsonOps.INSTANCE, object).getOrThrow();
        if (stack.isEmpty()) {
            throw new IllegalStateException("Stack is empty: " + stack.getItem() + " (" + stack.getCount() + ")");
        }
        return stack;
    }

    public static @Nullable EntityType<?> deserializeEntityType(JsonObject object) {
        final ResourceLocation id = ResourceLocation.tryParse(GsonHelper.getAsString(object, "entity"));
        return BuiltInRegistries.ENTITY_TYPE.getValue(id);
    }

    public static NonNullList<Ingredient> getIngredients(JsonArray json) {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        for (int i = 0; i < json.size(); i++) {
            Ingredient ingredient = Ingredient.CODEC.parse(JsonOps.INSTANCE, json.get(i)).getOrThrow();
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
        String command = GsonHelper.getAsString(object, "command");
        String type = GsonHelper.getAsString(object, "type");
        return new CommandType(command, type);
    }

    /**
     * We need this collector to "collect" into a "DefaultedList"
     * A Collector is specified by four functions that work together to accumulate entries into a mutable result container.
     *
     * @param <T>
     */
    public static class DefaultedListCollector<T> implements Collector<T, NonNullList<T>, NonNullList<T>> {

        private static final Set<Characteristics> CH_ID = Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));

        public static <T> DefaultedListCollector<T> toList() {
            return new DefaultedListCollector<>();
        }

        @Override
        public Supplier<NonNullList<T>> supplier() {
            return NonNullList::create;
        }

        @Override
        public BiConsumer<NonNullList<T>, T> accumulator() {
            return NonNullList::add;
        }

        @Override
        public BinaryOperator<NonNullList<T>> combiner() {
            return (left, right) -> {
                left.addAll(right);
                return left;
            };
        }

        @Override
        public Function<NonNullList<T>, NonNullList<T>> finisher() {
            return i -> (NonNullList<T>) i;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return CH_ID;
        }
    }
}
