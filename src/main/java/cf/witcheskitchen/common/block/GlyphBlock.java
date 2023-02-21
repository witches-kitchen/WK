package cf.witcheskitchen.common.block;

import cf.witcheskitchen.api.block.WKBlockWithEntity;
import cf.witcheskitchen.common.blockentity.GlyphBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class GlyphBlock extends WKBlockWithEntity {
    private static final IntProperty GLYPH = IntProperty.of("type", 0, 9);
    private static final BooleanProperty ACTIVE = BooleanProperty.of("active");
    private final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 1.0, 0.01, 1.0);
    public static final DirectionProperty FACING = DirectionProperty.of("facing", Direction.Type.HORIZONTAL);

    public GlyphBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(ACTIVE, false).with(FACING, Direction.NORTH).with(GLYPH, 0));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing()).with(GLYPH, ctx.getWorld().random.nextInt(9));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return state.get(ACTIVE) ? new GlyphBlockEntity(pos, state) : null;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE, FACING, GLYPH);
        super.appendProperties(builder);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return !world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos.down(), Direction.UP);
    }
}
