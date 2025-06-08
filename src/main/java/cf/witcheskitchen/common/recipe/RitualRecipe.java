package cf.witcheskitchen.common.recipe;

import cf.witcheskitchen.api.CommandType;
import cf.witcheskitchen.api.event.network.CustomPacketCodecs;
import cf.witcheskitchen.api.ritual.Ritual;
import cf.witcheskitchen.api.ritual.RitualCircle;
import cf.witcheskitchen.common.registry.WKRecipeTypes;
import cf.witcheskitchen.common.registry.WKRegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RitualRecipe implements Recipe<MultipleStackRecipeInput> {
    public final Ritual rite;
    public final Set<RitualCircle> circleSet;
    public final List<Ingredient> inputs;
    public final List<ItemStack> outputs;
    public final List<EntityType<?>> summons;
    public final List<EntityType<?>> sacrifices;
    public final int duration;
    public final Set<CommandType> command;
    public final String energy;

    public RitualRecipe(Ritual rite, String energy, Set<RitualCircle> circleSet, @Nullable List<Ingredient> inputs, @Nullable List<ItemStack> outputs, @Nullable List<EntityType<?>> sacrifices, @Nullable List<EntityType<?>> summons, int duration, Set<CommandType> command) {
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

    public static RitualRecipe fromCodec(Ritual rite, String energy, List<RitualCircle> circleSet, @Nullable List<Ingredient> inputs, @Nullable List<ItemStack> outputs, @Nullable List<EntityType<?>> sacrifices, @Nullable List<EntityType<?>> summons, int duration, List<CommandType> command) {
        return new RitualRecipe(rite, energy, new HashSet<>(circleSet), inputs, outputs, sacrifices, summons, duration, new HashSet<>(command));
    }

    public static boolean matches(MultipleStackRecipeInput inv, List<Ingredient> input, List<EntityType<?>> sacrifices) {
        List<ItemStack> checklist = new ArrayList<>();
        for (int i = 0; i < inv.size(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
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
    public boolean matches(MultipleStackRecipeInput input, World world) {
        return matches(input, inputs, sacrifices);
    }

    @Override
    public ItemStack craft(MultipleStackRecipeInput input, RegistryWrapper.WrapperLookup lookup) {
        return ItemStack.EMPTY;
    }

    public Ritual getRite() {
        return rite;
    }

    public String getEnergy() {
        return energy;
    }

    public List<ItemStack> getOutputs() {
        return outputs;
    }

    @Override
    public RecipeSerializer<? extends Recipe<MultipleStackRecipeInput>> getSerializer() {
        return WKRecipeTypes.RITUAL_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<? extends Recipe<MultipleStackRecipeInput>> getType() {
        return WKRecipeTypes.RITUAL_RECIPE_TYPE;
    }

    @Override
    public IngredientPlacement getIngredientPlacement() {
        if (this.inputs == null)
            return IngredientPlacement.NONE;

        return IngredientPlacement.forShapeless(this.inputs);
    }

    @Override
    public RecipeBookCategory getRecipeBookCategory() {
        // TODO: use custom recipe book category
        return null;
    }

    public List<EntityType<?>> getSacrifices() {
        return sacrifices;
    }

    public List<EntityType<?>> getSummons() {
        return summons;
    }

    public int getDuration() {
        return duration;
    }

    public Set<CommandType> getCommands() {
        return command;
    }

    public Set<RitualCircle> getCircles() {
        return circleSet;
    }

    public static class Serializer implements RecipeSerializer<RitualRecipe> {
        @Override
        public MapCodec<RitualRecipe> codec() {
            return RecordCodecBuilder.mapCodec(instance ->
                    instance.group(
                                    WKRegistries.RITUAL.getCodec()
                                            .fieldOf("ritual")
                                            .forGetter(RitualRecipe::getRite),
                                    Codec.STRING
                                            .optionalFieldOf("environment", "low")
                                            .forGetter(RitualRecipe::getEnergy),
                                    RitualCircle.CODEC
                                            .listOf()
                                            .fieldOf("circles")
                                            .validate(circles -> {
                                                if (circles.isEmpty()) {
                                                    return DataResult.error(() -> "No circles");
                                                }
                                                return DataResult.success(circles);
                                            })
                                            .forGetter(recipe -> recipe.getCircles().stream().toList()),
                                    Ingredient.CODEC
                                            .listOf()
                                            .fieldOf("inputs")
                                            .forGetter(recipe -> recipe.getIngredientPlacement().getIngredients()),
                                    ItemStack.CODEC
                                            .listOf()
                                            .fieldOf("outputs")
                                            .forGetter(RitualRecipe::getOutputs),
                                    Registries.ENTITY_TYPE.getCodec()
                                            .listOf()
                                            .fieldOf("sacrifices")
                                            .forGetter(RitualRecipe::getSacrifices),
                                    Registries.ENTITY_TYPE.getCodec()
                                            .listOf()
                                            .fieldOf("summons")
                                            .forGetter(RitualRecipe::getSummons),
                                    Codec.INT
                                            .fieldOf("duration")
                                            .forGetter(RitualRecipe::getDuration),
                                    CommandType.CODEC
                                            .listOf()
                                            .fieldOf("commands")
                                            .forGetter(recipe -> recipe.getCommands().stream().toList())
                            )
                            .apply(instance, RitualRecipe::fromCodec)
            );
        }

        @Override
        public PacketCodec<RegistryByteBuf, RitualRecipe> packetCodec() {
            return CustomPacketCodecs.tuple(
                    PacketCodecs.registryValue(WKRegistries.RITUAL.getKey()), RitualRecipe::getRite,
                    PacketCodecs.STRING, RitualRecipe::getEnergy,
                    CustomPacketCodecs.createSetCodec(RitualCircle.PACKET_CODEC), RitualRecipe::getCircles,
                    CustomPacketCodecs.INGREDIENT_LIST, recipe -> recipe.getIngredientPlacement().getIngredients(),
                    ItemStack.OPTIONAL_LIST_PACKET_CODEC, RitualRecipe::getOutputs,
                    CustomPacketCodecs.createListCodec(PacketCodecs.registryValue(RegistryKeys.ENTITY_TYPE)), RitualRecipe::getSacrifices,
                    CustomPacketCodecs.createListCodec(PacketCodecs.registryValue(RegistryKeys.ENTITY_TYPE)), RitualRecipe::getSummons,
                    PacketCodecs.VAR_INT, RitualRecipe::getDuration,
                    CustomPacketCodecs.createSetCodec(CommandType.PACKET_CODEC), RitualRecipe::getCommands,
                    RitualRecipe::new
            );
        }
    }
}
