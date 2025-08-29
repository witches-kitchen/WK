package cf.witcheskitchen.common.block.crop;

import cf.witcheskitchen.api.block.crop.WKCropBlock;
import cf.witcheskitchen.api.interfaces.CropVariants;
import cf.witcheskitchen.api.util.SeedTypeHelper;
import cf.witcheskitchen.common.block.crop.types.ChamomileTypes;
import cf.witcheskitchen.common.component.WKComponents;
import cf.witcheskitchen.common.registry.WKItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.Optional;

public class ChamomileCropBlock extends WKCropBlock implements CropVariants {
    public static final int MAX_AGE = 4;
    private static final IntegerProperty AGE = IntegerProperty.create("age", 0, MAX_AGE);
    private final ChamomileTypes type;

    public ChamomileCropBlock(Properties settings) {
        this(settings, ChamomileTypes.COMMON);
    }

    public ChamomileCropBlock(Properties settings, ChamomileTypes rarity) {
        super(settings);
        this.type = rarity;
        this.registerDefaultState(this.defaultBlockState().setValue(getAgeProperty(), 0));
    }

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        Optional<ChamomileTypes> nextType = type.next(type);
        if (nextType.isPresent()) {
            CompoundTag nbtCompound = new CompoundTag();
            var data = SeedTypeHelper.toComponent(nextType.get().getName(), nextType.get().getType(), nextType.get().getColor());
            getNextSeed(world, pos, data);
        }
        return super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Environment(EnvType.CLIENT)
    @Override
    protected ItemStack getSeedsItemStack() {
        CompoundTag nbt = new CompoundTag();
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
