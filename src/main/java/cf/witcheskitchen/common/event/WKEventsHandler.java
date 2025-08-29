package cf.witcheskitchen.common.event;

import cf.witcheskitchen.WitchesKitchen;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;

public class WKEventsHandler {
    /**
     * Loot tables are technical JSON files that are used to dictate what items should generate in various situations,
     * such as killing a mob or what items can be fished.
     * <p>
     * The simplest solution to add an item to the loot tables without replacing vanilla loot table files, (which can break other mods)
     * is by listening to Loot Tables loading (an event provided by the Fabric API).
     */
    public static class LootTablesListener implements LootTableEvents.Modify {
        @Override
        public void modifyLootTable(ResourceKey<LootTable> key, LootTable.Builder tableBuilder, LootTableSource source, HolderLookup.Provider registries) {
            final ResourceKey<LootTable> grassLootTable = Blocks.SHORT_GRASS.getLootTable().orElseThrow();
            final ResourceKey<LootTable> tallGrassLootTable = Blocks.TALL_GRASS.getLootTable().orElseThrow();
            final ResourceKey<LootTable> seedsAddition = ResourceKey.create(Registries.LOOT_TABLE, WitchesKitchen.id("listener/seeds"));
            if (key.equals(grassLootTable) || key.equals(tallGrassLootTable)) {
                // Adds a new entry for grass and tall grass loot tables
                tableBuilder.pool(LootPool.lootPool().add(NestedLootTable.lootTableReference(seedsAddition).setWeight(1)).build());
            }
        }
    }
}
