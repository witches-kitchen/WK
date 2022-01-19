package cf.witcheskitchen.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

/**
 * Provides abstraction for a CropBlock
 */
public abstract class WKCropBlock extends CropBlock {

    public WKCropBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(this.getAgeRange(), 0));
    }

    /**
     * A property that specifies the age of a block on a scale of 0 to maxAge.
     */
    protected abstract IntProperty getAgeRange();

    /**
     * Used to get the seed item of the plant
     * which is used on a client context by minecraft
     * @return Item
     */
    @Environment(EnvType.CLIENT)
    @Override
    protected abstract ItemConvertible getSeedsItem();

    /**
     * The top stage this plant is going to have.
     * @return int
     */
    @Override
    public abstract int getMaxAge();


    /**
     * We need to override this
     * Because we are going to use different age ranges
     * @return IntProperty
     */
    @Override
    public IntProperty getAgeProperty() {
        return this.getAgeRange();
    }

    /**
     * Returns the integer value of the current age property
     * @param state BlockState
     * @return int
     */
    @Override
    protected int getAge(BlockState state) {
        return state.get(this.getAgeProperty());
    }

    /**
     * Returns a random amount of growth.
     * Mainly used by the {@link net.minecraft.item.BoneMealItem}
     * @param world World
     * @return int
     */
    @Override
    protected int getGrowthAmount(World world) {
        return MathHelper.nextInt(world.random, 2, this.getMaxAge() - 2);
    }

    /**
     * Adds the age property to the BlockState builder
     */
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(this.getAgeProperty());
    }
}
