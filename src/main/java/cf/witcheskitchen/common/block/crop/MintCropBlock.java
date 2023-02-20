package cf.witcheskitchen.common.block.crop;

import cf.witcheskitchen.api.block.crop.WKCropBlock;
import cf.witcheskitchen.common.registry.WKItems;
import cf.witcheskitchen.common.block.crop.variants.MintTypes;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.property.IntProperty;
import org.quiltmc.loader.api.minecraft.ClientOnly;

public class MintCropBlock extends WKCropBlock {
    public static final int MAX_AGE = 4;
    private final MintTypes type;

    public MintCropBlock(Settings settings) {
        this(settings, MintTypes.COMMON);
    }

    public MintCropBlock(Settings settings, MintTypes type) {
        super(settings);
        this.type = type;
        this.setDefaultState(this.getDefaultState().with(getAgeProperty(), 0));
    }

    @Override
    public IntProperty getAgeProperty() {
        return IntProperty.of("age", 0, MAX_AGE);
    }

    @ClientOnly
    @Override
    protected ItemConvertible getSeedsItem() {
        return  WKItems.MINT_SPRIG;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }
}
