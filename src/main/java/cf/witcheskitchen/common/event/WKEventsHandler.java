package cf.witcheskitchen.common.event;

import cf.witcheskitchen.WitchesKitchen;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.block.Blocks;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

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
        public void modifyLootTable(RegistryKey<LootTable> key, LootTable.Builder tableBuilder, LootTableSource source, RegistryWrapper.WrapperLookup registries) {
            final RegistryKey<LootTable> grassLootTable = Blocks.SHORT_GRASS.getLootTableKey().orElseThrow();
            final RegistryKey<LootTable> tallGrassLootTable = Blocks.TALL_GRASS.getLootTableKey().orElseThrow();
            final RegistryKey<LootTable> seedsAddition = RegistryKey.of(RegistryKeys.LOOT_TABLE, WitchesKitchen.id("listener/seeds"));
            if (key.equals(grassLootTable) || key.equals(tallGrassLootTable)) {
                // Adds a new entry for grass and tall grass loot tables
                tableBuilder.pool(LootPool.builder().with(LootTableEntry.builder(seedsAddition).weight(1)).build());
            }
        }
    }
}
