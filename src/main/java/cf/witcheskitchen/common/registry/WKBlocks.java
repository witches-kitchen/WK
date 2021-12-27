package cf.witcheskitchen.common.registry;

import cf.witcheskitchen.WK;
import cf.witcheskitchen.common.blocks.SaltBlock;
import cf.witcheskitchen.common.blocks.WKSaplingBlock;
import cf.witcheskitchen.common.blocks.WKStairsBlock;
import cf.witcheskitchen.common.generator.WKSaplingGenerator;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;

public class WKBlocks {
    public static final Block SALT_BLOCK = new SaltBlock(FabricBlockSettings.of(Material.DECORATION).noCollision().breakInstantly());
    public static final Block RAW_GINGERBREAD_BLOCK = new Block(FabricBlockSettings.of(Material.CAKE));
    public static final Block RAW_CHISELED_GINGERBREAD_BLOCK = new Block(FabricBlockSettings.of(Material.CAKE));
    public static final Block GINGERBREAD_BEVELED_BLOCK = new Block(FabricBlockSettings.of(Material.CAKE));
    public static final Block GINGERBREAD_BLOCK = new Block(FabricBlockSettings.of(Material.CAKE));
    public static final Block RAW_GINGERBREAD_BEVELED_BLOCK = new Block(FabricBlockSettings.of(Material.CAKE));
    public static final Block FROSTED_GINGERBREAD_BLOCK = new Block(FabricBlockSettings.of(Material.CAKE));
    public static final Block FROSTED_BEVELED_GINGERBREAD_BLOCK = new Block(FabricBlockSettings.of(Material.CAKE));
    public static final Block CHISELED_GINGERBREAD_BLOCK = new Block(FabricBlockSettings.of(Material.CAKE));
    public static final Block RAW_GINGERBREAD_TILED_BLOCK = new Block(FabricBlockSettings.of(Material.CAKE));
    public static final Block GINGERBREAD_TILED_BLOCK = new Block(FabricBlockSettings.of(Material.CAKE));
    public static final Block FROSTING_BLOCK = new Block(FabricBlockSettings.of(Material.CAKE));
    public static final Block FROSTED_GINGERBREAD_TILED_BLOCK = new Block(FabricBlockSettings.of(Material.CAKE));
    public static final Block FROSTED_BEVELED_GINGERBREAD_BLOCK_YELLOW = new Block(FabricBlockSettings.of(Material.CAKE));
    public static final Block FROSTED_BEVELED_GINGERBREAD_BLOCK_RED = new Block(FabricBlockSettings.of(Material.CAKE));
    public static final Block FROSTED_BEVELED_GINGERBREAD_BLOCK_PURPLE = new Block(FabricBlockSettings.of(Material.CAKE));
    public static final Block FROSTED_BEVELED_GINGERBREAD_BLOCK_GREEN = new Block(FabricBlockSettings.of(Material.CAKE));
    public static final Block FROSTED_TILED_GINGERBREAD_BLOCK_YELLOW = new Block(FabricBlockSettings.of(Material.CAKE));
    public static final Block FROSTED_TILED_GINGERBREAD_BLOCK_RED = new Block(FabricBlockSettings.of(Material.CAKE));
    public static final Block FROSTED_TILED_GINGERBREAD_BLOCK_PURPLE = new Block(FabricBlockSettings.of(Material.CAKE));
    public static final Block FROSTED_TILED_GINGERBREAD_BLOCK_GREEN = new Block(FabricBlockSettings.of(Material.CAKE));
    public static final Block FROSTED_TILED_GINGERBREAD_BLOCK_VARIANT = new Block(FabricBlockSettings.of(Material.CAKE));
    public static final Block RAW_GINGERBREAD_BLOCK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    public static final Block RAW_CHISELED_GINGERBREAD_BLOCK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    public static final Block GINGERBREAD_BEVELED_BLOCK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    public static final Block GINGERBREAD_BLOCK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    public static final Block RAW_GINGERBREAD_BEVELED_BLOCK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    public static final Block FROSTED_GINGERBREAD_BLOCK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    public static final Block FROSTED_BEVELED_GINGERBREAD_BLOCK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    public static final Block CHISELED_GINGERBREAD_BLOCK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    public static final Block RAW_GINGERBREAD_TILED_BLOCK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    public static final Block GINGERBREAD_TILED_BLOCK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    public static final Block FROSTING_BLOCK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    public static final Block FROSTED_GINGERBREAD_TILED_BLOCK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    public static final Block FROSTED_BEVELED_GINGERBREAD_BLOCK_YELLOW_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    public static final Block FROSTED_BEVELED_GINGERBREAD_BLOCK_RED_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    public static final Block FROSTED_BEVELED_GINGERBREAD_BLOCK_PURPLE_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    public static final Block FROSTED_BEVELED_GINGERBREAD_BLOCK_GREEN_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    public static final Block FROSTED_TILED_GINGERBREAD_BLOCK_YELLOW_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    public static final Block FROSTED_TILED_GINGERBREAD_BLOCK_RED_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    public static final Block FROSTED_TILED_GINGERBREAD_BLOCK_PURPLE_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    public static final Block FROSTED_TILED_GINGERBREAD_BLOCK_GREEN_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    public static final Block FROSTED_TILED_GINGERBREAD_BLOCK_VARIANT_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    public static final Block ELDER_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD));
    public static final Block SUMAC_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD));
    public static final Block HAWTHORN_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD));
    public static final Block BLACKTHORN_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD));
    public static final Block JUNIPER_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD));
    public static final Block ROWAN_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD));
    public static final Block ROWAN_STAIRS = new WKStairsBlock(ROWAN_PLANKS.getDefaultState(), FabricBlockSettings.copyOf(ROWAN_PLANKS));
    public static final Block ELDER_STAIRS = new WKStairsBlock(ELDER_PLANKS.getDefaultState(), FabricBlockSettings.copyOf(ELDER_PLANKS));
    public static final Block SUMAC_STAIRS = new WKStairsBlock(SUMAC_PLANKS.getDefaultState(), FabricBlockSettings.copyOf(SUMAC_PLANKS));
    public static final Block BLACKTHORN_STAIRS = new WKStairsBlock(BLACKTHORN_PLANKS.getDefaultState(), FabricBlockSettings.copyOf(BLACKTHORN_PLANKS));
    public static final Block HAWTHORN_STAIRS = new WKStairsBlock(HAWTHORN_PLANKS.getDefaultState(), FabricBlockSettings.copyOf(HAWTHORN_PLANKS));
    public static final Block JUNIPER_STAIRS = new WKStairsBlock(JUNIPER_PLANKS.getDefaultState(), FabricBlockSettings.copyOf(JUNIPER_PLANKS));
    public static final Block ELDER_LOG = WKBlocks.newLogBlock(MapColor.PALE_YELLOW, MapColor.OAK_TAN);
    public static final Block SUMAC_LOG = WKBlocks.newLogBlock(MapColor.DEEPSLATE_GRAY, MapColor.DARK_DULL_PINK);
    public static final Block HAWTHORN_LOG = WKBlocks.newLogBlock(MapColor.PALE_YELLOW, MapColor.DIRT_BROWN);
    public static final Block BLACKTHORN_LOG = WKBlocks.newLogBlock(MapColor.TERRACOTTA_BROWN, MapColor.TERRACOTTA_BLACK);
    public static final Block JUNIPER_LOG = WKBlocks.newLogBlock(MapColor.DIRT_BROWN, MapColor.DEEPSLATE_GRAY);
    public static final Block ROWAN_LOG = WKBlocks.newLogBlock(MapColor.TERRACOTTA_BLACK, MapColor.BROWN);

    public static final Block ELDER_SLAB = new SlabBlock(FabricBlockSettings.of(Material.WOOD));
    public static final Block SUMAC_SLAB = new SlabBlock(FabricBlockSettings.of(Material.WOOD));
    public static final Block HAWTHORN_SLAB = new SlabBlock(FabricBlockSettings.of(Material.WOOD));
    public static final Block BLACKTHORN_SLAB = new SlabBlock(FabricBlockSettings.of(Material.WOOD));
    public static final Block JUNIPER_SLAB = new SlabBlock(FabricBlockSettings.of(Material.WOOD));
    public static final Block ROWAN_SLAB = new SlabBlock(FabricBlockSettings.of(Material.WOOD));

    public static final Block ELDER_LEAVES = WKBlocks.newLeavesBlock(BlockSoundGroup.GRASS);
    public static final Block ELDER_LEAVES_COLORED = WKBlocks.newLeavesBlock(BlockSoundGroup.GRASS);
    public static final Block SUMAC_LEAVES = WKBlocks.newLeavesBlock(BlockSoundGroup.GRASS);
    public static final Block HAWTHORN_LEAVES = WKBlocks.newLeavesBlock(BlockSoundGroup.GRASS);
    public static final Block HAWTHORN_LEAVES_COLORED = WKBlocks.newLeavesBlock(BlockSoundGroup.GRASS);
    public static final Block BLACKTHORN_LEAVES = WKBlocks.newLeavesBlock(BlockSoundGroup.GRASS);
    public static final Block JUNIPER_LEAVES = WKBlocks.newLeavesBlock(BlockSoundGroup.GRASS);
    public static final Block ROWAN_LEAVES = WKBlocks.newLeavesBlock(BlockSoundGroup.GRASS);

    //Tile Entities
    public static final Block TEAPOT = new Block(FabricBlockSettings.of(Material.AMETHYST));

    public static final Block BLACKTHORN_SAPLING = new WKSaplingBlock(new WKSaplingGenerator(() -> WKGenerator.BLACKTHORN_TREE), FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final Block ELDER_SAPLING = new WKSaplingBlock(new WKSaplingGenerator(() -> WKGenerator.ELDER_TREE), FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final Block HAWTHORN_SAPLING = new WKSaplingBlock(new WKSaplingGenerator(() -> WKGenerator.HAWTHORN_TREE), FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final Block JUNIPER_SAPLING = new WKSaplingBlock(new WKSaplingGenerator(() -> WKGenerator.JUNIPER_TREE), FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final Block ROWAN_SAPLING = new WKSaplingBlock(new WKSaplingGenerator(() -> WKGenerator.ROWAN_TREE), FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS));
    public static final Block SUMAC_SAPLING = new WKSaplingBlock(new WKSaplingGenerator(() -> WKGenerator.SUMAC_TREE), FabricBlockSettings.of(Material.PLANT).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.GRASS));

    public static final Block POTTED_BLACKTHORN_SAPLING = new FlowerPotBlock(BLACKTHORN_SAPLING, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block POTTED_ELDER_SAPLING = new FlowerPotBlock(ELDER_SAPLING, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block POTTED_HAWTHORN_SAPLING = new FlowerPotBlock(HAWTHORN_SAPLING, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block POTTED_JUNIPER_SAPLING = new FlowerPotBlock(JUNIPER_SAPLING, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block POTTED_ROWAN_SAPLING = new FlowerPotBlock(ROWAN_SAPLING, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());
    public static final Block POTTED_SUMAC_SAPLING = new FlowerPotBlock(SUMAC_SAPLING, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque());


    public static void register() {
        //Food blocks
        registerBlock("raw_gingerbread_block", RAW_GINGERBREAD_BLOCK, WK.WK_GROUP);
        registerBlock("raw_chiseled_gingerbread_block", RAW_CHISELED_GINGERBREAD_BLOCK, WK.WK_GROUP);
        registerBlock("raw_gingerbread_beveled_block", RAW_GINGERBREAD_BEVELED_BLOCK, WK.WK_GROUP);
        registerBlock("gingerbread_beveled_block", GINGERBREAD_BEVELED_BLOCK, WK.WK_GROUP);
        registerBlock("gingerbread_block", GINGERBREAD_BLOCK, WK.WK_GROUP);
        registerBlock("frosting_block", FROSTING_BLOCK, WK.WK_GROUP);
        registerBlock("frosted_gingerbread_block", FROSTED_GINGERBREAD_BLOCK, WK.WK_GROUP);
        registerBlock("frosted_beveled_gingerbread_block", FROSTED_BEVELED_GINGERBREAD_BLOCK, WK.WK_GROUP);
        registerBlock("chiseled_gingerbread_block", CHISELED_GINGERBREAD_BLOCK, WK.WK_GROUP);
        registerBlock("raw_gingerbread_tiled_block", RAW_GINGERBREAD_TILED_BLOCK, WK.WK_GROUP);
        registerBlock("gingerbread_tiled_block", GINGERBREAD_TILED_BLOCK, WK.WK_GROUP);
        registerBlock("frosted_gingerbread_tiled_block", FROSTED_GINGERBREAD_TILED_BLOCK, WK.WK_GROUP);
        registerBlock("frosted_beveled_gingerbread_block_yellow", FROSTED_BEVELED_GINGERBREAD_BLOCK_YELLOW, WK.WK_GROUP);
        registerBlock("frosted_beveled_gingerbread_block_red", FROSTED_BEVELED_GINGERBREAD_BLOCK_RED, WK.WK_GROUP);
        registerBlock("frosted_beveled_gingerbread_block_green", FROSTED_BEVELED_GINGERBREAD_BLOCK_GREEN, WK.WK_GROUP);
        registerBlock("frosted_beveled_gingerbread_block_purple", FROSTED_BEVELED_GINGERBREAD_BLOCK_PURPLE, WK.WK_GROUP);
        registerBlock("frosted_tiled_gingerbread_block_yellow", FROSTED_TILED_GINGERBREAD_BLOCK_YELLOW, WK.WK_GROUP);
        registerBlock("frosted_tiled_gingerbread_block_red", FROSTED_TILED_GINGERBREAD_BLOCK_RED, WK.WK_GROUP);
        registerBlock("frosted_tiled_gingerbread_block_green", FROSTED_TILED_GINGERBREAD_BLOCK_GREEN, WK.WK_GROUP);
        registerBlock("frosted_tiled_gingerbread_block_purple", FROSTED_TILED_GINGERBREAD_BLOCK_PURPLE, WK.WK_GROUP);
        registerBlock("frosted_tiled_gingerbread_block_variant", FROSTED_TILED_GINGERBREAD_BLOCK_VARIANT, WK.WK_GROUP);

        //Gingerbread slabs
        registerBlock("raw_gingerbread_block_slab", RAW_GINGERBREAD_BLOCK_SLAB, WK.WK_GROUP);
        registerBlock("raw_chiseled_gingerbread_block_slab", RAW_CHISELED_GINGERBREAD_BLOCK_SLAB, WK.WK_GROUP);
        registerBlock("raw_gingerbread_beveled_block_slab", RAW_GINGERBREAD_BEVELED_BLOCK_SLAB, WK.WK_GROUP);
        registerBlock("gingerbread_beveled_block_slab", GINGERBREAD_BEVELED_BLOCK_SLAB, WK.WK_GROUP);
        registerBlock("gingerbread_block_slab", GINGERBREAD_BLOCK_SLAB, WK.WK_GROUP);
        registerBlock("frosting_block_slab", FROSTING_BLOCK_SLAB, WK.WK_GROUP);
        registerBlock("frosted_gingerbread_block_slab", FROSTED_GINGERBREAD_BLOCK_SLAB, WK.WK_GROUP);
        registerBlock("frosted_beveled_gingerbread_block_slab", FROSTED_BEVELED_GINGERBREAD_BLOCK_SLAB, WK.WK_GROUP);
        registerBlock("chiseled_gingerbread_block_slab", CHISELED_GINGERBREAD_BLOCK_SLAB, WK.WK_GROUP);
        registerBlock("raw_gingerbread_tiled_block_slab", RAW_GINGERBREAD_TILED_BLOCK_SLAB, WK.WK_GROUP);
        registerBlock("gingerbread_tiled_block_slab", GINGERBREAD_TILED_BLOCK_SLAB, WK.WK_GROUP);
        registerBlock("frosted_gingerbread_tiled_block_slab", FROSTED_GINGERBREAD_TILED_BLOCK_SLAB, WK.WK_GROUP);
        registerBlock("frosted_beveled_gingerbread_block_yellow_slab", FROSTED_BEVELED_GINGERBREAD_BLOCK_YELLOW_SLAB, WK.WK_GROUP);
        registerBlock("frosted_beveled_gingerbread_block_red_slab", FROSTED_BEVELED_GINGERBREAD_BLOCK_RED_SLAB, WK.WK_GROUP);
        registerBlock("frosted_beveled_gingerbread_block_green_slab", FROSTED_BEVELED_GINGERBREAD_BLOCK_GREEN_SLAB, WK.WK_GROUP);
        registerBlock("frosted_beveled_gingerbread_block_purple_slab", FROSTED_BEVELED_GINGERBREAD_BLOCK_PURPLE_SLAB, WK.WK_GROUP);
        registerBlock("frosted_tiled_gingerbread_block_yellow_slab", FROSTED_TILED_GINGERBREAD_BLOCK_YELLOW_SLAB, WK.WK_GROUP);
        registerBlock("frosted_tiled_gingerbread_block_red_slab", FROSTED_TILED_GINGERBREAD_BLOCK_RED_SLAB, WK.WK_GROUP);
        registerBlock("frosted_tiled_gingerbread_block_green_slab", FROSTED_TILED_GINGERBREAD_BLOCK_GREEN_SLAB, WK.WK_GROUP);
        registerBlock("frosted_tiled_gingerbread_block_purple_slab", FROSTED_TILED_GINGERBREAD_BLOCK_PURPLE_SLAB, WK.WK_GROUP);
        registerBlock("frosted_tiled_gingerbread_block_variant_slab", FROSTED_TILED_GINGERBREAD_BLOCK_VARIANT_SLAB, WK.WK_GROUP);

        //Gingerbread Stairs


        //Saplings
        registerBlock("blackthorn_sapling", BLACKTHORN_SAPLING, WK.WK_GROUP);
        registerBlock("elder_sapling", ELDER_SAPLING, WK.WK_GROUP);
        registerBlock("hawthorn_sapling", HAWTHORN_SAPLING, WK.WK_GROUP);
        registerBlock("juniper_sapling", JUNIPER_SAPLING, WK.WK_GROUP);
        registerBlock("rowan_sapling", ROWAN_SAPLING, WK.WK_GROUP);
        registerBlock("sumac_sapling", SUMAC_SAPLING, WK.WK_GROUP);
        registerBlock("potted_blackthorn_sapling", POTTED_BLACKTHORN_SAPLING, WK.WK_GROUP);
        registerBlock("potted_elder_sapling", POTTED_ELDER_SAPLING, WK.WK_GROUP);
        registerBlock("potted_hawthorn_sapling", POTTED_HAWTHORN_SAPLING, WK.WK_GROUP);
        registerBlock("potted_juniper_sapling", POTTED_JUNIPER_SAPLING, WK.WK_GROUP);
        registerBlock("potted_sumac_sapling", POTTED_SUMAC_SAPLING, WK.WK_GROUP);
        registerBlock("potted_rowan_sapling", POTTED_ROWAN_SAPLING, WK.WK_GROUP);

        //Wood planks
        registerBlock("elder_planks", ELDER_PLANKS, WK.WK_GROUP);
        registerBlock("sumac_planks", SUMAC_PLANKS, WK.WK_GROUP);
        registerBlock("hawthorn_planks", HAWTHORN_PLANKS, WK.WK_GROUP);
        registerBlock("blackthorn_planks", BLACKTHORN_PLANKS, WK.WK_GROUP);
        registerBlock("juniper_planks", JUNIPER_PLANKS, WK.WK_GROUP);
        registerBlock("rowan_planks", ROWAN_PLANKS, WK.WK_GROUP);

        //Wood stairs
        registerBlock("rowan_stairs", ROWAN_STAIRS, WK.WK_GROUP);
        registerBlock("elder_stairs", ELDER_STAIRS, WK.WK_GROUP);
        registerBlock("hawthorn_stairs", HAWTHORN_STAIRS, WK.WK_GROUP);
        registerBlock("blackthorn_stairs", BLACKTHORN_STAIRS, WK.WK_GROUP);
        registerBlock("juniper_stairs", JUNIPER_STAIRS, WK.WK_GROUP);
        registerBlock("sumac_stairs", SUMAC_STAIRS, WK.WK_GROUP);

        //Wood Slabs
        registerBlock("elder_slab", ELDER_SLAB, WK.WK_GROUP);
        registerBlock("sumac_slab", SUMAC_SLAB, WK.WK_GROUP);
        registerBlock("hawthorn_slab", HAWTHORN_SLAB, WK.WK_GROUP);
        registerBlock("blackthorn_slab", BLACKTHORN_SLAB, WK.WK_GROUP);
        registerBlock("juniper_slab", JUNIPER_SLAB, WK.WK_GROUP);
        registerBlock("rowan_slab", ROWAN_SLAB, WK.WK_GROUP);

        //Leaves
        registerBlock("elder_leaves", ELDER_LEAVES, WK.WK_GROUP);
        registerBlock("elder_leaves_colored", ELDER_LEAVES_COLORED, WK.WK_GROUP);
        registerBlock("sumac_leaves", SUMAC_LEAVES, WK.WK_GROUP);
        registerBlock("hawthorn_leaves", HAWTHORN_LEAVES, WK.WK_GROUP);
        registerBlock("hawthorn_leaves_colored", HAWTHORN_LEAVES_COLORED, WK.WK_GROUP);
        registerBlock("blackthorn_leaves", BLACKTHORN_LEAVES, WK.WK_GROUP);
        registerBlock("juniper_leaves", JUNIPER_LEAVES, WK.WK_GROUP);
        registerBlock("rowan_leaves", ROWAN_LEAVES, WK.WK_GROUP);

        //Wood logs
        registerBlock("elder_log", ELDER_LOG, WK.WK_GROUP);
        registerBlock("sumac_log", SUMAC_LOG, WK.WK_GROUP);
        registerBlock("hawthorn_log", HAWTHORN_LOG, WK.WK_GROUP);
        registerBlock("blackthorn_log", BLACKTHORN_LOG, WK.WK_GROUP);
        registerBlock("juniper_log", JUNIPER_LOG, WK.WK_GROUP);
        registerBlock("rowan_log", ROWAN_LOG, WK.WK_GROUP);

        //Minerals
        registerBlock("salt", SALT_BLOCK, WK.WK_GROUP);
    }

    //needs to be reworked for better suiting our needs.. maybe? keeping the never and 2 always in case we end up needing to use them.
    private static Boolean never(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return false;
    }

    private static Boolean always(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return true;
    }

    private static Boolean willSpawnOnLeaves(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return type == EntityType.OCELOT || type == EntityType.PARROT;
    }

    public static PillarBlock newLogBlock(MapColor topMapColor, MapColor sideMapColor) {
        return new PillarBlock(FabricBlockSettings.of(Material.WOOD, state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? topMapColor : sideMapColor).strength(2.0f).sounds(BlockSoundGroup.WOOD));
    }

    private static boolean always(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    private static boolean never(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    private static LeavesBlock newLeavesBlock(BlockSoundGroup soundGroup) {
        return new LeavesBlock(FabricBlockSettings.of(Material.LEAVES).strength(0.2f).ticksRandomly().sounds(soundGroup).nonOpaque().allowsSpawning(WKBlocks::willSpawnOnLeaves).suffocates(WKBlocks::never).blockVision(WKBlocks::never));
    }

    //mod code
    public static void registerBlock(String id, Block block, ItemGroup tab) {
        Registry.register(Registry.BLOCK, new Identifier(WK.MODID, id), block);
        Registry.register(Registry.ITEM, new Identifier(WK.MODID, id), new BlockItem(block, new FabricItemSettings().group(tab)));
    }


}
