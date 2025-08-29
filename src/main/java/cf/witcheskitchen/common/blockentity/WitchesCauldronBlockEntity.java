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
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ARGB;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class WitchesCauldronBlockEntity extends WKBlockEntityWithInventory implements IStorageHandler {

    public static final int TANK_CAPACITY = WKFluidAPI.BUCKET_VOLUME;
    private static final int TICKS_TO_BOIL = TimeHelper.toTicks(5);
    private static final int DEFAULT_WATER_COLOR = 0x3f76e4;
    private static final int DIRTY_WATER_COLOR = 0x402b2b;
    private static final int MAXIMUM_INGREDIENTS = 7;
    private static final int MINIMUM_INGREDIENTS = 2;
    private final FluidTank tank = new FluidTank(TANK_CAPACITY);
    private final AABB collectionBox = new AABB(this.worldPosition).deflate(0.65);
    private final CauldronBrewingRecipe recipe = null;
    private int color;
    private int ticksHeated;
    private boolean powered;

    public WitchesCauldronBlockEntity(BlockPos pos, BlockState state) {
        super(WKBlockEntityTypes.WITCHES_CAULDRON, pos, state, MAXIMUM_INGREDIENTS);
        this.color = DEFAULT_WATER_COLOR;
    }

    private static void lavaTick(Level world, BlockPos pos, boolean client) {
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
                world.playSound(null, pos, SoundEvents.LAVA_POP, SoundSource.BLOCKS, 0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * pitch);
            } else if (i == 50) {
                world.playSound(null, pos, SoundEvents.LAVA_AMBIENT, SoundSource.BLOCKS, 0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * pitch);
            }
        }
    }

    public void checkAndCollectIngredient(Level world, final ItemEntity entity) {
        // Wait until the cauldron is fully boiling
        if (this.isBoiling()) {
            // You must throw the stack correctly
            if (entity.getBoundingBox().intersects(this.collectionBox)) {
                final ItemStack ingredient = entity.getItem();
                if (ingredient.is(WKTags.RESETS_CAULDRON)) {
                    this.reset(false);
                } else {
                    final int emptySlot = this.manager.findAnyEmptySlot();
                    if (emptySlot >= 0) {
                        this.setItem(emptySlot, ingredient.split(1));
                        if (!this.getItem(emptySlot).isEmpty()) {
                            updateCauldron(this.getItem(emptySlot));
                        }
                    }
                }
                sendPlashPacket(entity);

                if (!world.isClientSide)
                    entity.kill((ServerLevel) world);
            }
        }

        if (world.getBlockState(worldPosition).getValue(WitchesCauldronBlock.LIT)) {
            this.manager.clearContent();
            PacketHelper.sendToAllTracking(entity, serverPlayer -> ParticlePacket.send(serverPlayer, this.getBlockPos(), BuiltInRegistries.PARTICLE_TYPE.getKey(ParticleTypes.LAVA), BuiltInRegistries.SOUND_EVENT.getKey(SoundEvents.LAVA_EXTINGUISH), (byte) 3));

            if (!world.isClientSide)
                entity.kill((ServerLevel) world);
        }
    }

    @Override
    public void tick(Level world, BlockPos pos, BlockState state, WKBlockEntity blockEntity) {
        final BlockState belowState = world.getBlockState(pos.below());
        boolean sync = false;
        if (this.hasFluid()) {
            if (state.getValue(WitchesCauldronBlock.LIT)) {
                if (world.getGameTime() % 10L == 8L) {
                    lavaTick(world, pos, false);
                }
            }
            if (belowState.is(WKTags.HEATS_CAULDRON)) {
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
            this.setChanged();
        }
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void onClientTick(Level world, BlockPos pos, BlockState state, WKBlockEntity wkBlockEntity) {
        if (state.getValue(WitchesCauldronBlock.LIT)) {
            WitchesCauldronBlockEntity.lavaTick(world, pos, true);
        }
    }

    private void sendPlashPacket(ItemEntity trackedEntity) {
        final float red = ARGB.red(this.color) / 255F;
        final float green = ARGB.green(this.color) / 255f;
        final float blue = ARGB.blue(this.color) / 255F;
        PacketHelper.sendToAllTracking(trackedEntity, serverPlayer -> SplashParticlePacket.send(serverPlayer, this.getBlockPos(), red, green, blue, 0.5D, 1.0D, 0.5D, (byte) 6));
        level.playSound(null, worldPosition, SoundEvents.PLAYER_SPLASH, SoundSource.BLOCKS, 0.2F, 1.0f);
    }

    private void reset(boolean fullReset) {
        boilWater(5);
        manager.clearContent();
        powered = false;
        if (fullReset) {
            tank.drain(tank.getCapacity(), null);
        }
        setChanged();
    }

    private void updateCauldron(ItemStack stack) {
        if (!stack.is(WKTags.VALID_BREW_ITEM)) {
            this.color = DIRTY_WATER_COLOR;
            setChanged();
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
        setChanged();
    }

    // TODO: write to NBT or to components?
    //       or some to components and some to NBT?
    @Override
    protected void loadAdditional(ValueInput data) {
        super.loadAdditional(data);
        this.tank.readStorage(data.childOrEmpty("Tank"));
        this.ticksHeated = data.getIntOr("TicksHeated", 0);
        this.color = data.getIntOr("Color", 0);
        this.powered = data.getBooleanOr("Powered", false);
    }

    @Override
    protected void saveAdditional(ValueOutput data) {
        super.saveAdditional(data);
        tank.writeStorage(data.child("Tank"));
        data.putInt("TicksHeated", this.ticksHeated);
        data.putInt("Color", this.color);
        data.putBoolean("Powered", this.powered);
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter components) {
        super.applyImplicitComponents(components);

        var cauldronData = components.get(WKComponents.WITCHES_CAULDRON);
        if (cauldronData != null) {
            ValueInput view = TagValueInput.create(ProblemReporter.DISCARDING, this.getLevel().registryAccess(), cauldronData.tankData());
            this.tank.readStorage(view);
            this.ticksHeated = cauldronData.ticksHeated();
            this.color = cauldronData.color();
            this.powered = cauldronData.powered();
        }
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder componentMapBuilder) {
        super.collectImplicitComponents(componentMapBuilder);
        TagValueOutput view = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, this.getLevel().registryAccess());
        tank.writeStorage(view);
        componentMapBuilder.set(WKComponents.WITCHES_CAULDRON, new WitchesCauldronData(
                view.buildResult(),
                this.ticksHeated,
                this.color,
                this.powered
        ));
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registryLookup) {
        final TagValueOutput data = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, registryLookup);
        saveAdditional(data);
        return data.buildResult();
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
