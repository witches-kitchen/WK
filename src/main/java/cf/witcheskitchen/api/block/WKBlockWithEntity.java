package cf.witcheskitchen.api.block;

import cf.witcheskitchen.api.block.entity.WKBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;


/**
 * <p>
 * Base class for WitchesKitchen's blocks with entity.
 * </p>
 * <p>
 * It simply creates a ticking context, Nothing else.
 * </p>
 */
public abstract class WKBlockWithEntity extends Block implements EntityBlock {

    public WKBlockWithEntity(Properties settings) {
        super(settings);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return (tickerWorld, pos, tickerState, blockEntity) -> {
            if (world != null) {
                if (blockEntity instanceof WKBlockEntity ticker) {
                    ticker.tick(tickerWorld, pos, tickerState, ticker);
                    if (world.isClientSide()) {
                        ticker.onClientTick(world, pos, state, ticker);
                    } else {
                        ticker.onServerTick(world, pos, state, ticker);
                    }
                }
            }
        };
    }
}
