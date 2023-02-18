package cf.witcheskitchen.common.crop;

import cf.witcheskitchen.api.crop.WKCropBlock;
import cf.witcheskitchen.api.crop.WKTallCropBlock;
import cf.witcheskitchen.common.registry.WKItems;
import cf.witcheskitchen.common.variants.ChamomileTypes;
import cf.witcheskitchen.common.variants.SanguinaryTypes;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.property.IntProperty;
import org.quiltmc.loader.api.minecraft.ClientOnly;

public class ChamomileCropBlock extends WKCropBlock {
    public static final int MAX_AGE = 6;
    private final ChamomileTypes type;

    public ChamomileCropBlock(Settings settings) {
        this(settings, ChamomileTypes.COMMON);
    }

    public ChamomileCropBlock(Settings settings, ChamomileTypes rarity) {
        super(settings);
        this.type = rarity;
    }

    @Override
    public IntProperty getAgeProperty() {
        return IntProperty.of("age", 0, MAX_AGE);
    }

    @ClientOnly
    @Override
    protected ItemConvertible getSeedsItem() {
        return  WKItems.CHAMOMILE_SEEDS;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }
}
