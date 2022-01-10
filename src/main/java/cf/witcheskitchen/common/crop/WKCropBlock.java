package cf.witcheskitchen.common.crop;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.Fertilizable;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public abstract class WKCropBlock extends CropBlock implements Fertilizable {

    public WKCropBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(this.getAgeRange(), 0));
    }

    /**
     * A property that specifies the age of a block on a scale of 0 to maxAge.
     */
    protected abstract IntProperty getAgeRange();

    @Environment(EnvType.CLIENT)
    @Override
    protected abstract ItemConvertible getSeedsItem();

    @Override
    public abstract int getMaxAge();


    // We need to override these
    // Because we are going to use different age ranges
    @Override
    public IntProperty getAgeProperty() {
        return this.getAgeRange();
    }

    @Override
    protected int getAge(BlockState state) {
        return state.get(this.getAgeProperty());
    }

    @Override
    protected int getGrowthAmount(World world) {
        return MathHelper.nextInt(world.random, 2, 5);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(this.getAgeProperty());
    }
}
