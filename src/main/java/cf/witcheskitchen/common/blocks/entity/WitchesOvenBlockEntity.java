package cf.witcheskitchen.common.blocks.entity;

import cf.witcheskitchen.client.gui.screen.handler.WitchesOvenScreenHandler;
import cf.witcheskitchen.common.recipe.WitchesOvenCookingRecipe;
import cf.witcheskitchen.common.registry.WKBlockEntityTypes;
import cf.witcheskitchen.common.registry.WKRecipeTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class WitchesOvenBlockEntity extends WKDeviceBlockEntity implements NamedScreenHandlerFactory {

    private final PropertyDelegate delegate;
    private final int fuel = 0;
    private final int input = 1;
    private final int jar = 2;
    private final int output = 3;
    private final int fume = 4;//fume output
    private int burnTime;
    private int maxBurnTime;
    private int progress;
    private int maxProgress;

    public WitchesOvenBlockEntity(BlockPos pos, BlockState state) {
        super(WKBlockEntityTypes.WITCHES_OVEN, pos, state, 5);
        //will sync values between client and server
        this.delegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> WitchesOvenBlockEntity.this.burnTime;
                    case 1 -> WitchesOvenBlockEntity.this.maxBurnTime;
                    case 2 -> WitchesOvenBlockEntity.this.progress;
                    case 3 -> WitchesOvenBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> WitchesOvenBlockEntity.this.burnTime = value;
                    case 1 -> WitchesOvenBlockEntity.this.maxBurnTime = value;
                    case 2 -> WitchesOvenBlockEntity.this.progress = value;
                    case 3 -> WitchesOvenBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int size() {
                return 4;
            }
        };
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.burnTime = nbt.getShort("BurnTime");
        this.progress = nbt.getShort("Progress");
        this.maxProgress = nbt.getShort("MaxProgress");
        this.maxBurnTime = this.getItemBurnTime(this.getStack(this.fuel));
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putShort("BurnTime", (short) this.burnTime);
        nbt.putShort("Progress", (short) this.progress);
        nbt.putShort("MaxProgress", (short) this.maxProgress);
    }

    public int getItemBurnTime(ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        } else {
            return AbstractFurnaceBlockEntity.createFuelTimeMap().getOrDefault(stack.getItem(), 0);
        }
    }

    public boolean isBurning() {
        return this.burnTime > 0;
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, WKDeviceBlockEntity blockEntity) {
        super.tick(world, pos, state, blockEntity);

        if (world != null && !world.isClient) {
            if (this.isBurning()) {
                this.burnTime--;
            } else {
                //if no fuel remaining
                //decrement progress
                if (this.progress > 0) {
                    this.progress = MathHelper.clamp((this.progress - 2), 0, this.maxProgress);
                }
            }
            boolean dirty = false;
            final var recipe = this.world.getRecipeManager()
                    .listAllOfType(WKRecipeTypes.WITCHES_OVEN_COOKING_RECIPE_TYPE)
                    .stream()
                    .filter(type -> {
                        return type.getInput().test(this.getStack(this.input));
                    }).findFirst()
                    .orElse(null);
            if (recipe != null) {
                this.maxProgress = recipe.getTime();
                if (!this.isBurning()) {
                    this.burnTime = this.getItemBurnTime(this.getStack(this.fuel));
                    this.maxBurnTime = this.burnTime;
                    if (this.isBurning()) {
                        dirty = true;
                        final ItemStack fuelStack = this.getStack(this.fuel);
                        if (fuelStack.getItem().hasRecipeRemainder()) {
                            this.setStack(this.fuel, new ItemStack(fuelStack.getItem().getRecipeRemainder()));
                        } else if (fuelStack.getCount() > 1) {
                            fuelStack.decrement(1);
                        } else if (fuelStack.getCount() == 1) {
                            this.setStack(this.fuel, ItemStack.EMPTY);
                        }
                    }
                }
                if (this.burnTime > 0) {
                    ++this.progress;
                    if (this.progress == this.maxProgress) {
                        this.progress = 0;
                        this.maxProgress = recipe.getTime();
                        if (this.craftRecipe(recipe)) {
                            dirty = true;
                        }
                    }
                }
                //update blockstate
                if (dirty) {
                    this.markDirty();
                }
            } else {
                this.progress = 0;
            }
        }
    }

    public boolean canCraft(final WitchesOvenCookingRecipe recipe) {
        if (this.world == null) {
            return false;
        } else if (recipe == null) {
            return false;
        } else {
            final ItemStack output = this.getStack(this.output);
            final ItemStack firstResult = recipe.getOutput();
            if (output.isEmpty()) {
                return true;
            } else if (!output.isItemEqualIgnoreDamage(firstResult)) {
                return false;
            } else {
                int nextCount = output.getCount() + firstResult.getCount();
                return (nextCount <= this.getMaxCountPerStack() && nextCount <= firstResult.getMaxCount());
            }
        }
    }

    public boolean craftRecipe(final WitchesOvenCookingRecipe recipe) {
        if (recipe == null) {
            return false;
        } else if (!canCraft(recipe)) {
            return false;
        } else {
            final ItemStack outputStack = recipe.getOutput().copy();
            final ItemStack stackInOutput = this.getStack(this.output);
            if (stackInOutput.isEmpty()) {
                this.setStack(this.output, outputStack);
            } else if (stackInOutput.isOf(outputStack.getItem())) {
                stackInOutput.increment(outputStack.getCount());
            }
            this.getStack(this.input).decrement(1);
            return true;
        }
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(this.getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new WitchesOvenScreenHandler(syncId, inv, this, this.delegate);
    }
}
