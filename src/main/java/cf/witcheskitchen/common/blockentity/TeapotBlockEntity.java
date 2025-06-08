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
import net.minecraft.block.BlockState;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentsAccess;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class TeapotBlockEntity extends WKBlockEntityWithInventory {
    public static final int MAX_DURATION = 20 * 60 * 20; //20min
    public static final int UNOBTAINABLE_OUTPUT = MAX_DURATION / 10;
    public static final int TIME_TO_BREW = 20 * 10;
    public int progress = 0;
    public int effectTimer = 0;
    public TeaRecipe teaRecipe = null;
    public RegistryEntry<StatusEffect> effect = null;
    public boolean hasWater = false;

    public TeapotBlockEntity(BlockPos pos, BlockState state) {
        super(WKBlockEntityTypes.TEAPOT, pos, state, 1);
    }

    @Override
    public void tick(World world, BlockPos blockPos, BlockState blockState, WKBlockEntity blockEntity) {
        if (world.getBlockState(pos.down()).getBlock() instanceof WitchesOvenBlock && world.getBlockState(pos.down()).get(WitchesOvenBlock.LIT)) {
            if (teaRecipe == null) {
                if (world instanceof ServerWorld serverWorld) {
                    teaRecipe = serverWorld.getRecipeManager().getAllOfType(WKRecipeTypes.TEA_RECIPE_TYPE).stream().filter(recipe -> recipe.value().input.test(this.manager.getStack(0))).findFirst().map(RecipeEntry::value).orElse(null);
                }
            } else {
                if (hasWater) {
                    if (effect == null) {
                        effectTimer = 0;
                        progress++;
                        if (progress >= UNOBTAINABLE_OUTPUT) {
                            effect = Registries.STATUS_EFFECT.getEntry(teaRecipe.getEffect());
                        }
                    } else {
                        progress = 0;
                        effectTimer++;
                        emitEffect(world, pos);
                        if (effectTimer >= MAX_DURATION) {
                            effect = null;
                            this.manager.clear();
                        }
                    }
                    markDirty();
                }
            }
            super.tick(world, blockPos, blockState, blockEntity);
        }
    }

    private void emitEffect(World world, BlockPos pos) {
        if (world.getTime() % 80L == 0L) {
            Box box = new Box(pos).expand(8);
            var list = world.getEntitiesByClass(LivingEntity.class, box, Entity::isAlive);
            for (LivingEntity livingEntity : list) {
                livingEntity.addStatusEffect(new StatusEffectInstance(effect, 12 * 20, 1, true, false));
            }
        }
    }

    public void onUse(World world, BlockState state, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (player.getActiveHand() == Hand.MAIN_HAND) {
            ItemStack stack = player.getMainHandStack();
            if (player.isSneaking()) {
                emptyInventoryAndReset(world, true);
            } else {
                if (player.getMainHandStack().isOf(Items.GLASS_BOTTLE)) {
                    tryFillBottle(player);
                } else if (stack.isOf(Items.POTION) && stack.contains(DataComponentTypes.POTION_CONTENTS) && stack.get(DataComponentTypes.POTION_CONTENTS).potion().isPresent() && stack.get(DataComponentTypes.POTION_CONTENTS).potion().orElseThrow().matches(Potions.WATER)) {
                    fillKettle(player);
                } else if (world instanceof ServerWorld serverWorld) {
                    serverWorld.getRecipeManager().getAllOfType(WKRecipeTypes.TEA_RECIPE_TYPE).stream().filter(recipe -> recipe.value().input.test(stack)).findFirst().map(RecipeEntry::value)
                            .ifPresent(teaRecipe -> tryAddIngredientToTeaPot(stack, world));
                }
            }
        }
    }

    private void tryAddIngredientToTeaPot(ItemStack input, World world) {
        if (manager.isEmpty()) {
            manager.setStack(0, input.split(1));
            world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.BLOCKS, 1 / 3f, 1.5f);
        }
    }

    private void fillKettle(PlayerEntity player) {
        if (!hasWater) {
            ItemUtil.addItemToInventoryAndConsume(player, Hand.MAIN_HAND, new ItemStack(Items.GLASS_BOTTLE));
            player.getWorld().playSound(null, pos, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1 / 3f, 1.0f);
            hasWater = true;
        }
    }

    private void tryFillBottle(PlayerEntity player) {
        if (progress > TIME_TO_BREW && progress < UNOBTAINABLE_OUTPUT) {
            if (teaRecipe != null) {
                player.getWorld().playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1 / 3f, 1.0F);
                ItemUtil.addItemToInventoryAndConsume(player, Hand.MAIN_HAND, teaRecipe.getOutput());
                emptyInventoryAndReset(world, false);
            }
        }
    }

    private void emptyInventoryAndReset(World world, boolean sound) {
        if (sound) {
            world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.BLOCKS, 1 / 3f, 1);
        }
        this.manager.clear();
        this.hasWater = false;
        this.effect = null;
        this.progress = 0;
        this.effectTimer = 0;
        this.teaRecipe = null;
        this.markDirty();
    }

    @Override
    protected void readComponents(ComponentsAccess components) {
        super.readComponents(components);
        TeapotData data = components.get(WKComponents.TEAPOT);

        if (data != null) {
            this.progress = data.progress();
            this.effectTimer = data.effectTimer();
            this.hasWater = data.hasWater();
            this.effect = data.effect();
        }
    }

    @Override
    protected void addComponents(ComponentMap.Builder builder) {
        super.addComponents(builder);
        builder.add(WKComponents.TEAPOT, new TeapotData(this.progress, this.effectTimer, this.hasWater, this.effect));
    }
}
