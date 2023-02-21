package cf.witcheskitchen.common.block;

import cf.witcheskitchen.api.block.WKBlock;
import cf.witcheskitchen.api.util.WKUtils;
import cf.witcheskitchen.common.blockentity.WitchesOvenBlockEntity;
import cf.witcheskitchen.common.registry.WKDamageSources;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@SuppressWarnings("deprecation")
public class WitchesOvenBlock extends WKBlock implements Waterloggable {

    public static final BooleanProperty LIT = Properties.LIT;
    public static final BooleanProperty PASSIVE_LIT = BooleanProperty.of("passive_lit");
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;

    public static final VoxelShape SHAPE = VoxelShapes.union(
            createCuboidShape(0, 14, 0, 16, 16, 16),
            createCuboidShape(0, 2, 1, 16, 13, 16),
            createCuboidShape(0, 11, 0, 16, 13, 1),
            createCuboidShape(13, 2, 0, 16, 4, 1),
            createCuboidShape(0, 2, 0, 3, 4, 1),
            createCuboidShape(3, 2, 0, 13, 8, 1),
            createCuboidShape(5, 8, 0, 11, 9, 1),
            createCuboidShape(1, 13, 2, 15, 14, 15),
            createCuboidShape(13, 0, 1, 15, 2, 3),
            createCuboidShape(13, 0, 13, 15, 2, 15),
            createCuboidShape(1, 0, 13, 3, 2, 15),
            createCuboidShape(1, 0, 1, 3, 2, 3)
    );

    public WitchesOvenBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH).with(Properties.WATERLOGGED, false).with(LIT, false).with(PASSIVE_LIT, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return WKUtils.rotateShape(Direction.NORTH, state.get(FACING), SHAPE);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite()).with(Properties.WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING, LIT, PASSIVE_LIT, Properties.WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WitchesOvenBlockEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        final var entity = world.getBlockEntity(pos);
        if (entity instanceof WitchesOvenBlockEntity oven) {
            // Try to insert item on top
            if (hit.getType() == HitResult.Type.BLOCK) {
                final Direction side = hit.getSide();
                if (side == Direction.UP) {
                    final ItemStack stackInHand = player.getStackInHand(hand);
                    final CampfireCookingRecipe passiveRecipe = oven.getCampfireRecipeFor(world, stackInHand);
                    // It can only place an item if it is part of a campfire recipe
                    if (!world.isClient() && passiveRecipe != null) {
                        if (oven.putItemOnTop(player.isCreative() ? stackInHand.copy() : stackInHand)) {
                            return ActionResult.SUCCESS;
                        }
                        return ActionResult.CONSUME;
                    }
                } else {
                    // Open GUI
                    return super.onUse(state, world, pos, player, hand, hit);
                }
            }
        }
        return ActionResult.PASS;
    }

    @Override
    @ClientOnly
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, RandomGenerator random) {
        if (state.get(LIT)) {
            CampfireBlock.spawnSmokeParticle(world, pos, false, false);
            Blocks.FURNACE.randomDisplayTick(state, world, pos, random);
        }
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        super.onSteppedOn(world, pos, state, entity);
        if (state.get(LIT) && !entity.isFireImmune() && entity instanceof LivingEntity) {
            if (!EnchantmentHelper.hasFrostWalker((LivingEntity) entity)) {
                entity.damage(WKDamageSources.ON_OVEN, 1);
            }
        }
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            final BlockEntity entity = world.getBlockEntity(pos);
            if (entity instanceof WitchesOvenBlockEntity ovenEntity) {
                ItemScatterer.spawn(world, pos, ovenEntity.getStacksOnTop());
            }
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }
}
