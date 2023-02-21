package cf.witcheskitchen.common.item;

import net.minecraft.item.Item;

public class CurseBundleItem extends Item {
    public int levels;
    public CurseBundleItem(Settings settings, int levels) {
        super(settings);
        this.levels = levels;
    }
}
