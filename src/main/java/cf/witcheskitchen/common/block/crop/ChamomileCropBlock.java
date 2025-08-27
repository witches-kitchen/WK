package cf.witcheskitchen.common.block.crop;

import cf.witcheskitchen.api.block.crop.WKCropBlock;
import cf.witcheskitchen.api.interfaces.CropVariants;
import cf.witcheskitchen.api.util.SeedTypeHelper;
import cf.witcheskitchen.common.block.crop.types.ChamomileTypes;
import cf.witcheskitchen.common.component.WKComponents;
import cf.witcheskitchen.common.registry.WKItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class ChamomileCropBlock extends WKCropBlock implements CropVariants {
    public static final int MAX_AGE = 4;
    private static final IntProperty AGE = IntProperty.of("age", 0, MAX_AGE);
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
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        Optional<ChamomileTypes> nextType = type.next(type);
        if (nextType.isPresent()) {
            NbtCompound nbtCompound = new NbtCompound();
            var data = SeedTypeHelper.toComponent(nextType.get().getName(), nextType.get().getType(), nextType.get().getColor());
            getNextSeed(world, pos, data);
        }
        return super.onBreak(world, pos, state, player);
    }

    @Override
    public IntProperty getAgeProperty() {
        return AGE;
    }

    @Environment(EnvType.CLIENT)
    @Override
    protected ItemStack getSeedsItemStack() {
        NbtCompound nbt = new NbtCompound();
        var data = SeedTypeHelper.toComponent(type.getName(), type.getType(), type.getColor());
        ItemStack seed = new ItemStack(WKItems.CHAMOMILE_SEEDS);
        seed.set(WKComponents.SEED_TYPE, data);
        return seed;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }
}
