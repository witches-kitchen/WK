package cf.witcheskitchen.common.blocks.entity;

import cf.witcheskitchen.api.fluid.FluidStack;
import cf.witcheskitchen.api.fluid.FluidTank;
import cf.witcheskitchen.api.fluid.IStorageHandler;
import cf.witcheskitchen.api.fluid.WKFluidAPI;
import cf.witcheskitchen.client.network.packet.SplashParticlePacketHandler;
import cf.witcheskitchen.common.blocks.technical.WitchesCauldronBlock;
import cf.witcheskitchen.common.registry.WKBlockEntityTypes;
import cf.witcheskitchen.common.registry.WKTags;
import cf.witcheskitchen.common.util.PacketHelper;
import cf.witcheskitchen.common.util.TimeHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
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

    public WitchesCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(WKBlockEntityTypes.WITCHES_CAULDRON, pos, state, MAXIMUM_INGREDIENTS);
        this.color = DEFAULT_WATER_COLOR;
    }

    private static void lavaTick(World world, BlockPos pos, EnvType side) {
        final Random random = world.getRandom();
        final int i = random.nextInt(50) + 1;
        switch (side) {
            case CLIENT -> {
                final double offsetPos = 0.3D;
                if (i == 1) {
                    world.addParticle(ParticleTypes.LAVA, pos.getX() + offsetPos, pos.getY() + offsetPos, pos.getZ() + offsetPos, 0, 0, 0);
                } else if (i == 50) {
                    world.addParticle(ParticleTypes.ASH, pos.getX() + offsetPos, pos.getY() + offsetPos, pos.getZ() + offsetPos, 0, 0, 0);
                }
            }
            case SERVER -> {
                final float pitch = 0.15F;
                if (i == 1) {
                    world.playSound(null, pos, SoundEvents.BLOCK_LAVA_POP, SoundCategory.BLOCKS, 0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * pitch);
                } else if (i == 50) {
                    world.playSound(null, pos, SoundEvents.BLOCK_LAVA_AMBIENT, SoundCategory.BLOCKS, 0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * pitch);
                }
            }
        }
    }

    public void checkAndCollectIngredient(World world, final ItemEntity entity) {
        // Wait until the cauldron is fully boiling
        if (this.isBoiling()) {
            // You must throw the stack correctly
            if (entity.getBoundingBox().intersects(this.collectionBox)) {
                final Item item = entity.getStack().getItem();
                final ItemStack ingredient = entity.getStack();
                final float red = ColorHelper.Argb.getRed(this.color) / 255F;
                final float green = ColorHelper.Argb.getGreen(this.color) / 255f;
                final float blue = ColorHelper.Argb.getBlue(this.color) / 255F;
                PacketHelper.sendToAllTracking(entity, serverPlayer -> SplashParticlePacketHandler.send(serverPlayer, this.getPos(), red, green, blue, 0.5D, 1.0D, 0.5D, (byte) 6));
                world.playSound(null, pos, SoundEvents.ENTITY_PLAYER_SPLASH, SoundCategory.BLOCKS, 0.2F, 1.0f);
            }
        }
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, WKDeviceBlockEntity blockEntity) {
        final BlockState belowState = world.getBlockState(pos.down());
        boolean sync = false;
        boolean lava = this.hasLava();
//        for (int i = 0 ; i < this.size(); i++) {
//            final var at = this.getStack(i);
//            System.out.println("Position " + i + " contains "+ at + " count: " + at.getCount());
//        }
   //      System.out.println(this.tank.getStack());
        if (this.hasFluid()) {
            if (this.hasLava()) {
                if (world.getTime() % 10L == 8L) {
                    // Runs every 10 ticks
                    WitchesCauldronBlockEntity.lavaTick(world, pos, EnvType.SERVER);
                }
            } else if (this.hasWater()) {
                lava = false;
                if (belowState.isIn(WKTags.HEATS_CAULDRON)) {
                    final int heatTicks = TimeHelper.toSeconds(this.ticksHeated);
                    if (this.ticksHeated < TICKS_TO_BOIL) {
                        this.ticksHeated++;
                    }
                    /*
                     * Done to update bubble particles on each second
                     * And not only when it reaches the ticks required to boil
                     * This way the client knows that it has to render the particles
                     *  When ticksHeated is at least >= 1
                     */
                    final int time = TimeHelper.toSeconds(this.ticksHeated);
                    if (time != heatTicks) {
                        switch (time) {
                            case 0 -> this.color = DEFAULT_WATER_COLOR;
                            case 1 -> this.color = 0x3567cc;
                            case 2 -> this.color = 0x3363c4;
                            case 3 -> this.color = 0x305db8;
                            case 4 -> this.color = 0x2752a8;
                            case 5 -> this.color = 0x2450a6;
                        }
                        sync = true;
                    }
                } else if (this.ticksHeated != 0) {
                    this.ticksHeated = 0;
                    sync = true;
                }
            } else {
                this.ticksHeated = 0;
            }
        }
        if (lava != world.getBlockState(pos).get(WitchesCauldronBlock.LAVA)) {
            this.updateState(world, pos, state, lava);
        }
        if (sync) {
            this.markDirty(true);
        }
    }

    private void updateState(World world, BlockPos pos, BlockState state, boolean lava) {
        world.setBlockState(pos, state.with(WitchesCauldronBlock.LAVA, lava), Block.NOTIFY_ALL);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void onClientTick(World world, BlockPos pos, BlockState state, Random random) {
        super.onClientTick(world, pos, state, random);
        if (this.hasLava()) {
            WitchesCauldronBlockEntity.lavaTick(world, pos, EnvType.CLIENT);
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.tank.readStorage(nbt.getCompound("Tank"));
        this.ticksHeated = nbt.getInt("TicksHeated");
        this.color = nbt.getInt("Color");
    }


    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.put("Tank", tank.writeStorage());
        nbt.putInt("TicksHeated", this.ticksHeated);
        nbt.putInt("Color", this.color);
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

    @Override
    public int fill(FluidStack stack, Direction side) {
        int fillAmount;
        FluidStack filledStack;
        if (this.tank.isEmpty()) {
            // returns the filled amount of fluid
            fillAmount = this.tank.fill(stack, side);
            filledStack = this.tank.getStack();
            if (!filledStack.isEmpty()) {
                filledStack.setNbt(stack.getNbt() == null ? new NbtCompound() : stack.getNbt().copy());
                this.markDirty(false);
            }
            return fillAmount;
            // Otherwise, if both are valid stacks
            // let's fill the remaining space
        } else if (stack.isEqualIgnoreNbt(this.tank.getStack())) {
            fillAmount = this.tank.fill(stack, side); // fills remaining space of the tank
            filledStack = this.tank.getStack();
            if (!filledStack.isEmpty()) {
                this.markDirty(false);
            }
            return fillAmount;
        } else {
            return 0;
        }
    }

    @Override
    public boolean canFill(FluidStack stack, Direction side) {
        return true;
    }

    @NotNull
    @Override
    public FluidStack getStackForTank(int tank) {
        return this.tank.getStack();
    }

    @NotNull
    @Override
    public FluidStack drain(FluidStack stack, Direction side) {
        var drain = this.tank.drain(stack, side);
        if (this.tank.getFluidAmount() == 0) {
            //TODO: invChange
        }
        this.markDirty();
        return drain;
    }

    @NotNull
    @Override
    public FluidStack drain(int maxAmount, Direction side) {
        var drain = this.tank.drain(maxAmount, side);
        if (this.tank.getFluidAmount() == 0) {
            //TODO: invChange
        }
        this.markDirty();
        return drain;
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

    public boolean hasLava() {
        return this.hasFluid() && this.tank.getStack().hasFluid(Fluids.LAVA);
    }

    public boolean hasWater() {
        return this.hasFluid() && this.tank.getStack().hasFluid(Fluids.WATER);
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



}
