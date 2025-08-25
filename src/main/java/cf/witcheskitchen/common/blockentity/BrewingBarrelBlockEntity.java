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
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.storage.NbtWriteView;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.ErrorReporter;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BrewingBarrelBlockEntity extends WKBlockEntityWithInventory implements NamedScreenHandlerFactory {

    public static final int MAX_TIME = 168_000; // 7 days
    // TODO: are we sure this wouldn't crash the dedicated server?
    @Environment(EnvType.CLIENT)
    private final InventoryManager<BrewingBarrelBlockEntity> clientInventoryManager;
    private final PropertyDelegate delegate;
    private boolean hasWater;
    private boolean hasFinished;
    private int timer;
    private BarrelFermentingRecipe previousRecipe = null;

    public BrewingBarrelBlockEntity(BlockPos pos, BlockState state) {
        super(WKBlockEntityTypes.BREWING_BARREL, pos, state, 6);
        this.clientInventoryManager = new InventoryManager<>(this, 1);
        this.hasFinished = false;
        this.delegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                return BrewingBarrelBlockEntity.this.timer;
            }

            @Override
            public void set(int index, int value) {
                BrewingBarrelBlockEntity.this.timer = value;
            }

            @Override
            public int size() {
                return 1;
            }
        };
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state, WKBlockEntity blockEntity) {
        super.tick(world, pos, state, blockEntity);
        boolean dirty = false;
        if (!(world instanceof ServerWorld serverWorld)) {
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
            this.markDirty();
        }
    }

    public boolean insertBottle(ItemStack stack) {
        if (this.clientInventoryManager.isEmpty() && stack.isOf(Items.GLASS_BOTTLE)) {
            this.timer = 0;
            this.setRenderStack(stack.split(1));
            this.markDirty();
            return true;
        }
        return false;
    }

    public boolean fillBarrel(ItemStack stack) {
        if (!this.getRenderStack().isOf(Items.GLASS_BOTTLE)) {
            return false;
        }
        if (this.hasWater) {
            return false;
        }
        if (stack.isOf(Items.WATER_BUCKET)) {
            this.hasWater = true;
            this.playSound(SoundEvents.ITEM_BOTTLE_FILL);
            return true;
        }
        return false;
    }

    public boolean emptyBarrel(ItemStack stack) {
        if (!this.hasWater) {
            return false;
        }
        if (stack.isOf(Items.BUCKET)) {
            this.hasWater = false;
            this.setRenderStack(new ItemStack(Items.GLASS_BOTTLE));
            this.playSound(SoundEvents.ITEM_BUCKET_FILL);
            return true;
        }
        return false;
    }

    public void removeBottle(PlayerEntity player) {
        this.setRenderStack(ItemStack.EMPTY);
        final ItemStack output = new ItemStack(Items.GLASS_BOTTLE);
        if (!player.getInventory().insertStack(output)) {
            player.dropItem(output, false, false);
        }
    }

    private boolean canMakeAlcohol(BarrelFermentingRecipe matchingRecipe) {
        if (this.world == null) {
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
            return matchingRecipe.matches(new MultipleStackRecipeInput(this.clientInventoryManager.getStacks()), this.world);
        }
    }

    private BarrelFermentingRecipe findRecipeFor(ServerWorld world, DefaultedList<ItemStack> inputs) {
        if (world == null) {
            return null;
        } else if (inputs.isEmpty()) {
            return null;
        } else if (this.previousRecipe != null && previousRecipe.matches(new MultipleStackRecipeInput(this.manager.getStacks()), world)) {
            return previousRecipe;
        } else {
            final BarrelFermentingRecipe recipe = world.getRecipeManager()
                    .getAllOfType(WKRecipeTypes.BARREL_FERMENTING_RECIPE_TYPE)
                    .stream()
                    .filter(brewingRecipe -> brewingRecipe.value().matches(new MultipleStackRecipeInput(this.manager.getStacks()), world))
                    .findFirst()
                    .map(RecipeEntry::value)
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
            for (int i = 0; i < recipe.getIngredientPlacement().getIngredients().size(); i++) {
                final List<Ingredient> ingredients = recipe.getIngredientPlacement().getIngredients();
                final Ingredient ingredient = ingredients.get(i);
                final ItemStack stack = this.getStack(i);
                if (ingredient.test(stack)) {
                    stack.decrement(1);
                } else {
                    // Remove remaining items, if any.
                    for (int j = 0; j < this.size() - 1; j++) {
                        final ItemStack remainingStack = this.getStack(j);
                        if (ingredient.test(remainingStack)) {
                            remainingStack.decrement(1);
                            break;
                        }
                    }
                }
            }
            this.clientInventoryManager.setStack(0, recipe.craft(new MultipleStackRecipeInput(this.clientInventoryManager.getStacks()), world.getRegistryManager()));
            world.updateListeners(pos, this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
            return true;
        }
    }

    @Override
    public void readData(ReadView data) {
        super.readData(data);
        this.clientInventoryManager.clear();
        Inventories.readData(data.getReadView("ClientInventory"), this.clientInventoryManager.getStacks());
        this.timer = data.getInt("Timer", 0);
        this.hasWater = data.getBoolean("HasWater", false);
        this.hasFinished = data.getBoolean("HasFinished", false);
    }

    @Override
    protected void writeData(WriteView data) {
        super.writeData(data);
        Inventories.writeData(data.get("ClientInventory"), this.clientInventoryManager.getStacks());
        data.putInt("Timer", this.timer);
        data.putBoolean("HasWater", this.hasWater);
        data.putBoolean("HasFinished", this.hasFinished);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("screen.title.witcheskitchen.brewing_barrel");
    }

    @Override
    public void onOpen(PlayerEntity player) {
        super.onOpen(player);
        this.playSound(SoundEvents.BLOCK_BARREL_OPEN);
    }

    @Override
    public void onClose(PlayerEntity player) {
        super.onClose(player);
        this.playSound(SoundEvents.BLOCK_BARREL_CLOSE);
    }

    public void reset() {
        if (this.world != null) {
            this.hasWater = false;
            this.hasFinished = false;
            this.markDirty();
        }
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new BrewingBarrelScreenHandler(syncId, inv, this, this.delegate);
    }

    // Client Sync
    @Override
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        final NbtWriteView data = NbtWriteView.create(ErrorReporter.EMPTY, registryLookup);
        writeData(data);
        return data.getNbt();
    }

    private void playSound(SoundEvent soundEvent) {
        final Vec3i vec3i = this.getCachedState().get(HorizontalFacingBlock.FACING).getVector();
        double d = (double) this.pos.getX() + 0.5 + (double) vec3i.getX() / 2.0;
        double e = (double) this.pos.getY() + 0.5 + (double) vec3i.getY() / 2.0;
        double f = (double) this.pos.getZ() + 0.5 + (double) vec3i.getZ() / 2.0;
        this.world.playSound(null, d, e, f, soundEvent, SoundCategory.BLOCKS, 0.5f, this.world.random.nextFloat() * 0.1f + 0.9f);
    }

    @Environment(EnvType.CLIENT)
    public ItemStack getRenderStack() {
        if (this.clientInventoryManager.isEmpty()) {
            return ItemStack.EMPTY;
        }
        return this.clientInventoryManager.getStack(0);
    }

    public void setRenderStack(ItemStack stack) {
        this.clientInventoryManager.setStack(0, stack);
    }

    public boolean hasFinished() {
        return this.hasFinished;
    }
}
