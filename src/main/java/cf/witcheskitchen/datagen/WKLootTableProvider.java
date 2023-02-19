package cf.witcheskitchen.datagen;

import cf.witcheskitchen.common.registry.WKBlockEntityTypes;
import cf.witcheskitchen.common.registry.WKBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.DynamicEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.CopyNameLootFunction;
import net.minecraft.loot.function.CopyNbtLootFunction;
import net.minecraft.loot.function.SetContentsLootFunction;
import net.minecraft.loot.provider.nbt.ContextLootNbtProvider;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

import static net.minecraft.data.server.BlockLootTableGenerator.addSurvivesExplosionCondition;

public class WKLootTableProvider {

    public static class BlockLoot extends FabricBlockLootTableProvider {
        private static final float[] SAPLING_DROP_CHANCE = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};

        protected BlockLoot(FabricDataGenerator dataGenerator) {
            super(dataGenerator);
        }

        @Override
        protected void generateBlockLootTables() {
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

            this.addDrop(WKBlocks.BLACKTHORN_SAPLING);
            this.addDrop(WKBlocks.ELDER_SAPLING);
            this.addDrop(WKBlocks.HAWTHORN_SAPLING);
            this.addDrop(WKBlocks.JUNIPER_SAPLING);
            this.addDrop(WKBlocks.ROWAN_SAPLING);
            this.addDrop(WKBlocks.SUMAC_SAPLING);

            this.addDrop(WKBlocks.BLACKTHORN_LOG);
            this.addDrop(WKBlocks.ELDER_LOG);
            this.addDrop(WKBlocks.HAWTHORN_LOG);
            this.addDrop(WKBlocks.JUNIPER_LOG);
            this.addDrop(WKBlocks.ROWAN_LOG);
            this.addDrop(WKBlocks.SUMAC_LOG);

            this.addDrop(WKBlocks.BLACKTHORN_PLANKS);
            this.addDrop(WKBlocks.ELDER_PLANKS);
            this.addDrop(WKBlocks.HAWTHORN_PLANKS);
            this.addDrop(WKBlocks.JUNIPER_PLANKS);
            this.addDrop(WKBlocks.ROWAN_PLANKS);
            this.addDrop(WKBlocks.SUMAC_PLANKS);

            this.addDrop(WKBlocks.BLACKTHORN_WOOD);
            this.addDrop(WKBlocks.ELDER_WOOD);
            this.addDrop(WKBlocks.HAWTHORN_WOOD);
            this.addDrop(WKBlocks.JUNIPER_WOOD);
            this.addDrop(WKBlocks.ROWAN_WOOD);
            this.addDrop(WKBlocks.SUMAC_WOOD);

            leavesDrop(WKBlocks.BLACKTHORN_LEAVES, WKBlocks.BLACKTHORN_SAPLING, SAPLING_DROP_CHANCE);
            leavesDrop(WKBlocks.ELDER_LEAVES, WKBlocks.ELDER_SAPLING, SAPLING_DROP_CHANCE);
            leavesDrop(WKBlocks.HAWTHORN_LEAVES, WKBlocks.HAWTHORN_SAPLING, SAPLING_DROP_CHANCE);
            leavesDrop(WKBlocks.JUNIPER_LEAVES, WKBlocks.JUNIPER_SAPLING, SAPLING_DROP_CHANCE);
            leavesDrop(WKBlocks.ROWAN_LEAVES, WKBlocks.ROWAN_SAPLING, SAPLING_DROP_CHANCE);
            leavesDrop(WKBlocks.SUMAC_LEAVES, WKBlocks.SUMAC_SAPLING, SAPLING_DROP_CHANCE);

            slabDrops(WKBlocks.BLACKTHORN_SLAB);
            slabDrops(WKBlocks.ELDER_SLAB);
            slabDrops(WKBlocks.HAWTHORN_SLAB);
            slabDrops(WKBlocks.JUNIPER_SLAB);
            slabDrops(WKBlocks.ROWAN_SLAB);
            slabDrops(WKBlocks.SUMAC_SLAB);

            slabDrops(WKBlocks.BLACKTHORN_STAIRS);
            slabDrops(WKBlocks.ELDER_STAIRS);
            slabDrops(WKBlocks.HAWTHORN_STAIRS);
            slabDrops(WKBlocks.JUNIPER_STAIRS);
            slabDrops(WKBlocks.ROWAN_STAIRS);
            slabDrops(WKBlocks.SUMAC_STAIRS);

            this.addDrop(WKBlocks.STRIPPED_BLACKTHORN_WOOD);
            this.addDrop(WKBlocks.STRIPPED_ELDER_WOOD);
            this.addDrop(WKBlocks.STRIPPED_HAWTHORN_WOOD);
            this.addDrop(WKBlocks.STRIPPED_JUNIPER_WOOD);
            this.addDrop(WKBlocks.STRIPPED_ROWAN_WOOD);
            this.addDrop(WKBlocks.STRIPPED_SUMAC_WOOD);

            this.addDrop(WKBlocks.STRIPPED_BLACKTHORN_LOG);
            this.addDrop(WKBlocks.STRIPPED_ELDER_LOG);
            this.addDrop(WKBlocks.STRIPPED_HAWTHORN_LOG);
            this.addDrop(WKBlocks.STRIPPED_JUNIPER_LOG);
            this.addDrop(WKBlocks.STRIPPED_ROWAN_LOG);
            this.addDrop(WKBlocks.STRIPPED_SUMAC_LOG);
        }
    }

    public static class EntityLoot extends SimpleFabricLootTableProvider {
        public EntityLoot(FabricDataGenerator dataGenerator) {
            super(dataGenerator, LootContextTypes.ENTITY);
        }

        @Override
        public void accept(BiConsumer<Identifier, LootTable.Builder> identifierBuilderBiConsumer) {

        }
    }

    public static LootTable.Builder barrelDrops(Block drop) {
        return LootTable.builder().pool(addSurvivesExplosionCondition(drop,
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

    public static LootTable.Builder ovenDrops(Block drop) {
        return LootTable.builder().pool(addSurvivesExplosionCondition(drop,
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

    public static LootTable.Builder cauldronDrops(Block drop) {
        return LootTable.builder().pool(addSurvivesExplosionCondition(drop,
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
