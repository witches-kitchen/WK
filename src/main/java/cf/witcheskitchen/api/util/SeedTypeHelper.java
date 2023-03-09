package cf.witcheskitchen.api.util;

import cf.witcheskitchen.common.registry.WKBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.Map;
import java.util.Optional;

public class SeedTypeHelper {

    /**
     * Gets the block corresponding to the present nbt
     *
     * @param nbt the nbt to test for a block
     * @return optional block depending on if the nbt was valid for a block to be returned
     */
    public static Optional<Block> getBlockFromNbt(NbtCompound nbt) {
        NbtList nbtList = nbt.getList("Variant", NbtElement.COMPOUND_TYPE);
        if (!nbtList.isEmpty()) {
            NbtCompound nameNbt = nbtList.getCompound(0);
            NbtCompound typeNbt = nbtList.getCompound(1);
            String fullName = nameNbt.getString("Name") + "_" + typeNbt.getString("Type");
            for (Map.Entry<String, Block> entry : WKBlocks.getTypeBlocks().entrySet()) {
                String nameMap = entry.getKey();
                Block blockMap = entry.getValue();
                if (nameMap.contains(fullName)) {
                    return Optional.of(blockMap);
                }
            }
        }
        return Optional.empty();
    }

    public static NbtCompound toNbt(NbtCompound nbt, String plantName, String typeName, int variantColor) {
        NbtList list = new NbtList();

        NbtCompound name = new NbtCompound();
        name.putString("Name", plantName);
        NbtCompound type = new NbtCompound();
        type.putString("Type", typeName);
        NbtCompound color = new NbtCompound();
        color.putInt("Color", variantColor);

        list.add(name);
        list.add(type);
        list.add(color);

        nbt.put("Variant", list);
        return nbt;
    }

    public static MutableText getSeedTypeText(ItemStack stack) {
        NbtList nbtList = stack.getOrCreateNbt().getList("Variant", NbtElement.COMPOUND_TYPE);
        if (!nbtList.isEmpty() && !nbtList.getCompound(1).isEmpty() && !nbtList.getCompound(2).isEmpty()) {
            String name = nbtList.getCompound(1).getString("Type");
            String formatName = TextUtils.capitalizeString(name);
            NbtCompound colorNbt = nbtList.getCompound(2);
            int color = colorNbt.getInt("Color");
            return Text.translatable(formatName).setStyle(Style.EMPTY.withColor(color));
        }
        return null;
    }
}
