package cf.witcheskitchen.common.blocks.entity;

import cf.witcheskitchen.api.IDeviceExperienceHandler;
import cf.witcheskitchen.client.gui.screen.handler.WitchesOvenScreenHandler;
import cf.witcheskitchen.common.blocks.technical.WitchesOvenBlock;
import cf.witcheskitchen.common.recipe.WitchesOvenCookingRecipe;
import cf.witcheskitchen.common.registry.WKBlockEntityTypes;
import cf.witcheskitchen.common.registry.WKRecipeTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class WitchesOvenBlockEntity extends WKDeviceBlockEntity implements NamedScreenHandlerFactory, IDeviceExperienceHandler {

    private final PropertyDelegate delegate;
    private final int fuel = 0;
    private final int input = 1;
    private final int output = 2;
    private final int extra = 3;//extra output
    private int burnTime;
    private int maxBurnTime;
    private int progress;
    private int maxProgress;
    private float experience;

    public WitchesOvenBlockEntity(BlockPos pos, BlockState state) {
        super(WKBlockEntityTypes.WITCHES_OVEN, pos, state, 4);
        //Default values for witches oven smelting recipes is 100
        this.maxProgress = 100;
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
        this.experience = nbt.getFloat("Experience");
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putShort("BurnTime", (short) this.burnTime);
        nbt.putShort("Progress", (short) this.progress);
        nbt.putShort("MaxProgress", (short) this.maxProgress);
        nbt.putFloat("Experience", this.experience);
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

    /**
     * Finds the matching recipe for the given input
     *
     * @param world World
     * @param input ItemStack
     * @return possible return types are SmeltingRecipe (furnace) and WitchesOvenCookingRecipe (witches oven) or null if none
     */
    private Recipe<?> findMatchingRecipeFor(World world, final ItemStack input) {
        if (input.isEmpty()) {
            return null;
        } else if (input.isFood()) {
            return world.getRecipeManager().listAllOfType(RecipeType.SMELTING)
                    .stream()
                    .filter(recipe -> {
                        if (recipe.getIngredients().size() == 1 && recipe.getIngredients().get(0).test(input)) {
                            return recipe.getOutput().isFood();
                        } return false;
                    }).findFirst()
                    .orElse(null);
        } else {
            return world.getRecipeManager().listAllOfType(WKRecipeTypes.WITCHES_OVEN_COOKING_RECIPE_TYPE)
                    .stream()
                    .filter(type -> type.getInput().test(input))
                    .findFirst()
                    .orElse(null);
        }
    }

    private float getExperience(Recipe<?> recipe) {
        if (recipe instanceof SmeltingRecipe smelting) {
            return smelting.getExperience();
        } else {
            if (recipe instanceof WitchesOvenCookingRecipe cooking) {
                return cooking.getXp();
            }
        }
        return 0.0F;
    }

    //Returns the outputs of the given recipe
    private DefaultedList<ItemStack> getOutputsFrom(final Recipe<?> recipe)  {
        if (recipe instanceof SmeltingRecipe) {
            return DefaultedList.ofSize(1, recipe.getOutput());
        } else if (recipe instanceof WitchesOvenCookingRecipe ovenRecipe) {
            return ovenRecipe.getOutputs();
        } else {
            return DefaultedList.of();
        }
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
            final var recipe = this.findMatchingRecipeFor(world, this.getStack(this.input));
            if (recipe != null) {
                final DefaultedList<ItemStack> outputs = this.getOutputsFrom(recipe);
                if (outputs != null && !outputs.isEmpty()) {
                    if (!this.isBurning() && canCraft(outputs)) {
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
                    if (this.isBurning() && canCraft(outputs)) {
                        ++this.progress;
                        if (this.progress == this.maxProgress) {
                            this.progress = 0;
                            this.maxProgress = 100;

                            if (this.craftRecipe(outputs, this.getExperience(recipe))) {
                                dirty = true;
                            }
                        }
                    }
                    if (state.get(Properties.LIT) != this.isBurning()) {
                        final BlockState nextState = state.with(WitchesOvenBlock.LIT, this.burnTime > 0);
                        world.setBlockState(this.pos, nextState, Block.NOTIFY_ALL);
                        dirty = true;
                    }
                    if (dirty) {
                        this.markDirty();
                    }
                } else {
                    this.progress = 0;
                }
            } else {
                this.progress = 0;
            }
        }
    }

    // Checks that input and output are valid
    // And we have enough space to craft
    public boolean canCraft(final DefaultedList<ItemStack> outputs) {
        if (this.world == null) {
            return false;
        } else if (outputs.isEmpty()) {
            return false;
        } else {
            final ItemStack stackInOutput = this.getStack(this.output);
            final ItemStack recipeOutput = outputs.get(0);
            final int nextOutputCount = stackInOutput.getCount() + recipeOutput.getCount();

            if (outputs.size() > 1) {//Means recipe has an extra output
                final ItemStack recipeExtra = outputs.get(1);
                final ItemStack stackInExtra = this.getStack(this.extra);
                final int nextExtraCount = stackInExtra.getCount() + recipeExtra.getCount();
                if (stackInOutput.isEmpty() && stackInExtra.isEmpty()) {
                    return true;
                } else if (stackInOutput.isEmpty() || stackInExtra.isEmpty()) {
                    if (stackInOutput.isEmpty()) {
                        //if first output is empty
                        //extra output is not
                        if (!stackInExtra.isItemEqualIgnoreDamage(recipeExtra)) {
                            return false;
                        }
                    }
                    if (stackInExtra.isEmpty()) {
                        //if extra output is empty
                        //we know first output is not empty
                        if (!stackInOutput.isItemEqualIgnoreDamage(recipeOutput)) {
                            return false;
                        }
                    }
                }
                if (nextOutputCount <= this.getMaxCountPerStack() && nextOutputCount <= recipeOutput.getMaxCount()) {
                    return nextExtraCount <= this.getMaxCountPerStack() && nextExtraCount <= recipeExtra.getMaxCount();
                }
            } else {
                // Otherwise, there is only 1 output
                if (stackInOutput.isEmpty()) {
                    return true;
                } else if (!stackInOutput.isItemEqualIgnoreDamage(recipeOutput)) {
                    return false;
                } else {
                    return nextOutputCount <= this.getMaxCountPerStack() && nextOutputCount <= recipeOutput.getMaxCount();
                }
            }
        }
        return false;
    }

    //More checks to craft the recipe and update experience
    public boolean craftRecipe(final DefaultedList<ItemStack> outputs, final float experience) {
        if (this.world == null) {
            return false;
        } else if (outputs == null) {
            return false;
        } else if (outputs.isEmpty()) {
            return false;
        } else if (!canCraft(outputs)) {
            return false;
        } else {
            final ItemStack stackInOutput = this.getStack(this.output);
            final ItemStack stackInExtra = this.getStack(this.extra);
            final ItemStack recipeOutput = outputs.get(0);
            if (outputs.size() > 1) {
                //means this recipe has an extra
                final ItemStack recipeExtra = outputs.get(1);
                if (stackInOutput.isEmpty() && stackInExtra.isEmpty()) {
                    this.setStack(this.output, recipeOutput.copy());
                    this.setStack(this.extra, recipeExtra.copy());
                } else if (stackInOutput.isOf(recipeOutput.getItem()) && stackInExtra.isOf(recipeExtra.getItem())) {
                    stackInOutput.increment(recipeOutput.getCount());
                    stackInExtra.increment(recipeExtra.getCount());
                } else {
                    if (!stackInExtra.isEmpty()) {
                        //If extra output stack is not empty
                        //We can assume the first output is empty
                        //So we just increment extra
                        //And set the first output
                        stackInExtra.increment(recipeExtra.getCount());
                        this.setStack(this.output, recipeOutput.copy());
                    }
                    if (!stackInOutput.isEmpty()) {
                        //If first output stack is not empty
                        //We can assume the extra is empty
                        //So we just increment the first output
                        //And set the extra output
                        stackInOutput.increment(recipeOutput.getCount());
                        this.setStack(this.extra, recipeExtra.copy());
                    }
                }
            } else {
                //Otherwise, this is a normal recipe
                if (stackInOutput.isEmpty()) {
                    this.setStack(this.output, recipeOutput.copy());
                } else if (stackInOutput.isOf(recipeOutput.getItem())) {
                    stackInOutput.increment(recipeOutput.getCount());
                }
            }
            this.experience += experience;
            this.getStack(this.input).decrement(1);
            return true;
        }
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText("screen.title.witcheskitchen.witches_oven");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new WitchesOvenScreenHandler(syncId, inv, this, this.delegate);
    }

    //from IDeviceExperienceHandler
    @Override
    public void dropExperience(ServerWorld world, Vec3d playerPos) {
        dropExperience(world, playerPos, this.experience);
        this.experience = 0;
    }

    private static void dropExperience(ServerWorld world, Vec3d pos, float experience) {
        int i = MathHelper.floor(experience);
        final float f = MathHelper.fractionalPart(experience);
        if (f != 0.0F && Math.random() < (double) f) {
            ++i;
        }
        ExperienceOrbEntity.spawn(world, pos, i);
    }
}
