package cf.witcheskitchen.common.event;

import cf.witcheskitchen.WitchesKitchen;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.minecraft.block.Blocks;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

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
        public void modifyLootTable(ResourceManager resourceManager, LootManager lootManager, Identifier id, LootTable.Builder tableBuilder, LootTableSource source) {
            final Identifier grassLootTable = Blocks.GRASS.getLootTableId();
            final Identifier tallGrassLootTable = Blocks.TALL_GRASS.getLootTableId();
            final Identifier seedsAddition = WitchesKitchen.id("listener/seeds");
            if (id.equals(grassLootTable) || id.equals(tallGrassLootTable)) {
                // Adds a new entry for grass and tall grass loot tables
                tableBuilder.pool(LootPool.builder().with(LootTableEntry.builder(seedsAddition).weight(1)).build());
            }
        }
    }
}
