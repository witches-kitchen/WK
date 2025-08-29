package cf.witcheskitchen.api.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;

public class WKBlockEntity extends BlockEntity implements BlockEntityTicker<WKBlockEntity> {
    public boolean needsSync;

    public WKBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    protected void loadAdditional(ValueInput view) {
        needsSync = true;
        super.loadAdditional(view);
    }

    public void sync(Level world, BlockPos pos) {
        if (world != null && !world.isClientSide) {
            world.sendBlockUpdated(pos, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
            getUpdatePacket();
        }
    }

    @Override
    public void setChanged() {
        super.setChanged();
        sync(level, worldPosition);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    /**
     * Called on both sides to force an update after reload
     */
    public void init() {

    }


    @Override
    public void tick(Level world, BlockPos blockPos, BlockState blockState, WKBlockEntity blockEntity) {

    }

    // Server-side Tick
    public void onServerTick(Level world, BlockPos blockPos, BlockState blockState, WKBlockEntity blockEntity) {

    }

    // Client-side Tick
    @Environment(EnvType.CLIENT)
    public void onClientTick(Level world, BlockPos pos, BlockState state, WKBlockEntity blockEntity) {

    }
}