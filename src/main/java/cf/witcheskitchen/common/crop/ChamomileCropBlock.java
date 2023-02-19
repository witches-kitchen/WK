package cf.witcheskitchen.common.crop;

import cf.witcheskitchen.api.crop.WKCropBlock;
import cf.witcheskitchen.common.registry.WKItems;
import cf.witcheskitchen.common.util.TypeHelper;
import cf.witcheskitchen.common.variants.ChamomileTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.Optional;

public class ChamomileCropBlock extends WKCropBlock {
    public static final int MAX_AGE = 4;
    private final ChamomileTypes type;

    public ChamomileCropBlock(Settings settings) {
        this(settings, ChamomileTypes.COMMON);
    }

    public ChamomileCropBlock(Settings settings, ChamomileTypes rarity) {
        super(settings);
        this.type = rarity;
        this.setDefaultState(this.getDefaultState().with(getAgeProperty(), 0));
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        Optional<ChamomileTypes> nextType = type.next(type);
        if(nextType.isPresent()){
            NbtCompound nbtCompound = new NbtCompound();
            TypeHelper.toNbt(nbtCompound, nextType.get().getName(), nextType.get().getType(), nextType.get().getColor());
            getNextSeed(world, pos, nbtCompound);
        }
        super.onBreak(world, pos, state, player);
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
