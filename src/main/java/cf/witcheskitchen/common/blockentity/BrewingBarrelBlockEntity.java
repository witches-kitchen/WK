package cf.witcheskitchen.common.blockentity;

import cf.witcheskitchen.api.block.entity.WKBlockEntity;
import cf.witcheskitchen.api.block.entity.WKBlockEntityWithInventory;
import cf.witcheskitchen.api.util.InventoryManager;
import cf.witcheskitchen.common.recipe.BarrelFermentingRecipe;
import cf.witcheskitchen.common.recipe.MultipleStackRecipeInput;
import cf.witcheskitchen.common.registry.WKBlockEntityTypes;
import cf.witcheskitchen.common.registry.WKRecipeTypes;
import cf.witcheskitchen.common.screenhandler.BrewingBarrelScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BrewingBarrelBlockEntity extends WKBlockEntityWithInventory implements MenuProvider {

    public static final int MAX_TIME = 168_000; // 7 days
    // TODO: are we sure this wouldn't crash the dedicated server?
    @Environment(EnvType.CLIENT)
    private final InventoryManager<BrewingBarrelBlockEntity> clientInventoryManager;
    private final ContainerData delegate;
    private boolean hasWater;
    private boolean hasFinished;
    private int timer;
    private BarrelFermentingRecipe previousRecipe = null;

    public BrewingBarrelBlockEntity(BlockPos pos, BlockState state) {
        super(WKBlockEntityTypes.BREWING_BARREL, pos, state, 6);
        this.clientInventoryManager = new InventoryManager<>(this, 1);
        this.hasFinished = false;
        this.delegate = new ContainerData() {
            @Override
            public int get(int index) {
                return BrewingBarrelBlockEntity.this.timer;
            }

            @Override
            public void set(int index, int value) {
                BrewingBarrelBlockEntity.this.timer = value;
            }

            @Override
            public int getCount() {
                return 1;
            }
        };
    }

    @Override
    public void tick(Level world, BlockPos pos, BlockState state, WKBlockEntity blockEntity) {
        super.tick(world, pos, state, blockEntity);
        boolean dirty = false;
        if (!(world instanceof ServerLevel serverWorld)) {
            this.timer = 0;
            return;
        }

        final var recipe = this.findRecipeFor(serverWorld, this.manager.getStacks());
        if (recipe == null) {
            this.timer = 0;
            return;
        }
        if (this.timer >= MAX_TIME) {
            if (this.makeAlcohol(recipe)) {
                this.timer = 0;
                this.hasWater = false;
                this.hasFinished = true;
                dirty = true;
            }
        } else {
            if (this.canMakeAlcohol(recipe)) {
                dirty = true;
                this.timer++;
            } else {
                this.timer = 0;
            }
        }
        if (dirty) {
            this.setChanged();
        }
    }

    public boolean insertBottle(ItemStack stack) {
        if (this.clientInventoryManager.isEmpty() && stack.is(Items.GLASS_BOTTLE)) {
            this.timer = 0;
            this.setRenderStack(stack.split(1));
            this.setChanged();
            return true;
        }
        return false;
    }

    public boolean fillBarrel(ItemStack stack) {
        if (!this.getRenderStack().is(Items.GLASS_BOTTLE)) {
            return false;
        }
        if (this.hasWater) {
            return false;
        }
        if (stack.is(Items.WATER_BUCKET)) {
            this.hasWater = true;
            this.playSound(SoundEvents.BOTTLE_FILL);
            return true;
        }
        return false;
    }

    public boolean emptyBarrel(ItemStack stack) {
        if (!this.hasWater) {
            return false;
        }
        if (stack.is(Items.BUCKET)) {
            this.hasWater = false;
            this.setRenderStack(new ItemStack(Items.GLASS_BOTTLE));
            this.playSound(SoundEvents.BUCKET_FILL);
            return true;
        }
        return false;
    }

    public void removeBottle(Player player) {
        this.setRenderStack(ItemStack.EMPTY);
        final ItemStack output = new ItemStack(Items.GLASS_BOTTLE);
        if (!player.getInventory().add(output)) {
            player.drop(output, false, false);
        }
    }

    private boolean canMakeAlcohol(BarrelFermentingRecipe matchingRecipe) {
        if (this.level == null) {
            return false;
        } else if (matchingRecipe == null) {
            return false;
        } else if (this.manager.isEmpty()) {
            return false;
        } else if (!this.hasWater) {
            return false;
        } else {
            if (this.clientInventoryManager.isEmpty()) {
                return false;
            }
            return matchingRecipe.matches(new MultipleStackRecipeInput(this.clientInventoryManager.getStacks()), this.level);
        }
    }

    private BarrelFermentingRecipe findRecipeFor(ServerLevel world, NonNullList<ItemStack> inputs) {
        if (world == null) {
            return null;
        } else if (inputs.isEmpty()) {
            return null;
        } else if (this.previousRecipe != null && previousRecipe.matches(new MultipleStackRecipeInput(this.manager.getStacks()), world)) {
            return previousRecipe;
        } else {
            final BarrelFermentingRecipe recipe = world.recipeAccess()
                .getAllOfType(WKRecipeTypes.BARREL_FERMENTING_RECIPE_TYPE)
                .stream()
                .filter(brewingRecipe -> brewingRecipe.value().matches(new MultipleStackRecipeInput(this.manager.getStacks()), world))
                .findFirst()
                .map(RecipeHolder::value)
                .orElse(null);
            if (recipe != null) {
                this.previousRecipe = recipe;
                return recipe;
            }
            return null;
        }
    }

    private boolean makeAlcohol(BarrelFermentingRecipe recipe) {
        if (recipe == null) {
            return false;
        } else if (!canMakeAlcohol(recipe)) {
            return false;
        } else {
            for (int i = 0; i < recipe.placementInfo().ingredients().size(); i++) {
                final List<Ingredient> ingredients = recipe.placementInfo().ingredients();
                final Ingredient ingredient = ingredients.get(i);
                final ItemStack stack = this.getItem(i);
                if (ingredient.test(stack)) {
                    stack.shrink(1);
                } else {
                    // Remove remaining items, if any.
                    for (int j = 0; j < this.getContainerSize() - 1; j++) {
                        final ItemStack remainingStack = this.getItem(j);
                        if (ingredient.test(remainingStack)) {
                            remainingStack.shrink(1);
                            break;
                        }
                    }
                }
            }
            this.clientInventoryManager.setItem(0, recipe.assemble(new MultipleStackRecipeInput(this.clientInventoryManager.getStacks()), level.registryAccess()));
            level.sendBlockUpdated(worldPosition, this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
            return true;
        }
    }

    @Override
    public void loadAdditional(ValueInput data) {
        super.loadAdditional(data);
        this.clientInventoryManager.clearContent();
        ContainerHelper.loadAllItems(data.childOrEmpty("ClientInventory"), this.clientInventoryManager.getStacks());
        this.timer = data.getIntOr("Timer", 0);
        this.hasWater = data.getBooleanOr("HasWater", false);
        this.hasFinished = data.getBooleanOr("HasFinished", false);
    }

    @Override
    protected void saveAdditional(ValueOutput data) {
        super.saveAdditional(data);
        ContainerHelper.saveAllItems(data.child("ClientInventory"), this.clientInventoryManager.getStacks());
        data.putInt("Timer", this.timer);
        data.putBoolean("HasWater", this.hasWater);
        data.putBoolean("HasFinished", this.hasFinished);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("screen.title.witcheskitchen.brewing_barrel");
    }

    @Override
    public void startOpen(Player player) {
        super.startOpen(player);
        this.playSound(SoundEvents.BARREL_OPEN);
    }

    @Override
    public void stopOpen(Player player) {
        super.stopOpen(player);
        this.playSound(SoundEvents.BARREL_CLOSE);
    }

    public void reset() {
        if (this.level != null) {
            this.hasWater = false;
            this.hasFinished = false;
            this.setChanged();
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
        return new BrewingBarrelScreenHandler(syncId, inv, this, this.delegate);
    }

    // Client Sync
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registryLookup) {
        final TagValueOutput data = TagValueOutput.createWithContext(ProblemReporter.DISCARDING, registryLookup);
        saveAdditional(data);
        return data.buildResult();
    }

    private void playSound(SoundEvent soundEvent) {
        final Vec3i vec3i = this.getBlockState().getValue(HorizontalDirectionalBlock.FACING).getUnitVec3i();
        double d = (double) this.worldPosition.getX() + 0.5 + (double) vec3i.getX() / 2.0;
        double e = (double) this.worldPosition.getY() + 0.5 + (double) vec3i.getY() / 2.0;
        double f = (double) this.worldPosition.getZ() + 0.5 + (double) vec3i.getZ() / 2.0;
        this.level.playSound(null, d, e, f, soundEvent, SoundSource.BLOCKS, 0.5f, this.level.random.nextFloat() * 0.1f + 0.9f);
    }

    @Environment(EnvType.CLIENT)
    public ItemStack getRenderStack() {
        if (this.clientInventoryManager.isEmpty()) {
            return ItemStack.EMPTY;
        }
        return this.clientInventoryManager.getItem(0);
    }

    public void setRenderStack(ItemStack stack) {
        this.clientInventoryManager.setItem(0, stack);
    }

    public boolean hasFinished() {
        return this.hasFinished;
    }
}
