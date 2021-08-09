package cf.witcheskitchen.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import cf.witcheskitchen.WK;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WKBlocks
{
    static Block RAW_GINGERBREAD_BLOCK = new Block(FabricBlockSettings.of(Material.CAKE));
    public static void register()
    {
        //Registry.register(Registry.BLOCK, new Identifier(WK.MODID, "raw_gingerbread_block"), RAW_GINGERBREAD_BLOCK);
        registerBlock("raw_gingerbread_block", RAW_GINGERBREAD_BLOCK, ItemGroup.BUILDING_BLOCKS);
    }
    public static void registerBlock(String id, Block block, ItemGroup tab)
    {
        Registry.register(Registry.BLOCK, new Identifier(WK.MODID, id), block);
        Registry.register(Registry.ITEM, new Identifier(WK.MODID, id), new BlockItem(block, new FabricItemSettings().group(tab)));
    }
}
