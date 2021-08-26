package cf.witcheskitchen.registry;

import cf.witcheskitchen.WK;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.SlabBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WKBlocks {
    static Block RAW_GINGERBREAD_BLOCK = new Block(FabricBlockSettings.of(Material.CAKE));
    static Block RAW_CHISELED_GINGERBREAD_BLOCK = new Block(FabricBlockSettings.of(Material.CAKE));
    static Block GINGERBREAD_BEVELED_BLOCK = new Block(FabricBlockSettings.of(Material.CAKE));
    static Block GINGERBREAD_BLOCK = new Block(FabricBlockSettings.of(Material.CAKE));
    static Block RAW_GINGERBREAD_BEVELED_BLOCK = new Block(FabricBlockSettings.of(Material.CAKE));
    static Block FROSTED_GINGERBREAD_BLOCK = new Block(FabricBlockSettings.of(Material.CAKE));
    static Block FROSTED_BEVELED_GINGERBREAD_BLOCK = new Block(FabricBlockSettings.of(Material.CAKE));
    static Block CHISELED_GINGERBREAD_BLOCK = new Block(FabricBlockSettings.of(Material.CAKE));
    static Block RAW_GINGERBREAD_TILED_BLOCK = new Block(FabricBlockSettings.of(Material.CAKE));
    static Block GINGERBREAD_TILED_BLOCK = new Block(FabricBlockSettings.of(Material.CAKE));
    static Block FROSTING_BLOCK = new Block(FabricBlockSettings.of(Material.CAKE));
    static Block FROSTED_GINGERBREAD_TILED_BLOCK = new Block(FabricBlockSettings.of(Material.CAKE));
    static Block FROSTED_BEVELED_GINGERBREAD_BLOCK_YELLOW = new Block(FabricBlockSettings.of(Material.CAKE));
    static Block FROSTED_BEVELED_GINGERBREAD_BLOCK_RED = new Block(FabricBlockSettings.of(Material.CAKE));
    static Block FROSTED_BEVELED_GINGERBREAD_BLOCK_PURPLE = new Block(FabricBlockSettings.of(Material.CAKE));
    static Block FROSTED_BEVELED_GINGERBREAD_BLOCK_GREEN = new Block(FabricBlockSettings.of(Material.CAKE));
    static Block FROSTED_TILED_GINGERBREAD_BLOCK_YELLOW = new Block(FabricBlockSettings.of(Material.CAKE));
    static Block FROSTED_TILED_GINGERBREAD_BLOCK_RED = new Block(FabricBlockSettings.of(Material.CAKE));
    static Block FROSTED_TILED_GINGERBREAD_BLOCK_PURPLE = new Block(FabricBlockSettings.of(Material.CAKE));
    static Block FROSTED_TILED_GINGERBREAD_BLOCK_GREEN = new Block(FabricBlockSettings.of(Material.CAKE));
    static Block FROSTED_TILED_GINGERBREAD_BLOCK_VARIANT = new Block(FabricBlockSettings.of(Material.CAKE));

    static Block RAW_GINGERBREAD_BLOCK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    static Block RAW_CHISELED_GINGERBREAD_BLOCK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    static Block GINGERBREAD_BEVELED_BLOCK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    static Block GINGERBREAD_BLOCK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    static Block RAW_GINGERBREAD_BEVELED_BLOCK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    static Block FROSTED_GINGERBREAD_BLOCK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    static Block FROSTED_BEVELED_GINGERBREAD_BLOCK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    static Block CHISELED_GINGERBREAD_BLOCK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    static Block RAW_GINGERBREAD_TILED_BLOCK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    static Block GINGERBREAD_TILED_BLOCK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    static Block FROSTING_BLOCK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    static Block FROSTED_GINGERBREAD_TILED_BLOCK_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    static Block FROSTED_BEVELED_GINGERBREAD_BLOCK_YELLOW_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    static Block FROSTED_BEVELED_GINGERBREAD_BLOCK_RED_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    static Block FROSTED_BEVELED_GINGERBREAD_BLOCK_PURPLE_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    static Block FROSTED_BEVELED_GINGERBREAD_BLOCK_GREEN_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    static Block FROSTED_TILED_GINGERBREAD_BLOCK_YELLOW_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    static Block FROSTED_TILED_GINGERBREAD_BLOCK_RED_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    static Block FROSTED_TILED_GINGERBREAD_BLOCK_PURPLE_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    static Block FROSTED_TILED_GINGERBREAD_BLOCK_GREEN_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));
    static Block FROSTED_TILED_GINGERBREAD_BLOCK_VARIANT_SLAB = new SlabBlock(FabricBlockSettings.of(Material.CAKE));

    static Block ELDER_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD));
    static Block SUMAC_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD));
    static Block HAWTHORN_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD));
    static Block BLACKTHORN_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD));
    static Block JUNIPER_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD));
    static Block ROWAN_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD));

    static Block ELDER_LOG = new Block(FabricBlockSettings.of(Material.WOOD));
    static Block SUMAC_LOG = new Block(FabricBlockSettings.of(Material.WOOD));
    static Block HAWTHORN_LOG = new Block(FabricBlockSettings.of(Material.WOOD));
    static Block BLACKTHORN_LOG = new Block(FabricBlockSettings.of(Material.WOOD));
    static Block JUNIPER_LOG = new Block(FabricBlockSettings.of(Material.WOOD));
    static Block ROWAN_LOG = new Block(FabricBlockSettings.of(Material.WOOD));

    static Block ELDER_LEAVES = new Block(FabricBlockSettings.of(Material.LEAVES));
    static Block SUMAC_LEAVES = new Block(FabricBlockSettings.of(Material.LEAVES));
    static Block HAWTHORN_LEAVES = new Block(FabricBlockSettings.of(Material.LEAVES));
    static Block BLACKTHORN_LEAVES = new Block(FabricBlockSettings.of(Material.LEAVES));
    static Block JUNIPER_LEAVES = new Block(FabricBlockSettings.of(Material.LEAVES));
    static Block ROWAN_LEAVES = new Block(FabricBlockSettings.of(Material.LEAVES));

    public static void register() {
        //Food blocks
        //Registry.register(Registry.BLOCK, new Identifier(WK.MODID, "raw_gingerbread_block"), RAW_GINGERBREAD_BLOCK);
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
        registerBlock("frosted_beveled_gingerbread_block_yellow", FROSTED_BEVELED_GINGERBREAD_BLOCK_YELLOW_SLAB, WK.WK_GROUP);
        registerBlock("frosted_beveled_gingerbread_block_red", FROSTED_BEVELED_GINGERBREAD_BLOCK_RED_SLAB, WK.WK_GROUP);
        registerBlock("frosted_beveled_gingerbread_block_green", FROSTED_BEVELED_GINGERBREAD_BLOCK_GREEN_SLAB, WK.WK_GROUP);
        registerBlock("frosted_beveled_gingerbread_block_purple", FROSTED_BEVELED_GINGERBREAD_BLOCK_PURPLE_SLAB, WK.WK_GROUP);
        registerBlock("frosted_tiled_gingerbread_block_yellow", FROSTED_TILED_GINGERBREAD_BLOCK_YELLOW_SLAB, WK.WK_GROUP);
        registerBlock("frosted_tiled_gingerbread_block_red", FROSTED_TILED_GINGERBREAD_BLOCK_RED_SLAB, WK.WK_GROUP);
        registerBlock("frosted_tiled_gingerbread_block_green", FROSTED_TILED_GINGERBREAD_BLOCK_GREEN_SLAB, WK.WK_GROUP);
        registerBlock("frosted_tiled_gingerbread_block_purple", FROSTED_TILED_GINGERBREAD_BLOCK_PURPLE_SLAB, WK.WK_GROUP);
        registerBlock("frosted_tiled_gingerbread_block_variant", FROSTED_TILED_GINGERBREAD_BLOCK_VARIANT_SLAB, WK.WK_GROUP);


        //Wood planks
        registerBlock("elder_planks", ELDER_PLANKS, WK.WK_GROUP);
        registerBlock("sumac_planks", SUMAC_PLANKS, WK.WK_GROUP);
        registerBlock("hawthorn_planks", HAWTHORN_PLANKS, WK.WK_GROUP);
        registerBlock("blackthorn_planks", BLACKTHORN_PLANKS, WK.WK_GROUP);
        registerBlock("juniper_planks", JUNIPER_PLANKS, WK.WK_GROUP);
        registerBlock("rowan_planks", ROWAN_PLANKS, WK.WK_GROUP);

        //Leaves
        registerBlock("elder_leaves", ELDER_LEAVES, WK.WK_GROUP);
        registerBlock("sumac_leaves", SUMAC_LEAVES, WK.WK_GROUP);
        registerBlock("hawthorn_leaves", HAWTHORN_LEAVES, WK.WK_GROUP);
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
    }

    public static void registerBlock(String id, Block block, ItemGroup tab) {
        Registry.register(Registry.BLOCK, new Identifier(WK.MODID, id), block);
        Registry.register(Registry.ITEM, new Identifier(WK.MODID, id), new BlockItem(block, new FabricItemSettings().group(tab)));
    }
}
