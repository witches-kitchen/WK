package cf.witcheskitchen.common.crop;

import cf.witcheskitchen.api.crop.WKCropBlock;
import cf.witcheskitchen.common.registry.WKItems;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.property.IntProperty;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import static cf.witcheskitchen.common.crop.MintCropBlock.Type.COMMON;

//Fixme: Figure out why it wants to call stuff from other crops. This issue plagued amaranth.
public class MintCropBlock extends WKCropBlock {
    public static final int MAX_AGE = 5;
    private final Type type;

    public MintCropBlock(Settings settings) {
        this(settings, COMMON);
    }

    public MintCropBlock(Settings settings, Type rarity) {
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
        return switch (this.type) {
            case COMMON -> WKItems.MINT_SPRIG;
        };
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    public enum Type {
        COMMON
    }
}
