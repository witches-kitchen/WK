package cf.witcheskitchen.common.block.crop;

import cf.witcheskitchen.api.block.crop.WKCropBlock;
import cf.witcheskitchen.api.interfaces.CropVariants;
import cf.witcheskitchen.api.util.SeedTypeHelper;
import cf.witcheskitchen.common.block.crop.types.SanguinaryTypes;
import cf.witcheskitchen.common.component.WKComponents;
import cf.witcheskitchen.common.registry.WKItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import java.util.Optional;

public class SanguinaryCropBlock extends WKCropBlock implements CropVariants {
    public static final int MAX_AGE = 4;
    private final SanguinaryTypes type;
    private static final IntegerProperty AGE = IntegerProperty.create("age", 0, MAX_AGE);

    public SanguinaryCropBlock(Properties settings) {
        this(settings, SanguinaryTypes.COMMON);
    }

    public SanguinaryCropBlock(Properties settings, SanguinaryTypes rarity) {
        super(settings);
        this.type = rarity;
        this.registerDefaultState(this.defaultBlockState().setValue(getAgeProperty(), 0));
    }

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        Optional<SanguinaryTypes> nextType = type.next(type);
        if (nextType.isPresent()) {
            var component = SeedTypeHelper.toComponent(nextType.get().getName(), nextType.get().getType(), nextType.get().getColor());
            getNextSeed(world, pos, component);
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
        var component = SeedTypeHelper.toComponent(type.getName(), type.getType(), type.getColor());
        ItemStack seed = new ItemStack(WKItems.SANGUINARY_SEEDS);
        seed.set(WKComponents.SEED_TYPE, component);
        return seed;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }
}
