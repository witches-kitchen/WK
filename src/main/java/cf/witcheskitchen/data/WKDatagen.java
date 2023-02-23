package cf.witcheskitchen.data;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class WKDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(WKLanguageProvider::new);
        fabricDataGenerator.addProvider(WKLootTableProvider.BlockLoot::new);
        fabricDataGenerator.addProvider(WKLootTableProvider.EntityLoot::new);
        fabricDataGenerator.addProvider(WKModelProvider::new);
        fabricDataGenerator.addProvider(WKRecipeProvider::new);
        fabricDataGenerator.addProvider(WKTagProvider.WKBlockTags::new);
        fabricDataGenerator.addProvider(data -> new WKTagProvider.WKItemTags(data, new WKTagProvider.WKBlockTags(data)));
        fabricDataGenerator.addProvider(WKTagProvider.WKEntityTypeTags::new);
        fabricDataGenerator.addProvider(WKAdvancementsProvider::new);
    }
}
