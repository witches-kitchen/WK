package cf.witcheskitchen.common.blockentity;

import cf.witcheskitchen.api.block.entity.WKBlockEntity;
import cf.witcheskitchen.api.block.entity.WKBlockEntityWithInventory;
import cf.witcheskitchen.api.util.InventoryManager;
import cf.witcheskitchen.api.util.ItemUtil;
import cf.witcheskitchen.common.recipe.TeaRecipe;
import cf.witcheskitchen.common.registry.WKBlockEntityTypes;
import cf.witcheskitchen.common.registry.WKRecipeTypes;
import cf.witcheskitchen.common.registry.WKTags;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Nameable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class WKTeapotEntity extends WKBlockEntityWithInventory {
    private final int MAX_DURATION = 20 * 60 * 20; //20min
    private final int UNOBTAINABLE_OUTPUT = MAX_DURATION / 10;
    private final int TIME_TO_BREW = 20 * 10;
    private int progress = 0;
    private int effectTimer = 0;
    public TeaRecipe teaRecipe = null;
    public StatusEffect effect = null;
    public boolean hasWater = false;

    public WKTeapotEntity(BlockPos pos, BlockState state) {
        super(WKBlockEntityTypes.TEAPOT, pos, state, 1);
    }



    @Override
    public void tick(World world, BlockPos blockPos, BlockState blockState, WKBlockEntity blockEntity) {
        if (world != null) {
            if(world.getBlockState(pos.down()).isIn(WKTags.HEATS_CAULDRON)){
                if(teaRecipe == null){
                    teaRecipe = world.getRecipeManager().listAllOfType(WKRecipeTypes.TEA_RECIPE_TYPE).stream().filter(recipe -> recipe.matches(this, world)).findFirst().orElse(null);
                }else{
                    if(hasWater){
                        if(effect == null){
                            effectTimer = 0;
                            progress++;
                            if(progress >= UNOBTAINABLE_OUTPUT){
                                effect = teaRecipe.getEffect();
                            }
                        }else{
                            progress = 0;
                            effectTimer++;
                            if(effectTimer >= MAX_DURATION){
                                effect = null;
                                this.manager.clear();
                            }
                        }
                    }
                }
                super.tick(world, blockPos, blockState, blockEntity);
            }
        }
    }

    public void onUse(World world, BlockState state, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if(!world.isClient() && player.getActiveHand() == Hand.MAIN_HAND){
            ItemStack stack = player.getMainHandStack();
            if(player.isSneaking()){
                emptyInventoryAndReset();
            }else{
                if(player.getMainHandStack().isOf(Items.GLASS_BOTTLE)){
                    tryFillBottle(player);
                }else if(stack.isOf(Items.POTION) && PotionUtil.getPotion(stack) == Potions.WATER){
                    fillKettle(player);
                }else{
                    TeaRecipe teaRecipe = world.getRecipeManager().listAllOfType(WKRecipeTypes.TEA_RECIPE_TYPE).stream().filter(recipe -> recipe.input.test(stack)).findFirst().orElse(null);;
                    if(teaRecipe != null){
                        tryAddIngredientToTeaPot(stack.split(1));
                    }
                }
            }
        }
    }

    private void tryAddIngredientToTeaPot(ItemStack input) {
        if(manager.isEmpty()){
            manager.setStack(0, input.split(1));
        }
    }

    private void fillKettle(PlayerEntity player) {
        if(!hasWater){
            ItemUtil.addItemToInventoryAndConsume(player, Hand.MAIN_HAND, new ItemStack(Items.GLASS_BOTTLE));
            hasWater = true;
        }
    }

    private void tryFillBottle(PlayerEntity player) {
        if(progress > TIME_TO_BREW && progress < UNOBTAINABLE_OUTPUT){
            Optional<TeaRecipe> optional = world.getRecipeManager().getFirstMatch(WKRecipeTypes.TEA_RECIPE_TYPE, this, world);
            optional.ifPresent(recipe -> ItemUtil.addItemToInventoryAndConsume(player, Hand.MAIN_HAND, recipe.getOutput()));
        }
    }

    private void emptyInventoryAndReset() {
        this.manager.clear();
        this.hasWater = false;
        this.effect = null;
        this.progress = 0;
        this.effectTimer = 0;
        this.markDirty();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        progress = nbt.getInt("Progress");
        effectTimer = nbt.getInt("EffectTimer");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putInt("Progress", progress);
        nbt.putInt("EffectTimer", effectTimer);
    }
}
