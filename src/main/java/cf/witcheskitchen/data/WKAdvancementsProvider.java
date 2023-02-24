package cf.witcheskitchen.data;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.common.registry.WKItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class WKAdvancementsProvider extends FabricAdvancementProvider {
    protected WKAdvancementsProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> consumer) {
        var root = Advancement.Task.create()
                .display(
                        WKItems.DOLLOP_OF_FROSTING,
                        Text.translatable("witcheskitchen.advancements.witcheskitchen.root.title"),
                        Text.translatable("witcheskitchen.advancements.witcheskitchen.root.desc"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        false,
                        false
                )
                .criterion("has_item", InventoryChangedCriterion.Conditions.items(WKItems.DOLLOP_OF_FROSTING))
                .criterion("has_the_recipe", RecipeUnlockedCriterion.create(WitchesKitchen.id("frosting")))
                .build(consumer, "witcheskitchen:witcheskitchen/root");
    }
}
