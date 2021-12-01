package cf.witcheskitchen.common.blocks;

import cf.witcheskitchen.api.WKApi;
import cf.witcheskitchen.common.registry.WKBlocks;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.floats.Float2ObjectArrayMap;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.enums.WireConnection;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

@SuppressWarnings("deprecation")
public class SaltBlock extends Block {
    public static final EnumProperty<WireConnection> WIRE_CONNECTION_NORTH;
    public static final EnumProperty<WireConnection> WIRE_CONNECTION_EAST;
    public static final EnumProperty<WireConnection> WIRE_CONNECTION_SOUTH;
    public static final EnumProperty<WireConnection> WIRE_CONNECTION_WEST;
    public static final Map<Direction, EnumProperty<WireConnection>> DIRECTION_TO_WIRE_CONNECTION_PROPERTY;
    private static final VoxelShape DOT_SHAPE;
    private static final Map<Direction, VoxelShape> field_24414;
    private static final Map<Direction, VoxelShape> field_24415;
    private static final Map<BlockState, VoxelShape> SHAPES;
    private static final Vec3f COLOR = new Vec3f(2147483647, 2147483647, 2147483647);
    private static final Float2ObjectArrayMap<VoxelShape> SALT_SHAPE_CACHE = new Float2ObjectArrayMap<>();

