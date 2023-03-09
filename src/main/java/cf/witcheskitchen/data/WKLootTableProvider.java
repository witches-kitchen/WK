package cf.witcheskitchen.data;

import cf.witcheskitchen.common.registry.WKBlockEntityTypes;
import cf.witcheskitchen.common.registry.WKBlocks;
import cf.witcheskitchen.common.registry.WKItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemConvertible;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.DynamicEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.CopyNameLootFunction;
import net.minecraft.loot.function.CopyNbtLootFunction;
import net.minecraft.loot.function.SetContentsLootFunction;
import net.minecraft.loot.provider.nbt.ContextLootNbtProvider;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

public class WKLootTableProvider {

    public static class BlockLoot extends FabricBlockLootTableProvider {
        private static final float[] SAPLING_DROP_CHANCE = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};

        protected BlockLoot(FabricDataOutput dataOutput) {
            super(dataOutput);
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

            addDrop(WKBlocks.BLACKTHORN_SAPLING);
            addDrop(WKBlocks.ELDER_SAPLING);
            addDrop(WKBlocks.HAWTHORN_SAPLING);
            addDrop(WKBlocks.JUNIPER_SAPLING);
            addDrop(WKBlocks.ROWAN_SAPLING);
            addDrop(WKBlocks.SUMAC_SAPLING);

            addDrop(WKBlocks.BLACKTHORN_LOG);
            addDrop(WKBlocks.ELDER_LOG);
            addDrop(WKBlocks.HAWTHORN_LOG);
            addDrop(WKBlocks.JUNIPER_LOG);
            addDrop(WKBlocks.ROWAN_LOG);
            addDrop(WKBlocks.SUMAC_LOG);

            addDrop(WKBlocks.BLACKTHORN_PLANKS);
            addDrop(WKBlocks.ELDER_PLANKS);
            addDrop(WKBlocks.HAWTHORN_PLANKS);
            addDrop(WKBlocks.JUNIPER_PLANKS);
            addDrop(WKBlocks.ROWAN_PLANKS);
            addDrop(WKBlocks.SUMAC_PLANKS);

            addDrop(WKBlocks.BLACKTHORN_WOOD);
            addDrop(WKBlocks.ELDER_WOOD);
            addDrop(WKBlocks.HAWTHORN_WOOD);
            addDrop(WKBlocks.JUNIPER_WOOD);
            addDrop(WKBlocks.ROWAN_WOOD);
            addDrop(WKBlocks.SUMAC_WOOD);

            leavesDrops(WKBlocks.BLACKTHORN_LEAVES, WKBlocks.BLACKTHORN_SAPLING, SAPLING_DROP_CHANCE);
            leavesDrops(WKBlocks.ELDER_LEAVES, WKBlocks.ELDER_SAPLING, SAPLING_DROP_CHANCE);
            leavesDrops(WKBlocks.HAWTHORN_LEAVES, WKBlocks.HAWTHORN_SAPLING, SAPLING_DROP_CHANCE);
            leavesDrops(WKBlocks.JUNIPER_LEAVES, WKBlocks.JUNIPER_SAPLING, SAPLING_DROP_CHANCE);
            leavesDrops(WKBlocks.ROWAN_LEAVES, WKBlocks.ROWAN_SAPLING, SAPLING_DROP_CHANCE);
            leavesDrops(WKBlocks.SUMAC_LEAVES, WKBlocks.SUMAC_SAPLING, SAPLING_DROP_CHANCE);

            slabDrops(WKBlocks.BLACKTHORN_SLAB);
            slabDrops(WKBlocks.ELDER_SLAB);
            slabDrops(WKBlocks.HAWTHORN_SLAB);
            slabDrops(WKBlocks.JUNIPER_SLAB);
            slabDrops(WKBlocks.ROWAN_SLAB);
            slabDrops(WKBlocks.SUMAC_SLAB);

            addDrop(WKBlocks.BLACKTHORN_STAIRS);
            addDrop(WKBlocks.ELDER_STAIRS);
            addDrop(WKBlocks.HAWTHORN_STAIRS);
            addDrop(WKBlocks.JUNIPER_STAIRS);
            addDrop(WKBlocks.ROWAN_STAIRS);
            addDrop(WKBlocks.SUMAC_STAIRS);

            addDrop(WKBlocks.STRIPPED_BLACKTHORN_WOOD);
            addDrop(WKBlocks.STRIPPED_ELDER_WOOD);
            addDrop(WKBlocks.STRIPPED_HAWTHORN_WOOD);
            addDrop(WKBlocks.STRIPPED_JUNIPER_WOOD);
            addDrop(WKBlocks.STRIPPED_ROWAN_WOOD);
            addDrop(WKBlocks.STRIPPED_SUMAC_WOOD);

            addDrop(WKBlocks.STRIPPED_BLACKTHORN_LOG);
            addDrop(WKBlocks.STRIPPED_ELDER_LOG);
            addDrop(WKBlocks.STRIPPED_HAWTHORN_LOG);
            addDrop(WKBlocks.STRIPPED_JUNIPER_LOG);
            addDrop(WKBlocks.STRIPPED_ROWAN_LOG);
            addDrop(WKBlocks.STRIPPED_SUMAC_LOG);

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
        public void accept(BiConsumer<Identifier, LootTable.Builder> identifierBuilderBiConsumer) {

        }

        public void addPlantDrop(Block block, ItemConvertible drop, ItemConvertible seed) {
            LootCondition.Builder builder = BlockStatePropertyLootCondition.builder(block);
            this.add(block, applyExplosionDecay(
                    seed, LootTable.builder()
                            .pool(LootPool.builder()
                                    .with(ItemEntry.builder(seed)))
                            .pool(LootPool.builder().conditionally(builder)
                                    .with(ItemEntry.builder(seed)
                                            .apply(ApplyBonusLootFunction
                                                    .binomialWithBonusCount(Enchantments.FORTUNE, 0.5714286F, 3)))))
                    .pool(LootPool.builder()
                            .with(ItemEntry.builder(drop))));
        }

        public LootTable.Builder barrelDrops(Block drop) {
            return LootTable.builder().pool(applyExplosionDecay(drop,
                            LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                                    .with(ItemEntry.builder(drop).apply(CopyNameLootFunction.builder(CopyNameLootFunction.Source.BLOCK_ENTITY))
                                            .apply(CopyNbtLootFunction.builder(ContextLootNbtProvider.BLOCK_ENTITY)
                                                    .withOperation("Lock", "BlockEntityTag.Lock")
                                                    .withOperation("LootTable", "BlockEntityTag.LootTable")
                                                    .withOperation("LootTableSeed", "BlockEntityTag.LootTableSeed")
                                            )
                                            .apply(SetContentsLootFunction.builder(WKBlockEntityTypes.BREWING_BARREL).withEntry(DynamicEntry.builder(ShulkerBoxBlock.CONTENTS)))
                                    )
                    )
            );
        }

        public LootTable.Builder ovenDrops(Block drop) {
            return LootTable.builder().pool(applyExplosionDecay(drop,
                            LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                                    .with(ItemEntry.builder(drop).apply(CopyNameLootFunction.builder(CopyNameLootFunction.Source.BLOCK_ENTITY))
                                            .apply(CopyNbtLootFunction.builder(ContextLootNbtProvider.BLOCK_ENTITY)
                                                    .withOperation("Lock", "BlockEntityTag.Lock")
                                                    .withOperation("LootTable", "BlockEntityTag.LootTable")
                                                    .withOperation("LootTableSeed", "BlockEntityTag.LootTableSeed")
                                            )
                                            .apply(SetContentsLootFunction.builder(WKBlockEntityTypes.WITCHES_OVEN).withEntry(DynamicEntry.builder(ShulkerBoxBlock.CONTENTS)))
                                    )
                    )
            );
        }

        public LootTable.Builder teaDrops(Block drop) {
            return LootTable.builder().pool(applyExplosionDecay(drop,
                            LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                                    .with(ItemEntry.builder(drop).apply(CopyNameLootFunction.builder(CopyNameLootFunction.Source.BLOCK_ENTITY))
                                            .apply(CopyNbtLootFunction.builder(ContextLootNbtProvider.BLOCK_ENTITY)
                                                    .withOperation("Lock", "BlockEntityTag.Lock")
                                                    .withOperation("LootTable", "BlockEntityTag.LootTable")
                                                    .withOperation("LootTableSeed", "BlockEntityTag.LootTableSeed")
                                            )
                                            .apply(SetContentsLootFunction.builder(WKBlockEntityTypes.TEAPOT).withEntry(DynamicEntry.builder(ShulkerBoxBlock.CONTENTS)))
                                    )
                    )
            );
        }

        public LootTable.Builder cauldronDrops(Block drop) {
            return LootTable.builder().pool(applyExplosionDecay(drop,
                            LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F))
                                    .with(ItemEntry.builder(drop).apply(CopyNameLootFunction.builder(CopyNameLootFunction.Source.BLOCK_ENTITY))
                                            .apply(CopyNbtLootFunction.builder(ContextLootNbtProvider.BLOCK_ENTITY)
                                                    .withOperation("Lock", "BlockEntityTag.Lock")
                                                    .withOperation("LootTable", "BlockEntityTag.LootTable")
                                                    .withOperation("LootTableSeed", "BlockEntityTag.LootTableSeed")
                                            )
                                            .apply(SetContentsLootFunction.builder(WKBlockEntityTypes.WITCHES_CAULDRON).withEntry(DynamicEntry.builder(ShulkerBoxBlock.CONTENTS)))
                                    )
                    )
            );
        }
    }

    public static class EntityLoot extends SimpleFabricLootTableProvider {
        public EntityLoot(FabricDataOutput output) {
            super(output, LootContextTypes.ENTITY);
        }

        @Override
        public void accept(BiConsumer<Identifier, LootTable.Builder> identifierBuilderBiConsumer) {

        }
    }


}
