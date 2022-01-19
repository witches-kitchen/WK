package cf.witcheskitchen.common.blocks.technical;

import cf.witcheskitchen.api.WKBlockEntityProvider;
import cf.witcheskitchen.common.blocks.entity.WKDeviceBlockEntity;
import cf.witcheskitchen.common.blocks.entity.WitchesCauldronBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class WitchesCauldronBlock extends WKBlockEntityProvider implements Waterloggable {

    public static final BooleanProperty HANGING = BooleanProperty.of("hanging");
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final int TOP_LEVEL = 2;
    public static final IntProperty WATER_LEVELS = IntProperty.of("water_levels", 0, TOP_LEVEL);

    public static final VoxelShape SHAPE = VoxelShapes.union(
            createCuboidShape(2, 9,1, 14, 11, 2),
            createCuboidShape(2,9,14, 14, 11, 15),
            createCuboidShape(14, 9, 2, 15, 11, 14),
            createCuboidShape(1, 9, 2, 2, 11, 14),
            createCuboidShape(2, 8, 13, 14, 9, 14),
            createCuboidShape(2, 8, 3, 3, 9, 13),
            createCuboidShape(13, 8, 3, 14, 9, 13),
            createCuboidShape(1, 2, 2, 3, 8, 14),
            createCuboidShape(2, 2, 13, 14, 8, 15),
            createCuboidShape(13, 2, 2, 15, 8, 14),
            createCuboidShape(2, 2, 1, 14, 8, 3),
            createCuboidShape(2, 1, 2, 14, 2, 14),
            createCuboidShape(3, 0, 3, 13, 1,13)
    );

    public WitchesCauldronBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH).with(Properties.WATERLOGGED, false).with(HANGING, false));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing()).with(Properties.WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(Properties.WATERLOGGED)) {
            return Fluids.WATER.getStill(false);
        } else {
            return super.getFluidState(state);
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        world.setBlockState(pos, state.with(HANGING, !world.getBlockState(pos.up()).isAir()), Block.NOTIFY_ALL); // Triggers Hanging state
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (hit.getSide() != Direction.DOWN) {
            final ItemStack activeStack = player.getStackInHand(hand);
            if (!activeStack.isEmpty()) {
                final int i = getWaterLevelFor(activeStack.getItem());
                // Only if the stack can fill the cauldron
                // We want to interact with it.
                if (i > 0) {
                    // Let's try to get the water back
                    if (activeStack.isOf(Items.BUCKET) || activeStack.isOf(Items.GLASS_BOTTLE)) {
                        this.setWaterLevel(world, state, pos, this.getWaterLevel(state) - i);
                        final ItemStack output = i == 3 ? new ItemStack(Items.WATER_BUCKET) : Items.POTION.getDefaultStack();
                        player.setStackInHand(hand, ItemUsage.exchangeStack(activeStack, player, output));
                        return ActionResult.SUCCESS;
                    }
                    // We couldn't get water back
                    // That means we currently have a water bucket or bottle
                    // But the cauldron is full of water, so the action is not going to be performed.
                    if (this.getWaterLevel(state) >= TOP_LEVEL) {
                        return ActionResult.PASS;
                    }
                    // Fill cauldron
                    this.setWaterLevel(world, state, pos, this.getWaterLevel(state) + i);
                    final ItemStack output = i == 3 ? new ItemStack(activeStack.getItem().getRecipeRemainder()) : Items.GLASS_BOTTLE.getDefaultStack();
                    player.setStackInHand(hand, ItemUsage.exchangeStack(activeStack, player,  output));
                    return ActionResult.SUCCESS;
                }
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    public int getWaterLevel(BlockState state) {
        return state.get(WATER_LEVELS);
    }

    public static int getWaterLevelFor(Item item) {
        if (item instanceof BucketItem) {
            return 3;
        }
        return item instanceof GlassBottleItem || item instanceof PotionItem ? 1 : 0;
    }

    public void setWaterLevel(World world, BlockState state, BlockPos pos, int level) {
        if (level < 0) {
            level = 0;
        }
        if (level > TOP_LEVEL) {
            level = TOP_LEVEL;
        }
        final BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof WKDeviceBlockEntity device) {
            world.setBlockState(pos, state.with(WATER_LEVELS, level), Block.NOTIFY_ALL);
       //     device.updateListeners();
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WitchesCauldronBlockEntity(pos, state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING, WATER_LEVELS, HANGING, Properties.WATERLOGGED);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

}
