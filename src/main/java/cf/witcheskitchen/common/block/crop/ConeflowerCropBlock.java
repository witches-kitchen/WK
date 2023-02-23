package cf.witcheskitchen.common.block.crop;

import cf.witcheskitchen.api.block.crop.WKTallCropBlock;
import cf.witcheskitchen.common.registry.WKItems;
import cf.witcheskitchen.api.util.SeedTypeHelper;
import cf.witcheskitchen.common.block.crop.types.ConeflowerTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.Optional;

public class ConeflowerCropBlock extends WKTallCropBlock {
    public static final int MAX_AGE = 6;
    private final ConeflowerTypes type;

    public ConeflowerCropBlock(Settings settings) {
        this(settings, ConeflowerTypes.COMMON);
    }

    public ConeflowerCropBlock(Settings settings, ConeflowerTypes rarity) {
        super(settings);
        this.type = rarity;
        this.setDefaultState(this.getDefaultState().with(getAgeProperty(), 0).with(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        Optional<ConeflowerTypes> nextType = type.next(type);
        if(nextType.isPresent()){
            NbtCompound nbtCompound = new NbtCompound();
            SeedTypeHelper.toNbt(nbtCompound, nextType.get().getName(), nextType.get().getType(), nextType.get().getColor());
            getNextSeed(world, pos, nbtCompound);
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    public VoxelShape[] getLowerShape() {
        return AmaranthCropBlock.LOWER_AGE_TO_SHAPE;
    }

    @Override
    public VoxelShape[] getUpperShape() {
        return AmaranthCropBlock.UPPER_AGE_TO_SHAPE;
    }

    @Override
    public IntProperty getAgeProperty() {
        return IntProperty.of("age", 0, MAX_AGE);
    }

    @ClientOnly
    @Override
    protected ItemStack getSeedsItemStack() {
        NbtCompound nbt = new NbtCompound();
        SeedTypeHelper.toNbt(nbt, type.getName(), type.getType(), type.getColor());
        ItemStack seed = new ItemStack(WKItems.CONEFLOWER_SEEDS);
        seed.getOrCreateNbt().copyFrom(nbt);
        return seed;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    public int doubleBlockAge() {
        return 2;
    }
}
