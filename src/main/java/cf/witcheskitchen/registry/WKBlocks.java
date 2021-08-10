package cf.witcheskitchen.registry;

import cf.witcheskitchen.WK;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
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

    static Block ELDER_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD));
    static Block SUMAC_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD));
    static Block HAWTHORN_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD));
    static Block BLACKTHORN_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD));
    static Block JUNIPER_PLANKS = new Block(FabricBlockSettings.of(Material.WOOD));

    static Block ELDER_LOG = new Block(FabricBlockSettings.of(Material.WOOD));
    static Block SUMAC_LOG = new Block(FabricBlockSettings.of(Material.WOOD));
    static Block HAWTHORN_LOG = new Block(FabricBlockSettings.of(Material.WOOD));
    static Block BLACKTHORN_LOG = new Block(FabricBlockSettings.of(Material.WOOD));
    static Block JUNIPER_LOG = new Block(FabricBlockSettings.of(Material.WOOD));

    public static void register() {
        //Food blocks
        //Registry.register(Registry.BLOCK, new Identifier(WK.MODID, "raw_gingerbread_block"), RAW_GINGERBREAD_BLOCK);
        registerBlock("raw_gingerbread_block", RAW_GINGERBREAD_BLOCK, ItemGroup.BUILDING_BLOCKS);
        registerBlock("raw_chiseled_gingerbread_block", RAW_CHISELED_GINGERBREAD_BLOCK, ItemGroup.BUILDING_BLOCKS);
        registerBlock("raw_gingerbread_beveled_block", RAW_GINGERBREAD_BEVELED_BLOCK, ItemGroup.BUILDING_BLOCKS);
        registerBlock("gingerbread_beveled_block", GINGERBREAD_BEVELED_BLOCK, ItemGroup.BUILDING_BLOCKS);
        registerBlock("gingerbread_block", GINGERBREAD_BLOCK, ItemGroup.BUILDING_BLOCKS);

        //Wood planks
        registerBlock("elder_planks", ELDER_PLANKS, ItemGroup.BUILDING_BLOCKS);
        registerBlock("sumac_planks", SUMAC_PLANKS, ItemGroup.BUILDING_BLOCKS);
        registerBlock("hawthorn_planks", HAWTHORN_PLANKS, ItemGroup.BUILDING_BLOCKS);
        registerBlock("blackthorn_planks", BLACKTHORN_PLANKS, ItemGroup.BUILDING_BLOCKS);
        registerBlock("juniper_planks", JUNIPER_PLANKS, ItemGroup.BUILDING_BLOCKS);

        //Wood logs
        registerBlock("elder_log", ELDER_LOG, ItemGroup.BUILDING_BLOCKS);
        registerBlock("sumac_log", SUMAC_LOG, ItemGroup.BUILDING_BLOCKS);
        registerBlock("hawthorn_log", HAWTHORN_LOG, ItemGroup.BUILDING_BLOCKS);
        registerBlock("blackthorn_log", BLACKTHORN_LOG, ItemGroup.BUILDING_BLOCKS);
        registerBlock("juniper_log", JUNIPER_LOG, ItemGroup.BUILDING_BLOCKS);
    }

    public static void registerBlock(String id, Block block, ItemGroup tab) {
        Registry.register(Registry.BLOCK, new Identifier(WK.MODID, id), block);
        Registry.register(Registry.ITEM, new Identifier(WK.MODID, id), new BlockItem(block, new FabricItemSettings().group(tab)));
    }
}
