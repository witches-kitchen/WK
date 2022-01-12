package cf.witcheskitchen.common.blocks.entity;

import cf.witcheskitchen.api.InventoryManager;
import cf.witcheskitchen.client.gui.screen.handler.BrewingBarrelScreenHandler;
import cf.witcheskitchen.common.recipe.BarrelFermentingRecipe;
import cf.witcheskitchen.common.registry.WKBlockEntityTypes;
import cf.witcheskitchen.common.registry.WKRecipeTypes;
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
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BrewingBarrelBlockEntity extends WKDeviceBlockEntity implements NamedScreenHandlerFactory {

    @Environment(EnvType.CLIENT)
    private final InventoryManager<BrewingBarrelBlockEntity> clientInventoryManager;
    public static final int MAX_TIME = 168_000; // 7 days
    private final PropertyDelegate delegate;
    private boolean hasWater;
    private int timer;
    private BarrelFermentingRecipe lastRecipe = null;

    public BrewingBarrelBlockEntity(BlockPos pos, BlockState state) {
        super(WKBlockEntityTypes.BREWING_BARREL, pos, state, 6);
        this.clientInventoryManager = new InventoryManager<>(this, 1);
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
    public void tick(World world, BlockPos pos, BlockState state, WKDeviceBlockEntity blockEntity) {
        super.tick(world, pos, state, blockEntity);
        boolean dirty = false;
        final var recipe = this.findRecipeFor(world, this.getMainManager().getStacks());
        if (recipe == null) {
            this.timer = 0;
            return;
        }
        if (this.timer >= MAX_TIME) {
            if (this.makeAlcohol(recipe)) {
                this.hasWater = false;
                this.timer = 0;
                dirty = true;
            }
        } else {
            if (this.canMakeAlcohol(recipe)) {
                dirty = true;
                this.timer+= 2000;
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
            this.updateListeners();
            return true;
        }
        return false;
    }

    public boolean fillBarrel(ItemStack stack) {
        if (this.hasWater) {
            return false;
        }
        if (stack.isOf(Items.WATER_BUCKET)) {
            this.hasWater = true;
            this.setRenderStack(Items.POTION.getDefaultStack());
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

    private boolean canMakeAlcohol(BarrelFermentingRecipe matchingRecipe) {
        if (this.world == null) {
            return false;
        } else if (matchingRecipe == null) {
            return false;
        } else if (this.getMainManager().isEmpty()) {
            return false;
        } else if (!this.hasWater) {
            return false;
        } else {
            if (this.clientInventoryManager.isEmpty()) {
                return false;
            }
            return matchingRecipe.matches(this, this.world);
        }
    }

    private BarrelFermentingRecipe findRecipeFor(World world, DefaultedList<ItemStack> inputs) {
        if (world == null) {
            return null;
        } else if (inputs.isEmpty()) {
            return null;
        } else if (this.lastRecipe != null && lastRecipe.matches(this, world)) {
            return lastRecipe;
        } else {
            final BarrelFermentingRecipe recipe = world.getRecipeManager()
                    .listAllOfType(WKRecipeTypes.BARREL_FERMENTING_RECIPE_TYPE)
                    .stream()
                    .filter(brewingRecipe -> brewingRecipe.matches(this, world))
                    .findFirst()
                    .orElse(null);
            if (recipe != null) {
                this.lastRecipe = recipe;
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
            for (int i = 0; i < recipe.getInputs().size(); i++) {
                final DefaultedList<Ingredient> ingredients = recipe.getInputs();
                final Ingredient ingredient = ingredients.get(i);
                final ItemStack stack = this.getStack(i);
                if (ingredient.test(stack)) {
                    stack.decrement(1);
                } else {
                    // Remove remaining items, if any.
                    for (int j = 0; j < this.size()-1; j++) {
                        final ItemStack remainingStack = this.getStack(j);
                        if (ingredient.test(remainingStack)) {
                            remainingStack.decrement(1);
                            break;
                        }
                    }
                }
            }
            ItemStack output = recipe.craft(this.clientInventoryManager);
            ItemScatterer.spawn(world, pos.getX(), pos.getY(), pos.getZ(), output);
            this.clientInventoryManager.setStack(0, ItemStack.EMPTY);
            world.updateListeners(pos, this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
            return true;
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.clientInventoryManager.clear();
        Inventories.readNbt(nbt.getCompound("ClientInventory"), this.clientInventoryManager.getStacks());
        this.timer = nbt.getInt("Timer");
        this.hasWater = nbt.getBoolean("HasWater");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        NbtCompound clientData = new NbtCompound();
        Inventories.writeNbt(clientData, this.clientInventoryManager.getStacks());
        nbt.put("ClientInventory", clientData);
        nbt.putInt("Timer", this.timer);
        nbt.putBoolean("HasWater", this.hasWater);
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText("screen.title.witcheskitchen.brewing_barrel");
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
    public NbtCompound toInitialChunkDataNbt() {
        final NbtCompound data = new NbtCompound();
        writeNbt(data);
        return data;
    }

    private void playSound(SoundEvent soundEvent) {
        final Vec3i vec3i = this.getCachedState().get(HorizontalFacingBlock.FACING).getVector();
        double d = (double)this.pos.getX() + 0.5 + (double)vec3i.getX() / 2.0;
        double e = (double)this.pos.getY() + 0.5 + (double)vec3i.getY() / 2.0;
        double f = (double)this.pos.getZ() + 0.5 + (double)vec3i.getZ() / 2.0;
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
}
