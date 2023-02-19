package cf.witcheskitchen.common.crop;

import cf.witcheskitchen.api.crop.WKTallCropBlock;
import cf.witcheskitchen.common.registry.WKItems;
import cf.witcheskitchen.common.util.TypeHelper;
import cf.witcheskitchen.common.variants.BelladonnaTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import org.quiltmc.loader.api.minecraft.ClientOnly;

public class BelladonnaCropBlock extends WKTallCropBlock {
    public static final int MAX_AGE = 6;
    public static final IntProperty AGE = IntProperty.of("age", 0, MAX_AGE);
    private final BelladonnaTypes type;

    public BelladonnaCropBlock(Settings settings) {
        this(settings, BelladonnaTypes.COMMON);
    }

    @Override
    public VoxelShape[] getLowerShape() {
        return AmaranthCropBlock.LOWER_AGE_TO_SHAPE;
    }

    @Override
    public VoxelShape[] getUpperShape() {
        return AmaranthCropBlock.UPPER_AGE_TO_SHAPE;
    }

    public BelladonnaCropBlock(Settings settings, BelladonnaTypes type) {
        super(settings);
        this.type = type;
        this.setDefaultState(this.getDefaultState().with(AGE, 0).with(HALF, DoubleBlockHalf.LOWER));
    }

    //TODO make this but in WKLootTables with a builder
    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        ItemStack itemStack = getSeedsItem().asItem().getDefaultStack();
        NbtCompound nbtCompound = new NbtCompound();
        TypeHelper.toNbt(nbtCompound, type.getName(), type.getType(), type.getColor());
        itemStack.writeNbt(nbtCompound);
        ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
    }

    //TODO make this nbt supported to get type seeds
    @ClientOnly
    @Override
    protected ItemConvertible getSeedsItem() {
        return  WKItems.BELLADONNA_SEEDS;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    public IntProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int doubleBlockAge() {
        return 4;
    }
}
