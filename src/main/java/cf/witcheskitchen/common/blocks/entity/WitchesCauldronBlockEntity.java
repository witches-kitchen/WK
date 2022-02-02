package cf.witcheskitchen.common.blocks.entity;

import cf.witcheskitchen.api.fluid.FluidStack;
import cf.witcheskitchen.api.fluid.FluidTank;
import cf.witcheskitchen.api.fluid.IStorageHandler;
import cf.witcheskitchen.api.fluid.WKFluidAPI;
import cf.witcheskitchen.client.network.packet.ParticleHandlerPacket;
import cf.witcheskitchen.common.blocks.technical.WitchesCauldronBlock;
import cf.witcheskitchen.common.registry.WKBlockEntityTypes;
import cf.witcheskitchen.common.registry.WKParticleTypes;
import cf.witcheskitchen.common.registry.WKSounds;
import cf.witcheskitchen.common.registry.WKTags;
import cf.witcheskitchen.common.util.PacketHelper;
import cf.witcheskitchen.common.util.TimeHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class WitchesCauldronBlockEntity extends WKDeviceBlockEntity implements IStorageHandler {

    private static final int TICKS_TO_BOIL = TimeHelper.toTicks(5);
    private static final int DEFAULT_COLOR = 0x3f76e4;
    private final FluidTank tank = new FluidTank(WKFluidAPI.BUCKET_VOLUME);
    private final Box collectionBox = new Box(this.pos).contract(0.65);
    private final int color;
    private int ticksHeated;
    private int ticks;

    public WitchesCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(WKBlockEntityTypes.WITCHES_CAULDRON, pos, state, 7);
        this.color = DEFAULT_COLOR;
    }

    private static void lavaTick(World world, BlockPos pos, EnvType side, Inventory cauldron) {
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
        if (this.isBoiling()) {
            int i = this.manager.findAnyEmptySlot();
            if (i >= 0) {
                world.getEntitiesByType(EntityType.ITEM, this.collectionBox, possibleIngredient -> true).forEach(itemEntity -> {
                    this.setStack(i, entity.getStack());
                    PacketHelper.sendToAllTracking(entity, serverPlayer -> ParticleHandlerPacket.send(serverPlayer, this.getPos(), Registry.PARTICLE_TYPE.getId(ParticleTypes.SPLASH), Registry.SOUND_EVENT.getId(SoundEvents.ENTITY_PLAYER_SPLASH), (byte) 8));
                    entity.kill();
                });
            }
        }
        if (this.tank.getStack().hasFluid(Fluids.LAVA)) {
            this.manager.clear();
            PacketHelper.sendToAllTracking(entity, serverPlayer -> ParticleHandlerPacket.send(serverPlayer, this.getPos(), Registry.PARTICLE_TYPE.getId(ParticleTypes.LAVA), Registry.SOUND_EVENT.getId(SoundEvents.BLOCK_LAVA_EXTINGUISH), (byte) 3));
            entity.kill();
        }
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, WKDeviceBlockEntity blockEntity) {
        final BlockState belowState = world.getBlockState(pos.down());
        boolean sync = false;
        boolean lava = this.hasLava();
        if (this.hasFluid()) {
            if (this.hasLava()) {
                if (world.getTime() % 10L == 8L) { // 10 ticks
                    WitchesCauldronBlockEntity.lavaTick(world, pos, EnvType.SERVER, this.manager);
                }
            } else if (this.hasWater()) {
                lava = false;
                if (belowState.isIn(WKTags.HEATS_CAULDRON)) {
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
            }
        }
        this.updateState(world, pos, state, lava);
        if (sync) {
            this.markDirty(true);
        }
    }

    private void updateState(World world, BlockPos pos, BlockState state, boolean lava) {
        world.setBlockState(pos, state.with(WitchesCauldronBlock.LAVA, lava), Block.NOTIFY_ALL);
    }

    // Render particles
    @Environment(EnvType.CLIENT)
    @Override
    public void onClientTick(World world, BlockPos pos, BlockState state, Random random) {
        super.onClientTick(world, pos, state, random);
        if (this.hasLava()) {
            WitchesCauldronBlockEntity.lavaTick(world, pos, EnvType.CLIENT, this.manager);
            return;
        }
        if (Fluids.WATER == this.tank.getStack().getFluid() && this.isBoiling()) {
            float depth = (float) (((this.getPercentFilled() - 1) * (0.4D)) + (0.6D));
            final double r = ((this.color >> 16) & 0xff) / 255F;
            final double g = ((this.color >> 8) & 0xff) / 255F;
            final double b = (this.color & 0xff) / 255F;
            double particleX = 0.2 + (random.nextDouble() * 0.6);
            double particleZ = 0.2 + (random.nextDouble() * 0.6);
            for (int i = 0; i < 6; i++) {
                world.addParticle((ParticleEffect) WKParticleTypes.BUBBLE, pos.getX() + particleX, pos.getY() + depth, pos.getZ() + particleZ, r, g, b);
                world.playSound(null, pos, WKSounds.CAULDRON_BOIL_EVENT, SoundCategory.BLOCKS, 0.8f, 0.7f);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public double getPercentFilled() {
        return ((((double) tank.getFluidAmount() / this.tank.getCapacity())));
    }


    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.tank.readStorage(nbt.getCompound("Tank"));
        this.ticksHeated = nbt.getInt("TicksHeated");
    }


    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.put("Tank", tank.writeStorage());
        nbt.putInt("TicksHeated", this.ticksHeated);
    }

    @Environment(EnvType.CLIENT)
    public int getColor() {
        return DEFAULT_COLOR;
    }

    public boolean isBoiling() {
        return this.hasFluid() && this.ticksHeated == TICKS_TO_BOIL;
    }

    public boolean hasFluid() {
        return !this.tank.isEmpty();
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
        } else if (stack.isEqualTo(this.tank.getStack())) {
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
        if (tank == 0) {
            return this.tank.getStack();
        } else {
            return FluidStack.EMPTY;
        }
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

    public boolean hasLava() {
        return this.hasFluid() && this.tank.getStack().hasFluid(Fluids.LAVA);
    }

    public boolean hasWater() {
        return this.hasFluid() && this.tank.getStack().hasFluid(Fluids.WATER);
    }

}