    static {
        WIRE_CONNECTION_NORTH = Properties.NORTH_WIRE_CONNECTION;
        WIRE_CONNECTION_EAST = Properties.EAST_WIRE_CONNECTION;
        WIRE_CONNECTION_SOUTH = Properties.SOUTH_WIRE_CONNECTION;
        WIRE_CONNECTION_WEST = Properties.WEST_WIRE_CONNECTION;
        DIRECTION_TO_WIRE_CONNECTION_PROPERTY = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, WIRE_CONNECTION_NORTH, Direction.EAST, WIRE_CONNECTION_EAST, Direction.SOUTH, WIRE_CONNECTION_SOUTH, Direction.WEST, WIRE_CONNECTION_WEST));
        DOT_SHAPE = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D);
        field_24414 = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.createCuboidShape(3.0D, 0.0D, 0.0D, 13.0D, 1.0D, 13.0D), Direction.SOUTH, Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 16.0D), Direction.EAST, Block.createCuboidShape(3.0D, 0.0D, 3.0D, 16.0D, 1.0D, 13.0D), Direction.WEST, Block.createCuboidShape(0.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D)));
        field_24415 = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, VoxelShapes.union(field_24414.get(Direction.NORTH), Block.createCuboidShape(3.0D, 0.0D, 0.0D, 13.0D, 16.0D, 1.0D)), Direction.SOUTH, VoxelShapes.union(field_24414.get(Direction.SOUTH), Block.createCuboidShape(3.0D, 0.0D, 15.0D, 13.0D, 16.0D, 16.0D)), Direction.EAST, VoxelShapes.union(field_24414.get(Direction.EAST), Block.createCuboidShape(15.0D, 0.0D, 3.0D, 16.0D, 16.0D, 13.0D)), Direction.WEST, VoxelShapes.union(field_24414.get(Direction.WEST), Block.createCuboidShape(0.0D, 0.0D, 3.0D, 1.0D, 16.0D, 13.0D))));
        SHAPES = Maps.newHashMap();
    }

    private final BlockState dotState;

    public SaltBlock(FabricBlockSettings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WIRE_CONNECTION_NORTH, WireConnection.NONE).with(WIRE_CONNECTION_EAST, WireConnection.NONE).with(WIRE_CONNECTION_SOUTH, WireConnection.NONE).with(WIRE_CONNECTION_WEST, WireConnection.NONE));
        this.dotState = this.getDefaultState().with(WIRE_CONNECTION_NORTH, WireConnection.SIDE).with(WIRE_CONNECTION_EAST, WireConnection.SIDE).with(WIRE_CONNECTION_SOUTH, WireConnection.SIDE).with(WIRE_CONNECTION_WEST, WireConnection.SIDE);

        for (BlockState blockState : this.getStateManager().getStates()) {
            SHAPES.put(blockState, this.getShapeForState(blockState));
        }
    }

    private static boolean isFullyConnected(BlockState state) {
        return state.get(WIRE_CONNECTION_NORTH).isConnected() && state.get(WIRE_CONNECTION_SOUTH).isConnected() && state.get(WIRE_CONNECTION_EAST).isConnected() && state.get(WIRE_CONNECTION_WEST).isConnected();
    }

    private static boolean isNotConnected(BlockState state) {
        return !state.get(WIRE_CONNECTION_NORTH).isConnected() && !state.get(WIRE_CONNECTION_SOUTH).isConnected() && !state.get(WIRE_CONNECTION_EAST).isConnected() && !state.get(WIRE_CONNECTION_WEST).isConnected();
    }

    protected static boolean connectsTo(BlockState state) {
        return connectsTo(state, null);
    }

    protected static boolean connectsTo(BlockState state, @Nullable Direction dir) {
        if (state.isOf(WKBlocks.SALT_BLOCK)) {
            return true;
        } else if (state.isOf(Blocks.REPEATER)) {
            Direction direction = state.get(RepeaterBlock.FACING);
            return direction == dir || direction.getOpposite() == dir;
        } else if (state.isOf(Blocks.OBSERVER)) {
            return dir == state.get(ObserverBlock.FACING);
        } else {
            return state.emitsRedstonePower() && dir != null;
        }
    }

    private static VoxelShape getSaltShape(float stepHeight) {
        return SALT_SHAPE_CACHE.computeIfAbsent(stepHeight, SaltBlock::createSaltShape);
    }

    private static VoxelShape createSaltShape(double stepHeight) {
        return Block.createCuboidShape(0, 0, 0, 16, 17 + 16 * stepHeight, 16);
    }

    private VoxelShape getShapeForState(BlockState state) {
        VoxelShape voxelShape = DOT_SHAPE;

        for (Direction direction : Direction.Type.HORIZONTAL) {
            WireConnection wireConnection = state.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction));
            if (wireConnection == WireConnection.SIDE) {
                voxelShape = VoxelShapes.union(voxelShape, field_24414.get(direction));
            } else if (wireConnection == WireConnection.UP) {
                voxelShape = VoxelShapes.union(voxelShape, field_24415.get(direction));
            }
        }

        return voxelShape;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES.get(state);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getPlacementState(ctx.getWorld(), this.dotState, ctx.getBlockPos());
    }

    private BlockState getPlacementState(BlockView world, BlockState state, BlockPos pos) {
        boolean bl = isNotConnected(state);
        state = this.method_27843(world, this.getDefaultState(), pos);
        if (!bl || !isNotConnected(state)) {
            boolean bl2 = state.get(WIRE_CONNECTION_NORTH).isConnected();
            boolean bl3 = state.get(WIRE_CONNECTION_SOUTH).isConnected();
            boolean bl4 = state.get(WIRE_CONNECTION_EAST).isConnected();
            boolean bl5 = state.get(WIRE_CONNECTION_WEST).isConnected();
            boolean bl6 = !bl2 && !bl3;
            boolean bl7 = !bl4 && !bl5;
            if (!bl5 && bl6) {
                state = state.with(WIRE_CONNECTION_WEST, WireConnection.SIDE);
            }

            if (!bl4 && bl6) {
                state = state.with(WIRE_CONNECTION_EAST, WireConnection.SIDE);
            }

            if (!bl2 && bl7) {
                state = state.with(WIRE_CONNECTION_NORTH, WireConnection.SIDE);
            }

            if (!bl3 && bl7) {
                state = state.with(WIRE_CONNECTION_SOUTH, WireConnection.SIDE);
            }

        }
        return state;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext entityShapeContext && entityShapeContext.getEntity() instanceof LivingEntity living && WKApi.isSpiritualEntity(living)) {
            return VoxelShapes.fullCube();
        }
        return super.getCollisionShape(state, world, pos, context);
    }

    private BlockState method_27843(BlockView world, BlockState state, BlockPos pos) {
        boolean bl = !world.getBlockState(pos.up()).isSolidBlock(world, pos);

        for (Direction direction : Direction.Type.HORIZONTAL) {
            if (!state.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction)).isConnected()) {
                WireConnection wireConnection = this.getRenderConnectionType(world, pos, direction, bl);
                state = state.with(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction), wireConnection);
            }
        }

        return state;
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.DOWN) {
            return state;
        } else if (direction == Direction.UP) {
            return this.getPlacementState(world, state, pos);
        } else {
            WireConnection wireConnection = this.getRenderConnectionType(world, pos, direction);
            return wireConnection.isConnected() == state.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction)).isConnected() && !isFullyConnected(state) ? state.with(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction), wireConnection) : this.getPlacementState(world, this.dotState.with(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction), wireConnection), pos);
        }
    }

    public void prepare(BlockState state, WorldAccess world, BlockPos pos, int flags, int maxUpdateDepth) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for (Direction direction : Direction.Type.HORIZONTAL) {
            WireConnection wireConnection = state.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction));
            if (wireConnection != WireConnection.NONE && !world.getBlockState(mutable.set(pos, direction)).isOf(this)) {
                mutable.move(Direction.DOWN);
                BlockState blockState = world.getBlockState(mutable);
                if (!blockState.isOf(Blocks.OBSERVER)) {
                    BlockPos blockPos = mutable.offset(direction.getOpposite());
                    BlockState blockState2 = blockState.getStateForNeighborUpdate(direction.getOpposite(), world.getBlockState(blockPos), world, mutable, blockPos);
                    replace(blockState, blockState2, world, mutable, flags, maxUpdateDepth);
                }

                mutable.set(pos, direction).move(Direction.UP);
                BlockState blockState3 = world.getBlockState(mutable);
                if (!blockState3.isOf(Blocks.OBSERVER)) {
                    BlockPos blockPos2 = mutable.offset(direction.getOpposite());
                    BlockState blockState4 = blockState3.getStateForNeighborUpdate(direction.getOpposite(), world.getBlockState(blockPos2), world, mutable, blockPos2);
                    replace(blockState3, blockState4, world, mutable, flags, maxUpdateDepth);
                }
            }
        }

    }

    private WireConnection getRenderConnectionType(BlockView world, BlockPos pos, Direction direction) {
        return this.getRenderConnectionType(world, pos, direction, !world.getBlockState(pos.up()).isSolidBlock(world, pos));
    }

    private WireConnection getRenderConnectionType(BlockView world, BlockPos pos, Direction direction, boolean bl) {
        BlockPos blockPos = pos.offset(direction);
        BlockState blockState = world.getBlockState(blockPos);
        if (bl) {
            boolean bl2 = this.canRunOnTop(world, blockPos, blockState);
            if (bl2 && connectsTo(world.getBlockState(blockPos.up()))) {
                if (blockState.isSideSolidFullSquare(world, blockPos, direction.getOpposite())) {
                    return WireConnection.UP;
                }

                return WireConnection.SIDE;
            }
        }

        return !connectsTo(blockState, direction) && (blockState.isSolidBlock(world, blockPos) || !connectsTo(world.getBlockState(blockPos.down()))) ? WireConnection.NONE : WireConnection.SIDE;
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        BlockState blockState = world.getBlockState(blockPos);
        return this.canRunOnTop(world, blockPos, blockState);
    }

    private boolean canRunOnTop(BlockView world, BlockPos pos, BlockState floor) {
        return floor.isSideSolidFullSquare(world, pos, Direction.UP) || floor.isOf(Blocks.HOPPER);
    }

    private void updateNeighbors(World world, BlockPos pos) {
        if (world.getBlockState(pos).isOf(this)) {
            world.updateNeighborsAlways(pos, this);
            Direction[] var3 = Direction.values();

            for (Direction direction : var3) {
                world.updateNeighborsAlways(pos.offset(direction), this);
            }
        }
    }

    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (!oldState.isOf(state.getBlock()) && !world.isClient) {
            for (Direction direction : Direction.Type.VERTICAL) {
                world.updateNeighborsAlways(pos.offset(direction), this);
            }

            this.updateOffsetNeighbors(world, pos);
        }
    }

    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!moved && !state.isOf(newState.getBlock())) {
            super.onStateReplaced(state, world, pos, newState, false);
            if (!world.isClient) {
                Direction[] var6 = Direction.values();

                for (Direction direction : var6) {
                    world.updateNeighborsAlways(pos.offset(direction), this);
                }

                this.updateOffsetNeighbors(world, pos);
            }
        }
    }

    private void updateOffsetNeighbors(World world, BlockPos pos) {
        Iterator<Direction> var3 = Direction.Type.HORIZONTAL.iterator();

        Direction direction2;
        while (var3.hasNext()) {
            direction2 = var3.next();
            this.updateNeighbors(world, pos.offset(direction2));
        }

        var3 = Direction.Type.HORIZONTAL.iterator();

        while (var3.hasNext()) {
            direction2 = var3.next();
            BlockPos blockPos = pos.offset(direction2);
            if (world.getBlockState(blockPos).isSolidBlock(world, blockPos)) {
                this.updateNeighbors(world, blockPos.up());
            } else {
                this.updateNeighbors(world, blockPos.down());
            }
        }

    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (!world.isClient) {
            if (!state.canPlaceAt(world, pos)) {
                dropStacks(state, world, pos);
                world.removeBlock(pos, false);
            }

        }
    }

    public boolean emitsRedstonePower(BlockState state) {
        return false;
    }

    private void addPoweredParticles(World world, Random random, BlockPos pos, Direction direction, Direction direction2, float f, float g) {
        float h = g - f;
        if (!(random.nextFloat() >= 0.2F * h)) {
            float j = f + h * random.nextFloat();
            double d = 0.5D + (double) (0.4375F * (float) direction.getOffsetX()) + (double) (j * (float) direction2.getOffsetX());
            double e = 0.5D + (double) (0.4375F * (float) direction.getOffsetY()) + (double) (j * (float) direction2.getOffsetY());
            double k = 0.5D + (double) (0.4375F * (float) direction.getOffsetZ()) + (double) (j * (float) direction2.getOffsetZ());
            world.addParticle(new DustParticleEffect(SaltBlock.COLOR, 1.0F), (double) pos.getX() + d, (double) pos.getY() + e, (double) pos.getZ() + k, 0.0D, 0.0D, 0.0D);
        }
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextFloat() > 0.8f) {
            for (Direction direction : Direction.Type.HORIZONTAL) {
                WireConnection wireConnection = state.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction));
                switch (wireConnection) {
                    case UP:
                        this.addPoweredParticles(world, random, pos, direction, Direction.UP, -0.5F, 0.5F);
                    case SIDE:
                        this.addPoweredParticles(world, random, pos, Direction.DOWN, direction, 0.0F, 0.5F);
                        break;
                    case NONE:
                    default:
                        this.addPoweredParticles(world, random, pos, Direction.DOWN, direction, 0.0F, 0.3F);
                }
            }
        }
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return switch (rotation) {
            case CLOCKWISE_180 -> state.with(WIRE_CONNECTION_NORTH, state.get(WIRE_CONNECTION_SOUTH)).with(WIRE_CONNECTION_EAST, state.get(WIRE_CONNECTION_WEST)).with(WIRE_CONNECTION_SOUTH, state.get(WIRE_CONNECTION_NORTH)).with(WIRE_CONNECTION_WEST, state.get(WIRE_CONNECTION_EAST));
            case COUNTERCLOCKWISE_90 -> state.with(WIRE_CONNECTION_NORTH, state.get(WIRE_CONNECTION_EAST)).with(WIRE_CONNECTION_EAST, state.get(WIRE_CONNECTION_SOUTH)).with(WIRE_CONNECTION_SOUTH, state.get(WIRE_CONNECTION_WEST)).with(WIRE_CONNECTION_WEST, state.get(WIRE_CONNECTION_NORTH));
            case CLOCKWISE_90 -> state.with(WIRE_CONNECTION_NORTH, state.get(WIRE_CONNECTION_WEST)).with(WIRE_CONNECTION_EAST, state.get(WIRE_CONNECTION_NORTH)).with(WIRE_CONNECTION_SOUTH, state.get(WIRE_CONNECTION_EAST)).with(WIRE_CONNECTION_WEST, state.get(WIRE_CONNECTION_SOUTH));
            default -> state;
        };
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return switch (mirror) {
            case LEFT_RIGHT -> state.with(WIRE_CONNECTION_NORTH, state.get(WIRE_CONNECTION_SOUTH)).with(WIRE_CONNECTION_SOUTH, state.get(WIRE_CONNECTION_NORTH));
            case FRONT_BACK -> state.with(WIRE_CONNECTION_EAST, state.get(WIRE_CONNECTION_WEST)).with(WIRE_CONNECTION_WEST, state.get(WIRE_CONNECTION_EAST));
            default -> super.mirror(state, mirror);
        };
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WIRE_CONNECTION_NORTH, WIRE_CONNECTION_EAST, WIRE_CONNECTION_SOUTH, WIRE_CONNECTION_WEST);
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.getAbilities().allowModifyWorld) {
            if (isFullyConnected(state) || isNotConnected(state)) {
                BlockState blockState = isFullyConnected(state) ? this.getDefaultState() : this.dotState;
                blockState = this.getPlacementState(world, blockState, pos);
                if (blockState != state) {
                    world.setBlockState(pos, blockState, Block.NOTIFY_ALL);
                    this.updateForNewState(world, pos, state, blockState);
                    return ActionResult.SUCCESS;
                }
            }

        }
        return ActionResult.PASS;
    }

    private void updateForNewState(World world, BlockPos pos, BlockState oldState, BlockState newState) {

        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos blockPos = pos.offset(direction);
            if (oldState.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction)).isConnected() != newState.get(DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction)).isConnected() && world.getBlockState(blockPos).isSolidBlock(world, blockPos)) {
                world.updateNeighborsExcept(blockPos, newState.getBlock(), direction.getOpposite());
            }
        }

    }
}
