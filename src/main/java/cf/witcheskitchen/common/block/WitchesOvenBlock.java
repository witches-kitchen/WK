package cf.witcheskitchen.common.block;

import cf.witcheskitchen.api.block.WKBlock;
import cf.witcheskitchen.api.util.WKUtils;
import cf.witcheskitchen.common.blockentity.WitchesOvenBlockEntity;
import cf.witcheskitchen.common.registry.WKDamageSources;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class WitchesOvenBlock extends WKBlock implements SimpleWaterloggedBlock {

    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty PASSIVE_LIT = BooleanProperty.create("passive_lit");
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;

    public static final VoxelShape SHAPE = Shapes.or(
        box(0, 14, 0, 16, 16, 16),
        box(0, 2, 1, 16, 13, 16),
        box(0, 11, 0, 16, 13, 1),
        box(13, 2, 0, 16, 4, 1),
        box(0, 2, 0, 3, 4, 1),
        box(3, 2, 0, 13, 8, 1),
        box(5, 8, 0, 11, 9, 1),
        box(1, 13, 2, 15, 14, 15),
        box(13, 0, 1, 15, 2, 3),
        box(13, 0, 13, 15, 2, 15),
        box(1, 0, 13, 3, 2, 15),
        box(1, 0, 1, 3, 2, 3)
    );

    public WitchesOvenBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(BlockStateProperties.WATERLOGGED, false).setValue(LIT, false).setValue(PASSIVE_LIT, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return WKUtils.rotateShape(Direction.NORTH, state.getValue(FACING), SHAPE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getNearestLookingDirection().getOpposite()).setValue(BlockStateProperties.WATERLOGGED, ctx.getLevel().getFluidState(ctx.getClickedPos()).getType() == Fluids.WATER);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, LIT, PASSIVE_LIT, BlockStateProperties.WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WitchesOvenBlockEntity(pos, state);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stackInHand, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        final var entity = world.getBlockEntity(pos);
        if (entity instanceof WitchesOvenBlockEntity oven) {
            // Try to insert item on top
            if (hit.getType() == HitResult.Type.BLOCK) {
                final Direction side = hit.getDirection();
                if (side == Direction.UP && !world.isClientSide()) {
                    final CampfireCookingRecipe passiveRecipe = oven.getCampfireRecipeFor((ServerLevel) world, stackInHand);
                    // It can only place an item if it is part of a campfire recipe
                    if (!world.isClientSide() && passiveRecipe != null) {
                        if (oven.putItemOnTop(player.isCreative() ? stackInHand.copy() : stackInHand)) {
                            return InteractionResult.SUCCESS;
                        }
                        return InteractionResult.CONSUME;
                    }
                } else {
                    // Open GUI
                    return super.useItemOn(stackInHand, state, world, pos, player, hand, hit);
                }
            }
        }
        return InteractionResult.TRY_WITH_EMPTY_HAND;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT)) {
            CampfireBlock.makeParticles(world, pos, false, false);
            Blocks.FURNACE.animateTick(state, world, pos, random);
        }
    }

    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        super.stepOn(world, pos, state, entity);
        if (state.getValue(LIT) && !entity.fireImmune() && entity instanceof LivingEntity && world instanceof ServerLevel serverWorld) {
            entity.hurtServer(serverWorld, entity.damageSources().source(WKDamageSources.ON_OVEN), 1);
        }
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel world, BlockPos pos, boolean moved) {
        final BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof WitchesOvenBlockEntity ovenEntity) {
            Containers.dropContents(world, pos, ovenEntity.getStacksOnTop());
        }
        super.affectNeighborsAfterRemoval(state, world, pos, moved);
    }
}
