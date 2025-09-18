package cf.witcheskitchen.common.blockentity;

import cf.witcheskitchen.api.block.entity.WKBlockEntity;
import cf.witcheskitchen.api.block.entity.WKBlockEntityWithInventory;
import cf.witcheskitchen.api.util.ItemUtil;
import cf.witcheskitchen.common.block.WitchesOvenBlock;
import cf.witcheskitchen.common.component.WKComponents;
import cf.witcheskitchen.common.component.blockentity.TeapotData;
import cf.witcheskitchen.common.recipe.TeaRecipe;
import cf.witcheskitchen.common.registry.WKBlockEntityTypes;
import cf.witcheskitchen.common.registry.WKRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

public class TeapotBlockEntity extends WKBlockEntityWithInventory {
    public static final int MAX_DURATION = 20 * 60 * 20; //20min
    public static final int UNOBTAINABLE_OUTPUT = MAX_DURATION / 10;
    public static final int TIME_TO_BREW = 20 * 10;
    public int progress = 0;
    public int effectTimer = 0;
    public TeaRecipe teaRecipe = null;
    public Holder<MobEffect> effect = null;
    public boolean hasWater = false;

    public TeapotBlockEntity(BlockPos pos, BlockState state) {
        super(WKBlockEntityTypes.TEAPOT, pos, state, 1);
    }

    @Override
    public void tick(Level world, BlockPos blockPos, BlockState blockState, WKBlockEntity blockEntity) {
        if (world.getBlockState(worldPosition.below()).getBlock() instanceof WitchesOvenBlock && world.getBlockState(worldPosition.below()).getValue(WitchesOvenBlock.LIT)) {
            if (teaRecipe == null) {
                if (world instanceof ServerLevel serverWorld) {
                    teaRecipe = serverWorld.recipeAccess().getAllOfType(WKRecipeTypes.TEA_RECIPE_TYPE).stream().filter(recipe -> recipe.value().input().test(this.manager.getItem(0))).findFirst().map(RecipeHolder::value).orElse(null);
                }
            } else {
                if (hasWater) {
                    if (effect == null) {
                        effectTimer = 0;
                        progress++;
                        if (progress >= UNOBTAINABLE_OUTPUT) {
                            effect = BuiltInRegistries.MOB_EFFECT.wrapAsHolder(teaRecipe.effect());
                        }
                    } else {
                        progress = 0;
                        effectTimer++;
                        emitEffect(world, worldPosition);
                        if (effectTimer >= MAX_DURATION) {
                            effect = null;
                            this.manager.clearContent();
                        }
                    }
                    setChanged();
                }
            }
            super.tick(world, blockPos, blockState, blockEntity);
        }
    }

    private void emitEffect(Level world, BlockPos pos) {
        if (world.getGameTime() % 80L == 0L) {
            AABB box = new AABB(pos).inflate(8);
            var list = world.getEntitiesOfClass(LivingEntity.class, box, Entity::isAlive);
            for (LivingEntity livingEntity : list) {
                livingEntity.addEffect(new MobEffectInstance(effect, 12 * 20, 1, true, false));
            }
        }
    }

    public void onUse(Level world, BlockState state, BlockPos pos, Player player, BlockHitResult hit) {
        if (player.getUsedItemHand() == InteractionHand.MAIN_HAND) {
            ItemStack stack = player.getMainHandItem();
            if (player.isShiftKeyDown()) {
                emptyInventoryAndReset(world, true);
            } else {
                if (player.getMainHandItem().is(Items.GLASS_BOTTLE)) {
                    tryFillBottle(player);
                } else if (stack.is(Items.POTION) && stack.has(DataComponents.POTION_CONTENTS) && stack.get(DataComponents.POTION_CONTENTS).potion().isPresent() && stack.get(DataComponents.POTION_CONTENTS).potion().orElseThrow().is(Potions.WATER)) {
                    fillKettle(player);
                } else if (world instanceof ServerLevel serverWorld) {
                    serverWorld.recipeAccess().getAllOfType(WKRecipeTypes.TEA_RECIPE_TYPE).stream().filter(recipe -> recipe.value().input().test(stack)).findFirst().map(RecipeHolder::value)
                        .ifPresent(teaRecipe -> tryAddIngredientToTeaPot(stack, world));
                }
            }
        }
    }

    private void tryAddIngredientToTeaPot(ItemStack input, Level world) {
        if (manager.isEmpty()) {
            manager.setItem(0, input.split(1));
            world.playSound(null, worldPosition, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 1 / 3f, 1.5f);
        }
    }

    private void fillKettle(Player player) {
        if (!hasWater) {
            ItemUtil.addItemToInventoryAndConsume(player, InteractionHand.MAIN_HAND, new ItemStack(Items.GLASS_BOTTLE));
            player.level().playSound(null, worldPosition, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1 / 3f, 1.0f);
            hasWater = true;
        }
    }

    private void tryFillBottle(Player player) {
        if (progress > TIME_TO_BREW && progress < UNOBTAINABLE_OUTPUT) {
            if (teaRecipe != null) {
                player.level().playSound(null, worldPosition, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1 / 3f, 1.0F);
                ItemUtil.addItemToInventoryAndConsume(player, InteractionHand.MAIN_HAND, teaRecipe.output());
                emptyInventoryAndReset(level, false);
            }
        }
    }

    private void emptyInventoryAndReset(Level world, boolean sound) {
        if (sound) {
            world.playSound(null, worldPosition, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 1 / 3f, 1);
        }
        this.manager.clearContent();
        this.hasWater = false;
        this.effect = null;
        this.progress = 0;
        this.effectTimer = 0;
        this.teaRecipe = null;
        this.setChanged();
    }

    @Override
    protected void applyImplicitComponents(DataComponentGetter components) {
        super.applyImplicitComponents(components);
        TeapotData data = components.get(WKComponents.TEAPOT);

        if (data != null) {
            this.progress = data.progress();
            this.effectTimer = data.effectTimer();
            this.hasWater = data.hasWater();
            this.effect = data.effect();
        }
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder builder) {
        super.collectImplicitComponents(builder);
        builder.set(WKComponents.TEAPOT, new TeapotData(this.progress, this.effectTimer, this.hasWater, this.effect));
    }
}
