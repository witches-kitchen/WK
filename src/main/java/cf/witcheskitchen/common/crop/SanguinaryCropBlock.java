package cf.witcheskitchen.common.crop;

import cf.witcheskitchen.api.crop.WKCropBlock;
import cf.witcheskitchen.common.registry.WKItems;
import cf.witcheskitchen.common.variants.SanguinaryTypes;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.property.IntProperty;
import org.quiltmc.loader.api.minecraft.ClientOnly;

public class SanguinaryCropBlock extends WKCropBlock {
    public static final int MAX_AGE = 6;
    private final SanguinaryTypes type;

    public SanguinaryCropBlock(Settings settings) {
        this(settings, SanguinaryTypes.COMMON);
    }

    public SanguinaryCropBlock(Settings settings, SanguinaryTypes rarity) {
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
        return  WKItems.AMARANTH_SEEDS;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }
}
