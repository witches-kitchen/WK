package cf.witcheskitchen.api.block;

import cf.witcheskitchen.api.block.entity.WKBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;


/**
 * <p>
 * Base class for WitchesKitchen's blocks with entity.
 * </p>
 * <p>
 * It simply creates a ticking context, Nothing else.
 * </p>
 */
public abstract class WKBlockWithEntity extends Block implements BlockEntityProvider {

    public WKBlockWithEntity(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return (tickerWorld, pos, tickerState, blockEntity) -> {
            if (world != null) {
                if (blockEntity instanceof WKBlockEntity ticker) {
                    ticker.tick(tickerWorld, pos, tickerState, ticker);
                    if (world.isClient()) {
                        ticker.onClientTick(world, pos, state, ticker);
                    } else {
                        ticker.onServerTick(world, pos, state, ticker);
                    }
                }
            }
        };
    }
}
