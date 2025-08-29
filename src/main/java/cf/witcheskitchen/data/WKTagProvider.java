package cf.witcheskitchen.data;

import cf.witcheskitchen.common.registry.WKBlocks;
import cf.witcheskitchen.common.registry.WKEntityTypes;
import cf.witcheskitchen.common.registry.WKItems;
import cf.witcheskitchen.common.registry.WKTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import java.util.concurrent.CompletableFuture;

public class WKTagProvider {
    public static class WKBlockTags extends FabricTagProvider.BlockTagProvider {
        public WKBlockTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider arg) {
            //WK
            builder(WKTags.HEATS_CAULDRON, Blocks.FIRE, Blocks.SOUL_FIRE, Blocks.MAGMA_BLOCK, Blocks.LAVA, Blocks.CAMPFIRE, Blocks.SOUL_CAMPFIRE);

            //VANILLA
            builder(BlockTags.MINEABLE_WITH_PICKAXE, WKBlocks.IRON_WITCHES_CAULDRON, WKBlocks.COPPER_WITCHES_OVEN, WKBlocks.EXPOSED_COPPER_WITCHES_OVEN, WKBlocks.WEATHERED_COPPER_WITCHES_OVEN, WKBlocks.OXIDIZED_COPPER_WITCHES_OVEN, WKBlocks.WAXED_COPPER_WITCHES_OVEN, WKBlocks.WAXED_EXPOSED_COPPER_WITCHES_OVEN, WKBlocks.WAXED_WEATHERED_COPPER_WITCHES_OVEN, WKBlocks.WAXED_OXIDIZED_COPPER_WITCHES_OVEN, WKBlocks.OAK_BREWING_BARREL, WKBlocks.SPRUCE_BREWING_BARREL, WKBlocks.BIRCH_BREWING_BARREL, WKBlocks.JUNGLE_BREWING_BARREL, WKBlocks.ACACIA_BREWING_BARREL, WKBlocks.DARK_OAK_BREWING_BARREL, WKBlocks.CRIMSON_BREWING_BARREL, WKBlocks.WARPED_BREWING_BARREL);
            builder(BlockTags.MINEABLE_WITH_AXE, WKBlocks.BLACKTHORN_LOG, WKBlocks.BLACKTHORN_WOOD, WKBlocks.BLACKTHORN_STAIRS, WKBlocks.BLACKTHORN_SLAB, WKBlocks.BLACKTHORN_PLANKS, WKBlocks.STRIPPED_BLACKTHORN_LOG, WKBlocks.STRIPPED_BLACKTHORN_WOOD, WKBlocks.ELDER_LOG, WKBlocks.ELDER_WOOD, WKBlocks.ELDER_STAIRS, WKBlocks.ELDER_SLAB, WKBlocks.ELDER_PLANKS, WKBlocks.STRIPPED_ELDER_LOG, WKBlocks.STRIPPED_ELDER_WOOD, WKBlocks.HAWTHORN_LOG, WKBlocks.HAWTHORN_WOOD, WKBlocks.HAWTHORN_STAIRS, WKBlocks.HAWTHORN_SLAB, WKBlocks.HAWTHORN_PLANKS, WKBlocks.STRIPPED_HAWTHORN_LOG, WKBlocks.STRIPPED_HAWTHORN_WOOD, WKBlocks.JUNIPER_LOG, WKBlocks.JUNIPER_WOOD, WKBlocks.JUNIPER_STAIRS, WKBlocks.JUNIPER_SLAB, WKBlocks.JUNIPER_PLANKS, WKBlocks.STRIPPED_JUNIPER_LOG, WKBlocks.STRIPPED_JUNIPER_WOOD, WKBlocks.ROWAN_LOG, WKBlocks.ROWAN_WOOD, WKBlocks.ROWAN_STAIRS, WKBlocks.ROWAN_SLAB, WKBlocks.ROWAN_PLANKS, WKBlocks.STRIPPED_ROWAN_LOG, WKBlocks.STRIPPED_ROWAN_WOOD, WKBlocks.SUMAC_LOG, WKBlocks.SUMAC_WOOD, WKBlocks.SUMAC_STAIRS, WKBlocks.SUMAC_SLAB, WKBlocks.SUMAC_PLANKS, WKBlocks.STRIPPED_SUMAC_LOG, WKBlocks.STRIPPED_SUMAC_WOOD);
            builder(BlockTags.LEAVES, WKBlocks.BLACKTHORN_LEAVES, WKBlocks.ELDER_LEAVES, WKBlocks.HAWTHORN_LEAVES, WKBlocks.JUNIPER_LEAVES, WKBlocks.ROWAN_LEAVES, WKBlocks.SUMAC_LEAVES);
            builder(BlockTags.LOGS, WKBlocks.BLACKTHORN_LOG, WKBlocks.BLACKTHORN_WOOD, WKBlocks.STRIPPED_BLACKTHORN_LOG, WKBlocks.STRIPPED_BLACKTHORN_WOOD, WKBlocks.ELDER_LOG, WKBlocks.ELDER_WOOD, WKBlocks.STRIPPED_ELDER_LOG, WKBlocks.STRIPPED_ELDER_WOOD, WKBlocks.HAWTHORN_LOG, WKBlocks.HAWTHORN_WOOD, WKBlocks.STRIPPED_HAWTHORN_LOG, WKBlocks.STRIPPED_HAWTHORN_WOOD, WKBlocks.JUNIPER_LOG, WKBlocks.JUNIPER_WOOD, WKBlocks.STRIPPED_JUNIPER_LOG, WKBlocks.STRIPPED_JUNIPER_WOOD, WKBlocks.ROWAN_LOG, WKBlocks.ROWAN_WOOD, WKBlocks.STRIPPED_ROWAN_LOG, WKBlocks.STRIPPED_ROWAN_WOOD, WKBlocks.SUMAC_LOG, WKBlocks.SUMAC_WOOD, WKBlocks.STRIPPED_SUMAC_LOG, WKBlocks.STRIPPED_SUMAC_WOOD);
            builder(BlockTags.LOGS_THAT_BURN, WKBlocks.BLACKTHORN_LOG, WKBlocks.BLACKTHORN_WOOD, WKBlocks.STRIPPED_BLACKTHORN_LOG, WKBlocks.STRIPPED_BLACKTHORN_WOOD, WKBlocks.ELDER_LOG, WKBlocks.ELDER_WOOD, WKBlocks.STRIPPED_ELDER_LOG, WKBlocks.STRIPPED_ELDER_WOOD, WKBlocks.HAWTHORN_LOG, WKBlocks.HAWTHORN_WOOD, WKBlocks.STRIPPED_HAWTHORN_LOG, WKBlocks.STRIPPED_HAWTHORN_WOOD, WKBlocks.JUNIPER_LOG, WKBlocks.JUNIPER_WOOD, WKBlocks.STRIPPED_JUNIPER_LOG, WKBlocks.STRIPPED_JUNIPER_WOOD, WKBlocks.ROWAN_LOG, WKBlocks.ROWAN_WOOD, WKBlocks.STRIPPED_ROWAN_LOG, WKBlocks.STRIPPED_ROWAN_WOOD, WKBlocks.SUMAC_LOG, WKBlocks.SUMAC_WOOD, WKBlocks.STRIPPED_SUMAC_LOG, WKBlocks.STRIPPED_SUMAC_WOOD);
            builder(BlockTags.SAPLINGS, WKBlocks.BLACKTHORN_SAPLING, WKBlocks.ELDER_SAPLING, WKBlocks.HAWTHORN_SAPLING, WKBlocks.JUNIPER_SAPLING, WKBlocks.ROWAN_SAPLING, WKBlocks.SUMAC_SAPLING);
            builder(BlockTags.PLANKS, WKBlocks.BLACKTHORN_PLANKS, WKBlocks.ELDER_PLANKS, WKBlocks.HAWTHORN_PLANKS, WKBlocks.JUNIPER_SAPLING, WKBlocks.ROWAN_PLANKS, WKBlocks.SUMAC_PLANKS);
        }

