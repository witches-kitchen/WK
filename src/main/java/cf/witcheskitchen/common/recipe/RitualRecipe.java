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
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record RitualRecipe(Ritual rite, String energy, Set<RitualCircle> circleSet, List<Ingredient> inputs,
                           List<ItemStack> outputs, List<EntityType<?>> sacrifices, List<EntityType<?>> summons,
                           int duration, Set<CommandType> command) implements Recipe<MultipleStackRecipeInput> {
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
            ItemStack stack = inv.getItem(i);
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
    public boolean matches(MultipleStackRecipeInput input, Level world) {
        return matches(input, inputs, sacrifices);
    }

    @Override
    public ItemStack assemble(MultipleStackRecipeInput input, HolderLookup.Provider lookup) {
        return ItemStack.EMPTY;
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
    public PlacementInfo placementInfo() {
        if (this.inputs == null)
            return PlacementInfo.NOT_PLACEABLE;

        return PlacementInfo.create(this.inputs);
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        // TODO: use custom recipe book category
        return null;
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
                        WKRegistries.RITUAL.byNameCodec()
                            .fieldOf("ritual")
                            .forGetter(RitualRecipe::rite),
                        Codec.STRING
                            .optionalFieldOf("environment", "low")
                            .forGetter(RitualRecipe::energy),
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
                            .forGetter(recipe -> recipe.placementInfo().ingredients()),
                        ItemStack.CODEC
                            .listOf()
                            .fieldOf("outputs")
                            .forGetter(RitualRecipe::outputs),
                        BuiltInRegistries.ENTITY_TYPE.byNameCodec()
                            .listOf()
                            .fieldOf("sacrifices")
                            .forGetter(RitualRecipe::sacrifices),
                        BuiltInRegistries.ENTITY_TYPE.byNameCodec()
                            .listOf()
                            .fieldOf("summons")
                            .forGetter(RitualRecipe::summons),
                        Codec.INT
                            .fieldOf("duration")
                            .forGetter(RitualRecipe::duration),
                        CommandType.CODEC
                            .listOf()
                            .fieldOf("commands")
                            .forGetter(recipe -> recipe.getCommands().stream().toList())
                    )
                    .apply(instance, RitualRecipe::fromCodec)
            );
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, RitualRecipe> streamCodec() {
            return CustomPacketCodecs.tuple(
                ByteBufCodecs.registry(WKRegistries.RITUAL.key()), RitualRecipe::rite,
                ByteBufCodecs.STRING_UTF8, RitualRecipe::energy,
                CustomPacketCodecs.createSetCodec(RitualCircle.PACKET_CODEC), RitualRecipe::getCircles,
                CustomPacketCodecs.INGREDIENT_LIST, recipe -> recipe.placementInfo().ingredients(),
                ItemStack.OPTIONAL_LIST_STREAM_CODEC, RitualRecipe::outputs,
                CustomPacketCodecs.createListCodec(ByteBufCodecs.registry(Registries.ENTITY_TYPE)), RitualRecipe::sacrifices,
                CustomPacketCodecs.createListCodec(ByteBufCodecs.registry(Registries.ENTITY_TYPE)), RitualRecipe::summons,
                ByteBufCodecs.VAR_INT, RitualRecipe::duration,
                CustomPacketCodecs.createSetCodec(CommandType.PACKET_CODEC), RitualRecipe::getCommands,
                RitualRecipe::new
            );
        }
    }
}
