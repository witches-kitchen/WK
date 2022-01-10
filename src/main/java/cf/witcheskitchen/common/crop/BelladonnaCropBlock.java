package cf.witcheskitchen.common.crop;

import cf.witcheskitchen.common.registry.WKItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.property.IntProperty;

public class BelladonnaCropBlock extends WKTallCropBlock {

    public static final int MAX_AGE = 6;

    public BelladonnaCropBlock(Settings settings) {
        super(settings);
    }

    @Override
    public int topLayerAge() {
        return 4;
    }

    @Override
    protected IntProperty getAgeRange() {
        return IntProperty.of("age", 0, MAX_AGE);
    }

    @Environment(EnvType.CLIENT)
    @Override
    protected ItemConvertible getSeedsItem() {
        return WKItems.BELLADONNA_SEEDS;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }
}
