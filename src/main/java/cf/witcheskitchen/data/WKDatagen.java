package cf.witcheskitchen.data;

import cf.witcheskitchen.data.worldgen.WKConfiguredFeatures;
import cf.witcheskitchen.data.worldgen.WKPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class WKDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
        FabricDataGenerator.Pack pack = dataGenerator.createPack();

        pack.addProvider(WKLanguageProvider::new);
        pack.addProvider(WKLootTableProvider.BlockLoot::new);
        pack.addProvider(WKLootTableProvider.EntityLoot::new);
        pack.addProvider(WKRecipeProvider::new);
        pack.addProvider(WKTagProvider.WKBlockTags::new);
        pack.addProvider(WKTagProvider.WKItemTags::new);
        pack.addProvider(WKTagProvider.WKEntityTypeTags::new);
        //pack.addProvider(WKTagProvider.WKDamageTypeTags::new);
        pack.addProvider(WKAdvancementsProvider::new);
        pack.addProvider(WKWorldGenProvider::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, WKConfiguredFeatures::init);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, WKPlacedFeatures::init);
    }
}
