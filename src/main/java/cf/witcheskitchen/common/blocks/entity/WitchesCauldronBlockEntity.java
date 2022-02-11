package cf.witcheskitchen.common.blocks.entity;

import cf.witcheskitchen.api.fluid.FluidStack;
import cf.witcheskitchen.api.fluid.FluidTank;
import cf.witcheskitchen.api.fluid.IStorageHandler;
import cf.witcheskitchen.api.fluid.WKFluidAPI;
import cf.witcheskitchen.client.network.packet.ParticlePacketHandler;
import cf.witcheskitchen.client.network.packet.SplashParticlePacketHandler;
import cf.witcheskitchen.common.blocks.technical.WitchesCauldronBlock;
import cf.witcheskitchen.common.registry.WKBlockEntityTypes;
import cf.witcheskitchen.common.registry.WKRecipeTypes;
import cf.witcheskitchen.common.registry.WKTags;
import cf.witcheskitchen.common.util.PacketHelper;
import cf.witcheskitchen.common.util.TimeHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class WitchesCauldronBlockEntity extends WKDeviceBlockEntity implements IStorageHandler {

    private static final int TICKS_TO_BOIL = TimeHelper.toTicks(5);
    private static final int DEFAULT_WATER_COLOR = 0x3f76e4;
    private static final int DIRTY_WATER_COLOR = 0x402b2b;
    private static final int MAXIMUM_INGREDIENTS = 7;
    private static final int MINIMUM_INGREDIENTS = 2;
    public static final int TANK_CAPACITY = WKFluidAPI.BUCKET_VOLUME;
    private final FluidTank tank = new FluidTank(TANK_CAPACITY);
    private final Box collectionBox = new Box(this.pos).contract(0.65);
    private int color;
    private int ticksHeated;
    private boolean powered;

    public WitchesCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(WKBlockEntityTypes.WITCHES_CAULDRON, pos, state, MAXIMUM_INGREDIENTS);
        this.color = DEFAULT_WATER_COLOR;
    }

    private static void lavaTick(World world, BlockPos pos, boolean client) {
        final var random = world.getRandom();
        final int i = random.nextInt(50) + 1;
        if (client) {
            final double offsetPos = 0.3D;
            if (i == 1) {
                world.addParticle(ParticleTypes.LAVA, pos.getX() + offsetPos, pos.getY() + offsetPos, pos.getZ() + offsetPos, 0, 0, 0);
            } else if (i == 50) {
                world.addParticle(ParticleTypes.ASH, pos.getX() + offsetPos, pos.getY() + offsetPos, pos.getZ() + offsetPos, 0, 0, 0);
            }
        } else {
            final float pitch = 0.15F;
            if (i == 1) {
                world.playSound(null, pos, SoundEvents.BLOCK_LAVA_POP, SoundCategory.BLOCKS, 0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * pitch);
            } else if (i == 50) {
                world.playSound(null, pos, SoundEvents.BLOCK_LAVA_AMBIENT, SoundCategory.BLOCKS, 0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * pitch);
            }
        }
    }

    public void checkAndCollectIngredient(World world, final ItemEntity entity) {
        // Wait until the cauldron is fully boiling
        if (this.isBoiling()) {
            // You must throw the stack correctly
            if (entity.getBoundingBox().intersects(this.collectionBox)) {
                final ItemStack ingredient = entity.getStack();
                final int emptySlot = this.manager.findAnyEmptySlot();
                if (emptySlot >= 0 && ingredient.isIn(WKTags.VALID_BREW_ITEM)) {
                    this.setStack(emptySlot, ingredient.split(1));
                    final float red = ColorHelper.Argb.getRed(this.color) / 255F;
                    final float green = ColorHelper.Argb.getGreen(this.color) / 255f;
                    final float blue = ColorHelper.Argb.getBlue(this.color) / 255F;
                    PacketHelper.sendToAllTracking(entity, serverPlayer -> SplashParticlePacketHandler.send(serverPlayer, this.getPos(), red, green, blue, 0.5D, 1.0D, 0.5D, (byte) 1));
                    this.markDirty(true);
                    world.playSound(null, pos, SoundEvents.ENTITY_PLAYER_SPLASH, SoundCategory.BLOCKS, 0.2F, 1.0f);
                    entity.kill();
                }
            }
        }
        if (world.getBlockState(pos).get(WitchesCauldronBlock.LIT)) {
            this.manager.clear();
            PacketHelper.sendToAllTracking(entity, serverPlayer -> ParticlePacketHandler.send(serverPlayer, this.getPos(), Registry.PARTICLE_TYPE.getId(ParticleTypes.LAVA), Registry.SOUND_EVENT.getId(SoundEvents.BLOCK_LAVA_EXTINGUISH), (byte) 3));
            entity.kill();
        }
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, WKDeviceBlockEntity blockEntity) {
        final BlockState belowState = world.getBlockState(pos.down());
        boolean sync = false;
        if (this.hasFluid()) {
            if (state.get(WitchesCauldronBlock.LIT)) {
                if (world.getTime() % 10L == 8L) {
                    lavaTick(world, pos, false);
                }
            }
            if (belowState.isIn(WKTags.HEATS_CAULDRON)) {
                final int secondsHeated = TimeHelper.toSeconds(this.ticksHeated);
                if (this.ticksHeated < TICKS_TO_BOIL) {
                    this.ticksHeated++;
                    final int time = TimeHelper.toSeconds(this.ticksHeated);
                    if (secondsHeated != time) {
                        boilWater(time);
                        sync = true;
                    }
                }
            }
        } else if(this.ticksHeated > 0) {
            this.color = DEFAULT_WATER_COLOR;
            this.ticksHeated = 0;
            sync = true;
        }
        if (sync) {
            this.markDirty(true);
        }
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void onClientTick(World world, BlockPos pos, BlockState state, Random random) {
        if (state.get(WitchesCauldronBlock.LIT)) {
            WitchesCauldronBlockEntity.lavaTick(world, pos, true);
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.tank.readStorage(nbt.getCompound("Tank"));
        this.ticksHeated = nbt.getInt("TicksHeated");
        this.color = nbt.getInt("Color");
        this.powered = nbt.getBoolean("Powered");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.put("Tank", tank.writeStorage());
        nbt.putInt("TicksHeated", this.ticksHeated);
        nbt.putInt("Color", this.color);
        nbt.putBoolean("Powered", this.powered);
    }

    @Override
    public int fill(FluidStack stack, Direction side) {
        return this.tank.fill(stack, side);
    }

    @Override
    public boolean canFill(FluidStack stack, Direction side) {
        return (stack.getFluid() == Fluids.WATER || stack.getFluid() == Fluids.LAVA) && this.tank.canFill(stack);
    }

    @NotNull
    @Override
    public FluidStack getFluidStack() {
        return this.tank.getStack();
    }

    @NotNull
    @Override
    public FluidStack drain(FluidStack stack, Direction side) {
        return this.tank.drain(stack, side);
    }

    @NotNull
    @Override
    public FluidStack drain(int maxAmount, Direction side) {
        return this.tank.drain(maxAmount, side);
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

    private void boilWater(int time) {
        switch (time) {
            case 0 -> this.color = DEFAULT_WATER_COLOR;
            case 1 -> this.color = 0x3567cc;
            case 2 -> this.color = 0x3363c4;
            case 3 -> this.color = 0x305db8;
            case 5 -> this.color = 0x2450a6;
        }
    }

    public boolean isHeating() {
        return this.ticksHeated >= TimeHelper.toTicks(1);
    }

    public boolean isBoiling() {
        return this.ticksHeated >= TICKS_TO_BOIL;
    }

    public boolean hasFluid() {
        return !this.tank.isEmpty();
    }

    @Environment(EnvType.CLIENT)
    public double getPercentFilled() {
        return ((((double) tank.getFluidAmount() / this.tank.getCapacity())));
    }

    @Environment(EnvType.CLIENT)
    public int getColor() {
        return this.color;
    }

    @Environment(EnvType.CLIENT)
    public int getTicksHeated() {
        return ticksHeated;
    }

    @Environment(EnvType.CLIENT)
    public boolean isPowered() {
        return powered;
    }
}
