package cf.witcheskitchen.api.block.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.quiltmc.loader.api.minecraft.ClientOnly;

public class WKBlockEntity extends BlockEntity implements BlockEntityTicker<WKBlockEntity> {
    public boolean needsSync;

    public WKBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void readNbt(NbtCompound pTag) {
        needsSync = true;
        super.readNbt(pTag);
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
        return BlockEntityUpdateS2CPacket.of(this);
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
    @ClientOnly
    public void onClientTick(World world, BlockPos pos, BlockState state, WKBlockEntity blockEntity) {

    }
}