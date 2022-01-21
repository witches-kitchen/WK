package cf.witcheskitchen.common.blocks.entity;

import cf.witcheskitchen.common.blocks.technical.WitchesCauldronBlock;
import cf.witcheskitchen.common.registry.WKBlockEntityTypes;
import cf.witcheskitchen.common.registry.WKTags;
import cf.witcheskitchen.common.util.TimeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class WitchesCauldronBlockEntity extends WKDeviceBlockEntity {

    public static final int[] WATER_COLORS = {4159204,};
    private static final int TICKS_TO_BOIL = TimeHelper.toTicks(5);
    private int waterColor;
    private int ticksHeated;

    public WitchesCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(WKBlockEntityTypes.WITCHES_CAULDRON, pos, state, 3);
        this.waterColor = WATER_COLORS[0]; // Default Water color
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, WKDeviceBlockEntity blockEntity) {
        super.tick(world, pos, state, blockEntity);
        final BlockState belowState = world.getBlockState(pos.down());
        boolean sync = false;
        if (belowState.isIn(WKTags.HEATS_CAULDRON) && this.isFilled()) {
            if (this.ticksHeated < TICKS_TO_BOIL) {
                this.ticksHeated++;
                if (this.ticksHeated == TICKS_TO_BOIL) {
                    sync = true;
                }
            }
        } else if (this.ticksHeated != 0) {
            this.ticksHeated = 0;
            sync = true;
        }
//        final CauldronBrewingRecipe recipe = world.getRecipeManager().listAllOfType(WKRecipeTypes.CAULDRON_BREWING_RECIPE_TYPE)
//                .stream()
//                .filter(type -> type.matches(this, world))
//                .findFirst()
//                .orElse(null);
//
//        if (recipe != null) {
//            this.setWaterColor(recipe.getColor());
//        }

        if (sync) {
            this.markBlockForUpdate(true);
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.waterColor = nbt.getInt("WaterColor");
        this.ticksHeated = nbt.getInt("TicksHeated");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("WaterColor", this.waterColor);
        nbt.putInt("TicksHeated", this.ticksHeated);
    }

    public boolean isBoiling() {
        return this.isFilled() && this.ticksHeated == TICKS_TO_BOIL;
    }

    public boolean isFilled() {
        final Block thisBlock = this.getCachedState().getBlock();
        if (thisBlock instanceof WitchesCauldronBlock block) {
            return block.getWaterLevel(this.getCachedState()) == WitchesCauldronBlock.TOP_LEVEL;
        } else {
            return false;
        }
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        final NbtCompound data = super.toInitialChunkDataNbt();
        writeNbt(data);
        return data;
    }

    private void setWaterColor(int color) {
        this.waterColor = color;
    }

    public int getWaterColor() {
        return this.waterColor;
    }
}
