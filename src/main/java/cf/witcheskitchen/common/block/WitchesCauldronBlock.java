package cf.witcheskitchen.common.block;

import cf.witcheskitchen.api.block.WKBlockWithEntity;
import cf.witcheskitchen.api.fluid.IFluidContainer;
import cf.witcheskitchen.api.fluid.WKFluidAPI;
import cf.witcheskitchen.api.util.ItemUtil;
import cf.witcheskitchen.api.util.TimeHelper;
import cf.witcheskitchen.common.blockentity.WitchesCauldronBlockEntity;
import cf.witcheskitchen.common.registry.WKParticleTypes;
import cf.witcheskitchen.common.registry.WKSoundEvents;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class WitchesCauldronBlock extends WKBlockWithEntity implements Waterloggable {

    public static final BooleanProperty HANGING = BooleanProperty.of("hanging");
    public static final BooleanProperty LIT = Properties.LIT;
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
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
        this.setDefaultState(this.getStateManager().getDefaultState().with(FACING, Direction.NORTH).with(Properties.WATERLOGGED, false).with(HANGING, false).with(LIT, false));
    }

    static void playSoundToPlayer(World world, BlockPos pos, PlayerEntity player, SoundEvent event) {
        world.playSound(player, pos, event, SoundCategory.BLOCKS, 0.5F, 0.4F / ((float) world.random.nextDouble() * 0.4F + 0.8F));
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

    // Cauldron fill/drain fluid logic
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        final var blockEntity = world.getBlockEntity(pos);
        final var heldStack = player.getStackInHand(hand);
        final var side = hit.getSide();
        if (world.isClient) {
            world.playSound(player, pos, WKSoundEvents.FERRET_IDLE_EVENT, SoundCategory.BLOCKS, 1.0f, 1.0f);
        }
        if (blockEntity instanceof final WitchesCauldronBlockEntity cauldron) {
            if (cauldron.isPowered() && heldStack.isOf(Items.STICK)) {
                //TODO: brew item
                return ActionResult.SUCCESS;
            }
            if (!(heldStack.getItem() instanceof IFluidContainer)) {
                // This is not even a fluid container
                // Fast return
                return ActionResult.FAIL;
            }
            if (!heldStack.isEmpty()) {
                // FluidStack in hand
                final var heldFluid = WKFluidAPI.getStackFor(heldStack);
                if (!heldFluid.isEmpty() && cauldron.canFill(heldFluid, side)) {
                    final int i = cauldron.fill(heldFluid, side); // filled amount of fluid
                    if (i > 0) {
                        ItemUtil.consumeItem(player, hand);
                        final SoundEvent event;
                        if (heldFluid.hasFluid(Fluids.LAVA)) {
                            event = SoundEvents.ITEM_BUCKET_EMPTY_LAVA;
                            world.setBlockState(pos, state.with(LIT, true), Block.NOTIFY_ALL);
                        } else {
                            event = SoundEvents.ENTITY_PLAYER_SWIM;
                            world.setBlockState(pos, state.with(LIT, false), Block.NOTIFY_ALL);
                        }
                        playSoundToPlayer(world, pos, player, event);
                        // Syncs the client
                        cauldron.markDirty();
                        return ActionResult.SUCCESS;
                    } else {
                        return ActionResult.FAIL;
                    }
                } else {
                    // Otherwise, the fluid container is empty
                    // Let's try to fill it.
                    final var stackInCauldron = cauldron.getFluidStack();
                    if (!stackInCauldron.isEmpty()) {
                        final var matchingStack = WKFluidAPI.getMatchingStackFor(stackInCauldron.getFluid(), heldStack);
                        if (matchingStack.isEmpty()) {
                            // Matching stack is air
                            // Which means it cannot hold the fluid from the cauldron
                            // This failed.
                            return ActionResult.FAIL;
                        }
                        final var fluid = WKFluidAPI.getStackFor(matchingStack);
                        if (stackInCauldron.getAmount() < fluid.getAmount()) {
                            // The amount of fluid in cauldron can't satisfy the capacity of the container
                            // (And we cannot return a fraction of water bucket fluid (3/4) or (1/2))
                            // Therefore this failed
                            return ActionResult.FAIL;
                        }
                        final var drainedStack = cauldron.drain(fluid.getAmount(), side);
                        if (drainedStack.getAmount() != stackInCauldron.getAmount()) {
                            ItemUtil.replaceItem(player, hand, matchingStack);
                            final SoundEvent event;
                            if (drainedStack.hasFluid(Fluids.LAVA)) {
                                event = SoundEvents.ITEM_BUCKET_FILL_LAVA;
                                world.setBlockState(pos, state.with(LIT, false), Block.NOTIFY_ALL);
                            } else {
                                event = SoundEvents.ENTITY_PLAYER_SWIM;
                                world.setBlockState(pos, state.with(LIT, false), Block.NOTIFY_ALL);
                            }
                            playSoundToPlayer(world, pos, player, event);
                            // Syncs the client
                            cauldron.markDirty();
                            return ActionResult.SUCCESS;
                        }
                    }
                }
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
        final BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!world.isClient && blockEntity instanceof WitchesCauldronBlockEntity cauldron) {
            if (cauldron.hasFluid()) {
                if (entity instanceof ItemEntity itemEntity) {
                    final Item item = itemEntity.getStack().getItem();
                    if (item != null && item != Items.AIR) {
                        cauldron.checkAndCollectIngredient(world, itemEntity);
                    }
                } else if (entity instanceof LivingEntity living) {
                    if (state.get(LIT)) {
                        living.damage(DamageSource.LAVA, 4);
                        living.setFireTicks(TimeHelper.toTicks(15));
                    }
                }

            }
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, RandomGenerator random) {
        final BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof WitchesCauldronBlockEntity cauldron) {
            if (cauldron.isPowered()) {
                if (random.nextInt(5) == 0) {
                    final float volume = 0.8F + (random.nextFloat() * 0.2F);
                    final float pitch = 0.8F + (random.nextFloat() * 0.2F);
                    world.playSound(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, WKSoundEvents.BUBBLE, SoundCategory.BLOCKS, volume, pitch, false);
                }
                final int color = cauldron.getColor();
                final double xPos = cauldron.getPos().getX();
                final double yPos = cauldron.getPos().getY();
                final double zPos = cauldron.getPos().getZ();
                final float depth = (float) (((cauldron.getPercentFilled() - 1) * (0.4D)) + (0.6D));
                final double r = ((color >> 16) & 0xff) / 255F;
                final double g = ((color >> 8) & 0xff) / 255F;
                final double b = (color & 0xff) / 255F;
                final double left = (random.nextDouble() * 0.4D) + 0.3D;
                final double front = (random.nextDouble() * 0.4D) + 0.3D;
                final double particleX = xPos + left;
                final double particleY = yPos + depth - 0.3D;
                final double particleZ = zPos + front;
                for (int i = 0; i < 2; i++) {
                    world.addParticle((ParticleEffect) WKParticleTypes.MAGIC_SPARKLE, particleX, particleY, particleZ, r, g, b);
                }
            }
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
        builder.add(FACING, LIT, HANGING, Properties.WATERLOGGED);
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
