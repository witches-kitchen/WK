package cf.witcheskitchen.api.block.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.storage.ReadView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WKBlockEntity extends BlockEntity implements BlockEntityTicker<WKBlockEntity> {
    public boolean needsSync;

    public WKBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    protected void readData(ReadView view) {
        needsSync = true;
        super.readData(view);
    }

    public void sync(World world, BlockPos pos) {
        if (world != null && !world.isClient) {
            world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_LISTENERS);
            toUpdatePacket();
        }
    }

    @Override
    public void markDirty() {
        super.markDirty();
        sync(world, pos);
    }

    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    /**
     * Called on both sides to force an update after reload
     */
    public void init() {

    }


    @Override
    public void tick(World world, BlockPos blockPos, BlockState blockState, WKBlockEntity blockEntity) {

    }

    // Server-side Tick
    public void onServerTick(World world, BlockPos blockPos, BlockState blockState, WKBlockEntity blockEntity) {

    }

    // Client-side Tick
    @Environment(EnvType.CLIENT)
    public void onClientTick(World world, BlockPos pos, BlockState state, WKBlockEntity blockEntity) {

    }
}