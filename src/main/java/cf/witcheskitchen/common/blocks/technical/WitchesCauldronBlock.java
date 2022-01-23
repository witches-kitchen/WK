package cf.witcheskitchen.common.blocks.technical;

import cf.witcheskitchen.api.WKBlockEntityProvider;
import cf.witcheskitchen.api.fluid.FluidStack;
import cf.witcheskitchen.api.fluid.WKFluidAPI;
import cf.witcheskitchen.common.blocks.entity.WitchesCauldronBlockEntity;
import cf.witcheskitchen.common.util.ItemUtil;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
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
            createCuboidShape(2, 9, 1, 14, 11, 2),
            createCuboidShape(2, 9, 14, 14, 11, 15),
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
            createCuboidShape(3, 0, 3, 13, 1, 13)
    );

    public WitchesCauldronBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH).with(Properties.WATERLOGGED, false).with(HANGING, false));
    }

    public static int getWaterLevelFor(Item item) {
        if (item instanceof BucketItem) {
            return 3;
        }
        return item instanceof GlassBottleItem || item instanceof PotionItem ? 1 : 0;
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

    // Triggers Hanging state
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        world.setBlockState(pos, state.with(HANGING, !world.getBlockState(pos.up()).isAir()), Block.NOTIFY_ALL);
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

//    @Environment(EnvType.CLIENT)
//    @Override
//    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
//        final BlockEntity entity = world.getBlockEntity(pos);
//        if (entity instanceof WitchesCauldronBlockEntity cauldron) {
//            if (cauldron.isBoiling()) {
//                int color = cauldron.getWaterColor();
//                double width = 0.3D;
//                double offsetX = 0.5D + MathHelper.nextDouble(random, -width, width);
//                double offsetZ = 0.5D + MathHelper.nextDouble(random, -width, width);
//                float height = WitchesCauldronBlockEntityRender.WATER_LEVELS[this.getWaterLevel(state) - 1];
//                for (int i = 0; i < 2; i++) {
//                    world.addParticle((ParticleEffect) WKParticleTypes.BUBBLE, pos.getX() + offsetX, pos.getY() + height, pos.getZ() + offsetZ, ((cauldron.getWaterColor() >> 16) & 0xff) / 255f, ((cauldron.getWaterColor() >> 8) & 0xff) / 255f, (cauldron.getWaterColor() & 0xff) / 255f);
//                }
//            }
//        }
//    }
//
//    public static boolean tryFillWith(World world, BlockPos pos, FluidStack stack, Direction side) {
//        if (!world.isClient) {
//            final BlockEntity entity = world.getBlockEntity(pos);
//            if (entity instanceof WitchesCauldronBlockEntity cauldron) {
//                if (cauldron.canFill(side, stack.getFluid())) {
//                    int quantity = cauldron.fill(side, stack);
//                    stack.setAmount(stack.getAmount() - quantity);
//                    if (stack.getAmount() < 0) {
//                        stack.setAmount(0);
//                    }
//                    if (quantity > 0) {
//                        cauldron.markDirty();
//                    }
//                    return quantity > 0;
//                }
//            }
//        }
//        return false;
//    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        final BlockEntity entity = world.getBlockEntity(pos);
        final ItemStack heldStack = player.getStackInHand(hand);
        final Direction side = hit.getSide();
        if (entity instanceof WitchesCauldronBlockEntity cauldron && !heldStack.isEmpty()) {
            /*
             * Fill cauldron tank
             */
            final FluidStack fluidStackToFill = WKFluidAPI.getFluidStackFor(heldStack);
            if (!fluidStackToFill.isEmpty()) {
                if (cauldron.canFill(fluidStackToFill, side)) {
                    int filled = cauldron.fill(fluidStackToFill, side);
                    if (filled > 0) {
                        if (!player.isCreative()) {
                            player.getInventory().setStack(player.getInventory().selectedSlot, ItemUtil.consumeStack(heldStack));
                        }
                        world.playSound(player, pos, SoundEvents.ENTITY_PLAYER_SWIM, SoundCategory.BLOCKS, 0.5F, 0.4F / ((float) world.random.nextDouble() * 0.4F + 0.8F));
                        cauldron.markDirty(true);
                    }
                }
                return ActionResult.SUCCESS;
            }
            /*
             * Drain cauldron tank
             */
            final FluidStack fluidInCauldron = cauldron.getStackForTank(0);
            if (!fluidInCauldron.isEmpty()) {
                final ItemStack filledContainerFor = WKFluidAPI.getFilledContainerFor(fluidInCauldron.getFluid(), heldStack);
                final FluidStack fluidToDrain = WKFluidAPI.getFluidStackFor(filledContainerFor);
                if (!fluidToDrain.isEmpty()) {
                    if (!player.isCreative()) {
                        if (heldStack.getCount() > 1) {
                            if (!player.getInventory().insertStack(filledContainerFor)) {
                                return ActionResult.FAIL;
                            }
                            player.getInventory().setStack(player.getInventory().selectedSlot, ItemUtil.consumeStack(heldStack));
                        } else {
                            player.getInventory().setStack(player.getInventory().selectedSlot, filledContainerFor);
                        }
                    }
                    cauldron.drain(fluidToDrain.getAmount(), side);
                    world.playSound(player, pos, SoundEvents.ENTITY_PLAYER_SWIM, SoundCategory.BLOCKS, 0.5F, 0.4F / ((float) world.random.nextDouble() * 0.4F + 0.8F));
                    world.markDirty(pos);
                    cauldron.markDirty(true);
                    return ActionResult.SUCCESS;
                }
            }
            world.updateListeners(pos, world.getBlockState(pos), world.getBlockState(pos), 2);
            cauldron.markDirty();
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        return false;
    }

    @Override
    public ItemStack tryDrainFluid(WorldAccess world, BlockPos pos, BlockState state) {
        return Waterloggable.super.tryDrainFluid(world, pos, state);
    }

    @Override
    public boolean canFillWithFluid(BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
        return false;
    }

    public int getWaterLevel(BlockState state) {
        return state.get(WATER_LEVELS);
    }

    public void setWaterLevel(World world, BlockState state, BlockPos pos, int level) {
        if (level < 0) {
            level = 0;
        }
        if (level > TOP_LEVEL) {
            level = TOP_LEVEL;
        }
        final BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof WitchesCauldronBlockEntity) {
            world.setBlockState(pos, state.with(WATER_LEVELS, level), Block.NOTIFY_ALL);
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
