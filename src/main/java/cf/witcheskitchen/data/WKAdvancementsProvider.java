package cf.witcheskitchen.data;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.common.registry.WKItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class WKAdvancementsProvider extends FabricAdvancementProvider {
    protected WKAdvancementsProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generateAdvancement(HolderLookup.Provider lookup, Consumer<AdvancementHolder> consumer) {
        var root = Advancement.Builder.advancement()
            .display(
                WKItems.DOLLOP_OF_FROSTING,
                Component.translatable("witcheskitchen.advancements.witcheskitchen.root.title"),
                Component.translatable("witcheskitchen.advancements.witcheskitchen.root.desc"),
                null,
                AdvancementType.TASK,
                true,
                false,
                false
            )
            .addCriterion("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(WKItems.DOLLOP_OF_FROSTING))
            .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(ResourceKey.create(Registries.RECIPE, WitchesKitchen.id("frosting"))))
            .save(consumer, "witcheskitchen:witcheskitchen/root");
    }
}
