package cf.witcheskitchen.common.blockentity;

import cf.witcheskitchen.api.block.entity.WKBlockEntity;
import cf.witcheskitchen.api.block.entity.WKBlockEntityWithInventory;
import cf.witcheskitchen.api.fluid.FluidStack;
import cf.witcheskitchen.api.fluid.FluidTank;
import cf.witcheskitchen.api.fluid.IStorageHandler;
import cf.witcheskitchen.api.fluid.WKFluidAPI;
import cf.witcheskitchen.api.util.InventoryHelper;
import cf.witcheskitchen.api.util.PacketHelper;
import cf.witcheskitchen.api.util.TimeHelper;
import cf.witcheskitchen.common.block.WitchesCauldronBlock;
import cf.witcheskitchen.common.component.WKComponents;
import cf.witcheskitchen.common.component.blockentity.WitchesCauldronData;
import cf.witcheskitchen.common.network.packet.ParticlePacket;
import cf.witcheskitchen.common.network.packet.SplashParticlePacket;
import cf.witcheskitchen.common.recipe.CauldronBrewingRecipe;
import cf.witcheskitchen.common.registry.WKBlockEntityTypes;
import cf.witcheskitchen.common.registry.WKTags;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.entity.ItemEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class WitchesCauldronBlockEntity extends WKBlockEntityWithInventory implements IStorageHandler {

    public static final int TANK_CAPACITY = WKFluidAPI.BUCKET_VOLUME;
    private static final int TICKS_TO_BOIL = TimeHelper.toTicks(5);
    private static final int DEFAULT_WATER_COLOR = 0x3f76e4;
    private static final int DIRTY_WATER_COLOR = 0x402b2b;
    private static final int MAXIMUM_INGREDIENTS = 7;
    private static final int MINIMUM_INGREDIENTS = 2;
    private final FluidTank tank = new FluidTank(TANK_CAPACITY);
    private final Box collectionBox = new Box(this.pos).contract(0.65);
    private final CauldronBrewingRecipe recipe = null;
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
                world.addParticleClient(ParticleTypes.LAVA, pos.getX() + offsetPos, pos.getY() + offsetPos, pos.getZ() + offsetPos, 0, 0, 0);
            } else if (i == 50) {
                world.addParticleClient(ParticleTypes.ASH, pos.getX() + offsetPos, pos.getY() + offsetPos, pos.getZ() + offsetPos, 0, 0, 0);
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
                if (ingredient.isIn(WKTags.RESETS_CAULDRON)) {
                    this.reset(false);
                } else {
                    final int emptySlot = this.manager.findAnyEmptySlot();
                    if (emptySlot >= 0) {
                        this.setStack(emptySlot, ingredient.split(1));
                        if (!this.getStack(emptySlot).isEmpty()) {
                            updateCauldron(this.getStack(emptySlot));
                        }
                    }
                }
                sendPlashPacket(entity);

                if (!world.isClient)
                    entity.kill((ServerWorld) world);
            }
        }

        if (world.getBlockState(pos).get(WitchesCauldronBlock.LIT)) {
            this.manager.clear();
            PacketHelper.sendToAllTracking(entity, serverPlayer -> ParticlePacket.send(serverPlayer, this.getPos(), Registries.PARTICLE_TYPE.getId(ParticleTypes.LAVA), Registries.SOUND_EVENT.getId(SoundEvents.BLOCK_LAVA_EXTINGUISH), (byte) 3));

            if (!world.isClient)
                entity.kill((ServerWorld) world);
        }
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, WKBlockEntity blockEntity) {
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
                //TODO: find recipe
            }
        } else if (this.ticksHeated > 0) {
            this.color = DEFAULT_WATER_COLOR;
            this.ticksHeated = 0;
            sync = true;
        }
        if (sync) {
            this.markDirty();
        }
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void onClientTick(World world, BlockPos pos, BlockState state, WKBlockEntity wkBlockEntity) {
        if (state.get(WitchesCauldronBlock.LIT)) {
            WitchesCauldronBlockEntity.lavaTick(world, pos, true);
        }
    }

    private void sendPlashPacket(ItemEntity trackedEntity) {
        final float red = ColorHelper.getRed(this.color) / 255F;
        final float green = ColorHelper.getGreen(this.color) / 255f;
        final float blue = ColorHelper.getBlue(this.color) / 255F;
        PacketHelper.sendToAllTracking(trackedEntity, serverPlayer -> SplashParticlePacket.send(serverPlayer, this.getPos(), red, green, blue, 0.5D, 1.0D, 0.5D, (byte) 6));
        world.playSound(null, pos, SoundEvents.ENTITY_PLAYER_SPLASH, SoundCategory.BLOCKS, 0.2F, 1.0f);
    }

    private void reset(boolean fullReset) {
        boilWater(5);
        manager.clear();
        powered = false;
        if (fullReset) {
            tank.drain(tank.getCapacity(), null);
        }
        markDirty();
    }

    private void updateCauldron(ItemStack stack) {
        if (!stack.isIn(WKTags.VALID_BREW_ITEM)) {
            this.color = DIRTY_WATER_COLOR;
            markDirty();
            return;
        }

        final int i = InventoryHelper.countInSet(manager.getStacks());
        if (i >= MINIMUM_INGREDIENTS) {
            powered = true;
        }
        switch (i) {
            case 1 -> color = 0x6decf2;
            case 2 -> color = 0x2495ff;
            case 3 -> color = 0x8936ff;
        }
        markDirty();
    }

    // TODO: write to NBT or to components?
    //       or some to components and some to NBT?
    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.tank.readStorage(nbt.getCompound("Tank").orElseThrow());
        this.ticksHeated = nbt.getInt("TicksHeated").orElseThrow();
        this.color = nbt.getInt("Color").orElseThrow();
        this.powered = nbt.getBoolean("Powered").orElseThrow();
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.put("Tank", tank.writeStorage());
        nbt.putInt("TicksHeated", this.ticksHeated);
        nbt.putInt("Color", this.color);
        nbt.putBoolean("Powered", this.powered);
    }

    @Override
    protected void readComponents(ComponentsAccess components) {
        super.readComponents(components);

        var cauldronData = components.get(WKComponents.WITCHES_CAULDRON);
        if (cauldronData != null) {
            this.tank.readStorage(cauldronData.tankData());
            this.ticksHeated = cauldronData.ticksHeated();
            this.color = cauldronData.color();
            this.powered = cauldronData.powered();
        }
    }

    @Override
    protected void addComponents(ComponentMap.Builder componentMapBuilder) {
        super.addComponents(componentMapBuilder);
        componentMapBuilder.add(WKComponents.WITCHES_CAULDRON, new WitchesCauldronData(
                tank.writeStorage(),
                this.ticksHeated,
                this.color,
                this.powered
        ));
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        final NbtCompound data = new NbtCompound();
        writeNbt(data, registryLookup);
        return data;
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

    public int getColor() {
        return this.color;
    }

    public int getTicksHeated() {
        return ticksHeated;
    }

    public boolean isPowered() {
        return powered;
    }
}
