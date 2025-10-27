package cf.witcheskitchen.common.block;

import cf.witcheskitchen.api.block.WKBlockWithEntity;
import cf.witcheskitchen.api.fluid.IFluidContainer;
import cf.witcheskitchen.api.fluid.WKFluidAPI;
import cf.witcheskitchen.api.util.ItemUtil;
import cf.witcheskitchen.api.util.TimeHelper;
import cf.witcheskitchen.common.blockentity.WitchesCauldronBlockEntity;
import cf.witcheskitchen.common.registry.WKParticleTypes;
import cf.witcheskitchen.common.registry.WKSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class WitchesCauldronBlock extends WKBlockWithEntity implements SimpleWaterloggedBlock {

    public static final BooleanProperty HANGING = BooleanProperty.create("hanging");
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    public static final VoxelShape SHAPE = Shapes.or(
        box(2, 9, 1, 14, 11, 2),
        box(2, 9, 14, 14, 11, 15),
        box(14, 9, 2, 15, 11, 14),
        box(1, 9, 2, 2, 11, 14),
        box(2, 8, 13, 14, 9, 14),
        box(2, 8, 3, 3, 9, 13),
        box(13, 8, 3, 14, 9, 13),
        box(1, 2, 2, 3, 8, 14),
        box(2, 2, 13, 14, 8, 15),
        box(13, 2, 2, 15, 8, 14),
        box(2, 2, 1, 14, 8, 3),
        box(2, 1, 2, 14, 2, 14),
        box(3, 0, 3, 13, 1, 13)
    );

    public WitchesCauldronBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(BlockStateProperties.WATERLOGGED, false).setValue(HANGING, false).setValue(LIT, false));
    }

    static void playSoundToPlayer(Level world, BlockPos pos, Player player, SoundEvent event) {
        world.playSound(player, pos, event, SoundSource.BLOCKS, 0.5F, 0.4F / ((float) world.random.nextDouble() * 0.4F + 0.8F));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getNearestLookingDirection()).setValue(BlockStateProperties.WATERLOGGED, ctx.getLevel().getFluidState(ctx.getClickedPos()).getType() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            return Fluids.WATER.getSource(false);
        } else {
            return super.getFluidState(state);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    // Triggers Hanging state

    @Override
    protected BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (world instanceof LevelWriter modifiableWorld)
            modifiableWorld.setBlock(pos, state.setValue(HANGING, !world.getBlockState(pos.above()).isAir()), Block.UPDATE_ALL);
        return super.updateShape(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    // Cauldron fill/drain fluid logic

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        final var hand = player.getUsedItemHand();
        final var blockEntity = world.getBlockEntity(pos);
        final var heldStack = player.getItemInHand(hand);
        final var side = hit.getDirection();
        if (world.isClientSide()) {
            world.playSound(player, pos, WKSoundEvents.FERRET_IDLE_EVENT, SoundSource.BLOCKS, 1.0f, 1.0f);
        }
        if (blockEntity instanceof final WitchesCauldronBlockEntity cauldron) {
            if (cauldron.isPowered() && heldStack.is(Items.STICK)) {
                //TODO: brew item
                return InteractionResult.SUCCESS;
            }
            if (!(heldStack.getItem() instanceof IFluidContainer)) {
                // This is not even a fluid container
                // Fast return
                return InteractionResult.FAIL;
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
                            event = SoundEvents.BUCKET_EMPTY_LAVA;
                            world.setBlock(pos, state.setValue(LIT, true), Block.UPDATE_ALL);
                        } else {
                            event = SoundEvents.PLAYER_SWIM;
                            world.setBlock(pos, state.setValue(LIT, false), Block.UPDATE_ALL);
                        }
                        playSoundToPlayer(world, pos, player, event);
                        // Syncs the client
                        cauldron.setChanged();
                        return InteractionResult.SUCCESS;
                    } else {
                        return InteractionResult.FAIL;
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
                            return InteractionResult.FAIL;
                        }
                        final var fluid = WKFluidAPI.getStackFor(matchingStack);
                        if (stackInCauldron.getAmount() < fluid.getAmount()) {
                            // The amount of fluid in cauldron can't satisfy the capacity of the container
                            // (And we cannot return a fraction of water bucket fluid (3/4) or (1/2))
                            // Therefore this failed
                            return InteractionResult.FAIL;
                        }
                        final var drainedStack = cauldron.drain(fluid.getAmount(), side);
                        if (drainedStack.getAmount() != stackInCauldron.getAmount()) {
                            ItemUtil.replaceItem(player, hand, matchingStack);
                            final SoundEvent event;
                            if (drainedStack.hasFluid(Fluids.LAVA)) {
                                event = SoundEvents.BUCKET_FILL_LAVA;
                                world.setBlock(pos, state.setValue(LIT, false), Block.UPDATE_ALL);
                            } else {
                                event = SoundEvents.PLAYER_SWIM;
                                world.setBlock(pos, state.setValue(LIT, false), Block.UPDATE_ALL);
                            }
                            playSoundToPlayer(world, pos, player, event);
                            // Syncs the client
                            cauldron.setChanged();
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
        }
        return super.useWithoutItem(state, world, pos, player, hit);
    }

    @Override
    protected void entityInside(BlockState state, Level world, BlockPos pos, Entity entity, InsideBlockEffectApplier handler, boolean intersects) {
        super.entityInside(state, world, pos, entity, handler, intersects);
        final BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!world.isClientSide() && blockEntity instanceof WitchesCauldronBlockEntity cauldron) {
            if (cauldron.hasFluid()) {
                if (entity instanceof ItemEntity itemEntity) {
                    final Item item = itemEntity.getItem().getItem();
                    if (item != null && item != Items.AIR) {
                        cauldron.checkAndCollectIngredient(world, itemEntity);
                    }
                } else if (entity instanceof LivingEntity living) {
                    if (state.getValue(LIT) && world instanceof ServerLevel serverWorld) {
                        living.hurtServer(serverWorld, entity.damageSources().lava(), 4);
                        living.setRemainingFireTicks(TimeHelper.toTicks(15));
                    }
                }

            }
        }
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        final BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof WitchesCauldronBlockEntity cauldron) {
            if (cauldron.isPowered()) {
                if (random.nextInt(5) == 0) {
                    final float volume = 0.8F + (random.nextFloat() * 0.2F);
                    final float pitch = 0.8F + (random.nextFloat() * 0.2F);
                    world.playLocalSound(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, WKSoundEvents.BUBBLE, SoundSource.BLOCKS, volume, pitch, false);
                }
                final int color = cauldron.getColor();
                final double xPos = cauldron.getBlockPos().getX();
                final double yPos = cauldron.getBlockPos().getY();
                final double zPos = cauldron.getBlockPos().getZ();
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
                    world.addParticle((ParticleOptions) WKParticleTypes.MAGIC_SPARKLE, particleX, particleY, particleZ, r, g, b);
                }
            }
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WitchesCauldronBlockEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, LIT, HANGING, BlockStateProperties.WATERLOGGED);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }
}
