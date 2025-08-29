package cf.witcheskitchen.common.blockentity;

import cf.witcheskitchen.WitchesKitchen;
import cf.witcheskitchen.api.block.entity.IExperienceHandler;
import cf.witcheskitchen.api.block.entity.WKBlockEntity;
import cf.witcheskitchen.api.block.entity.WKBlockEntityWithInventory;
import cf.witcheskitchen.api.util.InventoryManager;
import cf.witcheskitchen.common.block.WitchesOvenBlock;
import cf.witcheskitchen.common.recipe.OvenCookingRecipe;
import cf.witcheskitchen.common.registry.WKBlockEntityTypes;
import cf.witcheskitchen.common.registry.WKRecipeTypes;
import cf.witcheskitchen.common.screenhandler.WitchesOvenScreenHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class WitchesOvenBlockEntity extends WKBlockEntityWithInventory implements IExperienceHandler, MenuProvider {

    // Default cooking value for vanilla recipes is 200
    private static final int DEFAULT_COOKING_TIME = 200;
    private final InventoryManager<WitchesOvenBlockEntity> passiveInventory;
    private final ContainerData propertyDelegate;
    private final int[] passiveProgress;
    private final int fuel = 0;
    private final int input = 1;
    private final int output = 2;
    private final int extra = 3;
    private int burnTime;
    private int maxBurnTime;
    private int activeProgress;
    private int maxProgress;
    private float experience;

    public WitchesOvenBlockEntity(BlockPos pos, BlockState state) {
        super(WKBlockEntityTypes.WITCHES_OVEN, pos, state, 4);
        // Passive cooking inventory manager
        this.passiveInventory = new InventoryManager<>(this, 4);
        this.maxProgress = DEFAULT_COOKING_TIME;
        // Passive cooking times
        this.passiveProgress = new int[4];
        // Sync the values between client and server
        this.propertyDelegate = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> WitchesOvenBlockEntity.this.burnTime;
                    case 1 -> WitchesOvenBlockEntity.this.maxBurnTime;
                    case 2 -> WitchesOvenBlockEntity.this.activeProgress;
                    case 3 -> WitchesOvenBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> WitchesOvenBlockEntity.this.burnTime = value;
                    case 1 -> WitchesOvenBlockEntity.this.maxBurnTime = value;
                    case 2 -> WitchesOvenBlockEntity.this.activeProgress = value;
                    case 3 -> WitchesOvenBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    private static void dropExperience(ServerLevel world, Vec3 pos, float experience) {
        int i = Mth.floor(experience);
        final float f = Mth.frac(experience);
        if (f != 0.0F && Math.random() < (double) f) {
            ++i;
        }
        ExperienceOrb.award(world, pos, i);
    }

    /**
     * Finds the matching recipe for the given input
     *
     * @param world World
     * @param input ItemStack
     * @return possible return types are optional SmeltingRecipe (furnace) OvenCookingRecipe (witches oven), or Optional.empty()
     */
    private static Optional<Recipe<?>> findMatchingRecipeFor(ServerLevel world, final ItemStack input) {
        Objects.requireNonNull(world);
        Objects.requireNonNull(input);
        if (input.isEmpty()) {
            return Optional.empty();
        }
        final Optional<OvenCookingRecipe> optionalOvenRecipe = getOvenRecipe(world, input);
        if (optionalOvenRecipe.isPresent()) {
            return Optional.of(optionalOvenRecipe.get());
        }
        if (input.has(DataComponents.FOOD)) {
            final Optional<SmeltingRecipe> optional = world.recipeAccess()
                    .getAllOfType(RecipeType.SMELTING)
                    .stream()
                    .filter(entry -> {
                        var recipe = entry.value();
                        final List<Ingredient> ingredients = recipe.placementInfo().ingredients();
                        if (ingredients.size() == 1 && ingredients.get(0).test(input)) {
                            return recipe.assemble(new SingleRecipeInput(input), world.registryAccess()).has(DataComponents.FOOD);
                        }
                        return false;
                    }).findFirst().map(RecipeHolder::value);
            if (optional.isPresent()) return Optional.of(optional.get());
        }
        return Optional.empty();
    }

    /**
     * Returns an Optional recipe that matches the given input stack
     */
    private static Optional<OvenCookingRecipe> getOvenRecipe(ServerLevel world, ItemStack input) {
        return world.recipeAccess().getAllOfType(WKRecipeTypes.WITCHES_OVEN_COOKING_RECIPE_TYPE)
                .stream()
                .filter(type -> type.value().input().test(input))
                .findFirst()
                .map(RecipeHolder::value);
    }

    @Override
    public void loadAdditional(ValueInput data) {
        super.loadAdditional(data);
        // Load Inventories
        this.passiveInventory.clearContent();
        ContainerHelper.loadAllItems(data.childOrEmpty("PassiveInventory"), this.getStacksOnTop());
        this.burnTime = data.getShortOr("BurnTime", (short) 0);
        this.activeProgress = data.getShortOr("Progress", (short) 0);
        if (data.getIntArray("PassiveProgress").isPresent()) {
            System.arraycopy(data.getIntArray("PassiveProgress").orElseThrow(), 0, this.passiveProgress, 0, Math.min(this.maxProgress, 4));
        }
        this.maxProgress = data.getShortOr("MaxProgress", (short) 0);
        this.maxBurnTime = this.getItemBurnTime(this.level, this.getItem(this.fuel));
        this.experience = data.getFloatOr("Experience", 0f);
    }

    @Override
    protected void saveAdditional(ValueOutput data) {
        super.saveAdditional(data);
        // Save Inventories
        ContainerHelper.saveAllItems(data.child("PassiveInventory"), this.getStacksOnTop());
        data.putShort("BurnTime", (short) this.burnTime);
        data.putShort("Progress", (short) this.activeProgress);
        data.putIntArray("PassiveProgress", this.passiveProgress);
        data.putShort("MaxProgress", (short) this.maxProgress);
        data.putFloat("Experience", this.experience);
    }

    public boolean isBurning() {
        return this.burnTime > 0;
    }

    /**
     * Inserts a stack to the passive inventory if there is enough room.
     */
    public boolean putItemOnTop(ItemStack stack) {
        for (int i = 0; i < this.passiveInventory.getContainerSize(); i++) {
            final ItemStack stackOnTop = this.passiveInventory.getItem(i);
            if (stackOnTop.isEmpty()) { //We don't want to replace an item being cooked
                this.passiveProgress[i] = 0; // reset progress
                this.passiveInventory.setItem(i, stack.split(1)); // we only want to cook 1 stack at the time
                this.setChanged();
                return true;
            }
        }
        return false;
    }

    @Override
    public void tick(Level world, BlockPos pos, BlockState state, WKBlockEntity blockEntity) {
        super.tick(world, pos, state, blockEntity);
        if (world.isClientSide()) return;
        if (!state.getFluidState().isEmpty()) return;
        if (this.isBurning()) {
            this.burnTime--;
        } else {
            //if no fuel remaining
            //decrement progress
            if (this.activeProgress > 0) {
                this.activeProgress = Mth.clamp((this.activeProgress - 2), 0, this.maxProgress);
            }
        }
        boolean dirty = false;
        final Optional<Recipe<?>> optionRecipe = findMatchingRecipeFor((ServerLevel) world, this.getItem(this.input));
        if (optionRecipe.isPresent()) {
            final Recipe<?> recipe = optionRecipe.get();
            final List<ItemStack> outputs = this.getResults(recipe, world);
            this.maxProgress = getCookingTime(recipe);
            if (outputs != null && !outputs.isEmpty()) {
                if (!this.isBurning() && canCraft(outputs)) {
                    dirty = true;
                    this.burnTime = this.getItemBurnTime(this.getLevel(), this.getItem(this.fuel));
                    this.maxBurnTime = this.burnTime;
                    if (this.isBurning()) {
                        final ItemStack fuelStack = this.getItem(this.fuel);
                        if (!fuelStack.getItem().getRecipeRemainder(fuelStack).isEmpty()) {
                            this.setItem(this.fuel, fuelStack.getItem().getRecipeRemainder(fuelStack));
                        } else if (fuelStack.getCount() > 1) {
                            fuelStack.shrink(1);
                        } else if (fuelStack.getCount() == 1) {
                            this.setItem(this.fuel, ItemStack.EMPTY);
                        }
                    }
                }
                if (this.isBurning() && canCraft(outputs)) {
                    ++this.activeProgress;
                    if (this.activeProgress == this.maxProgress) {
                        this.activeProgress = 0;
                        this.maxProgress = getCookingTime(recipe);
                        if (this.craftRecipe(outputs, this.getExperience(recipe))) {
                            dirty = true;
                        }
                    }
                }
                if (state.getValue(BlockStateProperties.LIT) != this.isBurning()) {
                    dirty = true;
                    final BlockState nextState = state.setValue(WitchesOvenBlock.LIT, this.burnTime > 0).setValue(WitchesOvenBlock.PASSIVE_LIT, this.burnTime > 0);
                    world.setBlock(pos, nextState, Block.UPDATE_ALL);
                }
            } else {
                this.activeProgress = 0;
            }
        } else {
            this.activeProgress = 0;
        }
        if (this.isBurning()) {
            for (int i = 0; i < this.passiveInventory.getContainerSize(); i++) {
                final ItemStack foodAt = this.passiveInventory.getItem(i);
                if (!foodAt.isEmpty()) {
                    dirty = true;
                    this.passiveProgress[i]++;
                    if (this.passiveProgress[i] >= this.maxProgress) {
                        final var passiveRecipe = this.getCampfireRecipeFor((ServerLevel) world, foodAt);
                        if (passiveRecipe == null) {
                            WitchesKitchen.LOGGER.error("Attempted to craft a null passive recipe from Witches' Oven. This must be fixed");
                            return;
                        }
                        final ItemStack output = passiveRecipe.assemble(new SingleRecipeInput(this.passiveInventory.getItem(0)), world.registryAccess());
                        Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), output);
                        this.passiveInventory.setItem(i, ItemStack.EMPTY);
                        world.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
                    }
                }
            }
        } else {
            // if no longer burning
            // decrement progress
            for (int i = 0; i < this.passiveInventory.getContainerSize(); i++) {
                if (this.passiveProgress[i] > 0) {
                    dirty = true;
                    this.passiveProgress[i] = Mth.clamp(passiveProgress[i] - 2, 0, this.maxProgress);
                }
            }
            world.setBlock(pos, state.setValue(WitchesOvenBlock.LIT, this.isBurning()).setValue(WitchesOvenBlock.PASSIVE_LIT, this.isBurning()), Block.UPDATE_ALL);
        }
        if (dirty) {
            this.setChanged();
        }
    }

    /**
     * Cast recipe and find more appropriate cooking time
     */
    protected int getCookingTime(Recipe<?> recipe) {
        if (recipe instanceof SmeltingRecipe) {
            return ((SmeltingRecipe) recipe).cookingTime();
        } else if (recipe instanceof OvenCookingRecipe) {
            return ((OvenCookingRecipe) recipe).time();
        } else {
            return DEFAULT_COOKING_TIME;
        }
    }

    /**
     * Spawns passive cooking some particles
     */
    @Override
    public void onClientTick(Level world, BlockPos pos, BlockState state, WKBlockEntity wkBlockEntity) {
        super.onClientTick(world, pos, state, wkBlockEntity);
        int facing = state.getValue(CampfireBlock.FACING).get2DDataValue();
        for (int j = 0; j < this.passiveInventory.getContainerSize(); ++j) {
            if (state.getValue(WitchesOvenBlock.PASSIVE_LIT)) {
                if (!this.passiveInventory.getItem(j).isEmpty() && world.random.nextFloat() < 0.2F) {
                    final Direction direction = Direction.from2DDataValue(Math.floorMod(j + facing, 4));
                    float offset = 0.23F;
                    double d = (double) pos.getX() + 0.5D - (double) ((float) direction.getStepX() * offset) + (double) ((float) direction.getClockWise().getStepX() * offset);
                    double e = (double) pos.getY() + 1.0D;
                    double g = (double) pos.getZ() + 0.5D - (double) ((float) direction.getStepZ() * offset) + (double) ((float) direction.getClockWise().getStepZ() * offset);
                    for (int k = 0; k < 4; ++k) {
                        world.addParticle(ParticleTypes.SMOKE, d, e, g, 0.0D, 5.0E-4D, 0.0D);
                    }
                }
            }
        }
    }

    /**
     * Looks for the matching campfire recipe
     * for the given stack
     *
     * @param world World
     * @param stack ItemStack (Ingredient)
     * @return CampfireCookingRecipe
     */
    public @Nullable CampfireCookingRecipe getCampfireRecipeFor(ServerLevel world, ItemStack stack) {
        return world.recipeAccess().getAllOfType(RecipeType.CAMPFIRE_COOKING)
                .stream()
                .filter(entry -> {
                    var recipe = entry.value();
                    if (recipe.placementInfo().ingredients().size() == 1 && recipe.placementInfo().ingredients().get(0).test(stack)) {
                        return recipe.assemble(new SingleRecipeInput(stack), world.registryAccess()).has(DataComponents.FOOD);
                    }
                    return false;
                }).findFirst().map(RecipeHolder::value).orElse(null);
    }

    /**
     * Returns the experience of the recipe
     *
     * @param recipe RecipeType
     * @return Float (amount of experience)
     */
    private float getExperience(Recipe<?> recipe) {
        if (recipe instanceof SmeltingRecipe smelting) {
            return smelting.experience();
        } else {
            if (recipe instanceof OvenCookingRecipe cooking) {
                return cooking.xp();
            }
        }
        return 0.0F;
    }

    /**
     * Returns the number of ticks that the supplied fuel item will keep the
     * furnace burning, or 0 if the item isn't a fuel.
     *
     * @param stack fuel ItemStack
     * @return Integer Number of ticks
     */
    public int getItemBurnTime(Level world, ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        } else {
            return world.fuelValues().burnDuration(stack);
        }
    }

    /**
     * <p> Witches' Oven recipes may give you more than one item as result. </p>
     * <p>  This function is a handle for that. </p>
     *
     * @param recipe Recipe
     * @return the outputs of the given recipe
     */
    private List<ItemStack> getResults(final Recipe<?> recipe, Level world) {
        if (recipe instanceof SmeltingRecipe smeltingRecipe) {
            return NonNullList.withSize(1, smeltingRecipe.result());
        } else if (recipe instanceof OvenCookingRecipe ovenRecipe) {
            return ovenRecipe.outputs();
        } else {
            WitchesKitchen.LOGGER.error("Unknown recipe type was passed in for Witches' Oven");
            return NonNullList.create();
        }
    }

    /**
     * <p>
     * Validates ingredient and output slots.
     * It also checks that we have enough space for crafting
     * </p>
     */
    public boolean canCraft(final List<ItemStack> outputs) {
        if (this.level == null) {
            return false;
        } else if (outputs.isEmpty()) {
            return false;
        } else {
            final ItemStack stackInOutput = this.getItem(this.output);
            final ItemStack recipeOutput = outputs.get(0);
            final int nextOutputCount = stackInOutput.getCount() + recipeOutput.getCount();

            // Means recipe has an extra output
            if (outputs.size() > 1) {
                final ItemStack recipeExtra = outputs.get(1);
                final ItemStack stackInExtra = this.getItem(this.extra);
                final int nextExtraCount = stackInExtra.getCount() + recipeExtra.getCount();
                if (stackInOutput.isEmpty() && stackInExtra.isEmpty()) {
                    return true;
                } else if (stackInOutput.isEmpty() || stackInExtra.isEmpty()) {
                    if (stackInOutput.isEmpty()) {
                        //if first output is empty
                        //extra output is not
                        if (!ItemStack.isSameItem(stackInExtra, recipeExtra)) {
                            return false;
                        }
                    }
                    if (stackInExtra.isEmpty()) {
                        //if extra output is empty
                        //we know first output is not empty
                        if (!ItemStack.isSameItem(stackInOutput, recipeOutput)) {
                            return false;
                        }
                    }
                }
                if (nextOutputCount <= this.getMaxStackSize() && nextOutputCount <= recipeOutput.getMaxStackSize()) {
                    return nextExtraCount <= this.getMaxStackSize() && nextExtraCount <= recipeExtra.getMaxStackSize();
                }
            } else {
                // Otherwise, there is only 1 output
                if (stackInOutput.isEmpty()) {
                    return true;
                } else if (!ItemStack.isSameItem(stackInOutput, recipeOutput)) {
                    return false;
                } else {
                    return nextOutputCount <= this.getMaxStackSize() && nextOutputCount <= recipeOutput.getMaxStackSize();
                }
            }
        }
        return false;
    }

    /**
     * <p> Logic to craft the given recipe. </p>
     * <p> It also increments the experience count. </p>
     */
    public boolean craftRecipe(final List<ItemStack> outputs, final float experience) {
        if (this.level == null) {
            return false;
        } else if (outputs == null) {
            return false;
        } else if (outputs.isEmpty()) {
            return false;
        } else if (!canCraft(outputs)) {
            return false;
        } else {
            final ItemStack stackInOutput = this.getItem(this.output);
            final ItemStack stackInExtra = this.getItem(this.extra);
            final ItemStack recipeOutput = outputs.get(0);
            if (outputs.size() > 1) {
                //means this recipe has an extra
                final ItemStack recipeExtra = outputs.get(1);
                if (stackInOutput.isEmpty() && stackInExtra.isEmpty()) {
                    this.setItem(this.output, recipeOutput.copy());
                    this.setItem(this.extra, recipeExtra.copy());
                } else if (stackInOutput.is(recipeOutput.getItem()) && stackInExtra.is(recipeExtra.getItem())) {
                    stackInOutput.grow(recipeOutput.getCount());
                    stackInExtra.grow(recipeExtra.getCount());
                } else {
                    if (!stackInExtra.isEmpty()) {
                        //If extra output stack is not empty
                        //We can assume the first output is empty
                        //So we just increment extra
                        //And set the first output
                        stackInExtra.grow(recipeExtra.getCount());
                        this.setItem(this.output, recipeOutput.copy());
                    }
                    if (!stackInOutput.isEmpty()) {
                        //If first output stack is not empty
                        //We can assume the extra is empty
                        //So we just increment the first output
                        //And set the extra output
                        stackInOutput.grow(recipeOutput.getCount());
                        this.setItem(this.extra, recipeExtra.copy());
                    }
                }
            } else {
                //Otherwise, this is a normal recipe
                if (stackInOutput.isEmpty()) {
                    this.setItem(this.output, recipeOutput.copy());
                } else if (stackInOutput.is(recipeOutput.getItem())) {
                    stackInOutput.grow(recipeOutput.getCount());
                }
            }
            this.experience += experience;
            this.getItem(this.input).shrink(1);
            return true;
        }
    }


    // Client Sync
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    // Syncs the inventory
    // with the client
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider lookup) {
        final TagValueOutput data = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, lookup);
        saveAdditional(data);
        return data.buildResult();
    }

    // From NamedScreenHandlerFactory
    @Override
    public Component getDisplayName() {
        return Component.translatable("screen.title.witcheskitchen.witches_oven");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        return new WitchesOvenScreenHandler(syncId, inv, this, this.propertyDelegate);
    }

    public NonNullList<ItemStack> getStacksOnTop() {
        return this.passiveInventory.getStacks();
    }

    // From IDeviceExperienceHandler
    @Override
    public void dropExperience(ServerLevel world, Vec3 playerPos) {
        dropExperience(world, playerPos, this.experience);
        this.experience = 0;
    }

}