        private void builder(TagKey<Block> tag, Block... blocks) {
            var builder = builder(tag);
            for (Block block : blocks) {
                builder.add(block.builtInRegistryHolder().key());
            }
        }
    }

    public static class WKItemTags extends FabricTagProvider.ItemTagProvider {
        public WKItemTags(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(dataOutput, completableFuture, new WKTagProvider.WKBlockTags(dataOutput, completableFuture));
        }

        @Override
        protected void addTags(HolderLookup.Provider arg) {
            //WK
            builder(WKTags.BARREL_BLACKLIST, Items.AIR);
            builder(WKTags.OVEN_BLACKLIST, Items.AIR);
            builder(WKTags.TEA_BLACKLIST, Items.AIR);
            builder(WKTags.RESETS_CAULDRON, Items.CHARCOAL);
            builder(WKTags.BARREL_BLACKLIST, Items.AIR);
            builder(WKTags.VALID_BREW_ITEM, Items.BONE, Items.SUGAR, WKBlocks.SALT_BLOCK.asItem(), WKItems.AMARANTH_SPRIG, WKItems.WORMWOOD_SPRIG, WKItems.BELLADONNA_BLOSSOM);

            //VANILLA
            builder(ItemTags.LEAVES, WKBlocks.BLACKTHORN_LEAVES.asItem(), WKBlocks.ELDER_LEAVES.asItem(), WKBlocks.HAWTHORN_LEAVES.asItem(), WKBlocks.JUNIPER_LEAVES.asItem(), WKBlocks.ROWAN_LEAVES.asItem(), WKBlocks.SUMAC_LEAVES.asItem());
            builder(ItemTags.LOGS, WKBlocks.BLACKTHORN_LOG.asItem(), WKBlocks.BLACKTHORN_WOOD.asItem(), WKBlocks.STRIPPED_BLACKTHORN_LOG.asItem(), WKBlocks.STRIPPED_BLACKTHORN_WOOD.asItem(), WKBlocks.ELDER_LOG.asItem(), WKBlocks.ELDER_WOOD.asItem(), WKBlocks.STRIPPED_ELDER_LOG.asItem(), WKBlocks.STRIPPED_ELDER_WOOD.asItem(), WKBlocks.HAWTHORN_LOG.asItem(), WKBlocks.HAWTHORN_WOOD.asItem(), WKBlocks.STRIPPED_HAWTHORN_LOG.asItem(), WKBlocks.STRIPPED_HAWTHORN_WOOD.asItem(), WKBlocks.JUNIPER_LOG.asItem(), WKBlocks.JUNIPER_WOOD.asItem(), WKBlocks.STRIPPED_JUNIPER_LOG.asItem(), WKBlocks.STRIPPED_JUNIPER_WOOD.asItem(), WKBlocks.ROWAN_LOG.asItem(), WKBlocks.ROWAN_WOOD.asItem(), WKBlocks.STRIPPED_ROWAN_LOG.asItem(), WKBlocks.STRIPPED_ROWAN_WOOD.asItem(), WKBlocks.SUMAC_LOG.asItem(), WKBlocks.SUMAC_WOOD.asItem(), WKBlocks.STRIPPED_SUMAC_LOG.asItem(), WKBlocks.STRIPPED_SUMAC_WOOD.asItem());
            builder(ItemTags.LOGS_THAT_BURN, WKBlocks.BLACKTHORN_LOG.asItem(), WKBlocks.BLACKTHORN_WOOD.asItem(), WKBlocks.STRIPPED_BLACKTHORN_LOG.asItem(), WKBlocks.STRIPPED_BLACKTHORN_WOOD.asItem(), WKBlocks.ELDER_LOG.asItem(), WKBlocks.ELDER_WOOD.asItem(), WKBlocks.STRIPPED_ELDER_LOG.asItem(), WKBlocks.STRIPPED_ELDER_WOOD.asItem(), WKBlocks.HAWTHORN_LOG.asItem(), WKBlocks.HAWTHORN_WOOD.asItem(), WKBlocks.STRIPPED_HAWTHORN_LOG.asItem(), WKBlocks.STRIPPED_HAWTHORN_WOOD.asItem(), WKBlocks.JUNIPER_LOG.asItem(), WKBlocks.JUNIPER_WOOD.asItem(), WKBlocks.STRIPPED_JUNIPER_LOG.asItem(), WKBlocks.STRIPPED_JUNIPER_WOOD.asItem(), WKBlocks.ROWAN_LOG.asItem(), WKBlocks.ROWAN_WOOD.asItem(), WKBlocks.STRIPPED_ROWAN_LOG.asItem(), WKBlocks.STRIPPED_ROWAN_WOOD.asItem(), WKBlocks.SUMAC_LOG.asItem(), WKBlocks.SUMAC_WOOD.asItem(), WKBlocks.STRIPPED_SUMAC_LOG.asItem(), WKBlocks.STRIPPED_SUMAC_WOOD.asItem());
            builder(ItemTags.SAPLINGS, WKBlocks.BLACKTHORN_SAPLING.asItem(), WKBlocks.ELDER_SAPLING.asItem(), WKBlocks.HAWTHORN_SAPLING.asItem(), WKBlocks.JUNIPER_SAPLING.asItem(), WKBlocks.ROWAN_SAPLING.asItem(), WKBlocks.SUMAC_SAPLING.asItem());
            builder(ItemTags.PLANKS, WKBlocks.BLACKTHORN_PLANKS.asItem(), WKBlocks.ELDER_PLANKS.asItem(), WKBlocks.HAWTHORN_PLANKS.asItem(), WKBlocks.JUNIPER_SAPLING.asItem(), WKBlocks.ROWAN_PLANKS.asItem(), WKBlocks.SUMAC_PLANKS.asItem());
        }

        private void builder(TagKey<Item> tag, Item... items) {
            var builder = builder(tag);
            for (Item item : items) {
                builder.add(item.builtInRegistryHolder().key());
            }
        }
    }

    public static class WKEntityTypeTags extends FabricTagProvider.EntityTypeTagProvider {
        public WKEntityTypeTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider arg) {
            //WK
            builder(WKTags.GHOST, WKEntityTypes.CUSITH, WKEntityTypes.CHURCH_GRIM);
            builder(WKTags.GREATER_DEMON);
            builder(WKTags.LEFT_HAND_WITCH_SUMMON);
            builder(WKTags.LESSER_DEMON);
            builder(WKTags.RIGHT_HAND_WITCH_SUMMON);
            builder(WKTags.TAGLOCK_BLACKLIST, EntityType.ENDER_DRAGON, EntityType.WITHER);
            builder(WKTags.DEMONIC, WKEntityTypes.ROGGENWOLF);

            //VANILLA
            builder(EntityTypeTags.UNDEAD, WKEntityTypes.CUSITH, WKEntityTypes.CHURCH_GRIM);
            builder(EntityTypeTags.CAN_BREATHE_UNDER_WATER, WKEntityTypes.CUSITH, WKEntityTypes.ROGGENWOLF, WKEntityTypes.CHURCH_GRIM);
        }

        private void builder(TagKey<EntityType<?>> tag, EntityType<?>... types) {
            var builder = builder(tag);
            for (EntityType<?> type : types) {
                builder.add(type.builtInRegistryHolder().key());
            }
        }
    }

    // FIXME: Damage types aren't being loaded into the registry, for some reason.
    /*public static class WKDamageTypeTags extends FabricTagProvider<DamageType> {
        public WKDamageTypeTags(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, RegistryKeys.DAMAGE_TYPE, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            builder(DamageTypeTags.BURN_FROM_STEPPING)
                .add(WKDamageSources.ON_OVEN);

            builder(DamageTypeTags.BYPASSES_ARMOR)
                .add(WKDamageSources.HOLY)
                .add(WKDamageSources.ON_OVEN);

            builder(DamageTypeTags.BYPASSES_EFFECTS)
                .add(WKDamageSources.HOLY);

            builder(DamageTypeTags.BYPASSES_SHIELD)
                .add(WKDamageSources.HOLY);

            builder(DamageTypeTags.WITCH_RESISTANT_TO)
                .add(WKDamageSources.HOLY);
        }
    }*/
}
