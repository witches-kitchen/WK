package cf.witcheskitchen.common.crop;

import cf.witcheskitchen.common.registry.WKItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BelladonnaCropBlock extends WKTallCropBlock {

    public static final int MAX_AGE = 6;
    private final Type type;

    public BelladonnaCropBlock(Settings settings) {
        this(settings, Type.COMMON);
    }

    public BelladonnaCropBlock(Settings settings, Type rarity) {
        super(settings);
        this.type = rarity;
    }

    @Override
    public int topLayerAge() {
        return 4;
    }

    @Override
    public void applyGrowth(World world, BlockPos pos, BlockState state) {
        // Stop if this is the second layer
        if (isUpperState(world, pos)) {
            return;
        }
        int i = this.getAge(state) + 1;
        final BlockState nextState = this.withAge(i);
        world.setBlockState(pos, nextState, Block.NOTIFY_LISTENERS);
        if (i >= 4) {
            world.setBlockState(pos.up(), nextState.with(TALL_PLANT, DoubleBlockHalf.UPPER), Block.NOTIFY_ALL);
        }
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

    public enum Type {
        COMMON,
        GLOW,
        NOCTURNAL
    }
}
