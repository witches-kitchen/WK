package cf.witcheskitchen.common.blocks.entity;

import cf.witcheskitchen.common.registry.WKBlockEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WitchesCauldronBlockEntity extends WKDeviceBlockEntity {

    // TODO: find hex colors
    public static final int[] WATER_COLORS = {
            0x3ab3da
    };
    private int waterColor;

    public WitchesCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(WKBlockEntityTypes.WITCHES_CAULDRON, pos, state, 3);
        this.waterColor = WATER_COLORS[0]; // Default Water color
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, WKDeviceBlockEntity blockEntity) {
        super.tick(world, pos, state, blockEntity);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.waterColor = nbt.getInt("WaterColor");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("WaterColor", this.waterColor);
    }

    public int getWaterColor() {
        return this.waterColor;
    }
}
