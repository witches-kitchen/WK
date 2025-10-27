package cf.witcheskitchen.common.block;

import cf.witcheskitchen.api.WKApi;
import cf.witcheskitchen.common.registry.WKBlocks;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.floats.Float2ObjectArrayMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.RedstoneSide;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Map;

@SuppressWarnings("deprecation")
public class SaltBlock extends Block {
    public static final EnumProperty<RedstoneSide> WIRE_CONNECTION_NORTH = BlockStateProperties.NORTH_REDSTONE;
    public static final EnumProperty<RedstoneSide> WIRE_CONNECTION_EAST = BlockStateProperties.EAST_REDSTONE;
    public static final EnumProperty<RedstoneSide> WIRE_CONNECTION_SOUTH = BlockStateProperties.SOUTH_REDSTONE;
    public static final EnumProperty<RedstoneSide> WIRE_CONNECTION_WEST = BlockStateProperties.WEST_REDSTONE;
    public static final Map<Direction, EnumProperty<RedstoneSide>> DIRECTION_TO_WIRE_CONNECTION_PROPERTY = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, WIRE_CONNECTION_NORTH, Direction.EAST, WIRE_CONNECTION_EAST, Direction.SOUTH, WIRE_CONNECTION_SOUTH, Direction.WEST, WIRE_CONNECTION_WEST));
    private static final VoxelShape DOT_SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D);
    private static final Map<Direction, VoxelShape> field_24414 = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.box(3.0D, 0.0D, 0.0D, 13.0D, 1.0D, 13.0D), Direction.SOUTH, Block.box(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 16.0D), Direction.EAST, Block.box(3.0D, 0.0D, 3.0D, 16.0D, 1.0D, 13.0D), Direction.WEST, Block.box(0.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D)));
    private static final Map<Direction, VoxelShape> field_24415 = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Shapes.or(field_24414.get(Direction.NORTH), Block.box(3.0D, 0.0D, 0.0D, 13.0D, 16.0D, 1.0D)), Direction.SOUTH, Shapes.or(field_24414.get(Direction.SOUTH), Block.box(3.0D, 0.0D, 15.0D, 13.0D, 16.0D, 16.0D)), Direction.EAST, Shapes.or(field_24414.get(Direction.EAST), Block.box(15.0D, 0.0D, 3.0D, 16.0D, 16.0D, 13.0D)), Direction.WEST, Shapes.or(field_24414.get(Direction.WEST), Block.box(0.0D, 0.0D, 3.0D, 1.0D, 16.0D, 13.0D))));
    private static final Map<BlockState, VoxelShape> SHAPES = Maps.newHashMap();
    private static final Float2ObjectArrayMap<VoxelShape> SALT_SHAPE_CACHE = new Float2ObjectArrayMap<>();

    private final BlockState dotState;

    public SaltBlock(BlockBehaviour.Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(WIRE_CONNECTION_NORTH, RedstoneSide.NONE).setValue(WIRE_CONNECTION_EAST, RedstoneSide.NONE).setValue(WIRE_CONNECTION_SOUTH, RedstoneSide.NONE).setValue(WIRE_CONNECTION_WEST, RedstoneSide.NONE));
        this.dotState = this.defaultBlockState().setValue(WIRE_CONNECTION_NORTH, RedstoneSide.SIDE).setValue(WIRE_CONNECTION_EAST, RedstoneSide.SIDE).setValue(WIRE_CONNECTION_SOUTH, RedstoneSide.SIDE).setValue(WIRE_CONNECTION_WEST, RedstoneSide.SIDE);

        for (BlockState blockState : this.getStateDefinition().getPossibleStates()) {
            SHAPES.put(blockState, this.getShapeForState(blockState));
        }
    }

    private static boolean isFullyConnected(BlockState state) {
        return state.getValue(WIRE_CONNECTION_NORTH).isConnected() && state.getValue(WIRE_CONNECTION_SOUTH).isConnected() && state.getValue(WIRE_CONNECTION_EAST).isConnected() && state.getValue(WIRE_CONNECTION_WEST).isConnected();
    }

    private static boolean isNotConnected(BlockState state) {
        return !state.getValue(WIRE_CONNECTION_NORTH).isConnected() && !state.getValue(WIRE_CONNECTION_SOUTH).isConnected() && !state.getValue(WIRE_CONNECTION_EAST).isConnected() && !state.getValue(WIRE_CONNECTION_WEST).isConnected();
    }

    protected static boolean connectsTo(BlockState state) {
        return connectsTo(state, null);
    }

    protected static boolean connectsTo(BlockState state, @Nullable Direction dir) {
        if (state.is(WKBlocks.SALT_BLOCK)) {
            return true;
        } else if (state.is(Blocks.REPEATER)) {
            Direction direction = state.getValue(RepeaterBlock.FACING);
            return direction == dir || direction.getOpposite() == dir;
        } else if (state.is(Blocks.OBSERVER)) {
            return dir == state.getValue(ObserverBlock.FACING);
        } else {
            return state.isSignalSource() && dir != null;
        }
    }

    private static VoxelShape getSaltShape(float stepHeight) {
        return SALT_SHAPE_CACHE.computeIfAbsent(stepHeight, SaltBlock::createSaltShape);
    }

    private static VoxelShape createSaltShape(double stepHeight) {
        return Block.box(0, 0, 0, 16, 17 + 16 * stepHeight, 16);
    }


    private VoxelShape getShapeForState(BlockState state) {
        VoxelShape voxelShape = DOT_SHAPE;

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            RedstoneSide wireConnection = state.getValue(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction));
            if (wireConnection == RedstoneSide.SIDE) {
                voxelShape = Shapes.or(voxelShape, field_24414.get(direction));
            } else if (wireConnection == RedstoneSide.UP) {
                voxelShape = Shapes.or(voxelShape, field_24415.get(direction));
            }
        }
        return voxelShape;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.getPlacementState(ctx.getLevel(), this.dotState, ctx.getClickedPos());
    }

    private BlockState getPlacementState(BlockGetter world, BlockState state, BlockPos pos) {
        boolean bl = isNotConnected(state);
        state = this.getMissingConnections(world, this.defaultBlockState(), pos);
        if (!bl || !isNotConnected(state)) {
            boolean bl2 = state.getValue(WIRE_CONNECTION_NORTH).isConnected();
            boolean bl3 = state.getValue(WIRE_CONNECTION_SOUTH).isConnected();
            boolean bl4 = state.getValue(WIRE_CONNECTION_EAST).isConnected();
            boolean bl5 = state.getValue(WIRE_CONNECTION_WEST).isConnected();
            boolean bl6 = !bl2 && !bl3;
            boolean bl7 = !bl4 && !bl5;
            if (!bl5 && bl6) {
                state = state.setValue(WIRE_CONNECTION_WEST, RedstoneSide.SIDE);
            }

            if (!bl4 && bl6) {
                state = state.setValue(WIRE_CONNECTION_EAST, RedstoneSide.SIDE);
            }

            if (!bl2 && bl7) {
                state = state.setValue(WIRE_CONNECTION_NORTH, RedstoneSide.SIDE);
            }

            if (!bl3 && bl7) {
                state = state.setValue(WIRE_CONNECTION_SOUTH, RedstoneSide.SIDE);
            }
        }
        return state;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (context instanceof EntityCollisionContext entityShapeContext && entityShapeContext.getEntity() instanceof LivingEntity living && WKApi.isSpiritualEntity(living)) {
            Entity entity = entityShapeContext.getEntity();
            if (entity instanceof LivingEntity livingEntity) {
                boolean spiritual = WKApi.isSpiritualEntity(livingEntity);
                if (spiritual && !WKApi.isGreaterDemon(livingEntity)) {
                    boolean onSalt = world.getBlockState(livingEntity.blockPosition().offset(0, 0, 0)).getBlock() instanceof SaltBlock;
                    if (!onSalt) {
                        return getSaltShape(livingEntity.maxUpStep());
                    } else {
                        livingEntity.igniteForSeconds(1);
                    }
                }
            }
        }
        return super.getCollisionShape(state, world, pos, context);
    }

    private BlockState getMissingConnections(BlockGetter world, BlockState state, BlockPos pos) {
        boolean bl = !world.getBlockState(pos.above()).isRedstoneConductor(world, pos);

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            if (!state.getValue(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction)).isConnected()) {
                RedstoneSide wireConnection = this.getRenderConnectionType(world, pos, direction, bl);
                state = state.setValue(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction), wireConnection);
            }
        }
        return state;
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (direction == Direction.DOWN) {
            return state;
        } else if (direction == Direction.UP) {
            return this.getPlacementState(world, state, pos);
        } else {
            RedstoneSide wireConnection = this.getRenderConnectionType(world, pos, direction);
            return wireConnection.isConnected() == state.getValue(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction)).isConnected() && !isFullyConnected(state) ? state.setValue(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction), wireConnection) : this.getPlacementState(world, this.dotState.setValue(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction), wireConnection), pos);
        }
    }

    @Override
    public void updateIndirectNeighbourShapes(BlockState state, LevelAccessor world, BlockPos pos, int flags, int maxUpdateDepth) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            RedstoneSide wireConnection = state.getValue(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction));
            if (wireConnection != RedstoneSide.NONE && !world.getBlockState(mutable.setWithOffset(pos, direction)).is(this)) {
                mutable.move(Direction.DOWN);
                BlockState blockState = world.getBlockState(mutable);
                if (!blockState.is(Blocks.OBSERVER)) {
                    BlockPos blockPos = mutable.relative(direction.getOpposite());
                    BlockState blockState2 = blockState.updateShape(world, world, mutable, direction.getOpposite(), blockPos, world.getBlockState(blockPos), world.getRandom());
                    updateOrDestroy(blockState, blockState2, world, mutable, flags, maxUpdateDepth);
                }

                mutable.setWithOffset(pos, direction).move(Direction.UP);
                BlockState blockState3 = world.getBlockState(mutable);
                if (!blockState3.is(Blocks.OBSERVER)) {
                    BlockPos blockPos2 = mutable.relative(direction.getOpposite());
                    BlockState blockState4 = blockState3.updateShape(world, world, mutable, direction.getOpposite(), blockPos2, world.getBlockState(blockPos2), world.getRandom());
                    updateOrDestroy(blockState3, blockState4, world, mutable, flags, maxUpdateDepth);
                }
            }
        }

    }

    private RedstoneSide getRenderConnectionType(BlockGetter world, BlockPos pos, Direction direction) {
        return this.getRenderConnectionType(world, pos, direction, !world.getBlockState(pos.above()).isRedstoneConductor(world, pos));
    }

    private RedstoneSide getRenderConnectionType(BlockGetter world, BlockPos pos, Direction direction, boolean bl) {
        BlockPos blockPos = pos.relative(direction);
        BlockState blockState = world.getBlockState(blockPos);
        if (bl) {
            boolean bl2 = this.canRunOnTop(world, blockPos, blockState);
            if (bl2 && connectsTo(world.getBlockState(blockPos.above()))) {
                if (blockState.isFaceSturdy(world, blockPos, direction.getOpposite())) {
                    return RedstoneSide.UP;
                }
                return RedstoneSide.SIDE;
            }
        }
        return !connectsTo(blockState, direction) && (blockState.isRedstoneConductor(world, blockPos) || !connectsTo(world.getBlockState(blockPos.below()))) ? RedstoneSide.NONE : RedstoneSide.SIDE;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockPos = pos.below();
        BlockState blockState = world.getBlockState(blockPos);
        return this.canRunOnTop(world, blockPos, blockState);
    }

    private boolean canRunOnTop(BlockGetter world, BlockPos pos, BlockState floor) {
        return floor.isFaceSturdy(world, pos, Direction.UP) || floor.is(Blocks.HOPPER);
    }

    private void updateNeighbors(Level world, BlockPos pos) {
        if (world.getBlockState(pos).is(this)) {
            world.updateNeighborsAt(pos, this, Orientation.random(world.getRandom()));
            Direction[] var3 = Direction.values();

            for (Direction direction : var3) {
                world.updateNeighborsAt(pos.relative(direction), this, Orientation.random(world.getRandom()));
            }
        }
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!oldState.is(state.getBlock()) && !world.isClientSide()) {
            for (Direction direction : Direction.Plane.VERTICAL) {
                world.updateNeighborsAt(pos.relative(direction), this, Orientation.random(world.getRandom()));
            }
            this.updateOffsetNeighbors(world, pos);
        }
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel world, BlockPos pos, boolean moved) {
        if (!moved) {
            super.affectNeighborsAfterRemoval(state, world, pos, false);
            if (!world.isClientSide()) {
                Direction[] var6 = Direction.values();

                for (Direction direction : var6) {
                    world.updateNeighborsAt(pos.relative(direction), this, Orientation.random(world.getRandom()));
                }

                this.updateOffsetNeighbors(world, pos);
            }
        }
    }

    private void updateOffsetNeighbors(Level world, BlockPos pos) {
        Iterator<Direction> var3 = Direction.Plane.HORIZONTAL.iterator();

        Direction direction2;
        while (var3.hasNext()) {
            direction2 = var3.next();
            this.updateNeighbors(world, pos.relative(direction2));
        }

        var3 = Direction.Plane.HORIZONTAL.iterator();

        while (var3.hasNext()) {
            direction2 = var3.next();
            BlockPos blockPos = pos.relative(direction2);
            if (world.getBlockState(blockPos).isRedstoneConductor(world, blockPos)) {
                this.updateNeighbors(world, blockPos.above());
            } else {
                this.updateNeighbors(world, blockPos.below());
            }
        }
    }

    @Override
    protected void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, @Nullable Orientation wireOrientation, boolean notify) {
        if (!world.isClientSide()) {
            if (!state.canSurvive(world, pos)) {
                dropResources(state, world, pos);
                world.removeBlock(pos, false);
            }
        }
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return switch (rotation) {
            case CLOCKWISE_180 ->
                state.setValue(WIRE_CONNECTION_NORTH, state.getValue(WIRE_CONNECTION_SOUTH)).setValue(WIRE_CONNECTION_EAST, state.getValue(WIRE_CONNECTION_WEST)).setValue(WIRE_CONNECTION_SOUTH, state.getValue(WIRE_CONNECTION_NORTH)).setValue(WIRE_CONNECTION_WEST, state.getValue(WIRE_CONNECTION_EAST));
            case COUNTERCLOCKWISE_90 ->
                state.setValue(WIRE_CONNECTION_NORTH, state.getValue(WIRE_CONNECTION_EAST)).setValue(WIRE_CONNECTION_EAST, state.getValue(WIRE_CONNECTION_SOUTH)).setValue(WIRE_CONNECTION_SOUTH, state.getValue(WIRE_CONNECTION_WEST)).setValue(WIRE_CONNECTION_WEST, state.getValue(WIRE_CONNECTION_NORTH));
            case CLOCKWISE_90 ->
                state.setValue(WIRE_CONNECTION_NORTH, state.getValue(WIRE_CONNECTION_WEST)).setValue(WIRE_CONNECTION_EAST, state.getValue(WIRE_CONNECTION_NORTH)).setValue(WIRE_CONNECTION_SOUTH, state.getValue(WIRE_CONNECTION_EAST)).setValue(WIRE_CONNECTION_WEST, state.getValue(WIRE_CONNECTION_SOUTH));
            default -> state;
        };
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return switch (mirror) {
            case LEFT_RIGHT ->
                state.setValue(WIRE_CONNECTION_NORTH, state.getValue(WIRE_CONNECTION_SOUTH)).setValue(WIRE_CONNECTION_SOUTH, state.getValue(WIRE_CONNECTION_NORTH));
            case FRONT_BACK ->
                state.setValue(WIRE_CONNECTION_EAST, state.getValue(WIRE_CONNECTION_WEST)).setValue(WIRE_CONNECTION_WEST, state.getValue(WIRE_CONNECTION_EAST));
            default -> super.mirror(state, mirror);
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WIRE_CONNECTION_NORTH, WIRE_CONNECTION_EAST, WIRE_CONNECTION_SOUTH, WIRE_CONNECTION_WEST);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (player.getAbilities().mayBuild) {
            if (isFullyConnected(state) || isNotConnected(state)) {
                BlockState blockState = isFullyConnected(state) ? this.defaultBlockState() : this.dotState;
                blockState = this.getPlacementState(world, blockState, pos);
                if (blockState != state) {
                    world.setBlock(pos, blockState, Block.UPDATE_ALL);
                    this.updateForNewState(world, pos, state, blockState);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    private void updateForNewState(Level world, BlockPos pos, BlockState oldState, BlockState newState) {
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos blockPos = pos.relative(direction);
            if (oldState.getValue(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction)).isConnected() != newState.getValue(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction)).isConnected() && world.getBlockState(blockPos).isRedstoneConductor(world, blockPos)) {
                world.updateNeighborsAtExceptFromFacing(blockPos, newState.getBlock(), direction.getOpposite(), Orientation.random(world.getRandom()));
            }
        }
    }
}
