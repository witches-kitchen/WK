package cf.witcheskitchen.datagen;

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


            //WK SEEDS
            //getOrCreateTagBuilder(WKTags.SEED_AMARANTH).add(WKItems.AMARANTH_SEEDS, WKItems.AMARANTH_SWEETBERRY_SEEDS, WKItems.AMARANTH_TORCH_SEEDS, WKItems.AMARANTH_SUNDEW_SEEDS, WKItems.AMARANTH_CREEPER_SEEDS, WKItems.AMARANTH_VIRIDIAN_SEEDS, WKItems.AMARANTH_GRISELIN_SEEDS, WKItems.AMARANTH_CERISE_SEEDS, WKItems.AMARANTH_DARK_PASSION_SEEDS, WKItems.AMARANTH_FIREBIRD_SEEDS);
            //getOrCreateTagBuilder(WKTags.SEED_BELLADONNA).add(WKItems.BELLADONNA_SEEDS, WKItems.BELLADONNA_GLOW_SEEDS, WKItems.BELLADONNA_NOCTURNAL_SEEDS);
            //getOrCreateTagBuilder(WKTags.SEED_IRIS).add(WKItems.IRIS_SEEDS, WKItems.IRIS_BLEEDING_HEART_SEEDS, WKItems.IRIS_DEEP_SEA_SEEDS, WKItems.IRIS_OCEAN_SEEDS);
           // getOrCreateTagBuilder(WKTags.SEED_GINGER);
           // getOrCreateTagBuilder(WKTags.SEED_CAMELLIA).add(WKItems.CAMELLIA_SEEDS, WKItems.CAMELLIA_BISQUE_SEEDS, WKItems.CAMELLIA_BUTTERCREAM_SEEDS, WKItems.CAMELLIA_DEEP_LOVE_SEEDS, WKItems.CAMELLIA_FLINT_SEEDS);
           // getOrCreateTagBuilder(WKTags.SEED_ST_JOHNS_WORT).add(WKItems.ST_JOHNS_WORT_SEEDS);
            //getOrCreateTagBuilder(WKTags.SEED_SANGUINARY).add(WKItems.SANGUINARY_SEEDS, WKItems.SANGUINARY_AUREOLIN_SEEDS, WKItems.SANGUINARY_BLOSSOM, WKItems.SANGUINARY_BLUSHING_SEEDS, WKItems.SANGUINARY_MADDER_SEEDS, WKItems.SANGUINARY_MEADOW_SEEDS, WKItems.SANGUINARY_SUNSET_SEEDS);
           // getOrCreateTagBuilder(WKTags.SEED_WORMWOOD).add(WKItems.WORMWOOD_SEEDS);
           // getOrCreateTagBuilder(WKTags.SEED_FOXGLOVE).add(WKItems.FOXGLOVE_AURULENT_SEEDS, WKItems.FOXGLOVE_BABYS_DRESS_SEEDS, WKItems.FOXGLOVE_BASTARD_AMBER_SEEDS, WKItems.FOXGLOVE_BLUSH_SEEDS, WKItems.FOXGLOVE_CALAMINE_SEEDS, WKItems.FOXGLOVE_COWS_CREAM_SEEDS, WKItems.FOXGLOVE_FIERY_FIELD_SEEDS, WKItems.FOXGLOVE_IANTHINE_SEEDS, WKItems.FOXGLOVE_IVORY_SEEDS, WKItems.FOXGLOVE_LOVE_SEEDS, WKItems.FOXGLOVE_LOVELY_MORNING_SEEDS, WKItems.FOXGLOVE_MAIDENS_PINK_SEEDS, WKItems.FOXGLOVE_NETHERINE_SEEDS, WKItems.FOXGLOVE_NIVEOUS_SEEDS, WKItems.FOXGLOVE_MORNING_FIELD_SEEDS, WKItems.FOXGLOVE_PASSION_SEEDS, WKItems.FOXGLOVE_PURITY_SEEDS, WKItems.FOXGLOVE_PURPUREA_SEEDS, WKItems.FOXGLOVE_QUEENS_HAT_SEEDS, WKItems.FOXGLOVE_ROYAL_BLANKET_SEEDS, WKItems.FOXGLOVE_SANDSTONE_TEMPLE_SEEDS, WKItems.FOXGLOVE_SIDHE_MIST_SEEDS, WKItems.FOXGLOVE_SIGHE_GOWN_SEEDS, WKItems.FOXGLOVE_SMALT_SEEDS, WKItems.FOXGLOVE_SEEDS, WKItems.FOXGLOVE_STROLL_SEEDS, WKItems.FOXGLOVE_SUNDROP_SEEDS, WKItems.FOXGLOVE_SUNGLOW_SEEDS, WKItems.FOXGLOVE_TRANQUIL_EVENING_SEEDS);
           // getOrCreateTagBuilder(WKTags.SEED_CONEFLOWER).add(WKItems.CONEFLOWER_SEEDS, WKItems.CONEFLOWER_COMPANY_SEEDS, WKItems.CONEFLOWER_DANCING_LADIES_SEEDS, WKItems.CONEFLOWER_FLAME_SEEDS, WKItems.CONEFLOWER_FLEECE_SEEDS, WKItems.CONEFLOWER_GILDED_SEEDS, WKItems.CONEFLOWER_LADYS_WISH_SEEDS, WKItems.CONEFLOWER_MASQUERADE_SEEDS, WKItems.CONEFLOWER_MORNING_MIST_SEEDS, WKItems.CONEFLOWER_NETHER_SEEDS, WKItems.CONEFLOWER_PARTY_BLEND_SEEDS, WKItems.CONEFLOWER_QUEENS_DESIRE_SEEDS, WKItems.CONEFLOWER_ROSE_DRESS_SEEDS, WKItems.CONEFLOWER_SUITOR_SEEDS, WKItems.CONEFLOWER_SUNGLOW_SEEDS, WKItems.CONEFLOWER_VIOLET_SEEDS);
           // getOrCreateTagBuilder(WKTags.SEED_CHAMOMILE).add(WKItems.CHAMOMILE_SEEDS, WKItems.CHAMOMILE_DYEWORKS_SEEDS, WKItems.CHAMOMILE_STARLETT_SEEDS, WKItems.CHAMOMILE_VIRESCENT_SEEDS);
           // getOrCreateTagBuilder(WKTags.SEED_BRIAR).add(WKItems.BRIAR_SEEDS);
           // getOrCreateTagBuilder(WKTags.SEED_MINT);
           // getOrCreateTagBuilder(WKTags.SEED_BLACKBERRY);



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
