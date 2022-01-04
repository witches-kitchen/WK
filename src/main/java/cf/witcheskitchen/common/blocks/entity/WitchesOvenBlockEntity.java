package cf.witcheskitchen.common.blocks.entity;

import cf.witcheskitchen.client.gui.screen.handler.WitchesOvenScreenHandler;
import cf.witcheskitchen.common.registry.WKBlockEntityTypes;
import cf.witcheskitchen.common.registry.WKRecipeTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
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

    //searches the furnace output for the given item
    private ItemStack getFurnaceResultFor(ItemStack stack) {
        if (this.world == null || stack.isEmpty()) {
            return ItemStack.EMPTY;
        }
        final Recipe<?> matchingRecipe = world.getRecipeManager()
                .listAllOfType(RecipeType.SMELTING)
                .stream()
                .filter(recipe -> {
                    if (recipe.getIngredients().size() == 1 && recipe.getIngredients().get(0).test(stack)) {
                        return recipe.getOutput().isFood();
                    }
                    return false;
                }).findFirst()
                .orElse(null);

        if (matchingRecipe != null) {
            return matchingRecipe.getOutput().copy();
        }
        return ItemStack.EMPTY;
    }

    public boolean isBurning() {
        return this.burnTime > 0;
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, WKDeviceBlockEntity blockEntity) {
        super.tick(world, pos, state, blockEntity);

        if (this.world != null && !this.world.isClient) {
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
                    .filter(type -> type.getInput().test(this.getStack(this.input)))
                    .findFirst()
                    .orElse(null);

            final ItemStack output;
            if (recipe == null) {
                output = this.getFurnaceResultFor(this.getStack(this.input));
            } else {
                output = recipe.getOutput();//reference to output
            }
            if (output != null && !output.isEmpty()) {
                this.maxProgress = 100;
                if (!this.isBurning() && canCraft(output)) {
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
                if (this.isBurning() && canCraft(output)) {
                    ++this.progress;
                    if (this.progress == this.maxProgress) {
                        this.progress = 0;
                        this.maxProgress = 100;
                        if (this.craftRecipe(output.copy())) {
                            dirty = true;
                        }
                    }
                }
                //TODO: update blockstate
                if (dirty) {
                    this.markDirty();
                }
            } else {
                this.progress = 0;
            }
        }
    }

    // Checks that input and output are valid
    // And we have enough space to craft
    public boolean canCraft(final ItemStack output) {
        if (this.world == null) {
            return false;
        } else if (output.isEmpty()) {
            return false;
        } else {
            final ItemStack stackInOutput = this.getStack(this.output);
            if (stackInOutput.isEmpty()) {
                return true;
            } else if (!stackInOutput.isItemEqualIgnoreDamage(output)) {
                return false;
            } else {
                final int nextCount = stackInOutput.getCount() + output.getCount();
                return (nextCount <= this.getMaxCountPerStack() && nextCount <= output.getMaxCount());
            }
        }
    }

    //More checks and crafts the recipe
    public boolean craftRecipe(final ItemStack output) {
        if (this.world == null) {
            return false;
        } else if (output == null) {
            return false;
        } else if (output.isEmpty()) {
            return false;
        } else if (!canCraft(output)) {
            return false;
        } else {
            final ItemStack stackInOutput = this.getStack(this.output);
            if (stackInOutput.isEmpty()) {
                this.setStack(this.output, output);
            } else if (stackInOutput.isOf(output.getItem())) {
                stackInOutput.increment(output.getCount());
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
