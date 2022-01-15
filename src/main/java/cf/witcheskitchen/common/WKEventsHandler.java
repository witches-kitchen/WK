package cf.witcheskitchen.common;

import cf.witcheskitchen.WK;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.FabricLootSupplierBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.block.Blocks;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

public class WKEventsHandler {

    /**
     * Loot tables are technical JSON files that are used to dictate what items should generate in various situations,
     * such as killing a mob or what items can be fished.
     *
     * The simplest solution to add an item to the loot tables without replacing vanilla loot table files, (which can break other mods)
     * is by listening to Loot Tables loading (an event provided by the Fabric API).
     */
    public static class LootTablesListener implements LootTableLoadingCallback {

        @Override
        public void onLootTableLoading(ResourceManager resourceManager, LootManager manager, Identifier id, FabricLootSupplierBuilder supplier, LootTableSetter setter) {
            final Identifier grassLootTable = Blocks.GRASS.getLootTableId();
            final Identifier tallGrassLootTable = Blocks.TALL_GRASS.getLootTableId();
            final Identifier seedsAddition = WK.id("listener/seeds");
            if (id.equals(grassLootTable) || id.equals(tallGrassLootTable)) {
                // Adds a new entry for grass and tall grass loot tables
                supplier.withPool(FabricLootPoolBuilder.builder().with(LootTableEntry.builder(seedsAddition).weight(1)).build());
            }
        }
    }
}
