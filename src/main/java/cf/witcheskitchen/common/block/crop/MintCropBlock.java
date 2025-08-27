package cf.witcheskitchen.common.block.crop;

import cf.witcheskitchen.api.block.crop.WKCropBlock;
import cf.witcheskitchen.api.interfaces.CropVariants;
import cf.witcheskitchen.api.util.SeedTypeHelper;
import cf.witcheskitchen.common.block.crop.types.MintTypes;
import cf.witcheskitchen.common.component.WKComponents;
import cf.witcheskitchen.common.registry.WKItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.property.IntProperty;

public class MintCropBlock extends WKCropBlock implements CropVariants {
    public static final int MAX_AGE = 4;
    private static final IntProperty AGE = IntProperty.of("age", 0, MAX_AGE);
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
        return AGE;
    }

    @Environment(EnvType.CLIENT)
    @Override
    protected ItemStack getSeedsItemStack() {
        NbtCompound nbt = new NbtCompound();
        var component = SeedTypeHelper.toComponent(type.getName(), type.getType(), type.getColor());
        ItemStack seed = new ItemStack(WKItems.MINT_SPRIG);
        seed.set(WKComponents.SEED_TYPE, component);
        return seed;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }
}
