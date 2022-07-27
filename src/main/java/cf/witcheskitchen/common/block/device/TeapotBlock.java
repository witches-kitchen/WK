package cf.witcheskitchen.common.block.device;

import cf.witcheskitchen.api.block.WKDeviceBlock;
import cf.witcheskitchen.common.blockentity.WKTeapotEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TeapotBlock extends WKDeviceBlock implements Waterloggable {
    public TeapotBlock(Settings settings) {
        super(settings);
    }

    @SuppressWarnings("deprecation")
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WKTeapotEntity(pos, state);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            final BlockEntity entity = world.getBlockEntity(pos);
            if (entity instanceof WKTeapotEntity teapotEntity) {
                ItemScatterer.spawn(world, pos, teapotEntity);
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

}
