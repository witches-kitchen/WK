package cf.witcheskitchen.data;

import cf.witcheskitchen.common.registry.WKBlocks;
import cf.witcheskitchen.common.registry.WKItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.ContainerComponentManipulators;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.CopyCustomDataFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.SetContainerContents;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class WKLootTableProvider {

    public static class BlockLoot extends FabricBlockLootTableProvider {
        private static final float[] SAPLING_DROP_CHANCE = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};

        protected BlockLoot(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> future) {
            super(dataOutput, future);
        }

        @Override
        public void generate() {
            barrelDrops(WKBlocks.ACACIA_BREWING_BARREL);
            barrelDrops(WKBlocks.SPRUCE_BREWING_BARREL);
            barrelDrops(WKBlocks.OAK_BREWING_BARREL);
            barrelDrops(WKBlocks.DARK_OAK_BREWING_BARREL);
            barrelDrops(WKBlocks.JUNGLE_BREWING_BARREL);
            barrelDrops(WKBlocks.BIRCH_BREWING_BARREL);
            barrelDrops(WKBlocks.CRIMSON_BREWING_BARREL);
            barrelDrops(WKBlocks.WARPED_BREWING_BARREL);

            ovenDrops(WKBlocks.COPPER_WITCHES_OVEN);
            ovenDrops(WKBlocks.EXPOSED_COPPER_WITCHES_OVEN);
            ovenDrops(WKBlocks.WEATHERED_COPPER_WITCHES_OVEN);
            ovenDrops(WKBlocks.OXIDIZED_COPPER_WITCHES_OVEN);
            ovenDrops(WKBlocks.WAXED_COPPER_WITCHES_OVEN);
            ovenDrops(WKBlocks.WAXED_EXPOSED_COPPER_WITCHES_OVEN);
            ovenDrops(WKBlocks.WAXED_WEATHERED_COPPER_WITCHES_OVEN);
            ovenDrops(WKBlocks.WAXED_OXIDIZED_COPPER_WITCHES_OVEN);

            ovenDrops(WKBlocks.IRON_WITCHES_OVEN);

            cauldronDrops(WKBlocks.IRON_WITCHES_CAULDRON);

            teaDrops(WKBlocks.TEAPOT);
            teaDrops(WKBlocks.CAST_IRON_TEAPOT);

            teaDrops(WKBlocks.COPPER_TEAPOT);
            teaDrops(WKBlocks.EXPOSED_COPPER_TEAPOT);
            teaDrops(WKBlocks.WEATHERED_COPPER_TEAPOT);
            teaDrops(WKBlocks.OXIDIZED_COPPER_TEAPOT);
            teaDrops(WKBlocks.WAXED_COPPER_TEAPOT);
            teaDrops(WKBlocks.WAXED_EXPOSED_COPPER_TEAPOT);
            teaDrops(WKBlocks.WAXED_WEATHERED_COPPER_TEAPOT);
            teaDrops(WKBlocks.WAXED_OXIDIZED_COPPER_TEAPOT);

            dropSelf(WKBlocks.BLACKTHORN_SAPLING);
            dropSelf(WKBlocks.ELDER_SAPLING);
            dropSelf(WKBlocks.HAWTHORN_SAPLING);
            dropSelf(WKBlocks.JUNIPER_SAPLING);
            dropSelf(WKBlocks.ROWAN_SAPLING);
            dropSelf(WKBlocks.SUMAC_SAPLING);

            dropSelf(WKBlocks.BLACKTHORN_LOG);
            dropSelf(WKBlocks.ELDER_LOG);
            dropSelf(WKBlocks.HAWTHORN_LOG);
            dropSelf(WKBlocks.JUNIPER_LOG);
            dropSelf(WKBlocks.ROWAN_LOG);
            dropSelf(WKBlocks.SUMAC_LOG);

            dropSelf(WKBlocks.BLACKTHORN_PLANKS);
            dropSelf(WKBlocks.ELDER_PLANKS);
            dropSelf(WKBlocks.HAWTHORN_PLANKS);
            dropSelf(WKBlocks.JUNIPER_PLANKS);
            dropSelf(WKBlocks.ROWAN_PLANKS);
            dropSelf(WKBlocks.SUMAC_PLANKS);

            dropSelf(WKBlocks.BLACKTHORN_WOOD);
            dropSelf(WKBlocks.ELDER_WOOD);
            dropSelf(WKBlocks.HAWTHORN_WOOD);
            dropSelf(WKBlocks.JUNIPER_WOOD);
            dropSelf(WKBlocks.ROWAN_WOOD);
            dropSelf(WKBlocks.SUMAC_WOOD);

            createLeavesDrops(WKBlocks.BLACKTHORN_LEAVES, WKBlocks.BLACKTHORN_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES);
            createLeavesDrops(WKBlocks.ELDER_LEAVES, WKBlocks.ELDER_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES);
            createLeavesDrops(WKBlocks.HAWTHORN_LEAVES, WKBlocks.HAWTHORN_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES);
            createLeavesDrops(WKBlocks.JUNIPER_LEAVES, WKBlocks.JUNIPER_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES);
            createLeavesDrops(WKBlocks.ROWAN_LEAVES, WKBlocks.ROWAN_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES);
            createLeavesDrops(WKBlocks.SUMAC_LEAVES, WKBlocks.SUMAC_SAPLING, NORMAL_LEAVES_SAPLING_CHANCES);

            createSlabItemTable(WKBlocks.BLACKTHORN_SLAB);
            createSlabItemTable(WKBlocks.ELDER_SLAB);
            createSlabItemTable(WKBlocks.HAWTHORN_SLAB);
            createSlabItemTable(WKBlocks.JUNIPER_SLAB);
            createSlabItemTable(WKBlocks.ROWAN_SLAB);
            createSlabItemTable(WKBlocks.SUMAC_SLAB);

            dropSelf(WKBlocks.BLACKTHORN_STAIRS);
            dropSelf(WKBlocks.ELDER_STAIRS);
            dropSelf(WKBlocks.HAWTHORN_STAIRS);
            dropSelf(WKBlocks.JUNIPER_STAIRS);
            dropSelf(WKBlocks.ROWAN_STAIRS);
            dropSelf(WKBlocks.SUMAC_STAIRS);

            dropSelf(WKBlocks.STRIPPED_BLACKTHORN_WOOD);
            dropSelf(WKBlocks.STRIPPED_ELDER_WOOD);
            dropSelf(WKBlocks.STRIPPED_HAWTHORN_WOOD);
            dropSelf(WKBlocks.STRIPPED_JUNIPER_WOOD);
            dropSelf(WKBlocks.STRIPPED_ROWAN_WOOD);
            dropSelf(WKBlocks.STRIPPED_SUMAC_WOOD);

            dropSelf(WKBlocks.STRIPPED_BLACKTHORN_LOG);
            dropSelf(WKBlocks.STRIPPED_ELDER_LOG);
            dropSelf(WKBlocks.STRIPPED_HAWTHORN_LOG);
            dropSelf(WKBlocks.STRIPPED_JUNIPER_LOG);
            dropSelf(WKBlocks.STRIPPED_ROWAN_LOG);
            dropSelf(WKBlocks.STRIPPED_SUMAC_LOG);

            addPlantDrop(WKBlocks.AMARANTH_PLANT, WKItems.AMARANTH_SPRIG, WKItems.AMARANTH_SEEDS);
            addPlantDrop(WKBlocks.BELLADONNA_PLANT, WKItems.BELLADONNA_BLOSSOM, WKItems.BELLADONNA_SEEDS);
            addPlantDrop(WKBlocks.CHAMOMILE_PLANT, WKItems.CHAMOMILE_BLOSSOM, WKItems.CHAMOMILE_SEEDS);
            addPlantDrop(WKBlocks.CONEFLOWER, WKItems.CONEFLOWER_BLOSSOM, WKItems.CONEFLOWER_SEEDS);
            addPlantDrop(WKBlocks.FOXGLOVE_PLANT, WKItems.FOXGLOVE_BLOSSOM, WKItems.FOXGLOVE_SEEDS);
            addPlantDrop(WKBlocks.HELLEBORE_PLANT, WKItems.HELLEBORE_BLOSSOM, WKItems.HELLEBORE_SEEDS);
            addPlantDrop(WKBlocks.IRIS_PLANT, WKItems.IRIS_BLOSSOM, WKItems.IRIS_SEEDS);
            addPlantDrop(WKBlocks.SANGUINARY_PLANT, WKItems.SANGUINARY_BLOSSOM, WKItems.SANGUINARY_SEEDS);
        }

        @Override
        public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> biConsumer) {

        }

        public void addPlantDrop(Block block, ItemLike drop, ItemLike seed) {
            LootItemCondition.Builder builder = LootItemBlockStatePropertyCondition.hasBlockStateProperties(block);
            this.add(block, applyExplosionDecay(
                    seed, LootTable.lootTable()
                            .withPool(LootPool.lootPool()
                                    .add(LootItem.lootTableItem(seed)))
                            .withPool(LootPool.lootPool().when(builder)
                                    .add(LootItem.lootTableItem(seed)
                                            .apply(ApplyBonusCount
                                                    .addBonusBinomialDistributionCount(this.registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE), 0.5714286F, 3)))))
                    .withPool(LootPool.lootPool()
                            .add(LootItem.lootTableItem(drop))));
        }

        public LootTable.Builder barrelDrops(Block drop) {
            return LootTable.lootTable().withPool(applyExplosionDecay(drop,
                            LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                                    .add(LootItem.lootTableItem(drop).apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY))
                                            .apply(CopyCustomDataFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
                                                    .copy("Lock", "BlockEntityTag.Lock")
                                                    .copy("LootTable", "BlockEntityTag.LootTable")
                                                    .copy("LootTableSeed", "BlockEntityTag.LootTableSeed")
                                            )
                                            .apply(SetContainerContents.setContents(ContainerComponentManipulators.CONTAINER))
                                    )
                    )
            );
        }

        public LootTable.Builder ovenDrops(Block drop) {
            return LootTable.lootTable().withPool(applyExplosionDecay(drop,
                            LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                                    .add(LootItem.lootTableItem(drop).apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY))
                                            .apply(CopyCustomDataFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
                                                    .copy("Lock", "BlockEntityTag.Lock")
                                                    .copy("LootTable", "BlockEntityTag.LootTable")
                                                    .copy("LootTableSeed", "BlockEntityTag.LootTableSeed")
                                            )
                                            .apply(SetContainerContents.setContents(ContainerComponentManipulators.CONTAINER))
                                    )
                    )
            );
        }

        public LootTable.Builder teaDrops(Block drop) {
            return LootTable.lootTable().withPool(applyExplosionDecay(drop,
                            LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                                    .add(LootItem.lootTableItem(drop).apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY))
                                            .apply(CopyCustomDataFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
                                                    .copy("Lock", "BlockEntityTag.Lock")
                                                    .copy("LootTable", "BlockEntityTag.LootTable")
                                                    .copy("LootTableSeed", "BlockEntityTag.LootTableSeed")
                                            )
                                            .apply(SetContainerContents.setContents(ContainerComponentManipulators.CONTAINER))
                                    )
                    )
            );
        }

        public LootTable.Builder cauldronDrops(Block drop) {
            return LootTable.lootTable().withPool(applyExplosionDecay(drop,
                            LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                                    .add(LootItem.lootTableItem(drop).apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY))
                                            .apply(CopyCustomDataFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
                                                    .copy("Lock", "BlockEntityTag.Lock")
                                                    .copy("LootTable", "BlockEntityTag.LootTable")
                                                    .copy("LootTableSeed", "BlockEntityTag.LootTableSeed")
                                            )
                                            .apply(SetContainerContents.setContents(ContainerComponentManipulators.CONTAINER))
                                    )
                    )
            );
        }
    }

    public static class EntityLoot extends SimpleFabricLootTableProvider {
        public EntityLoot(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
            super(output, lookup, LootContextParamSets.ENTITY);
        }

        @Override
        public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> lootTableBiConsumer) {

        }
    }


}
