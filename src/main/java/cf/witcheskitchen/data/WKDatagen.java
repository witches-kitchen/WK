package cf.witcheskitchen.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class WKDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
        FabricDataGenerator.Pack pack = dataGenerator.createPack();

        pack.addProvider(WKLanguageProvider::new);
        pack.addProvider(WKLootTableProvider.BlockLoot::new);
        pack.addProvider(WKLootTableProvider.EntityLoot::new);
        pack.addProvider(WKModelProvider::new);
        pack.addProvider(WKRecipeProvider::new);
        pack.addProvider(WKTagProvider.WKBlockTags::new);
        pack.addProvider(WKTagProvider.WKItemTags::new);
        pack.addProvider(WKTagProvider.WKEntityTypeTags::new);
        pack.addProvider(WKAdvancementsProvider::new);
    }
}
