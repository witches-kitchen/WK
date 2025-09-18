package cf.witcheskitchen.common.blockentity;

import cf.witcheskitchen.api.block.entity.WKBlockEntity;
import cf.witcheskitchen.api.block.entity.WKBlockEntityWithInventory;
import cf.witcheskitchen.api.ritual.Ritual;
import cf.witcheskitchen.api.ritual.RitualCircle;
import cf.witcheskitchen.common.recipe.MultipleStackRecipeInput;
import cf.witcheskitchen.common.recipe.RitualRecipe;
import cf.witcheskitchen.common.registry.WKBlockEntityTypes;
import cf.witcheskitchen.common.registry.WKBlocks;
import cf.witcheskitchen.common.registry.WKRecipeTypes;
import cf.witcheskitchen.common.registry.WKRegistries;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GlyphBlockEntity extends WKBlockEntityWithInventory {
    public int progress = 0;
    public RitualRecipe ritualRecipe = null;
    public Ritual ritual = null;


    public GlyphBlockEntity(BlockPos pos, BlockState state) {
        super(WKBlockEntityTypes.GLYPH, pos, state, 9);
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void tick(Level world, BlockPos blockPos, BlockState blockState, WKBlockEntity blockEntity) {
        if (ritualRecipe != null) {
            progress++;
            if (progress > 0) {
                ritual = ritualRecipe.rite();
                if (progress < ritualRecipe.duration()) {
                    ritual.tick(world, blockPos, ritualRecipe);
                } else {
                    ritual.onEnd(world, worldPosition, ritualRecipe);
                    resetRitual();
                }
            }
            setChanged();
        }
    }

    private void resetRitual() {
        ritualRecipe = null;
        ritual = null;
        progress = 0;
    }

    public void onUse(Level world, BlockState state, BlockPos pos, Player player, BlockHitResult hit) {
        ItemStack handStack = player.getMainHandItem();

        if (handStack.isEmpty() && world instanceof ServerLevel serverWorld) {
            RitualRecipe ritualRecipeNoCircleCheck = serverWorld.recipeAccess().getAllOfType(WKRecipeTypes.RITUAL_RECIPE_TYPE).stream().filter(entry -> entry.value().matches(new MultipleStackRecipeInput(this.manager.getStacks()), world)).findFirst().map(RecipeHolder::value).orElse(null);
            if (ritualRecipeNoCircleCheck != null) {
                Set<RitualCircle> circle = ritualRecipeNoCircleCheck.circleSet();
                if (checkValidCircle(world, pos, circle)) {
                    if (checkValidSacrifices(ritualRecipeNoCircleCheck, serverWorld)) {
                        this.manager.clearContent();
                        ritualRecipe = ritualRecipeNoCircleCheck;
                        ritual = ritualRecipe.rite();
                        ritual.onStart(world, pos, player, ritualRecipe);
                        setChanged();
                    }
                }
            }
        }
    }

    private boolean checkValidSacrifices(RitualRecipe ritual, ServerLevel world) {
        if (ritual.sacrifices() != null && ritual.sacrifices().isEmpty()) {
            return true;
        }

        int size = (ritual.circleSet().size() * 2) + 1;

        List<LivingEntity> livingEntityList = world.getEntitiesOfClass(LivingEntity.class, new AABB(this.worldPosition).inflate(size), Entity::isAlive);
        List<EntityType<?>> entityTypeList = Lists.newArrayList(livingEntityList.stream().map(Entity::getType).toList());
        List<EntityType<?>> ritualSacrifices = ritual.sacrifices();

        if (ritualSacrifices != null && new HashSet<>(entityTypeList).containsAll(ritualSacrifices)) {
            for (EntityType<?> entityType : ritualSacrifices) {
                LivingEntity foundEntity = getClosestEntity(livingEntityList, entityType, this.worldPosition);
                if (foundEntity != null) {
                    foundEntity.hurtServer(world, world.damageSources().magic(), Integer.MAX_VALUE);
                }
            }
            return true;
        }
        return false;
    }

    public <T extends LivingEntity> T getClosestEntity(List<? extends T> entityList, EntityType<?> type, BlockPos pos) {
        double d = -1.0;
        T livingEntity = null;
        for (T livingEntity2 : entityList) {
            double e = livingEntity2.distanceToSqr(pos.getX(), pos.getY(), pos.getZ());
            if (livingEntity2.getType() == type && (d == -1.0 || e < d)) {
                d = e;
                livingEntity = livingEntity2;
            }
        }
        return livingEntity;
    }

    private boolean checkValidCircle(Level world, BlockPos pos, Set<RitualCircle> circleSet) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (RitualCircle circle : circleSet) {
            if (circle.size == RitualCircle.Size.small) {
                return checkValidCircle(world, circle, mutable, RitualCircle.small);
            }
            if (circle.size == RitualCircle.Size.medium) {
                return checkValidCircle(world, circle, mutable, RitualCircle.medium);
            }
            if (circle.size == RitualCircle.Size.large) {
                return checkValidCircle(world, circle, mutable, RitualCircle.large);
            }
        }
        return false;
    }

    private boolean checkValidCircle(Level world, RitualCircle circle, BlockPos.MutableBlockPos mutable, byte[][] size) {
        for (int x = 0; x < size.length; x++) {
            for (int z = 0; z < size.length; z++) {
                if (size[x][z] == 1 && !isValidGlyph(circle.type, world.getBlockState(mutable.set(worldPosition.getX() + (x - size.length / 2), worldPosition.getY(), worldPosition.getZ() + (z - size.length / 2))).getBlock())) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValidGlyph(RitualCircle.Type type, Block block) {
        if (type.equals(RitualCircle.Type.chalk) && block == WKBlocks.GLYPH) {
            return true;
        } else if (type.equals(RitualCircle.Type.salt) && block == WKBlocks.SALT_BLOCK) {
            return true;
        } else return type.equals(RitualCircle.Type.candle) && block == Blocks.CANDLE;
    }

    @Override
    protected void loadAdditional(ValueInput data) {
        super.loadAdditional(data);
        progress = data.getIntOr("Progress", 0);
        ritual = WKRegistries.RITUAL.getValue(ResourceLocation.tryParse(data.getString("Ritual").orElseThrow()));
    }

    @Override
    protected void saveAdditional(ValueOutput data) {
        super.saveAdditional(data);
        data.putInt("Progress", progress);
        data.putString("Ritual", ritual.toString());
    }
}
