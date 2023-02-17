package cf.witcheskitchen.common.util;

import cf.witcheskitchen.common.registry.WKBlocks;
import net.minecraft.block.Block;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

import java.util.Map;
import java.util.Optional;

public class TypeHelper {

    public static Optional<Block> getBlockFromNbt(NbtCompound nbt){
        NbtList nbtList = nbt.getList("Variant", NbtElement.COMPOUND_TYPE);
        if(!nbtList.isEmpty()){
            NbtCompound nameNbt = nbtList.getCompound(0);
            String name = nameNbt.getString("Name");
            for (Map.Entry<String, Block> entry : WKBlocks.getTypeBlocks().entrySet()) {
                String nameMap = entry.getKey();
                Block blockMap = entry.getValue();
                if(nameMap.contains(name)){
                    return Optional.of(blockMap);
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Call the Enum's getString() for variantName to get the correct name
     * @param nbt
     * @param variantName
     * @param variantColor
     * @return
     */
    public static NbtCompound toNbt(NbtCompound nbt, String variantName, int variantColor){
        NbtList list = new NbtList();

        NbtCompound name = new NbtCompound();
        name.putString("Name", variantName);

        NbtCompound color = new NbtCompound();
        color.putInt("Color", variantColor);

        list.add(name);
        list.add(color);
        nbt.put("Variant", list);

        return nbt;
    }
}
