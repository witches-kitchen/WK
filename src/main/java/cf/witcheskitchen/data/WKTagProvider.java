package cf.witcheskitchen.data;

import cf.witcheskitchen.common.registry.WKBlocks;
import cf.witcheskitchen.common.registry.WKEntityTypes;
import cf.witcheskitchen.common.registry.WKItems;
import cf.witcheskitchen.common.registry.WKTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.ItemTags;
import org.jetbrains.annotations.Nullable;

public class WKTagProvider {
    public static class WKBlockTags extends FabricTagProvider.BlockTagProvider {
        public WKBlockTags(FabricDataGenerator dataGenerator) {
            super(dataGenerator);
        }

        @Override
        protected void generateTags() {
            //WK
            getOrCreateTagBuilder(WKTags.HEATS_CAULDRON).add(Blocks.FIRE, Blocks.SOUL_FIRE, Blocks.MAGMA_BLOCK, Blocks.LAVA, Blocks.CAMPFIRE, Blocks.SOUL_CAMPFIRE);

            //VANILLA
            getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(WKBlocks.IRON_WITCHES_CAULDRON, WKBlocks.COPPER_WITCHES_OVEN, WKBlocks.EXPOSED_COPPER_WITCHES_OVEN, WKBlocks.WEATHERED_COPPER_WITCHES_OVEN, WKBlocks.OXIDIZED_COPPER_WITCHES_OVEN, WKBlocks.WAXED_COPPER_WITCHES_OVEN, WKBlocks.WAXED_EXPOSED_COPPER_WITCHES_OVEN, WKBlocks.WAXED_WEATHERED_COPPER_WITCHES_OVEN, WKBlocks.WAXED_OXIDIZED_COPPER_WITCHES_OVEN, WKBlocks.OAK_BREWING_BARREL, WKBlocks.SPRUCE_BREWING_BARREL, WKBlocks.BIRCH_BREWING_BARREL, WKBlocks.JUNGLE_BREWING_BARREL, WKBlocks.ACACIA_BREWING_BARREL, WKBlocks.DARK_OAK_BREWING_BARREL, WKBlocks.CRIMSON_BREWING_BARREL, WKBlocks.WARPED_BREWING_BARREL);
            getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(WKBlocks.BLACKTHORN_LOG, WKBlocks.BLACKTHORN_WOOD, WKBlocks.BLACKTHORN_STAIRS, WKBlocks.BLACKTHORN_SLAB, WKBlocks.BLACKTHORN_PLANKS, WKBlocks.STRIPPED_BLACKTHORN_LOG, WKBlocks.STRIPPED_BLACKTHORN_WOOD, WKBlocks.ELDER_LOG, WKBlocks.ELDER_WOOD, WKBlocks.ELDER_STAIRS, WKBlocks.ELDER_SLAB, WKBlocks.ELDER_PLANKS, WKBlocks.STRIPPED_ELDER_LOG, WKBlocks.STRIPPED_ELDER_WOOD, WKBlocks.HAWTHORN_LOG, WKBlocks.HAWTHORN_WOOD, WKBlocks.HAWTHORN_STAIRS, WKBlocks.HAWTHORN_SLAB, WKBlocks.HAWTHORN_PLANKS, WKBlocks.STRIPPED_HAWTHORN_LOG, WKBlocks.STRIPPED_HAWTHORN_WOOD, WKBlocks.JUNIPER_LOG, WKBlocks.JUNIPER_WOOD, WKBlocks.JUNIPER_STAIRS, WKBlocks.JUNIPER_SLAB, WKBlocks.JUNIPER_PLANKS, WKBlocks.STRIPPED_JUNIPER_LOG, WKBlocks.STRIPPED_JUNIPER_WOOD, WKBlocks.ROWAN_LOG, WKBlocks.ROWAN_WOOD, WKBlocks.ROWAN_STAIRS, WKBlocks.ROWAN_SLAB, WKBlocks.ROWAN_PLANKS, WKBlocks.STRIPPED_ROWAN_LOG, WKBlocks.STRIPPED_ROWAN_WOOD, WKBlocks.SUMAC_LOG, WKBlocks.SUMAC_WOOD, WKBlocks.SUMAC_STAIRS, WKBlocks.SUMAC_SLAB, WKBlocks.SUMAC_PLANKS, WKBlocks.STRIPPED_SUMAC_LOG, WKBlocks.STRIPPED_SUMAC_WOOD);
            getOrCreateTagBuilder(BlockTags.LEAVES).add(WKBlocks.BLACKTHORN_LEAVES, WKBlocks.ELDER_LEAVES, WKBlocks.HAWTHORN_LEAVES, WKBlocks.JUNIPER_LEAVES, WKBlocks.ROWAN_LEAVES, WKBlocks.SUMAC_LEAVES);
            getOrCreateTagBuilder(BlockTags.LOGS).add(WKBlocks.BLACKTHORN_LOG, WKBlocks.BLACKTHORN_WOOD, WKBlocks.STRIPPED_BLACKTHORN_LOG, WKBlocks.STRIPPED_BLACKTHORN_WOOD, WKBlocks.ELDER_LOG, WKBlocks.ELDER_WOOD, WKBlocks.STRIPPED_ELDER_LOG, WKBlocks.STRIPPED_ELDER_WOOD, WKBlocks.HAWTHORN_LOG, WKBlocks.HAWTHORN_WOOD, WKBlocks.STRIPPED_HAWTHORN_LOG, WKBlocks.STRIPPED_HAWTHORN_WOOD, WKBlocks.JUNIPER_LOG, WKBlocks.JUNIPER_WOOD, WKBlocks.STRIPPED_JUNIPER_LOG, WKBlocks.STRIPPED_JUNIPER_WOOD, WKBlocks.ROWAN_LOG, WKBlocks.ROWAN_WOOD, WKBlocks.STRIPPED_ROWAN_LOG, WKBlocks.STRIPPED_ROWAN_WOOD, WKBlocks.SUMAC_LOG, WKBlocks.SUMAC_WOOD, WKBlocks.STRIPPED_SUMAC_LOG, WKBlocks.STRIPPED_SUMAC_WOOD);
            getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN).add(WKBlocks.BLACKTHORN_LOG, WKBlocks.BLACKTHORN_WOOD, WKBlocks.STRIPPED_BLACKTHORN_LOG, WKBlocks.STRIPPED_BLACKTHORN_WOOD, WKBlocks.ELDER_LOG, WKBlocks.ELDER_WOOD, WKBlocks.STRIPPED_ELDER_LOG, WKBlocks.STRIPPED_ELDER_WOOD, WKBlocks.HAWTHORN_LOG, WKBlocks.HAWTHORN_WOOD, WKBlocks.STRIPPED_HAWTHORN_LOG, WKBlocks.STRIPPED_HAWTHORN_WOOD, WKBlocks.JUNIPER_LOG, WKBlocks.JUNIPER_WOOD, WKBlocks.STRIPPED_JUNIPER_LOG, WKBlocks.STRIPPED_JUNIPER_WOOD, WKBlocks.ROWAN_LOG, WKBlocks.ROWAN_WOOD, WKBlocks.STRIPPED_ROWAN_LOG, WKBlocks.STRIPPED_ROWAN_WOOD, WKBlocks.SUMAC_LOG, WKBlocks.SUMAC_WOOD, WKBlocks.STRIPPED_SUMAC_LOG, WKBlocks.STRIPPED_SUMAC_WOOD);
            getOrCreateTagBuilder(BlockTags.SAPLINGS).add(WKBlocks.BLACKTHORN_SAPLING, WKBlocks.ELDER_SAPLING, WKBlocks.HAWTHORN_SAPLING, WKBlocks.JUNIPER_SAPLING, WKBlocks.ROWAN_SAPLING, WKBlocks.SUMAC_SAPLING);
            getOrCreateTagBuilder(BlockTags.PLANKS).add(WKBlocks.BLACKTHORN_PLANKS, WKBlocks.ELDER_PLANKS, WKBlocks.HAWTHORN_PLANKS, WKBlocks.JUNIPER_SAPLING, WKBlocks.ROWAN_PLANKS, WKBlocks.SUMAC_PLANKS);
        }
    }

    public static class WKItemTags extends FabricTagProvider.ItemTagProvider {
        public WKItemTags(FabricDataGenerator dataGenerator, @Nullable BlockTagProvider blockTagProvider) {
            super(dataGenerator, blockTagProvider);
        }

        @Override
        protected void generateTags() {
            //WK
            getOrCreateTagBuilder(WKTags.BARREL_BLACKLIST).add(Items.AIR);
            getOrCreateTagBuilder(WKTags.OVEN_BLACKLIST).add(Items.AIR);
            getOrCreateTagBuilder(WKTags.TEA_BLACKLIST).add(Items.AIR);
            getOrCreateTagBuilder(WKTags.RESETS_CAULDRON).add(Items.CHARCOAL);
            getOrCreateTagBuilder(WKTags.BARREL_BLACKLIST).add(Items.AIR);
            getOrCreateTagBuilder(WKTags.VALID_BREW_ITEM).add(Items.BONE, Items.SUGAR, WKBlocks.SALT_BLOCK.asItem(), WKItems.AMARANTH_SPRIG, WKItems.WORMWOOD_SPRIG, WKItems.BELLADONNA_BLOSSOM);

            //VANILLA
            getOrCreateTagBuilder(ItemTags.LEAVES).add(WKBlocks.BLACKTHORN_LEAVES.asItem(), WKBlocks.ELDER_LEAVES.asItem(), WKBlocks.HAWTHORN_LEAVES.asItem(), WKBlocks.JUNIPER_LEAVES.asItem(), WKBlocks.ROWAN_LEAVES.asItem(), WKBlocks.SUMAC_LEAVES.asItem());
            getOrCreateTagBuilder(ItemTags.LOGS).add(WKBlocks.BLACKTHORN_LOG.asItem(), WKBlocks.BLACKTHORN_WOOD.asItem(), WKBlocks.STRIPPED_BLACKTHORN_LOG.asItem(), WKBlocks.STRIPPED_BLACKTHORN_WOOD.asItem(), WKBlocks.ELDER_LOG.asItem(), WKBlocks.ELDER_WOOD.asItem(), WKBlocks.STRIPPED_ELDER_LOG.asItem(), WKBlocks.STRIPPED_ELDER_WOOD.asItem(), WKBlocks.HAWTHORN_LOG.asItem(), WKBlocks.HAWTHORN_WOOD.asItem(), WKBlocks.STRIPPED_HAWTHORN_LOG.asItem(), WKBlocks.STRIPPED_HAWTHORN_WOOD.asItem(), WKBlocks.JUNIPER_LOG.asItem(), WKBlocks.JUNIPER_WOOD.asItem(), WKBlocks.STRIPPED_JUNIPER_LOG.asItem(), WKBlocks.STRIPPED_JUNIPER_WOOD.asItem(), WKBlocks.ROWAN_LOG.asItem(), WKBlocks.ROWAN_WOOD.asItem(), WKBlocks.STRIPPED_ROWAN_LOG.asItem(), WKBlocks.STRIPPED_ROWAN_WOOD.asItem(), WKBlocks.SUMAC_LOG.asItem(), WKBlocks.SUMAC_WOOD.asItem(), WKBlocks.STRIPPED_SUMAC_LOG.asItem(), WKBlocks.STRIPPED_SUMAC_WOOD.asItem());
            getOrCreateTagBuilder(ItemTags.LOGS_THAT_BURN).add(WKBlocks.BLACKTHORN_LOG.asItem(), WKBlocks.BLACKTHORN_WOOD.asItem(), WKBlocks.STRIPPED_BLACKTHORN_LOG.asItem(), WKBlocks.STRIPPED_BLACKTHORN_WOOD.asItem(), WKBlocks.ELDER_LOG.asItem(), WKBlocks.ELDER_WOOD.asItem(), WKBlocks.STRIPPED_ELDER_LOG.asItem(), WKBlocks.STRIPPED_ELDER_WOOD.asItem(), WKBlocks.HAWTHORN_LOG.asItem(), WKBlocks.HAWTHORN_WOOD.asItem(), WKBlocks.STRIPPED_HAWTHORN_LOG.asItem(), WKBlocks.STRIPPED_HAWTHORN_WOOD.asItem(), WKBlocks.JUNIPER_LOG.asItem(), WKBlocks.JUNIPER_WOOD.asItem(), WKBlocks.STRIPPED_JUNIPER_LOG.asItem(), WKBlocks.STRIPPED_JUNIPER_WOOD.asItem(), WKBlocks.ROWAN_LOG.asItem(), WKBlocks.ROWAN_WOOD.asItem(), WKBlocks.STRIPPED_ROWAN_LOG.asItem(), WKBlocks.STRIPPED_ROWAN_WOOD.asItem(), WKBlocks.SUMAC_LOG.asItem(), WKBlocks.SUMAC_WOOD.asItem(), WKBlocks.STRIPPED_SUMAC_LOG.asItem(), WKBlocks.STRIPPED_SUMAC_WOOD.asItem());
            getOrCreateTagBuilder(ItemTags.SAPLINGS).add(WKBlocks.BLACKTHORN_SAPLING.asItem(), WKBlocks.ELDER_SAPLING.asItem(), WKBlocks.HAWTHORN_SAPLING.asItem(), WKBlocks.JUNIPER_SAPLING.asItem(), WKBlocks.ROWAN_SAPLING.asItem(), WKBlocks.SUMAC_SAPLING.asItem());
            getOrCreateTagBuilder(ItemTags.PLANKS).add(WKBlocks.BLACKTHORN_PLANKS.asItem(), WKBlocks.ELDER_PLANKS.asItem(), WKBlocks.HAWTHORN_PLANKS.asItem(), WKBlocks.JUNIPER_SAPLING.asItem(), WKBlocks.ROWAN_PLANKS.asItem(), WKBlocks.SUMAC_PLANKS.asItem());
        }
    }

    public static class WKEntityTypeTags extends FabricTagProvider.EntityTypeTagProvider {
        public WKEntityTypeTags(FabricDataGenerator dataGenerator) {
            super(dataGenerator);
        }

        @Override
        protected void generateTags() {
            //WK
            getOrCreateTagBuilder(WKTags.GHOST).add(WKEntityTypes.CUSITH, WKEntityTypes.CHURCH_GRIM);
            getOrCreateTagBuilder(WKTags.GREATER_DEMON);
            getOrCreateTagBuilder(WKTags.LEFT_HAND_WITCH_SUMMON);
            getOrCreateTagBuilder(WKTags.LESSER_DEMON);
            getOrCreateTagBuilder(WKTags.RIGHT_HAND_WITCH_SUMMON);
            getOrCreateTagBuilder(WKTags.TAGLOCK_BLACKLIST).add(EntityType.ENDER_DRAGON, EntityType.WITHER);

            //VANILLA
        }
    }
}
